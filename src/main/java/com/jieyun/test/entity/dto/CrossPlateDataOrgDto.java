package com.jieyun.test.entity.dto;

import lombok.Data;

/**
 * 十字板原始数据对象
 *
 * @author huike
 * @data 2020/06/29
 */
@Data
public class CrossPlateDataOrgDto {
    /**
     * id
     */
    private String id;
    /**
     * 孔号
     */
    private String holeNum;
    /**
     * 孔主键id
     */
    private String drillId;
    /**
     * 中点深度
     */
    private String midpointDepth;
    /**
     * 原状土
     */
    private String orgCu;
    /**
     * 重塑土
     */
    private String remodelCu;
}
