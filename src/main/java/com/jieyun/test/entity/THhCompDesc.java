/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/ebigdata">ebigdata</a> All rights reserved.
 */
package com.jieyun.test.entity;

import lombok.Data;

/**
 * 探孔地层描述数据管理Entity
 *
 * @author wuhao
 * @version 2020-03-09
 */
@Data
public class THhCompDesc {

    private static final long serialVersionUID = 1L;
    private String stratumId;        // 外键，地层数据表主键
    private String holeNum;        // 孔号
    private String lelevNum;        // 层号
    private String stateDensity;        // 状态密度
    private Float limitFricPile;        // 桩周极限摩阻力(KPa)
    private Float bearCapacity;        // 承载力(kPa)
    private String rockDesc;        // 岩土描述
    private String delete = "";     //删除按钮

}