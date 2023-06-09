/*
 Navicat Premium Data Transfer

 Source Server         : 腾讯云
 Source Server Type    : MySQL
 Source Server Version : 50718 (5.7.18-cynos-log)
 Source Host           : bj-cynosdbmysql-grp-p55r1lki.sql.tencentcdb.com:21598
 Source Schema         : xxl_job

 Target Server Type    : MySQL
 Target Server Version : 50718 (5.7.18-cynos-log)
 File Encoding         : 65001

 Date: 09/06/2023 14:39:16
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for xxl_job_group
-- ----------------------------
DROP TABLE IF EXISTS `xxl_job_group`;
CREATE TABLE `xxl_job_group`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `app_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '执行器AppName',
  `title` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '执行器名称',
  `address_type` tinyint(4) NOT NULL DEFAULT 0 COMMENT '执行器地址类型：0=自动注册、1=手动录入',
  `address_list` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '执行器地址列表，多地址逗号分隔',
  `update_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of xxl_job_group
-- ----------------------------
INSERT INTO `xxl_job_group` VALUES (1, 'dlf-job', '动力风测试执行器', 1, ' http://66ihm7.natappfree.cc', '2023-03-15 16:39:34');

-- ----------------------------
-- Table structure for xxl_job_info
-- ----------------------------
DROP TABLE IF EXISTS `xxl_job_info`;
CREATE TABLE `xxl_job_info`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `job_group` int(11) NOT NULL COMMENT '执行器主键ID',
  `job_desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `add_time` datetime NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  `author` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '作者',
  `alarm_email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '报警邮件',
  `schedule_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'NONE' COMMENT '调度类型',
  `schedule_conf` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '调度配置，值含义取决于调度类型',
  `misfire_strategy` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'DO_NOTHING' COMMENT '调度过期策略',
  `executor_route_strategy` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '执行器路由策略',
  `executor_handler` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '执行器任务handler',
  `executor_param` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '执行器任务参数',
  `executor_block_strategy` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '阻塞处理策略',
  `executor_timeout` int(11) NOT NULL DEFAULT 0 COMMENT '任务执行超时时间，单位秒',
  `executor_fail_retry_count` int(11) NOT NULL DEFAULT 0 COMMENT '失败重试次数',
  `glue_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'GLUE类型',
  `glue_source` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT 'GLUE源代码',
  `glue_remark` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'GLUE备注',
  `glue_updatetime` datetime NULL DEFAULT NULL COMMENT 'GLUE更新时间',
  `child_jobid` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '子任务ID，多个逗号分隔',
  `trigger_status` tinyint(4) NOT NULL DEFAULT 0 COMMENT '调度状态：0-停止，1-运行',
  `trigger_last_time` bigint(13) NOT NULL DEFAULT 0 COMMENT '上次调度时间',
  `trigger_next_time` bigint(13) NOT NULL DEFAULT 0 COMMENT '下次调度时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of xxl_job_info
-- ----------------------------
INSERT INTO `xxl_job_info` VALUES (2, 1, 'test', '2023-03-15 15:49:17', '2023-03-15 16:57:49', '小简', '', 'CRON', '30 * * * * ?', 'DO_NOTHING', 'FIRST', 'testJobHandler', '1111', 'SERIAL_EXECUTION', 0, 0, 'BEAN', '', 'GLUE代码初始化', '2023-03-15 15:49:17', '', 0, 0, 0);

-- ----------------------------
-- Table structure for xxl_job_lock
-- ----------------------------
DROP TABLE IF EXISTS `xxl_job_lock`;
CREATE TABLE `xxl_job_lock`  (
  `lock_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '锁名称',
  PRIMARY KEY (`lock_name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of xxl_job_lock
-- ----------------------------
INSERT INTO `xxl_job_lock` VALUES ('schedule_lock');

-- ----------------------------
-- Table structure for xxl_job_log
-- ----------------------------
DROP TABLE IF EXISTS `xxl_job_log`;
CREATE TABLE `xxl_job_log`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `job_group` int(11) NOT NULL COMMENT '执行器主键ID',
  `job_id` int(11) NOT NULL COMMENT '任务，主键ID',
  `executor_address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '执行器地址，本次执行的地址',
  `executor_handler` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '执行器任务handler',
  `executor_param` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '执行器任务参数',
  `executor_sharding_param` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '执行器任务分片参数，格式如 1/2',
  `executor_fail_retry_count` int(11) NOT NULL DEFAULT 0 COMMENT '失败重试次数',
  `trigger_time` datetime NULL DEFAULT NULL COMMENT '调度-时间',
  `trigger_code` int(11) NOT NULL COMMENT '调度-结果',
  `trigger_msg` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '调度-日志',
  `handle_time` datetime NULL DEFAULT NULL COMMENT '执行-时间',
  `handle_code` int(11) NOT NULL COMMENT '执行-状态',
  `handle_msg` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '执行-日志',
  `alarm_status` tinyint(4) NOT NULL DEFAULT 0 COMMENT '告警状态：0-默认、1-无需告警、2-告警成功、3-告警失败',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `I_trigger_time`(`trigger_time`) USING BTREE,
  INDEX `I_handle_code`(`handle_code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of xxl_job_log
-- ----------------------------

-- ----------------------------
-- Table structure for xxl_job_log_report
-- ----------------------------
DROP TABLE IF EXISTS `xxl_job_log_report`;
CREATE TABLE `xxl_job_log_report`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `trigger_day` datetime NULL DEFAULT NULL COMMENT '调度-时间',
  `running_count` int(11) NOT NULL DEFAULT 0 COMMENT '运行中-日志数量',
  `suc_count` int(11) NOT NULL DEFAULT 0 COMMENT '执行成功-日志数量',
  `fail_count` int(11) NOT NULL DEFAULT 0 COMMENT '执行失败-日志数量',
  `update_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `i_trigger_day`(`trigger_day`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 86 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of xxl_job_log_report
-- ----------------------------
INSERT INTO `xxl_job_log_report` VALUES (1, '2023-03-15 00:00:00', 0, 10, 2, NULL);
INSERT INTO `xxl_job_log_report` VALUES (2, '2023-03-14 00:00:00', 0, 0, 0, NULL);
INSERT INTO `xxl_job_log_report` VALUES (3, '2023-03-13 00:00:00', 0, 0, 0, NULL);
INSERT INTO `xxl_job_log_report` VALUES (4, '2023-03-16 00:00:00', 0, 0, 0, NULL);
INSERT INTO `xxl_job_log_report` VALUES (5, '2023-03-17 00:00:00', 0, 0, 0, NULL);
INSERT INTO `xxl_job_log_report` VALUES (6, '2023-03-24 00:00:00', 0, 0, 0, NULL);
INSERT INTO `xxl_job_log_report` VALUES (7, '2023-03-23 00:00:00', 0, 0, 0, NULL);
INSERT INTO `xxl_job_log_report` VALUES (8, '2023-03-22 00:00:00', 0, 0, 0, NULL);
INSERT INTO `xxl_job_log_report` VALUES (9, '2023-03-25 00:00:00', 0, 0, 0, NULL);
INSERT INTO `xxl_job_log_report` VALUES (10, '2023-03-26 00:00:00', 0, 0, 0, NULL);
INSERT INTO `xxl_job_log_report` VALUES (11, '2023-03-27 00:00:00', 0, 0, 0, NULL);
INSERT INTO `xxl_job_log_report` VALUES (12, '2023-03-28 00:00:00', 0, 0, 0, NULL);
INSERT INTO `xxl_job_log_report` VALUES (13, '2023-03-29 00:00:00', 0, 0, 0, NULL);
INSERT INTO `xxl_job_log_report` VALUES (14, '2023-03-30 00:00:00', 0, 0, 0, NULL);
INSERT INTO `xxl_job_log_report` VALUES (15, '2023-03-31 00:00:00', 0, 0, 0, NULL);
INSERT INTO `xxl_job_log_report` VALUES (16, '2023-04-01 00:00:00', 0, 0, 0, NULL);
INSERT INTO `xxl_job_log_report` VALUES (17, '2023-04-02 00:00:00', 0, 0, 0, NULL);
INSERT INTO `xxl_job_log_report` VALUES (18, '2023-04-03 00:00:00', 0, 0, 0, NULL);
INSERT INTO `xxl_job_log_report` VALUES (19, '2023-04-04 00:00:00', 0, 0, 0, NULL);
INSERT INTO `xxl_job_log_report` VALUES (20, '2023-04-05 00:00:00', 0, 0, 0, NULL);
INSERT INTO `xxl_job_log_report` VALUES (21, '2023-04-06 00:00:00', 0, 0, 0, NULL);
INSERT INTO `xxl_job_log_report` VALUES (22, '2023-04-07 00:00:00', 0, 0, 0, NULL);
INSERT INTO `xxl_job_log_report` VALUES (23, '2023-04-08 00:00:00', 0, 0, 0, NULL);
INSERT INTO `xxl_job_log_report` VALUES (24, '2023-04-09 00:00:00', 0, 0, 0, NULL);
INSERT INTO `xxl_job_log_report` VALUES (25, '2023-04-10 00:00:00', 0, 0, 0, NULL);
INSERT INTO `xxl_job_log_report` VALUES (26, '2023-04-11 00:00:00', 0, 0, 0, NULL);
INSERT INTO `xxl_job_log_report` VALUES (27, '2023-04-12 00:00:00', 0, 0, 0, NULL);
INSERT INTO `xxl_job_log_report` VALUES (28, '2023-04-13 00:00:00', 0, 0, 0, NULL);
INSERT INTO `xxl_job_log_report` VALUES (29, '2023-04-14 00:00:00', 0, 0, 0, NULL);
INSERT INTO `xxl_job_log_report` VALUES (30, '2023-04-15 00:00:00', 0, 0, 0, NULL);
INSERT INTO `xxl_job_log_report` VALUES (31, '2023-04-16 00:00:00', 0, 0, 0, NULL);
INSERT INTO `xxl_job_log_report` VALUES (32, '2023-04-17 00:00:00', 0, 0, 0, NULL);
INSERT INTO `xxl_job_log_report` VALUES (33, '2023-04-18 00:00:00', 0, 0, 0, NULL);
INSERT INTO `xxl_job_log_report` VALUES (34, '2023-04-19 00:00:00', 0, 0, 0, NULL);
INSERT INTO `xxl_job_log_report` VALUES (35, '2023-04-20 00:00:00', 0, 0, 0, NULL);
INSERT INTO `xxl_job_log_report` VALUES (36, '2023-04-21 00:00:00', 0, 0, 0, NULL);
INSERT INTO `xxl_job_log_report` VALUES (37, '2023-04-22 00:00:00', 0, 0, 0, NULL);
INSERT INTO `xxl_job_log_report` VALUES (38, '2023-04-23 00:00:00', 0, 0, 0, NULL);
INSERT INTO `xxl_job_log_report` VALUES (39, '2023-04-24 00:00:00', 0, 0, 0, NULL);
INSERT INTO `xxl_job_log_report` VALUES (40, '2023-04-25 00:00:00', 0, 0, 0, NULL);
INSERT INTO `xxl_job_log_report` VALUES (41, '2023-04-26 00:00:00', 0, 0, 0, NULL);
INSERT INTO `xxl_job_log_report` VALUES (42, '2023-04-27 00:00:00', 0, 0, 0, NULL);
INSERT INTO `xxl_job_log_report` VALUES (43, '2023-04-28 00:00:00', 0, 0, 0, NULL);
INSERT INTO `xxl_job_log_report` VALUES (44, '2023-04-29 00:00:00', 0, 0, 0, NULL);
INSERT INTO `xxl_job_log_report` VALUES (45, '2023-04-30 00:00:00', 0, 0, 0, NULL);
INSERT INTO `xxl_job_log_report` VALUES (46, '2023-05-01 00:00:00', 0, 0, 0, NULL);
INSERT INTO `xxl_job_log_report` VALUES (47, '2023-05-02 00:00:00', 0, 0, 0, NULL);
INSERT INTO `xxl_job_log_report` VALUES (48, '2023-05-03 00:00:00', 0, 0, 0, NULL);
INSERT INTO `xxl_job_log_report` VALUES (49, '2023-05-04 00:00:00', 0, 0, 0, NULL);
INSERT INTO `xxl_job_log_report` VALUES (50, '2023-05-05 00:00:00', 0, 0, 0, NULL);
INSERT INTO `xxl_job_log_report` VALUES (51, '2023-05-06 00:00:00', 0, 0, 0, NULL);
INSERT INTO `xxl_job_log_report` VALUES (52, '2023-05-07 00:00:00', 0, 0, 0, NULL);
INSERT INTO `xxl_job_log_report` VALUES (53, '2023-05-08 00:00:00', 0, 0, 0, NULL);
INSERT INTO `xxl_job_log_report` VALUES (54, '2023-05-09 00:00:00', 0, 0, 0, NULL);
INSERT INTO `xxl_job_log_report` VALUES (55, '2023-05-10 00:00:00', 0, 0, 0, NULL);
INSERT INTO `xxl_job_log_report` VALUES (56, '2023-05-11 00:00:00', 0, 0, 0, NULL);
INSERT INTO `xxl_job_log_report` VALUES (57, '2023-05-12 00:00:00', 0, 0, 0, NULL);
INSERT INTO `xxl_job_log_report` VALUES (58, '2023-05-13 00:00:00', 0, 0, 0, NULL);
INSERT INTO `xxl_job_log_report` VALUES (59, '2023-05-14 00:00:00', 0, 0, 0, NULL);
INSERT INTO `xxl_job_log_report` VALUES (60, '2023-05-15 00:00:00', 0, 0, 0, NULL);
INSERT INTO `xxl_job_log_report` VALUES (61, '2023-05-16 00:00:00', 0, 0, 0, NULL);
INSERT INTO `xxl_job_log_report` VALUES (62, '2023-05-17 00:00:00', 0, 0, 0, NULL);
INSERT INTO `xxl_job_log_report` VALUES (63, '2023-05-18 00:00:00', 0, 0, 0, NULL);
INSERT INTO `xxl_job_log_report` VALUES (64, '2023-05-19 00:00:00', 0, 0, 0, NULL);
INSERT INTO `xxl_job_log_report` VALUES (65, '2023-05-20 00:00:00', 0, 0, 0, NULL);
INSERT INTO `xxl_job_log_report` VALUES (66, '2023-05-21 00:00:00', 0, 0, 0, NULL);
INSERT INTO `xxl_job_log_report` VALUES (67, '2023-05-22 00:00:00', 0, 0, 0, NULL);
INSERT INTO `xxl_job_log_report` VALUES (68, '2023-05-23 00:00:00', 0, 0, 0, NULL);
INSERT INTO `xxl_job_log_report` VALUES (69, '2023-05-24 00:00:00', 0, 0, 0, NULL);
INSERT INTO `xxl_job_log_report` VALUES (70, '2023-05-25 00:00:00', 0, 0, 0, NULL);
INSERT INTO `xxl_job_log_report` VALUES (71, '2023-05-26 00:00:00', 0, 0, 0, NULL);
INSERT INTO `xxl_job_log_report` VALUES (72, '2023-05-27 00:00:00', 0, 0, 0, NULL);
INSERT INTO `xxl_job_log_report` VALUES (73, '2023-05-28 00:00:00', 0, 0, 0, NULL);
INSERT INTO `xxl_job_log_report` VALUES (74, '2023-05-29 00:00:00', 0, 0, 0, NULL);
INSERT INTO `xxl_job_log_report` VALUES (75, '2023-05-30 00:00:00', 0, 0, 0, NULL);
INSERT INTO `xxl_job_log_report` VALUES (76, '2023-05-31 00:00:00', 0, 0, 0, NULL);
INSERT INTO `xxl_job_log_report` VALUES (77, '2023-06-01 00:00:00', 0, 0, 0, NULL);
INSERT INTO `xxl_job_log_report` VALUES (78, '2023-06-02 00:00:00', 0, 0, 0, NULL);
INSERT INTO `xxl_job_log_report` VALUES (79, '2023-06-03 00:00:00', 0, 0, 0, NULL);
INSERT INTO `xxl_job_log_report` VALUES (80, '2023-06-04 00:00:00', 0, 0, 0, NULL);
INSERT INTO `xxl_job_log_report` VALUES (81, '2023-06-05 00:00:00', 0, 0, 0, NULL);
INSERT INTO `xxl_job_log_report` VALUES (82, '2023-06-06 00:00:00', 0, 0, 0, NULL);
INSERT INTO `xxl_job_log_report` VALUES (83, '2023-06-07 00:00:00', 0, 0, 0, NULL);
INSERT INTO `xxl_job_log_report` VALUES (84, '2023-06-08 00:00:00', 0, 0, 0, NULL);
INSERT INTO `xxl_job_log_report` VALUES (85, '2023-06-09 00:00:00', 0, 0, 0, NULL);

-- ----------------------------
-- Table structure for xxl_job_logglue
-- ----------------------------
DROP TABLE IF EXISTS `xxl_job_logglue`;
CREATE TABLE `xxl_job_logglue`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `job_id` int(11) NOT NULL COMMENT '任务，主键ID',
  `glue_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'GLUE类型',
  `glue_source` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT 'GLUE源代码',
  `glue_remark` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'GLUE备注',
  `add_time` datetime NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of xxl_job_logglue
-- ----------------------------

-- ----------------------------
-- Table structure for xxl_job_registry
-- ----------------------------
DROP TABLE IF EXISTS `xxl_job_registry`;
CREATE TABLE `xxl_job_registry`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `registry_group` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `registry_key` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `registry_value` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `i_g_k_v`(`registry_group`, `registry_key`, `registry_value`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 789 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of xxl_job_registry
-- ----------------------------
INSERT INTO `xxl_job_registry` VALUES (788, 'EXECUTOR', 'dlf-job', 'http://127.0.0.1:8086/', '2023-06-09 13:49:08');

-- ----------------------------
-- Table structure for xxl_job_user
-- ----------------------------
DROP TABLE IF EXISTS `xxl_job_user`;
CREATE TABLE `xxl_job_user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '账号',
  `password` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '密码',
  `role` tinyint(4) NOT NULL COMMENT '角色：0-普通用户、1-管理员',
  `permission` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '权限：执行器ID列表，多个逗号分割',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `i_username`(`username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of xxl_job_user
-- ----------------------------
INSERT INTO `xxl_job_user` VALUES (1, 'admin', 'd3fccb495e775b460eed6d8cdc0b4a4f', 1, NULL);

SET FOREIGN_KEY_CHECKS = 1;
