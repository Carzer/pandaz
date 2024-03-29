/*
 Navicat Premium Data Transfer

 Source Server         : mysql@localhost
 Source Server Type    : MySQL
 Source Server Version : 50729
 Source Host           : localhost
 Source Database       : pandaz

 Target Server Type    : MySQL
 Target Server Version : 50729
 File Encoding         : utf-8

 Date: 10/15/2020 16:33:58 PM
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `api_gateway_route`
-- ----------------------------
DROP TABLE IF EXISTS `api_gateway_route`;
CREATE TABLE `api_gateway_route`
(
    `id`           varchar(32)  NOT NULL COMMENT '主键',
    `route_id`     varchar(255) NOT NULL COMMENT '路由ID',
    `uri`          varchar(255) NOT NULL COMMENT 'URI',
    `predicates`   text         NOT NULL COMMENT '判定器',
    `filters`      text COMMENT '过滤器',
    `route_order`  int(11)               DEFAULT NULL COMMENT '排序',
    `description`  varchar(500)          DEFAULT NULL COMMENT '描述',
    `created_by`   varchar(50)  NOT NULL DEFAULT 'system' COMMENT '创建人',
    `created_date` datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_by`   varchar(50)           DEFAULT NULL COMMENT '更新人',
    `updated_date` datetime              DEFAULT NULL COMMENT '更新时间',
    `deleted_by`   varchar(50)           DEFAULT NULL COMMENT '删除人',
    `deleted_date` datetime              DEFAULT NULL COMMENT '删除时间',
    `deleted_flag` varchar(32)  NOT NULL DEFAULT '0' COMMENT '删除标记(0:未删除，其他:已删除)',
    `version`      int(8)       NOT NULL DEFAULT '1' COMMENT '版本号',
    PRIMARY KEY (`id`),
    KEY `t_sys_gateway_route_deleted_flag_index` (`deleted_flag`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='网关路由表';

-- ----------------------------
--  Records of `api_gateway_route`
-- ----------------------------
BEGIN;
INSERT INTO `api_gateway_route`
VALUES ('1', 'pandaz-auth', 'lb://pandaz-auth-server', '[{\"name\":\"Path\",\"args\":{\"pattern\":\"/auth/**\"}}]',
        '[{\"name\":\"StripPrefix\",\"args\":{\"parts\":\"1\"}}]', '0', '授权服务', 'system', '2020-06-15 17:31:40', null,
        null, null, null, '0', '1'),
       ('2', 'pandaz-redis', 'lb://pandaz-redis-server', '[{\"name\":\"Path\",\"args\":{\"pattern\":\"/redis/**\"}}]',
        '[{\"name\":\"StripPrefix\",\"args\":{\"parts\":\"1\"}}]', '0', 'redis服务', 'system', '2020-06-16 13:45:42',
        null, null, null, null, '0', '1'),
       ('3', 'pandaz-im-websocket', 'lb:ws://pandaz-im-server',
        '[{\"name\":\"Path\",\"args\":{\"pattern\":\"/ws/**\"}}]',
        '[{\"name\":\"StripPrefix\",\"args\":{\"parts\":\"1\"}}]', '0', 'websocket服务', 'system', '2020-06-16 13:46:21',
        null, null, null, null, '0', '1'),
       ('4', 'pandaz-im', 'lb://pandaz-im-server', '[{\"name\":\"Path\",\"args\":{\"pattern\":\"/im/**\"}}]',
        '[{\"name\":\"StripPrefix\",\"args\":{\"parts\":\"1\"}}]', '0', '消息服务', 'system', '2020-06-16 13:46:21', null,
        null, null, null, '0', '1');
COMMIT;

-- ----------------------------
--  Table structure for `auth_client`
-- ----------------------------
DROP TABLE IF EXISTS `auth_client`;
CREATE TABLE `auth_client`
(
    `id`                      varchar(32)  NOT NULL COMMENT '主键',
    `client_id`               varchar(256) NOT NULL COMMENT '客户端ID',
    `client_name`             varchar(256) NOT NULL COMMENT '客户端名称',
    `resource_ids`            varchar(256)          DEFAULT NULL COMMENT '可访问资源ID',
    `client_secret`           varchar(256)          DEFAULT NULL COMMENT '客户端密钥',
    `scope`                   varchar(256)          DEFAULT NULL COMMENT '使用范围',
    `authorized_grant_types`  varchar(256)          DEFAULT NULL COMMENT 'token获取方式',
    `web_server_redirect_uri` varchar(256)          DEFAULT NULL COMMENT '授权码模式跳转uri',
    `authorities`             varchar(256)          DEFAULT NULL COMMENT '权限',
    `access_token_validity`   int(11)               DEFAULT NULL COMMENT 'token有效期',
    `refresh_token_validity`  int(11)               DEFAULT NULL COMMENT 'refresh_token有效期',
    `additional_information`  varchar(4096)         DEFAULT NULL COMMENT '附加信息',
    `auto_approve`            varchar(256)          DEFAULT NULL COMMENT '授权码模式自动跳过页面授权步骤',
    `created_by`              varchar(50)  NOT NULL DEFAULT 'system' COMMENT '创建人',
    `created_date`            datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_by`              varchar(50)           DEFAULT NULL COMMENT '修改人',
    `updated_date`            datetime              DEFAULT NULL COMMENT '修改时间',
    `deleted_by`              varchar(50)           DEFAULT NULL COMMENT '删除人',
    `deleted_date`            datetime              DEFAULT NULL COMMENT '删除时间',
    `deleted_flag`            varchar(32)  NOT NULL DEFAULT '0' COMMENT '删除标记(0:未删除，其他:已删除)',
    `locked`                  tinyint(4)   NOT NULL DEFAULT '0' COMMENT '锁定标记(0:未锁定，1:已锁定)',
    `version`                 int(8)       NOT NULL DEFAULT '1' COMMENT '版本号',
    PRIMARY KEY (`id`),
    UNIQUE KEY `t_os_info_code_key` (`client_id`, `deleted_flag`),
    KEY `t_sys_oauth_client_deleted_flag_index` (`deleted_flag`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='oauth2客户端信息';

-- ----------------------------
--  Records of `auth_client`
-- ----------------------------
BEGIN;
INSERT INTO `auth_client`
VALUES ('100da4a6af45bba4bac2d1d0a1b3', 'test6', 'test6', null,
        '$2a$10$iwMrcYC6Xjn19BDw2pXVjupFRIF1m2N3/4GSH3tGmYtYe5FZU4QzO', null, 'password,refresh', null, null, '1800',
        '1800', null, null, 'admin', '2020-04-01 11:04:43', 'admin', '2020-04-02 15:24:19', null, null, '0', '0', '6'),
       ('341b7efdb8461b504dd7acb1d6ad', 'test', '测试客户端', null,
        '$2a$10$CDNQqh7dVQft5szQDr9gRObBpf8LZ0AgLEY8VJuvFHtXgOU4JZ.KK', 'read,write', 'password,refresh', null, null,
        '1800', '1800', null, null, 'system', '2020-03-30 15:49:59', 'admin', '2020-03-31 16:44:31', null, null, '0',
        '0', '3'),
       ('6f3c5d8e524fda0538db7bb92214', 'test2', 'test2', null,
        '$2a$10$pJut3igNzRnvOX4b0rtttuT.pe3YIzs.5Itnc2lX1kjYaSdwSeVG2', null, 'password,refresh', null, null, '1800',
        '1800', null, null, 'admin', '2020-03-31 17:18:41', null, null, null, null, '0', '0', '1'),
       ('8f45003df340b9b8e8a438e96797', 'test3', 'test3', null,
        '$2a$10$dWb1v.C4gpk3Ju/01HQq8.SyZs2Cqst2F0yv4wSRlS4FAxlxzZbEO', null, 'password,refresh', null, null, '1800',
        '1800', null, null, 'admin', '2020-03-31 17:26:06', null, null, null, null, '0', '0', '1'),
       ('a2e76ed9dd4b2a6107bcf592f023', 'test5', 'test5', null,
        '$2a$10$K1Y9cMym7H57m0k8iHAUhOMEto.gHYsSaLywPMFIGAuJ8sNlsYIYO', null, 'password,refresh', null, null, '1800',
        '1800', null, null, 'admin', '2020-04-01 10:50:35', null, null, null, null, '0', '0', '1'),
       ('e1ae0a41ce49fa7a1648da61bd0b', 'test4', 'test4', null,
        '$2a$10$4/sAyIRL/kI7ztL2X7y6v.dfJuv.60XzUMXy4WwVJ3yDG5OaA5RQC', null, 'password,refresh', null, null, '1800',
        '1800', null, null, 'admin', '2020-04-01 10:50:07', null, null, null, null, '0', '0', '1'),
       ('ec0564277b4669731b6c5bcbdc1e', 'test8', 'test8', null,
        '$2a$10$AWiAD3D0wTvY5C8utoseA.utMhhYPJt8WrDatwfRILZM5X3YLQFVC', null, 'password,refresh', null, null, '1800',
        '1800', null, null, 'admin', '2020-04-02 09:00:14', 'admin', '2020-09-04 14:38:27', null, null, '0', '0', '5');
COMMIT;

-- ----------------------------
--  Table structure for `auth_dict_info`
-- ----------------------------
DROP TABLE IF EXISTS `auth_dict_info`;
CREATE TABLE `auth_dict_info`
(
    `id`           varchar(32)  NOT NULL COMMENT '主键',
    `name`         varchar(100) NOT NULL COMMENT '类型名称',
    `code`         varchar(50)  NOT NULL COMMENT '类型编码',
    `type_code`    varchar(50)  NOT NULL COMMENT '字典类型编码',
    `created_by`   varchar(50)  NOT NULL DEFAULT 'system' COMMENT '创建人',
    `created_date` datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_by`   varchar(50)           DEFAULT NULL COMMENT '修改人',
    `updated_date` datetime              DEFAULT NULL COMMENT '修改时间',
    `deleted_by`   varchar(50)           DEFAULT NULL COMMENT '删除人',
    `deleted_date` datetime              DEFAULT NULL COMMENT '删除时间',
    `deleted_flag` varchar(32)  NOT NULL DEFAULT '0' COMMENT '删除标记(0:未删除，其他:已删除)',
    `locked`       tinyint(4)   NOT NULL DEFAULT '0' COMMENT '锁定标记(0:未锁定，1:已锁定)',
    `version`      int(8)       NOT NULL DEFAULT '1' COMMENT '版本号',
    PRIMARY KEY (`id`),
    UNIQUE KEY `t_os_info_code_key` (`code`, `deleted_flag`),
    KEY `t_sys_dict_info_type_code_fk` (`type_code`),
    KEY `t_sys_dict_info_deleted_flag_index` (`deleted_flag`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='字典信息表';

-- ----------------------------
--  Records of `auth_dict_info`
-- ----------------------------
BEGIN;
INSERT INTO `auth_dict_info`
VALUES ('3943590ea9474ae22d7af41e779f', '测试信息1', 'test', 'test', 'admin', '2020-03-30 16:59:45', 'admin',
        '2020-03-30 16:59:50', null, null, '0', '0', '2'),
       ('fc67cb316d4f7ad2a24ca06fe34e', 'test22', 'test22', 'test', 'admin', '2020-04-14 09:53:39', null, null, null,
        null, '0', '0', '1');
COMMIT;

-- ----------------------------
--  Table structure for `auth_dict_type`
-- ----------------------------
DROP TABLE IF EXISTS `auth_dict_type`;
CREATE TABLE `auth_dict_type`
(
    `id`           varchar(32)  NOT NULL COMMENT '主键',
    `name`         varchar(100) NOT NULL COMMENT '类型名称',
    `code`         varchar(50)  NOT NULL COMMENT '类型编码',
    `created_by`   varchar(50)  NOT NULL DEFAULT 'system' COMMENT '创建人',
    `created_date` datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_by`   varchar(50)           DEFAULT NULL COMMENT '修改人',
    `updated_date` datetime              DEFAULT NULL COMMENT '修改时间',
    `deleted_by`   varchar(50)           DEFAULT NULL COMMENT '删除人',
    `deleted_date` datetime              DEFAULT NULL COMMENT '删除时间',
    `deleted_flag` varchar(32)  NOT NULL DEFAULT '0' COMMENT '删除标记(0:未删除，其他:已删除)',
    `locked`       tinyint(4)   NOT NULL DEFAULT '0' COMMENT '锁定标记(0:未锁定，1:已锁定)',
    `version`      int(8)       NOT NULL DEFAULT '1' COMMENT '版本号',
    PRIMARY KEY (`id`),
    UNIQUE KEY `t_os_info_code_key` (`code`, `deleted_flag`),
    KEY `t_sys_dict_type_deleted_flag_index` (`deleted_flag`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='字典类型表';

-- ----------------------------
--  Records of `auth_dict_type`
-- ----------------------------
BEGIN;
INSERT INTO `auth_dict_type`
VALUES ('70ce63807040787c016156f0c113', '测试类型1', 'test', 'admin', '2020-03-30 16:59:18', 'admin', '2020-08-28 16:21:40',
        null, null, '0', '1', '3'),
       ('d76dd4ede6465960ecc528d75c12', '测试类型2', 'test2', 'admin', '2020-03-31 08:55:24', 'admin',
        '2020-08-28 16:21:44', null, null, '0', '0', '3');
COMMIT;

-- ----------------------------
--  Table structure for `auth_group`
-- ----------------------------
DROP TABLE IF EXISTS `auth_group`;
CREATE TABLE `auth_group`
(
    `id`           varchar(32)  NOT NULL COMMENT '主键',
    `name`         varchar(100) NOT NULL COMMENT '组名',
    `code`         varchar(42)  NOT NULL COMMENT '组编码',
    `parent_code`  varchar(42)           DEFAULT NULL COMMENT '父级组编码',
    `is_private`   tinyint(4)   NOT NULL DEFAULT '0' COMMENT '是否私有(0:否，1:是)',
    `created_by`   varchar(50)  NOT NULL DEFAULT 'system' COMMENT '创建人',
    `created_date` datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_by`   varchar(50)           DEFAULT NULL COMMENT '修改人',
    `updated_date` datetime              DEFAULT NULL COMMENT '修改时间',
    `deleted_by`   varchar(50)           DEFAULT NULL COMMENT '删除人',
    `deleted_date` datetime              DEFAULT NULL COMMENT '删除时间',
    `deleted_flag` varchar(32)  NOT NULL DEFAULT '0' COMMENT '删除标记(0:未删除，其他:已删除)',
    `locked`       tinyint(4)   NOT NULL DEFAULT '0' COMMENT '锁定标记(0:未锁定，1:已锁定)',
    `version`      int(8)       NOT NULL DEFAULT '1' COMMENT '版本号',
    `tenant_id`    int(11)      NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `t_sys_group_code_key` (`code`, `deleted_flag`),
    KEY `t_sys_group_deleted_flag_index` (`deleted_flag`),
    KEY `auth_group_tenant_id_index` (`tenant_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='角色信息';

-- ----------------------------
--  Records of `auth_group`
-- ----------------------------
BEGIN;
INSERT INTO `auth_group`
VALUES ('1ee1d741fa4c89454f4d0e09f965', '系统管理员', 'GROUP_sys_admin', null, '0', 'admin', '2020-04-14 09:06:01', null,
        null, null, null, '0', '0', '1', '0'),
       ('8fd76c179a45d9a7b782d3c096c9', '测试组1', 'GROUP_test', null, '0', 'admin', '2020-03-30 16:53:09', 'admin',
        '2020-03-30 17:00:35', null, null, '0', '0', '2', '0'),
       ('90a51366714d3be2c96ac3223670', '测试人员_PRIVATE_GROUP', 'test', null, '1', 'admin', '2020-04-14 09:21:08', null,
        null, null, null, '0', '0', '1', '0'),
       ('c1157caf2e4b78907ec1c28e40c3', 'admin_PRIVATE_GROUP', 'admin', null, '1', 'system', '2020-03-30 15:49:59',
        null, null, null, null, '0', '0', '1', '0');
COMMIT;

-- ----------------------------
--  Table structure for `auth_group_role`
-- ----------------------------
DROP TABLE IF EXISTS `auth_group_role`;
CREATE TABLE `auth_group_role`
(
    `id`           varchar(32) NOT NULL COMMENT '主键',
    `group_code`   varchar(42) NOT NULL COMMENT '组编码',
    `role_code`    varchar(50) NOT NULL COMMENT '角色编码',
    `created_by`   varchar(50) NOT NULL DEFAULT 'system' COMMENT '创建人',
    `created_date` datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_by`   varchar(50)          DEFAULT NULL COMMENT '修改人',
    `updated_date` datetime             DEFAULT NULL COMMENT '修改时间',
    `deleted_by`   varchar(50)          DEFAULT NULL COMMENT '删除人',
    `deleted_date` datetime             DEFAULT NULL COMMENT '删除时间',
    `deleted_flag` varchar(32) NOT NULL DEFAULT '0' COMMENT '删除标记(0:未删除，其他:已删除)',
    `is_private`   tinyint(4)  NOT NULL DEFAULT '0' COMMENT '是否私有',
    `tenant_id`    int(11)     NOT NULL,
    PRIMARY KEY (`id`),
    KEY `t_sys_group_role_deleted_flag_index` (`deleted_flag`),
    KEY `auth_group_role_tenant_id_index` (`tenant_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='用户-组关联表';

-- ----------------------------
--  Records of `auth_group_role`
-- ----------------------------
BEGIN;
INSERT INTO `auth_group_role`
VALUES ('03396d286e40e8b26f4b98c91f05', 'GROUP_sys_admin', 'ROLE_ADMIN', 'admin', '2020-04-21 12:10:02', null, null,
        null, null, '0', '0', '0'),
       ('0ad9fde155459adb9b8f45067347', 'GROUP_test', 'ROLE_test', 'admin', '2020-04-26 14:39:19', null, null, null,
        null, '0', '0', '0'),
       ('22b0f0272a4778ba5f1a15a4bbee', 'GROUP_admin', 'ROLE_GROUP_admin', 'system', '2020-03-30 15:02:38', null, null,
        null, null, '0', '1', '0'),
       ('43ca64bf52473b3ba7a56b42d034', 'GROUP_sys_admin', 'GROUP_sys_admin', 'admin', '2020-04-14 09:06:01', null,
        null, null, null, '0', '1', '0'),
       ('50ce9993e043abf399a7f2c9fb32', 'admin', 'admin', 'system', '2020-03-30 15:49:59', null, null, null, null, '0',
        '1', '0'),
       ('677e206f5742d97b89b4c6aeacf5', 'test', 'test', 'admin', '2020-04-14 09:21:08', null, null, null, null, '0',
        '1', '0'),
       ('9ab0b494f6426ab6912c437becdb', 'GROUP_test', 'GROUP_test', 'admin', '2020-03-30 16:53:09', null, null, null,
        null, '0', '1', '0'),
       ('dac78e9b2e425befd145969cdf89', 'admin', 'admin', 'system', '2020-03-30 15:28:04', null, null, null, null, '0',
        '1', '0'),
       ('dce1a9e2fd4e98204536deec9b4f', 'admin', 'ROLE_ADMIN', 'system', '2020-03-30 15:49:59', null, null, null, null,
        '0', '0', '0');
COMMIT;

-- ----------------------------
--  Table structure for `auth_menu`
-- ----------------------------
DROP TABLE IF EXISTS `auth_menu`;
CREATE TABLE `auth_menu`
(
    `id`           varchar(32)  NOT NULL COMMENT '主键',
    `name`         varchar(100) NOT NULL COMMENT '菜单名',
    `code`         varchar(50)  NOT NULL COMMENT '菜单编码',
    `os_code`      varchar(50)           DEFAULT NULL COMMENT '系统编码',
    `parent_code`  varchar(50)  NOT NULL DEFAULT 'root' COMMENT '父菜单编码',
    `parent_codes` varchar(300)          DEFAULT NULL COMMENT '所有父编码',
    `url`          varchar(500)          DEFAULT NULL COMMENT '后台URL',
    `router`       varchar(200)          DEFAULT NULL COMMENT '前台router',
    `icon`         varchar(200)          DEFAULT NULL COMMENT '图标',
    `sorting`      int(8)       NOT NULL DEFAULT '0' COMMENT '排序',
    `is_leaf_node` tinyint(4)   NOT NULL DEFAULT '0' COMMENT '是否叶子节点',
    `remark`       varchar(256)          DEFAULT NULL COMMENT '描述',
    `created_by`   varchar(50)  NOT NULL DEFAULT 'system' COMMENT '创建人',
    `created_date` datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_by`   varchar(50)           DEFAULT NULL COMMENT '修改人',
    `updated_date` datetime              DEFAULT NULL COMMENT '修改时间',
    `deleted_by`   varchar(50)           DEFAULT NULL COMMENT '删除人',
    `deleted_date` datetime              DEFAULT NULL COMMENT '删除时间',
    `deleted_flag` varchar(32)  NOT NULL DEFAULT '0' COMMENT '删除标记(0:未删除，其他:已删除)',
    `locked`       tinyint(4)   NOT NULL DEFAULT '0' COMMENT '锁定标记(0:未锁定，1:已锁定)',
    `version`      int(8)       NOT NULL DEFAULT '1' COMMENT '版本号',
    PRIMARY KEY (`id`),
    UNIQUE KEY `t_sys_menu_code_key` (`code`, `deleted_flag`),
    KEY `t_sys_menu_os_code_index` (`os_code`),
    KEY `t_sys_menu_deleted_flag_index` (`deleted_flag`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='菜单信息';

-- ----------------------------
--  Records of `auth_menu`
-- ----------------------------
BEGIN;
INSERT INTO `auth_menu`
VALUES ('002b87190c400b2c03874cd950bd', 'oauth2客户端信息', 'client', 'portal', 'osPage', 'root,osPage', '/client',
        '/os/client', null, '0', '1', '', 'admin', '2020-04-07 15:12:11', 'admin', '2020-09-04 14:26:30', null, null,
        '0', '0', '2'),
       ('006f3c483c4deb5c7831fc44ae0b', '字典类型', 'dictType', 'portal', 'metaPage', 'root,metaPage', '/dict/type',
        '/meta/dictType', null, '0', '1', null, 'admin', '2020-04-07 15:17:32', null, null, null, null, '0', '0', '1'),
       ('1', '系统管理', 'osPage', 'portal', 'root', 'root', null, null, null, '0', '0', null, 'system',
        '2020-03-31 15:53:24', 'admin', '2020-04-07 15:00:33', null, null, '0', '0', '16'),
       ('3966ff475e4ca8a50794099f4a8e', '菜单信息', 'menu', 'portal', 'authPage', 'root,authPage', '/menu', '/auth/menu',
        null, '0', '1', null, 'admin', '2020-04-07 15:15:43', null, null, null, null, '0', '0', '1'),
       ('4bc95332c8486b0a48c4e645f516', '权限信息', 'permission', 'portal', 'authPage', 'root,authPage', '/permission',
        '/auth/permission', null, '0', '1', null, 'admin', '2020-04-07 15:16:00', null, null, null, null, '0', '0',
        '1'),
       ('64a86f9daa446909bbd6b46ffea4', '用户管理', 'userPage', 'portal', 'root', 'root', '', '', null, '1', '0', '',
        'admin', '2020-04-07 15:00:23', 'admin', '2020-04-29 16:27:19', null, null, '0', '0', '3'),
       ('68ba5414374a6823071c4d3bdd36', '组织信息', 'organization', 'portal', 'osPage', 'root,osPage', '/organization',
        '/os/organization', null, '0', '1', null, 'admin', '2020-04-07 15:11:33', 'admin', '2020-04-07 15:11:36', null,
        null, '0', '0', '2'),
       ('6ba7e1147d4dbbbd83862bc8c475', '用户信息', 'user', 'portal', 'userPage', 'root,osPage', '/user', '/user/user',
        null, '0', '1', null, 'admin', '2020-04-07 15:12:33', null, null, null, null, '0', '0', '1'),
       ('6e5adb200b48e99cbba770bd246c', '基础信息管理', 'metaPage', 'portal', 'root', 'root', '', '', null, '3', '0', '',
        'admin', '2020-04-07 15:04:42', 'admin', '2020-04-29 16:34:54', null, null, '0', '0', '5'),
       ('7dd8fd4cab46688282b50b6d870d', '用户组信息', 'group', 'portal', 'userPage', 'root,osPage', '/group', '/user/group',
        null, '0', '1', null, 'admin', '2020-04-07 15:12:54', null, null, null, null, '0', '0', '1'),
       ('85eca20608440b7294eeca78de03', '权限管理', 'authPage', 'portal', 'root', 'root', '', '', null, '2', '0', '',
        'admin', '2020-04-07 15:04:19', 'admin', '2020-04-29 16:27:24', null, null, '0', '0', '3'),
       ('8c8236f4024c796c8af634ae6542', '角色信息', 'role', 'portal', 'authPage', 'root,authPage', '/role', '/auth/role',
        null, '0', '1', null, 'admin', '2020-04-07 15:15:22', null, null, null, null, '0', '0', '1'),
       ('8f950a0c894c385b9ea42eb5d5ec', '字典信息', 'dictInfo', 'portal', 'metaPage', 'root,metaPage', '/dict/info',
        '/meta/dictInfo', null, '1', '1', '', 'admin', '2020-04-07 15:18:00', 'admin', '2020-08-28 16:21:30', null,
        null, '0', '0', '4'),
       ('cabfffbfaf4ffa265a1c3e13e9dc', '系统信息', 'osInfo', 'portal', 'osPage', 'root,osPage', '/osInfo', '/os/osInfo',
        null, '0', '1', null, 'admin', '2020-04-07 15:11:07', null, null, null, null, '0', '0', '1');
COMMIT;

-- ----------------------------
--  Table structure for `auth_organization`
-- ----------------------------
DROP TABLE IF EXISTS `auth_organization`;
CREATE TABLE `auth_organization`
(
    `id`           varchar(32)  NOT NULL COMMENT '主键',
    `name`         varchar(100) NOT NULL COMMENT '组织名',
    `code`         varchar(50)  NOT NULL COMMENT '组织编码',
    `parent_code`  varchar(50)  NOT NULL DEFAULT 'root' COMMENT '父组织编码',
    `icon`         varchar(200)          DEFAULT NULL COMMENT '图标',
    `sorting`      int(8)       NOT NULL DEFAULT '0' COMMENT '排序',
    `level`        int(8)                DEFAULT NULL COMMENT '数据权限级别',
    `is_leaf_node` tinyint(4)   NOT NULL DEFAULT '1' COMMENT '是否叶子节点',
    `remark`       varchar(256)          DEFAULT NULL,
    `created_by`   varchar(50)  NOT NULL DEFAULT 'system' COMMENT '创建人',
    `created_date` datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_by`   varchar(50)           DEFAULT NULL COMMENT '修改人',
    `updated_date` datetime              DEFAULT NULL COMMENT '修改时间',
    `deleted_by`   varchar(50)           DEFAULT NULL COMMENT '删除人',
    `deleted_date` datetime              DEFAULT NULL COMMENT '删除时间',
    `deleted_flag` varchar(32)  NOT NULL DEFAULT '0' COMMENT '删除标记(0:未删除，其他:已删除)',
    `locked`       tinyint(4)   NOT NULL DEFAULT '0' COMMENT '锁定标记(0:未锁定，1:已锁定)',
    `version`      int(8)       NOT NULL DEFAULT '1' COMMENT '版本号',
    `tenant_id`    int(11)      NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `t_sys_organization_code_key` (`code`, `deleted_flag`),
    KEY `t_sys_organization_deleted_flag_index` (`deleted_flag`),
    KEY `auth_organization_tenant_id_index` (`tenant_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='组织信息';

-- ----------------------------
--  Records of `auth_organization`
-- ----------------------------
BEGIN;
INSERT INTO `auth_organization`
VALUES ('60d91d7956472ac59357a3943d52', '测试2', 'test2_child2', 'test2', null, '0', null, '1', null, 'admin',
        '2020-06-15 11:23:11', null, null, null, null, '0', '0', '1', '0'),
       ('b46b35907a4a88740b0bb0b3d761', '测试组织', 'test', 'root', null, '0', null, '1', '', 'admin',
        '2020-03-31 09:16:24', 'admin', '2020-09-04 14:38:55', null, null, '0', '0', '6', '0'),
       ('b4d07f759340fa04881cf868bc7d', '测试组织2', 'test2', 'root', null, '0', null, '1', null, 'admin',
        '2020-06-04 09:47:02', null, null, null, null, '0', '0', '1', '0'),
       ('ef0982cce14e7a7b29518027a974', '测试1', 'test2_child', 'test2', null, '1', null, '1', '', 'admin',
        '2020-06-15 11:22:57', 'admin', '2020-08-28 16:20:52', null, null, '0', '0', '4', '0');
COMMIT;

-- ----------------------------
--  Table structure for `auth_os_info`
-- ----------------------------
DROP TABLE IF EXISTS `auth_os_info`;
CREATE TABLE `auth_os_info`
(
    `id`           varchar(32)  NOT NULL COMMENT '主键',
    `name`         varchar(100) NOT NULL COMMENT '系统名',
    `code`         varchar(50)  NOT NULL COMMENT '系统编码',
    `parent_code`  varchar(50)           DEFAULT NULL COMMENT '父系统编码',
    `created_by`   varchar(50)  NOT NULL DEFAULT 'system' COMMENT '创建人',
    `created_date` datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_by`   varchar(50)           DEFAULT NULL COMMENT '修改人',
    `updated_date` datetime              DEFAULT NULL COMMENT '修改时间',
    `deleted_by`   varchar(50)           DEFAULT NULL COMMENT '删除人',
    `deleted_date` datetime              DEFAULT NULL COMMENT '删除时间',
    `deleted_flag` varchar(32)  NOT NULL DEFAULT '0' COMMENT '删除标记(0:未删除，其他:已删除)',
    `locked`       tinyint(4)   NOT NULL DEFAULT '0' COMMENT '锁定标记(0:未锁定，1:已锁定)',
    `version`      int(8)       NOT NULL DEFAULT '1' COMMENT '版本号',
    `tenant_id`    int(11)      NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `t_os_info_code_key` (`code`, `deleted_flag`),
    KEY `t_sys_os_info_deleted_flag_index` (`deleted_flag`),
    KEY `auth_os_info_tenant_id_index` (`tenant_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='角色信息';

-- ----------------------------
--  Records of `auth_os_info`
-- ----------------------------
BEGIN;
INSERT INTO `auth_os_info`
VALUES ('3a33d8110940a9d325f89ac09e4c', 'Portal', 'portal', '', 'admin', '2020-03-30 16:54:23', null, null, null, null,
        '0', '0', '1', '0'),
       ('3e2830356f466a0c00347b2c9582', '测试系统', 'test', null, 'admin', '2020-04-03 10:13:16', null, null, null, null,
        '0', '0', '1', '0'),
       ('9a10bf18e243789c59597384bf22', 'test', 'sets', 'sats', 'admin', '2020-04-07 17:03:01', null, null, null, null,
        '0', '0', '1', '0');
COMMIT;

-- ----------------------------
--  Table structure for `auth_permission`
-- ----------------------------
DROP TABLE IF EXISTS `auth_permission`;
CREATE TABLE `auth_permission`
(
    `id`           varchar(32)  NOT NULL COMMENT '主键',
    `name`         varchar(100) NOT NULL COMMENT '权限名',
    `code`         varchar(50)  NOT NULL COMMENT '权限编码',
    `os_code`      varchar(50)  NOT NULL COMMENT '系统编码',
    `menu_code`    varchar(50)           DEFAULT NULL COMMENT '菜单编码',
    `bit_digit`    tinyint(2)   NOT NULL COMMENT '位移数',
    `bit_result`   int(8)       NOT NULL COMMENT '位运算结果',
    `level`        int(8)                DEFAULT NULL COMMENT '权限级别',
    `created_by`   varchar(50)  NOT NULL DEFAULT 'system' COMMENT '创建人',
    `created_date` datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_by`   varchar(50)           DEFAULT NULL COMMENT '修改人',
    `updated_date` datetime              DEFAULT NULL COMMENT '修改时间',
    `deleted_by`   varchar(50)           DEFAULT NULL COMMENT '删除人',
    `deleted_date` datetime              DEFAULT NULL COMMENT '删除时间',
    `deleted_flag` varchar(32)  NOT NULL DEFAULT '0' COMMENT '删除标记(0:未删除，其他:已删除)',
    `version`      int(8)       NOT NULL DEFAULT '1' COMMENT '版本号',
    PRIMARY KEY (`id`),
    UNIQUE KEY `t_sys_menu_code_key` (`code`, `os_code`, `deleted_flag`),
    KEY `t_sys_permission_code_index` (`code`),
    KEY `t_sys_permission_deleted_flag_index` (`deleted_flag`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='权限信息';

-- ----------------------------
--  Records of `auth_permission`
-- ----------------------------
BEGIN;
INSERT INTO `auth_permission`
VALUES ('0501ff541c43fa3f26b008d3524a', 'oauth2客户端信息:导出', 'client/export', 'portal', 'client', '5', '32', null,
        'system', '2020-04-23 15:45:52', null, null, null, null, '0', '1'),
       ('0600a731d4438aa9d9bceeaa2447', '字典信息:导入', 'dict/info/import', 'portal', 'dictInfo', '4', '16', null, 'system',
        '2020-04-23 15:45:53', null, null, null, null, '0', '1'),
       ('084df17c96479842239539c2943a', '用户信息:查看', 'user/get', 'portal', 'user', '0', '1', null, 'system',
        '2020-04-23 15:45:52', null, null, null, null, '0', '1'),
       ('0866a3ad5e435afd3a6dab66d7d7', 'oauth2客户端信息:编辑', 'client/update', 'portal', 'client', '2', '4', null, 'system',
        '2020-04-23 15:45:52', null, null, null, null, '0', '1'),
       ('0d203ba3064b1a175f74110175e9', '用户组信息:删除', 'group/delete', 'portal', 'group', '3', '8', null, 'system',
        '2020-04-23 15:45:53', null, null, null, null, '0', '1'),
       ('0d2fa64dd74fb8b8248f44162066', '权限信息:查看', 'permission/get', 'portal', 'permission', '0', '1', null, 'system',
        '2020-04-23 15:45:53', null, null, null, null, '0', '1'),
       ('0d437f24774b790675b56d1c9caa', '用户信息:导出', 'user/export', 'portal', 'user', '5', '32', null, 'system',
        '2020-04-23 15:45:52', null, null, null, null, '0', '1'),
       ('168125cf974da94ec1f59935bc28', '角色信息:编辑', 'role/update', 'portal', 'role', '2', '4', null, 'system',
        '2020-04-23 15:45:53', null, null, null, null, '0', '1'),
       ('192e95678a4d6963164cfa23e8fb', '用户组信息:导入', 'group/import', 'portal', 'group', '4', '16', null, 'system',
        '2020-04-23 15:45:53', null, null, null, null, '0', '1'),
       ('20f8e24d8a4f983f4ade777fd333', '菜单信息:编辑', 'menu/update', 'portal', 'menu', '2', '4', null, 'system',
        '2020-04-23 15:45:53', null, null, null, null, '0', '1'),
       ('21e3e626d14e3a8a0e984b831412', '用户组信息:导出', 'group/export', 'portal', 'group', '5', '32', null, 'system',
        '2020-04-23 15:45:53', null, null, null, null, '0', '1'),
       ('220d7bb5ca43e95166aa7c5f481f', '字典信息:编辑', 'dict/info/update', 'portal', 'dictInfo', '2', '4', null, 'system',
        '2020-04-23 15:45:53', null, null, null, null, '0', '1'),
       ('2235431ff34f2952bfbc64c5134c', '用户信息:编辑', 'user/update', 'portal', 'user', '2', '4', null, 'system',
        '2020-04-23 15:45:52', null, null, null, null, '0', '1'),
       ('279eb530d348ab30d2d33dadca2c', '角色信息:导出', 'role/export', 'portal', 'role', '5', '32', null, 'system',
        '2020-04-23 15:45:53', null, null, null, null, '0', '1'),
       ('3a0ca081014148b25ff1e16e72b5', '角色信息:导入', 'role/import', 'portal', 'role', '4', '16', null, 'system',
        '2020-04-23 15:45:53', null, null, null, null, '0', '1'),
       ('3a910591d54cba7fafb8bbf9580c', '字典信息:导出', 'dict/info/export', 'portal', 'dictInfo', '5', '32', null, 'system',
        '2020-04-23 15:45:53', null, null, null, null, '0', '1'),
       ('3b9ccf48b3456b4f4c7c4338fb64', '菜单信息:导入', 'menu/import', 'portal', 'menu', '4', '16', null, 'system',
        '2020-04-23 15:45:53', null, null, null, null, '0', '1'),
       ('3c363dec4c4ba957d728625cb17d', '字典信息:新增', 'dict/info/insert', 'portal', 'dictInfo', '1', '2', null, 'system',
        '2020-04-23 15:45:53', null, null, null, null, '0', '1'),
       ('3db9269b8144796ad6569cf8eb7b', '权限信息:编辑', 'permission/update', 'portal', 'permission', '2', '4', null,
        'system', '2020-04-23 15:45:53', null, null, null, null, '0', '1'),
       ('42395e1c994f5b11af24ba079428', '权限信息:删除', 'permission/delete', 'portal', 'permission', '3', '8', null,
        'system', '2020-04-23 15:45:53', null, null, null, null, '0', '1'),
       ('4cc7c8009c4f780de616bffc62b0', '字典类型:新增', 'dict/type/insert', 'portal', 'dictType', '1', '2', null, 'system',
        '2020-04-23 15:45:53', null, null, null, null, '0', '1'),
       ('58bf11cad647e9c6a71e0397fc32', '菜单信息:查看', 'menu/get', 'portal', 'menu', '0', '1', null, 'system',
        '2020-04-23 15:45:53', null, null, null, null, '0', '1'),
       ('5e4dd2dd3a4fcb5e5ffaa03bcf54', '字典信息:查看', 'dict/info/get', 'portal', 'dictInfo', '0', '1', null, 'system',
        '2020-04-23 15:45:53', null, null, null, null, '0', '1'),
       ('64846721bd48bae19dd0cca03fb8', '字典类型:删除', 'dict/type/delete', 'portal', 'dictType', '3', '8', null, 'system',
        '2020-04-23 15:45:53', null, null, null, null, '0', '1'),
       ('67eeafad7845b9bcc409692d1a29', '权限信息:新增', 'permission/insert', 'portal', 'permission', '1', '2', null,
        'system', '2020-04-23 15:45:53', null, null, null, null, '0', '1'),
       ('6a6ee9972e48a9adb77c0d44b3a4', '字典类型:查看', 'dict/type/get', 'portal', 'dictType', '0', '1', null, 'system',
        '2020-04-23 15:45:53', null, null, null, null, '0', '1'),
       ('6bd2468feb48da713d1e7406c909', '用户信息:导入', 'user/import', 'portal', 'user', '4', '16', null, 'system',
        '2020-04-23 15:45:52', null, null, null, null, '0', '1'),
       ('7aaf84006b4bf8f9f580c5b3abe4', '用户组信息:编辑', 'group/update', 'portal', 'group', '2', '4', null, 'system',
        '2020-04-23 15:45:53', null, null, null, null, '0', '1'),
       ('7bf41f0a68485949bbd8dbb735bc', '组织信息:编辑', 'organization/update', 'portal', 'organization', '2', '4', null,
        'system', '2020-04-23 15:45:52', null, null, null, null, '0', '1'),
       ('7cf930abfe456a43544d1ae43af7', '系统信息:查看', 'osInfo/get', 'portal', 'osInfo', '0', '1', null, 'system',
        '2020-04-23 15:45:52', null, null, null, null, '0', '1'),
       ('81df38e9c84b6a5f0a4ccceb9d46', '用户信息:删除', 'user/delete', 'portal', 'user', '3', '8', null, 'system',
        '2020-04-23 15:45:52', null, null, null, null, '0', '1'),
       ('87b5dc399f4c3a4d5fa083a3cd50', '字典类型:编辑', 'dict/type/update', 'portal', 'dictType', '2', '4', null, 'system',
        '2020-04-23 15:45:53', null, null, null, null, '0', '1'),
       ('8b8dc0490f48fbcc7513f254dd47', '权限信息:导入', 'permission/import', 'portal', 'permission', '4', '16', null,
        'system', '2020-04-23 15:45:53', null, null, null, null, '0', '1'),
       ('8defa27cbe43e81d68b2ce8672c5', '菜单信息:删除', 'menu/delete', 'portal', 'menu', '3', '8', null, 'system',
        '2020-04-23 15:45:53', null, null, null, null, '0', '1'),
       ('9ed613c7ba44181eef170b4f0bf8', '字典信息:删除', 'dict/info/delete', 'portal', 'dictInfo', '3', '8', null, 'system',
        '2020-04-23 15:45:53', null, null, null, null, '0', '1'),
       ('9fbdb91a4d4ac89848f736c80ba1', '系统信息:新增', 'osInfo/insert', 'portal', 'osInfo', '1', '2', null, 'system',
        '2020-04-23 15:45:52', null, null, null, null, '0', '1'),
       ('a02f70954048786c4e5763d524f0', '字典类型:导入', 'dict/type/import', 'portal', 'dictType', '4', '16', null, 'system',
        '2020-04-23 15:45:53', null, null, null, null, '0', '1'),
       ('a0f148c7d8432a106dc6d4ad3682', '角色信息:新增', 'role/insert', 'portal', 'role', '1', '2', null, 'system',
        '2020-04-23 15:45:53', null, null, null, null, '0', '1'),
       ('a0ff07cac04f1ad2b029abee048d', 'oauth2客户端信息:导入', 'client/import', 'portal', 'client', '4', '16', null,
        'system', '2020-04-23 15:45:52', null, null, null, null, '0', '1'),
       ('a4df09e144450b9f163382518381', '字典类型:导出', 'dict/type/export', 'portal', 'dictType', '5', '32', null, 'system',
        '2020-04-23 15:45:53', null, null, null, null, '0', '1'),
       ('afe6404e604c28e0739dd7e5931c', '系统信息:导入', 'osInfo/import', 'portal', 'osInfo', '4', '16', null, 'system',
        '2020-04-23 15:45:52', null, null, null, null, '0', '1'),
       ('b0920dff144afb93d4684b265a1e', '角色信息:查看', 'role/get', 'portal', 'role', '0', '1', null, 'system',
        '2020-04-23 15:45:53', null, null, null, null, '0', '1'),
       ('b27b989d8641f9b6dc33ffdfa75a', '组织信息:新增', 'organization/insert', 'portal', 'organization', '1', '2', null,
        'system', '2020-04-23 15:45:52', null, null, null, null, '0', '1'),
       ('b4576746dd48784b74abc07cc17c', '系统信息:删除', 'osInfo/delete', 'portal', 'osInfo', '3', '8', null, 'system',
        '2020-04-23 15:45:52', null, null, null, null, '0', '1'),
       ('b8068dfdb441db63c3c52649c750', '菜单信息:新增', 'menu/insert', 'portal', 'menu', '1', '2', null, 'system',
        '2020-04-23 15:45:53', null, null, null, null, '0', '1'),
       ('ba1111a3114deb7ed2795aadb5a8', '组织信息:删除', 'organization/delete', 'portal', 'organization', '3', '8', null,
        'system', '2020-04-23 15:45:52', null, null, null, null, '0', '1'),
       ('ba451720ef4e5b1e685f27d1589d', 'oauth2客户端信息:查看', 'client/get', 'portal', 'client', '0', '1', null, 'system',
        '2020-04-23 15:45:52', null, null, null, null, '0', '1'),
       ('c543918f56469abdf06ab623061b', '系统信息:编辑', 'osInfo/update', 'portal', 'osInfo', '2', '4', null, 'system',
        '2020-04-23 15:45:52', null, null, null, null, '0', '1'),
       ('c8ff3040124c6959860a2656866e', '角色信息:删除', 'role/delete', 'portal', 'role', '3', '8', null, 'system',
        '2020-04-23 15:45:53', null, null, null, null, '0', '1'),
       ('ce74d4701d49d9856134754c3d8b', '用户信息:新增', 'user/insert', 'portal', 'user', '1', '2', null, 'system',
        '2020-04-23 15:45:52', null, null, null, null, '0', '1'),
       ('d0622dddbe46eb8aed056574b35e', '权限信息:导出', 'permission/export', 'portal', 'permission', '5', '32', null,
        'system', '2020-04-23 15:45:53', null, null, null, null, '0', '1'),
       ('d13c70ade4431b9635bac80e1a1b', '用户组信息:查看', 'group/get', 'portal', 'group', '0', '1', null, 'system',
        '2020-04-23 15:45:53', null, null, null, null, '0', '1'),
       ('d466a581e841bb476e7386710de4', '组织信息:导出', 'organization/export', 'portal', 'organization', '5', '32', null,
        'system', '2020-04-23 15:45:52', null, null, null, null, '0', '1'),
       ('d4b1d0c720476b30599c67808664', '系统信息:导出', 'osInfo/export', 'portal', 'osInfo', '5', '32', null, 'system',
        '2020-04-23 15:45:52', null, null, null, null, '0', '1'),
       ('d53adc33414b0a07e27ec48441c9', 'oauth2客户端信息:新增', 'client/insert', 'portal', 'client', '1', '2', null, 'system',
        '2020-04-23 15:45:52', null, null, null, null, '0', '1'),
       ('df8ca8930a4c8b1b0a54283e072a', '菜单信息:导出', 'menu/export', 'portal', 'menu', '5', '32', null, 'system',
        '2020-04-23 15:45:53', null, null, null, null, '0', '1'),
       ('eac651fb26497bdbf9920484f101', 'oauth2客户端信息:删除', 'client/delete', 'portal', 'client', '3', '8', null, 'system',
        '2020-04-23 15:45:52', null, null, null, null, '0', '1'),
       ('ed41c495f847d9e9c3f1bda01153', '组织信息:查看', 'organization/get', 'portal', 'organization', '0', '1', null,
        'system', '2020-04-23 15:45:52', null, null, null, null, '0', '1'),
       ('f46b3b996f4eb8e4e570b31b870a', '用户组信息:新增', 'group/insert', 'portal', 'group', '1', '2', null, 'system',
        '2020-04-23 15:45:53', null, null, null, null, '0', '1'),
       ('fb9cd08976433b8888686bbcca16', '组织信息:导入', 'organization/import', 'portal', 'organization', '4', '16', null,
        'system', '2020-04-23 15:45:52', null, null, null, null, '0', '1');
COMMIT;

-- ----------------------------
--  Table structure for `auth_role`
-- ----------------------------
DROP TABLE IF EXISTS `auth_role`;
CREATE TABLE `auth_role`
(
    `id`           varchar(32)  NOT NULL COMMENT '主键',
    `name`         varchar(100) NOT NULL COMMENT '角色名',
    `code`         varchar(50)  NOT NULL COMMENT '角色编码',
    `parent_code`  varchar(50)           DEFAULT NULL COMMENT '父角色编码',
    `level`        int(8)                DEFAULT NULL COMMENT '数据权限级别',
    `is_private`   tinyint(4)   NOT NULL DEFAULT '0' COMMENT '是否私有(0:否，1:是)',
    `created_by`   varchar(50)  NOT NULL DEFAULT 'system' COMMENT '创建人',
    `created_date` datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_by`   varchar(50)           DEFAULT NULL COMMENT '修改人',
    `updated_date` datetime              DEFAULT NULL COMMENT '修改时间',
    `deleted_by`   varchar(50)           DEFAULT NULL COMMENT '删除人',
    `deleted_date` datetime              DEFAULT NULL COMMENT '删除时间',
    `deleted_flag` varchar(32)  NOT NULL DEFAULT '0' COMMENT '删除标记(0:未删除，其他:已删除)',
    `locked`       tinyint(4)   NOT NULL DEFAULT '0' COMMENT '锁定标记(0:未锁定，1:已锁定)',
    `version`      int(8)       NOT NULL DEFAULT '1' COMMENT '版本号',
    `tenant_id`    int(11)      NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `t_sys_role_code_key` (`code`, `deleted_flag`),
    KEY `t_sys_role_deleted_flag_index` (`deleted_flag`),
    KEY `auth_role_tenant_id_index` (`tenant_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='角色信息';

-- ----------------------------
--  Records of `auth_role`
-- ----------------------------
BEGIN;
INSERT INTO `auth_role`
VALUES ('1deb69ad5e4d48dbcd08bbe5165a', '测试人员_PRIVATE_GROUP_PRIVATE_ROLE', 'test', null, null, '1', 'admin',
        '2020-04-14 09:21:08', null, null, null, null, '0', '0', '1', '0'),
       ('31390ed2714e2a3754af720f8259', '系统管理员', 'ROLE_ADMIN', null, null, '0', 'system', '2020-03-30 15:49:59', null,
        null, null, null, '0', '0', '1', '0'),
       ('7a5fdff4154b6b25192cef38c550', '测试组_PRIVATE_ROLE', 'GROUP_test', null, null, '1', 'admin',
        '2020-03-30 16:53:09', null, null, null, null, '0', '0', '1', '0'),
       ('9ad56a5d0746796b01041f616fb2', 'admin_PRIVATE_GROUP_PRIVATE_ROLE', 'admin', null, null, '1', 'system',
        '2020-03-30 15:49:59', null, null, null, null, '0', '0', '1', '0'),
       ('e6ad88cffe4d2be02f22afeeffb1', '测试组角色', 'ROLE_test', null, null, '0', 'admin', '2020-04-14 09:21:49', null,
        null, null, null, '0', '0', '1', '0'),
       ('fe4ff2c7a1414a865d57c0a2c83c', '系统管理员_PRIVATE_ROLE', 'GROUP_sys_admin', null, null, '1', 'admin',
        '2020-04-14 09:06:01', null, null, null, null, '0', '0', '1', '0');
COMMIT;

-- ----------------------------
--  Table structure for `auth_role_permission`
-- ----------------------------
DROP TABLE IF EXISTS `auth_role_permission`;
CREATE TABLE `auth_role_permission`
(
    `id`              varchar(32) NOT NULL COMMENT '主键',
    `role_code`       varchar(50) NOT NULL COMMENT '角色编码',
    `permission_code` varchar(50) NOT NULL COMMENT '权限编码',
    `os_code`         varchar(50) NOT NULL COMMENT '系统编码',
    `menu_code`       varchar(50) NOT NULL COMMENT '菜单编码',
    `created_by`      varchar(50) NOT NULL DEFAULT 'system' COMMENT '创建人',
    `created_date`    datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_by`      varchar(50)          DEFAULT NULL COMMENT '更新人',
    `updated_date`    datetime             DEFAULT NULL COMMENT '更新时间',
    `deleted_by`      varchar(50)          DEFAULT NULL COMMENT '删除人',
    `deleted_date`    datetime             DEFAULT NULL COMMENT '删除时间',
    `deleted_flag`    varchar(32)          DEFAULT '0' COMMENT '删除标记(0:未删除，其他:已删除)',
    `tenant_id`       int(11)     NOT NULL COMMENT '租户ID',
    PRIMARY KEY (`id`),
    KEY `t_sys_role_permission_deleted_flag_index` (`deleted_flag`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='组-权限关联表';

-- ----------------------------
--  Records of `auth_role_permission`
-- ----------------------------
BEGIN;
INSERT INTO `auth_role_permission`
VALUES ('016b445c934f8b221311562feaa8', 'ROLE_ADMIN', 'user/update', 'portal', 'user', 'admin', '2020-04-23 15:48:54',
        null, null, null, null, '0', '0'),
       ('06011117084bb92c13653e29d680', 'ROLE_ADMIN', 'osInfo/get', 'portal', 'osInfo', 'admin', '2020-04-23 15:48:41',
        null, null, null, null, '0', '0'),
       ('13e76fbed947390581b15e2319d7', 'ROLE_ADMIN', 'menu/export', 'portal', 'menu', 'admin', '2020-04-23 15:49:07',
        null, null, null, null, '0', '0'),
       ('1547a32b2a4bcb6cd8c33efa0049', 'ROLE_ADMIN', 'dict/info/delete', 'portal', 'dictInfo', 'admin',
        '2020-04-23 16:10:38', null, null, null, null, '0', '0'),
       ('18998d79594fea3eebfdf01baab5', 'ROLE_ADMIN', 'permission/get', 'portal', 'permission', 'admin',
        '2020-04-23 15:49:11', null, null, null, null, '0', '0'),
       ('245798e01c4458ab76beb6ce45d1', 'ROLE_ADMIN', 'menu/import', 'portal', 'menu', 'admin', '2020-04-23 15:49:07',
        null, null, null, null, '0', '0'),
       ('24cd8a83b84eeb5b674874a6ebaa', 'ROLE_ADMIN', 'permission/delete', 'portal', 'permission', 'admin',
        '2020-04-23 15:49:11', null, null, null, null, '0', '0'),
       ('2b4109b3184cdbe9bf80ef832b54', 'ROLE_ADMIN', 'user/export', 'portal', 'user', 'admin', '2020-04-23 15:48:54',
        null, null, null, null, '0', '0'),
       ('2e7c03ad7548b83c3b979b746928', 'ROLE_ADMIN', 'menu/get', 'portal', 'menu', 'admin', '2020-04-23 15:49:07',
        null, null, null, null, '0', '0'),
       ('39364ebcb5443bb6515472353209', 'ROLE_ADMIN', 'client/update', 'portal', 'client', 'admin',
        '2020-04-23 15:48:49', null, null, null, null, '0', '0'),
       ('45608c7e03445a9674db4d9b24ff', 'ROLE_ADMIN', 'permission/insert', 'portal', 'permission', 'admin',
        '2020-04-23 15:49:11', null, null, null, null, '0', '0'),
       ('45fa5cd4da4c18d4914ce2d0c50e', 'ROLE_ADMIN', 'role/import', 'portal', 'role', 'admin', '2020-04-23 15:49:02',
        null, null, null, null, '0', '0'),
       ('4703a9c2124e7b6bfb61ae4d9ce8', 'ROLE_ADMIN', 'organization/delete', 'portal', 'organization', 'admin',
        '2020-04-23 15:48:45', null, null, null, null, '0', '0'),
       ('4837d01fda44381fdbb8c58c3925', 'ROLE_ADMIN', 'dict/type/insert', 'portal', 'dictType', 'admin',
        '2020-04-23 16:10:34', null, null, null, null, '0', '0'),
       ('4abb773353444a11079efaec7599', 'ROLE_ADMIN', 'group/delete', 'portal', 'group', 'admin', '2020-04-23 15:48:58',
        null, null, null, null, '0', '0'),
       ('548f9296d64869a3e0c0207e5b86', 'ROLE_ADMIN', 'permission/export', 'portal', 'permission', 'admin',
        '2020-04-23 15:49:11', null, null, null, null, '0', '0'),
       ('594413dcb44d9834cd00c99b5b75', 'ROLE_ADMIN', 'role/delete', 'portal', 'role', 'admin', '2020-04-23 15:49:02',
        null, null, null, null, '0', '0'),
       ('5d0c6633774c8883e4258feee6bd', 'ROLE_ADMIN', 'menu/delete', 'portal', 'menu', 'admin', '2020-04-23 15:49:07',
        null, null, null, null, '0', '0'),
       ('67b25661fe43c8c5b780c0187efe', 'ROLE_ADMIN', 'dict/info/import', 'portal', 'dictInfo', 'admin',
        '2020-04-23 16:10:38', null, null, null, null, '0', '0'),
       ('67cb5ee36a4aea16eedfa16267c1', 'ROLE_ADMIN', 'dict/type/import', 'portal', 'dictType', 'admin',
        '2020-04-23 16:10:34', null, null, null, null, '0', '0'),
       ('68db55c469462a6977917b718ecc', 'ROLE_ADMIN', 'permission/update', 'portal', 'permission', 'admin',
        '2020-04-23 15:49:11', null, null, null, null, '0', '0'),
       ('6a917ccc794069b06b4120351352', 'ROLE_ADMIN', 'dict/info/insert', 'portal', 'dictInfo', 'admin',
        '2020-04-23 16:10:38', null, null, null, null, '0', '0'),
       ('6b6bd38d284dfbf8af6ced266444', 'ROLE_ADMIN', 'client/delete', 'portal', 'client', 'admin',
        '2020-04-23 15:48:49', null, null, null, null, '0', '0'),
       ('6b87e338c541dbf757a4630bedf5', 'ROLE_ADMIN', 'osInfo/delete', 'portal', 'osInfo', 'admin',
        '2020-04-23 15:48:41', null, null, null, null, '0', '0'),
       ('6cd74ea86349fb752fd2143303a4', 'ROLE_ADMIN', 'group/update', 'portal', 'group', 'admin', '2020-04-23 15:48:58',
        null, null, null, null, '0', '0'),
       ('6f258dc5a8435958fd1910ef24f1', 'ROLE_ADMIN', 'organization/update', 'portal', 'organization', 'admin',
        '2020-04-23 15:48:45', null, null, null, null, '0', '0'),
       ('700afbe64b477b2dbde406e1c801', 'ROLE_ADMIN', 'dict/info/get', 'portal', 'dictInfo', 'admin',
        '2020-04-23 16:10:38', null, null, null, null, '0', '0'),
       ('737222c5a44d1a503d9605ea1a49', 'ROLE_ADMIN', 'user/insert', 'portal', 'user', 'admin', '2020-04-23 15:48:54',
        null, null, null, null, '0', '0'),
       ('75452642e6487a31212aab0b54f8', 'ROLE_ADMIN', 'group/export', 'portal', 'group', 'admin', '2020-04-23 15:48:58',
        null, null, null, null, '0', '0'),
       ('782399c49d466b781eea847a8a6f', 'ROLE_ADMIN', 'dict/type/delete', 'portal', 'dictType', 'admin',
        '2020-04-23 16:10:34', null, null, null, null, '0', '0'),
       ('7cb754ab7c462bc40c7355dcbaa5', 'ROLE_ADMIN', 'client/insert', 'portal', 'client', 'admin',
        '2020-04-23 15:48:49', null, null, null, null, '0', '0'),
       ('7e6051809f481ba0e9e05682c4e9', 'ROLE_ADMIN', 'client/get', 'portal', 'client', 'admin', '2020-04-23 15:48:49',
        null, null, null, null, '0', '0'),
       ('8052d51aed484b360613d560a0d7', 'ROLE_ADMIN', 'organization/import', 'portal', 'organization', 'admin',
        '2020-04-23 15:48:45', null, null, null, null, '0', '0'),
       ('817f6748814aabe166bf669e864b', 'ROLE_ADMIN', 'role/update', 'portal', 'role', 'admin', '2020-04-23 15:49:02',
        null, null, null, null, '0', '0'),
       ('82c88579084a6baecb8622da5d9a', 'ROLE_ADMIN', 'user/get', 'portal', 'user', 'admin', '2020-04-23 15:48:54',
        null, null, null, null, '0', '0'),
       ('838a5837d0424854cef50c0b641e', 'ROLE_ADMIN', 'dict/type/get', 'portal', 'dictType', 'admin',
        '2020-04-23 16:10:34', null, null, null, null, '0', '0'),
       ('887eb02eec4baab177d337326a1c', 'ROLE_ADMIN', 'role/get', 'portal', 'role', 'admin', '2020-04-23 15:49:02',
        null, null, null, null, '0', '0'),
       ('8c18dec33e46c95f63337c5857d5', 'ROLE_ADMIN', 'user/delete', 'portal', 'user', 'admin', '2020-04-23 15:48:54',
        null, null, null, null, '0', '0'),
       ('8e20cd7f68412b4a30c9ed68d41b', 'ROLE_ADMIN', 'dict/info/update', 'portal', 'dictInfo', 'admin',
        '2020-04-23 16:10:38', null, null, null, null, '0', '0'),
       ('8eb32f059c4a4b965bb7e6d910ae', 'ROLE_ADMIN', 'permission/import', 'portal', 'permission', 'admin',
        '2020-04-23 15:49:11', null, null, null, null, '0', '0'),
       ('91aebf912a4caa7216a72057a755', 'ROLE_ADMIN', 'osInfo/insert', 'portal', 'osInfo', 'admin',
        '2020-04-23 15:48:41', null, null, null, null, '0', '0'),
       ('944f828e424bbbb424d7d6eb9276', 'ROLE_ADMIN', 'osInfo/update', 'portal', 'osInfo', 'admin',
        '2020-04-23 15:48:41', null, null, null, null, '0', '0'),
       ('9782aa71b142093fd68b83c1943b', 'ROLE_ADMIN', 'menu/insert', 'portal', 'menu', 'admin', '2020-04-23 15:49:07',
        null, null, null, null, '0', '0'),
       ('98cad5e4d9438969e55fa272eee2', 'ROLE_ADMIN', 'group/insert', 'portal', 'group', 'admin', '2020-04-23 15:48:58',
        null, null, null, null, '0', '0'),
       ('ae2e755e014b8b5211ad9592b1d0', 'ROLE_ADMIN', 'organization/get', 'portal', 'organization', 'admin',
        '2020-04-23 15:48:45', null, null, null, null, '0', '0'),
       ('ae7f1d822445b8e412e708931aed', 'ROLE_ADMIN', 'dict/type/export', 'portal', 'dictType', 'admin',
        '2020-04-23 16:10:34', null, null, null, null, '0', '0'),
       ('b89d888fef405bb93581d0a6074d', 'ROLE_ADMIN', 'organization/insert', 'portal', 'organization', 'admin',
        '2020-04-23 15:48:45', null, null, null, null, '0', '0'),
       ('bbd817158b41cbe1e85c03e1b202', 'ROLE_ADMIN', 'role/insert', 'portal', 'role', 'admin', '2020-04-23 15:49:02',
        null, null, null, null, '0', '0'),
       ('bc0e53d9764d2b3fbac288e97838', 'ROLE_ADMIN', 'dict/info/export', 'portal', 'dictInfo', 'admin',
        '2020-04-23 16:10:38', null, null, null, null, '0', '0'),
       ('bf8609e19146d8dfb6b1da539b1a', 'ROLE_ADMIN', 'group/get', 'portal', 'group', 'admin', '2020-04-23 15:48:58',
        null, null, null, null, '0', '0'),
       ('c8f95ac92c4bf8590ceb484269b1', 'ROLE_ADMIN', 'role/export', 'portal', 'role', 'admin', '2020-04-23 15:49:02',
        null, null, null, null, '0', '0'),
       ('ce40c164a34a2a8508e9b1d8ab5b', 'ROLE_ADMIN', 'dict/type/update', 'portal', 'dictType', 'admin',
        '2020-04-23 16:10:34', null, null, null, null, '0', '0'),
       ('ceea1f62f5477be6fa51ebb67bbe', 'ROLE_ADMIN', 'organization/export', 'portal', 'organization', 'admin',
        '2020-04-23 15:48:45', null, null, null, null, '0', '0'),
       ('d1d277f41e4059648b5e69bc1d5a', 'ROLE_ADMIN', 'group/import', 'portal', 'group', 'admin', '2020-04-23 15:48:58',
        null, null, null, null, '0', '0'),
       ('d680a0526b4b6a84a10a1cc60acc', 'ROLE_ADMIN', 'client/import', 'portal', 'client', 'admin',
        '2020-04-23 15:48:49', null, null, null, null, '0', '0'),
       ('d98b92b38a488a4a0c2720198f40', 'ROLE_ADMIN', 'osInfo/import', 'portal', 'osInfo', 'admin',
        '2020-04-23 15:48:41', null, null, null, null, '0', '0'),
       ('eb1949188d4fe8eef4455ad44f8a', 'ROLE_ADMIN', 'user/import', 'portal', 'user', 'admin', '2020-04-23 15:48:54',
        null, null, null, null, '0', '0'),
       ('eb5004558c497b030d3bdc1f2b26', 'ROLE_ADMIN', 'client/export', 'portal', 'client', 'admin',
        '2020-04-23 15:48:49', null, null, null, null, '0', '0'),
       ('f94f937d9a4a49fa1c741786abd5', 'ROLE_ADMIN', 'menu/update', 'portal', 'menu', 'admin', '2020-04-23 15:49:07',
        null, null, null, null, '0', '0'),
       ('fad4e7e9db4508be5f944eb04612', 'ROLE_ADMIN', 'osInfo/export', 'portal', 'osInfo', 'admin',
        '2020-04-23 15:48:41', null, null, null, null, '0', '0'),
       ('fe719723b940c9d5b9f2ff476b49', 'ROLE_ADMIN', 'user/joinOrg', 'portal', 'user', 'admin', '2020-06-04 10:03:15',
        null, null, null, null, '0', '0');
COMMIT;

-- ----------------------------
--  Table structure for `auth_user`
-- ----------------------------
DROP TABLE IF EXISTS `auth_user`;
CREATE TABLE `auth_user`
(
    `id`           varchar(32) NOT NULL COMMENT '主键',
    `name`         varchar(50) NOT NULL COMMENT '姓名',
    `code`         varchar(36) NOT NULL COMMENT '用户编码',
    `login_name`   varchar(50) NOT NULL COMMENT '登录名',
    `password`     varchar(60) NOT NULL COMMENT '密码',
    `gender`       tinyint(4)  NOT NULL DEFAULT '0' COMMENT '性别(0:男，1:女)',
    `user_type`    varchar(10) NOT NULL DEFAULT '0' COMMENT '用户类型',
    `email`        varchar(100)         DEFAULT NULL COMMENT '邮箱',
    `phone`        varchar(20) NOT NULL COMMENT '电话号码',
    `created_by`   varchar(50) NOT NULL DEFAULT 'system' COMMENT '创建人',
    `created_date` datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_by`   varchar(50)          DEFAULT NULL COMMENT '修改人',
    `updated_date` datetime             DEFAULT NULL COMMENT '修改时间',
    `deleted_by`   varchar(50)          DEFAULT NULL COMMENT '删除人',
    `deleted_date` datetime             DEFAULT NULL COMMENT '删除时间',
    `deleted_flag` varchar(32) NOT NULL DEFAULT '0' COMMENT '删除标记(0:未删除，其他:已删除)',
    `locked`       tinyint(4)  NOT NULL DEFAULT '0' COMMENT '锁定标记(0:未锁定，1:已锁定)',
    `expire_at`    timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '过期时间',
    `version`      int(8)      NOT NULL DEFAULT '1' COMMENT '版本号',
    `tenant_id`    int(11)     NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `t_sys_user_code_key` (`code`, `deleted_flag`),
    UNIQUE KEY `t_sys_user_login_name_uindex` (`login_name`, `deleted_flag`),
    KEY `t_sys_user_version_index` (`version`),
    KEY `t_sys_user_phone_index` (`phone`),
    KEY `t_sys_user_deleted_flag_index` (`deleted_flag`),
    KEY `auth_user_tenant_id_index` (`tenant_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='用户信息';

-- ----------------------------
--  Records of `auth_user`
-- ----------------------------
BEGIN;
INSERT INTO `auth_user`
VALUES ('62aa7f996443bb8b484cab8910dc', '测试人员', 'test', 'test',
        '$2a$10$89UJRZ6A.ubPmT9MrN6iEePGKdmW2N2b8tIe3Ng1MAVaTfRB2gTKC', '2', '0', null, '15000000000', 'admin',
        '2020-04-14 09:21:08', null, null, null, null, '0', '0', '2020-10-14 09:21:08', '1', '0'),
       ('8161b2215f49e91e62687e7648da', 'admin', 'admin', 'admin',
        '$2a$10$7Saa7QlgRxnq6e6FGTxyJeKdWDGGUXBv/SXAlNjXJmq19/hxkBmra', '0', '0', null, '15000000000', 'system',
        '2020-03-30 15:49:59', null, null, null, null, '0', '0', '2021-03-30 15:49:59', '1', '0');
COMMIT;

-- ----------------------------
--  Table structure for `auth_user_group`
-- ----------------------------
DROP TABLE IF EXISTS `auth_user_group`;
CREATE TABLE `auth_user_group`
(
    `id`           varchar(32) NOT NULL COMMENT '主键',
    `user_code`    varchar(36) NOT NULL COMMENT '用户编码',
    `group_code`   varchar(42) NOT NULL COMMENT '组编码',
    `created_by`   varchar(50) NOT NULL DEFAULT 'system' COMMENT '创建人',
    `created_date` datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_by`   varchar(50)          DEFAULT NULL COMMENT '修改人',
    `updated_date` datetime             DEFAULT NULL COMMENT '修改时间',
    `deleted_by`   varchar(50)          DEFAULT NULL COMMENT '删除人',
    `deleted_date` datetime             DEFAULT NULL COMMENT '删除时间',
    `deleted_flag` varchar(32) NOT NULL DEFAULT '0' COMMENT '删除标记(0:未删除，其他:已删除)',
    `is_private`   tinyint(4)  NOT NULL DEFAULT '0' COMMENT '是否私有',
    `tenant_id`    int(11)     NOT NULL,
    PRIMARY KEY (`id`),
    KEY `t_sys_user_group_deleted_flag_index` (`deleted_flag`),
    KEY `auth_user_group_tenant_id_index` (`tenant_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='用户-组关联表';

-- ----------------------------
--  Records of `auth_user_group`
-- ----------------------------
BEGIN;
INSERT INTO `auth_user_group`
VALUES ('27534e427d46c9ef7ce11423b9b8', 'test', 'test', 'admin', '2020-04-14 09:21:08', null, null, null, null, '0',
        '1', '0'),
       ('d9c0373c664abb3c402e76a4e94f', 'admin', 'admin', 'system', '2020-03-30 15:49:59', null, null, null, null, '0',
        '1', '0'),
       ('f04cb1ea1141688d5b214bf0dc11', 'admin', 'GROUP_sys_admin', 'admin', '2020-04-20 15:58:23', null, null, null,
        null, '0', '0', '0');
COMMIT;

-- ----------------------------
--  Table structure for `auth_user_org`
-- ----------------------------
DROP TABLE IF EXISTS `auth_user_org`;
CREATE TABLE `auth_user_org`
(
    `id`           varchar(32) NOT NULL COMMENT '主键',
    `user_code`    varchar(36) NOT NULL COMMENT '用户编码',
    `org_code`     varchar(50) NOT NULL COMMENT '组织编码',
    `created_by`   varchar(50) NOT NULL DEFAULT 'system' COMMENT '创建人',
    `created_date` datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_by`   varchar(50)          DEFAULT NULL COMMENT '修改人',
    `updated_date` datetime             DEFAULT NULL COMMENT '修改时间',
    `deleted_by`   varchar(50)          DEFAULT NULL COMMENT '删除人',
    `deleted_date` datetime             DEFAULT NULL COMMENT '删除时间',
    `deleted_flag` varchar(32) NOT NULL DEFAULT '0' COMMENT '删除标记(0:未删除，其他:已删除)',
    `tenant_id`    int(11)     NOT NULL,
    PRIMARY KEY (`id`),
    KEY `t_sys_user_org_deleted_flag_index` (`deleted_flag`),
    KEY `auth_user_org_tenant_id_index` (`tenant_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='用户-组织关联表';

SET FOREIGN_KEY_CHECKS = 1;
