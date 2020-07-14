package com.jieyun.test.csvtest;

import lombok.Data;

import java.io.Serializable;
/**
 * @author huike
 */
@Data
public class WftTestEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 交易时间
     */
    private String test1;


    /**
     * 公众账号ID
     */
    private String test2;

}
