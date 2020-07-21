package com.jieyun.test.csvtest;

import com.jieyun.test.entity.dto.DkOrgDataParseDto;
import com.opencsv.CSVReader;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author huike
 * CSV文件解析
 */
public class CsvTest {
    public static void main(String[] args) throws IOException {
        String url = "/Users/huike/Desktop/DK.csv";
        List<Object> objects = readFile(url);
        System.out.println(objects);

    }

    public static <T> List<T> readFile(String fileName) {
        InputStreamReader fileReader = null;
        CSVReader csvReader = null;
        ColumnPositionMappingStrategy start = new ColumnPositionMappingStrategy();
        CsvToBean csv = new CsvToBean();
        // 用于存放映射字段
        String[] columns = null;
        // 此处可以写成任意对象
        List<T> listObject = new ArrayList<>(16);
        try {
            // 创建文件读写器
            // fileReader = new FileReader(file);
            fileReader = new InputStreamReader(new FileInputStream(fileName), "UTF-8");
            // 创建CSV读写对象
            csvReader = new CSVReader(fileReader);

            start.setType(DkOrgDataParseDto.class);
            // 此处的string[] 代表的是对应的实体类的字段,(按照csv文件的顺序对应字段,感觉如果顺序一样可以用反射来做)
//            columns = new String[] { "test1", "test2" };
            columns = new String[]{"holeNum", "holeHigh", "holeDeep", "startWaterLine", "stableWaterLine", "outDate",
                    "viewDate", "coordinateX", "coordinateY", "wellDeep", "exploreSiteTypeCode"};

            // 映射字段放进处理的类
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
}
