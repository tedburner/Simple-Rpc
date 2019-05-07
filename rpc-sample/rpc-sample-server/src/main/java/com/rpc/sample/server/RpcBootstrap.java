package com.rpc.sample.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author: lingjun.jlj
 * @date: 2019/5/5 14:07
 * @description:
 */
public class RpcBootstrap {

    private static final Logger logger = LoggerFactory.getLogger(RpcBootstrap.class);

    public static void main(String[] args) {
        logger.debug("start server");
        System.out.println("start server");
        new ClassPathXmlApplicationContext("spring.xml");
    }
}
