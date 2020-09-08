package com.hongyan.study.shardingjdbc.entity;

import lombok.Data;

/**
 * @author yan
 */
@Data
public class UserInfo {

    private Long userId;

    private String userName;

    private String account;

    private String password;
}
