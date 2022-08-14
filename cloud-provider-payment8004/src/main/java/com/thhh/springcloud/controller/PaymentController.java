package com.thhh.springcloud.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("payment")
public class PaymentController {

    @Value("${server.port}")
    private String port;

    @GetMapping("zk")
    public String paymentZK(){
        return "spring cloud with zookeeper : " + port + "\t" + UUID.randomUUID().toString();
    }

}
