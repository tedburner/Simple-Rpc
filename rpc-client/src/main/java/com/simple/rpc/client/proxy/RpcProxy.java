package com.simple.rpc.client.proxy;

import com.simple.common.bean.RpcRequest;
import com.simple.common.bean.RpcResponse;
import com.simple.common.exception.SystemException;
import com.simple.common.util.IDUtils;
import com.simple.rpc.client.client.RpcClient;
import com.simple.rpc.registry.ServiceDiscovery;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.UUID;

/**
 * @author: lingjun.jlj
 * @date: 2019/5/5 13:35
 * @description: Rpc 代理
 */
@Slf4j
public class RpcProxy {

    private String serviceAddress;

    private ServiceDiscovery serviceDiscovery;

    public RpcProxy(String serviceAddress) {
        this.serviceAddress = serviceAddress;
    }

    public RpcProxy(ServiceDiscovery serviceDiscovery) {
        this.serviceDiscovery = serviceDiscovery;
    }

    public <T> T create(final Class<?> interfaceClass) {
        return create(interfaceClass, "");
    }

    @SuppressWarnings("unchecked")
    public <T> T create(final Class<?> interfaceClass, final String serviceVersion) {
        // 使用 GCLib 创建动态代理对象
        return (T) Proxy.newProxyInstance(
                interfaceClass.getClassLoader(),
                new Class<?>[]{interfaceClass},
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        //创建RPC请对并设置请求参数
                        RpcRequest request = new RpcRequest();
                        request.setRequestId(IDUtils.generateId());
                        request.setServiceVersion(serviceVersion);
                        request.setMethodName(method.getName());
                        request.setParameterTypes(method.getParameterTypes());
                        request.setParameters(args);
                        //获取服务地址
                        if (serviceDiscovery != null) {
                            String serviceName = interfaceClass.getName();
                            if (StringUtils.isNotEmpty(serviceVersion)) {
                                serviceName += "-" + serviceVersion;
                            }
                            serviceAddress = serviceDiscovery.discover(serviceName);
                            log.info("discover service: {} => {}", serviceName, serviceAddress);
                        }
                        if (StringUtils.isEmpty(serviceAddress)) {
                            throw new SystemException("service address is empty");
                        }
                        //从服务地址中解析主机名与端口号
                        String[] array = StringUtils.split(serviceAddress, ":");
                        String host = array[0];
                        int port = Integer.valueOf(array[1]);
                        //创建RPC 客户端对象并发送RPC请求
                        RpcClient client = new RpcClient(host, port);
                        Long startTime = System.currentTimeMillis();
                        RpcResponse response = client.send(request);
                        log.info("execute time {}:ms", System.currentTimeMillis() - startTime);
                        if (response == null) {
                            throw new SystemException("response is null");
                        }
                        if (response.hasException()) {
                            throw response.getException();
                        } else {
                            return response.getResult();
                        }
                    }
                }
        );
    }


}
