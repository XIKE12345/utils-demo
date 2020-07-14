package com.jieyun.test.service.impl;

import com.jieyun.test.entity.*;
import com.jieyun.test.entity.dto.*;
import com.jieyun.test.mapper.*;
import com.jieyun.test.service.FileService;
import com.jieyun.test.utils.BaseResult;
import com.jieyun.test.utils.CsvUtil;
import com.jieyun.test.utils.zip.UnZipUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;


/**
 * @author huike
 */
@Service
@Slf4j
public class FileServiceImpl implements FileService {

    private final ProjectMapper projectDao;
    private final SingleHoleMapper singleHoleMapper;
    private final SampleDataMapper sampleDataMapper;
    private final ExpPointDataMapper expPointDataMapper;
    private final FourVaneMapper fourVaneMapper;
    private final StratumMapper stratumMapper;
    private final FeelerInspectionMapper feelerInspectionMapper;
    private final TLateralLoadTestMapper lateralLoadTestDao;
    private final TCdzhdcDataMapper areaMultipleDataDto;


    public FileServiceImpl(ProjectMapper projectDao, SingleHoleMapper singleHoleMapper, SampleDataMapper sampleDataMapper,
                           ExpPointDataMapper expPointDataMapper, FourVaneMapper fourVaneMapper, StratumMapper stratumMapper,
                           FeelerInspectionMapper feelerInspectionMapper, TLateralLoadTestMapper lateralLoadTestDao,
                           TCdzhdcDataMapper areaMultipleDataDto) {
        this.projectDao = projectDao;
        this.singleHoleMapper = singleHoleMapper;
        this.sampleDataMapper = sampleDataMapper;
        this.expPointDataMapper = expPointDataMapper;
        this.fourVaneMapper = fourVaneMapper;
        this.stratumMapper = stratumMapper;
        this.feelerInspectionMapper = feelerInspectionMapper;
        this.lateralLoadTestDao = lateralLoadTestDao;
        this.areaMultipleDataDto = areaMultipleDataDto;
    }

    /**
     * 存放文件路径的Set
     */
    private final Set<String> filePathSet = new HashSet<>(16);
    /**
     * 判断是不是整数，小数
     */
    Pattern pattern = Pattern.compile("([1-9]\\d*\\.?\\d+)|(0\\.\\d*[1-9])|(\\d+)");

    @Override
    public BaseResult<Map<String, Object>> fileParse(MultipartFile zipFile, String projectLocalNum) throws Exception {
        BaseResult<Map<String, Object>> result;
        // 查询所有的工程代号t_project.local_code
        Set<String> localCodeLists = this.projectDao.getLocalCodeLists();
        if (!localCodeLists.contains(projectLocalNum)) {
            return BaseResult.create(HttpStatus.SC_INTERNAL_SERVER_ERROR, "该工程还没有录入系统中，请检查文件的工程代号是否正确");
        }

        String projectId = this.singleHoleMapper.selectProjectIdByProjectNum(projectLocalNum);
        // 将zip文件上传到文件
        String proZipFilePath = uploadZipFile(zipFile);
        // 解压文件
        String unZipFolderPath = UnZipUtils.getUnZipDirFile().getAbsolutePath();
        UnZipUtils.unZipFiles(proZipFilePath, unZipFolderPath);
        // 递归遍历文件夹中的文件，将遍历出来的文件地址放入Set中等待下一步读取

        File file = new File(unZipFolderPath);
        // 递归文件夹下的文件，并将文件过滤并修改成.csv格式的文件，返回新格式下的文件路径
        Set<String> funcFilePathSet = func(file);

        List<SingleHoleDto> singleHoles = this.singleHoleMapper.selectSingleHoleNum(projectLocalNum);

        // 处理单孔原始数据 -- ✅
        handleDkOrgData(funcFilePathSet, projectId);
        log.info("----------handle single hole org data end----------");

        // 处理采样原始数据 -- ✅
        handleSampleOrgData(funcFilePathSet, projectId);
        log.info("----------handle Sampling org data end ----------");

        // 处理标贯数据 -- ✅
        handleStandardOrgData(funcFilePathSet, projectId);
        log.info("----------handle standard org data end ----------");

        // 处理单桥静力触探数据文件(t_feeler_inspection)  -- ✅
        handleDqTouchOrgData(funcFilePathSet, projectId, singleHoles);
        log.info("----------handle Dq Touch org data end ----------");

        // 处理双桥静力触探数据(t_feeler_inspection)  -- ✅
        handleSqTouchOrgData(funcFilePathSet, projectId, singleHoles);
        log.info("----------handle Sq Touch org data end ----------");

        // 处理地层数据文件(t_stratum) -- ✅
        handleStratumOrgData(funcFilePathSet, projectId);
        log.info("----------handle stratum org data end ----------");

        // 处理十字板数据文件(t_four_vane) -- ✅
        handleCrossPlateOrgData(funcFilePathSet, projectId);
        log.info("----------handle cross plate org data end ----------");

        // 处理旁压数据文件(t_lateral_load_test)  -- ✅
        handleSidePressureOrgData(funcFilePathSet, projectId);
        log.info("----------handle side pressure org data end ----------");

        // 处理场地综合描述数据(t_cdzhdc_data)
        handleAreaMultipleOrgData(funcFilePathSet, projectId);
        log.info("----------handle area multiple org data end ----------");

        // 处理动探数据文件N63.5动探原始数据(t_exp_point_data)  -- ✅
        handleDynamicN63OrgData(funcFilePathSet, projectId, singleHoles);
        log.info("----------handle dynamic 63.5 org data end ----------");

        // 处理动探数据文件N120动探原始数据(t_exp_point_data) -- ✅
        handleDynamicN120OrgData(funcFilePathSet, projectId, singleHoles);
        log.info("----------handle dynamic 120 org data end ----------");

        // 处理动探数据文件N10动探原始数据(t_exp_point_data) -- ✅
        handleDynamicN10OrgData(funcFilePathSet, projectId, singleHoles);
        log.info("----------handle dynamic 120 org data end ----------");

        // 删除上传的文件
        String uploadFload = proZipFilePath.substring(0, proZipFilePath.lastIndexOf("/"));
        deleteAll(new File(uploadFload));
        log.info("----------delete file success ----------");
        result = BaseResult.create(HttpStatus.SC_OK, "Request handled success");
        return result;
    }

