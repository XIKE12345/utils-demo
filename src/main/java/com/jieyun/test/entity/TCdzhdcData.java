/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/ebigdata">ebigdata</a> All rights reserved.
 */
package com.jieyun.test.entity;


import lombok.Data;

/**
 * 场地综合管理Entity
 * @author fengyang
 * @version 2020-01-17
 */
@Data
public class TCdzhdcData {
	
	private static final long serialVersionUID = 1L;

	private  String id;

	private String projectId;		// 外键(工程主键ID）
	private Integer sequNum;		// 顺序号
	private Double levelNum;		// 层号
	private String lelevNum1;       //用来存放特殊层号数据(类似1-2)
	private String grouCode;		// 岩土代码
	private String grouName;		// 岩土名称
	private String geoOrigin;		// 地质成因
	private String geoAge;		// 地质时代，枚举类型
	private Float bearCapacity;		// 承载力(KPa)
	private Float compModel;		// 压缩模量
    private Float unitWeight;		// 重度&gamma;(KN/㎡)
    private String cadgrouCode;		// 剖面图全图例数据CAD岩土代码
    private String cadForecolor;		// 岩土图例CAD前景色
    private String cadBackColor;		// 岩土图例CAD背景色
	private Float stratumType;		// 地层类别
	private Float firstQsk;		// 第一种方案预制桩极限侧阻力标准值qsk(KPa)
	private Float fristQpk;		// 第一种方案预制桩极限端承力标准值qpk(KPa)
	private Float secondQsk;		// 第二种方案灌注桩等极限侧阻力标准值qsk
	private Float secondQpk;		// 第二种方案灌注桩等
	private Integer soilType;		// 土的类别
	private Float tcop;		// 抗拔桩抗拔系数&lambda;
	private Float dcorgs;		// 后注浆侧阻力系数
	private Float dcorge;		// 后注浆端阻力系数&beta;p
    private Integer color;		// 颜色
    private Integer humidity;		// 湿度
    private Integer stateDensity;		// 状态密度
    private Integer coercibility;		// 压缩性
    private Integer dilatance;		// 摇震反应
    private Integer glossReaction;		// 光泽反应
    private Integer dryStrength;		// 干强度
    private Integer tenacity;		// 韧性
    private Integer inclusion;		// 夹杂物
    private Integer rockHardness;		// 岩石坚硬程度
    private Integer rockIntegrity;		// 岩体完整程度
    private Integer rockStrcType;		// 岩体结构类型
    private String otherRockDesc;		// 其他岩土描述

	private String delete = ""; //删除按钮


}