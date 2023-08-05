/*
 Navicat Premium Data Transfer

 Source Server         : 初柒MySQL8.0
 Source Server Type    : MySQL
 Source Server Version : 80033 (8.0.33)
 Source Host           : 121.62.61.121:3308
 Source Schema         : yun

 Target Server Type    : MySQL
 Target Server Version : 80033 (8.0.33)
 File Encoding         : 65001

 Date: 05/08/2023 22:51:13
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '管理员ID',
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '管理员用户',
  `pwd` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '管理员密码',
  `mail` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '管理员邮箱',
  `role` tinyint(1) NOT NULL COMMENT '管理员角色',
  `create_time` datetime NOT NULL COMMENT '管理员创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=10003 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='管理员信息表';

-- ----------------------------
-- Table structure for blessing
-- ----------------------------
DROP TABLE IF EXISTS `blessing`;
CREATE TABLE `blessing` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '祝福ID',
  `category_id` int NOT NULL COMMENT '祝福分类ID',
  `content` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '祝福内容',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `category_id` (`category_id`) USING BTREE,
  CONSTRAINT `blessing_ibfk_1` FOREIGN KEY (`category_id`) REFERENCES `blessing_category` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='愿望祝福表';

-- ----------------------------
-- Table structure for blessing_category
-- ----------------------------
DROP TABLE IF EXISTS `blessing_category`;
CREATE TABLE `blessing_category` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '祝福分类ID',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '祝福分类名称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='祝福分类表';

-- ----------------------------
-- Table structure for blessing_to_wish
-- ----------------------------
DROP TABLE IF EXISTS `blessing_to_wish`;
CREATE TABLE `blessing_to_wish` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '祝福语ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `blessing` tinyint NOT NULL COMMENT '祝福语',
  `wish_id` bigint NOT NULL COMMENT '愿望ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `is_filter` tinyint(1) NOT NULL COMMENT '是否过滤',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='祝福语录表';

-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '评论ID',
  `parent_id` bigint NOT NULL DEFAULT '0' COMMENT '父级评论ID',
  `user_id` bigint NOT NULL COMMENT '评论用户ID',
  `mail_or_letter_id` bigint NOT NULL COMMENT '评论对应邮件或者信件',
  `for_type` tinyint NOT NULL COMMENT '评论对应表[mail：0，letter：1]',
  `level` tinyint(1) NOT NULL DEFAULT '0' COMMENT '评论深度',
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '评论内容',
  `create_time` datetime NOT NULL COMMENT '评论时间',
  `is_filter` tinyint(1) NOT NULL COMMENT '是否过滤',
  `is_sensitive` tinyint(1) NOT NULL COMMENT '是否敏感',
  `ai_check_msg` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT 'AI审核消息',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_parent_id` (`parent_id`) USING BTREE,
  KEY `idx_user_id` (`user_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1022 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='邮件与信件评论表';

-- ----------------------------
-- Table structure for id_card_auth
-- ----------------------------
DROP TABLE IF EXISTS `id_card_auth`;
CREATE TABLE `id_card_auth` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '实名认证ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `id_card` varchar(18) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '身份证号',
  `phone` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '手机号',
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '姓名',
  `other` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '其他证明身份图片[JSON]',
  `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '实名认证状态',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='实名认证表';

-- ----------------------------
-- Table structure for issues
-- ----------------------------
DROP TABLE IF EXISTS `issues`;
CREATE TABLE `issues` (
  `id` int NOT NULL AUTO_INCREMENT,
  `issue` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=157 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='问题表';

-- ----------------------------
-- Table structure for letter
-- ----------------------------
DROP TABLE IF EXISTS `letter`;
CREATE TABLE `letter` (
  `letter_id` bigint NOT NULL AUTO_INCREMENT COMMENT '信件唯一ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `letter_title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '信件标题',
  `letter_content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '信件内容',
  `letter_create_time` datetime NOT NULL COMMENT '信件创建时间',
  `is_public` tinyint(1) NOT NULL COMMENT '信件是否公开',
  `go_to_time` date NOT NULL COMMENT '信件发送时间',
  `is_yourself` tinyint NOT NULL COMMENT '信件是否发给自己',
  `address` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '信件发往地址',
  `addressee` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '信件收件人姓名',
  `phone` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '信件收信人手机号',
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '信件备注',
  `letter_type` tinyint(1) NOT NULL COMMENT '信件类型',
  `state` tinyint(1) NOT NULL COMMENT '信件状态',
  `ai_check_msg` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT 'AI审核消息',
  PRIMARY KEY (`letter_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=10012 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='时光信件表';

-- ----------------------------
-- Table structure for letter_orders
-- ----------------------------
DROP TABLE IF EXISTS `letter_orders`;
CREATE TABLE `letter_orders` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '订单ID',
  `orders_serial` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '订单编号',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `date` datetime NOT NULL COMMENT '订单时间',
  `pay_time` datetime DEFAULT NULL COMMENT '支付时间',
  `pay_type` tinyint(1) NOT NULL COMMENT '支付类型',
  `amount` decimal(10,2) DEFAULT NULL COMMENT '支付金额',
  `trade_no` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '支付商订单号',
  `state` tinyint(1) NOT NULL COMMENT '支付状态',
  `letter_id` bigint NOT NULL COMMENT '信件ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='信件订单表';

-- ----------------------------
-- Table structure for letter_type
-- ----------------------------
DROP TABLE IF EXISTS `letter_type`;
CREATE TABLE `letter_type` (
  `id` tinyint NOT NULL AUTO_INCREMENT COMMENT '信件类型ID',
  `name` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '信件类型名称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='信件类型表';

-- ----------------------------
-- Table structure for logistics
-- ----------------------------
DROP TABLE IF EXISTS `logistics`;
CREATE TABLE `logistics` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '物流ID',
  `letter_id` bigint NOT NULL COMMENT '信件ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `logistics_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '物流单号',
  `logistics_code` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '物流商户[''SF'',''YZ'',''YT''...]',
  `status` tinyint(1) NOT NULL COMMENT '物流状态',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='信件物流表';

-- ----------------------------
-- Table structure for mail
-- ----------------------------
DROP TABLE IF EXISTS `mail`;
CREATE TABLE `mail` (
  `mail_id` bigint NOT NULL AUTO_INCREMENT COMMENT '邮件唯一ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `mail_title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '邮件标题',
  `mail_content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '邮件内容',
  `mail_create_time` datetime NOT NULL COMMENT '邮件创建时间',
  `go_to` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '发往地址',
  `is_public` tinyint(1) NOT NULL COMMENT '邮件是否公开',
  `go_to_time` datetime NOT NULL COMMENT '邮件发送时间',
  `is_yourself` tinyint NOT NULL COMMENT '邮件是否发给自己',
  `use_serve` tinyint NOT NULL DEFAULT '0' COMMENT '邮件发送使用的服务',
  `state` tinyint(1) NOT NULL DEFAULT '0' COMMENT '邮件发送状态',
  `ai_check_msg` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT 'AI审核消息',
  PRIMARY KEY (`mail_id`) USING BTREE,
  KEY `user_for_mail` (`user_id`) USING BTREE COMMENT '邮件表用户索引'
) ENGINE=InnoDB AUTO_INCREMENT=10006 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='邮件任务表';

-- ----------------------------
-- Table structure for message
-- ----------------------------
DROP TABLE IF EXISTS `message`;
CREATE TABLE `message` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '短信ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `text` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '短信内容',
  `send_time` date NOT NULL COMMENT '短信发送时间',
  `create_time` datetime NOT NULL COMMENT '短信创建时间',
  `is_unnamed` tinyint(1) NOT NULL COMMENT '短信是否匿名',
  `phone` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '对方手机号',
  `state` tinyint(1) NOT NULL COMMENT '短信状态',
  `ai_check_msg` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT 'AI审核消息',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=10001 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='短信任务表';

-- ----------------------------
-- Table structure for message_orders
-- ----------------------------
DROP TABLE IF EXISTS `message_orders`;
CREATE TABLE `message_orders` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '订单ID',
  `orders_serial` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '订单编号',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `date` datetime NOT NULL COMMENT '订单时间',
  `pay_time` datetime DEFAULT NULL COMMENT '支付时间',
  `pay_type` tinyint(1) NOT NULL COMMENT '支付类型',
  `amount` decimal(10,2) DEFAULT NULL COMMENT '支付金额',
  `trade_no` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '支付商订单号',
  `state` tinyint(1) NOT NULL COMMENT '支付状态',
  `message_id` bigint NOT NULL COMMENT '短信ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='信件订单表';

-- ----------------------------
-- Table structure for oauth
-- ----------------------------
DROP TABLE IF EXISTS `oauth`;
CREATE TABLE `oauth` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '第三方授权表ID',
  `user_id` bigint NOT NULL COMMENT '第三方授权绑定的用户ID',
  `provider` tinyint(1) NOT NULL COMMENT '第三方授权类型',
  `open_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '第三方授权唯一标识',
  `access_token` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '第三方授权Token',
  `refresh_token` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '第三方授权长Token',
  `token_expiry` int DEFAULT NULL COMMENT '第三方授权Token时长',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '第三方授权创建时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '第三方授权更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `user_provider_unique` (`user_id`) USING BTREE COMMENT '用户ID唯一索引',
  KEY `open_id_index` (`open_id`) USING BTREE COMMENT '授权唯一ID索引'
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='用户第三方授权表';

-- ----------------------------
-- Table structure for perm
-- ----------------------------
DROP TABLE IF EXISTS `perm`;
CREATE TABLE `perm` (
  `id` tinyint NOT NULL AUTO_INCREMENT COMMENT '权限ID',
  `permissions` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '权限路径',
  `desc` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '权限描述',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='权限表';

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` tinyint NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色名称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='角色表';

-- ----------------------------
-- Table structure for role_to_prem
-- ----------------------------
DROP TABLE IF EXISTS `role_to_prem`;
CREATE TABLE `role_to_prem` (
  `id` int NOT NULL COMMENT '角色与权限中间表ID',
  `role_id` tinyint NOT NULL COMMENT '角色ID',
  `prem_id` tinyint NOT NULL COMMENT '权限ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='角色与权限中间表';

-- ----------------------------
-- Table structure for sponsor
-- ----------------------------
DROP TABLE IF EXISTS `sponsor`;
CREATE TABLE `sponsor` (
  `id` bigint NOT NULL COMMENT '赞助表ID',
  `user_id` bigint NOT NULL COMMENT '赞助用户',
  `sponsor_say` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '呀~这位好心人居然没有留言！' COMMENT '赞助留言',
  `sponsor_amount` decimal(10,2) NOT NULL COMMENT '赞助金额',
  `pay_type` tinyint(1) NOT NULL COMMENT '支付类型',
  `trade_no` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '支付商订单号',
  `create_time` datetime NOT NULL COMMENT '赞助创建时间',
  `pay_time` datetime DEFAULT NULL COMMENT '支付时间',
  `state` tinyint(1) NOT NULL COMMENT '支付状态',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='赞助信息表';

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint NOT NULL COMMENT '用户自增ID',
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户账户',
  `nick_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户昵称',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'https://a.ideaopen.cn/JanYork/YPfetj0a.png' COMMENT '用户头像',
  `email` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '用户邮箱',
  `phone` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '用户手机',
  `pwd` varchar(150) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户密码',
  `auth_real_name` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否实名认证',
  `create_time` datetime NOT NULL COMMENT '用户创建时间',
  `id_card_auth_id` int DEFAULT NULL COMMENT '实名信息ID',
  `state` tinyint(1) NOT NULL DEFAULT '1' COMMENT '用户状态',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `user_for_name` (`name`) USING BTREE COMMENT '用户账户索引',
  UNIQUE KEY `user_for_email` (`email`) USING BTREE COMMENT '用户邮箱索引',
  UNIQUE KEY `user_for_phone` (`phone`) USING BTREE COMMENT '用户手机号索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='云寄用户表';

-- ----------------------------
-- Table structure for user_to_prem
-- ----------------------------
DROP TABLE IF EXISTS `user_to_prem`;
CREATE TABLE `user_to_prem` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '用户与权限中间表ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `prem_id` tinyint NOT NULL COMMENT '权限ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='用户与权限中间表';

-- ----------------------------
-- Table structure for user_to_role
-- ----------------------------
DROP TABLE IF EXISTS `user_to_role`;
CREATE TABLE `user_to_role` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '用户与角色中间表ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `role_id` tinyint NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='用户与角色中间表';

-- ----------------------------
-- Table structure for wish
-- ----------------------------
DROP TABLE IF EXISTS `wish`;
CREATE TABLE `wish` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '愿望ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `text` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '愿望',
  `image` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '图片',
  `creat_time` datetime NOT NULL COMMENT '创建时间',
  `state` tinyint(1) NOT NULL COMMENT '心愿状态',
  `ai_check_msg` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT 'AI审核消息',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1004 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='许愿表';

-- ----------------------------
-- Table structure for wish_orders
-- ----------------------------
DROP TABLE IF EXISTS `wish_orders`;
CREATE TABLE `wish_orders` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '订单ID',
  `orders_serial` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '订单编号',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `date` datetime NOT NULL COMMENT '订单时间',
  `pay_time` datetime DEFAULT NULL COMMENT '支付时间',
  `pay_type` tinyint(1) NOT NULL COMMENT '支付类型',
  `amount` decimal(10,2) DEFAULT NULL COMMENT '支付金额',
  `trade_no` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '支付商订单号',
  `state` tinyint(1) NOT NULL COMMENT '支付状态',
  `wish_id` bigint NOT NULL COMMENT '心愿ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='信件订单表';

SET FOREIGN_KEY_CHECKS = 1;
