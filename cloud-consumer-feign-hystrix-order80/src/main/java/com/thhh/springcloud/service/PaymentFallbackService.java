package com.thhh.springcloud.service;

import org.springframework.stereotype.Component;

@Component
public class PaymentFallbackService implements PaymentHystrixService{
    @Override
    public String paymentInfo_OK(Integer id) {
        return "----- paymentInfo_OK ----- 服务器当前不在线 ,o(╥﹏╥)o";
    }

    @Override
    public String paymentInfo_TimeOut(Integer id) {
        return "----- paymentInfo_TimeOut ----- 服务器当前不在线 ,o(╥﹏╥)o";
    }
}
