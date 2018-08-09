CREATE SCHEMA `springnext` DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci ;

CREATE TABLE IF NOT EXISTS `springnext`.`t_user` (
  `tid` BIGINT NOT NULL AUTO_INCREMENT,
  `login_name` VARCHAR(255) NOT NULL,
  `login_password` VARCHAR(255) NOT NULL,
  `user_name` VARCHAR(255) NOT NULL,
  `email` VARCHAR(128) NULL,
  `phone` VARCHAR(128) NULL,
  `user_status` VARCHAR(32) NOT NULL,
  `group_id` BIGINT NOT NULL,
  `role_id` BIGINT NOT NULL,
  `is_delete` boolean NOT NULL DEFAULT false,
  PRIMARY KEY (`tid`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_unicode_ci;


CREATE TABLE IF NOT EXISTS `springnext`.`t_group` (
  `tid` BIGINT NOT NULL AUTO_INCREMENT,
  `group_name` VARCHAR(255) NOT NULL,
  `parent_id` BIGINT NULL,
  `is_delete` boolean NOT NULL DEFAULT false,
  PRIMARY KEY (`tid`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_unicode_ci;


CREATE TABLE IF NOT EXISTS `springnext`.`t_role` (
  `tid` BIGINT NOT NULL AUTO_INCREMENT,
  `role_name` VARCHAR(255) NOT NULL,
  `remark` VARCHAR(255) NOT NULL,
  `is_delete` boolean NOT NULL DEFAULT false,
  PRIMARY KEY (`tid`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_unicode_ci;


CREATE TABLE IF NOT EXISTS `springnext`.`t_permissions` (
  `tid` BIGINT NOT NULL AUTO_INCREMENT,
  `is_delete` boolean NOT NULL DEFAULT false,
  `parent_id` BIGINT NULL,
  `permissions` VARCHAR(255) NOT NULL,
  `remark` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`tid`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_unicode_ci;


CREATE TABLE IF NOT EXISTS `springnext`.`t_resources` (
  `tid` BIGINT NOT NULL AUTO_INCREMENT,
  `is_delete` boolean NOT NULL DEFAULT false,
  `url` VARCHAR(255) NOT NULL,
  `permissions_id` BIGINT NOT NULL,
  `permissions_name` VARCHAR(255) NOT NULL,
  `remark` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`tid`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_unicode_ci;

CREATE TABLE IF NOT EXISTS `springnext`.`t_role_permissions` (
  `role_id` BIGINT NOT NULL,
  `permissions_id` BIGINT NOT NULL,
  PRIMARY KEY (`role_id`, `permissions_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_unicode_ci;

CREATE TABLE IF NOT EXISTS `springnext`.`t_dictionary` (
  `tid` int(11) NOT NULL AUTO_INCREMENT,
  `dict_name` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `dict_value` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `type_name` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `type_value` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `is_delete` boolean NOT NULL DEFAULT false,
  PRIMARY KEY (`tid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

INSERT INTO t_role(role_name,remark,is_delete)VALUES('super_admin','超级管理员',false);
INSERT INTO t_group(group_name,is_delete)VALUES('SpringNext',false);

INSERT INTO t_user
(login_name,login_password,
user_name,email,user_status,group_id,role_id,is_delete)
VALUES('hyde','{bcrypt}$2a$10$nlRom5kDo1Vw.RRef2TSJuF8kFU0.d7RTdoV85WrvdXIBVBEp8LHe',
'HyDe','szhyde@qq.com','enable',1,1,false);


INSERT INTO t_dictionary(dict_name,dict_value,type_name,type_value,is_delete)VALUES('启用','enable','用户状态','userStatus',false);
INSERT INTO t_dictionary(dict_name,dict_value,type_name,type_value,is_delete)VALUES('停用','disable','用户状态','userStatus',false);
