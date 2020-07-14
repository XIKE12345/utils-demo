package com.jieyun.test.mapper;

import com.jieyun.test.entity.TFeelerInspection;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author huike
 */
@Mapper
public interface FeelerInspectionMapper {

    /**
     * 插入单桥触探原始数据
     *
     * @param tFeelerInspections
     * @return
     */
    int batchInsertTouchOrgData(@Param("list") List<TFeelerInspection> tFeelerInspections);
}
