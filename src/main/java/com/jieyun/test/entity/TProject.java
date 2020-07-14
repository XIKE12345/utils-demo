package com.jieyun.test.entity;


import lombok.Data;

import java.util.List;

@Data
public class TProject  {

    private static final long serialVersionUID = 1L;
    /**
     * 主键id
     */
    private String id;
    /**
     * 企业id
     */
    private String officeId;
    /**
     * 本地工程代号
     */
    private String localCode;
    /**
     * 工程编号
     */
    private String projectNum;
    /**
     * 用户ID
     */
    private String userId;
    /**
     * 采用规范
     */
    private String codeRequ;
    /**
     * 勘察阶段
     */
    private String inveStage;
    /**
     * 坐标系统
     */
    private String coorSys;
    /**
     * 图号
     */
    private String figureNum;
    /**
     * 工程名称
     */
    private String enginnerName;
    /**
     * 项目名称
     */
    private String projectName;
    /**
     * 建设单位
     */
    private String consUnit;
    /**
     * 工程地点
     */
    private String projectSite;
    /**
     * 设计单位
     */
    private String designUnit;
    /**
     * 勘察单位
     */
    private String inveUnti;
    /**
     * 外业开始时间
     */
    private String oworkStartData;
    /**
     * 外业结束时间
     */
    private String oworkEndData;
    /**
     * 内业开始时间
     */
    private String iworkStartData;
    /**
     * 内业结束时间
     */
    private String iworkEndData;
    /**
     * 项目负责人
     */
    private String projectLeader;
    /**
     * 上传时间
     */
    private String uploadTime;
    /**
     * 上传位置
     */
    private String uploadLocation;
    /**
     * 上传ip
     */
    private String uploadIp;
    /**
     * 校核人
     */
    private String proofread;
    /**
     * 审核人
     */
    private String audit;
    /**
     * 审定人
     */
    private String judgement;
    /**
     * 批准人
     */
    private String ratify;
    /**
     * 建立IP
     */
    private String buildIp;
    /**
     * 经度
     */
    private Float longitude;
    /**
     * 纬度
     */
    private Float latitude;

    /**
     * 场地综合集合
     */
    private List<TCdzhdcData> tCdzhdcDatas;
    /**
     * 单孔数据集合
     */
    private List<TSingleHole> singleHoles;

}
