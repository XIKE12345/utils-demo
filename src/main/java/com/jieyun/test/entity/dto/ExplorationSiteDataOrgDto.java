package com.jieyun.test.entity.dto;

import lombok.Data;

/**
 * 勘探点数据文件传输对象
 *
 * @author huike
 * @data 2020/06/29
 */
@Data
public class ExplorationSiteDataOrgDto {

    /**
     * id
     */
    private String id;
    /**
     * 孔号
     */
    private String holeNum;
    /**
     * 钻孔主键
     */
    private String drillId;
    /**
     * 起始深度（米）
     */
    private String startDepth;
    /**
     * 杆长（米）
     */
    private String poleLength;
    /**
     * 实测击数
     */
    private String factHit;

    /**
     * 勘探点类型 (1-标贯；2-N63.5；3-N120；4-N10)
     */
    private int expPointType;

}