    /**
     * 处理单孔原始数据
     *
     * @param funcFilePathSet 递归之后的文件
     * @throws Exception 异常
     */
    private void handleDkOrgData(Set<String> funcFilePathSet, String projectId) throws Exception {

        List<TSingleHole> tSingleHoles = new ArrayList<>(16);
        String dkOrgName = "DK.";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd yy:MM:dd");
        for (String filePath : funcFilePathSet) {
            // 先处理单孔数据，如果是单孔数据，将单孔数据导入数据库,保证该文件夹下的单孔数据首先被处理
            if (filePath.contains(dkOrgName)) {
                // 读取文件内容转化为Bean
                CsvUtil csvUtil = new CsvUtil();
                String[] columns = new String[]{"holeNum", "holeHigh", "holeDeep", "startWaterLine", "stableWaterLine",
                        "outDate", "viewDate", "coordinateX", "coordinateY", "wellDeep", "exploreSiteTypeCode"};
                // 返回的是单个文件的Bean，之后将Bean存入数据库中
                List<DkOrgDataParseDto> dkOrgDataParseDtos = csvUtil.readFileToBean(filePath, DkOrgDataParseDto.class, columns);
                dkOrgDataParseDtos.removeIf(dkOrgDataParseDto1 -> dkOrgDataParseDto1.getHoleNum().contains("END"));

                dkOrgDataParseDtos.forEach(
                        dkOrgDataParseDto -> {
                            TSingleHole singleHole = new TSingleHole();
                            singleHole.setProjectId(projectId);
                            singleHole.setHoleNum(dkOrgDataParseDto.getHoleNum());
                            singleHole.setOrifElev(Double.parseDouble(StringUtils.isEmpty(dkOrgDataParseDto.getHoleHigh()) ? "0.0" : dkOrgDataParseDto.getHoleHigh()));
                            singleHole.setExplDepth(Float.parseFloat(StringUtils.isEmpty(dkOrgDataParseDto.getHoleDeep()) ? "0.0" : dkOrgDataParseDto.getHoleDeep()));
                            singleHole.setIwlDepth(Float.parseFloat(StringUtils.isEmpty(dkOrgDataParseDto.getStartWaterLine()) ? "0.0" : dkOrgDataParseDto.getStartWaterLine()));
                            singleHole.setFlDepth(Float.parseFloat(StringUtils.isEmpty(dkOrgDataParseDto.getStableWaterLine()) ? "0.0" : dkOrgDataParseDto.getStableWaterLine()));
                            try {
                                singleHole.setOworkData(sdf.parse(StringUtils.isEmpty(dkOrgDataParseDto.getOutDate()) ? "1970-01-01 00:00:00" : dkOrgDataParseDto.getOutDate()));
                                singleHole.setObseData(sdf.parse(StringUtils.isEmpty(dkOrgDataParseDto.getViewDate()) ? "1970-01-01 00:00:00" : dkOrgDataParseDto.getViewDate()));
                            } catch (ParseException e) {
                                log.error("日期解析错误，请检查文件中的时间格式 孔号：{} {}", dkOrgDataParseDto.getHoleNum(), e);
                            }
                            singleHole.setCoorX(Double.parseDouble(StringUtils.isEmpty(dkOrgDataParseDto.getCoordinateX()) ? "0.0" : dkOrgDataParseDto.getCoordinateX()));
                            singleHole.setCoorY(Double.parseDouble(StringUtils.isEmpty(dkOrgDataParseDto.getCoordinateY()) ? "0.0" : dkOrgDataParseDto.getCoordinateY()));
                            singleHole.setExplWellDepth(Float.parseFloat(StringUtils.isEmpty(dkOrgDataParseDto.getWellDeep()) ? "0.0" : dkOrgDataParseDto.getWellDeep()));
                            singleHole.setEpTypeCode(Integer.parseInt(StringUtils.isEmpty(dkOrgDataParseDto.getExploreSiteTypeCode()) ? "0.0" : dkOrgDataParseDto.getExploreSiteTypeCode()));
                            singleHole.setMileage(dkOrgDataParseDto.getMileage());
                            dkOrgDataParseDto.setProjectId(projectId);
                            tSingleHoles.add(singleHole);
                        }
                );

                int i = singleHoleMapper.batchInsertIntoDkData(tSingleHoles);
                if (i <= 0) {
                    throw new Exception("单孔数据入库异常，请联系系统管理员");
                }
            }
        }
    }

    /**
     * 处理采样数据文件
     *
     * @param funcFilePathSet 文件路径Set
     */
    private void handleSampleOrgData(Set<String> funcFilePathSet, String projectId) throws Exception {

        List<String> sampleDataFilePaths = new ArrayList<>(16);
        List<SampleData> sampleDataLists = new ArrayList<>(16);
        // 处理单孔（某个孔）下的取样数据，存入取样数据表（t_sample_data）
        for (String filePath : funcFilePathSet) {
            String sampleOrgName = "TY1";
            if (filePath.contains(sampleOrgName)) {
                sampleDataFilePaths.add(filePath);
            }
        }
        // 解析采样数据文件，将文件转换成Bean
        CsvUtil csvUtil = new CsvUtil();
        for (String samplePath : sampleDataFilePaths) {
            // 先从解析的文件中挑出 采样数据文件 ,采样数据文件是 TY+孔号.工程号
            List<SamplingDataOrgDto> samplingDataOrgDtos;
            String[] columns = new String[]{"holeNum", "startDepth", "sampleType", "sampleLength", "testNum"};
            // 返回的是单个文件的Bean，之后将Bean存入数据库中
            samplingDataOrgDtos = csvUtil.readFileToBean(samplePath, SamplingDataOrgDto.class, columns);
            samplingDataOrgDtos.removeIf(samplingDataOrgDto -> samplingDataOrgDto.getHoleNum().contains("END"));

            final String[] holeNumTmp = {null};
            for (SamplingDataOrgDto samplingDataOrgDto : samplingDataOrgDtos) {
                String holeNum = samplingDataOrgDto.getHoleNum();
                // 根据holeNum查drillId
                if (!StringUtils.isEmpty(holeNum)) {
                    holeNumTmp[0] = holeNum;
                } else {
                    holeNum = holeNumTmp[0];
                }
                String drillId = this.singleHoleMapper.selectDrillIdByHoleNum(holeNum, projectId);
                samplingDataOrgDto.setDrillId(drillId);
                // 以"." 开头，在其前面补零
                if (samplingDataOrgDto.getStartDepth().startsWith(".")) {
                    samplingDataOrgDto.setStartDepth("0" + samplingDataOrgDto.getStartDepth());
                }
                String depth = samplingDataOrgDto.getStartDepth();
                String startDepth = "";
                double endDepth;
                String[] split = depth.split("-");
                if (split.length > 1) {
                    startDepth = split[0];
                    endDepth = Double.parseDouble(split[1]);
                } else {
                    // 终止深度 =  起始深度 + 取样长度
                    endDepth = Double.parseDouble(split[0]) + Double.parseDouble(StringUtils.isEmpty(samplingDataOrgDto.getSampleLength()) ? "0.0" : samplingDataOrgDto.getSampleLength());
                }
                if (!StringUtils.isEmpty(drillId)) {
                    SampleData sampleData = new SampleData();
                    sampleData.setDrillId(drillId);
                    sampleData.setStartDepth(Double.parseDouble(StringUtils.isEmpty(startDepth) ? "0.0" : startDepth));
                    sampleData.setEndDepth(endDepth);
                    sampleData.setSampleLength(Double.parseDouble(StringUtils.isEmpty(samplingDataOrgDto.getSampleLength()) ? "0.0" : samplingDataOrgDto.getSampleLength()));
                    sampleData.setSampleType(Integer.parseInt(StringUtils.isEmpty(samplingDataOrgDto.getSampleType()) ? "0" : samplingDataOrgDto.getSampleType()));
                    sampleData.setTestNum(samplingDataOrgDto.getTestNum());
                    sampleDataLists.add(sampleData);
                }
            }
            // 入库
            int i = sampleDataMapper.batchInsertIntoSampleData(sampleDataLists);
            if (i <= 0) {
                throw new Exception("采样数据入库异常，请联系系统管理员");
            }
        }
    }

