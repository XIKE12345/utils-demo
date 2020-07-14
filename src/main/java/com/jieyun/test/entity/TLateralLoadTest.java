/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/ebigdata">ebigdata</a> All rights reserved.
 */
package com.jieyun.test.entity;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * 旁压试验点管理Entity
 *
 * @author wuhao
 * @version 2020-03-07
 */
@Data
public class TLateralLoadTest {

    private static final long serialVersionUID = 1L;
    /**
     * 外键，是单孔数据表的主键
     */
    private String drillId;
    /**
     * 孔号
     */
    private String holeNum;
    /**
     * 试验中点深度h(m)
     */
    private Float testDepth;
    /**
     * fu(kPa)
     */
    private Float testFu;
    /**
     * fak(kPa)
     */
    private Float testFak;
    /**
     * EO(kPa)
     */
    private Float testEo;
    /**
     * Es(kPa)
     */
    private Float testEs;
    /**
     * 删除按钮
     */
    private String delete = "";
}