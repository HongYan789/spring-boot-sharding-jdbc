package com.hongyan.study.shardingjdbc.demo;

import com.hongyan.study.shardingjdbc.service.OrderInfoService;
import com.hongyan.study.shardingjdbc.service.UserInfoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DatabaseTableTest {

    @Autowired
    private UserInfoService userInfoService;

    @Resource
    private OrderInfoService orderInfoService;

    /***
     * 测试分表插入user数据
     */
    @Test
    public void shardingUserInfoDemo() {
        //sharding测试
        userInfoService.insertBatchUserInfo();
    }

    @Test
    public void queryUserInfo() {
        //sharding测试
        userInfoService.queryUserIfo();
    }

    /***
     * 测试分表插入order数据
     */
    @Test
    public void shardingOrderInfoDemo() {
        //sharding测试
        orderInfoService.insertBatchOrderInfo();
    }


    @Test
    public void queryOrderById() {
        //sharding测试 - 主数据库
        orderInfoService.queryOrderById();
    }

}