    /**
     * 处理标贯数据文件（按工程处理-->孔--->数据）
     * 勘探点数据表（t_exp_point_data)
     */
    private void handleStandardOrgData(Set<String> funcFilePathSet, String projectId) throws Exception {
        // 标贯文件路径
        List<String> standardFilePaths = new ArrayList<>(16);
        // 实体列表
        List<TExpPointData> expPointDataLists = new ArrayList<>(16);
        // 挑出文件夹中的标贯数据文件
        for (String filePath : funcFilePathSet) {
            String filePath1 = filePath;
            // 挑出孔下的标贯数据文件
            String substring = filePath.substring(filePath.lastIndexOf("/") + 1);
            if (substring.startsWith("BG.")) {
                standardFilePaths.add(filePath);
            }
        }
        // 将标贯数据转化成Bean
        CsvUtil csvUtil = new CsvUtil();
        for (String standardFilePath : standardFilePaths) {
            List<ExplorationSiteDataOrgDto> explorationSiteDataOrgDtos;
            String[] columns = new String[]{"holeNum", "startDepth", "poleLength", "factHit"};
            // 返回的是单个文件的Bean，之后将Bean存入数据库中
            explorationSiteDataOrgDtos = csvUtil.readFileToBean(standardFilePath, ExplorationSiteDataOrgDto.class, columns);

            explorationSiteDataOrgDtos.removeIf(explorationSiteDataOrgDto -> explorationSiteDataOrgDto.getHoleNum().contains("END"));
            String holeNumTmp = null;
            for (ExplorationSiteDataOrgDto explorationSiteDataOrgDto : explorationSiteDataOrgDtos) {
                // 勘探点数据类型 设置为标贯
                explorationSiteDataOrgDto.setExpPointType(1);
                // 解决文件第一列中孔号不齐全的问题
                String holeNum = explorationSiteDataOrgDto.getHoleNum();
                if (!StringUtils.isEmpty(holeNum)) {
                    holeNumTmp = holeNum;
                } else {
                    holeNum = holeNumTmp;
                }
                String drillId = this.singleHoleMapper.selectDrillIdByHoleNum(holeNum, projectId);
                // 以"." 开头，在其前面补零
                if (explorationSiteDataOrgDto.getStartDepth().startsWith(".")) {
                    explorationSiteDataOrgDto.setStartDepth("0" + explorationSiteDataOrgDto.getStartDepth());
                }
                if (!StringUtils.isEmpty(drillId)) {
                    TExpPointData expPointData = new TExpPointData();
                    expPointData.setId(UUID.randomUUID().toString().replace("-", ""));
                    expPointData.setDrillId(drillId);
                    expPointData.setHoleNum(holeNum);
                    expPointData.setSptOrigDepth(Double.parseDouble(StringUtils.isEmpty(explorationSiteDataOrgDto.getStartDepth()) ? "0.0" : explorationSiteDataOrgDto.getStartDepth()));
                    expPointData.setSptLength(Double.parseDouble(StringUtils.isEmpty(explorationSiteDataOrgDto.getPoleLength()) ? "0.0" : explorationSiteDataOrgDto.getPoleLength()));
                    boolean matches = pattern.matcher(explorationSiteDataOrgDto.getFactHit()).matches();
                    if (!matches) {
                        explorationSiteDataOrgDto.setFactHit("500");
                    }
                    expPointData.setSptMeasNum(Double.parseDouble(StringUtils.isEmpty(explorationSiteDataOrgDto.getFactHit()) ? "0.0" : explorationSiteDataOrgDto.getFactHit()));
                    // 勘探点类型：标贯
                    expPointData.setExpPointType(1);
                    expPointDataLists.add(expPointData);
                }
            }
            // 入库
            if (expPointDataLists.size() > 0) {
                int i = expPointDataMapper.batchInsertBgDataOrg(expPointDataLists);
                if (i <= 0) {
                    throw new Exception("标贯数据入库异常，请联系系统管理员");
                }
            }
        }
    }

    /**
     * 处理触探数据文件单桥
     * 单桥DQ
     *
     * @param funcFilePathSet 文件路径Set
     */
    private void handleDqTouchOrgData(Set<String> funcFilePathSet, String projectId, List<SingleHoleDto> singleHoles) throws Exception {
        // 存放触探文件路径
        List<String> dqTouchDataFilePaths = new ArrayList<>(16);
        List<TFeelerInspection> tFeelerInspections = new ArrayList<>(16);
        // 挑出文件夹中的单桥触探数据文件
        for (String filePath : funcFilePathSet) {
            String substring = filePath.substring(filePath.lastIndexOf("/") + 1);
            // 挑出孔下的触探数据文件 DQ + 孔号
            for (SingleHoleDto singleHole : singleHoles) {
                String sampleOrgName = "DQ" + singleHole.getHoleNum();
                if (substring.startsWith("DQ") && filePath.contains(sampleOrgName)) {
                    dqTouchDataFilePaths.add(filePath);
                }
            }
        }
        // 解析单桥触探文件，将文件转换成Bean
        List<SteadyTouchDataOrgDto> dqTouchDataOrgLists;
        CsvUtil csvUtil = new CsvUtil();
        for (String touchDataFilePath : dqTouchDataFilePaths) {
            String[] columns = new String[]{"depth", "microStrainP"};
            // 根据文件标题取出孔号
            File file = new File(touchDataFilePath);
            String pointBefore = file.getName().substring(0, file.getName().indexOf("."));
            String ty = pointBefore.substring(0, pointBefore.indexOf("DQ"));
            String holeNum = pointBefore.substring(ty.length() + 2);
            // DQ + 孔号 .csv
            dqTouchDataOrgLists = csvUtil.readFileToBean(touchDataFilePath, SteadyTouchDataOrgDto.class, columns);
            dqTouchDataOrgLists.removeIf(dqTouchDataOrgDto -> dqTouchDataOrgDto.getDepth().contains("END"));
            // 孔的id
            String drillId = this.singleHoleMapper.selectDrillIdByHoleNum(holeNum, projectId);
            if (!StringUtils.isEmpty(drillId)) {
                for (int i = 0; i <= dqTouchDataOrgLists.size() - 1; i++) {
                    float ratioP = 1.0f;
                    // 获取第一行的数据，第一行的数据是率定系数
                    SteadyTouchDataOrgDto steadyTouchDataOrgDto = dqTouchDataOrgLists.get(i);
                    if (i == 0) {
                        // 率定系数
                        ratioP = Float.parseFloat(StringUtils.isEmpty(steadyTouchDataOrgDto.getMicroStrainP()) ? "1.0" : steadyTouchDataOrgDto.getMicroStrainP());
                    }
                    TFeelerInspection tFeelerInspection = new TFeelerInspection();
                    tFeelerInspection.setDrillId(drillId);
                    tFeelerInspection.setRatioP(ratioP);
                    tFeelerInspection.setMicroStrainF(Float.parseFloat(StringUtils.isEmpty(steadyTouchDataOrgDto.getMicroStrainF()) ? "1.0" : steadyTouchDataOrgDto.getMicroStrainF()));
                    tFeelerInspection.setMicroStrainQ(Float.parseFloat(StringUtils.isEmpty(steadyTouchDataOrgDto.getMicroStrainU()) ? "1.0" : steadyTouchDataOrgDto.getMicroStrainU()));
                    tFeelerInspection.setDataType(1);
                    tFeelerInspections.add(tFeelerInspection);
                }
            }
        }
        // 入库
        if (tFeelerInspections.size() > 0) {
            int i = this.feelerInspectionMapper.batchInsertTouchOrgData(tFeelerInspections);
            if (i <= 0) {
                throw new Exception("单桥触探数据入库异常，请联系系统管理员");
            }
        }
    }

