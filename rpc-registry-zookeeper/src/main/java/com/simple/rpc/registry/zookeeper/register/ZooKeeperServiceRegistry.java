package com.simple.rpc.registry.zookeeper.register;

import com.simple.rpc.registry.ServiceRegistry;
import com.simple.rpc.registry.zookeeper.constant.ZkConstants;
import org.I0Itec.zkclient.ZkClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author: lingjun.jlj
 * @date: 2019/4/30 15:24
 * @description: 基于ZooKeeper的服务注册接口实现
 */
public class ZooKeeperServiceRegistry implements ServiceRegistry {

    private static final Logger log = LoggerFactory.getLogger(ZooKeeperServiceRegistry.class);

    private ZkClient zkClient;

    public ZooKeeperServiceRegistry(String zkAddress) {
        // 创建 ZooKeeper 客户端
        zkClient = new ZkClient(zkAddress, ZkConstants.SESSION_TIMEOUT, ZkConstants.CONNECTION_TIMEOUT);
        log.debug("connect zookeeper");
    }

    @Override
    public void register(String serviceName, String serviceAddress) {

        try {
            String registryPath = ZkConstants.REGISTRY_PATH;
            if (!zkClient.exists(registryPath)) {
                zkClient.createPersistent(registryPath);
                log.debug("zk create registry node: {}", registryPath);
            }
            //创建服务节点（持久化）
            String servicePath = registryPath + "/" + serviceName;
            if (!zkClient.exists(servicePath)) {
                zkClient.createPersistent(servicePath);
                log.debug("zk create service node: {}", servicePath);
            }
            //创建 address 节点（临时）
            String addressPath = servicePath + "/address-";
            String addressNode = zkClient.createEphemeralSequential(addressPath, serviceAddress);
            log.debug("zk create ip address node: {}", addressNode);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("zk create error: {}", e.getMessage());
        }

    }
}
