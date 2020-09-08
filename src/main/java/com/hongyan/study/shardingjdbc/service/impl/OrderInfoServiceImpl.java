package com.hongyan.study.shardingjdbc.service.impl;

import com.hongyan.study.shardingjdbc.entity.OrderInfo;
import com.hongyan.study.shardingjdbc.mapper.OrderInfoMapper;
import com.hongyan.study.shardingjdbc.service.OrderInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Slf4j
@Service
public class OrderInfoServiceImpl implements OrderInfoService {

    @Resource
    private OrderInfoMapper orderInfoMapper;

    public static Long orderId = 250L;

    @Override
    public void queryOrderById() {
        Long currentOrderId = 252L;
        log.info(">>>>>>>>>queryOrderById>>>>>>>>>");
        OrderInfo order = orderInfoMapper.selectByPrimaryKey(currentOrderId);
        log.info(">>>>>>>>>order:[{}]",order);
    }

    @Override
    public void insertBatchOrderInfo() {
        System.out.println(">>>>>>>>>>>>insertBatchOrderInfo>>>>>>>>>>>>");
        for (int i = 1; i <= 10; i++) {
            OrderInfo orderInfo = new OrderInfo();
            orderInfo.setOrderId(orderId);
            orderInfo.setAccount("Account-w" + i);
            orderInfo.setPassword("pass-w" + i);
            orderInfo.setUserName("name-w" + i);
            orderId++;
            orderInfoMapper.insert(orderInfo);
        }
        System.out.println(">>>>>>>>>>>>over>>>>>>>>>>>>");
    }
}
