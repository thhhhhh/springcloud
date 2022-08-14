package com.thhh.springcloud.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@RestController
@RequestMapping("consumer")
public class OrderConsulController {
    private static final String INVOK_URL = "http://consul-provider-payment";

    @Resource
    private RestTemplate restTemplate;

    @GetMapping("/payment/consul")
    public String paymentConsul(){
        return restTemplate.getForObject(INVOK_URL + "/payment/consul", String.class);
    }
}
