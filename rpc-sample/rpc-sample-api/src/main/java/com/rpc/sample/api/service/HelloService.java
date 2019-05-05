package com.rpc.sample.api.service;

import com.rpc.sample.api.domain.UserDO;

/**
 * @author: lingjun.jlj
 * @date: 2019/5/5 14:08
 * @description:
 */
public interface HelloService {

    String hello(String name);

    String hello(UserDO user);
}
