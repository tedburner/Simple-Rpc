package com.rpc.sample.server.service.impl;

import com.rpc.sample.api.domain.UserDO;
import com.rpc.sample.api.service.HelloService;
import com.simple.rpc.server.annotation.Reference;

/**
 * @author: lingjun.jlj
 * @date: 2019/5/5 14:07
 * @description:
 */
@Reference(HelloService.class)
public class HelloServiceImpl implements HelloService {

    @Override
    public String hello(String name) {
        return "Hello " + name;
    }

    @Override
    public String hello(UserDO user) {
        return user.toString();
    }
}
