package com.jieyun.test.entity.dto;

import lombok.Data;

/**
 * 旁压数据实体
 *
 * @author huike
 */
@Data
public class SidePressureDto {
    /**
     * id
     */
    private String id;
    /**
     * 孔号
     */
    private String holeNum;
    /**
     * 孔主键Id
     */
    private String drillId;
    /**
     * 测试中点深度
     */
    private String testMidpointDepth;

}
