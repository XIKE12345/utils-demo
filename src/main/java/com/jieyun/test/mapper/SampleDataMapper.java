package com.jieyun.test.mapper;

import com.jieyun.test.entity.SampleData;
import com.jieyun.test.entity.dto.SamplingDataOrgDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author huike
 */
@Mapper
public interface SampleDataMapper {

    /**
     * 插入采样数据
     *
     * @param sampleDataLists 采样数据
     * @return int
     */
    int batchInsertIntoSampleData(@Param("list") List<SampleData> sampleDataLists);
}
