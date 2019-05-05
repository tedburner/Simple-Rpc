package com.simple.rpc.server.server;

import com.simple.common.bean.RpcRequest;
import com.simple.common.bean.RpcResponse;
import com.simple.common.codec.RpcDecoder;
import com.simple.common.codec.RpcEncoder;
import com.simple.rpc.registry.ServiceRegistry;
import com.simple.rpc.server.annotation.Reference;
import com.simple.rpc.server.handler.RpcServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: lingjun.jlj
 * @date: 2019/5/5 09:01
 * @description: 使用netty实现RPC服务器
 */
@Slf4j
public class RpcServer implements ApplicationContextAware, InitializingBean {


    private String serviceAddress;
    private ServiceRegistry serviceRegistry;
    private Map<String, Object> handlerMap = new ConcurrentHashMap<>();

    public RpcServer(String serviceAddress) {
        this.serviceAddress = serviceAddress;
    }


    public RpcServer(String serviceAddress, ServiceRegistry registry) {
        this.serviceAddress = serviceAddress;
        this.serviceRegistry = registry;
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel channel) throws Exception {
                            channel.pipeline()
                                    .addLast(new RpcDecoder(RpcRequest.class))// 解码 RPC 请求
                                    .addLast(new RpcEncoder(RpcResponse.class))// 编码 RPC 响应
                                    .addLast(new RpcServerHandler(handlerMap)); // 处理 RPC 请求
                        }
                    });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        //获取所有带有Reference注解的spring bean
        Map<String, Object> serviceBeanMap = applicationContext.getBeansWithAnnotation(Reference.class);
        if (MapUtils.isNotEmpty(serviceBeanMap)) {
            for (Object serviceBean : serviceBeanMap.values()) {
                Reference reference = serviceBean.getClass().getAnnotation(Reference.class);
                String serviceName = reference.value().getName();
                String serviceVersion = reference.version();
                if (StringUtils.isNotEmpty(serviceVersion)) {
                    serviceName += "-" + serviceVersion;
                }
                handlerMap.put(serviceName, serviceBean);
            }
        }
    }
}
