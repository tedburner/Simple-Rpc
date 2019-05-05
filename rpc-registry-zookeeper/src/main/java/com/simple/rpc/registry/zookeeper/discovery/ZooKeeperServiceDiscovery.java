package com.simple.rpc.registry.zookeeper.discovery;

import com.simple.common.exception.SystemException;
import com.simple.rpc.registry.zookeeper.constant.ZkConstants;
import com.simple.rpc.registry.ServiceDiscovery;
import lombok.extern.slf4j.Slf4j;
import org.I0Itec.zkclient.ZkClient;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author: lingjun.jlj
 * @date: 2019/4/30 15:22
 * @description: 基于ZooKeeper的服务发现接口实现
 */
public class ZooKeeperServiceDiscovery implements ServiceDiscovery {

    private static final Logger log = LoggerFactory.getLogger(ZooKeeperServiceDiscovery.class);

    private String zkAddress;
    public ZooKeeperServiceDiscovery(String zkAddress) {
        this.zkAddress = zkAddress;
    }


    @Override
    public String discover(String serviceName) {
        ZkClient zkClient = new ZkClient(zkAddress, ZkConstants.SESSION_TIMEOUT, ZkConstants.CONNECTION_TIMEOUT);
        log.info("connect zookeeper....");
        try {
            String servicePath = ZkConstants.REGISTRY_PATH + "/" + serviceName;
            if (!zkClient.exists(servicePath)) {
                throw new SystemException(String.format("can not find any service node on path: %s", servicePath));
            }
            //获取路径的子节点
            List<String> addressList = zkClient.getChildren(servicePath);
            if (CollectionUtils.isEmpty(addressList)) {
                throw new SystemException(String.format("can not find any address node on path: %s", servicePath));
            }
            //获取 address 节点
            String address;
            if (Objects.equals(addressList.size(), 1)) {
                //如果只有一个地址，则获取地址
                address = addressList.get(0);
                log.info("get only address node: {}", address);
            } else {
                //如果有多个ip，随机选择一个
                address = addressList.get(ThreadLocalRandom.current().nextInt(addressList.size()));
                log.info("get random address node:{}", address);
            }
            //获取 address 节点的值
            String addressPath = servicePath + "/" + address;
            return zkClient.readData(addressPath);
        } finally {
            zkClient.close();
        }
    }
}
