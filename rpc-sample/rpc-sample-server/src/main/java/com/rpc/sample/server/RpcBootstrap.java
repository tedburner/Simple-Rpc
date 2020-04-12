package com.rpc.sample.server;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author: lingjun.jlj
 * @date: 2019/5/5 14:07
 * @description: RPC DEMO 服务端
 */
public class RpcBootstrap {

    public static void main(String[] args) {
        System.out.println("start server");
        new ClassPathXmlApplicationContext("spring.xml");
    }
}
