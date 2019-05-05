package com.simple.common.bean;

import lombok.Data;

/**
 * @author: lingjun.jlj
 * @date: 2019/5/5 09:20
 * @description: RPC 请求封装类
 */
@Data
public class RpcRequest {

    private String requestId;
    private String interfaceName;
    private String serviceVersion;
    private String methodName;
    private Class<?>[] parameterTypes;
    private Object[] parameters;
}
