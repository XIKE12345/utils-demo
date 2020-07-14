package com.jieyun.test.entity.dto;

import lombok.Data;

/**
 * 新版场地综合原始数据文件DTO
 *
 * @author huike
 */
@Data
public class AreaMultipleDataNewOrgDto {
    /**
     * 主键Id
     */
    private String id;
    /**
     * 工程主键
     */
    private String projectId;
    /**
     * 序列号
     */
    private String serialNum;
    /**
     * 层号
     */
    private String levelNum;
    /**
     * 岩土代码
     */
    private String rockCode;
    /**
     * 岩土名称
     */
    private String rockName;
    /**
     * 地质成因
     */
    private String geologyReason;
    /**
     * 地质时代
     */
    private String geologyAge;
    /**
     * 承载力
     */
    private String bearingCapacity;
    /**
     * 压缩模量
     */
    private String modulusCom;
    /**
     * 重度
     */
    private String weight;
    /**
     * 地层类别
     */
    private String stratumType;
    /**
     * 预制桩极限侧阻力标准值
     */
    private String precastLimitNewel;
    /**
     * 预制桩极限端承力标准值
     */
    private String precastLimitBearing;
    /**
     * 灌注桩等极限侧阻力标准值
     */
    private String castInPlaceLimitNewel;
    /**
     * 灌注桩等极限端承力标准值
     */
    private String castInPlaceLimitBearing;
    /**
     * 土 类别（空为粘性土几粉土）
     */
    private String soilType;
    /**
     * 抗拔桩抗拔系数
     */
    private String upliftPile_λ;
    /**
     * 后注浆端册阻力系数β si0
     */
    private String backGrout_βsi0;
    /**
     * 后注浆端册阻力系数βp
     */
    private String backGrout_βp;
    /**
     * 颜色
     */
    private String colour;
    /**
     * 湿度
     */
    private String humidity;
    /**
     * 状态密度
     */
    private String stateDensity;
    /**
     * 压缩性
     */
    private String compressibility;
    /**
     * 摇震反应
     */
    private String shakingResp;
    /**
     * 光泽反应
     */
    private String glossResp;
    /**
     * 干强度
     */
    private String dryStrength;
    /**
     * 韧性
     */
    private String tenacity;
    /**
     * 夹杂物
     */
    private String inclusions;
    /**
     * 岩石坚硬程度
     */
    private String rockStiff;
    /**
     * 岩石完整程度
     */
    private String rockFull;
    /**
     * 岩石结构类型
     */
    private String rockFrameType;
    /**
     * 其他岩石描述
     */
    private String otherRockDesc;

}
