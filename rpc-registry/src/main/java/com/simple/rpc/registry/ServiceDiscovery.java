package com.simple.rpc.registry;

/**
 * @author: lingjun.jlj
 * @date: 2019/4/30 14:47
 * @description: 服务发现接口
 */
public interface ServiceDiscovery {

    /**
     * 根据服务名称查找服务地址
     *
     * @param serviceName 服务名称
     * @return 服务地址
     */
    String discover(String serviceName);
}
