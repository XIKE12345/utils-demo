package com.jieyun.test.entity.dto;

import com.alibaba.excel.annotation.ExcelProperty;
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
    @ExcelProperty(index = 0)
    private String holeNum;

    /**
     * 孔口标高
     */
    @ExcelProperty(index = 1)
    private String holeHigh;


    /**
     * 孔深
     */
    @ExcelProperty(index = 2)
    private String holeDeep;

    /**
     * 初见水位
     */
    @ExcelProperty(index = 3)
    private String startWaterLine;

    /**
     * 稳定水位
     */
    @ExcelProperty(index = 4)
    private String stableWaterLine;

    /**
     * 外业日期
     */
    @ExcelProperty(index = 5)
    private String  outDate;

    /**
     * 观测日期
     */
    @ExcelProperty(index = 6)
    private String viewDate;

    /**
     * x坐标
     */
    @ExcelProperty(index = 7)
    private String coordinateX;

    /**
     * Y坐标
     */
    @ExcelProperty(index = 8)
    private String coordinateY;

    /**
     * 探井深度
     */
    @ExcelProperty(index = 9)
    private String wellDeep;

    /**
     * 勘探点类型代号
     */
    @ExcelProperty(index = 10)
    private String exploreSiteTypeCode;

    /**
     * 里程
     */
    @ExcelProperty(index = 11)
    private String mileage;


}
