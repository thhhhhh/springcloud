package com.thhh.springcloud.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.thhh.springcloud.dao.PaymentDao;
import com.thhh.springcloud.entities.Payment;
import com.thhh.springcloud.service.PaymentService;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl extends ServiceImpl<PaymentDao, Payment> implements PaymentService {
}
