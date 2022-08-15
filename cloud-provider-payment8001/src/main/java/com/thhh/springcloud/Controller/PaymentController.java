package com.thhh.springcloud.Controller;

import com.thhh.springcloud.entities.CommonResult;
import com.thhh.springcloud.entities.Payment;
import com.thhh.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("payment")
public class PaymentController {
    @Resource
    private PaymentService paymentService;

    @Value("${server.port}")
    private String port;

    @Resource
    private DiscoveryClient discoveryClient;

    @PostMapping("create")
    public CommonResult create(@RequestBody Payment payment){
        log.info("===================== > " + payment.toString());
        Boolean save = paymentService.save(payment);
        log.info("数据插入结果 ===== " + save);

        if (save){
            return new CommonResult(200, "数据插入成功, port = " + port, save);
        }else {
            return new CommonResult(444, "数据插入失败", null);
        }
    }

    @GetMapping("get/{id}")
    public CommonResult getPaymentById(@PathVariable("id") Long id){
        Payment payment = paymentService.getById(id);
        log.info("数据插入结果 ===== " + payment);
        if (payment != null){
            return new CommonResult(200, "数据查询成功, port = " + port, payment);
        }else {
            return new CommonResult(444, "数据查询失败，ID = " + id, null);
        }

    }

    @GetMapping("discovery")
    public Object discovery(){
        List<String> services = discoveryClient.getServices();
        //获取所有eureka中的1服务信息
        for (String service : services) {
            log.info("================== > service = " + service);
        }

        //获取指定别名的服务信息
        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        for (ServiceInstance instance : instances) {
            log.info(instance.getInstanceId() + "\t" +instance.getHost() + "\t" + instance.getPort() + "\t" + instance.getUri() + "\t" + instance.getServiceId() + "\t" +instance.getScheme() + "\t" + instance.getMetadata());
        }

        return discoveryClient;
    }

    @GetMapping(value = "lb")
    public String getPaymentByLb() {
        //只需要返回端口号即可，方便查看是哪台服务器进行的服务
        return port;
    }
}