    /**
     * 处理十字板原始数据文件
     * 数据库表：t_four_vane
     *
     * @param funcFilePathSet 文件路径Set
     */
    private void handleCrossPlateOrgData(Set<String> funcFilePathSet, String projectId) throws Exception {
        // 十字板文件路径
        List<String> crossPlateFilePaths = new ArrayList<>(16);
        List<TFourVane> fourVaneLists = new ArrayList<>(16);
        // 挑出文件夹中的十字板数据文件
        for (String filePath : funcFilePathSet) {
            // 挑出孔下的十字板数据文件"SZBSJ"--十字板数据
            String sampleOrgName = filePath.substring(filePath.lastIndexOf("/") + 1);
            if (sampleOrgName.startsWith("SZBSJ.")) {
                crossPlateFilePaths.add(filePath);
            }
        }
        // 将十字板文件数据转化成Bean
        CsvUtil csvUtil = new CsvUtil();
        for (String standardFilePath : crossPlateFilePaths) {
            // 十字板文件对应的Bean对象
            String[] columns = new String[]{"holeNum", "midpointDepth", "orgCu", "remodelCu"};
            // 返回的是单个文件的Bean，之后将Bean存入数据库中
            List<CrossPlateDataOrgDto> standardDataOrgDtos = csvUtil.readFileToBean(standardFilePath, CrossPlateDataOrgDto.class, columns);
            standardDataOrgDtos.removeIf(crossPlateDataOrgDto -> crossPlateDataOrgDto.getHoleNum().contains("END"));
            String holeNumTmp = null;
            for (CrossPlateDataOrgDto crossPlateDataOrgDto : standardDataOrgDtos) {
                // 解决文件第一列中孔号不齐全的问题
                String holeNum = crossPlateDataOrgDto.getHoleNum();
                if (!StringUtils.isEmpty(holeNum)) {
                    holeNumTmp = holeNum;
                } else {
                    holeNum = holeNumTmp;
                }
                // 根据孔号和工程Id查孔对应的主键id
                String drillId = this.singleHoleMapper.selectDrillIdByHoleNum(holeNum, projectId);
                if (!StringUtils.isEmpty(drillId)) {
                    TFourVane fourVane = new TFourVane();
                    fourVane.setDrillId(drillId);
                    fourVane.setTestDepth(Float.parseFloat(StringUtils.isEmpty(crossPlateDataOrgDto.getMidpointDepth()) ? "0.0" : crossPlateDataOrgDto.getMidpointDepth()));
                    fourVane.setTestCu(Float.parseFloat(StringUtils.isEmpty(crossPlateDataOrgDto.getOrgCu()) ? "0.0" : crossPlateDataOrgDto.getOrgCu()));
                    fourVane.setTestFcu(Float.parseFloat(StringUtils.isEmpty(crossPlateDataOrgDto.getRemodelCu()) ? "0.0" : crossPlateDataOrgDto.getRemodelCu()));
                    fourVaneLists.add(fourVane);
                }

            }
            // 入库
            if (fourVaneLists.size() > 0) {
                int i = fourVaneMapper.batchInsertCrossPlateOrgData(fourVaneLists);
                if (i <= 0) {
                    throw new Exception("十字板数据入库异常，请联系系统管理员");
                }
            }
        }
    }

    /**
     * 处理地层数据文件
     *
     * @param funcFilePathSet 文件路径Set
     */
    private void handleStratumOrgData(Set<String> funcFilePathSet, String projectId) throws Exception {
        // 存放地层数据文件路径
        List<String> stratumDataFilePaths = new ArrayList<>(16);
        // 挑出 地层原始数据文件
        for (String stratumDataFilePath : funcFilePathSet) {
            String substring = stratumDataFilePath.substring(stratumDataFilePath.lastIndexOf("/") + 1);
            if (substring.startsWith("DCSJ.")) {
                stratumDataFilePaths.add(stratumDataFilePath);
            }
        }
        // 解析地层文件，将文件转换成Bean
        List<StratumDataOrgDto> stratumDataOrgLists = new ArrayList<>(16);
        List<TStratum> stratumLists = new ArrayList<>(16);
        for (String stratumDataFilePath : stratumDataFilePaths) {
            CsvUtil csvUtil = new CsvUtil();
            String[] columns = new String[]{"holeNum", "levelNum", "levelBottomDepth", "stratumCode", "stratumSlope", "backup"};
            stratumDataOrgLists = csvUtil.readFileToBean(stratumDataFilePath, StratumDataOrgDto.class, columns);
        }
        stratumDataOrgLists.removeIf(stratumDataOrgDto -> stratumDataOrgDto.getHoleNum().contains("END"));
        String holeNumTmp = null;

        for (StratumDataOrgDto stratumDataOrgDto : stratumDataOrgLists) {
            // 解决文件第一列中孔号不齐全的问题
            String holeNum = stratumDataOrgDto.getHoleNum();
            if (!StringUtils.isEmpty(holeNum)) {
                holeNumTmp = holeNum;
            } else {
                holeNum = holeNumTmp;
            }
            // 根据孔号和工程Id查孔对应的主键id
            String drillId = this.singleHoleMapper.selectDrillIdByHoleNum(holeNum, projectId);
            if (!StringUtils.isEmpty(drillId)) {
                TStratum stratum = new TStratum();
                stratum.setHoleNum(holeNum);
                stratum.setDrillId(drillId);
                stratum.setLelevNum(StringUtils.isEmpty(stratumDataOrgDto.getLevelNum()) ? "0.0" : stratumDataOrgDto.getLevelNum());
                stratum.setRockBottomDepth(Float.parseFloat(StringUtils.isEmpty(stratumDataOrgDto.getLevelBottomDepth()) ? "0.0" : stratumDataOrgDto.getLevelBottomDepth()));
                stratum.setRockCode(Float.parseFloat(StringUtils.isEmpty(stratumDataOrgDto.getStratumCode()) ? "0.0" : stratumDataOrgDto.getStratumCode()));
                stratum.setRockDipAngle(Float.parseFloat(StringUtils.isEmpty(stratumDataOrgDto.getStratumSlope()) ? "0.0" : stratumDataOrgDto.getStratumSlope()));
                stratumLists.add(stratum);
            }
        }
        // 入库
        if (stratumDataOrgLists.size() > 0) {
            int i = stratumMapper.batchInsertStratumOrgData(stratumLists);
            if (i <= 0) {
                throw new Exception("地层数据入库异常，请联系系统管理员");
            }
        }
    }

