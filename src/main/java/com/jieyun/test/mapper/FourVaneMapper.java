package com.jieyun.test.mapper;

import com.jieyun.test.entity.TFourVane;
import com.jieyun.test.entity.dto.CrossPlateDataOrgDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author huike
 */
@Mapper
public interface FourVaneMapper {


    /**
     * 插入十字板原始数据
     *
     * @param fourVaneLists
     * @return
     */
    int batchInsertCrossPlateOrgData(@Param("list") List<TFourVane> fourVaneLists);
}
