package com.hongyan.study.shardingjdbc.entity;

import lombok.Data;

/**
 * @author yan
 */
@Data
public class OrderInfo {

    private Long orderId;

    private String userName;

    private String account;

    private String password;
}
