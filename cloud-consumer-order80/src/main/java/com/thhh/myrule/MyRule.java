package com.thhh.myrule;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import com.netflix.loadbalancer.RoundRobinRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.atomic.AtomicInteger;

@Configuration
public class MyRule {
    @Bean
    public IRule getRule(){
        return new RoundRobinRule();
    }

    public static void main(String[] args) {
        AtomicInteger atomicInteger = new AtomicInteger(10);
        System.out.println(atomicInteger.compareAndSet(10, 1024) + "\t" + "current data is : " + atomicInteger.get());
        System.out.println(atomicInteger.compareAndSet(10, 2048) + "\t" + "current data is : " + atomicInteger.get());
    }
}
