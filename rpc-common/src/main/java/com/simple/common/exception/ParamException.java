package com.simple.common.exception;

/**
 * @author: lingjun.jlj
 * @date: 2019/4/30 15:33
 * @description: 参数异常
 */
public class ParamException extends RuntimeException {

    public ParamException() {
        super();
    }

    public ParamException(String message) {
        super(message);
    }

    public ParamException(String message, Throwable cause) {
        super(message, cause);
    }
}
