package com.simple.registry.zookeeper.registry;

import com.simple.rpc.registry.ServiceRegistry;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: lingjun.jlj
 * @date: 2019/4/30 15:24
 * @description: 基于ZooKeeper的服务注册接口实现
 */
@Slf4j
public class ZooKeeperServiceRegistry implements ServiceRegistry {

    @Override
    public void register(String serviceName, String serviceAddress) {

    }
}
