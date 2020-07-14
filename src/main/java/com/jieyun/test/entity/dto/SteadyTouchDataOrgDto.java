package com.jieyun.test.entity.dto;

import lombok.Data;

/**
 * 静力触探数据传输对象
 *
 * @author huike
 * @Date 2020/06/29
 */
@Data
public class SteadyTouchDataOrgDto {

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
     * 深度
     */
    private String depth;

    /**
     * 微应变εp
     */
    private String microStrainP;
    /**
     * 微应变εf
     */
    private String microStrainF;
    /**
     * 微应变εu
     */
    private String microStrainU;

    /**
     * 触探数据类型（1-单桥；2-双桥；3-孔压）
     */
    private int dataType;


}
