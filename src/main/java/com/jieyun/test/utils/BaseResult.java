package com.jieyun.test.utils;

import com.google.common.collect.Maps;
import lombok.Data;

import java.io.Serializable;

/**
 * 通用的执行结果
 *
 * @author huike
 * @date 2018-11-14
 */
@Data
public class BaseResult<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 操作结果状态码
     */
    private Integer code;

    /**
     * 操作结果通知消息
     */
    private String message;

    /**
     * 操作结果展示数据
     */
    private T data;

    @SuppressWarnings("unchecked")
    public T getData() {
        return data == null ? (T) Maps.newHashMapWithExpectedSize(1) : data;
    }

    @SuppressWarnings("unchecked")
    public void setData(T data) {
        this.data = data == null ? (T) Maps.newHashMapWithExpectedSize(1) : data;
    }

    /**
     * @return BaseResult<T>
     */
    private static <T> BaseResult<T> create() {
        return new BaseResult<>();
    }

    /**
     * 接口调用成功时的无data场景
     *
     * @param code    Integer
     * @param message String
     * @return BaseResult<T>
     */
    public static <T> BaseResult<T> create(Integer code, String message) {
        BaseResult<T> baseResult = BaseResult.create();
        baseResult.setCode(code);
        baseResult.setMessage(message);

        return baseResult;
    }

    /**
     * 接口调用成功时也需要code和message的场景
     *
     * @param code    Integer
     * @param message String
     * @param data    T
     * @return BaseResult<T>
     */
    public static <T> BaseResult<T> create(Integer code, String message, T data) {
        BaseResult<T> baseResult = BaseResult.create();
        baseResult.setCode(code);
        baseResult.setMessage(message);
        baseResult.setData(data);

        return baseResult;
    }

}

