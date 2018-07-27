DROP TABLE if EXISTS t_user;
DROP TABLE if EXISTS t_group;
DROP TABLE if EXISTS t_role;
DROP TABLE if EXISTS t_permissions;
DROP TABLE if EXISTS t_resources;
DROP TABLE if EXISTS t_user_role;
DROP TABLE if EXISTS t_role_permissions;

CREATE TABLE t_user (
  tid BIGINT NOT NULL AUTO_INCREMENT,
  login_name VARCHAR(255) NOT NULL,
  login_password VARCHAR(255) NOT NULL,
  user_name VARCHAR(255) NOT NULL,
  email VARCHAR(128) NULL,
  phone VARCHAR(128) NULL,
  user_status VARCHAR(32) NOT NULL,
  group_id BIGINT NOT NULL,
  role_id BIGINT NOT NULL,
  is_delete boolean NOT NULL DEFAULT false,
  PRIMARY KEY (tid),
  UNIQUE INDEX login_name_UNIQUE (login_name ASC));


CREATE TABLE t_group (
  tid BIGINT NOT NULL AUTO_INCREMENT,
  group_name VARCHAR(255) NOT NULL,
  parent_id BIGINT NULL,
  is_delete boolean NOT NULL DEFAULT false,
  PRIMARY KEY (tid),
  UNIQUE INDEX group_name_UNIQUE (group_name ASC));


CREATE TABLE t_role (
  tid BIGINT NOT NULL AUTO_INCREMENT,
  role VARCHAR(255) NOT NULL,
  remark VARCHAR(255) NOT NULL,
  is_delete boolean NOT NULL DEFAULT false,
  PRIMARY KEY (tid),
  UNIQUE INDEX role_UNIQUE (role ASC));


CREATE TABLE t_permissions (
  tid BIGINT NOT NULL AUTO_INCREMENT,
  permissions VARCHAR(255) NOT NULL,
  remark VARCHAR(255) NOT NULL,
  is_delete boolean NOT NULL DEFAULT false,
  PRIMARY KEY (tid),
  UNIQUE INDEX permissions_UNIQUE (permissions ASC));


CREATE TABLE t_resources (
  tid BIGINT NOT NULL AUTO_INCREMENT,
  url VARCHAR(255) NOT NULL,
  permissions VARCHAR(255) NOT NULL,
  remark VARCHAR(255) NOT NULL,
  is_delete boolean NOT NULL DEFAULT false,
  PRIMARY KEY (tid));


CREATE TABLE t_role_permissions (
  role_id BIGINT NOT NULL,
  permissions_id BIGINT NOT NULL,
  PRIMARY KEY (role_id, permissions_id));
  
CREATE TABLE t_dictionary (
  tid BIGINT NOT NULL AUTO_INCREMENT,
  dict_name VARCHAR(255) NOT NULL,
  dict_value VARCHAR(255)  NOT NULL,
  type_name VARCHAR(255)  NOT NULL,
  type_value VARCHAR(255) NOT NULL,
  is_delete boolean NOT NULL DEFAULT false,
  PRIMARY KEY (tid)
);
  
INSERT INTO t_role(role,remark,is_delete)VALUES('super_admin','超级管理员',false);
INSERT INTO t_group(group_name,is_delete)VALUES('SpringNext',false);

INSERT INTO t_user
(login_name,login_password,
user_name,email,user_status,group_id,role_id,is_delete)
VALUES('hyde','{bcrypt}$2a$10$nlRom5kDo1Vw.RRef2TSJuF8kFU0.d7RTdoV85WrvdXIBVBEp8LHe',
'HyDe','szhyde@qq.com','enable',1,1,false);

INSERT INTO t_dictionary(dict_name,dict_value,type_name,type_value,is_delete)VALUES('启用','enable','userStatus','用户状态',false);
INSERT INTO t_dictionary(dict_name,dict_value,type_name,type_value,is_delete)VALUES('停用','disable','userStatus','用户状态',false);
