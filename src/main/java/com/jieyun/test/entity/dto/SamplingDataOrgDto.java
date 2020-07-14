package com.jieyun.test.entity.dto;

import lombok.Data;

/**
 * 取样原始数据
 *
 * @author huike
 * @date 2020/06/28
 */
@Data
public class SamplingDataOrgDto {

    /**
     * id
     */
    private String id;
    /**
     * 孔id
     */
    private String holeNum;
    private String drillId;
    /**
     * 起始深度
     */
    private String startDepth;
    /**
     * 取样类型
     */
    private String sampleType;
    /**
     * 取样长度
     */
    private String sampleLength;
    /**
     * 实验编号
     */
    private String testNum;

}
