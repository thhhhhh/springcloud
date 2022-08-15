package com.thhh.springcloud.controller;

import com.thhh.springcloud.entities.CommonResult;
import com.thhh.springcloud.entities.Payment;
import com.thhh.springcloud.service.PaymentFeignService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@Slf4j
@RequestMapping("consumer")
public class OrderFeignController {
    @Resource
    private PaymentFeignService paymentFeignService;

    @GetMapping("/payment/create")
    CommonResult create(Payment payment){
        return paymentFeignService.create(payment);
    }

    @GetMapping("/payment/get/{id}")
    CommonResult getPaymentById(@PathVariable("id") Long id){
        return paymentFeignService.getPaymentById(id);
    }

    @GetMapping("/payment/discovery")
    Object discovery(){
        return paymentFeignService.discovery();
    }

    @GetMapping(value = "/payment/lb")
    String getPaymentByLb(){
        return paymentFeignService.getPaymentByLb();
    }
}
