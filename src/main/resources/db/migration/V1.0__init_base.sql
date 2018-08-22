DROP TABLE if EXISTS t_sys_user;
DROP TABLE if EXISTS t_sys_group;
DROP TABLE if EXISTS t_sys_role;
DROP TABLE if EXISTS t_sys_permissions;
DROP TABLE if EXISTS t_sys_resources;
DROP TABLE if EXISTS t_sys_user_role;
DROP TABLE if EXISTS t_sys_role_permissions;

CREATE TABLE t_sys_user (
  tid CHAR(32) NOT NULL ,
  login_name VARCHAR(255) NOT NULL,
  login_password VARCHAR(255) NOT NULL,
  user_name VARCHAR(255) NOT NULL,
  email VARCHAR(128) NULL,
  phone VARCHAR(128) NULL,
  user_status VARCHAR(32) NOT NULL,
  group_id CHAR(32) NOT NULL,
  role_id CHAR(32) NOT NULL,
  is_delete boolean NOT NULL DEFAULT false,
  PRIMARY KEY (tid));


CREATE TABLE t_sys_group (
  tid CHAR(32) NOT NULL ,
  group_name VARCHAR(255) NOT NULL,
  parent_id CHAR(32) NULL,
  is_delete boolean NOT NULL DEFAULT false,
  PRIMARY KEY (tid));


CREATE TABLE t_sys_role (
  tid CHAR(32) NOT NULL ,
  is_delete boolean NOT NULL DEFAULT false,
  role_name VARCHAR(255) NOT NULL,
  remark VARCHAR(255) NOT NULL,
  PRIMARY KEY (tid));


CREATE TABLE t_sys_permissions (
  tid CHAR(32) NOT NULL ,
  is_delete boolean NOT NULL DEFAULT false,
  parent_id VARCHAR(255) NULL,
  permissions VARCHAR(255) NOT NULL,
  remark VARCHAR(255) NOT NULL,
  PRIMARY KEY (tid));


CREATE TABLE t_sys_resources (
  tid CHAR(32) NOT NULL ,
  is_delete boolean NOT NULL DEFAULT false,
  url VARCHAR(255) NOT NULL,
  permissions_id CHAR(32) NOT NULL,
  permissions_name VARCHAR(255) NOT NULL,
  remark VARCHAR(255) NOT NULL,
  PRIMARY KEY (tid));


CREATE TABLE t_sys_role_permissions (
  role_id CHAR(32) NOT NULL,
  permissions_id CHAR(32) NOT NULL,
  PRIMARY KEY (role_id, permissions_id));
  
CREATE TABLE t_sys_dictionary (
  tid CHAR(32) NOT NULL ,
  dict_name VARCHAR(255) NOT NULL,
  dict_value VARCHAR(255)  NOT NULL,
  type_name VARCHAR(255)  NOT NULL,
  type_value VARCHAR(255) NOT NULL,
  is_delete boolean NOT NULL DEFAULT false,
  PRIMARY KEY (tid)
);
  
INSERT INTO t_sys_role(tid,role_name,remark,is_delete)VALUES('692dfb7178b842f09044bb86f34aaa3c','super_admin','超级管理员',false);
INSERT INTO t_sys_group(tid,group_name,is_delete)VALUES('e17823842da44b16a9b37a321f4f2cb9','SpringNext',false);

INSERT INTO t_sys_user
(tid,login_name,login_password,
user_name,email,user_status,group_id,role_id,is_delete)
VALUES('1ff2260096b14417bed8a2cd674ea3d9','hyde','{bcrypt}$2a$10$nlRom5kDo1Vw.RRef2TSJuF8kFU0.d7RTdoV85WrvdXIBVBEp8LHe',
'HyDe','szhyde@qq.com','enable','e17823842da44b16a9b37a321f4f2cb9','692dfb7178b842f09044bb86f34aaa3c',false);

INSERT INTO t_sys_dictionary(tid,dict_name,dict_value,type_name,type_value,is_delete)VALUES('e12647c2088e4f7392212149aab02087','启用','enable','用户状态','userStatus',false);
INSERT INTO t_sys_dictionary(tid,dict_name,dict_value,type_name,type_value,is_delete)VALUES('ea6c39656b974afa8ee10401d39618b0','停用','disable','用户状态','userStatus',false);
