package com.jieyun.test.entity.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * 单孔数据实体类
 *
 * @author huike
 */
@Data
public class DkOrgDataParseDto implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 孔id
     */
    private String id;

    /**
     * 工程id
     */
    private String projectId;


    /**
     * 孔号
     */
    private String holeNum;

    /**
     * 孔口标高
     */
    private String holeHigh;


    /**
     * 孔深
     */
    private String holeDeep;

    /**
     * 初见水位
     */
    private String startWaterLine;

    /**
     * 稳定水位
     */
    private String stableWaterLine;

    /**
     * 外业日期
     */
    private String  outDate;

    /**
     * 观测日期
     */
    private String viewDate;

    /**
     * x坐标
     */
    private String coordinateX;

    /**
     * Y坐标
     */
    private String coordinateY;

    /**
     * 探井深度
     */
    private String wellDeep;

    /**
     * 勘探点类型代号
     */
    private String exploreSiteTypeCode;

    /**
     * 里程
     */
    private String mileage;


}
