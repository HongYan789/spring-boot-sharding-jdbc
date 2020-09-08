CREATE DATABASE `user_0` ;

CREATE TABLE `user_0`.`user_info_1` (
  `user_id` BIGINT(19) NOT NULL,
  `user_name` VARCHAR(45) DEFAULT NULL,
  `account` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;


CREATE TABLE `user_0`.`user_info_0` (
  `user_id` BIGINT(19) NOT NULL,
  `user_name` VARCHAR(45) DEFAULT NULL,
  `account` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;


CREATE TABLE `user_0`.`t_order_item_0` (
  `order_id` BIGINT(19) NOT NULL,
  `user_name` VARCHAR(45) DEFAULT NULL,
  `account` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) DEFAULT NULL,
  PRIMARY KEY (`order_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

CREATE TABLE `user_0`.`t_order_item_1` (
  `order_id` BIGINT(19) NOT NULL,
  `user_name` VARCHAR(45) DEFAULT NULL,
  `account` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) DEFAULT NULL,
  PRIMARY KEY (`order_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;


CREATE DATABASE `user_1` ;

CREATE TABLE `user_1`.`user_info_1` (
  `user_id` BIGINT(19) NOT NULL,
  `user_name` VARCHAR(45) DEFAULT NULL,
  `account` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;


CREATE TABLE `user_1`.`user_info_0` (
  `user_id` BIGINT(19) NOT NULL,
  `user_name` VARCHAR(45) DEFAULT NULL,
  `account` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

CREATE TABLE `user_1`.`t_order_item_0` (
  `order_id` BIGINT(19) NOT NULL,
  `user_name` VARCHAR(45) DEFAULT NULL,
  `account` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) DEFAULT NULL,
  PRIMARY KEY (`order_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

CREATE TABLE `user_1`.`t_order_item_1` (
  `order_id` BIGINT(19) NOT NULL,
  `user_name` VARCHAR(45) DEFAULT NULL,
  `account` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) DEFAULT NULL,
  PRIMARY KEY (`order_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;