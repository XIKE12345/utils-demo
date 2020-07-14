/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/ebigdata">ebigdata</a> All rights reserved.
 */
package com.jieyun.test.entity;

import lombok.Data;

/**
 * 触探数据管理Entity
 *
 * @author liying
 * @version 2020-03-09
 */
@Data
public class TFeelerInspection{

    private static final long serialVersionUID = 1L;

    private String id;
    /**
     * 外键，是单孔数据表的主键
     */
    private String drillId;
    /**
     * 深度
     */
    private Float depth;
    /**
     * 微应变&epsilon;q
     */
    private Float microStrainQ;
    /**
     * 微应变&epsilon;f
     */
    private Float microStrainF;
    /**
     * 微应变&epsilon;u
     */
    private Float microStrainU;
    /**
     * 触探数据类型(1-单桥|2-双桥|3-孔压)
     */
    private Integer dataType;

    /**
     * 率定系数Kp（MPa/微应变εp）—单桥
     */
    private float ratioP;
    /**
     * 率定系数Kq（MPa/微应变εq）—双桥
     */
    private float ratioQ;
    /**
     * 率定系数Kf（MPa/微应变εf）—双桥
     */
    private float ratioF;

    /**
     * 修改按钮
     */
    private String update = "";
    /**
     * 删除按钮
     */
    private String delete = "";

}