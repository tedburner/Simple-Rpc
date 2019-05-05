package com.rpc.sample.server.service.impl;

import com.rpc.sample.api.domain.UserDO;
import com.rpc.sample.api.service.HelloService;
import com.simple.rpc.server.annotation.Reference;

@Reference(value = HelloService.class, version = "sample.hello2")
public class HelloServiceImpl2 implements HelloService {

    @Override
    public String hello(String name) {
        return "你好! " + name;
    }

    @Override
    public String hello(UserDO person) {
        return "你好! " + person.getName();
    }
}
