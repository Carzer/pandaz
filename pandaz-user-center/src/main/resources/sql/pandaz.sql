/*
 Navicat Premium Data Transfer

 Source Server         : 172.16.2.130
 Source Server Type    : MySQL
 Source Server Version : 50727
 Source Host           : 172.16.2.130
 Source Database       : pandaz

 Target Server Type    : MySQL
 Target Server Version : 50727
 File Encoding         : utf-8

 Date: 01/03/2020 10:07:18 AM
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `t_sys_dict_info`
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_dict_info`;
CREATE TABLE `t_sys_dict_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(100) NOT NULL COMMENT '类型名称',
  `code` varchar(50) NOT NULL COMMENT '类型编码',
  `type_code` varchar(50) NOT NULL COMMENT '字典类型编码',
  `created_by` varchar(50) NOT NULL DEFAULT 'system' COMMENT '创建人',
  `created_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_by` varchar(50) DEFAULT NULL COMMENT '修改人',
  `updated_date` datetime DEFAULT NULL COMMENT '修改时间',
  `deleted_by` varchar(50) DEFAULT NULL COMMENT '删除人',
  `deleted_date` datetime DEFAULT NULL COMMENT '删除时间',
  `deleted_flag` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除标记(0:未删除，1:已删除)',
  `locked` tinyint(4) NOT NULL DEFAULT '0' COMMENT '锁定标记(0:未锁定，1:已锁定)',
  `version` int(8) NOT NULL DEFAULT '1' COMMENT '版本号',
  PRIMARY KEY (`id`),
  UNIQUE KEY `t_os_info_code_key` (`code`),
  KEY `t_sys_dict_info_type_code_fk` (`type_code`),
  CONSTRAINT `t_sys_dict_info_type_code_fk` FOREIGN KEY (`type_code`) REFERENCES `t_sys_dict_type` (`code`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='字典信息表';

-- ----------------------------
--  Table structure for `t_sys_dict_type`
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_dict_type`;
CREATE TABLE `t_sys_dict_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(100) NOT NULL COMMENT '类型名称',
  `code` varchar(50) NOT NULL COMMENT '类型编码',
  `created_by` varchar(50) NOT NULL DEFAULT 'system' COMMENT '创建人',
  `created_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_by` varchar(50) DEFAULT NULL COMMENT '修改人',
  `updated_date` datetime DEFAULT NULL COMMENT '修改时间',
  `deleted_by` varchar(50) DEFAULT NULL COMMENT '删除人',
  `deleted_date` datetime DEFAULT NULL COMMENT '删除时间',
  `deleted_flag` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除标记(0:未删除，1:已删除)',
  `locked` tinyint(4) NOT NULL DEFAULT '0' COMMENT '锁定标记(0:未锁定，1:已锁定)',
  `version` int(8) NOT NULL DEFAULT '1' COMMENT '版本号',
  PRIMARY KEY (`id`),
  UNIQUE KEY `t_os_info_code_key` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='字典类型表';

-- ----------------------------
--  Records of `t_sys_dict_type`
-- ----------------------------
BEGIN;
INSERT INTO `t_sys_dict_type` VALUES ('1', 'demoData2', 'demoData', 'system', '2019-12-20 11:31:51', null, '2019-12-20 13:11:39', null, null, '0', '0', '7');
COMMIT;

-- ----------------------------
--  Table structure for `t_sys_group`
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_group`;
CREATE TABLE `t_sys_group` (
  `id` varchar(32) NOT NULL COMMENT '主键',
  `name` varchar(100) NOT NULL COMMENT '组名',
  `code` varchar(42) NOT NULL COMMENT '组编码',
  `parent_code` varchar(42) DEFAULT NULL COMMENT '父级组编码',
  `is_private` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否私有(0:否，1:是)',
  `created_by` varchar(50) NOT NULL DEFAULT 'system' COMMENT '创建人',
  `created_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_by` varchar(50) DEFAULT NULL COMMENT '修改人',
  `updated_date` datetime DEFAULT NULL COMMENT '修改时间',
  `deleted_by` varchar(50) DEFAULT NULL COMMENT '删除人',
  `deleted_date` datetime DEFAULT NULL COMMENT '删除时间',
  `deleted_flag` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除标记(0:未删除，1:已删除)',
  `locked` tinyint(4) NOT NULL DEFAULT '0' COMMENT '锁定标记(0:未锁定，1:已锁定)',
  `version` int(8) NOT NULL DEFAULT '1' COMMENT '版本号',
  PRIMARY KEY (`id`),
  UNIQUE KEY `t_sys_group_code_key` (`code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色信息';

-- ----------------------------
--  Records of `t_sys_group`
-- ----------------------------
BEGIN;
INSERT INTO `t_sys_group` VALUES ('1', '管理员组', 'GROUP_ADMIN', null, '0', 'system', '2019-10-23 16:39:15', null, null, null, null, '0', '0', '1'), ('4f705c0e11644c2c974e0be129826114', 'test_PRIVATE_GROUP', 'GROUP_test', null, '1', 'admin', '2019-10-25 03:26:07', null, null, null, null, '0', '0', '1'), ('6b862847514a3ac68a62e0456e95', 'test2_PRIVATE_GROUP', 'GROUP_test2', null, '1', '管理员', '2019-10-29 02:52:07', null, null, null, null, '0', '0', '1'), ('74790e4e004b8944e33fa3765016', 'test1_PRIVATE_GROUP', 'GROUP_test1', null, '1', '管理员', '2019-11-01 16:59:01', null, null, null, null, '0', '0', '1');
COMMIT;

-- ----------------------------
--  Table structure for `t_sys_group_role`
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_group_role`;
CREATE TABLE `t_sys_group_role` (
  `id` varchar(32) NOT NULL COMMENT '主键',
  `group_code` varchar(42) NOT NULL COMMENT '组编码',
  `role_code` varchar(50) NOT NULL COMMENT '角色编码',
  `created_by` varchar(50) NOT NULL DEFAULT 'system' COMMENT '创建人',
  `created_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `is_private` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否私有',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户-组关联表';

-- ----------------------------
--  Records of `t_sys_group_role`
-- ----------------------------
BEGIN;
INSERT INTO `t_sys_group_role` VALUES ('1', 'GROUP_ADMIN', 'ROLE_ADMIN', 'system', '2019-10-23 16:40:29', '0'), ('130c6b69b9a14659a3f24a2dc3e0fa24', 'GROUP_test', 'ROLE_GROUP_test', 'admin', '2019-10-25 03:26:07', '1'), ('70591d4e444dbbdbb9ceb44232b8', 'GROUP_test1', 'ROLE_GROUP_test1', '管理员', '2019-11-01 16:59:01', '1'), ('f59ed6c24641c9025443b6a239d0', 'GROUP_test2', 'ROLE_GROUP_test2', '管理员', '2019-10-29 02:52:07', '1');
COMMIT;

-- ----------------------------
--  Table structure for `t_sys_menu`
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_menu`;
CREATE TABLE `t_sys_menu` (
  `id` varchar(32) NOT NULL COMMENT '主键',
  `name` varchar(100) NOT NULL COMMENT '菜单名',
  `code` varchar(50) NOT NULL COMMENT '菜单编码',
  `os_code` varchar(50) DEFAULT NULL COMMENT '系统编码',
  `parent_code` varchar(50) NOT NULL DEFAULT 'root' COMMENT '父菜单编码',
  `icon` varchar(200) DEFAULT NULL COMMENT '图标',
  `sorting` int(8) NOT NULL DEFAULT '0' COMMENT '排序',
  `is_leaf_node` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否叶子节点',
  `created_by` varchar(50) NOT NULL DEFAULT 'system' COMMENT '创建人',
  `created_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_by` varchar(50) DEFAULT NULL COMMENT '修改人',
  `updated_date` datetime DEFAULT NULL COMMENT '修改时间',
  `deleted_by` varchar(50) DEFAULT NULL COMMENT '删除人',
  `deleted_date` datetime DEFAULT NULL COMMENT '删除时间',
  `deleted_flag` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除标记(0:未删除，1:已删除)',
  `locked` tinyint(4) NOT NULL DEFAULT '0' COMMENT '锁定标记(0:未锁定，1:已锁定)',
  `version` int(8) NOT NULL DEFAULT '1' COMMENT '版本号',
  PRIMARY KEY (`id`),
  UNIQUE KEY `t_sys_menu_code_key` (`code`),
  KEY `t_sys_menu_os_code_index` (`os_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='菜单信息';

-- ----------------------------
--  Table structure for `t_sys_oauth_client`
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_oauth_client`;
CREATE TABLE `t_sys_oauth_client` (
  `id` varchar(32) NOT NULL COMMENT '主键',
  `client_id` varchar(256) NOT NULL COMMENT '客户端ID',
  `resource_ids` varchar(256) DEFAULT NULL COMMENT '可访问资源ID',
  `client_secret` varchar(256) DEFAULT NULL COMMENT '客户端密钥',
  `scope` varchar(256) DEFAULT NULL COMMENT '使用范围',
  `authorized_grant_types` varchar(256) DEFAULT NULL COMMENT 'token获取方式',
  `web_server_redirect_uri` varchar(256) DEFAULT NULL COMMENT '授权码模式跳转uri',
  `authorities` varchar(256) DEFAULT NULL COMMENT '权限',
  `access_token_validity` int(11) DEFAULT NULL COMMENT 'token有效期',
  `refresh_token_validity` int(11) DEFAULT NULL COMMENT 'refresh_token有效期',
  `additional_information` varchar(4096) DEFAULT NULL COMMENT '附加信息',
  `auto_approve` varchar(256) DEFAULT NULL COMMENT '授权码模式自动跳过页面授权步骤',
  `created_by` varchar(50) NOT NULL DEFAULT 'system' COMMENT '创建人',
  `created_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_by` varchar(50) DEFAULT NULL COMMENT '修改人',
  `updated_date` datetime DEFAULT NULL COMMENT '修改时间',
  `deleted_by` varchar(50) DEFAULT NULL COMMENT '删除人',
  `deleted_date` datetime DEFAULT NULL COMMENT '删除时间',
  `deleted_flag` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除标记(0:未删除，1:已删除)',
  `locked` tinyint(4) NOT NULL DEFAULT '0' COMMENT '锁定标记(0:未锁定，1:已锁定)',
  `version` int(8) NOT NULL DEFAULT '1' COMMENT '版本号',
  PRIMARY KEY (`id`),
  UNIQUE KEY `t_os_info_code_key` (`client_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='oauth2客户端信息';

-- ----------------------------
--  Records of `t_sys_oauth_client`
-- ----------------------------
BEGIN;
INSERT INTO `t_sys_oauth_client` VALUES ('1', 'test', 'pandaz-user-center,pandaz-redis', '$2a$10$ok1NsN6t9dL1qNYKY16HVuSYP3NrzV6wL4AUYrQha7zCngV7/W.QW', 'read,write', 'password,refresh_token', null, null, null, null, null, null, 'system', '2020-01-02 13:17:50', null, null, null, null, '0', '0', '1');
COMMIT;

-- ----------------------------
--  Table structure for `t_sys_organization`
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_organization`;
CREATE TABLE `t_sys_organization` (
  `id` varchar(32) NOT NULL COMMENT '主键',
  `name` varchar(100) NOT NULL COMMENT '组织名',
  `code` varchar(50) NOT NULL COMMENT '组织编码',
  `parent_code` varchar(50) NOT NULL DEFAULT 'root' COMMENT '父组织编码',
  `icon` varchar(200) DEFAULT NULL COMMENT '图标',
  `sorting` int(8) NOT NULL DEFAULT '0' COMMENT '排序',
  `level` int(8) NOT NULL COMMENT '级别',
  `is_leaf_node` tinyint(4) NOT NULL DEFAULT '1' COMMENT '是否叶子节点',
  `created_by` varchar(50) NOT NULL DEFAULT 'system' COMMENT '创建人',
  `created_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_by` varchar(50) DEFAULT NULL COMMENT '修改人',
  `updated_date` datetime DEFAULT NULL COMMENT '修改时间',
  `deleted_by` varchar(50) DEFAULT NULL COMMENT '删除人',
  `deleted_date` datetime DEFAULT NULL COMMENT '删除时间',
  `deleted_flag` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除标记(0:未删除，1:已删除)',
  `locked` tinyint(4) NOT NULL DEFAULT '0' COMMENT '锁定标记(0:未锁定，1:已锁定)',
  `version` int(8) NOT NULL DEFAULT '1' COMMENT '版本号',
  PRIMARY KEY (`id`),
  UNIQUE KEY `t_sys_organization_code_key` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='组织信息';

-- ----------------------------
--  Table structure for `t_sys_os_info`
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_os_info`;
CREATE TABLE `t_sys_os_info` (
  `id` varchar(32) NOT NULL COMMENT '主键',
  `name` varchar(100) NOT NULL COMMENT '系统名',
  `code` varchar(50) NOT NULL COMMENT '系统编码',
  `parent_code` varchar(50) DEFAULT NULL COMMENT '父系统编码',
  `created_by` varchar(50) NOT NULL DEFAULT 'system' COMMENT '创建人',
  `created_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_by` varchar(50) DEFAULT NULL COMMENT '修改人',
  `updated_date` datetime DEFAULT NULL COMMENT '修改时间',
  `deleted_by` varchar(50) DEFAULT NULL COMMENT '删除人',
  `deleted_date` datetime DEFAULT NULL COMMENT '删除时间',
  `deleted_flag` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除标记(0:未删除，1:已删除)',
  `locked` tinyint(4) NOT NULL DEFAULT '0' COMMENT '锁定标记(0:未锁定，1:已锁定)',
  `version` int(8) NOT NULL DEFAULT '1' COMMENT '版本号',
  PRIMARY KEY (`id`),
  UNIQUE KEY `t_os_info_code_key` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色信息';

-- ----------------------------
--  Table structure for `t_sys_permission`
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_permission`;
CREATE TABLE `t_sys_permission` (
  `id` varchar(32) NOT NULL COMMENT '主键',
  `name` varchar(100) NOT NULL COMMENT '权限名',
  `code` varchar(36) NOT NULL COMMENT '权限编码',
  `os_code` varchar(50) NOT NULL COMMENT '系统编码',
  `menu_code` varchar(50) DEFAULT NULL COMMENT '菜单编码',
  `url` varchar(200) NOT NULL COMMENT '资源URL',
  `request_type` tinyint(4) NOT NULL COMMENT '请求方法(1:get,2:post,3:put,4:delete)',
  `priority` tinyint(2) NOT NULL COMMENT '优先级',
  `bit_result` int(8) NOT NULL COMMENT '位运算结果',
  `level` int(8) NOT NULL COMMENT '权限级别',
  `created_by` varchar(50) NOT NULL DEFAULT 'system' COMMENT '创建人',
  `created_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_by` varchar(50) DEFAULT NULL COMMENT '修改人',
  `updated_date` datetime DEFAULT NULL COMMENT '修改时间',
  `deleted_by` varchar(50) DEFAULT NULL COMMENT '删除人',
  `deleted_date` datetime DEFAULT NULL COMMENT '删除时间',
  `deleted_flag` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除标记(0:未删除，1:已删除)',
  `version` int(8) NOT NULL DEFAULT '1' COMMENT '版本号',
  PRIMARY KEY (`id`),
  UNIQUE KEY `t_sys_menu_code_key` (`menu_code`,`url`,`request_type`),
  KEY `t_sys_permission_code_index` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='权限信息';

-- ----------------------------
--  Records of `t_sys_permission`
-- ----------------------------
BEGIN;
INSERT INTO `t_sys_permission` VALUES ('4062b09ab3459a12d6428fcc581c', 'test', '843d1089ce4b78eb7164accadc70', '', null, '/test', '1', '1', '2', '0', 'system', '2019-11-25 15:21:19', null, null, null, null, '0', '1'), ('8261edc94f4ff95adcb913087666', 'test', '843d1089ce4b78eb7164accadc71', '', null, '/test', '1', '1', '2', '0', 'system', '2019-11-25 14:57:31', null, null, null, null, '0', '1');
COMMIT;

-- ----------------------------
--  Table structure for `t_sys_role`
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_role`;
CREATE TABLE `t_sys_role` (
  `id` varchar(32) NOT NULL COMMENT '主键',
  `name` varchar(100) NOT NULL COMMENT '角色名',
  `code` varchar(50) NOT NULL COMMENT '角色编码',
  `parent_code` varchar(50) DEFAULT NULL COMMENT '父角色编码',
  `is_private` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否私有(0:否，1:是)',
  `created_by` varchar(50) NOT NULL DEFAULT 'system' COMMENT '创建人',
  `created_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_by` varchar(50) DEFAULT NULL COMMENT '修改人',
  `updated_date` datetime DEFAULT NULL COMMENT '修改时间',
  `deleted_by` varchar(50) DEFAULT NULL COMMENT '删除人',
  `deleted_date` datetime DEFAULT NULL COMMENT '删除时间',
  `deleted_flag` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除标记(0:未删除，1:已删除)',
  `locked` tinyint(4) NOT NULL DEFAULT '0' COMMENT '锁定标记(0:未锁定，1:已锁定)',
  `version` int(8) NOT NULL DEFAULT '1' COMMENT '版本号',
  PRIMARY KEY (`id`),
  UNIQUE KEY `t_sys_role_code_key` (`code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色信息';

-- ----------------------------
--  Records of `t_sys_role`
-- ----------------------------
BEGIN;
INSERT INTO `t_sys_role` VALUES ('15f94b82fa0b4885b62a6ff6efb4c776', 'test_PRIVATE_GROUP_PRIVATE_ROLE', 'ROLE_GROUP_test', null, '1', 'admin', '2019-10-25 03:26:07', null, null, null, null, '0', '0', '1'), ('3ba60dca13414a678dd6f04d54aa', 'test1_PRIVATE_GROUP_PRIVATE_ROLE', 'ROLE_GROUP_test1', null, '1', '管理员', '2019-11-01 16:59:01', null, null, null, null, '0', '0', '1'), ('44337a8904b64341a2be6532e1393954', '管理员', 'ROLE_ADMIN', null, '0', 'system', '2019-08-22 15:55:09', null, null, null, null, '0', '0', '1'), ('fe37c2df6e4d3abcbd6f4fbbbbbf', 'test2_PRIVATE_GROUP_PRIVATE_ROLE', 'ROLE_GROUP_test2', null, '1', '管理员', '2019-10-29 02:52:07', null, null, null, null, '0', '0', '1');
COMMIT;

-- ----------------------------
--  Table structure for `t_sys_role_permission`
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_role_permission`;
CREATE TABLE `t_sys_role_permission` (
  `id` varchar(32) NOT NULL COMMENT '主键',
  `role_code` varchar(50) NOT NULL COMMENT '角色编码',
  `os_code` varchar(50) NOT NULL COMMENT '系统编码',
  `menu_code` varchar(50) NOT NULL COMMENT '菜单编码',
  `request_type` tinyint(4) DEFAULT NULL COMMENT '请求方式',
  `permission_value` int(8) NOT NULL COMMENT '权限值',
  `created_by` varchar(50) NOT NULL DEFAULT 'system' COMMENT '创建人',
  `created_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='组-权限关联表';

-- ----------------------------
--  Table structure for `t_sys_user`
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_user`;
CREATE TABLE `t_sys_user` (
  `id` varchar(32) NOT NULL COMMENT '主键',
  `name` varchar(50) NOT NULL COMMENT '姓名',
  `code` varchar(36) NOT NULL COMMENT '用户编码',
  `login_name` varchar(50) NOT NULL COMMENT '登录名',
  `password` varchar(60) NOT NULL COMMENT '密码',
  `gender` tinyint(4) NOT NULL DEFAULT '0' COMMENT '性别(0:男，1:女)',
  `user_type` varchar(10) NOT NULL DEFAULT '0' COMMENT '用户类型',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(20) NOT NULL COMMENT '电话号码',
  `created_by` varchar(50) NOT NULL DEFAULT 'system' COMMENT '创建人',
  `created_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_by` varchar(50) DEFAULT NULL COMMENT '修改人',
  `updated_date` datetime DEFAULT NULL COMMENT '修改时间',
  `deleted_by` varchar(50) DEFAULT NULL COMMENT '删除人',
  `deleted_date` datetime DEFAULT NULL COMMENT '删除时间',
  `deleted_flag` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除标记(0:未删除，1:已删除)',
  `locked` tinyint(4) NOT NULL DEFAULT '0' COMMENT '锁定标记(0:未锁定，1:已锁定)',
  `expire_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '过期时间',
  `version` int(8) NOT NULL DEFAULT '1' COMMENT '版本号',
  PRIMARY KEY (`id`),
  UNIQUE KEY `t_sys_user_code_key` (`code`),
  KEY `t_sys_user_version_index` (`version`),
  KEY `t_sys_user_login_name_index` (`login_name`),
  KEY `t_sys_user_phone_index` (`phone`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户信息';

-- ----------------------------
--  Records of `t_sys_user`
-- ----------------------------
BEGIN;
INSERT INTO `t_sys_user` VALUES ('18aa92168a42403486c4d1c1da9c3f35', 'test', 'test', 'test', '$2a$10$ok1NsN6t9dL1qNYKY16HVuSYP3NrzV6wL4AUYrQha7zCngV7/W.QW', '0', '0', null, '15192782889', 'admin', '2019-10-25 03:26:07', null, null, null, null, '0', '0', '2019-11-19 07:26:32', '1'), ('351d61d784458a35fdaee71f8727', 'test21', 'test2', 'test2', '$2a$10$Zh6iblW01KoYEUyqL0D13eAYv/R2XKyV22XYSRQlVEA2pl48nO3xe', '1', '0', null, '15192782889', '管理员', '2019-10-29 02:52:07', null, null, null, null, '0', '0', '2020-04-29 02:52:07', '1'), ('54337a8904b64341a2be6532e1393954', '管理员', 'admin', 'admin', '$2a$10$HUU2dCFoglJWi7Jv.F6b3.T50OAwGsNEqUT/NDSvbnSu1cAd9gKlC', '0', '0', null, '15192782889', 'system', '2019-08-22 15:55:09', null, null, null, null, '0', '0', '2020-11-23 15:26:28', '1'), ('efa464c6914ea94a59d796279e14', 'test21', 'test1', 'test1', '$2a$10$x7LvRs7Sd0uh8OyG4Tnqde41Mqadv1lcrOdtK1cNudSqMyQO4UyVe', '1', '0', null, '15192782889', '管理员', '2019-11-01 16:59:01', null, null, null, null, '0', '0', '2020-05-01 16:59:01', '1');
COMMIT;

-- ----------------------------
--  Table structure for `t_sys_user_group`
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_user_group`;
CREATE TABLE `t_sys_user_group` (
  `id` varchar(32) NOT NULL COMMENT '主键',
  `user_code` varchar(36) NOT NULL COMMENT '用户编码',
  `group_code` varchar(42) NOT NULL COMMENT '组编码',
  `created_by` varchar(50) NOT NULL DEFAULT 'system' COMMENT '创建人',
  `created_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `is_private` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否私有',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户-组关联表';

-- ----------------------------
--  Records of `t_sys_user_group`
-- ----------------------------
BEGIN;
INSERT INTO `t_sys_user_group` VALUES ('1', 'admin', 'GROUP_ADMIN', 'system', '2019-10-23 16:39:50', '0'), ('21afd40dc04f1ac498d4052866d1', 'test2', 'GROUP_test2', '管理员', '2019-10-29 02:52:07', '1'), ('305f83bea1401af37e365faf8c00', 'test1', 'GROUP_test1', '管理员', '2019-11-01 16:59:01', '1'), ('4f27e7ea5cb3463985c23572c0b48916', 'test', 'GROUP_test', 'admin', '2019-10-25 03:26:07', '1');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
