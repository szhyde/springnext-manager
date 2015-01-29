CREATE SCHEMA `springnext` DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci ;

CREATE TABLE `springnext`.`t_user` (
  `tid` BIGINT NOT NULL AUTO_INCREMENT,
  `login_name` VARCHAR(255) NOT NULL,
  `login_password` VARCHAR(255) NULL,
  `password_salt` VARCHAR(64) NULL,
  `user_name` VARCHAR(255) NULL,
  `email` VARCHAR(128) NULL,
  `user_status` VARCHAR(32) NULL,
  `group_id` BIGINT NULL,
  is_delete boolean NOT NULL,
  PRIMARY KEY (`tid`),
  UNIQUE INDEX `login_name_UNIQUE` (`login_name` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_unicode_ci;


CREATE TABLE `springnext`.`t_group` (
  `tid` BIGINT NOT NULL AUTO_INCREMENT,
  `group_name` VARCHAR(255) NOT NULL,
  `parent_id` BIGINT NULL,
  is_delete boolean NOT NULL,
  PRIMARY KEY (`tid`),
  UNIQUE INDEX `group_name_UNIQUE` (`group_name` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_unicode_ci;


CREATE TABLE `springnext`.`t_role` (
  `tid` BIGINT NOT NULL AUTO_INCREMENT,
  `role` VARCHAR(255) NOT NULL,
  `remark` VARCHAR(255) NOT NULL,
  is_delete boolean NOT NULL,
  PRIMARY KEY (`tid`),
  UNIQUE INDEX `role_name_UNIQUE` (`role` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_unicode_ci;


CREATE TABLE `springnext`.`t_permissions` (
  `tid` BIGINT NOT NULL AUTO_INCREMENT,
  `permissions` VARCHAR(255) NOT NULL,
  `remark` VARCHAR(255) NOT NULL,
  is_delete boolean NOT NULL,
  PRIMARY KEY (`tid`),
  UNIQUE INDEX `permissions_UNIQUE` (`permissions` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_unicode_ci;


CREATE TABLE `springnext`.`t_resources` (
  `tid` BIGINT NOT NULL AUTO_INCREMENT,
  `url` VARCHAR(255) NOT NULL,
  `permissions` VARCHAR(255) NOT NULL,
  `remark` VARCHAR(255) NOT NULL,
  is_delete boolean NOT NULL,
  PRIMARY KEY (`tid`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_unicode_ci;


CREATE TABLE `springnext`.`t_user_role` (
  `user_id` BIGINT NOT NULL,
  `role_id` BIGINT NOT NULL,
  PRIMARY KEY (`user_id`, `role_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_unicode_ci;


CREATE TABLE `springnext`.`t_role_permissions` (
  `role_id` BIGINT NOT NULL,
  `permissions_id` BIGINT NOT NULL,
  PRIMARY KEY (`role_id`, `permissions_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_unicode_ci;

INSERT INTO t_group(group_name,is_delete)VALUES('SpringNext',false);

INSERT INTO t_user
(login_name,login_password,password_salt,
user_name,email,user_status,group_id,is_delete)
VALUES('hyde','d84d2c9b86d5ffc6999b24ab21bbc674e25b6bce',
'84520b3c01901d06','HyDe','szhyde@qq.com','enable',1,false);