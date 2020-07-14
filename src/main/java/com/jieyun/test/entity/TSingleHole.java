/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/ebigdata">ebigdata</a> All rights reserved.
 */
package com.jieyun.test.entity;


import lombok.Data;

import java.util.Date;

/**
 * 单孔数据管理Entity
 *
 * @author fengyang
 * @version 2020-01-17
 */
@Data
public class TSingleHole {

    private static final long serialVersionUID = 1L;
    private String id;
    /**
     * 外键(工程主键ID）
     */
    private String projectId;
    /**
     * 序号，用于排序用
     */
    private Integer sequNum;
    /**
     * 孔号
     */
    private String holeNum;
    /**
     * 孔口或井口标高(m)
     */
    private Double orifElev;
    /**
     * 勘探点深度(m)
     */
    private Float explDepth;
    /**
     * 初见水位深度(m)
     */
    private Float iwlDepth;
    /**
     * 稳定水位深度(m)
     */
    private Float flDepth;
    /**
     * 外业日期
     */
    private Date oworkData;
    /**
     * 观测日期
     */
    private Date obseData;
    /**
     * 坐标X(m)
     */
    private Double coorX;
    /**
     * 坐标Y(m)
     */
    private Double coorY;
    /**
     * 探井深度(m)
     */
    private Float explWellDepth;
    /**
     * 勘探点类型代号
     */
    private Integer epTypeCode;
    /**
     * 里程
     */
    private String mileage;
    /**
     * 进入按钮
     */
    private String into = "";
    /**
     * 删除按钮
     */
    private String delete = "";

}