    /**
     * 处理触探数据文件(双桥 分开处理)
     * 双桥 Sq
     *
     * @param funcFilePathSet 文件路径Set
     */
    private void handleSqTouchOrgData(Set<String> funcFilePathSet, String projectId, List<SingleHoleDto> singleHoles) throws Exception {
        // 存放触探文件路径
        List<String> sqTouchDataFilePaths = new ArrayList<>(16);
        // 挑出文件夹中的单桥触探数据文件
        for (String filePath : funcFilePathSet) {

            String substring = filePath.substring(filePath.lastIndexOf("/") + 1);
            // 挑出孔下的触探数据文件 Sq + 孔号 双桥
            for (SingleHoleDto singleHole : singleHoles) {
                String sampleOrgName = "Sq" + singleHole.getHoleNum();
                if (substring.startsWith("Sq") && filePath.contains(sampleOrgName)) {
                    sqTouchDataFilePaths.add(filePath);
                }
            }
        }
        // 解析单桥静力触探文件，将文件转换成Bean
        List<SteadyTouchDataOrgDto> sqTouchDataOrgLists;
        List<TFeelerInspection> tFeelerInspections = new ArrayList<>(16);
        CsvUtil csvUtil = new CsvUtil();
        // 解析双桥触探数据
        for (String touchDataFilePath : sqTouchDataFilePaths) {
            // 双桥静探原始文件对象
            String[] columns = new String[]{"microStrainQ", "microStrainF"};
            // DQ + 孔号 .csv
            sqTouchDataOrgLists = csvUtil.readFileToBean(touchDataFilePath, SteadyTouchDataOrgDto.class, columns);
            sqTouchDataOrgLists.removeIf(sqTouchDataOrgDto -> sqTouchDataOrgDto.getMicroStrainF().contains("END"));
            // 根据文件标题取出孔号
            File file = new File(touchDataFilePath);
            String pointBefore = file.getName().substring(0, file.getName().indexOf("."));
            String ty = pointBefore.substring(0, pointBefore.indexOf("Sq"));

            String holeNum = pointBefore.substring(ty.length() + 2);
            String drillId = this.singleHoleMapper.selectDrillIdByHoleNum(holeNum, projectId);
            for (int i = 0; i <= sqTouchDataOrgLists.size() - 1; i++) {
                float ratioQ = 1.0f;
                float ratioF = 1.0f;
                // 获取第一行的数据，第一行的数据是率定系数
                SteadyTouchDataOrgDto steadyTouchDataOrgDto = sqTouchDataOrgLists.get(i);
                if (i == 0) {
                    ratioQ = Float.parseFloat(StringUtils.isEmpty(steadyTouchDataOrgDto.getMicroStrainU()) ? "1.0" : steadyTouchDataOrgDto.getMicroStrainU());
                    ratioF = Float.parseFloat(StringUtils.isEmpty(steadyTouchDataOrgDto.getMicroStrainF()) ? "1.0" : steadyTouchDataOrgDto.getMicroStrainF());
                }
                if (!StringUtils.isEmpty(drillId)) {
                    TFeelerInspection tFeelerInspection = new TFeelerInspection();
                    tFeelerInspection.setDrillId(drillId);
                    tFeelerInspection.setRatioQ(ratioQ);
                    tFeelerInspection.setRatioF(ratioF);
                    tFeelerInspection.setMicroStrainF(Float.parseFloat(StringUtils.isEmpty(steadyTouchDataOrgDto.getMicroStrainF()) ? "1.0" : steadyTouchDataOrgDto.getMicroStrainF()));
                    tFeelerInspection.setMicroStrainQ(Float.parseFloat(StringUtils.isEmpty(steadyTouchDataOrgDto.getMicroStrainU()) ? "1.0" : steadyTouchDataOrgDto.getMicroStrainU()));
                    tFeelerInspection.setDataType(2);
                    tFeelerInspections.add(tFeelerInspection);
                }
            }
        }
        // 入库
        if (tFeelerInspections.size() > 0) {
            int i = this.feelerInspectionMapper.batchInsertTouchOrgData(tFeelerInspections);
            if (i <= 0) {
                throw new Exception("双桥触探数据入库异常，请联系系统管理员");
            }
        }
    }

    /**
     * 处理旁压数据文件
     *
     * @param funcFilePathSet 文件路径Set
     */
    private void handleSidePressureOrgData(Set<String> funcFilePathSet, String projectId) throws Exception {
        // 旁压文件路径
        List<String> sidePressureFilePaths = new ArrayList<>(16);

        List<TLateralLoadTest> lateralLoadTestLists = new ArrayList<>(16);

        // 挑出文件夹中的旁压数据文件
        for (String filePath : funcFilePathSet) {
            String substring = filePath.substring(filePath.lastIndexOf("/") + 1);
            if (substring.startsWith("PY.")) {
                sidePressureFilePaths.add(filePath);
            }
        }
        // 将旁压数据转化成Bean
        CsvUtil csvUtil = new CsvUtil();
        for (String sidePressureFilePath : sidePressureFilePaths) {
            // 旁压文件对应的Bean对象
            String[] columns = new String[]{"holeNum", "testMidpointDepth"};
            // 返回的是单个文件的Bean，之后将Bean存入数据库中
            List<SidePressureDto> sidePressureDtos = csvUtil.readFileToBean(sidePressureFilePath, SidePressureDto.class, columns);
            sidePressureDtos.removeIf(sidePressureDto -> sidePressureDto.getHoleNum().contains("END"));
            String holeNumTmp = null;
            for (SidePressureDto sidePressureDto : sidePressureDtos) {
                // 解决文件第一列中孔号不齐全的问题
                String holeNum = sidePressureDto.getHoleNum();
                if (!StringUtils.isEmpty(holeNum)) {
                    holeNumTmp = holeNum;
                } else {
                    holeNum = holeNumTmp;
                }
                // 根据孔号和工程Id查孔对应的主键id
                String drillId = this.singleHoleMapper.selectDrillIdByHoleNum(holeNum, projectId);
                if (!StringUtils.isEmpty(drillId)) {
                    TLateralLoadTest tLateralLoadTest = new TLateralLoadTest();
                    tLateralLoadTest.setDrillId(drillId);
                    tLateralLoadTest.setTestDepth(Float.parseFloat(StringUtils.isEmpty(sidePressureDto.getTestMidpointDepth()) ? "0.0" : sidePressureDto.getTestMidpointDepth()));
                    lateralLoadTestLists.add(tLateralLoadTest);
                }
            }
            if (lateralLoadTestLists.size() > 0) {
                int i = lateralLoadTestDao.batchInsertSidePressureData(lateralLoadTestLists);
                if (i <= 0) {
                    throw new Exception("旁压数据入库异常，请联系系统管理员");
                }
            }
        }
    }

