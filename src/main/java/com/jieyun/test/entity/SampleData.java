package com.jieyun.test.entity;

import lombok.Data;

/**
 * @author huike
 * @desc 采样数据实体类
 * @date 2020/06/28
 */
@Data
public class SampleData {

    private String id;

    /**
     * 外键，是单孔数据表的主键
     */
    private String drillId;

    /**
     * 起始深度
     */
    private double startDepth;
    /**
     * 终止深度
     */
    private double endDepth;
    /**
     * 取样类型
     */
    private int sampleType;
    /**
     * 取样长度
     */
    private double sampleLength;
    /**
     * 试验编号
     */
    private String testNum;
    /**
     * 备注
     */
    private String remarks;
    /**
     * 删除标志 默认0
     */
    private String delFlag;
}
