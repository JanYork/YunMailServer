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

 Date: 18/07/2023 09:24:49
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '管理员ID',
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '管理员用户',
  `pwd` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '管理员密码',
  `mail` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '管理员邮箱',
  `role` tinyint(1) NOT NULL COMMENT '管理员角色',
  `create_time` datetime NOT NULL COMMENT '管理员创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10003 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '管理员信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES (10001, 'JanYork', '$2a$10$8SSN2hpmwyKxgg1NuhJcFOP84dgbiFilVX9yff0r/Sf7iJ4QGz.DS', '747945307@qq.com', 0, '2023-04-25 16:22:16');

-- ----------------------------
-- Table structure for blessing
-- ----------------------------
DROP TABLE IF EXISTS `blessing`;
CREATE TABLE `blessing`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '祝福ID',
  `category_id` int NOT NULL COMMENT '祝福分类ID',
  `content` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '祝福内容',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `category_id`(`category_id` ASC) USING BTREE,
  CONSTRAINT `blessing_ibfk_1` FOREIGN KEY (`category_id`) REFERENCES `blessing_category` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '愿望祝福表' ROW_FORMAT = DYNAMIC;

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
  `id` int NOT NULL AUTO_INCREMENT COMMENT '祝福分类ID',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '祝福分类名称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '祝福分类表' ROW_FORMAT = DYNAMIC;

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
  `id` int NOT NULL AUTO_INCREMENT COMMENT '祝福语ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `blessing` tinyint NOT NULL COMMENT '祝福语',
  `wish_id` bigint NOT NULL COMMENT '愿望ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `is_filter` tinyint(1) NOT NULL COMMENT '是否过滤',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '祝福语录表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of blessing_to_wish
-- ----------------------------
INSERT INTO `blessing_to_wish` VALUES (2, 1640385966864732160, 1, 1001, '2023-06-23 23:44:49', 0);
INSERT INTO `blessing_to_wish` VALUES (3, 1640385966864732160, 2, 1001, '2023-06-23 23:47:35', 0);

-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '评论ID',
  `parent_id` bigint NOT NULL DEFAULT 0 COMMENT '父级评论ID',
  `user_id` bigint NOT NULL COMMENT '评论用户ID',
  `mail_or_letter_id` bigint NOT NULL COMMENT '评论对应邮件或者信件',
  `for_type` tinyint NOT NULL COMMENT '评论对应表[mail：0，letter：1]',
  `level` tinyint(1) NOT NULL DEFAULT 0 COMMENT '评论深度',
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '评论内容',
  `create_time` datetime NOT NULL COMMENT '评论时间',
  `is_filter` tinyint(1) NOT NULL COMMENT '是否过滤',
  `is_sensitive` tinyint(1) NOT NULL COMMENT '是否敏感',
  `ai_check_msg` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'AI审核消息',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_parent_id`(`parent_id` ASC) USING BTREE,
  INDEX `idx_user_id`(`user_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1022 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '邮件与信件评论表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of comment
-- ----------------------------
INSERT INTO `comment` VALUES (1008, 0, 1640385966864732160, 10004, 1, 1, '你好，评论测试', '2023-06-23 21:09:15', 0, 0, NULL);
INSERT INTO `comment` VALUES (1009, 0, 1640385966864732160, 10006, 1, 1, '你好，评论测试', '2023-06-23 21:09:33', 0, 0, NULL);
INSERT INTO `comment` VALUES (1010, 1008, 1640385966864732160, 10004, 1, 2, '你好，评论测试，多级', '2023-06-23 21:10:33', 0, 0, NULL);
INSERT INTO `comment` VALUES (1011, 1008, 1640385966864732160, 10004, 1, 2, '你好，评论测试，多级2', '2023-06-23 21:10:42', 0, 0, NULL);
INSERT INTO `comment` VALUES (1012, 1010, 1640385966864732160, 10004, 1, 3, '你好，评论测试，多级', '2023-06-23 21:11:12', 0, 0, NULL);
INSERT INTO `comment` VALUES (1013, 1010, 1640385966864732160, 10006, 1, 3, '你好，草泥马的，多级', '2023-06-23 22:30:42', 0, 1, '存在低俗辱骂不合规;');
INSERT INTO `comment` VALUES (1014, 1010, 1640385966864732160, 10006, 1, 3, '你好，裸聊哦', '2023-06-23 22:31:31', 0, 1, '存在文本色情不合规;');
INSERT INTO `comment` VALUES (1017, 1010, 1640385966864732160, 10006, 1, 3, '你好，加一个微信吧，JanYork！', '2023-06-23 22:33:08', 0, 1, '存在恶意推广不合规;');
INSERT INTO `comment` VALUES (1019, 1010, 1640385966864732160, 10006, 1, 3, '澳门葡京在线发牌，大小押注稳赚不赔！', '2023-06-23 22:34:14', 0, 1, '存在暴恐违禁不合规;');

-- ----------------------------
-- Table structure for id_card_auth
-- ----------------------------
DROP TABLE IF EXISTS `id_card_auth`;
CREATE TABLE `id_card_auth`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '实名认证ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `id_card` varchar(18) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '身份证号',
  `phone` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '手机号',
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '姓名',
  `other` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '其他证明身份图片[JSON]',
  `status` tinyint(1) NOT NULL DEFAULT 1 COMMENT '实名认证状态',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '实名认证表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of id_card_auth
-- ----------------------------
INSERT INTO `id_card_auth` VALUES (1, 1640385966864732160, '43032120040916003X', '16670880818', '张三', '[{id:1,url:https://xxx.com/xxx.jpg}]', 2);

