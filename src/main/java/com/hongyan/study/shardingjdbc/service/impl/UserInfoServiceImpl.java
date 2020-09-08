package com.hongyan.study.shardingjdbc.service.impl;


import com.hongyan.study.shardingjdbc.entity.UserInfo;
import com.hongyan.study.shardingjdbc.mapper.UserInfoMapper;
import com.hongyan.study.shardingjdbc.service.UserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private UserInfoMapper userInfoMapper;

    public static Long userId = 150L;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertBatchUserInfo() {
        log.info("insertBatchUserInfo--------------");
        for (int i = 1; i <= 10; i++) {
            UserInfo userInfo = new UserInfo();
            userInfo.setUserId(userId);
            userInfo.setAccount("Account" + i);
            userInfo.setPassword("pass" + i);
            userInfo.setUserName("name" + i);
            userId++;
            userInfoMapper.insert(userInfo);
        }
        log.info("over..........");
    }

    @Override
    public void queryUserIfo() {
        UserInfo user = userInfoMapper.selectByPrimaryKey(154L);
        log.info("user:[{}]",user);
    }
}
