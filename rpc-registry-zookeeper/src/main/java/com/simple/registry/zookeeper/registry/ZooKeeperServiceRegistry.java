package com.simple.registry.zookeeper.registry;

import com.simple.registry.zookeeper.constant.ZkConstants;
import com.simple.rpc.registry.ServiceRegistry;
import lombok.extern.slf4j.Slf4j;
import org.I0Itec.zkclient.ZkClient;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;

import java.util.concurrent.CountDownLatch;

/**
 * @author: lingjun.jlj
 * @date: 2019/4/30 15:24
 * @description: 基于ZooKeeper的服务注册接口实现
 */
@Slf4j
public class ZooKeeperServiceRegistry implements ServiceRegistry {

    private ZkClient zkClient;
    private static CountDownLatch latch = new CountDownLatch(1);

    public ZooKeeperServiceRegistry(String zkAddress) {
        // 创建 ZooKeeper 客户端
        zkClient = new ZkClient(zkAddress, ZkConstants.SESSION_TIMEOUT, ZkConstants.CONNECTION_TIMEOUT);
        log.info("connect zookeeper");
    }

    @Override
    public void register(String serviceName, String serviceAddress) {

        try {
            String registryPath = ZkConstants.REGISTRY_PATH;
            if (!zkClient.exists(registryPath)) {
                zkClient.createPersistent(registryPath);
                log.info("zk create registry node: {}", registryPath);
            }
            //创建服务节点（持久化）
            String servicePath = registryPath + "/" + serviceName;
            if (!zkClient.exists(servicePath)) {
                zkClient.createPersistent(servicePath);
                log.info("zk create service node: {}", servicePath);
            }
            //创建 address 节点（临时）
            String addressPath = servicePath + "/address-";
            String addressNode = zkClient.createEphemeralSequential(addressPath, serviceAddress);
            log.info("zk create ip address node: {}",addressNode);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("zk create error: {}", e.getMessage());
        }

    }

//    @Override
//    public void process(WatchedEvent watchedEvent) {
//        if (watchedEvent.getState() == Event.KeeperState.SyncConnected) {
//            latch.countDown();
//        }
//    }
}
