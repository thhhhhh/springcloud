package com.thhh.springcloud.lb;

import org.springframework.cloud.client.ServiceInstance;

import java.util.List;

/**
 * 面向接口编程
 */
public interface MyLoadBalance {
    //用于返回获取到的注册中心中选中的那一个ServiceInstance对象
    ServiceInstance instances(List<ServiceInstance> serviceInstances);
}
