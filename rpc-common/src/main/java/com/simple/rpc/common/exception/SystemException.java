package com.simple.rpc.common.exception;

/**
 * @author: lingjun.jlj
 * @date: 2019/4/30 15:33
 * @description: 系统异常
 */
public class SystemException extends RuntimeException {

    public SystemException() {
        super();
    }

    public SystemException(String message) {
        super(message);
    }

    public SystemException(String message, Throwable cause) {
        super(message, cause);
    }
}
