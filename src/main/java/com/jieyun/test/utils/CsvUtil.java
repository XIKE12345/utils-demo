package com.jieyun.test.utils;

import com.jieyun.test.entity.dto.DkOrgDataParseDto;
import com.opencsv.CSVReader;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import org.apache.maven.internal.commons.io.input.BOMInputStream;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author huike
 */
public class CsvUtil {
    /**
     * 解析csv文件并转成bean,需要表头绑定
     *
     * @param file  csv文件
     * @param clazz 类
     * @param <T>   泛型
     * @return 泛型bean集合
     */
    public <T> List<T> getCsvData(BOMInputStream file, Class<T> clazz, String charsetName) {
        InputStreamReader in = null;
        try {
            in = new InputStreamReader(file, charsetName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        HeaderColumnNameMappingStrategy<T> strategy = new HeaderColumnNameMappingStrategy<>();
        strategy.setType(clazz);
        CsvToBean<T> csvToBean = new CsvToBeanBuilder<T>(in)
                .withSeparator(',')
                .withQuoteChar('\'')
                .withMappingStrategy(strategy).build();
        return csvToBean.parse();
    }

    public <T> List<T> readFileToBean(String fileName , Class<T> clazz , String[] columns ) {
        InputStreamReader fileReader = null;
        CSVReader csvReader = null;
        ColumnPositionMappingStrategy start = new ColumnPositionMappingStrategy();
        CsvToBean csv = new CsvToBean();
        // 此处可以写成任意对象
        List<T> listObject = new ArrayList<>(16);
        try {
            // 创建文件读写器
            // fileReader = new FileReader(file);
            fileReader = new InputStreamReader(new FileInputStream(fileName), "GBK");
            // 创建CSV读写对象
            csvReader = new CSVReader(fileReader);
            start.setType(clazz);
            // 此处的string[] 代表的是对应的实体类的字段,(按照csv文件的顺序对应字段,感觉如果顺序一样可以用反射来做)
            start.setColumnMapping(columns);
            // 根据映射 读取存入 list
            listObject = csv.parse(start, csvReader);
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                csvReader.close();
                fileReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return listObject;
    }

    public static void main(String[] args) {
        List<Object> objects = readFile("/Users/huike/IdeaProjects/test/src/main/resources/unzip/可一书店/DK.csv");
        System.out.println(objects.toArray().toString());
    }

    public static List<Object> readFile(String fileName) {
        InputStreamReader fileReader = null;
        CSVReader csvReader = null;
        ColumnPositionMappingStrategy strat = new ColumnPositionMappingStrategy();
        CsvToBean csv = new CsvToBean();
        String[] columns = null; // 用于存放映射字段
        List<Object> listobject = new ArrayList<Object>(); // 此处可以写成任意对象
        try {
            // fileReader = new FileReader(file); // 创建文件读写器
            fileReader = new InputStreamReader(new FileInputStream(fileName), "UTF-8");

            csvReader = new CSVReader(fileReader); // 创建CSV读写对象
            strat.setType(DkOrgDataParseDto.class);
            // 此处的string[] 代表的是对应的实体类的字段,(按照csv文件的顺序对应字段,感觉如果顺序一样可以用反射来做)
            columns = new String[]{"holeNum", "holeHigh", "holeDeep", "startWaterLine", "stableWaterLine",
                    "outDate", "viewDate", "coordinateX", "coordinateY", "wellDeep", "exploreSiteTypeCode"};
            strat.setColumnMapping(columns); // 映射字段放进处理的类
            listobject = csv.parse(strat, csvReader); // 根据映射 读取存入 list
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                csvReader.close();
                fileReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return listobject;
    }
}
