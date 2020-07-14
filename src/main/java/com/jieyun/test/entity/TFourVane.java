/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/ebigdata">ebigdata</a> All rights reserved.
 */
package com.jieyun.test.entity;

import lombok.Data;

/**
 * 十字板数据管理Entity
 * @author wuhao
 * @version 2020-03-07
 */
@Data
public class TFourVane {
	
	private static final long serialVersionUID = 1L;
	private String id;
	private String drillId;		// 外键，是单孔数据表的主键
	private String holeNum;		// 孔号
	private Float testDepth;		// 试验中点深度h(m)
	private Float testCu;		// cu(kPa)
	private Float testFcu;		// cu' (kPa)
	private Float testSt;		// st
	private String delete = "";     //删除按钮

}