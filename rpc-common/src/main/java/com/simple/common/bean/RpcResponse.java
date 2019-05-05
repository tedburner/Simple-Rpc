package com.simple.common.bean;

import lombok.Data;

/**
 * @author: lingjun.jlj
 * @date: 2019/5/5 09:20
 * @description: RPC 响应封装类
 */
@Data
public class RpcResponse {

    private String requestId;
    private Exception exception;
    private Object result;

    public boolean hasException() {
        return exception != null;
    }

}
