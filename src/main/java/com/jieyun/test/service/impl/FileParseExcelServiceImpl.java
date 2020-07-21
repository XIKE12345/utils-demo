package com.jieyun.test.service.impl;

import com.alibaba.excel.EasyExcel;
import com.jieyun.test.entity.dto.DkOrgDataParseDto;
import com.jieyun.test.mapper.FileParseExcelMapper;
import com.jieyun.test.service.FileParseExcelService;
import com.jieyun.test.utils.BaseResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author huike
 */
@Service
@Slf4j
public class FileParseExcelServiceImpl implements FileParseExcelService {

    public final static String EXCEL_LOAD = "excel";


    public final static String UPLOAD_PATH_PREFIX = "uploadFile/";

    private final FileParseExcelMapper fileParseExcelMapper;

    public FileParseExcelServiceImpl(FileParseExcelMapper fileParseExcelMapper) {
        this.fileParseExcelMapper = fileParseExcelMapper;
    }


    /**
     * 解析单孔Excel文件并入库
     *
     * @param files excel文件
     */
    @Override
    public BaseResult<String> parseExcelFile(MultipartFile files, HttpServletRequest request) throws IOException {
        if (files.isEmpty()) {
            return BaseResult.create(HttpStatus.SC_INTERNAL_SERVER_ERROR, "请选择需要上传的文件");
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd/");
        //构建文件上传所要保存的"文件夹路径"--这里是相对路径，保存到项目根路径的文件夹下
        String realPath = new String("src/main/resources/" + UPLOAD_PATH_PREFIX);
        log.info("-----------上传文件保存的路径【" + realPath + "】-----------");
        String format = sdf.format(new Date());
        //存放上传文件的文件夹
        File file = new File(realPath + format);
        log.info("-----------存放上传文件的文件夹【" + file + "】-----------");
        log.info("-----------输出文件夹绝对路径 -- 这里的绝对路径是相当于当前项目的路径而不是“容器”路径【" + file.getAbsolutePath() + "】-----------");
        if (!file.isDirectory()) {
            //递归生成文件夹
            file.mkdirs();
        }
        //获取原始的名字  original:最初的，起始的  方法是得到原来的文件名在客户机的文件系统名称
        String oldName = files.getOriginalFilename();
        log.info("-----------文件原始的名字【" + oldName + "】-----------");
        try {
            //构建真实的文件路径
            File newFile = new File(file.getAbsolutePath() + File.separator + oldName);
            //转存文件到指定路径，如果文件名重复的话，将会覆盖掉之前的文件,这里是把文件上传到 “绝对路径”
            files.transferTo(newFile);
            String filePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/uploadFile/" + format + oldName;
            log.info("-----------【" + filePath + "】-----------");
            String fileAbsolutePath = file.getAbsolutePath() + "/" + oldName;
            log.info("-------------上传文件的绝对路径：【" + fileAbsolutePath + "】------------");

            // 转置
            // 将文件地址作为参数，调用python程序进行转置
            // todo python文件需要换成相对路径
            String[] params = new String[]{"python3", "/Users/huike/IdeaProjects/shendi/ebigdata/src/main/java/com/ebigdata/fps/modules/ebigdata/python/JavaRunPython.py",
                    fileAbsolutePath, fileAbsolutePath};
            Runtime.getRuntime().exec(params);

            File transposeFile = new File(fileAbsolutePath);
            InputStream inputStream = new FileInputStream(transposeFile);
            List<DkOrgDataParseDto> dkOrgDataParseDtos = EasyExcel.read(inputStream).head(DkOrgDataParseDto.class).sheet().headRowNumber(1).doReadSync();

            System.out.println(dkOrgDataParseDtos);

            // 根据条件入库 获取到projectId之后，可以直接调用上面的批量插入方法

        } catch (Exception e) {
            log.error("文件上传失败");
            return BaseResult.create(HttpStatus.SC_INTERNAL_SERVER_ERROR, "文件上传失败");
        }

        return BaseResult.create(HttpStatus.SC_OK, "excel文件解析成功");
    }
}