-- ----------------------------
-- Table structure for issues
-- ----------------------------
DROP TABLE IF EXISTS `issues`;
CREATE TABLE `issues`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `issue` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 157 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '问题表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of issues
-- ----------------------------
INSERT INTO `issues` VALUES (1, '对于“道德”和“三观”这两个词，你是如何理解和定义的？请描述一下你对道德和三观的个人理解。');
INSERT INTO `issues` VALUES (2, '在您看来，道德与文化、传统的关系是什么样的？您认为道德应该是普适的还是因文化而异？');
INSERT INTO `issues` VALUES (3, '如何看待个人的自由权利与社会责任之间的平衡？您认为一个人的自由是否有界限？');
INSERT INTO `issues` VALUES (4, '对于社会不平等问题，您是持怎样的观点？您认为社会应该追求完全平等还是更注重公正与机会均等？');
INSERT INTO `issues` VALUES (5, '当道德准则与法律规定发生冲突时，您会如何取舍和决策？对您来说，道德和法律的关系是什么样的？');
INSERT INTO `issues` VALUES (6, '您如何看待对动物的道德责任？您认为人类有义务保护和尊重动物的权益吗？');
INSERT INTO `issues` VALUES (7, '在您看来，什么是一个道德高尚的行为？请描述一个您认为是道德高尚的行为的例子。');
INSERT INTO `issues` VALUES (8, '当您面对一个需要做出道德抉择的困境时，您会如何权衡和决策？');
INSERT INTO `issues` VALUES (9, '您对真实与谎言之间的道德边界有何看法？您认为什么情况下可以接受说谎？');
INSERT INTO `issues` VALUES (10, '如何看待商业道德？您认为企业应该更注重利润还是道德价值观？');
INSERT INTO `issues` VALUES (11, '在您的价值观中，宽容与道德之间有何关系？您如何看待宽容与理解对于社会的重要性？');
INSERT INTO `issues` VALUES (12, '对于环境保护和可持续发展，您是持怎样的态度？您认为个人应该承担怎样的环境责任？');
INSERT INTO `issues` VALUES (13, '您如何看待性别平等和女性权益？您认为社会应该采取怎样的措施促进性别平等？');
INSERT INTO `issues` VALUES (14, '当您发现身边的人在欺骗他人或不道德行为时，您会如何应对？您是否愿意揭发这样的行为？');
INSERT INTO `issues` VALUES (16, '对于社交媒体和信息传播的道德问题，您有何看法？您认为个人在使用社交媒体时应该有怎样的责任和意识？');
INSERT INTO `issues` VALUES (17, '您如何看待权力与道德的关系？权力是否容易导致道德滑坡？');
INSERT INTO `issues` VALUES (18, '当您面对两个道德价值冲突的选择时，您会如何进行决策？请描述一种您遇到过的道德冲突的情境。');
INSERT INTO `issues` VALUES (19, '您对于对待弱势群体和社会边缘人士的道德责任有何看法？');
INSERT INTO `issues` VALUES (20, '在您看来，怎样才算是一个道德高尚的领导者？请提供一个您认为是道德高尚的领导者的例子。');
INSERT INTO `issues` VALUES (21, '对于隐私和信息安全的道德问题，您是持怎样的态度？个人隐私权和信息安全之间应该如何平衡？');
INSERT INTO `issues` VALUES (22, '如何看待人工智能和自动化对道德的挑战？您认为人工智能技术应该受到怎样的道德约束和监管？');
INSERT INTO `issues` VALUES (23, '您对于人道主义援助和慈善行为的态度是怎样的？您认为个人应该如何参与和支持这些活动？');
INSERT INTO `issues` VALUES (24, '当您面对道德困境时，是否会受到宗教信仰或道德观念的指引？您如何看待宗教和道德的关系？');
INSERT INTO `issues` VALUES (25, '对于自我利益和集体利益之间的冲突，您会如何选择？您认为个人应该更加关注自我还是更加关注集体利益？');
INSERT INTO `issues` VALUES (26, '您对于教育和道德教育的重要性有何看法？您认为道德教育应该在家庭、学校还是社会中进行？');
INSERT INTO `issues` VALUES (27, '如何看待死刑和道德正当性？您认为国家是否有权利对犯罪行为实施死刑？');
INSERT INTO `issues` VALUES (28, '对于医疗伦理和生命尊严的问题，您是持怎样的观点？您认为医疗人员应该如何平衡治疗和患者权益？');
INSERT INTO `issues` VALUES (29, '您如何看待智能科技和隐私权的关系？您对于个人数据隐私保护有何看法？');
INSERT INTO `issues` VALUES (30, '在您看来，个人道德和职业道德之间有何关系？您认为职业道德对于一个人的成功和职业发展有多重要？');
INSERT INTO `issues` VALUES (31, '您如何看待言论自由和道德的平衡？您认为是否应该对言论进行某种限制或约束？');
INSERT INTO `issues` VALUES (32, '当您目睹他人遭受不公正待遇时，您会如何反应？您认为个人应该如何应对社会的不公平现象？');
INSERT INTO `issues` VALUES (33, '对于科学研究和实验的道德问题，您是持怎样的观点？您认为科学家应该遵守怎样的道德准则？');
INSERT INTO `issues` VALUES (34, '您如何看待利益冲突和道德冲突之间的关系？在面对利益和道德之间的冲突时，您会如何做出决策？');
INSERT INTO `issues` VALUES (35, '在您看来，个人道德和社会道德之间的关系是怎样的？个人的行为是否应该符合社会的道德标准？');
INSERT INTO `issues` VALUES (36, '您对于贪污腐败和廉政建设的问题有何看法？您认为应该采取怎样的措施来解决这些问题？');
INSERT INTO `issues` VALUES (37, '当您面对道德困境时，您是否会受到个人利益和道德原则的影响？请描述一个您曾经面对过的道德困境。');
INSERT INTO `issues` VALUES (38, '对于知识产权和盗版问题，您是持怎样的态度？您认为个人是否有义务尊重和保护知识产权？');
INSERT INTO `issues` VALUES (39, '您如何看待虚假广告和欺诈行为？您认为应该采取怎样的措施来打击虚假广告和欺诈行为？');
INSERT INTO `issues` VALUES (40, '在您的价值观中，诚实和道德之间有何关系？您认为诚实是一个人的道德底线吗？');
INSERT INTO `issues` VALUES (41, '对于战争和军事行动的道德问题，您是持怎样的观点？您认为何时可以使用武力解决问题？');
INSERT INTO `issues` VALUES (42, '您如何看待平等和公正的关系？您认为社会应该如何追求平等和公正？');
INSERT INTO `issues` VALUES (43, '当您发现他人有道德缺失或错误行为时，您会如何处理？您是否会选择直接干涉或提出批评？');
INSERT INTO `issues` VALUES (44, '对于自然环境保护和生态道德的问题，您有何看法？您认为个人应该如何履行生态道德责任？');
INSERT INTO `issues` VALUES (45, '您对人生的意义和目标是怎样理解的？您认为人应该追求什么样的生活价值和目标？');
INSERT INTO `issues` VALUES (46, '在您看来，个人的幸福和他人的幸福之间有何关系？您认为个人应该如何追求幸福和满足感？');
INSERT INTO `issues` VALUES (47, '您如何看待人与人之间的互助和合作？您认为人类社会应该如何构建和维护良好的人际关系？');
INSERT INTO `issues` VALUES (48, '对于个人成长和自我提升的重要性，您有何看法？您认为个人应该如何不断成长和发展？');
INSERT INTO `issues` VALUES (49, '您对于人类的价值和尊严有何看法？您认为每个人都应该被平等对待并受到尊重吗？');
INSERT INTO `issues` VALUES (50, '当面对困难和挑战时，您会如何看待并应对？您认为积极乐观的心态对于克服困难有何作用？');
INSERT INTO `issues` VALUES (51, '对于个人的责任和义务，您是持怎样的观点？您认为个人应该如何履行自己的责任和义务？');
INSERT INTO `issues` VALUES (52, '您如何看待个人的自由意志和命运的关系？您认为人的行为受命运还是自由意志主导？');
INSERT INTO `issues` VALUES (53, '在您的价值观中，正义和公平有何重要性？您认为社会应该如何追求正义和公平？');
INSERT INTO `issues` VALUES (54, '对于人与自然的关系，您有何看法？您认为人类应该如何保护和维护自然环境？');
INSERT INTO `issues` VALUES (55, '您如何看待个人的价值观和道德观念对行为的指导作用？您认为个人应该如何树立正确的价值观和道德观念？');
INSERT INTO `issues` VALUES (56, '在您的世界观中，爱与关爱的力量有何重要性？您认为个人应该如何表达和传递爱与关爱？');
INSERT INTO `issues` VALUES (57, '您对于个人意义和社会意义的平衡有何看法？您认为个人应该如何追求自己的意义和价值？');
INSERT INTO `issues` VALUES (58, '当面对不确定性和风险时，您会如何看待并应对？您认为勇气和决心在面对挑战时的作用有多重要？');
INSERT INTO `issues` VALUES (59, '对于个人与群体的关系，您有何看法？您认为个人应该如何在群体中找到平衡和发展自己？');
INSERT INTO `issues` VALUES (60, '您如何看待自我认知和自我理解的重要性？您认为了解自己对于个人的成长和发展有何帮助？');
INSERT INTO `issues` VALUES (61, '对于个人的人生态度和心态，您有何看法？您认为积极的人生态度对于个人的幸福和成功有何影响？');
INSERT INTO `issues` VALUES (62, '您如何看待学习和知识的重要性？您认为个人应该如何持续学习和追求知识？');
INSERT INTO `issues` VALUES (63, '在您看来，个人的自我实现和社会责任之间的关系是怎样的？您认为个人应该如何追求自我实现同时承担社会责任？');
INSERT INTO `issues` VALUES (64, '对于个人的情绪管理和心理健康，您有何看法？您认为个人应该如何关注和维护自己的心理健康？');
INSERT INTO `issues` VALUES (65, '当面对人际冲突和争议时，您会如何看待并应对？您认为沟通和妥协在解决冲突中的作用有多重要？');
INSERT INTO `issues` VALUES (66, '您对于个人的人生目标和追求有何看法？您认为个人应该如何设定目标并追求自己的梦想？');
INSERT INTO `issues` VALUES (67, '在您看来，个人的情感和情绪表达有何重要性？您认为个人应该如何健康表达和处理自己的情感？');
INSERT INTO `issues` VALUES (68, '您如何看待个人的自律和自控能力？您认为个人应该如何培养和加强自己的自律能力？');
INSERT INTO `issues` VALUES (69, '对于个人的人际关系和社交能力，您有何看法？您认为个人应该如何建立和维护健康的人际关系？');
INSERT INTO `issues` VALUES (70, '当面对失败和挫折时，您会如何看待并应对？您认为坚韧和毅力在克服困难中的作用有多重要？');
INSERT INTO `issues` VALUES (71, '您如何看待个人的责任与权力之间的平衡？您认为个人在拥有权力时应该如何行使责任？');
INSERT INTO `issues` VALUES (72, '对于个人的社会参与和公民责任，您有何看法？您认为个人应该如何积极参与社会事务并履行公民责任？');
INSERT INTO `issues` VALUES (73, '当面对伦理和道德困境时，您会如何看待并应对？您认为道德判断和决策在面对伦理问题时的作用有多重要？');
INSERT INTO `issues` VALUES (74, '您对于个人的生活意义和人生价值有何看法？您认为个人应该如何寻找和实现自己的生活意义？');
INSERT INTO `issues` VALUES (75, '在您看来，个人的自我认同和身份认同有何重要性？您认为个人应该如何建立和维护自己的身份认同？');
INSERT INTO `issues` VALUES (76, '您如何看待个人的道德观念和行为之间的关系？您认为个人应该如何将道德观念付诸实践？');
INSERT INTO `issues` VALUES (77, '对于个人的成就和成功，您有何看法？您认为个人应该如何定义和追求自己的成就和成功？');
INSERT INTO `issues` VALUES (78, '当面对人生抉择和重要决策时，您会如何看待并应对？您认为理性思考和决策在人生选择中的作用有多重要？');
INSERT INTO `issues` VALUES (79, '您如何看待个人的情感和情绪对于生活质量的影响？您认为情感管理和情绪调节的重要性有多大？');
INSERT INTO `issues` VALUES (80, '对于个人的自尊和自信，您有何看法？您认为个人应该如何建立和提升自己的自尊和自信？');
INSERT INTO `issues` VALUES (81, '在您看来，个人的社会责任和道德义务有何关系？您认为个人应该如何履行自己的社会责任？');
INSERT INTO `issues` VALUES (82, '您如何看待个人的心理幸福和内心平静？您认为个人应该如何追求心灵的宁静和满足感？');
INSERT INTO `issues` VALUES (83, '当面对困境和挑战时，您会如何看待并应对？您认为乐观和坚韧的心态在面对困难时的作用有多大？');
INSERT INTO `issues` VALUES (84, '您对于个人的人生价值观和道德观念有何看法？您认为个人应该如何树立和坚守自己的价值观和道德观念？');
INSERT INTO `issues` VALUES (85, '在您的世界观中，友情和人际关系的重要性有何意义？您认为个人应该如何建立和维护良好的友情和人际关系？');
INSERT INTO `issues` VALUES (86, '对于个人的心灵成长和修炼，您有何看法？您认为个人应该如何培养和发展自己的心灵境界？');
INSERT INTO `issues` VALUES (87, '您如何看待个人的社会认同和归属感？您认为个人应该如何在社会中找到自己的归属和认同？');
INSERT INTO `issues` VALUES (88, '您如何看待个人的情感智商和情商的重要性？您认为个人应该如何培养和提升自己的情感智商？');
INSERT INTO `issues` VALUES (89, '对于个人的道德选择和行为一致性，您有何看法？您认为个人应该如何在行为中体现自己的道德选择？');
INSERT INTO `issues` VALUES (90, '在您看来，个人的社会公义感和正义观念有何关系？您认为个人应该如何追求社会公义和正义？');
INSERT INTO `issues` VALUES (91, '您如何看待个人的人际沟通和表达能力？您认为个人应该如何提升自己的沟通和表达技巧？');
INSERT INTO `issues` VALUES (92, '对于个人的职业选择和职业发展，您有何看法？您认为个人应该如何寻找和追求自己的职业道路？');
INSERT INTO `issues` VALUES (93, '当面对挑战和逆境时，您会如何看待并应对？您认为勇气和坚持在面对困难时的作用有多大？');
INSERT INTO `issues` VALUES (94, '您如何看待个人的社会影响力和责任？您认为个人应该如何发挥自己的社会影响力并履行社会责任？');
INSERT INTO `issues` VALUES (95, '对于个人的自我接纳和身心健康，您有何看法？您认为个人应该如何接纳自己并关注自己的身心健康？');
INSERT INTO `issues` VALUES (96, '在您看来，个人的自我实现和他人利益之间有何关系？您认为个人应该如何追求自我实现同时考虑他人利益？');
INSERT INTO `issues` VALUES (97, '您如何看待个人的道德观念和伦理原则的培养？您认为个人应该如何塑造自己的道德观念和伦理原则？');
INSERT INTO `issues` VALUES (98, '对于个人的人际信任和信任建立，您有何看法？您认为个人应该如何建立和维护他人的信任？');
INSERT INTO `issues` VALUES (99, '当面对道德困境和伦理冲突时，您会如何看待并应对？您认为道德判断和伦理决策在困境中的作用有多大？');
INSERT INTO `issues` VALUES (100, '您如何看待个人的自我探索和发现之旅？您认为个人应该如何探索和发现自己的潜能和可能性？');
INSERT INTO `issues` VALUES (101, '您如何看待个人的情感表达和情感交流的重要性？您认为个人应该如何有效表达和交流自己的情感？');
INSERT INTO `issues` VALUES (102, '对于个人的道德责任和社会道德的关系，您有何看法？您认为个人应该如何履行自己的道德责任并遵守社会道德？');
INSERT INTO `issues` VALUES (103, '在您看来，个人的自我认知和自我反思有何重要性？您认为个人应该如何加强自我认知和进行自我反思？');
INSERT INTO `issues` VALUES (104, '您如何看待个人的慈善和公益行为？您认为个人应该如何参与慈善和公益事业，为社会作出贡献？');
INSERT INTO `issues` VALUES (105, '对于个人的职业道德和职业道德准则，您有何看法？您认为个人应该如何遵守和践行职业道德？');
INSERT INTO `issues` VALUES (106, '当面对道德选择和伦理抉择时，您会如何看待并应对？您认为道德判断和伦理决策在关键时刻的作用有多大？');
INSERT INTO `issues` VALUES (107, '您如何看待个人的情感稳定和内心平衡？您认为个人应该如何保持情感的稳定和内心的平衡？');
INSERT INTO `issues` VALUES (108, '在您的价值观中，个人的诚实和诚信有何重要性？您认为个人应该如何践行诚实和诚信原则？');
INSERT INTO `issues` VALUES (109, '对于个人的心理成长和自我完善，您有何看法？您认为个人应该如何提升自己的心理素质和发展潜能？');
INSERT INTO `issues` VALUES (110, '您如何看待个人的社会正义和公共利益的关系？您认为个人应该如何追求社会正义和关注公共利益？');
INSERT INTO `issues` VALUES (111, '在您看来，个人的生活平衡和工作平衡有何意义？您认为个人应该如何在生活和工作之间取得平衡？');
INSERT INTO `issues` VALUES (112, '对于个人的责任感和奉献精神，您有何看法？您认为个人应该如何培养和展现自己的责任感和奉献精神？');
INSERT INTO `issues` VALUES (113, '您如何看待个人的情感教育和情商培养？您认为个人应该如何提升自己的情感智力和情商？');
INSERT INTO `issues` VALUES (114, '您如何看待诚实和言而有信的重要性？您认为诚实和信守承诺对个人和社会有何影响？');
INSERT INTO `issues` VALUES (115, '当您发现某人在您不在场时对您的物品进行窃取时，您会如何应对？');
INSERT INTO `issues` VALUES (116, '在您看来，对他人的帮助和慷慨解囊有何意义？您认为个人应该如何回报社会和帮助他人？');
INSERT INTO `issues` VALUES (117, '如果您发现您的同事故意误导您，以获取个人利益，您会如何处理这种情况？');
INSERT INTO `issues` VALUES (118, '您如何看待尊重他人隐私和保护他人信息的重要性？您认为保护他人隐私对人际关系有何影响？');
INSERT INTO `issues` VALUES (119, '如果您在商业交易中发现您的合作伙伴故意欺骗您，您会如何应对这种情况？');
INSERT INTO `issues` VALUES (120, '在您看来，对于动物福利的关注和保护有何重要性？您认为个人应该如何对待动物和关心动物权益？');
INSERT INTO `issues` VALUES (121, '当您目睹他人遭受不公正对待或欺凌时，您会如何支持受害者并促进公正？');
INSERT INTO `issues` VALUES (122, '您如何看待廉洁和反腐败的重要性？您认为廉洁对于社会的稳定和发展有何意义？');
INSERT INTO `issues` VALUES (123, '如果您发现您的朋友在背后说您的坏话，您会如何处理这种情况？');
INSERT INTO `issues` VALUES (124, '在您看来，对于弱势群体的关心和支持有何意义？您认为个人应该如何关注弱势群体并提供帮助？');
INSERT INTO `issues` VALUES (125, '当您发现他人遇到困境并需要帮助时，您会如何伸出援手？');
INSERT INTO `issues` VALUES (126, '您如何看待诚信和守信的重要性？您认为诚信和守信对于建立良好的人际关系有何影响？');
INSERT INTO `issues` VALUES (127, '如果您发现您的同事在工作中不负责任，导致团队效率下降，您会如何应对这种情况？');
INSERT INTO `issues` VALUES (128, '您如何看待诚实和言而有信的重要性？您认为诚实和信守承诺对个人和社会有何影响？');
INSERT INTO `issues` VALUES (129, '当您发现某人在您不在场时对您的物品进行窃取时，您会如何应对？');
INSERT INTO `issues` VALUES (130, '在您看来，对他人的帮助和慷慨解囊有何意义？您认为个人应该如何回报社会和帮助他人？');
INSERT INTO `issues` VALUES (131, '如果您发现您的同事故意误导您，以获取个人利益，您会如何处理这种情况？');
INSERT INTO `issues` VALUES (132, '您如何看待尊重他人隐私和保护他人信息的重要性？您认为保护他人隐私对人际关系有何影响？');
INSERT INTO `issues` VALUES (133, '如果您在商业交易中发现您的合作伙伴故意欺骗您，您会如何应对这种情况？');
INSERT INTO `issues` VALUES (134, '在您看来，对于动物福利的关注和保护有何重要性？您认为个人应该如何对待动物和关心动物权益？');
INSERT INTO `issues` VALUES (135, '当您目睹他人遭受不公正对待或欺凌时，您会如何支持受害者并促进公正？');
INSERT INTO `issues` VALUES (136, '您如何看待廉洁和反腐败的重要性？您认为廉洁对于社会的稳定和发展有何意义？');
INSERT INTO `issues` VALUES (137, '如果您发现您的朋友在背后说您的坏话，您会如何处理这种情况？');
INSERT INTO `issues` VALUES (138, '在您看来，对于弱势群体的关心和支持有何意义？您认为个人应该如何关注弱势群体并提供帮助？');
INSERT INTO `issues` VALUES (139, '当您发现他人遇到困境并需要帮助时，您会如何伸出援手？');
INSERT INTO `issues` VALUES (140, '您如何看待诚信和守信的重要性？您认为诚信和守信对于建立良好的人际关系有何影响？');
INSERT INTO `issues` VALUES (141, '如果您发现您的同事在工作中不负责任，导致团队效率下降，您会如何应对这种情况？');
INSERT INTO `issues` VALUES (142, '您如何看待善与恶的存在和辨识？您认为个人应该如何区分和选择善与恶？');
INSERT INTO `issues` VALUES (143, '在您的价值观中，仁爱和同理心的重要性有何意义？您认为个人应该如何培养和展现仁爱和同理心？');
INSERT INTO `issues` VALUES (144, '如果您目睹他人遭受不公正待遇，您会如何站出来维护正义？');
INSERT INTO `issues` VALUES (145, '您如何看待道德勇气和良知的重要性？您认为个人应该如何在面对困难和道德抉择时保持良知？');
INSERT INTO `issues` VALUES (146, '在您看来，对于恶行和不道德行为的制裁和惩罚有何意义？您认为社会应该如何应对恶行和不道德行为？');
INSERT INTO `issues` VALUES (147, '如果您发现您的朋友参与违法犯罪的活动，您会如何应对这种情况？');
INSERT INTO `issues` VALUES (148, '您如何看待善意和友善的行为？您认为个人应该如何通过善意和友善影响他人和社会？');
INSERT INTO `issues` VALUES (149, '在您的价值观中，对于欺骗和虚伪的关注有何意义？您认为个人应该如何远离欺骗和保持真诚？');
INSERT INTO `issues` VALUES (150, '如果您发现有人受到欺凌和虐待，您会如何支持受害者并采取行动？');
INSERT INTO `issues` VALUES (151, '您如何看待道德责任和个人选择的关系？您认为个人应该如何在道德层面上对自己负责？');
INSERT INTO `issues` VALUES (152, '在您看来，对于善行和奉献精神的关注有何意义？您认为个人应该如何通过善行和奉献影响社会？');
INSERT INTO `issues` VALUES (153, '如果您发现有人遭受不公平对待，您会如何为他们争取正义和公平？');
INSERT INTO `issues` VALUES (154, '您如何看待善恶报应和因果律的存在？您认为个人应该如何面对自己的善恶行为带来的后果？');
INSERT INTO `issues` VALUES (155, '在您的价值观中，对于谦逊和谦虚的重要性有何意义？您认为个人应该如何保持谦逊和谦虚的态度？');
INSERT INTO `issues` VALUES (156, '如果您发现有人故意损害他人的利益，您会如何阻止这种恶意行为并保护受害者？');