    /**
     * 处理场地综合描述数据 （老版的是由三个文件组合而成）
     * 新版场地综合文件处理
     * DCSH_2018.10 整合之后的文件
     * DSCH.10
     *
     * @param funcFilePathSet 文件路径Set
     */
    private void handleAreaMultipleOrgData(Set<String> funcFilePathSet, String projectId) throws Exception {
        List<String> yardFilePaths = new ArrayList<>(16);
        List<TCdzhdcData> cdzhdcDataLists = new ArrayList<>(16);

        // 挑出文件集合中的新版场地综合的原始数据文件
        for (String filePath : funcFilePathSet) {
            String substring = filePath.substring(filePath.lastIndexOf("/") + 1);
            if (substring.startsWith("DCSH_2018.")) {
                yardFilePaths.add(filePath);
            }
        }
        CsvUtil csvUtil = new CsvUtil();
        for (String yardFilepath : yardFilePaths) {
            String[] columns = new String[]{"levelNum", "rockCode", "rockName", "geologyReason", "geologyAge",
                    "bearingCapacity", "modulusCom", "weight", "stratumType", "precastLimitNewel", "precastLimitBearing",
                    "castInPlaceLimitNewel", "castInPlaceLimitBearing", "soilType", "upliftPile_λ", "backGrout_βsi0",
                    "backGrout_βp", "colour", "humidity", "stateDensity", "compressibility", "shakingResp", "glossResp",
                    "dryStrength", "tenacity", "inclusions", "rockStiff", "rockFull", "rockFrameType", "otherRockDesc"};
            List<AreaMultipleDataNewOrgDto> areaMultipleDataNewOrgDtos = csvUtil.readFileToBean(yardFilepath, AreaMultipleDataNewOrgDto.class, columns);
            areaMultipleDataNewOrgDtos.removeIf(areaMultipleDataNewOrgDto -> areaMultipleDataNewOrgDto.getLevelNum().contains("END"));
            for (AreaMultipleDataNewOrgDto areaMultipleDataNewOrgDto : areaMultipleDataNewOrgDtos) {

                TCdzhdcData cdzhdcData = new TCdzhdcData();
                cdzhdcData.setProjectId(projectId);
                cdzhdcData.setLevelNum(Double.parseDouble(StringUtils.isEmpty(areaMultipleDataNewOrgDto.getLevelNum()) ? "0.0" : areaMultipleDataNewOrgDto.getLevelNum()));
                cdzhdcData.setGrouCode(areaMultipleDataNewOrgDto.getRockCode());
                cdzhdcData.setGrouName(areaMultipleDataNewOrgDto.getRockName());
                cdzhdcData.setGeoOrigin(areaMultipleDataNewOrgDto.getGeologyReason());
                cdzhdcData.setGeoAge(areaMultipleDataNewOrgDto.getGeologyAge());
                cdzhdcData.setBearCapacity(Float.parseFloat(StringUtils.isEmpty(areaMultipleDataNewOrgDto.getBearingCapacity()) ? "0.0" : areaMultipleDataNewOrgDto.getBearingCapacity()));
                cdzhdcData.setCompModel(Float.parseFloat(StringUtils.isEmpty(areaMultipleDataNewOrgDto.getModulusCom()) ? "0.0" : areaMultipleDataNewOrgDto.getModulusCom()));

                cdzhdcData.setUnitWeight(Float.parseFloat(StringUtils.isEmpty(areaMultipleDataNewOrgDto.getWeight()) ? "0.0" : areaMultipleDataNewOrgDto.getWeight()));
                cdzhdcData.setStratumType(Float.parseFloat(StringUtils.isEmpty(areaMultipleDataNewOrgDto.getStratumType()) ? "0.0" : areaMultipleDataNewOrgDto.getStratumType()));
                cdzhdcData.setFirstQsk(Float.parseFloat(StringUtils.isEmpty(areaMultipleDataNewOrgDto.getPrecastLimitNewel()) ? "0.0" : areaMultipleDataNewOrgDto.getPrecastLimitNewel()));
                cdzhdcData.setFristQpk(Float.parseFloat(StringUtils.isEmpty(areaMultipleDataNewOrgDto.getPrecastLimitBearing()) ? "0.0" : areaMultipleDataNewOrgDto.getPrecastLimitBearing()));
                cdzhdcData.setSoilType(Integer.parseInt(StringUtils.isEmpty(areaMultipleDataNewOrgDto.getSoilType()) ? "0" : areaMultipleDataNewOrgDto.getSoilType()));
                cdzhdcData.setTcop(Float.parseFloat(StringUtils.isEmpty(areaMultipleDataNewOrgDto.getUpliftPile_λ()) ? "0.0" : areaMultipleDataNewOrgDto.getUpliftPile_λ()));
                cdzhdcData.setDcorgs(Float.parseFloat(StringUtils.isEmpty(areaMultipleDataNewOrgDto.getBackGrout_βsi0()) ? "0.0" : areaMultipleDataNewOrgDto.getBackGrout_βsi0()));
                cdzhdcData.setDcorge(Float.parseFloat(StringUtils.isEmpty(areaMultipleDataNewOrgDto.getBackGrout_βp()) ? "0.0" : areaMultipleDataNewOrgDto.getBackGrout_βp()));
                cdzhdcData.setColor(Integer.parseInt(StringUtils.isEmpty(areaMultipleDataNewOrgDto.getColour()) ? "0" : areaMultipleDataNewOrgDto.getColour()));

                cdzhdcData.setHumidity(Integer.parseInt(StringUtils.isEmpty(areaMultipleDataNewOrgDto.getHumidity()) ? "0" : areaMultipleDataNewOrgDto.getHumidity()));
                cdzhdcData.setStateDensity(Integer.parseInt(StringUtils.isEmpty(areaMultipleDataNewOrgDto.getStateDensity()) ? "" : areaMultipleDataNewOrgDto.getStateDensity()));
                cdzhdcData.setCoercibility(Integer.parseInt(StringUtils.isEmpty(areaMultipleDataNewOrgDto.getCompressibility()) ? "0" : areaMultipleDataNewOrgDto.getCompressibility()));
                cdzhdcData.setDilatance(Integer.parseInt(StringUtils.isEmpty(areaMultipleDataNewOrgDto.getShakingResp()) ? "0" : areaMultipleDataNewOrgDto.getShakingResp()));
                cdzhdcData.setGlossReaction(Integer.parseInt(StringUtils.isEmpty(areaMultipleDataNewOrgDto.getGlossResp()) ? "0" : areaMultipleDataNewOrgDto.getGlossResp()));
                cdzhdcData.setDryStrength(Integer.parseInt(StringUtils.isEmpty(areaMultipleDataNewOrgDto.getDryStrength()) ? "0" : areaMultipleDataNewOrgDto.getDryStrength()));
                cdzhdcData.setTenacity(Integer.parseInt(StringUtils.isEmpty(areaMultipleDataNewOrgDto.getTenacity()) ? "0" : areaMultipleDataNewOrgDto.getTenacity()));
                cdzhdcData.setInclusion(Integer.parseInt(StringUtils.isEmpty(areaMultipleDataNewOrgDto.getInclusions()) ? "0" : areaMultipleDataNewOrgDto.getInclusions()));

                cdzhdcData.setRockHardness(Integer.parseInt(StringUtils.isEmpty(areaMultipleDataNewOrgDto.getRockStiff()) ? "0" : areaMultipleDataNewOrgDto.getRockStiff()));
                cdzhdcData.setRockIntegrity(Integer.parseInt(StringUtils.isEmpty(areaMultipleDataNewOrgDto.getRockFull()) ? "0" : areaMultipleDataNewOrgDto.getRockFrameType()));
                cdzhdcData.setRockStrcType(Integer.parseInt(StringUtils.isEmpty(areaMultipleDataNewOrgDto.getRockFrameType()) ? "0" : areaMultipleDataNewOrgDto.getRockFrameType()));
                cdzhdcData.setOtherRockDesc(areaMultipleDataNewOrgDto.getOtherRockDesc());
                cdzhdcDataLists.add(cdzhdcData);
            }
            // 入库
            if (cdzhdcDataLists.size() > 0) {
                int i = this.areaMultipleDataDto.batchInsertAreaMultipleData(cdzhdcDataLists);
                if (i <= 0) {
                    throw new Exception("旁压数据入库异常，请联系系统管理员");
                }
            }
        }
    }

