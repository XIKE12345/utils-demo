package com.jieyun.test.mapper;

import com.jieyun.test.entity.TLateralLoadTest;
import com.jieyun.test.entity.dto.SidePressureDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author huike
 */
@Mapper
public interface TLateralLoadTestMapper {

    /**
     * 旁压数据批量插入
     *
     * @param TLateralLoadTests 旁压数据对象
     * @return int
     */

    int batchInsertSidePressureData(@Param("list") List<TLateralLoadTest> TLateralLoadTests);
}
