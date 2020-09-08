# springboot2.x集成sharding jdbc4.x实现分库分表功能demo

该项目主要采用springboot2.x+sharding jdbc4.x实现了mysql主从复制，读写分离（读从库，写主库）、分库分表（按模运算分配）

实施步骤：

1. 启用mysql主从数据库同步操作，具体见下述链接地址。
   核心思想：

##### master主库：

1、在my.cnf中添加启用binlog日志功能，并设置唯一server-id

```
[mysqld]
## 同一局域网内注意要唯一
server-id=100  
## 开启二进制日志功能，可以随便取（关键）
log-bin=mysql-bin
```

2、创建同步用户并授权用户权限

```
CREATE USER 'slave'@'%' IDENTIFIED BY '123456';
GRANT REPLICATION SLAVE, REPLICATION CLIENT ON *.* TO 'slave'@'%';
```

![image-20200908171658890](/Users/dearzhang/Library/Application Support/typora-user-images/image-20200908171658890.png)



3、重启master数据库，并查看数据库File和Position/位置点字段

```
show master status;
```

![image-20200908171728144](/Users/dearzhang/Library/Application Support/typora-user-images/image-20200908171728144.png)



##### slave从库：

4、和配置Master(主)一样，在Slave配置文件my.cnf中添加如下配置

```
[mysqld]
## 设置server_id,注意要唯一
server-id=101  
## 开启二进制日志功能，以备Slave作为其它Slave的Master时使用
log-bin=mysql-slave-bin   
## relay_log配置中继日志
relay_log=edu-mysql-relay-bin  
```

![image-20200908171821386](/Users/dearzhang/Library/Application Support/typora-user-images/image-20200908171821386.png)

5、执行配置读取master数据库中binlog文件，ip、账号、密码、file、Position等参数

```
change master to master_host='172.17.0.2', master_user='slave', master_password='123456', master_port=3306, master_log_file='mysql-bin.000001', master_log_pos= 2830, master_connect_retry=30;
```

![image-20200908171748933](/Users/dearzhang/Library/Application Support/typora-user-images/image-20200908171748933.png)



6、查看slave端主从同步状态、配置

```
show slave status \G;
```

![image-20200908171903692](/Users/dearzhang/Library/Application Support/typora-user-images/image-20200908171903692.png)

7、测试主从复制
在master数据库中执行crud操作，在slave数据库中观察数据是否正常同步过来即可

2. 引入sharding jdbc所需核心jar包

```
<!--sharding jdbc-->
<dependency>
    <groupId>org.apache.shardingsphere</groupId>
    <artifactId>sharding-jdbc-spring-boot-starter</artifactId>
    <version>4.0.1</version>
</dependency>
<dependency>
    <groupId>org.apache.shardingsphere</groupId>
    <artifactId>sharding-jdbc-spring-namespace</artifactId>
    <version>4.0.1</version>
</dependency>
```

3. 编写业务逻辑代码，代码无需做任何额外操作，业务代码跟平时ssm框架代码一致即可

   ![image-20200908171538868](/Users/dearzhang/Library/Application Support/typora-user-images/image-20200908171538868.png)

4. 增加sharding相关分片规则配置

```
spring:
  application:
    name: spring-boot-sharding-jdbc
  # 数据源
  shardingsphere:
    datasource:
      names: master0,master1,slave0,slave1
      #数据库db
      master0:
        type: com.alibaba.druid.pool.DruidDataSource
        driver-class-name: com.mysql.jdbc.Driver
        url: jdbc:mysql://127.0.0.1:3306/user_0?useUnicode=true&characterEncoding=utf8&useSSL=true&allowMultiQueries=true&verifyServerCertificate=false
        username: root
        password: 123456
      master1:
        type: com.alibaba.druid.pool.DruidDataSource
        driver-class-name: com.mysql.jdbc.Driver
        url: jdbc:mysql://127.0.0.1:3306/user_1?useUnicode=true&characterEncoding=utf8&useSSL=true&allowMultiQueries=true&verifyServerCertificate=false
        username: root
        password: 123456
      slave0:
        type: com.alibaba.druid.pool.DruidDataSource
        driver-class-name: com.mysql.jdbc.Driver
        url: jdbc:mysql://127.0.0.1:3307/user_0?useUnicode=true&characterEncoding=utf8&useSSL=true&allowMultiQueries=true&verifyServerCertificate=false
        username: root
        password: 123456
      slave1:
        type: com.alibaba.druid.pool.DruidDataSource
        driver-class-name: com.mysql.jdbc.Driver
        url: jdbc:mysql://127.0.0.1:3307/user_1?useUnicode=true&characterEncoding=utf8&useSSL=true&allowMultiQueries=true&verifyServerCertificate=false
        username: root
        password: 123456
    sharding:
      tables:
        user_info: #user_info表
          key-generator-column-name: user_id #主键
          actual-data-nodes: master$->{0..1}.user_info_$->{0..1}    #数据节点,均匀分布
          database-strategy:   #分库策略
            inline: #行表达式
              sharding-column: user_id        #列名称，多个列以逗号分隔
              algorithm-expression: master$->{user_id % 2}    #按模运算分配
          table-strategy:  #分表策略
            inline: #行表达式
              sharding-column: user_id
              algorithm-expression: user_info_$->{user_id % 2}
        t_order_item: #order_info表
          key-generator-column-name: order_id #主键
          actual-data-nodes: master$->{0..1}.t_order_item_$->{0..1}    #数据节点,均匀分布
          database-strategy:   #分库策略
            inline: #行表达式
              sharding-column: order_id        #列名称，多个列以逗号分隔
              algorithm-expression: master$->{order_id % 2}    #按模运算分配
          table-strategy:  #分表策略
            inline: #行表达式
              sharding-column: order_id
              algorithm-expression: t_order_item_$->{order_id % 2}
      master-slave-rules: #这里配置读写分离的时候一定要记得添加主库的数据源名称 这里为master0
        master0: #指定master0为主库，slave0为它的从库
          master-data-source-name: master0
          slave-data-source-names: slave0
        master1: #指定master1为主库，slave1为它的从库
          master-data-source-name: master1
          slave-data-source-names: slave1
    props:
      sql: #打印sql
        show: true
```

5. 测试读写分离，分库分表：com.hongyan.study.shardingjdbc.demo.DatabaseTableTest



执行写操作：发现是按模运算分配依次执行的主库master0、主库master1

```
@Test
public void shardingUserInfoDemo() {
    //sharding测试
    userInfoService.insertBatchUserInfo();
}
```

![image-20200908171322682](/Users/dearzhang/Library/Application Support/typora-user-images/image-20200908171322682.png)







执行读操作：发现读的是从库slave1（具体数据是读从库slave0还是slave1,也会按照分片策略读取）：

```
@Test
public void queryOrderById() {
    //sharding测试 - 主数据库
    orderInfoService.queryOrderById();
}
```

![image-20200908170707404](/Users/dearzhang/Library/Application%20Support/typora-user-images/image-20200908170707404.png)



5. 至此，咱们就成功的采用sharding jdbc实现了分库分表、读写分离的完整功能，以上！！！





### 借鉴于：

- [启用mysql主从数据库同步操作](https://www.cnblogs.com/songwenjie/p/9371422.html)

- [SpringBoot集成Sharding Jdbc进行分库分表](https://blog.csdn.net/tianyaleixiaowu/article/details/98057916)

  



