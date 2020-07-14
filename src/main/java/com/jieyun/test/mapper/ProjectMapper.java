package com.jieyun.test.mapper;


import org.apache.ibatis.annotations.Mapper;

import java.util.Set;

/**
 * @author huike
 * @desc 工程相关的mapper
 */
@Mapper
public interface ProjectMapper {

    /**
     * 获取工程的代号
     *
     * @return Set
     */
    Set<String> getLocalCodeLists();

}