    /**
     * 处理动探数据文件(N63.5动探数据）
     *
     * @param funcFilePathSet 文件路径Set
     */
    private void handleDynamicN63OrgData(Set<String> funcFilePathSet, String projectId, List<SingleHoleDto> singleHoles) throws Exception {
        // 存放N63.5动探文件路径
        List<String> dynamicN63FilePaths = new ArrayList<>(16);

        List<TExpPointData> expPointDataLists = new ArrayList<>(16);
        // 挑出文件夹中的单桥触探数据文件
        for (String filePath : funcFilePathSet) {
            String substring = filePath.substring(filePath.lastIndexOf("/") + 1);
            // 挑出孔下的N63.5动探数据文件 N63 + 孔号
            for (SingleHoleDto singleHole : singleHoles) {
                String sampleOrgName = "N63" + singleHole.getHoleNum();
                if (substring.startsWith("N63") && filePath.contains(sampleOrgName)) {
                    dynamicN63FilePaths.add(filePath);
                }
            }
        }
        // 解析N63.5动探数据文件，将文件转换成Bean
        List<ExplorationSiteDataOrgDto> dynamicN63DataOrgLists;
        CsvUtil csvUtil = new CsvUtil();
        for (String touchDataFilePath : dynamicN63FilePaths) {
            String[] columns = new String[]{"poleLength", "factHit"};
            // 根据文件标题取出孔号
            File file = new File(touchDataFilePath);
            String pointBefore = file.getName().substring(0, file.getName().indexOf("."));
            String ty = pointBefore.substring(0, pointBefore.indexOf("N63"));
            String holeNum = pointBefore.substring(ty.length() + 2);
            // DQ + 孔号 .csv
            dynamicN63DataOrgLists = csvUtil.readFileToBean(touchDataFilePath, ExplorationSiteDataOrgDto.class, columns);
            dynamicN63DataOrgLists.removeIf(dynamicN63DataOrgDto -> dynamicN63DataOrgDto.getPoleLength().contains("END"));
            String drillId = this.singleHoleMapper.selectDrillIdByHoleNum(holeNum, projectId);
            for (ExplorationSiteDataOrgDto dynamicN63DataOrgDto : dynamicN63DataOrgLists) {
                if (!StringUtils.isEmpty(drillId)) {
                    TExpPointData expPointData = new TExpPointData();
                    // 孔的id
                    expPointData.setDrillId(drillId);
                    expPointData.setSptLength(Double.parseDouble(StringUtils.isEmpty(dynamicN63DataOrgDto.getPoleLength()) ? "0.0" : dynamicN63DataOrgDto.getPoleLength()));
                    expPointData.setSptMeasNum(Double.parseDouble(StringUtils.isEmpty(dynamicN63DataOrgDto.getFactHit()) ? "0.0" : dynamicN63DataOrgDto.getFactHit()));
                    // N63.5动探文件
                    expPointData.setExpPointType(2);
                    expPointDataLists.add(expPointData);
                }
            }
        }
        // 入库
        if (expPointDataLists.size() > 0) {
            int i = this.expPointDataMapper.batchInsertBgDataOrg(expPointDataLists);
            if (i <= 0) {
                throw new Exception("单桥触探数据入库异常，请联系系统管理员");
            }
        }
    }