-- ----------------------------
-- Table structure for letter
-- ----------------------------
DROP TABLE IF EXISTS `letter`;
CREATE TABLE `letter`  (
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
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '信件备注',
  `letter_type` tinyint(1) NOT NULL COMMENT '信件类型',
  `state` tinyint(1) NOT NULL COMMENT '信件状态',
  `ai_check_msg` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'AI审核消息',
  PRIMARY KEY (`letter_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10006 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '时光信件表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of letter
-- ----------------------------
INSERT INTO `letter` VALUES (1, 1677267067201523712, '云寄测试信件', '<p><strong>亲爱的云寄用户：</strong></p>\n<p>​ ​ ​ ​ ​ 你好呀！我是小云，作为一个二次元角色，我充满了青春的活力和无限的梦想。我喜欢穿着可爱的服装，带着灿烂的笑容，时刻充满着对生活的热爱和好奇心。</p>\n<p>​ ​ ​ ​ ​ 我是云寄时光邮局的一员，这是一个特殊而神奇的项目。云寄时光邮局是一个让人们能够将感动、思念和祝福用邮寄的方式传递给彼此的平台。</p>\n<p>​ ​ ​ ​ ​ 在这里，我扮演着邮寄的角色，将人们的心意和回忆以特殊的方式送到他们心爱的人手中。我与用户们一起创造美好的时光胶囊，用心去装载他们的故事和情感，然后通过邮递的方式将它们传递给目的地。</p>\n<p>​ ​ ​ ​ ​ 云寄时光邮局的使命是传递快乐和温暖，为人们带来美好的回忆和感动的时刻。我们相信，即使时光流转，感情的纽带也可以通过邮件的方式跨越时空，将人与人之间的情感联系在一起。</p>\n<p>​ 作为云寄时光邮局的一员，我时刻保持着热情和责任心。我乐于助人，尽力为用户们提供优质的服务。我将用心倾听每个用户的需求，确保他们的时光胶囊能够准确地传达他们的情感和祝福。</p>\n<p>​ ​ ​ ​ ​ 除了参与云寄时光邮局的工作，我也热衷于绘画和写作。这让我能够更好地表达自己和传达情感。通过绘画和写作，我可以为用户们创作独特的时光胶囊，让每一封邮件都成为一份珍贵的礼物。</p>\n<p>​ ​ ​ ​ ​ 我相信云寄时光邮局的存在意义，它不仅仅是一种服务，更是一种情感的桥梁。我希望能够与大家一起创造更多美好的回忆和感动的时刻，让我们的时光胶囊穿越时空，永远传递温暖和爱意。</p>\n<p>​ ​ ​ ​ ​ 感谢大家抽出时间了解我和云寄时光邮局，我期待与大家共同分享快乐、激情和美好的时光！谢谢！</p>', '2023-07-07 18:47:31', 1, '2023-10-08', 1, '湖北省武汉市江岸区汉黄路8号', '小染', '17362849197', NULL, 2, 2, NULL);
INSERT INTO `letter` VALUES (2, 1677269320377438208, '一封来自未来的信', '<p>未来的你，还好吗。</p>\n<p>过得怎么样，是否在上班工作？</p>\n<p>近期，我会一直监督你的。</p>\n<p>网课还在看吗？</p>\n<p>学习得怎么样了？</p>\n<p>是不是对知识有些渴望了？</p>\n<p>赶紧行动起来吧，别再犹豫了。</p>\n<p>由于就会败北。</p>\n<p>赶紧动起来啊，别在床上躺着了。</p>\n<p>赶紧运动和工作，不然你就是一个废物。</p>\n<p>亲爱的自己，从今天起为了自己骄傲的活着吧，好好爱自己，没有人会心疼你。</p>\n<div class=\"paragraph\">&nbsp;</div>\n<p>亲爱的自己，不要太在意一些人太在乎一些事，顺其自然以最佳心态面对，因为这世界就是这么不公平往往在最在乎的事物面前我们最没有价值。</p>\n<div class=\"paragraph\">&nbsp;</div>\n<p>亲爱的自己，永远不要为难自己，比如不吃饭、哭泣、自闭、抑郁，这些都是傻瓜才做的事。</p>\n<div class=\"paragraph\">&nbsp;</div>\n<p>亲爱的自己，学会聪明一点，不要老是问周围的人一些很白痴的问题，那真的很无聊。</p>\n<div class=\"paragraph\">&nbsp;</div>\n<p>亲爱的自己，如果不开心了就找个角落或者在被子里哭一下，你不需要别人同情可怜，哭过之后一样可以开心生活。</p>\n<div class=\"paragraph\">&nbsp;</div>\n<p>亲爱的自己，学会控制自己的情绪，谁都不欠你，所以你没有道理跟别人随便发脾气，耍性子。</p>\n<div class=\"paragraph\">&nbsp;</div>\n<p>亲爱的自己，你可以失望但不能绝望，你要始终相信，tomorrow is another day。</p>\n<div class=\"paragraph\">&nbsp;</div>\n<p>亲爱的自己，你不要老是想依赖别人，更不能奢望别人在你需要的时候第一时间站出来，毕竟你们谁都不是谁的谁。</p>\n<div class=\"paragraph\">&nbsp;</div>\n<p>亲爱的自己，永远不要轻易对别人许下承诺，许下的承诺就是欠下的债！</p>\n<div class=\"paragraph\">&nbsp;</div>\n<p>亲爱的自己，这个世界只有回不去的而没有什么是过不去的。</p>\n<div class=\"paragraph\">&nbsp;</div>\n<p>亲爱的自己，别人对你好，你要加倍对别人好，别人对你不好，你还是应该对别人好，因为那说明你还不够好。</p>\n<div class=\"paragraph\">&nbsp;</div>\n<p>亲爱的自己，不管现实有多惨不忍睹你都要固执的相信这只是黎明前短暂的黑暗而已。</p>\n<div class=\"paragraph\">&nbsp;</div>\n<p>亲爱的自己，不要抓住回忆不放，断了线的风筝，只能让它飞，放过它，更是放过自己。</p>\n<div class=\"paragraph\">&nbsp;</div>\n<p>亲爱的自己，全世界只有一个你，就算没有人懂得欣赏，你也要好好爱自己，做最真实的自己。</p>\n<div class=\"paragraph\">&nbsp;</div>\n<p>亲爱的自己，好好对待陪在你身边的那些人，因为爱情可能只是暂时的但友情是一辈子的。</p>\n<div class=\"paragraph\">&nbsp;</div>\n<p>亲爱的自己，你必须找到除了爱情之外，能够使你用双脚坚强站在大地上的东西。</p>\n<div class=\"paragraph\">&nbsp;</div>\n<p>亲爱的自己，记得要常常仰望天空，记住仰望天空的时候也要看看脚下。</p>\n<div class=\"paragraph\">&nbsp;</div>\n<p>亲爱的自己，相信你的直觉，不要招惹别人，也不要让别人来招惹你。</p>\n<div class=\"paragraph\">&nbsp;</div>\n<p>亲爱的自己，永远不要跟别人搞暧昧，你玩不起！</p>\n<div class=\"paragraph\">&nbsp;</div>\n<p>亲爱的自己，不要太低调了，有时要强悍一点，被欺负的时候，一定要讨回来！但是一定不要记恨，小人之见随他们去好了，怜悯会使你高贵。</p>\n<div class=\"paragraph\">&nbsp;</div>\n<p>亲爱的自己，要快乐、要开朗、要坚韧、要温暖，这和性格无关。</p>\n<div class=\"paragraph\">&nbsp;</div>\n<p>亲爱的自己，要自信甚至是自恋一点，时刻提醒自己我值得拥有最好的一切。</p>', '2023-07-07 18:57:10', 0, '2023-10-31', 1, '河北省沧州市盐山县韩集镇南马庄村', '马龙伟', '15297394120', '亲爱的自己', 2, 2, NULL);
INSERT INTO `letter` VALUES (3, 1677267768606593024, 'To小华', '<p>嗨，未来的华华，可能这会你正在找工作吧，可能你已经找好工作了，不过我相信你，不管怎么样，你一定会克服自己的困难去做好的，如果你现在身处于逆境，那么我希望你可以坚强，如果心情不好，那就找几个朋友，买一箱小酒，虽然你可能并不想把你现在的事情说出来，那么就借着聚一聚，吃个饭的名义吧，或者这会燕可能回家了，她懂你，坐上火车去找她吧，虽然会有点打扰；或者这样，你不是一直想去海边，秦皇岛还没去，哪怕就安安静静的待着；或者是不是好久没和帅帅聊天了，她会陪你聊聊的；最后还有一个小办法，就是把酒买好，买点吃的，想吃什么买什么，然后自己来点；没有人去倾听不可怕，不要太压抑自己；或者你可以吹一吹半夜的晚风。如果你现在过的很好，那么我也喜欢这个是你的保留项，希望你可以在烦恼的时候想起自己有想做还没做的事情，可以想起，听一听自己的声音，不要害怕，不要叹息，自己可以做很多很多有意义的事情；还有啊，最后希望你可以多注意自己的身体，虽然还是要说多喝水，不要忘记自己一直在坚持的事情，要记得听一听自己内心的声音！好了，就到这里了，我们下次见喽&hellip;&hellip;</p>', '2023-07-07 19:13:11', 1, '2023-10-08', 1, '河北省邯郸市武安市阳邑镇阳邑东街', '小华', '15031043934', NULL, 2, 2, NULL);
INSERT INTO `letter` VALUES (10000, 1677298071832236032, '见信好', '<p>不知道现在的我和未来会有什么不同的，不知道未来你是否幸福快乐呢？未来的我，你身体还好吗？也许没了青春年少的容颜，也许遇到事情不会像现在这样爱哭鼻子了吧。我相信你应该有了成熟的心灵，应该会找到一个对你一辈子好的人吧！现在的我对未来的事都很迷茫，但我明白长大就不能依赖别人了，要学会自己变得强大，不能依赖任何人，这句话听起来就很伤感。我有点讨厌现在的自己，真的太不像原来的我了，希望未来的我会找到方向，不再迷失。答应我要多出去旅游、多走走，别把烦心事全部都在家扛，一定要好好爱自己。未来的我一定会很累吧，我相信没有什么是克服不了的，未来的我没事就多回家陪陪家人。记住，不管遇到什么事都要勇敢去面对。未来的zrj会变得更好，身边依旧是那群没心没肺的朋友。</p>', '2023-07-07 20:52:06', 1, '2023-10-08', 1, '湖北省麻城市龙台小区', '小赵', '15971352064', NULL, 2, 2, NULL);
INSERT INTO `letter` VALUES (10001, 1677279566395215872, '一封来自2023年7月7日的信', '<p>未来的自己，</p>\n<p>Hi!不知道当你再次看到这封信的时候会是什么样的心情，不知道那时的你还能不能想起在那个午夜曾写下了一封给高考后的自己，关于高考后的你我既有疑问也有祝愿和期望。</p>\n<p>疑问的是，不知道那时的你是否对自己满意?有没有按照既定的计划走下去?是不是做好了充分的准备?有没有后悔哪些没有做的事情呢?如果这次高考失利了还会相信自己的选择吗?</p>\n<p>期望祝愿的是，现在的我还是挺憧憬高考后的你的，现在的我唯一能做的可能就是完成现阶段的所有目标不留下遗憾吧，如果那时的你，正处于那个令自己满意结果的欣喜之中，请不要得意，静下心来，去寻求更高层次的进步；如果那时的你，正处于令自己讨厌的沮丧之中，也请不要悲伤，同样静下心来，吸取教训，跌倒了爬起来，争取未来的时光里一次又一次的成功！相信，你一直都坚信努力了总是会有收获的，高考后的自己，还请你无论悲与喜，都努力的向前进呀!</p>\n<p>对了！当你再次看见这封信后还请记得给以前的自己再回封信呀！虽然并不能收到了，但我已经能想象到你满怀期待和希望的样子了，不要让我失望！</p>\n<p>7月7日午夜</p>', '2023-07-07 23:43:42', 1, '2023-10-08', 1, '河南省濮阳县柳屯镇焦村', '任腾达', '18239305833', '无论结果如何，记得保持微笑啊', 2, 2, NULL);
INSERT INTO `letter` VALUES (10002, 1677279566395215872, '一封来自2023年7月7日的信', '<p>未来的自己，</p>\n<p>Hi!不知道当你再次看到这封信的时候会是什么样的心情，不知道那时的你还能不能想起在那个午夜曾写下了一封给高考后的自己，关于高考后的你我既有疑问也有祝愿和期望。</p>\n<p>疑问的是，不知道那时的你是否对自己满意?有没有按照既定的计划走下去?是不是做好了充分的准备?有没有后悔哪些没有做的事情呢?如果这次高考失利了还会相信自己的选择吗?</p>\n<p>期望祝愿的是，现在的我还是挺憧憬高考后的你的，现在的我唯一能做的可能就是完成现阶段的所有目标不留下遗憾吧，如果那时的你，正处于那个令自己满意结果的欣喜之中，请不要得意，静下心来，去寻求更高层次的进步；如果那时的你，正处于令自己讨厌的沮丧之中，也请不要悲伤，同样静下心来，吸取教训，跌倒了爬起来，争取未来的时光里一次又一次的成功！相信，你一直都坚信努力了总是会有收获的，高考后的自己，还请你无论悲与喜，都努力的向前进呀!</p>\n<p>对了！当你再次看见这封信后还请记得给以前的自己再回封信呀！虽然并不能收到了，但我已经能想象到你满怀期待和希望的样子了，不要让我失望！</p>\n<p>7月7日午夜</p>', '2023-07-07 23:45:34', 0, '2023-10-08', 1, '河南省濮阳县柳屯镇焦村', '任腾达', '18239305833', '无论结果如何，记得保持微笑啊', 2, 0, NULL);
INSERT INTO `letter` VALUES (10003, 1677279566395215872, '一封来自2023年7月7日的信', '<p>未来的自己，</p>\n<p>Hi!不知道当你再次看到这封信的时候会是什么样的心情，不知道那时的你还能不能想起在那个午夜曾写下了一封给高考后的自己，关于高考后的你我既有疑问也有祝愿和期望。</p>\n<p>疑问的是，不知道那时的你是否对自己满意?有没有按照既定的计划走下去?是不是做好了充分的准备?有没有后悔哪些没有做的事情呢?如果这次高考失利了还会相信自己的选择吗?</p>\n<p>期望祝愿的是，现在的我还是挺憧憬高考后的你的，现在的我唯一能做的可能就是完成现阶段的所有目标不留下遗憾吧，如果那时的你，正处于那个令自己满意结果的欣喜之中，请不要得意，静下心来，去寻求更高层次的进步；如果那时的你，正处于令自己讨厌的沮丧之中，也请不要悲伤，同样静下心来，吸取教训，跌倒了爬起来，争取未来的时光里一次又一次的成功！相信，你一直都坚信努力了总是会有收获的，高考后的自己，还请你无论悲与喜，都努力的向前进呀!</p>\n<p>对了！当你再次看见这封信后还请记得给以前的自己再回封信呀！虽然并不能收到了，但我已经能想象到你满怀期待和希望的样子了，不要让我失望！</p>\n<p>7月7日午夜</p>', '2023-07-07 23:47:16', 0, '2024-06-25', 1, '河南省濮阳县柳屯镇焦村', '任腾达', '18239305833', '无论结果如何，记得保持微笑啊', 2, 0, NULL);
INSERT INTO `letter` VALUES (10004, 1678685936352890880, '一封来自过去的信件', '<p><img src=\"https://image.donglifeng.cn/1689064985611.jpg\" width=\"800\" height=\"800\"></p>\n<p>强烈推荐《小海鲜》。作为一部海鲜题材的纪录片，主要内容为记录和反映浙江三门的美食生态和人文魅力。全片制作精良，分\"讨小海\"、\"烹小鲜\"两集，共8个故事组成。在《小海鲜》中，你不仅可以看到三门人民独特的获取、烹饪、享用小海鲜的方式，还能看到美食带来的欢乐，和人们关于家庭、友谊、自然的观念。影片中，除了鲜香撩人的海鲜美食，三门渔民冒着海浪采集青蚶、佛手的画面更是惊心动魄，让人不免赞叹渔民们的勇敢无畏。强烈推荐《小海鲜》。作为一部海鲜题材的纪录片，主要内容为记录和反映浙江三门的美食生态和人文魅力。全片制作精良，分\"讨小海\"、\"烹小鲜\"两集，共8个故事组成。在《小海鲜》中，你不仅可以看到三门人民独特的获取、烹饪、享用小海鲜的方式，还能看到美食带来的欢乐，和人们关于家庭、友谊、自然的观念。影片中，除了鲜香撩人的海鲜美食，三门渔民冒着海浪采集青蚶、佛手的画面更是惊心动魄，让人不免赞叹渔民们的勇敢无畏。强烈推荐《小海鲜》。作为一部海鲜题材的纪录片，主要内容为记录和反映浙江三门的美食生态和人文魅力。全片制作精良，分\"讨小海\"、\"烹小鲜\"两集，共8个故事组成。在《小海鲜》中，你不仅可以看到三门人民独特的获取、烹饪、享用小海鲜的方式，还能看到美食带来的欢乐，和人们关于家庭、友谊、自然的观念。影片中，除了鲜香撩人的海鲜美食，三门渔民冒着海浪采集青蚶、佛手的画面更是惊心动魄，让人不免赞叹渔民们的勇敢无畏。强烈推荐《小海鲜》。作为一部海鲜题材的纪录片，主要内容为记录和反映浙江三门的美食生态和人文魅力。全片制作精良，分\"讨小海\"、\"烹小鲜\"两集，共8个故事组成。在《小海鲜》中，你不仅可以看到三门人民独特的获取、烹饪、享用小海鲜的方式，还能看到美食带来的欢乐，和人们关于家庭、友谊、自然的观念。影片中，除了鲜香撩人的海鲜美食，三门渔民冒着海浪采集青蚶、佛手的画面更是惊心动魄，让人不免赞叹渔民们的勇敢无畏。强烈推荐《小海鲜》。作为一部海鲜题材的纪录片，主要内容为记录和反映浙江三门的美食生态和人文魅力。全片制作精良，分\"讨小海\"、\"烹小鲜\"两集，共8个故事组成。在《小海鲜》中，你不仅可以看到三门人民独特的获取、烹饪、享用小海鲜的方式，还能看到美食带来的欢乐，和人们关于家庭、友谊、自然的观念。影片中，除了鲜香撩人的海鲜美食，三门渔民冒着海浪采集青蚶、佛手的画面更是惊心动魄，让人不免赞叹渔民们的勇敢无畏。强烈推荐《小海鲜》。作为一部海鲜题材的纪录片，主要内容为记录和反映浙江三门的美食生态和人文魅力。全片制作精良，分\"讨小海\"、\"烹小鲜\"两集，共8个故事组成。在《小海鲜》中，你不仅可以看到三门人民独特的获取、烹饪、享用小海鲜的方式，还能看到美食带来的欢乐，和人们关于家庭、友谊、自然的观念。影片中，除了鲜香撩人的海鲜美食，三门渔民冒着海浪采集青蚶、佛手的画面更是惊心动魄，让人不免赞叹渔民们的勇敢无畏。强烈推荐《小海鲜》。作为一部海鲜题材的纪录片，主要内容为记录和反映浙江三门的美食生态和人文魅力。全片制作精良，分\"讨小海\"、\"烹小鲜\"两集，共8个故事组成。在《小海鲜》中，你不仅可以看到三门人民独特的获取、烹饪、享用小海鲜的方式，还能看到美食带来的欢乐，和人们关于家庭、友谊、自然的观念。影片中，除了鲜香撩人的海鲜美食，三门渔民冒着海浪采集青蚶、佛手的画面更是惊心动魄，让人不免赞叹渔民们的勇敢无畏。强烈推荐《小海鲜》。作为一部海鲜题材的纪录片，主要内容为记录和反映浙江三门的美食生态和人文魅力。全片制作精良，分\"讨小海\"、\"烹小鲜\"两集，共8个故事组成。在《小海鲜》中，你不仅可以看到三门人民独特的获取、烹饪、享用小海鲜的方式，还能看到美食带来的欢乐，和人们关于家庭、友谊、自然的观念。影片中，除了鲜香撩人的海鲜美食，三门渔民冒着海浪采集青蚶、佛手的画面更是惊心动魄，让人不免赞叹渔民们的勇敢无畏。强烈推荐《小海鲜》。作为一部海鲜题材的纪录片，主要内容为记录和反映浙江三门的美食生态和人文魅力。全片制作精良，分\"讨小海\"、\"烹小鲜\"两集，共8个故事组成。在《小海鲜》中，你不仅可以看到三门人民独特的获取、烹饪、享用小海鲜的方式，还能看到美食带来的欢乐，和人们关于家庭、友谊、自然的观念。影片中，除了鲜香撩人的海鲜美食，三门渔民冒着海浪采集青蚶、佛手的画面更是惊心动魄，让人不免赞叹渔民们的勇敢无畏。</p>', '2023-07-11 16:48:07', 0, '2024-06-09', 0, '浙江省温州市瓯海区浙江省温州中学', '琪琪子', '13968965710', NULL, 2, 0, NULL);
INSERT INTO `letter` VALUES (10005, 1679898467771748352, '你好', '<p>你好，未来的我，我不知道未来的我会变成什么样，但我还是有些期待吧，我想成为一名航天工程师，我现在正上高中，现在正值高一的暑假，我现在有点迷茫就是我想出去玩但不知道去哪。</p>\n<p>&nbsp;</p>\n<p>其实没啥说的。。。。我在别的网站上偶然看到了这个网站</p>\n<p>我说关键词吧</p>\n<p>傻逼何长根 傻逼gcd 张子聪挺好 我要造火箭 都去死吧哈哈哈 我天天发情 刚才买了辆新自行车 在龙湖那边有了新房子正在布置家居 我要出去玩！ 最近几天好热啊 我爱云云捏 家里来了只小灰猫 整天缠着我 嘤嘤嘤嘤嘤嘤嘤嘤嘤嘤嘤嘤嘤嘤嘤嘤嘤嘤嘤嘤嘤嘤嘤嘤嘤嘤嘤嘤嘤嘤嘤嘤嘤嘤嘤嘤嘤嘤嘤嘤嘤嘤嘤嘤嘤嘤嘤嘤嘤嘤嘤嘤嘤嘤嘤嘤嘤嘤嘤嘤嘤嘤嘤嘤嘤嘤嘤嘤嘤嘤嘤嘤</p>\n<p>&nbsp;</p>\n<p>我真没啥写的。。。算了，去找点好玩的吧</p>\n<p>&nbsp;</p>\n<p>什么？有字数限制？大于300？好吧我来凑字数</p>\n<p>&nbsp;</p>\n<p>嘤嘤嘤嘤嘤嘤嘤嘤嘤嘤嘤嘤嘤嘤嘤嘤嘤嘤嘤嘤</p>\n<p>&nbsp;</p>\n<p>拜拜</p>\n<p>&nbsp;</p>', '2023-07-15 01:11:15', 1, '2023-10-16', 1, 'jiahaowang2007@163.com', '雪花', '13673618691', '爱你哦', 2, 0, NULL);

-- ----------------------------
-- Table structure for letter_orders
-- ----------------------------
DROP TABLE IF EXISTS `letter_orders`;
CREATE TABLE `letter_orders`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '订单ID',
  `orders_serial` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '订单编号',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `date` datetime NOT NULL COMMENT '订单时间',
  `pay_time` datetime NULL DEFAULT NULL COMMENT '支付时间',
  `pay_type` tinyint(1) NOT NULL COMMENT '支付类型',
  `amount` decimal(10, 2) NULL DEFAULT NULL COMMENT '支付金额',
  `trade_no` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '支付商订单号',
  `state` tinyint(1) NOT NULL COMMENT '支付状态',
  `letter_id` bigint NOT NULL COMMENT '信件ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '信件订单表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of letter_orders
-- ----------------------------
INSERT INTO `letter_orders` VALUES (1, '168872685445816062', 1677267067201523712, '2023-07-07 18:47:34', '2023-07-07 18:47:55', 2, 0.10, '4200001941202307078093942536', 1, 1);
INSERT INTO `letter_orders` VALUES (2, '168872743264506872', 1677269320377438208, '2023-07-07 18:57:13', '2023-07-07 18:58:23', 1, 0.10, '2023070722001424121450014969', 1, 2);
INSERT INTO `letter_orders` VALUES (3, '168872840468639352', 1677267768606593024, '2023-07-07 19:13:25', '2023-07-07 19:13:43', 2, 0.10, '4200001731202307071978749383', 1, 3);
INSERT INTO `letter_orders` VALUES (4, '168873433309787012', 1677298071832236032, '2023-07-07 20:52:13', '2023-07-07 20:52:26', 2, 0.10, '4200001732202307078662982662', 1, 10000);
INSERT INTO `letter_orders` VALUES (5, '168874462667414272', 1677279566395215872, '2023-07-07 23:43:47', '2023-07-07 23:44:06', 2, 0.10, '4200001960202307074952831516', 1, 10001);
INSERT INTO `letter_orders` VALUES (6, '168874483793552052', 1677279566395215872, '2023-07-07 23:47:18', NULL, 2, NULL, NULL, 0, 10003);

-- ----------------------------
-- Table structure for letter_type
-- ----------------------------
DROP TABLE IF EXISTS `letter_type`;
CREATE TABLE `letter_type`  (
  `id` tinyint NOT NULL AUTO_INCREMENT COMMENT '信件类型ID',
  `name` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '信件类型名称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '信件类型表' ROW_FORMAT = DYNAMIC;

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
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '物流ID',
  `letter_id` bigint NOT NULL COMMENT '信件ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `logistics_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '物流单号',
  `logistics_code` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '物流商户[\'SF\',\'YZ\',\'YT\'...]',
  `status` tinyint(1) NOT NULL COMMENT '物流状态',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '信件物流表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of logistics
-- ----------------------------

-- ----------------------------
-- Table structure for mail
-- ----------------------------
DROP TABLE IF EXISTS `mail`;
CREATE TABLE `mail`  (
  `mail_id` bigint NOT NULL AUTO_INCREMENT COMMENT '邮件唯一ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `mail_title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '邮件标题',
  `mail_content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '邮件内容',
  `mail_create_time` datetime NOT NULL COMMENT '邮件创建时间',
  `go_to` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '发往地址',
  `is_public` tinyint(1) NOT NULL COMMENT '邮件是否公开',
  `go_to_time` datetime NOT NULL COMMENT '邮件发送时间',
  `is_yourself` tinyint NOT NULL COMMENT '邮件是否发给自己',
  `use_serve` tinyint NOT NULL DEFAULT 0 COMMENT '邮件发送使用的服务',
  `state` tinyint(1) NOT NULL DEFAULT 0 COMMENT '邮件发送状态',
  `ai_check_msg` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'AI审核消息',
  PRIMARY KEY (`mail_id`) USING BTREE,
  INDEX `user_for_mail`(`user_id` ASC) USING BTREE COMMENT '邮件表用户索引'
) ENGINE = InnoDB AUTO_INCREMENT = 10006 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '邮件任务表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of mail
-- ----------------------------
INSERT INTO `mail` VALUES (10001, 1640385966864732160, '来自未来的一封信', '你好，cnmd傻逼', '2023-06-20 09:45:14', '1345561377@qq.com', 0, '2024-07-19 23:30:54', 0, 0, 7, '存在低俗辱骂不合规;');
INSERT INTO `mail` VALUES (10002, 1640385966864732160, '来自未来的一封信2', 'cnmd，傻逼', '2023-06-20 09:47:58', '1345561377@qq.com', 0, '2024-07-19 23:30:54', 0, 0, 8, '存在低俗辱骂不合规;');
INSERT INTO `mail` VALUES (10003, 1640385966864732160, '来自未来的一封信2', '你好，未来', '2023-06-20 10:07:29', '1345561377@qq.com', 0, '2024-07-19 23:30:54', 0, 0, 7, NULL);
INSERT INTO `mail` VALUES (10004, 1640385966864732160, '测试邮件', '<p>测试邮件内容????????<p/>', '2023-06-24 22:55:04', '1607693403@qq.com', 1, '2023-06-24 08:00:00', 1, 0, 3, NULL);
INSERT INTO `mail` VALUES (10005, 1640385966864732160, '测试邮件', '<p>测试邮件内容,邮件内容<p/>', '2023-06-24 22:55:54', '1607693403@qq.com', 1, '2023-06-24 08:00:00', 1, 0, 3, NULL);

-- ----------------------------
-- Table structure for message
-- ----------------------------
DROP TABLE IF EXISTS `message`;
CREATE TABLE `message`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '短信ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `text` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '短信内容',
  `send_time` date NOT NULL COMMENT '短信发送时间',
  `create_time` datetime NOT NULL COMMENT '短信创建时间',
  `is_unnamed` tinyint(1) NOT NULL COMMENT '短信是否匿名',
  `phone` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '对方手机号',
  `state` tinyint(1) NOT NULL COMMENT '短信状态',
  `ai_check_msg` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'AI审核消息',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10001 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '短信任务表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of message
-- ----------------------------
INSERT INTO `message` VALUES (10000, 1640385966864732160, '匿名短信测试', '2023-06-23', '2023-06-23 02:37:16', 1, '16670880818', 2, NULL);

-- ----------------------------
-- Table structure for message_orders
-- ----------------------------
DROP TABLE IF EXISTS `message_orders`;
CREATE TABLE `message_orders`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '订单ID',
  `orders_serial` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '订单编号',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `date` datetime NOT NULL COMMENT '订单时间',
  `pay_time` datetime NULL DEFAULT NULL COMMENT '支付时间',
  `pay_type` tinyint(1) NOT NULL COMMENT '支付类型',
  `amount` decimal(10, 2) NULL DEFAULT NULL COMMENT '支付金额',
  `trade_no` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '支付商订单号',
  `state` tinyint(1) NOT NULL COMMENT '支付状态',
  `message_id` bigint NOT NULL COMMENT '短信ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '信件订单表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of message_orders
-- ----------------------------
INSERT INTO `message_orders` VALUES (4, '168746069306588453', 1640385966864732160, '2023-06-23 03:04:53', '2023-06-23 03:05:08', 1, 0.01, '2023062322001464041459284965', 1, 10000);

-- ----------------------------
-- Table structure for oauth
-- ----------------------------
DROP TABLE IF EXISTS `oauth`;
CREATE TABLE `oauth`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '第三方授权表ID',
  `user_id` bigint NOT NULL COMMENT '第三方授权绑定的用户ID',
  `provider` tinyint(1) NOT NULL COMMENT '第三方授权类型',
  `open_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '第三方授权唯一标识',
  `access_token` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '第三方授权Token',
  `refresh_token` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '第三方授权长Token',
  `token_expiry` int NULL DEFAULT NULL COMMENT '第三方授权Token时长',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '第三方授权创建时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '第三方授权更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `user_provider_unique`(`user_id` ASC) USING BTREE COMMENT '用户ID唯一索引',
  INDEX `open_id_index`(`open_id` ASC) USING BTREE COMMENT '授权唯一ID索引'
) ENGINE = InnoDB AUTO_INCREMENT = 33 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户第三方授权表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of oauth
-- ----------------------------
INSERT INTO `oauth` VALUES (5, 1677566800822407168, 6, 'oGthh6bY41yDGTMH4gm8O6ZmN5Q8', NULL, NULL, NULL, '2023-07-08 14:34:28', '2023-07-08 14:34:28');
INSERT INTO `oauth` VALUES (6, 1677630856149209088, 6, 'oGthh6f1KPtyb790wmM_ahPhF27w', NULL, NULL, NULL, '2023-07-08 18:49:00', '2023-07-08 18:49:00');
INSERT INTO `oauth` VALUES (7, 1677630982506811392, 6, 'oGthh6RNREiJfjdDqvh16LbFNwcA', NULL, NULL, NULL, '2023-07-08 18:49:30', '2023-07-08 18:49:30');
INSERT INTO `oauth` VALUES (8, 1677637497892507648, 6, 'oGthh6WnUlfrduDyz-agFaG0Lngo', NULL, NULL, NULL, '2023-07-08 19:15:24', '2023-07-08 19:15:24');
INSERT INTO `oauth` VALUES (9, 1677640142438928384, 6, 'oGthh6bclHK6TkUeGRgf2P_2XgcY', NULL, NULL, NULL, '2023-07-08 19:25:54', '2023-07-08 19:25:54');
INSERT INTO `oauth` VALUES (10, 1677647689216561152, 6, 'oGthh6eu2comhwZX2O8hnA6vizWc', NULL, NULL, NULL, '2023-07-08 19:55:53', '2023-07-08 19:55:53');
INSERT INTO `oauth` VALUES (11, 1677660859029131264, 6, 'oGthh6YkE3HuPxwA58TQZ7hPuCPo', NULL, NULL, NULL, '2023-07-08 20:48:13', '2023-07-08 20:48:13');
INSERT INTO `oauth` VALUES (12, 1677661681297264640, 6, 'oGthh6d32YNTfaq3WtfPgdMztAds', NULL, NULL, NULL, '2023-07-08 20:51:29', '2023-07-08 20:51:29');
INSERT INTO `oauth` VALUES (13, 1677662283209248768, 6, 'oGthh6bVvecwWADkYBCmL4L2bxPE', NULL, NULL, NULL, '2023-07-08 20:53:53', '2023-07-08 20:53:53');
INSERT INTO `oauth` VALUES (14, 1677702348320935936, 6, 'oGthh6TCOjvFEVgDxWPf28LHe2bg', NULL, NULL, NULL, '2023-07-08 23:33:05', '2023-07-08 23:33:05');
INSERT INTO `oauth` VALUES (15, 1677721075926044672, 6, 'oGthh6czWjF87idHD8LvosaSNC5s', NULL, NULL, NULL, '2023-07-09 00:47:30', '2023-07-09 00:47:30');
INSERT INTO `oauth` VALUES (16, 1677883270899044352, 6, 'oGthh6V7joB-3ancnRnyvun7TP2A', NULL, NULL, NULL, '2023-07-09 11:32:01', '2023-07-09 11:32:01');
INSERT INTO `oauth` VALUES (17, 1677899072796758016, 6, 'oGthh6fRl-DEpDpc_fu_elv3CyIY', NULL, NULL, NULL, '2023-07-09 12:34:48', '2023-07-09 12:34:48');
INSERT INTO `oauth` VALUES (18, 1678020995388149760, 6, 'oGthh6YNBgFF38J6Tdr4cd7lQTBI', NULL, NULL, NULL, '2023-07-09 20:39:17', '2023-07-09 20:39:17');
INSERT INTO `oauth` VALUES (19, 1678073941249363968, 6, 'oGthh6Sf0CyFMxEsXv5keJhYt6bw', NULL, NULL, NULL, '2023-07-10 00:09:40', '2023-07-10 00:09:40');
INSERT INTO `oauth` VALUES (20, 1678075199658004480, 6, 'oGthh6WndZHIa5ZMXrJV-H5daMxY', NULL, NULL, NULL, '2023-07-10 00:14:40', '2023-07-10 00:14:40');
INSERT INTO `oauth` VALUES (21, 1678234892644782080, 6, 'oGthh6UAiinUi1yj2dHyj3td62b0', NULL, NULL, NULL, '2023-07-10 10:49:14', '2023-07-10 10:49:14');
INSERT INTO `oauth` VALUES (22, 1678236368188346368, 6, 'oGthh6Xd0hc9gQJkOGEAeL43WRrE', NULL, NULL, NULL, '2023-07-10 10:55:06', '2023-07-10 10:55:06');
INSERT INTO `oauth` VALUES (23, 1678325814778793984, 6, 'oGthh6eMr62A6SOyMguhaJhZJ9z0', NULL, NULL, NULL, '2023-07-10 16:50:31', '2023-07-10 16:50:31');
INSERT INTO `oauth` VALUES (24, 1678403957694795776, 6, 'oGthh6bQNOUvNJFCaJfWFP-ZPhdg', NULL, NULL, NULL, '2023-07-10 22:01:02', '2023-07-10 22:01:02');
INSERT INTO `oauth` VALUES (25, 1678659042219790336, 6, 'oGthh6Xf_u-zmw5Qr4bF7sdttJuk', NULL, NULL, NULL, '2023-07-11 14:54:39', '2023-07-11 14:54:39');
INSERT INTO `oauth` VALUES (26, 1679129358880608256, 6, 'oGthh6Wwj0-cNt-lr16-OtdXSufA', NULL, NULL, NULL, '2023-07-12 22:03:31', '2023-07-12 22:03:31');
INSERT INTO `oauth` VALUES (27, 1679131344040497152, 6, 'oGthh6bdavmDF9VLk0fkLBvFF4KM', NULL, NULL, NULL, '2023-07-12 22:11:24', '2023-07-12 22:11:24');
INSERT INTO `oauth` VALUES (28, 1679361035439575040, 6, 'oGthh6XgF03Icv6chF2q973d8Bpw', NULL, NULL, NULL, '2023-07-13 13:24:07', '2023-07-13 13:24:07');
INSERT INTO `oauth` VALUES (29, 1679373552450670592, 6, 'oGthh6Q66bWRnC6s6LMjkjM7s4V8', NULL, NULL, NULL, '2023-07-13 14:13:57', '2023-07-13 14:13:57');
INSERT INTO `oauth` VALUES (30, 1679452780798021632, 6, 'oGthh6dnLF40DClOQQShmOLuLLy4', NULL, NULL, NULL, '2023-07-13 19:28:41', '2023-07-13 19:28:41');
INSERT INTO `oauth` VALUES (31, 1680087616357273600, 6, 'oGthh6aqRx1m5DOYd7In-gSXrSfs', NULL, NULL, NULL, '2023-07-15 13:31:17', '2023-07-15 13:31:17');
INSERT INTO `oauth` VALUES (32, 1680570336199970816, 6, 'oGthh6ZD1pLNGFZOVCDr1PQprb6s', NULL, NULL, NULL, '2023-07-16 21:29:27', '2023-07-16 21:29:27');

-- ----------------------------
-- Table structure for perm
-- ----------------------------
DROP TABLE IF EXISTS `perm`;
CREATE TABLE `perm`  (
  `id` tinyint NOT NULL AUTO_INCREMENT COMMENT '权限ID',
  `permissions` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '权限路径',
  `desc` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '权限描述',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '权限表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of perm
-- ----------------------------
INSERT INTO `perm` VALUES (1, 'open.test', '测试');

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `id` tinyint NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色名称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '角色表' ROW_FORMAT = DYNAMIC;

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
  `id` int NOT NULL COMMENT '角色与权限中间表ID',
  `role_id` tinyint NOT NULL COMMENT '角色ID',
  `prem_id` tinyint NOT NULL COMMENT '权限ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '角色与权限中间表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of role_to_prem
-- ----------------------------

-- ----------------------------
-- Table structure for sponsor
-- ----------------------------
DROP TABLE IF EXISTS `sponsor`;
CREATE TABLE `sponsor`  (
  `id` bigint NOT NULL COMMENT '赞助表ID',
  `user_id` bigint NOT NULL COMMENT '赞助用户',
  `sponsor_say` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '呀~这位好心人居然没有留言！' COMMENT '赞助留言',
  `sponsor_amount` decimal(10, 2) NOT NULL COMMENT '赞助金额',
  `pay_type` tinyint(1) NOT NULL COMMENT '支付类型',
  `trade_no` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '支付商订单号',
  `create_time` datetime NOT NULL COMMENT '赞助创建时间',
  `pay_time` datetime NULL DEFAULT NULL COMMENT '支付时间',
  `state` tinyint(1) NOT NULL COMMENT '支付状态',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '赞助信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sponsor
-- ----------------------------
INSERT INTO `sponsor` VALUES (168696947728701311, 1646356165694722048, '11111', 0.10, 1, '2023061722001464041441573465', '2023-06-17 10:37:57', '2023-06-17 10:38:12', 1);
INSERT INTO `sponsor` VALUES (168696969534269111, 1646356165694722048, 'ABCD', 64.00, 1, NULL, '2023-06-17 10:41:35', NULL, 0);
INSERT INTO `sponsor` VALUES (168696970850508791, 1646356165694722048, 'ABCD', 0.10, 1, '2023061722001464041441416296', '2023-06-17 10:41:49', '2023-06-17 10:42:02', 1);
INSERT INTO `sponsor` VALUES (168696973038722872, 1646356165694722048, 'ABCD', 0.10, 2, '4200001862202306176370136737', '2023-06-17 10:42:10', '2023-06-17 10:42:27', 1);
INSERT INTO `sponsor` VALUES (168696984424414122, 1646356165694722048, '祝云寄越来越好', 0.10, 2, NULL, '2023-06-17 10:44:04', NULL, 0);
INSERT INTO `sponsor` VALUES (168697118617768012, 1646356165694722048, '123456', 0.10, 2, '4200001844202306178336096302', '2023-06-17 11:06:26', '2023-06-17 11:06:54', 1);
INSERT INTO `sponsor` VALUES (168697174199034252, 1646356165694722048, '1', 0.01, 2, '4200001847202306170822828319', '2023-06-17 11:15:42', '2023-06-17 11:15:58', 1);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` bigint NOT NULL COMMENT '用户自增ID',
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户账户',
  `nick_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户昵称',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'https://a.ideaopen.cn/JanYork/YPfetj0a.png' COMMENT '用户头像',
  `email` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户邮箱',
  `phone` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户手机',
  `pwd` varchar(150) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户密码',
  `auth_real_name` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否实名认证',
  `create_time` datetime NOT NULL COMMENT '用户创建时间',
  `id_card_auth_id` int NULL DEFAULT NULL COMMENT '实名信息ID',
  `state` tinyint(1) NOT NULL DEFAULT 1 COMMENT '用户状态',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `user_for_name`(`name` ASC) USING BTREE COMMENT '用户账户索引',
  UNIQUE INDEX `user_for_email`(`email` ASC) USING BTREE COMMENT '用户邮箱索引',
  UNIQUE INDEX `user_for_phone`(`phone` ASC) USING BTREE COMMENT '用户手机号索引'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '云寄用户表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1677255645839101952, '16670880818', '时空穿梭者:F0818', 'https://a.ideaopen.cn/JanYork/YPfetj0a.png', NULL, '16670880818', '$2a$10$PdiC55T7I/kaS5eWctMqGOIlNtDXZ9WPj6ib31Za41Usr8lLjwFIO', 0, '2023-07-07 17:58:03', NULL, 1);
INSERT INTO `user` VALUES (1677267067201523712, '17362849197', '时空穿梭者:K9197', 'https://a.ideaopen.cn/JanYork/YPfetj0a.png', NULL, '17362849197', '$2a$10$2OY8wA7EiQ88veCTmd.e9.So9txtk/Lz3g7tkVPjp8ql.Hb5AiNlq', 0, '2023-07-07 18:43:26', NULL, 1);
INSERT INTO `user` VALUES (1677267114425192448, '18522581177', '时空穿梭者:K1177', 'https://a.ideaopen.cn/JanYork/YPfetj0a.png', NULL, '18522581177', '$2a$10$O7dp.7u.i//7tgWkuu2PceV0RbioQtsfDHP4Z3gf74RCD1zWsTmgW', 0, '2023-07-07 18:43:37', NULL, 1);
INSERT INTO `user` VALUES (1677267768606593024, '15031043934', '时空穿梭者:U3934', 'https://a.ideaopen.cn/JanYork/YPfetj0a.png', NULL, '15031043934', '$2a$10$9k7i.P36sTwavhenmQc9oetGQFFmCdlIGvhyuzbveX4k59kEScCBC', 0, '2023-07-07 18:46:13', NULL, 1);
INSERT INTO `user` VALUES (1677268730402443264, '15779794435', '时空穿梭者:Z4435', 'https://a.ideaopen.cn/JanYork/YPfetj0a.png', NULL, '15779794435', '$2a$10$A47yJ4umxsinSZ02AT4HLeiXEKqAONf6i7n8tEDA1dIqKoLCAknEC', 0, '2023-07-07 18:50:02', NULL, 1);
INSERT INTO `user` VALUES (1677268969146421248, '15171337428', '时空穿梭者:L7428', 'https://a.ideaopen.cn/JanYork/YPfetj0a.png', NULL, '15171337428', '$2a$10$Xr7Vp1vCgNXTZ3cVaB2PUuuszJmbiejI9.ZHoVWlCMQNFrbNN1S8K', 0, '2023-07-07 18:50:59', NULL, 1);
INSERT INTO `user` VALUES (1677269320377438208, '15297394120', '时空穿梭者:E4120', 'https://a.ideaopen.cn/JanYork/YPfetj0a.png', NULL, '15297394120', '$2a$10$SN.A/E6jo2Zz0bIB6nkF3euR4Vr7pnMMzqJgodX38mOTyt6RGb2Ze', 0, '2023-07-07 18:52:23', NULL, 1);
INSERT INTO `user` VALUES (1677269744882946048, '19841800936', '时空穿梭者:M0936', 'https://a.ideaopen.cn/JanYork/YPfetj0a.png', NULL, '19841800936', '$2a$10$ikHuILsqs49ix0F39Frys.4.Pg.yOmQvuyu7VjEgA7yGMdGicNG22', 0, '2023-07-07 18:54:04', NULL, 1);
INSERT INTO `user` VALUES (1677270067554947072, '18646019740', '时空穿梭者:U9740', 'https://a.ideaopen.cn/JanYork/YPfetj0a.png', NULL, '18646019740', '$2a$10$IgQga4mniyag.Wfo/t3b1.T.XfW6wH11wXj2IZqCNcRpewlDuhlT2', 0, '2023-07-07 18:55:21', NULL, 1);
INSERT INTO `user` VALUES (1677271924834701312, '18231529036', '时空穿梭者:I9036', 'https://a.ideaopen.cn/JanYork/YPfetj0a.png', NULL, '18231529036', '$2a$10$o2v4kUFw3UnmGtLxtq8K/.Yck7jyUAfWLLqZgnkPnPNut31wLC3Tm', 0, '2023-07-07 19:02:44', NULL, 1);
INSERT INTO `user` VALUES (1677278865833201664, '15731519131', '时空穿梭者:H9131', 'https://a.ideaopen.cn/JanYork/YPfetj0a.png', NULL, '15731519131', '$2a$10$bTA1AyEdKwGlSetsuf/B5OPXA3AJPDwbifiQ5qQPxFkm.3IBRLNk.', 0, '2023-07-07 19:30:19', NULL, 1);
INSERT INTO `user` VALUES (1677279566395215872, '18239305833', '时空穿梭者:O5833', 'https://a.ideaopen.cn/JanYork/YPfetj0a.png', NULL, '18239305833', '$2a$10$2rZfWag4wSecJ.eNgVrg4OVH5OweW5Nsh2rHkPvVIrBCtX57V6TT.', 0, '2023-07-07 19:33:06', NULL, 1);
INSERT INTO `user` VALUES (1677283961505714176, '15816088620', '时空穿梭者:X8620', 'https://a.ideaopen.cn/JanYork/YPfetj0a.png', NULL, '15816088620', '$2a$10$7q9UtjMJUNguHa9/UU9jU.i.MpR8WX/2COtlwxubMZTgkrRXWAsnS', 0, '2023-07-07 19:50:34', NULL, 1);
INSERT INTO `user` VALUES (1677298071832236032, '15971352064', '时空穿梭者:N2064', 'https://a.ideaopen.cn/JanYork/YPfetj0a.png', NULL, '15971352064', '$2a$10$U3pzZP8l73WA0cbN4JYDRu0T8ooWEW598GsW3bPmHdHSIHE4LBdue', 0, '2023-07-07 20:46:38', NULL, 1);
INSERT INTO `user` VALUES (1677332317233876992, '19166918721', '时空穿梭者:H8721', 'https://a.ideaopen.cn/JanYork/YPfetj0a.png', NULL, '19166918721', '$2a$10$oYfwSj4hhHNfZEgQT4IJXeQBm/l2j/F1jyli3OZi7HnnzGocBCEMe', 0, '2023-07-07 23:02:42', NULL, 1);
INSERT INTO `user` VALUES (1677566800822407168, 'Yunna038158068227', '微信用户_7753108cefda49439920ea01b6bd3766', 'https://image.totime.net/ee8ee40ae8454ddab872aca2e5dd28fb.jpg', NULL, NULL, '$2a$10$oI/qJyOXjpxgDJYNjCpaj.ogmZNBpeMmBjWvwNZ510R5IHjLKn4EW', 0, '2023-07-08 14:34:28', NULL, 1);
INSERT INTO `user` VALUES (1677630856149209088, 'Yunqb344243339980', '微信用户_6ea8a25873904060874259bf2ae6316e', 'https://image.totime.net/45255d7c5fad4461afaaa02f1f683a72.jpg', NULL, NULL, '$2a$10$8KngbkG7e9hDwT5Ms5FMaO6xkDCqKxi46DsHmA8pKuIAZWfASlbBa', 0, '2023-07-08 18:49:00', NULL, 1);
INSERT INTO `user` VALUES (1677630982506811392, 'Yunced68cd3369863', '微信用户_687e49924d9547bc99cc8c607f03e91e', 'https://image.totime.net/07104341c5fe4c3c8916dbc37b8bbf49.jpg', NULL, NULL, '$2a$10$GWLzS98.NE4Hcwlm50.Kj.q2rS2URG4R8/ueRkXyPUJ/FkvQGDFka', 0, '2023-07-08 18:49:30', NULL, 1);
INSERT INTO `user` VALUES (1677634272963465216, '18891124699', '时空穿梭者:I4699', 'https://a.ideaopen.cn/JanYork/YPfetj0a.png', NULL, '18891124699', '$2a$10$lkxMhj0Rsg9MlGo7TXJJAOn/kc.zxN9AuuCzz.upWP7wPtWZEGQVm', 0, '2023-07-08 19:02:34', NULL, 1);
INSERT INTO `user` VALUES (1677637497892507648, 'Yundcedfb34923512', '微信用户_9d54f47b192947ab8424a04ec7dbf5d1', 'https://image.totime.net/e939345d81f34f1e8a37b49d4a9fe62d.jpg', NULL, NULL, '$2a$10$RifkQRH76LHf2BbcBcLtmeEfWF80JjJKCP79fqWjxeG40Z9dWnAmW', 0, '2023-07-08 19:15:24', NULL, 1);
INSERT INTO `user` VALUES (1677640142438928384, 'Yunp04eeaa5553932', '微信用户_df5a7fd02a21476981e9751e0fe9531a', 'https://image.totime.net/023e7fafc76b4164b22ad7a688668ccb.jpg', NULL, NULL, '$2a$10$dut11ivowMYDzFqzYBBoEONqhOTLxloqrAI4r2EYe6PLTxe8Yz2cO', 0, '2023-07-08 19:25:54', NULL, 1);
INSERT INTO `user` VALUES (1677647689216561152, 'Yunec3f1247353312', '微信用户_5ba38abb4d3d4e8bb51e724a2aeefe4e', 'https://image.totime.net/1b3b2d2300b44bf19fc10bacfea0e8cc.jpg', NULL, '13836564644', '$2a$10$.oAlN9G1reWJxcaGwTcUxOeqF4rQmYBq8bDp3utDTNyn1TInm0drK', 0, '2023-07-08 19:55:53', NULL, 1);
INSERT INTO `user` VALUES (1677660859029131264, 'Yunb4b95d90493172', '微信用户_85341e8d4797439690ee368398236c41', 'https://image.totime.net/97252d850778448b9d3c6a183615e458.jpg', NULL, NULL, '$2a$10$hLKgF9OQ/RT7KN0X16aAPur0NmRpOn4H23wXx5xdzkJ7mbIJ3b7IO', 0, '2023-07-08 20:48:13', NULL, 1);
INSERT INTO `user` VALUES (1677661681297264640, 'Yunf4c96bf0689257', '微信用户_3b5fe777b74d437fa53bcdd4e4d8ea63', 'https://image.totime.net/ae570ef824c2447e888848b47e713d3d.jpg', NULL, NULL, '$2a$10$yvIEAZokBuA2EiTvBn4gAODmTZyScBjegxeWGVGKziJAQBQYz00aS', 0, '2023-07-08 20:51:29', NULL, 1);
INSERT INTO `user` VALUES (1677662283209248768, 'Yunq9ad0450832733', '微信用户_07192c4fb1cc476882a61194fef189a3', 'https://image.totime.net/bca04cfefaef44de8bf8d56bb7df2ecc.jpg', NULL, NULL, '$2a$10$5wGhUQAfRVZwW2tmQchuJu1KMrCnVFNJYyyXIVRP.gL39z219Cykm', 0, '2023-07-08 20:53:53', NULL, 1);
INSERT INTO `user` VALUES (1677663296716673024, '17633544127', '时空穿梭者:A4127', 'https://a.ideaopen.cn/JanYork/YPfetj0a.png', NULL, '17633544127', '$2a$10$76dQPn.cHdjzxLFY1vu.6epzySugXLkUikwTZKBKHTGvnmbF17EKW', 0, '2023-07-08 20:57:54', NULL, 1);
INSERT INTO `user` VALUES (1677702348320935936, 'Yunx2c9d5b0384946', '微信用户_d3790958203441bb9a5b001c7141ef21', 'https://image.totime.net/7a0f44ee6d9144c0abeedc2da67ad890.jpg', NULL, NULL, '$2a$10$Kq32Y1vCLuXuWNKrcU8XLehmYiVFLxPpz8dSoYBSdiizGP8Avhj7S', 0, '2023-07-08 23:33:05', NULL, 1);
INSERT INTO `user` VALUES (1677721075926044672, 'Yunyb6f5794850066', '微信用户_822efff930e5475994a410f40f261636', 'https://image.totime.net/d565b50cf255459480799f2b0181c1bf.jpg', NULL, NULL, '$2a$10$EbPIItvwkFhrKKgi0FuLW.t9zO2w2onzspBZ7Q9vCOV.UxHRUyglO', 0, '2023-07-09 00:47:30', NULL, 1);
INSERT INTO `user` VALUES (1677883270899044352, 'Yuntc6c0fb3520350', '微信用户_0f29bf325c91442981a366f6586eb2d6', 'https://image.totime.net/d2e85821d28f4ed589a98650a4a01e56.jpg', NULL, NULL, '$2a$10$gzBJcWYvZJEMd.CkrBx1butLiDkBMMgSJ5h0Kh8i7UTrDPiYZIZ.S', 0, '2023-07-09 11:32:00', NULL, 1);
INSERT INTO `user` VALUES (1677899072796758016, 'Yunx9e06847287746', '微信用户_2e6105d62dcb4afdbf15fb17eae08be5', 'https://image.totime.net/19665584f3fc45a89ef91ff95aa4b3bb.jpg', NULL, NULL, '$2a$10$UROlRyg9fzYQnGafUvkGJ.dMdStYjkJoTJF1ufQDpFebMz4ylDaxC', 0, '2023-07-09 12:34:48', NULL, 1);
INSERT INTO `user` VALUES (1678020995388149760, 'Yunda5815c6356458', '微信用户_4dce01aa9b1e4efb83e1017adeda5726', 'https://image.totime.net/8121f069421e414498e2cd39bf70a77c.jpg', NULL, NULL, '$2a$10$QOY9yM5dinUwBCYBAIfyU.ho12PBBfSbyjorqrKPm1Kg9a8LXL91i', 0, '2023-07-09 20:39:16', NULL, 1);
INSERT INTO `user` VALUES (1678068595764105216, '13379944338', '时空穿梭者:P4338', 'https://a.ideaopen.cn/JanYork/YPfetj0a.png', NULL, '13379944338', '$2a$10$AN3GkD9myWxwuAftPtaNdOh/5N/7IFX64.ezoe8jEdxkgOPqXgeDq', 0, '2023-07-09 23:48:25', NULL, 1);
INSERT INTO `user` VALUES (1678073941249363968, 'Yunf410f118979651', '微信用户_dbef2651e82843eeb4340183cd31d563', 'https://image.totime.net/5162bb0f6e1943c5981f13720e6f44f2.jpg', NULL, '15829522089', '$2a$10$cAzo6vo7R1ayKJRSOYwR3OVJC5AISpVG3GBi3438/SXukJItG8AsS', 0, '2023-07-10 00:09:40', NULL, 1);
INSERT INTO `user` VALUES (1678075199658004480, 'Yuna85c7499279758', '微信用户_6880142485584dfd8ac72fc6ed07bbc7', 'https://image.totime.net/501e45cdaaa64cc5a5409b536fba847e.jpg', NULL, '15292270876', '$2a$10$s18OghbS3KJuCTuqn.GhBuLugLCAqr0oqVmFmyDL3J6fxzOdBs0tW', 0, '2023-07-10 00:14:40', NULL, 1);
INSERT INTO `user` VALUES (1678164099747418112, '13419718065', '时空穿梭者:N8065', 'https://a.ideaopen.cn/JanYork/YPfetj0a.png', NULL, '13419718065', '$2a$10$pKMBSAYGn8l.nF0Vg3e.G.UkvgLqLwyhSWoX2KUdTCNaDc6FDNu7S', 0, '2023-07-10 06:07:55', NULL, 1);
INSERT INTO `user` VALUES (1678165605691297792, '19103186425', '时空穿梭者:D6425', 'https://a.ideaopen.cn/JanYork/YPfetj0a.png', NULL, '19103186425', '$2a$10$U/w604VgGUSaRWkk/odHXO/Ey1v8UxDhf0WXzqE.DrNSeePqxGHMm', 0, '2023-07-10 06:13:54', NULL, 1);
INSERT INTO `user` VALUES (1678234892644782080, 'Yunkd5e1e07353490', '微信用户_995c65b4ab3e4aa788ce5efcb0627926', 'https://image.totime.net/e7e83313e9d947e2bd3561b262e6196a.jpg', NULL, NULL, '$2a$10$6lcEe4YDDLe4RmVA8pFg7OIhi4i7rO1Y2ezym9bxTYFxjwC74qnKS', 0, '2023-07-10 10:49:13', NULL, 1);
INSERT INTO `user` VALUES (1678236368188346368, 'Yuncc09dc17705535', '微信用户_c9cfd56fa8fb44e4b3766f8da2372f72', 'https://image.totime.net/e297415ed9ac4a409cb3ccce57a339ed.jpg', NULL, NULL, '$2a$10$G2yyJ1m7Wiak8f1L8vsCXeLueyXD4zN7Sj2XSRBO.PWHSrq.38TRK', 0, '2023-07-10 10:55:06', NULL, 1);
INSERT INTO `user` VALUES (1678246526259957760, '18188944128', '时空穿梭者:G4128', 'https://a.ideaopen.cn/JanYork/YPfetj0a.png', NULL, '18188944128', '$2a$10$8azK9.6RdpX.4WoMejA.kudPaIgO66xKrbDQiMbHdxaukYtCMG8xy', 0, '2023-07-10 11:35:27', NULL, 1);
INSERT INTO `user` VALUES (1678319527911362560, '18323983863', '时空穿梭者:I3863', 'https://a.ideaopen.cn/JanYork/YPfetj0a.png', NULL, '18323983863', '$2a$10$XNynE2z/m4zXYdkiydzxoe2XPYo2NwMW38sHdTb7KawAyxSQfWfuO', 0, '2023-07-10 16:25:32', NULL, 1);
INSERT INTO `user` VALUES (1678325814778793984, 'Yunuf7a7f99031036', '微信用户_bf7400e52ea646e0bae954527a676fa8', 'https://image.totime.net/8640906777c6426881997724a945e517.jpg', NULL, NULL, '$2a$10$EjNc1V3WtDG/WW1XhvlZwOAM/ibjtRcWkOKH3B1zctymhAT34mkJ.', 0, '2023-07-10 16:50:31', NULL, 1);
INSERT INTO `user` VALUES (1678398804803588096, '15083015073', '时空穿梭者:Y5073', 'https://a.ideaopen.cn/JanYork/YPfetj0a.png', NULL, '15083015073', '$2a$10$BgiKndU.RZl3NnfNrKGRKeatYhA0lE/oiE/riyHg9I2dFJ/cel8j6', 0, '2023-07-10 21:40:33', NULL, 1);
INSERT INTO `user` VALUES (1678403957694795776, 'Yunz8145c57662141', '微信用户_4108f2edbf6744fa8bae13ec22b77478', 'https://image.totime.net/5355f454fa51480d8282edc4b4e7d47d.jpg', NULL, NULL, '$2a$10$U0YiYapxKDnqNm3BD6vjfukO1lV6HqShuAphyKhnISIn2Q7A1eHiu', 0, '2023-07-10 22:01:02', NULL, 1);
INSERT INTO `user` VALUES (1678438443153231872, '15900068949', '时空穿梭者:W8949', 'https://a.ideaopen.cn/JanYork/YPfetj0a.png', NULL, '15900068949', '$2a$10$WfTEc2hcs4lVcZdgDu4.OOyGckrzMuz1GvrgtCMmvUs0RZrr5oDoe', 0, '2023-07-11 00:18:03', NULL, 1);
INSERT INTO `user` VALUES (1678659042219790336, 'Yunr41d4048478825', '微信用户_07fb55ed02704ca1b1b2c6d50764c7ff', 'https://image.totime.net/7676d082506f4dc2bf20c2d58b75786d.jpg', NULL, NULL, '$2a$10$g40VWWrpjlpKd4/jmadd9uNyCRcEcAapJCmCHW1B3t1kNVu.7hsje', 0, '2023-07-11 14:54:39', NULL, 1);
INSERT INTO `user` VALUES (1678659365424467968, '18531425998', '时空穿梭者:S5998', 'https://a.ideaopen.cn/JanYork/YPfetj0a.png', NULL, '18531425998', '$2a$10$5hfi53mI90EKKz6M9vfq.O1vgt8xrFDc.nHKlmjIF5f1UFiQMj0a6', 0, '2023-07-11 14:55:55', NULL, 1);
INSERT INTO `user` VALUES (1678685936352890880, '13968965710', '时空穿梭者:R5710', 'https://a.ideaopen.cn/JanYork/YPfetj0a.png', NULL, '13968965710', '$2a$10$kfDf56TybNAiUOpH804Fm.bdhik9QRkUpi6KaGHQZtRimyWUJWj9u', 0, '2023-07-11 16:41:30', NULL, 1);
INSERT INTO `user` VALUES (1679100676279504896, '17773222317', '时空穿梭者:S2317', 'https://a.ideaopen.cn/JanYork/YPfetj0a.png', NULL, '17773222317', '$2a$10$TAuO0tcwgWUZE/XGGBBPsOnWkrYrHugac9RhM1q9sGPGZKimYrBDS', 0, '2023-07-12 20:09:32', NULL, 1);
INSERT INTO `user` VALUES (1679129358880608256, 'Yunva887ab0610867', '微信用户_56eb138b8a604cc1afd69b3178089ebe', 'https://image.totime.net/b3bf13e8d82048d083facee19a2b9664.jpg', NULL, NULL, '$2a$10$sMXbItSyRzQUKCYOD6M3dOLBTcRKeXpbEyef/oroMrYKEncGgF/nC', 0, '2023-07-12 22:03:31', NULL, 1);
INSERT INTO `user` VALUES (1679129557254410240, '15702320028', '时空穿梭者:Y0028', 'https://a.ideaopen.cn/JanYork/YPfetj0a.png', NULL, '15702320028', '$2a$10$zMj7KVRgv6lm03WHgCrrzuupfvdnV0BCcZTDZRWxyu9A4OopYqB5K', 0, '2023-07-12 22:04:18', NULL, 1);
INSERT INTO `user` VALUES (1679131344040497152, 'Yunw1000951084119', '微信用户_1f3f8b98348f49759b91a9b936f9cd20', 'https://image.totime.net/853e7d54d38f4531bee91e8a44abe6fe.jpg', NULL, NULL, '$2a$10$SVH/Ef9zMF23LOlZbsEPpeX7ptS7DiDDpwvIAQpZD4FvOilTrleqy', 0, '2023-07-12 22:11:24', NULL, 1);
INSERT INTO `user` VALUES (1679329165582667776, '15833816553', '时空穿梭者:R6553', 'https://a.ideaopen.cn/JanYork/YPfetj0a.png', NULL, '15833816553', '$2a$10$3rFQiXN2FXEajUaxWXhExO9qjaJq4zYaOy/2WxfUOW03Yq10VhLsS', 0, '2023-07-13 11:17:28', NULL, 1);
INSERT INTO `user` VALUES (1679361035439575040, 'Yunq5fdf415846869', '微信用户_d16449cbaa9546238555e0401558a90e', 'https://image.totime.net/90a2e054595c44378948afaea92c36d7.jpg', NULL, NULL, '$2a$10$ik.XfCy/mNwl1QyGgQVhU.1bxIyr4x34300mtxPu0o81aTSQMK2Fu', 0, '2023-07-13 13:24:07', NULL, 1);
INSERT INTO `user` VALUES (1679373552450670592, 'Yunu3f92ec8836359', '微信用户_db3ed25d3a5949d8b40dbb5c0fc33d9e', 'https://image.totime.net/ed3be483457841d1b79577be4c71e262.jpg', NULL, NULL, '$2a$10$7OH3NcN7a8LJbR0IZTLdF.q76MfAmh8d//5ZD/WP4rtT2HDkjX5Wq', 0, '2023-07-13 14:13:56', NULL, 1);
INSERT INTO `user` VALUES (1679373680569880576, '15708996586', '时空穿梭者:F6586', 'https://a.ideaopen.cn/JanYork/YPfetj0a.png', NULL, '15708996586', '$2a$10$AclSWmlPqpuGhq5l/bkWou23F6Fzbh9K9X5Om.6TxlrZPZpYdUI6C', 0, '2023-07-13 14:14:21', NULL, 1);
INSERT INTO `user` VALUES (1679446891584163840, '13511691763', '时空穿梭者:T1763', 'https://a.ideaopen.cn/JanYork/YPfetj0a.png', NULL, '13511691763', '$2a$10$jRRWjf0SNAdwcREXWJFMWeNyrBqCUkRdO1oWOJ3XPvnDoEsQo2Zlu', 0, '2023-07-13 19:05:16', NULL, 1);
INSERT INTO `user` VALUES (1679452780798021632, 'Yunib34c4d7720659', '微信用户_d13791b07b5c4adb9622a3337f5f0511', 'https://image.totime.net/63ef90e5f39c42cc9c83ae1a04499481.jpg', NULL, NULL, '$2a$10$xg7niiPtLmC9LPIEXVh9eeeyrWOfLcIagNxFSNkMrMZwZSSExyu4a', 0, '2023-07-13 19:28:41', NULL, 1);
INSERT INTO `user` VALUES (1679533081465327616, '17311280221', '时空穿梭者:D0221', 'https://a.ideaopen.cn/JanYork/YPfetj0a.png', NULL, '17311280221', '$2a$10$77xV9Oz.tN0cK3wCVU2w6.ZL3fdMMXxzc2s1n0KIbCGMoqTkD7T/O', 0, '2023-07-14 00:47:45', NULL, 1);
INSERT INTO `user` VALUES (1679898467771748352, '13673618691', '时空穿梭者:N8691', 'https://a.ideaopen.cn/JanYork/YPfetj0a.png', NULL, '13673618691', '$2a$10$KPiey6F.P3BqpV0pv1fsf.y3NZX.hGmjdJeD5bIlKBHyhdus3Gl/6', 0, '2023-07-15 00:59:40', NULL, 1);
INSERT INTO `user` VALUES (1680026959062634496, '18662912890', '时空穿梭者:G2890', 'https://a.ideaopen.cn/JanYork/YPfetj0a.png', NULL, '18662912890', '$2a$10$ByKTnaEj3pNAOyuqPZQNiOGs0.xwqFmAv.NKVSC3fmlsE8KC.f15y', 0, '2023-07-15 09:30:15', NULL, 1);
INSERT INTO `user` VALUES (1680087616357273600, 'Yunz165e899077207', '微信用户_2aa8024e1fae4aaeb398a982a5fc5ed6', 'https://image.totime.net/cbda5ac553c248839290c9b842c69011.jpg', NULL, NULL, '$2a$10$GU5y7rN1Pex1ihMcObvTH.vfBpzF1dOOQrpu7Fdq6IeHyB1vR8KQq', 0, '2023-07-15 13:31:17', NULL, 1);
INSERT INTO `user` VALUES (1680108404456165376, '18093631765', '时空穿梭者:S1765', 'https://a.ideaopen.cn/JanYork/YPfetj0a.png', NULL, '18093631765', '$2a$10$XdyITGO4vgOkscJbQza7l.798zl4c7mbbr1tX/q/FgQqGh5xRbKlm', 0, '2023-07-15 14:53:53', NULL, 1);
INSERT INTO `user` VALUES (1680570336199970816, 'Yunt1e44f14166928', '微信用户_cc630c4bec3340dbb264600de614de50', 'https://image.totime.net/0986372ad68347258d340fc8dcb5b00b.jpg', NULL, '15737975620', '$2a$10$YNsBadzIN4P9dcDp0J8wm.EzuFyKkxogY00objJLG08gnuCwUYsti', 0, '2023-07-16 21:29:27', NULL, 1);
INSERT INTO `user` VALUES (1680635022039715840, '15587920053', '时空穿梭者:M0053', 'https://a.ideaopen.cn/JanYork/YPfetj0a.png', NULL, '15587920053', '$2a$10$kqRQEmeQcz/9KYV/8GDw0eOxvA87VdneK2Xhtfvn5tgCRa7wGoNem', 0, '2023-07-17 01:46:29', NULL, 1);
INSERT INTO `user` VALUES (1680760253995683840, '15578379550', '时空穿梭者:Z9550', 'https://a.ideaopen.cn/JanYork/YPfetj0a.png', NULL, '15578379550', '$2a$10$KzHvPwWhWjeZtMufBIXXTuUJo4JI1r35dwJ62.yboJHrlK5.o6sVm', 0, '2023-07-17 10:04:06', NULL, 1);
INSERT INTO `user` VALUES (1680785559037022208, '18035120189', '时空穿梭者:U0189', 'https://a.ideaopen.cn/JanYork/YPfetj0a.png', NULL, '18035120189', '$2a$10$YWaynIiRao4TOxzX7ZWXT.z7GgkXqJR9.jaSkz8MSOtQ0T5Ktn2ha', 0, '2023-07-17 11:44:39', NULL, 1);
INSERT INTO `user` VALUES (1680848894503817216, '15974296105', '时空穿梭者:A6105', 'https://a.ideaopen.cn/JanYork/YPfetj0a.png', NULL, '15974296105', '$2a$10$axfgeqHeaCEYUXmMrflg6Om.k4HRUlDtRQfG2K0T.v9feoPtCxlKW', 0, '2023-07-17 15:56:20', NULL, 1);

-- ----------------------------
-- Table structure for user_to_prem
-- ----------------------------
DROP TABLE IF EXISTS `user_to_prem`;
CREATE TABLE `user_to_prem`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '用户与权限中间表ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `prem_id` tinyint NOT NULL COMMENT '权限ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户与权限中间表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user_to_prem
-- ----------------------------
INSERT INTO `user_to_prem` VALUES (1, 1639936309851193344, 1);

-- ----------------------------
-- Table structure for user_to_role
-- ----------------------------
DROP TABLE IF EXISTS `user_to_role`;
CREATE TABLE `user_to_role`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '用户与角色中间表ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `role_id` tinyint NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户与角色中间表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user_to_role
-- ----------------------------
INSERT INTO `user_to_role` VALUES (1, 1639936309851193344, 1);

-- ----------------------------
-- Table structure for wish
-- ----------------------------
DROP TABLE IF EXISTS `wish`;
CREATE TABLE `wish`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '愿望ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `text` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '愿望',
  `image` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '图片',
  `creat_time` datetime NOT NULL COMMENT '创建时间',
  `state` tinyint(1) NOT NULL COMMENT '心愿状态',
  `ai_check_msg` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'AI审核消息',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1004 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '许愿表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of wish
-- ----------------------------
INSERT INTO `wish` VALUES (1001, 1640385966864732160, '希望云寄越来越好，越来越优秀', NULL, '2023-06-23 17:49:24', 5, NULL);
INSERT INTO `wish` VALUES (1002, 1640385966864732160, '希望云寄越来越好，越来越优秀', 'https://a.ideaopen.cn/JanYork/93rwjJbz.png', '2023-06-23 17:52:13', 7, '存在水印不合规;存在二维码不合规;');
INSERT INTO `wish` VALUES (1003, 1640385966864732160, '祝愿云寄越来越好，越来越优秀', NULL, '2023-06-25 00:18:37', 5, NULL);

-- ----------------------------
-- Table structure for wish_orders
-- ----------------------------
DROP TABLE IF EXISTS `wish_orders`;
CREATE TABLE `wish_orders`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '订单ID',
  `orders_serial` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '订单编号',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `date` datetime NOT NULL COMMENT '订单时间',
  `pay_time` datetime NULL DEFAULT NULL COMMENT '支付时间',
  `pay_type` tinyint(1) NOT NULL COMMENT '支付类型',
  `amount` decimal(10, 2) NULL DEFAULT NULL COMMENT '支付金额',
  `trade_no` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '支付商订单号',
  `state` tinyint(1) NOT NULL COMMENT '支付状态',
  `wish_id` bigint NOT NULL COMMENT '心愿ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '信件订单表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of wish_orders
-- ----------------------------
INSERT INTO `wish_orders` VALUES (1, '168751357378249203', 1640385966864732160, '2023-06-23 17:46:14', '2023-06-23 17:46:34', 2, 0.01, '4200001818202306234048810891', 1, 1000);
INSERT INTO `wish_orders` VALUES (2, '168751377040426813', 1640385966864732160, '2023-06-23 17:49:30', '2023-06-23 17:49:44', 2, 0.01, '4200001838202306232029855014', 1, 1001);
INSERT INTO `wish_orders` VALUES (3, '168751394691701773', 1640385966864732160, '2023-06-23 17:52:27', '2023-06-23 17:52:38', 2, 0.01, '4200001805202306235406328418', 1, 1002);
INSERT INTO `wish_orders` VALUES (4, '168762356237535273', 1640385966864732160, '2023-06-25 00:19:22', NULL, 1, NULL, NULL, 0, 1003);
INSERT INTO `wish_orders` VALUES (5, '168762371387444923', 1640385966864732160, '2023-06-25 00:21:54', '2023-06-25 00:23:08', 2, 0.01, '4200001805202306255887200579', 1, 1003);

SET FOREIGN_KEY_CHECKS = 1;
