package com.jieyun.test.mapper;

import com.jieyun.test.entity.TStratum;
import com.jieyun.test.entity.dto.StratumDataOrgDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author huike
 */
@Mapper
public interface StratumMapper {

    /**
     * 插入地层数据
     *
     * @param tStratumLists
     * @return
     */
    int batchInsertStratumOrgData(@Param("list") List<TStratum> tStratumLists);
}
