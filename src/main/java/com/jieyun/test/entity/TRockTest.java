/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/ebigdata">ebigdata</a> All rights reserved.
 */
package com.jieyun.test.entity;

/**
 * 岩石试验数据管理Entity
 *
 * @author wuhao
 * @version 2020-03-09
 */
public class TRockTest  {

    private static final long serialVersionUID = 1L;
    private String drillId;        // 外键，是单孔数据表的主键
    private Integer sequNum;        // 序号
    private String holeNum;        // 孔号
    private String rockSpecNum;        // 岩样编号
    private Float sampleDepth;        // 取样起始深度(m)
    private Float sampleLength;        // 取样长度(m)
    private Float natuDensity;        // 天然密度&rho;
    private Float satuDensity;        // 饱和密度&rho;w
    private Float dryDensity;        // 干燥密度&rho;d
    private Float mpaR;        // 天然单轴抗压强度R
    private Float mpaRw;        // 饱和单轴抗压强度Rw
    private Float mpaRd;        // 干燥单轴抗压强度Rd
    private Float softCoefficient;        // 软化系数
    private Float waterContent;        // 含水率w
    private Float grainDensity;        // 颗粒密度&rho;
    private Float waterAbso;        // 吸水率Wa
    private Float satuWaterAbso;        // 饱和吸水率Wsa
    private Float poriness;        // 孔隙率n
    private Float tensileStrength;        // 抗拉强度&sigma;t
    private Float dstCohesion;        // 直剪试验黏聚力C
    private Float dstAngle;        // 直剪试验内摩擦角&phi;
    private Float ttCohesion;        // 三轴试验黏聚力C
    private Float ttAngle;        // 三轴试验内摩擦角&phi;
    private Float ssCohesion;        // 抗剪断强度黏聚力C
    private Float ssAngle;        // 抗剪断强度内摩擦角&phi;
    private Float elasModulus;        // 弹性模量E
    private Float poissonRatio;        // 泊松比&mu;
    private Float plseAxial;        // 点载荷强度指数轴向
    private Float plseRadial;        // 点载荷强度指数径向
    private Float pwaveSpeed;        // 纵波波速Vp
    private Float twaveSpeed;        // 横波波速Vs
    private Float deModulus;        // 动弹性模量Ed
    private Float dsModulus;        // 动剪切模量Gd
    private Float dpRatio;        // 动泊松比&mu;
    private Float afeRale;        // 轴向自由膨胀率VH
    private Float rfeRale;        // 径向自由膨胀率VD
    private Float lceRale;        // 侧向约束膨胀率VHP
    private Float swelPressure;        // 膨胀压力Pe
    private Float drIndex;        // 耐崩解指数Id2
    private Float fucStrength;        // 冻融单轴抗压强度Rfm
    private String frCoefficient;        // 抗冻系数Kfm
    private String delete = "";     //删除按钮

}