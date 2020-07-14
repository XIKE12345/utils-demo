/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/ebigdata">ebigdata</a> All rights reserved.
 */
package com.jieyun.test.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * 地层数据Entity
 *
 * @author sunqihuan
 * @version 2020-03-05
 */
@Data
public class TStratum {

    private static final long serialVersionUID = 1L;
    /**
     * 临时字段,工程id
     */
    @JsonIgnore
    private String projectId;

    /**
     * 外键，是单孔数据表的主键
     */
    private String drillId;

    /**
     * 孔号
     */
    @Length(max = 11, message = "序号长度必须介于 0 和 11 之间")
    private String holeNum;

    /**
     * 层号
     */
    @JsonIgnore
    private String lelevNum;

    /**
     * 用来存放特殊层号数据(类似1-2)
     */
    private String lelevNum1;

    /**
     * 层底深度(m)
     */
    private Float rockBottomDepth;

    /**
     * 岩土代码
     */
    private Float rockCode;

    /**
     * 基岩倾角度
     */
    private Float rockDipAngle;

    /**
     * 进入按钮
     */
    private String into = "";

    /**
     * 删除按钮
     */
    private String delete = "";

}