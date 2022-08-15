package com.thhh.springcloud.lb.Impl;

import com.netflix.loadbalancer.Server;
import com.thhh.springcloud.lb.MyLoadBalance;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

//作为组件放入ioc容器
@Component
public class MyLb implements MyLoadBalance {
    //用于存储当前服务的index
    private AtomicInteger atomicInteger = new AtomicInteger(0);

    //获取下一个可用服务的index

    /**
     * 模仿 RoundRobinRule.incrementAndGetModulo()来写
     * 即利用cas获取下一个服务的index
     * @return
     */
    public final int getAndIncrement(){
        int curr, next;
        do{
            //获取当前index
            curr = this.atomicInteger.get();
            //判断当前服务的index是否越界int的最大表示范围，没有就将curr+1并赋值给next
            next = curr >= Integer.MAX_VALUE ? 0 : curr+1;
            //通过cas修改当前存储服务index的AtomicInteger的值，因为curr和next两个值方法执行完之后就会销毁，只有对象属性还会保留下次还可以使用
            //判断AtomicInteger当前的值是不是curr，如果是就将其修改为next，这是由cas保障的线程安全的递增操作
        }while (!this.atomicInteger.compareAndSet(curr, next));
        System.out.println("********** > next = " + next);
        //将新的service的index返回出去
        return next;
    }

    @Override
    public ServiceInstance instances(List<ServiceInstance> serviceInstances) {
        //获取当前应该返回的service的index
        //getAndIncrement()返回的数字是不断增长的，serviceInstances.size()的值是固定的
        int index = getAndIncrement() % serviceInstances.size();
        return serviceInstances.get(index);
    }
}
