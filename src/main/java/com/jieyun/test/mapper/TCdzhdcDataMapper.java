package com.jieyun.test.mapper;

import com.jieyun.test.entity.TCdzhdcData;
import com.jieyun.test.entity.dto.AreaMultipleDataNewOrgDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author huike
 */
@Mapper
public interface TCdzhdcDataMapper {


    /**
     * 批量添加场地综合数据
     *
     * @param cdzhdcDataLists
     * @return
     */
    int batchInsertAreaMultipleData(@Param("list") List<TCdzhdcData> cdzhdcDataLists);
}
