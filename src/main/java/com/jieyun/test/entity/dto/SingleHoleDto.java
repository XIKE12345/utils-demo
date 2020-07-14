package com.jieyun.test.entity.dto;

import lombok.Data;

/**
 * @author huike
 * @desc 单孔信息传输对象
 * @date 2020/06/28
 */
@Data
public class SingleHoleDto {

    /**
     * 工程代号
     */
    private String localCode;
    /**
     * 孔号
     */
    private String holeNum;

    /**
     * 工程id
     */
    private String projectId;
}
