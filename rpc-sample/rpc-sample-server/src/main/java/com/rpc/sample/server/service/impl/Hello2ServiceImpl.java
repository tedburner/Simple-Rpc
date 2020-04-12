package com.rpc.sample.server.service.impl;

import com.rpc.sample.api.domain.UserDO;
import com.rpc.sample.api.service.HelloService;
import com.simple.rpc.server.annotation.Reference;

/**
 * @author: lingjun.jlj
 * @date: 2019/5/5 14:07
 * @description:
 */
@Reference(value = HelloService.class, version = "sample.hello2")
public class Hello2ServiceImpl implements HelloService {

    @Override
    public String hello(String name) {
        return "你好! " + name;
    }

    @Override
    public String hello(UserDO person) {
        return "你好! " + person.getName();
    }
}
