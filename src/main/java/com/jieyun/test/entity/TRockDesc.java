/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/ebigdata">ebigdata</a> All rights reserved.
 */
package com.jieyun.test.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

/**
 * 岩石描述数据Entity
 * @author liying
 * @version 2020-03-09
 */
@Data
public class TRockDesc  {
	
	private static final long serialVersionUID = 1L;
	private String drillId;		// 外键，是单孔数据表的主键
	@JsonIgnore
	private Double lelevNum;		// 层号
	private String lelevNum1;       //用来存放特殊层号数据(类似1-2)
	private String stateDensity;		// 状态密度
	private Float limitFricPile;		// 桩周极限摩阻力(KPa)
	private Float bearCapacity;		// 承载力(kPa)
	private String rockDesc;		// 岩土名称:颜色、夹杂物、状态等
    private String update = "";       //修改按钮
    private String delete = "";     //删除按钮
	

}