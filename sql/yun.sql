/*
 Navicat Premium Data Transfer

 Source Server         : 127.0.0.1
 Source Server Type    : MySQL
 Source Server Version : 80012 (8.0.12)
 Source Host           : localhost:3306
 Source Schema         : yun

 Target Server Type    : MySQL
 Target Server Version : 80012 (8.0.12)
 File Encoding         : 65001

 Date: 09/06/2023 13:17:10
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '管理员ID',
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '管理员用户',
  `pwd` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '管理员密码',
  `mail` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '管理员邮箱',
  `role` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'admin' COMMENT '管理员权限级别',
  `create_time` datetime NOT NULL COMMENT '管理员创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10003 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '管理员信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES (10001, 'JanYork', '$2a$10$8SSN2hpmwyKxgg1NuhJcFOP84dgbiFilVX9yff0r/Sf7iJ4QGz.DS', '747945307@qq.com', 'admin', '2023-04-25 16:22:16');

-- ----------------------------
-- Table structure for blessing
-- ----------------------------
DROP TABLE IF EXISTS `blessing`;
CREATE TABLE `blessing`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '祝福ID',
  `category_id` int(11) NOT NULL COMMENT '祝福分类ID',
  `content` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '祝福内容',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `category_id`(`category_id` ASC) USING BTREE,
  CONSTRAINT `blessing_ibfk_1` FOREIGN KEY (`category_id`) REFERENCES `blessing_category` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '愿望祝福表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of blessing
-- ----------------------------
INSERT INTO `blessing` VALUES (1, 1, '生日快乐！祝你岁岁平安，年年有余。');
INSERT INTO `blessing` VALUES (2, 1, '愿你在新的一岁里，事业更上一层楼，生活更加美好！');
INSERT INTO `blessing` VALUES (3, 2, '爱你一万年，直到永远！');
INSERT INTO `blessing` VALUES (4, 2, '你是我生命中的唯一，我的爱人。');
INSERT INTO `blessing` VALUES (5, 3, '祝您在事业上蒸蒸日上，越来越好！');
INSERT INTO `blessing` VALUES (6, 3, '加油，明天会更好！');
INSERT INTO `blessing` VALUES (7, 4, '新年快乐！祝你财源滚滚，万事如意！');

-- ----------------------------
-- Table structure for blessing_category
-- ----------------------------
DROP TABLE IF EXISTS `blessing_category`;
CREATE TABLE `blessing_category`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '祝福分类ID',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '祝福分类名称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '祝福分类表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of blessing_category
-- ----------------------------
INSERT INTO `blessing_category` VALUES (1, '生日');
INSERT INTO `blessing_category` VALUES (2, '爱情');
INSERT INTO `blessing_category` VALUES (3, '事业');
INSERT INTO `blessing_category` VALUES (4, '新年');

-- ----------------------------
-- Table structure for blessing_to_wish
-- ----------------------------
DROP TABLE IF EXISTS `blessing_to_wish`;
CREATE TABLE `blessing_to_wish`  (
  `id` int(11) NOT NULL COMMENT '祝福语ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `blessing` tinyint(4) NOT NULL COMMENT '祝福语',
  `wish_id` bigint(20) NOT NULL COMMENT '愿望ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `is_filter` tinyint(1) NOT NULL COMMENT '是否过滤',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '祝福语录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of blessing_to_wish
-- ----------------------------

-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '评论ID',
  `parent_id` bigint(20) NULL DEFAULT NULL COMMENT '父级评论ID',
  `user_id` bigint(20) NOT NULL COMMENT '评论用户ID',
  `mail_or_letter_id` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '评论对应邮件或者信件',
  `for_type` tinyint(4) NOT NULL COMMENT '评论对应表[mail：0，letter：1]',
  `level` tinyint(1) NOT NULL DEFAULT 0 COMMENT '评论深度',
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '评论内容',
  `create_time` datetime NOT NULL COMMENT '评论时间',
  `is_filter` tinyint(1) NOT NULL COMMENT '是否过滤',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_parent_id`(`parent_id` ASC) USING BTREE,
  INDEX `idx_user_id`(`user_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1008 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '邮件与信件评论表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of comment
-- ----------------------------
INSERT INTO `comment` VALUES (1002, 0, 1640385966864732160, 'mail1680013311822', 0, 0, '111111111', '2023-04-23 15:34:23', 0);
INSERT INTO `comment` VALUES (1003, 1002, 1640385966864732160, 'mail1680013311822', 0, 1, '2222222222', '2023-04-23 15:36:34', 0);
INSERT INTO `comment` VALUES (1004, 1003, 1640385966864732160, 'mail1680013311822', 0, 2, '3333333333', '2023-04-23 15:36:53', 0);
INSERT INTO `comment` VALUES (1005, 0, 1646356165694722048, 'mail1680013311822', 0, 0, '5555555555', '2023-04-23 17:48:46', 0);
INSERT INTO `comment` VALUES (1006, 1005, 1646356165694722048, 'mail1680013311822', 0, 1, '666666666666', '2023-04-23 17:49:41', 0);
INSERT INTO `comment` VALUES (1007, 1005, 1646356165694722048, 'mail1680013311822', 0, 1, '77777777777', '2023-04-23 17:50:26', 0);

-- ----------------------------
-- Table structure for goods
-- ----------------------------
DROP TABLE IF EXISTS `goods`;
CREATE TABLE `goods`  (
  `id` tinyint(4) NOT NULL AUTO_INCREMENT COMMENT '商品ID',
  `goods_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '商品名称',
  `goods_price` decimal(10, 2) NOT NULL COMMENT '商品价格',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '全局商品表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of goods
-- ----------------------------
INSERT INTO `goods` VALUES (1, '赞助', 5.00);
INSERT INTO `goods` VALUES (2, '信件', 10.00);

-- ----------------------------
-- Table structure for id_card_auth
-- ----------------------------
DROP TABLE IF EXISTS `id_card_auth`;
CREATE TABLE `id_card_auth`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '实名认证ID',
  `id_card` varchar(18) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '身份证号',
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '姓名',
  `other` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '其他证明身份图片[JSON]',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '实名认证表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of id_card_auth
-- ----------------------------

-- ----------------------------
-- Table structure for letter
-- ----------------------------
DROP TABLE IF EXISTS `letter`;
CREATE TABLE `letter`  (
  `letter_id` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '信件唯一ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `letter_title` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '信件标题',
  `letter_content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '信件内容',
  `letter_create_time` datetime NOT NULL COMMENT '信件创建时间',
  `is_public` tinyint(1) NOT NULL COMMENT '信件是否公开',
  `go_to_time` datetime NOT NULL COMMENT '信件发送时间',
  `is_yourself` tinyint(4) NOT NULL COMMENT '信件是否发给自己',
  `address` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '信件发往地址',
  `addressee` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '信件收件人姓名',
  `phone` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '信件收信人手机号',
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '信件备注',
  `state` tinyint(1) NOT NULL COMMENT '信件发送状态',
  `letter_type` tinyint(1) NOT NULL COMMENT '信件类型',
  PRIMARY KEY (`letter_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '时光信件表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of letter
-- ----------------------------

-- ----------------------------
-- Table structure for letter_type
-- ----------------------------
DROP TABLE IF EXISTS `letter_type`;
CREATE TABLE `letter_type`  (
  `id` tinyint(4) NOT NULL AUTO_INCREMENT COMMENT '信件类型ID',
  `name` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '信件类型名称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '信件类型表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of letter_type
-- ----------------------------
INSERT INTO `letter_type` VALUES (1, '日常信件');
INSERT INTO `letter_type` VALUES (2, '生日信件');
INSERT INTO `letter_type` VALUES (3, '表白信件');
INSERT INTO `letter_type` VALUES (4, '祝福信件');
INSERT INTO `letter_type` VALUES (5, '亲属信件');

-- ----------------------------
-- Table structure for logistics
-- ----------------------------
DROP TABLE IF EXISTS `logistics`;
CREATE TABLE `logistics`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '物流ID',
  `letter_id` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '信件ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `logistics_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '物流单号',
  `logistics_code` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '物流商户[\'SF\',\'YZ\',\'YT\'...]',
  `status` tinyint(1) NOT NULL COMMENT '物流状态',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2075340802 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '信件物流表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of logistics
-- ----------------------------
INSERT INTO `logistics` VALUES (2075340801, '32532523534534', '2023-05-12 17:06:19', '2023-05-12 17:06:19', '773219955004555', 'STO', 0);

-- ----------------------------
-- Table structure for mail
-- ----------------------------
DROP TABLE IF EXISTS `mail`;
CREATE TABLE `mail`  (
  `mail_id` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '邮件唯一ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `mail_title` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '邮件标题',
  `mail_content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '邮件内容',
  `mail_create_time` datetime NOT NULL COMMENT '邮件创建时间',
  `go_to` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '发往地址',
  `is_public` tinyint(1) NOT NULL COMMENT '邮件是否公开',
  `go_to_time` datetime NOT NULL COMMENT '邮件发送时间',
  `is_yourself` tinyint(4) NOT NULL COMMENT '邮件是否发给自己',
  `use_serve` tinyint(4) NOT NULL COMMENT '邮件发送使用的服务',
  `state` tinyint(1) NOT NULL DEFAULT 0 COMMENT '邮件发送状态',
  PRIMARY KEY (`mail_id`) USING BTREE,
  INDEX `user_for_mail`(`user_id` ASC) USING BTREE COMMENT '邮件表用户索引'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '邮件任务表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of mail
-- ----------------------------
INSERT INTO `mail` VALUES ('mail1680013311822', 1640385966864732160, 'String', '1111111111111111111111111111111111111111111111', '2023-03-28 22:21:52', '747945307@qq.com', 0, '2023-06-28 21:40:22', 0, 0, 0);
INSERT INTO `mail` VALUES ('mail1680013368342', 1640385966864732160, 'String', '222222222222222', '2023-03-28 22:22:48', '747945307@qq.com', 0, '2023-05-28 21:40:22', 0, 0, 0);
INSERT INTO `mail` VALUES ('mail1680013399612', 1640385966864732160, 'String', '33333333333333', '2023-03-28 22:23:20', '747945307@qq.com', 0, '2023-03-28 23:40:25', 0, 0, 0);
INSERT INTO `mail` VALUES ('mail1680015996713', 1640385966864732160, 'String', '4444444444', '2023-03-28 23:06:37', '747945307@qq.com', 0, '2023-03-29 00:10:39', 0, 0, 0);
INSERT INTO `mail` VALUES ('mail1680074047150', 1640385966864732160, 'String', '555555555555', '2023-03-29 15:14:07', '747945307@qq.com', 1, '2023-03-30 00:10:39', 1, 0, 0);
INSERT INTO `mail` VALUES ('mail1680074077023', 1640385966864732160, 'String', '66666666666', '2023-03-29 15:14:37', '747945307@qq.com', 1, '2023-03-30 00:10:39', 1, 0, 0);
INSERT INTO `mail` VALUES ('mail1680228412560', 1640385966864732160, 'String', '77777777', '2023-03-31 10:06:53', '747945307@qq.com', 1, '2023-06-01 00:10:39', 1, 0, 0);
INSERT INTO `mail` VALUES ('mail1680228739699', 1640385966864732160, 'String', '888888888888', '2023-03-31 10:12:20', '747945307@qq.com', 1, '2023-06-01 00:10:39', 1, 0, 0);

-- ----------------------------
-- Table structure for message
-- ----------------------------
DROP TABLE IF EXISTS `message`;
CREATE TABLE `message`  (
  `id` bigint(20) NOT NULL COMMENT '短信ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `text` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '短信内容',
  `send_time` datetime NOT NULL COMMENT '短信发送时间',
  `create_time` datetime NOT NULL COMMENT '短信创建时间',
  `is_unnamed` tinyint(1) NOT NULL COMMENT '短信是否匿名',
  `state` tinyint(1) NOT NULL COMMENT '短信状态',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '短信任务表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of message
-- ----------------------------

-- ----------------------------
-- Table structure for oauth
-- ----------------------------
DROP TABLE IF EXISTS `oauth`;
CREATE TABLE `oauth`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '第三方授权表ID',
  `user_id` bigint(20) NOT NULL COMMENT '第三方授权绑定的用户ID',
  `provider` tinyint(1) NOT NULL COMMENT '第三方授权类型',
  `open_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '第三方授权唯一标识',
  `access_token` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '第三方授权Token',
  `refresh_token` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '第三方授权长Token',
  `token_expiry` int(11) NULL DEFAULT NULL COMMENT '第三方授权Token时长',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '第三方授权创建时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '第三方授权更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `user_provider_unique`(`user_id` ASC) USING BTREE COMMENT '用户ID唯一索引',
  INDEX `open_id_index`(`open_id` ASC) USING BTREE COMMENT '授权唯一ID索引'
) ENGINE = InnoDB AUTO_INCREMENT = 10006 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户第三方授权表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of oauth
-- ----------------------------
INSERT INTO `oauth` VALUES (10005, 1646356165694722048, 3, '9735876', '9ddc8d6335c51ac55327b7f44ffae4b7', 'f674ea1869bd34d2308995037ecdbdb95bfec7cc508254edc8c6e45d1b4eafda', 86400, '2023-04-13 11:34:32', '2023-04-13 11:34:32');

-- ----------------------------
-- Table structure for orders
-- ----------------------------
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders`  (
  `id` bigint(20) NOT NULL COMMENT '订单ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `date` datetime NOT NULL COMMENT '订单时间',
  `goods_id` tinyint(4) NOT NULL COMMENT '商品ID',
  `pay_time` datetime NULL DEFAULT NULL COMMENT '支付时间',
  `pay_type` tinyint(1) NOT NULL COMMENT '支付类型',
  `amount` decimal(10, 2) NULL DEFAULT NULL COMMENT '支付金额',
  `trade_no` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '支付商订单号',
  `state` tinyint(1) NOT NULL COMMENT '支付状态',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '全局订单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of orders
-- ----------------------------

-- ----------------------------
-- Table structure for perm
-- ----------------------------
DROP TABLE IF EXISTS `perm`;
CREATE TABLE `perm`  (
  `id` tinyint(11) NOT NULL AUTO_INCREMENT COMMENT '权限ID',
  `permissions` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '权限路径',
  `desc` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '权限描述',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '权限表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of perm
-- ----------------------------
INSERT INTO `perm` VALUES (1, 'open.test', '测试');

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `id` tinyint(4) NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色名称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES (1, 'admin');
INSERT INTO `role` VALUES (5, '111');
INSERT INTO `role` VALUES (6, '666');
INSERT INTO `role` VALUES (7, '666');
INSERT INTO `role` VALUES (8, '666');
INSERT INTO `role` VALUES (9, '666');

-- ----------------------------
-- Table structure for role_to_prem
-- ----------------------------
DROP TABLE IF EXISTS `role_to_prem`;
CREATE TABLE `role_to_prem`  (
  `id` int(11) NOT NULL COMMENT '角色与权限中间表ID',
  `role_id` tinyint(4) NOT NULL COMMENT '角色ID',
  `prem_id` tinyint(4) NOT NULL COMMENT '权限ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '角色与权限中间表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role_to_prem
-- ----------------------------

-- ----------------------------
-- Table structure for sponsor
-- ----------------------------
DROP TABLE IF EXISTS `sponsor`;
CREATE TABLE `sponsor`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '赞助表ID',
  `user_id` bigint(20) NOT NULL COMMENT '赞助用户',
  `sponsor_say` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '呀~这位好心人居然没有留言！' COMMENT '赞助留言',
  `sponsor_amount` decimal(10, 2) NULL DEFAULT NULL COMMENT '赞助金额',
  `pay_type` tinyint(1) NOT NULL COMMENT '支付类型',
  `state` tinyint(1) NOT NULL COMMENT '支付状态',
  `trade_no` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '支付商订单号',
  `create_time` datetime NOT NULL COMMENT '赞助创建时间',
  `pay_time` datetime NULL DEFAULT NULL COMMENT '支付时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 168207104461546432 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '赞助信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sponsor
-- ----------------------------
INSERT INTO `sponsor` VALUES (168189489756784931, 1640385966864732160, '11111111111', 0.01, 1, 1, '2023041922001464041450047528', '2023-04-19 17:01:38', '2023-04-19 17:01:51');
INSERT INTO `sponsor` VALUES (168190982297441362, 1640385966864732160, '11111111111', 0.01, 2, 1, '4200001798202304190371168081', '2023-04-19 21:10:23', '2023-04-19 21:10:34');
INSERT INTO `sponsor` VALUES (168190994075986852, 1640385966864732160, '11111111111', 0.01, 2, 1, '4200001776202304198383970352', '2023-04-19 21:12:21', '2023-04-19 21:12:34');
INSERT INTO `sponsor` VALUES (168190997362795682, 1640385966864732160, '11111111111', 0.01, 2, 1, '4200001777202304198177972027', '2023-04-19 21:12:54', '2023-04-19 21:13:08');
INSERT INTO `sponsor` VALUES (168191009962153672, 1640385966864732160, '11111111111', 0.01, 2, 1, '4200001851202304193152912084', '2023-04-19 21:15:00', '2023-04-19 21:15:20');
INSERT INTO `sponsor` VALUES (168198152620694652, 1646356165694722048, '1', 0.01, 2, 1, '4200001774202304208343638757', '2023-04-20 17:05:26', '2023-04-20 17:05:42');
INSERT INTO `sponsor` VALUES (168198160707596772, 1646356165694722048, '1', 0.01, 2, 1, '4200001794202304207470890271', '2023-04-20 17:06:47', '2023-04-20 17:07:05');
INSERT INTO `sponsor` VALUES (168198170780585342, 1646356165694722048, '1', 0.01, 2, 1, '4200001803202304208019597019', '2023-04-20 17:08:28', '2023-04-20 17:08:39');
INSERT INTO `sponsor` VALUES (168204730099849672, 1646356165694722048, '祝云寄越来越好', 0.10, 2, 1, '4200001788202304218370036467', '2023-04-21 11:21:41', '2023-04-21 11:21:53');
INSERT INTO `sponsor` VALUES (168205158425810622, 1640385966864732160, '11111111111', 0.01, 2, 1, '4200001788202304212562893270', '2023-04-21 12:33:04', '2023-04-21 12:33:35');
INSERT INTO `sponsor` VALUES (168207012925551092, 1646356165694722048, '喝一杯奶茶。', 0.10, 2, 1, '4200001790202304214997832020', '2023-04-21 17:42:09', '2023-04-21 17:42:20');
INSERT INTO `sponsor` VALUES (168207052836476161, 1646356165694722048, '一杯奶茶', 0.10, 1, 1, '2023042122001464041455013864', '2023-04-21 17:48:48', '2023-04-21 17:49:01');
INSERT INTO `sponsor` VALUES (168207059047308362, 1646356165694722048, '买一瓶饮料', 0.10, 2, 1, '4200001798202304219665025575', '2023-04-21 17:49:50', '2023-04-21 17:50:07');
INSERT INTO `sponsor` VALUES (168207065173463091, 1646356165694722048, '喝水', NULL, 1, 0, NULL, '2023-04-21 17:50:52', NULL);
INSERT INTO `sponsor` VALUES (168207069930085712, 1646356165694722048, '喝水', NULL, 2, 0, NULL, '2023-04-21 17:51:39', NULL);

-- ----------------------------
-- Table structure for state_type
-- ----------------------------
DROP TABLE IF EXISTS `state_type`;
CREATE TABLE `state_type`  (
  `id` tinyint(4) NOT NULL COMMENT '状态类型ID',
  `state` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '状态名称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '状态类型表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of state_type
-- ----------------------------

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` bigint(20) NOT NULL COMMENT '用户自增ID',
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户账户',
  `nick_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户昵称',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'https://a.ideaopen.cn/JanYork/YPfetj0a.png' COMMENT '用户头像',
  `email` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户邮箱',
  `phone` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户手机',
  `pwd` varchar(150) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户密码',
  `salt` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户盐值',
  `auth_real_name` tinyint(1) NOT NULL DEFAULT 1 COMMENT '是否实名认证',
  `create_time` datetime NOT NULL COMMENT '用户创建时间',
  `state` tinyint(1) NOT NULL DEFAULT 0 COMMENT '用户状态',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `user_for_name`(`name` ASC) USING BTREE COMMENT '用户账户索引'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '云寄用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1640385966864732160, 'JanYork', '', 'https://a.ideaopen.cn/JanYork/7LlPSYEV.png', NULL, '18888888888', '$2a$10$NZ2rTuP2LIO8gTF3WOhjJ.r/SKKAUfHCtP9de6YE5fl9oLi0uv0gu', '$2a$10$NZ2rTuP2LIO8gTF3WOhjJ.', 1, '2023-03-28 00:11:06', 1);
INSERT INTO `user` VALUES (1646356165694722048, 'GITEE_9735876', 'JanYork', 'https://dlf-1309416366.cos.ap-beijing.myqcloud.com/9735876_1681356476020.png', NULL, '0', '$2a$10$I92TqGF8.6NV1sxJjWUnKOQjmFHiYNAivq9WJ.IXpn9thKQPdby3K', '$2a$10$I92TqGF8.6NV1sxJjWUnKO', 1, '2023-04-13 11:34:32', 1);

-- ----------------------------
-- Table structure for user_to_prem
-- ----------------------------
DROP TABLE IF EXISTS `user_to_prem`;
CREATE TABLE `user_to_prem`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户与权限中间表ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `prem_id` tinyint(4) NOT NULL COMMENT '权限ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户与权限中间表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_to_prem
-- ----------------------------
INSERT INTO `user_to_prem` VALUES (1, 1639936309851193344, 1);

-- ----------------------------
-- Table structure for user_to_role
-- ----------------------------
DROP TABLE IF EXISTS `user_to_role`;
CREATE TABLE `user_to_role`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户与角色中间表ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `role_id` tinyint(4) NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户与角色中间表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_to_role
-- ----------------------------
INSERT INTO `user_to_role` VALUES (1, 1639936309851193344, 1);

-- ----------------------------
-- Table structure for wish
-- ----------------------------
DROP TABLE IF EXISTS `wish`;
CREATE TABLE `wish`  (
  `id` bigint(20) NOT NULL COMMENT '愿望ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `text` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '愿望',
  `image` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '图片',
  `blessing` int(11) NOT NULL COMMENT '祝福',
  `creat_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '许愿表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wish
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
