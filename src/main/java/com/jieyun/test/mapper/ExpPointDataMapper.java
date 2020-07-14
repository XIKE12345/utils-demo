package com.jieyun.test.mapper;

import com.jieyun.test.entity.TExpPointData;
import com.jieyun.test.entity.dto.ExplorationSiteDataOrgDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author huike
 */
@Mapper
public interface ExpPointDataMapper {

    /**
     * 插入标贯数据
     *
     * @param expPointDataLists
     * @return
     */
    int batchInsertBgDataOrg(@Param("list") List<TExpPointData> expPointDataLists);
}
