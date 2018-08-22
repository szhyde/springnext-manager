CREATE SCHEMA IF NOT EXISTS `springnext` DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci ;

CREATE TABLE IF NOT EXISTS `springnext`.`t_sys_user` (
  `tid` CHAR(32) NOT NULL ,
  `login_name` VARCHAR(255) NOT NULL,
  `login_password` VARCHAR(255) NOT NULL,
  `user_name` VARCHAR(255) NOT NULL,
  `email` VARCHAR(128) NULL,
  `phone` VARCHAR(128) NULL,
  `user_status` VARCHAR(32) NOT NULL,
  `group_id` CHAR(32) NOT NULL,
  `role_id` CHAR(32) NOT NULL,
  `is_delete` boolean NOT NULL DEFAULT false,
  PRIMARY KEY (`tid`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_unicode_ci;


CREATE TABLE IF NOT EXISTS `springnext`.`t_sys_group` (
  `tid` CHAR(32) NOT NULL ,
  `group_name` VARCHAR(255) NOT NULL,
  `parent_id` CHAR(32) NULL,
  `is_delete` boolean NOT NULL DEFAULT false,
  PRIMARY KEY (`tid`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_unicode_ci;


CREATE TABLE IF NOT EXISTS `springnext`.`t_sys_role` (
  `tid` CHAR(32) NOT NULL ,
  `role_name` VARCHAR(255) NOT NULL,
  `remark` VARCHAR(255) NOT NULL,
  `is_delete` boolean NOT NULL DEFAULT false,
  PRIMARY KEY (`tid`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_unicode_ci;


CREATE TABLE IF NOT EXISTS `springnext`.`t_sys_permissions` (
  `tid` CHAR(32) NOT NULL ,
  `is_delete` boolean NOT NULL DEFAULT false,
  `parent_id` CHAR(32) NULL,
  `permissions` VARCHAR(255) NOT NULL,
  `remark` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`tid`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_unicode_ci;


CREATE TABLE IF NOT EXISTS `springnext`.`t_sys_resources` (
  `tid` CHAR(32) NOT NULL ,
  `is_delete` boolean NOT NULL DEFAULT false,
  `url` VARCHAR(255) NOT NULL,
  `permissions_id` CHAR(32) NOT NULL,
  `permissions_name` VARCHAR(255) NOT NULL,
  `remark` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`tid`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_unicode_ci;

CREATE TABLE IF NOT EXISTS `springnext`.`t_sys_role_permissions` (
  `role_id` CHAR(32) NOT NULL,
  `permissions_id` CHAR(32) NOT NULL,
  PRIMARY KEY (`role_id`, `permissions_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_unicode_ci;

CREATE TABLE IF NOT EXISTS `springnext`.`t_sys_dictionary` (
  `tid` int(11) NOT NULL ,
  `dict_name` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `dict_value` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `type_name` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `type_value` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `is_delete` boolean NOT NULL DEFAULT false,
  PRIMARY KEY (`tid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
  
INSERT INTO t_sys_role(tid,role_name,remark,is_delete)VALUES('692dfb7178b842f09044bb86f34aaa3c','super_admin','超级管理员',false);
INSERT INTO t_sys_group(tid,group_name,is_delete)VALUES('e17823842da44b16a9b37a321f4f2cb9','SpringNext',false);

INSERT INTO t_sys_user
(tid,login_name,login_password,
user_name,email,user_status,group_id,role_id,is_delete)
VALUES('1ff2260096b14417bed8a2cd674ea3d9','hyde','{bcrypt}$2a$10$nlRom5kDo1Vw.RRef2TSJuF8kFU0.d7RTdoV85WrvdXIBVBEp8LHe',
'HyDe','szhyde@qq.com','enable','e17823842da44b16a9b37a321f4f2cb9','692dfb7178b842f09044bb86f34aaa3c',false);

INSERT INTO t_sys_dictionary(tid,dict_name,dict_value,type_name,type_value,is_delete)VALUES('e12647c2088e4f7392212149aab02087','启用','enable','用户状态','userStatus',false);
INSERT INTO t_sys_dictionary(tid,dict_name,dict_value,type_name,type_value,is_delete)VALUES('ea6c39656b974afa8ee10401d39618b0','停用','disable','用户状态','userStatus',false);
