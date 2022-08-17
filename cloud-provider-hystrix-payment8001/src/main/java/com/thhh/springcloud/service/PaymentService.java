package com.thhh.springcloud.service;

import cn.hutool.core.util.IdUtil;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.concurrent.TimeUnit;

@Service
public class PaymentService {
    public String paymentInfo_OK(Integer id) {
        return "线程池:  "+Thread.currentThread().getName()+"  paymentInfo_OK,id:  "+id+"\t"+"O(∩_∩)O哈哈~";
    }

    /**
     * 假设我们设置这个方法3s内返回是正常的，超过3s就不正常，此时就需要hystrix做服务降级
     * 1. 添加注解@HystrixCommand
     * 2. 配置这个注解的fallbackMethod属性，属性值设置为为这个方法 超时/报错 时兜底处理的方法名称paymentInfo_TimeOutHelper
     * 3. 在这个方法下面紧接着定义方法paymentInfo_TimeOutHelper，方法签名除了方法名称多了一个 helper 之外不要有任何区别
     * 4. 编写paymentInfo_TimeOutHelper返回的方法体，返回 服务超时/不可用 的提示信息
     * 5. 还需要为注解@HystrixCommand设置一个属性 commandProperties，这是一个数组属性，用于存储HystrixProperty类型的属性，我们只需要将配置的k-v存入HystrixProperty中即可
     * 6. 配置的key为execution.isolation.thread.timeoutInMilliseconds，即设置这个方法对应的超时时间，这就配合前面我们说的超过3s认为服务超时，就进行服务降级，时间单位为ms
     * @param id
     * @return
     */
    @HystrixCommand(fallbackMethod = "paymentInfo_TimeOutHelper", commandProperties = {
        @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000")
    })
    public String paymentInfo_TimeOut(Integer id) {
        //把sleep时间设置为5，所以这个方法肯定超过了hystrix设置的3s超时时间，所以一定会触发服务降级
        int time = 2;
        try { TimeUnit.SECONDS.sleep(time); } catch (InterruptedException e) { e.printStackTrace(); }
        return "线程池:  "+Thread.currentThread().getName()+" id = "+id+"\t"+"O(∩_∩)O哈哈~"+"  耗时(秒) = " + time;
    }

    public String paymentInfo_TimeOutHelper(Integer id) {
        return "线程池:  "+Thread.currentThread().getName()+",8001系统繁忙或者运行报错，请稍后再试, id = "+id+"\t"+"/(ㄒoㄒ)/~~";
    }


    //=====服务熔断

    @HystrixCommand(fallbackMethod = "paymentCircuitBreaker_fallback",commandProperties = {
            @HystrixProperty(name = "circuitBreaker.enabled",value = "true"),// 是否开启断路器
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold",value = "10"),// 请求次数
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage",value = "60"),// 失败率达到多少后跳闸
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds",value = "10000"), // 时间窗口期,10s
    })
    public String paymentCircuitBreaker(@PathVariable("id") Integer id) {
        if(id < 0) {
            throw new RuntimeException("******id 不能负数");
        }
        String serialNumber = IdUtil.simpleUUID();

        return Thread.currentThread().getName()+"\t"+"调用成功，流水号: " + serialNumber;
    }

    public String paymentCircuitBreaker_fallback(@PathVariable("id") Integer id) {
        return "id 不能负数，请稍后再试，/(ㄒoㄒ)/~~   id: " +id;
    }
}
