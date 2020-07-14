package com.jieyun.test.entity.dto;

import lombok.Data;

/**
 * @author huike
 */
@Data
public class StratumDataOrgDto {

    /**
     * id
     */
    private String id;
    /**
     * 孔号
     */
    private String holeNum;
    /**
     * 孔号Id
     */
    private String drillId;
    /**
     * 层号
     */
    private String levelNum;
    /**
     * 层底深度
     */
    private String levelBottomDepth;
    /**
     * 岩土代码
     */
    private String stratumCode;
    /**
     * 基岩倾角度
     */
    private String stratumSlope;

    /**
     * 备用
     */
    private String backup;
}