    /**
     * 处理动探数据文件(N120动探数据）
     *
     * @param funcFilePathSet 文件路径Set
     */
    private void handleDynamicN120OrgData(Set<String> funcFilePathSet, String projectId, List<SingleHoleDto> singleHoles) throws Exception {
        // 存放N63.5动探文件路径
        List<String> dynamicN120FilePaths = new ArrayList<>(16);
        List<TExpPointData> expPointDataLists = new ArrayList<>(16);
        // 挑出文件夹中的单桥触探数据文件
        for (String filePath : funcFilePathSet) {
            String substring = filePath.substring(filePath.lastIndexOf("/") + 1);
            // 挑出孔下的N12动探数据文件 N63 + 孔号
            for (SingleHoleDto singleHole : singleHoles) {
                String sampleOrgName = "N12" + singleHole.getHoleNum();
                if (substring.startsWith("N12") && filePath.contains(sampleOrgName)) {
                    dynamicN120FilePaths.add(filePath);
                }
            }
        }
        // 解析N63.5动探数据文件，将文件转换成Bean
        List<ExplorationSiteDataOrgDto> dynamicN120DataOrgLists;
        CsvUtil csvUtil = new CsvUtil();
        for (String touchDataFilePath : dynamicN120FilePaths) {
            String[] columns = new String[]{"poleLength", "factHit"};
            // 根据文件标题取出孔号
            File file = new File(touchDataFilePath);
            String pointBefore = file.getName().substring(0, file.getName().indexOf("."));
            String ty = pointBefore.substring(0, pointBefore.indexOf("N12"));
            String holeNum = pointBefore.substring(ty.length() + 2);
            // DQ + 孔号 .csv
            dynamicN120DataOrgLists = csvUtil.readFileToBean(touchDataFilePath, ExplorationSiteDataOrgDto.class, columns);
            dynamicN120DataOrgLists.removeIf(dynamicN120DataOrgDto -> dynamicN120DataOrgDto.getPoleLength().contains("END"));
            String drillId = this.singleHoleMapper.selectDrillIdByHoleNum(holeNum, projectId);

            for (ExplorationSiteDataOrgDto dynamicN120DataOrgDto : dynamicN120DataOrgLists) {
                if (!StringUtils.isEmpty(drillId)) {
                    TExpPointData expPointData = new TExpPointData();
                    expPointData.setDrillId(drillId);
                    expPointData.setSptLength(Double.parseDouble(StringUtils.isEmpty(dynamicN120DataOrgDto.getPoleLength()) ? "0.0" : dynamicN120DataOrgDto.getPoleLength()));
                    expPointData.setSptMeasNum(Double.parseDouble(StringUtils.isEmpty(dynamicN120DataOrgDto.getFactHit()) ? "0.0" : dynamicN120DataOrgDto.getFactHit()));
                    // N120动探文件
                    expPointData.setExpPointType(3);
                    expPointDataLists.add(expPointData);
                }
            }
        }
        // 入库
        if (expPointDataLists.size() > 0) {
            int i = this.expPointDataMapper.batchInsertBgDataOrg(expPointDataLists);
            if (i <= 0) {
                throw new Exception("单桥触探数据入库异常，请联系系统管理员");
            }
        }
    }

    /**
     * 处理动探数据文件(N10动探数据）
     *
     * @param funcFilePathSet 文件路径Set
     */
    private void handleDynamicN10OrgData(Set<String> funcFilePathSet, String projectId, List<SingleHoleDto> singleHoles) throws Exception {
        // 存放N10动探文件路径
        List<String> dynamicN10FilePaths = new ArrayList<>(16);

        List<TExpPointData> expPointDataLists = new ArrayList<>(16);
        // 挑出文件夹中的单桥触探数据文件
        for (String filePath : funcFilePathSet) {
            String substring = filePath.substring(filePath.lastIndexOf("/") + 1);
            // 挑出孔下的N12动探数据文件 N10 + 孔号
            for (SingleHoleDto singleHole : singleHoles) {
                String sampleOrgName = "N10" + singleHole.getHoleNum();
                if (substring.startsWith("N10") && filePath.contains(sampleOrgName)) {
                    dynamicN10FilePaths.add(filePath);
                }
            }
        }
        // 解析N10动探数据文件，将文件转换成Bean
        List<ExplorationSiteDataOrgDto> dynamicN10DataOrgLists;
        CsvUtil csvUtil = new CsvUtil();
        for (String touchDataFilePath : dynamicN10FilePaths) {
            String[] columns = new String[]{"poleLength", "factHit"};
            // 根据文件标题取出孔号
            File file = new File(touchDataFilePath);
            String pointBefore = file.getName().substring(0, file.getName().indexOf("."));
            String ty = pointBefore.substring(0, pointBefore.indexOf("N10"));
            String holeNum = pointBefore.substring(ty.length() + 2);
            // DQ + 孔号 .csv
            dynamicN10DataOrgLists = csvUtil.readFileToBean(touchDataFilePath, ExplorationSiteDataOrgDto.class, columns);
            dynamicN10DataOrgLists.removeIf(dynamicN10DataOrgDto -> dynamicN10DataOrgDto.getPoleLength().contains("END"));
            // 孔的id
            String drillId = this.singleHoleMapper.selectDrillIdByHoleNum(holeNum, projectId);
            for (ExplorationSiteDataOrgDto dynamicN10DataOrgDto : dynamicN10DataOrgLists) {
                if (!StringUtils.isEmpty(drillId)) {
                    TExpPointData expPointData = new TExpPointData();
                    expPointData.setDrillId(drillId);
                    expPointData.setSptLength(Double.parseDouble(StringUtils.isEmpty(dynamicN10DataOrgDto.getPoleLength()) ? "0.0" : dynamicN10DataOrgDto.getPoleLength()));
                    expPointData.setSptMeasNum(Double.parseDouble(StringUtils.isEmpty(dynamicN10DataOrgDto.getFactHit()) ? "0.0" : dynamicN10DataOrgDto.getFactHit()));
                    // N120动探文件
                    expPointData.setExpPointType(4);
                    expPointDataLists.add(expPointData);
                }
            }
        }
        // 入库
        if (expPointDataLists.size() > 0) {
            int i = this.expPointDataMapper.batchInsertBgDataOrg(expPointDataLists);
            if (i <= 0) {
                throw new Exception("单桥触探数据入库异常，请联系系统管理员");
            }
        }
    }

    /**
     * 上传文件到项目中
     *
     * @param zipFile zip文件
     * @return String proFilePath 上传之后的zip文件绝对路径
     * @throws IOException IO异常
     */
    private String uploadZipFile(MultipartFile zipFile) throws IOException {
        String zipFileName = zipFile.getOriginalFilename();
        //存放上传zip文件的文件夹
        File unZipDirFile = UnZipUtils.getUnZipDirFile();
        String absolutePath = unZipDirFile.getAbsolutePath();
        log.info("absolutePath is {}", absolutePath);
        File proZipFile = new File(unZipDirFile.getAbsolutePath() + File.separator + zipFileName);
        String proFilePath = proZipFile.getAbsolutePath();
        log.info("proAbsoluteFilePath is {} ", proFilePath);
        zipFile.transferTo(proZipFile);
        return proFilePath;
    }

    /**
     * 删除上传的文件
     *
     * @param file 文件
     */
    public void deleteAll(File file) {
        if (!file.isFile() && file.list().length != 0) {
            for (File f : Objects.requireNonNull(file.listFiles())) {
                // 递归删除每个文件
                deleteAll(f);
            }
            // 删除文件夹
        }
        file.delete();
    }

    /**
     * 递归遍历文件夹
     *
     * @param file 文件
     * @return Set<String> 文件夹下文件的路径
     */
    public Set<String> func(File file) {
        File[] fs = file.listFiles();
        for (File f : Objects.requireNonNull(fs)) {
            //若是目录，则递归打印该目录下的文件
            if (f.isDirectory()) {
                func(f);
            }
            //若是文件，将文件路径放入Set中
            if (f.isFile()) {
                // 改文件后缀，改成.csv格式，便于之后的数据读取
                File oldFile = new File(f.getPath());
                String substring = f.getPath().substring(0, f.getPath().lastIndexOf("."));
                log.info("-----------------{}", substring);
                File newFile = new File(substring + ".csv");
                boolean renameIsOk = oldFile.renameTo(newFile);
                if (renameIsOk) {
                    filePathSet.add(newFile.getPath());
                }
            }
        }
        return filePathSet;
    }

}
