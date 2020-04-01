/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50729
 Source Host           : localhost
 Source Database       : pandaz

 Target Server Type    : MySQL
 Target Server Version : 50729
 File Encoding         : utf-8

 Date: 03/31/2020 12:13:30 PM
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `t_sys_dict_info`
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_dict_info`;
CREATE TABLE `t_sys_dict_info` (
                                   `id` varchar(32) NOT NULL COMMENT '主键',
                                   `name` varchar(100) NOT NULL COMMENT '类型名称',
                                   `code` varchar(50) NOT NULL COMMENT '类型编码',
                                   `type_code` varchar(50) NOT NULL COMMENT '字典类型编码',
                                   `created_by` varchar(50) NOT NULL DEFAULT 'system' COMMENT '创建人',
                                   `created_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                   `updated_by` varchar(50) DEFAULT NULL COMMENT '修改人',
                                   `updated_date` datetime DEFAULT NULL COMMENT '修改时间',
                                   `deleted_by` varchar(50) DEFAULT NULL COMMENT '删除人',
                                   `deleted_date` datetime DEFAULT NULL COMMENT '删除时间',
                                   `deleted_flag` varchar(32) NOT NULL DEFAULT '0' COMMENT '删除标记(0:未删除，其他:已删除)',
                                   `locked` tinyint(4) NOT NULL DEFAULT '0' COMMENT '锁定标记(0:未锁定，1:已锁定)',
                                   `version` int(8) NOT NULL DEFAULT '1' COMMENT '版本号',
                                   PRIMARY KEY (`id`),
                                   UNIQUE KEY `t_os_info_code_key` (`code`,`deleted_flag`),
                                   KEY `t_sys_dict_info_type_code_fk` (`type_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='字典信息表';

-- ----------------------------
--  Table structure for `t_sys_dict_type`
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_dict_type`;
CREATE TABLE `t_sys_dict_type` (
                                   `id` varchar(32) NOT NULL COMMENT '主键',
                                   `name` varchar(100) NOT NULL COMMENT '类型名称',
                                   `code` varchar(50) NOT NULL COMMENT '类型编码',
                                   `created_by` varchar(50) NOT NULL DEFAULT 'system' COMMENT '创建人',
                                   `created_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                   `updated_by` varchar(50) DEFAULT NULL COMMENT '修改人',
                                   `updated_date` datetime DEFAULT NULL COMMENT '修改时间',
                                   `deleted_by` varchar(50) DEFAULT NULL COMMENT '删除人',
                                   `deleted_date` datetime DEFAULT NULL COMMENT '删除时间',
                                   `deleted_flag` varchar(32) NOT NULL DEFAULT '0' COMMENT '删除标记(0:未删除，其他:已删除)',
                                   `locked` tinyint(4) NOT NULL DEFAULT '0' COMMENT '锁定标记(0:未锁定，1:已锁定)',
                                   `version` int(8) NOT NULL DEFAULT '1' COMMENT '版本号',
                                   PRIMARY KEY (`id`),
                                   UNIQUE KEY `t_os_info_code_key` (`code`,`deleted_flag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='字典类型表';

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
                               `deleted_flag` varchar(32) NOT NULL DEFAULT '0' COMMENT '删除标记(0:未删除，其他:已删除)',
                               `locked` tinyint(4) NOT NULL DEFAULT '0' COMMENT '锁定标记(0:未锁定，1:已锁定)',
                               `version` int(8) NOT NULL DEFAULT '1' COMMENT '版本号',
                               PRIMARY KEY (`id`),
                               UNIQUE KEY `t_sys_group_code_key` (`code`,`deleted_flag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色信息';

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
                                    `updated_by` varchar(50) DEFAULT NULL COMMENT '修改人',
                                    `updated_date` datetime DEFAULT NULL COMMENT '修改时间',
                                    `deleted_by` varchar(50) DEFAULT NULL COMMENT '删除人',
                                    `deleted_date` datetime DEFAULT NULL COMMENT '删除时间',
                                    `deleted_flag` varchar(32) NOT NULL DEFAULT '0' COMMENT '删除标记(0:未删除，其他:已删除)',
                                    `is_private` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否私有',
                                    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户-组关联表';

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
                              `deleted_flag` varchar(32) NOT NULL DEFAULT '0' COMMENT '删除标记(0:未删除，其他:已删除)',
                              `locked` tinyint(4) NOT NULL DEFAULT '0' COMMENT '锁定标记(0:未锁定，1:已锁定)',
                              `version` int(8) NOT NULL DEFAULT '1' COMMENT '版本号',
                              PRIMARY KEY (`id`),
                              UNIQUE KEY `t_sys_menu_code_key` (`code`,`deleted_flag`),
                              KEY `t_sys_menu_os_code_index` (`os_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='菜单信息';

-- ----------------------------
--  Table structure for `t_sys_oauth_client`
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_oauth_client`;
CREATE TABLE `t_sys_oauth_client` (
                                      `id` varchar(32) NOT NULL COMMENT '主键',
                                      `client_id` varchar(256) NOT NULL COMMENT '客户端ID',
                                      `client_name` varchar(256) NOT NULL COMMENT '客户端名称',
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
                                      `deleted_flag` varchar(32) NOT NULL DEFAULT '0' COMMENT '删除标记(0:未删除，其他:已删除)',
                                      `locked` tinyint(4) NOT NULL DEFAULT '0' COMMENT '锁定标记(0:未锁定，1:已锁定)',
                                      `version` int(8) NOT NULL DEFAULT '1' COMMENT '版本号',
                                      PRIMARY KEY (`id`),
                                      UNIQUE KEY `t_os_info_code_key` (`client_id`,`deleted_flag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='oauth2客户端信息';

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
                                      `level` int(8) DEFAULT NULL COMMENT '级别',
                                      `is_leaf_node` tinyint(4) NOT NULL DEFAULT '1' COMMENT '是否叶子节点',
                                      `created_by` varchar(50) NOT NULL DEFAULT 'system' COMMENT '创建人',
                                      `created_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                      `updated_by` varchar(50) DEFAULT NULL COMMENT '修改人',
                                      `updated_date` datetime DEFAULT NULL COMMENT '修改时间',
                                      `deleted_by` varchar(50) DEFAULT NULL COMMENT '删除人',
                                      `deleted_date` datetime DEFAULT NULL COMMENT '删除时间',
                                      `deleted_flag` varchar(32) NOT NULL DEFAULT '0' COMMENT '删除标记(0:未删除，其他:已删除)',
                                      `locked` tinyint(4) NOT NULL DEFAULT '0' COMMENT '锁定标记(0:未锁定，1:已锁定)',
                                      `version` int(8) NOT NULL DEFAULT '1' COMMENT '版本号',
                                      PRIMARY KEY (`id`),
                                      UNIQUE KEY `t_sys_organization_code_key` (`code`,`deleted_flag`)
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
                                 `deleted_flag` varchar(32) NOT NULL DEFAULT '0' COMMENT '删除标记(0:未删除，其他:已删除)',
                                 `locked` tinyint(4) NOT NULL DEFAULT '0' COMMENT '锁定标记(0:未锁定，1:已锁定)',
                                 `version` int(8) NOT NULL DEFAULT '1' COMMENT '版本号',
                                 PRIMARY KEY (`id`),
                                 UNIQUE KEY `t_os_info_code_key` (`code`,`deleted_flag`)
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
                                    `level` int(8) DEFAULT NULL COMMENT '权限级别',
                                    `is_private` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否私有(0:否，1:是)',
                                    `created_by` varchar(50) NOT NULL DEFAULT 'system' COMMENT '创建人',
                                    `created_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                    `updated_by` varchar(50) DEFAULT NULL COMMENT '修改人',
                                    `updated_date` datetime DEFAULT NULL COMMENT '修改时间',
                                    `deleted_by` varchar(50) DEFAULT NULL COMMENT '删除人',
                                    `deleted_date` datetime DEFAULT NULL COMMENT '删除时间',
                                    `deleted_flag` varchar(32) NOT NULL DEFAULT '0' COMMENT '删除标记(0:未删除，其他:已删除)',
                                    `version` int(8) NOT NULL DEFAULT '1' COMMENT '版本号',
                                    PRIMARY KEY (`id`),
                                    UNIQUE KEY `t_sys_menu_code_key` (`code`,`deleted_flag`),
                                    KEY `t_sys_permission_code_index` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='权限信息';

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
                              `deleted_flag` varchar(32) NOT NULL DEFAULT '0' COMMENT '删除标记(0:未删除，其他:已删除)',
                              `locked` tinyint(4) NOT NULL DEFAULT '0' COMMENT '锁定标记(0:未锁定，1:已锁定)',
                              `version` int(8) NOT NULL DEFAULT '1' COMMENT '版本号',
                              PRIMARY KEY (`id`),
                              UNIQUE KEY `t_sys_role_code_key` (`code`,`deleted_flag`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色信息';

-- ----------------------------
--  Table structure for `t_sys_role_permission`
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_role_permission`;
CREATE TABLE `t_sys_role_permission` (
                                         `id` varchar(32) NOT NULL COMMENT '主键',
                                         `role_code` varchar(50) NOT NULL COMMENT '角色编码',
                                         `permission_code` varchar(50) NOT NULL COMMENT '权限编码',
                                         `created_by` varchar(50) NOT NULL DEFAULT 'system' COMMENT '创建人',
                                         `created_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                         `updated_by` varchar(50) DEFAULT NULL COMMENT '更新人',
                                         `updated_date` datetime DEFAULT NULL COMMENT '更新时间',
                                         `deleted_by` varchar(50) DEFAULT NULL COMMENT '删除人',
                                         `deleted_date` datetime DEFAULT NULL COMMENT '删除时间',
                                         `deleted_flag` varchar(32) DEFAULT NULL COMMENT '删除标记(0:未删除，其他:已删除)',
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
                              `deleted_flag` varchar(32) NOT NULL DEFAULT '0' COMMENT '删除标记(0:未删除，其他:已删除)',
                              `locked` tinyint(4) NOT NULL DEFAULT '0' COMMENT '锁定标记(0:未锁定，1:已锁定)',
                              `expire_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '过期时间',
                              `version` int(8) NOT NULL DEFAULT '1' COMMENT '版本号',
                              PRIMARY KEY (`id`),
                              UNIQUE KEY `t_sys_user_code_key` (`code`,`deleted_flag`),
                              UNIQUE KEY `t_sys_user_login_name_uindex` (`login_name`,`deleted_flag`),
                              KEY `t_sys_user_version_index` (`version`),
                              KEY `t_sys_user_phone_index` (`phone`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户信息';

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
                                    `updated_by` varchar(50) DEFAULT NULL COMMENT '修改人',
                                    `updated_date` datetime DEFAULT NULL COMMENT '修改时间',
                                    `deleted_by` varchar(50) DEFAULT NULL COMMENT '删除人',
                                    `deleted_date` datetime DEFAULT NULL COMMENT '删除时间',
                                    `deleted_flag` varchar(32) NOT NULL DEFAULT '0' COMMENT '删除标记(0:未删除，其他:已删除)',
                                    `is_private` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否私有',
                                    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户-组关联表';

SET FOREIGN_KEY_CHECKS = 1;
