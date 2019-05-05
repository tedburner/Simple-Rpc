package com.simple.rpc.registry.zookeeper;

import com.simple.rpc.registry.zookeeper.discovery.ZooKeeperServiceDiscovery;
import com.simple.rpc.registry.zookeeper.register.ZooKeeperServiceRegistry;
import com.simple.rpc.registry.ServiceDiscovery;
import com.simple.rpc.registry.ServiceRegistry;

/**
 * @author: lingjun.jlj
 * @date: 2019/4/30 17:10
 * @description: 测试基于zk的服务注册于发现
 */
public class ZkTest {

    public static void main(String[] args) {
        ServiceRegistry registry = new ZooKeeperServiceRegistry("127.0.0.1:2181");
        registry.register("rpc", "192.168.20.49:8080");
        ServiceDiscovery discovery = new ZooKeeperServiceDiscovery("127.0.0.1:2181");
        String address = discovery.discover("rpc");
        System.out.println("服务RPC的地址是：" + address);
    }
}
