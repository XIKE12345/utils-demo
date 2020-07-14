/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/ebigdata">ebigdata</a> All rights reserved.
 */
package com.jieyun.test.entity;

import lombok.Data;

/**
 * 勘探点管理Entity
 *
 * @author liying
 * @version 2020-03-05
 */
@Data
public class TExpPointData {

    private static final long serialVersionUID = 1L;

    private String id;
    /**
     * 外键，是单孔数据表的主键
     */
    private String drillId;
    /**
     * 孔号
     */
    private String holeNum;

    /**
     * 起始深度
     */
    private double sptOrigDepth;

    /**
     * 杆长
     */
    private double sptLength;

    /**
     * 实测击数
     */
    private double sptMeasNum;

    /**
     * 勘探点数据类型(1-标贯|2-N63.55散点数据|3-N120散点数据|4-N10数据|5-N63.5连续动探数据|6-N120连续动探数据)
     */
    private Integer expPointType;

    /**
     * 修改按钮
     */
    private String update = "";

    /**
     * 删除按钮
     */
    private String delete = "";

}