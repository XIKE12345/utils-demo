package com.jieyun.test.demo;

import com.jieyun.test.entity.dto.DkOrgDataParseDto;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * @author huike
 */
public class Csv {
    public static <T> List<T> getCsvData(String fileName, Class<T> clazz) throws IOException {
        InputStreamReader is = new InputStreamReader(new FileInputStream(fileName),"gbk");

        HeaderColumnNameMappingStrategy<T> strategy = new HeaderColumnNameMappingStrategy<>();
        strategy.setType(clazz);

        CsvToBean<T> csvToBean = new CsvToBeanBuilder<T>(is)
                .withSeparator(',')
                .withQuoteChar('\'')
                .withMappingStrategy(strategy).build();
        return csvToBean.parse();
    }

    public static void main(String[] args) throws IOException {


        List<DkOrgDataParseDto> wftTestEntityList = getCsvData("/Users/huike/IdeaProjects/test/src/main/resources/unzip/可一书店/BGT2-1.csv",DkOrgDataParseDto.class);
        System.out.println(wftTestEntityList);
    }

}
