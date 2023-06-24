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

 Date: 24/06/2023 19:59:38
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
  `role` tinyint(1) NOT NULL COMMENT '管理员角色',
  `create_time` datetime NOT NULL COMMENT '管理员创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10003 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '管理员信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES (10001, 'JanYork', '$2a$10$8SSN2hpmwyKxgg1NuhJcFOP84dgbiFilVX9yff0r/Sf7iJ4QGz.DS', '747945307@qq.com', 0, '2023-04-25 16:22:16');

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
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '祝福语ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `blessing` tinyint(4) NOT NULL COMMENT '祝福语',
  `wish_id` bigint(20) NOT NULL COMMENT '愿望ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `is_filter` tinyint(1) NOT NULL COMMENT '是否过滤',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '祝福语录表' ROW_FORMAT = Dynamic;

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
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '评论ID',
  `parent_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '父级评论ID',
  `user_id` bigint(20) NOT NULL COMMENT '评论用户ID',
  `mail_or_letter_id` bigint(20) NOT NULL COMMENT '评论对应邮件或者信件',
  `for_type` tinyint(4) NOT NULL COMMENT '评论对应表[mail：0，letter：1]',
  `level` tinyint(1) NOT NULL DEFAULT 0 COMMENT '评论深度',
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '评论内容',
  `create_time` datetime NOT NULL COMMENT '评论时间',
  `is_filter` tinyint(1) NOT NULL COMMENT '是否过滤',
  `is_sensitive` tinyint(1) NOT NULL COMMENT '是否敏感',
  `ai_check_msg` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'AI审核消息',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_parent_id`(`parent_id` ASC) USING BTREE,
  INDEX `idx_user_id`(`user_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1022 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '邮件与信件评论表' ROW_FORMAT = Dynamic;

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
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '实名认证ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `id_card` varchar(18) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '身份证号',
  `phone` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '手机号',
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '姓名',
  `other` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '其他证明身份图片[JSON]',
  `state` tinyint(1) NOT NULL COMMENT '实名认证状态',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '实名认证表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of id_card_auth
-- ----------------------------

-- ----------------------------
-- Table structure for issues
-- ----------------------------
DROP TABLE IF EXISTS `issues`;
CREATE TABLE `issues`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `issue` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 157 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '问题表' ROW_FORMAT = Dynamic;

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
  `letter_id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '信件唯一ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `letter_title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '信件标题',
  `letter_content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '信件内容',
  `letter_create_time` datetime NOT NULL COMMENT '信件创建时间',
  `is_public` tinyint(1) NOT NULL COMMENT '信件是否公开',
  `go_to_time` date NOT NULL COMMENT '信件发送时间',
  `is_yourself` tinyint(4) NOT NULL COMMENT '信件是否发给自己',
  `address` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '信件发往地址',
  `addressee` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '信件收件人姓名',
  `phone` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '信件收信人手机号',
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '信件备注',
  `letter_type` tinyint(1) NOT NULL COMMENT '信件类型',
  `state` tinyint(1) NOT NULL COMMENT '信件状态',
  `ai_check_msg` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'AI审核消息',
  PRIMARY KEY (`letter_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10010 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '时光信件表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of letter
-- ----------------------------
INSERT INTO `letter` VALUES (10000, 1646356165694722048, '云寄信件测试', '信件测试11111111111111111111111111111111111111111111111111', '2023-06-18 00:47:10', 0, '2023-06-18', 1, '湖南省长沙市岳麓区', '小简', '16670880818', NULL, 1, 2, NULL);
INSERT INTO `letter` VALUES (10001, 1646356165694722048, '云寄信件测试2', '信件测试11111111111111111111111111111111111111111111111111', '2023-06-18 00:55:58', 0, '2023-06-18', 1, '湖南省长沙市岳麓区', '小简', '16670880818', NULL, 1, 2, NULL);
INSERT INTO `letter` VALUES (10002, 1640385966864732160, '云寄信件测试3', '信件测试11111111111111111111111111111111111111111111111111', '2023-06-18 17:03:12', 0, '2023-06-18', 1, '湖南省长沙市岳麓区', '小简', '16670880818', NULL, 1, 3, NULL);
INSERT INTO `letter` VALUES (10003, 1640385966864732160, '写给未来的自己', '你好，未来的自己', '2023-06-18 17:13:56', 0, '2023-06-18', 1, '湖南省长沙市岳麓区', '小简', '16670880818', NULL, 1, 3, NULL);
INSERT INTO `letter` VALUES (10004, 1640385966864732160, '写给未来的自己', '你好，未来的自己', '2023-06-18 17:22:22', 0, '2023-06-18', 1, '湖南省长沙市岳麓区', '小简', '16670880818', NULL, 1, 2, NULL);
INSERT INTO `letter` VALUES (10005, 1640385966864732160, '写给未来的自己', '11111111111111111111111111111111111111111111111114444444444444444444444466666666666666666666666666666', '2023-06-18 17:25:26', 0, '2023-06-18', 1, '湖南省长沙市岳麓区', '小简', '16670880818', NULL, 1, 2, NULL);
INSERT INTO `letter` VALUES (10006, 1640385966864732160, '写给未来的自己', '草泥马的傻逼玩意靠', '2023-06-18 17:27:07', 0, '2023-06-18', 1, '湖南省长沙市岳麓区', '小简', '16670880818', NULL, 1, 7, '存在低俗辱骂不合规;');
INSERT INTO `letter` VALUES (10007, 1640385966864732160, '写给未来的自己', '你好，未来的自己', '2023-06-18 17:59:25', 0, '2023-06-18', 0, '湖南省长沙市岳麓区', '小简', '16670887668', NULL, 1, 2, NULL);
INSERT INTO `letter` VALUES (10008, 1640385966864732160, '写给未来的自己', '你好，未来的自己', '2023-06-18 18:03:49', 0, '2023-06-18', 0, '湖南省长沙市岳麓区', '小简', '16670887668', NULL, 1, 2, NULL);
INSERT INTO `letter` VALUES (10009, 1640385966864732160, '写给未来的自己', '你好，未来的自己', '2023-06-18 18:12:26', 0, '2023-06-18', 1, '江西省南昌市南昌县', '小简', '16670887668', NULL, 1, 3, NULL);

-- ----------------------------
-- Table structure for letter_orders
-- ----------------------------
DROP TABLE IF EXISTS `letter_orders`;
CREATE TABLE `letter_orders`  (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT '订单ID',
  `orders_serial` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '订单编号',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `date` datetime NOT NULL COMMENT '订单时间',
  `pay_time` datetime NULL DEFAULT NULL COMMENT '支付时间',
  `pay_type` tinyint(1) NOT NULL COMMENT '支付类型',
  `amount` decimal(10, 2) NULL DEFAULT NULL COMMENT '支付金额',
  `trade_no` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '支付商订单号',
  `state` tinyint(1) NOT NULL COMMENT '支付状态',
  `letter_id` bigint(20) NOT NULL COMMENT '信件ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '信件订单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of letter_orders
-- ----------------------------
INSERT INTO `letter_orders` VALUES (1, '168702080420086782', 1646356165694722048, '2023-06-18 00:53:24', '2023-06-18 00:54:29', 2, 0.01, '2023061822001464041443479556', 1, 10000);
INSERT INTO `letter_orders` VALUES (2, '168702097735728422', 1646356165694722048, '2023-06-18 00:56:17', '2023-06-18 00:56:30', 2, 0.01, '4200001834202306189244610578', 1, 10001);
INSERT INTO `letter_orders` VALUES (3, '168707904599208712', 1640385966864732160, '2023-06-18 17:04:06', NULL, 2, NULL, NULL, 3, 10002);
INSERT INTO `letter_orders` VALUES (4, '168707941003220372', 1640385966864732160, '2023-06-18 17:10:10', '2023-06-18 17:10:23', 2, 0.01, '4200001847202306186848063977', 1, 10002);
INSERT INTO `letter_orders` VALUES (5, '168707966065060092', 1640385966864732160, '2023-06-18 17:14:21', '2023-06-18 17:14:32', 2, 0.01, '4200001827202306186207531812', 1, 10003);
INSERT INTO `letter_orders` VALUES (6, '168708015044119672', 1640385966864732160, '2023-06-18 17:22:30', '2023-06-18 17:22:42', 2, 0.01, '4200001807202306189497638923', 1, 10004);
INSERT INTO `letter_orders` VALUES (7, '168708033619082992', 1640385966864732160, '2023-06-18 17:25:36', '2023-06-18 17:25:49', 2, 0.01, '4200001811202306189379169228', 1, 10005);
INSERT INTO `letter_orders` VALUES (8, '168708043704384032', 1640385966864732160, '2023-06-18 17:27:17', '2023-06-18 17:27:56', 2, 0.01, '4200001819202306184725335678', 1, 10006);
INSERT INTO `letter_orders` VALUES (9, '168708239445815322', 1640385966864732160, '2023-06-18 17:59:54', '2023-06-18 18:00:12', 2, 0.01, '4200001820202306184872650190', 1, 10007);
INSERT INTO `letter_orders` VALUES (10, '168708263650002742', 1640385966864732160, '2023-06-18 18:03:57', '2023-06-18 18:04:20', 2, 0.01, '4200001719202306188263721555', 1, 10008);
INSERT INTO `letter_orders` VALUES (11, '168708315296764212', 1640385966864732160, '2023-06-18 18:12:33', '2023-06-18 18:12:47', 2, 0.01, '4200001810202306187530969505', 1, 10009);

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
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '物流ID',
  `letter_id` bigint(20) NOT NULL COMMENT '信件ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `logistics_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '物流单号',
  `logistics_code` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '物流商户[\'SF\',\'YZ\',\'YT\'...]',
  `status` tinyint(1) NOT NULL COMMENT '物流状态',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10000 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '信件物流表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of logistics
-- ----------------------------

-- ----------------------------
-- Table structure for mail
-- ----------------------------
DROP TABLE IF EXISTS `mail`;
CREATE TABLE `mail`  (
  `mail_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '邮件唯一ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `mail_title` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '邮件标题',
  `mail_content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '邮件内容',
  `mail_create_time` datetime NOT NULL COMMENT '邮件创建时间',
  `go_to` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '发往地址',
  `is_public` tinyint(1) NOT NULL COMMENT '邮件是否公开',
  `go_to_time` datetime NOT NULL COMMENT '邮件发送时间',
  `is_yourself` tinyint(4) NOT NULL COMMENT '邮件是否发给自己',
  `use_serve` tinyint(4) NOT NULL DEFAULT 0 COMMENT '邮件发送使用的服务',
  `state` tinyint(1) NOT NULL DEFAULT 0 COMMENT '邮件发送状态',
  `ai_check_msg` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'AI审核消息',
  PRIMARY KEY (`mail_id`) USING BTREE,
  INDEX `user_for_mail`(`user_id` ASC) USING BTREE COMMENT '邮件表用户索引'
) ENGINE = InnoDB AUTO_INCREMENT = 10004 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '邮件任务表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of mail
-- ----------------------------
INSERT INTO `mail` VALUES (10001, 1640385966864732160, '来自未来的一封信', '你好，cnmd傻逼', '2023-06-20 09:45:14', '1345561377@qq.com', 0, '2024-07-19 23:30:54', 0, 0, 7, '存在低俗辱骂不合规;');
INSERT INTO `mail` VALUES (10002, 1640385966864732160, '来自未来的一封信2', 'cnmd，傻逼', '2023-06-20 09:47:58', '1345561377@qq.com', 0, '2024-07-19 23:30:54', 0, 0, 8, '存在低俗辱骂不合规;');
INSERT INTO `mail` VALUES (10003, 1640385966864732160, '来自未来的一封信2', '你好，未来', '2023-06-20 10:07:29', '1345561377@qq.com', 0, '2024-07-19 23:30:54', 0, 0, 7, NULL);

-- ----------------------------
-- Table structure for message
-- ----------------------------
DROP TABLE IF EXISTS `message`;
CREATE TABLE `message`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '短信ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `text` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '短信内容',
  `send_time` date NOT NULL COMMENT '短信发送时间',
  `create_time` datetime NOT NULL COMMENT '短信创建时间',
  `is_unnamed` tinyint(1) NOT NULL COMMENT '短信是否匿名',
  `phone` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '对方手机号',
  `state` tinyint(1) NOT NULL COMMENT '短信状态',
  `ai_check_msg` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'AI审核消息',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10001 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '短信任务表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of message
-- ----------------------------
INSERT INTO `message` VALUES (10000, 1640385966864732160, '匿名短信测试', '2023-06-23', '2023-06-23 02:37:16', 1, '16670880818', 2, NULL);

-- ----------------------------
-- Table structure for message_orders
-- ----------------------------
DROP TABLE IF EXISTS `message_orders`;
CREATE TABLE `message_orders`  (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT '订单ID',
  `orders_serial` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '订单编号',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `date` datetime NOT NULL COMMENT '订单时间',
  `pay_time` datetime NULL DEFAULT NULL COMMENT '支付时间',
  `pay_type` tinyint(1) NOT NULL COMMENT '支付类型',
  `amount` decimal(10, 2) NULL DEFAULT NULL COMMENT '支付金额',
  `trade_no` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '支付商订单号',
  `state` tinyint(1) NOT NULL COMMENT '支付状态',
  `message_id` bigint(20) NOT NULL COMMENT '短信ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '信件订单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of message_orders
-- ----------------------------
INSERT INTO `message_orders` VALUES (4, '168746069306588453', 1640385966864732160, '2023-06-23 03:04:53', '2023-06-23 03:05:08', 1, 0.01, '2023062322001464041459284965', 1, 10000);

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
  `id` bigint(20) NOT NULL COMMENT '赞助表ID',
  `user_id` bigint(20) NOT NULL COMMENT '赞助用户',
  `sponsor_say` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '呀~这位好心人居然没有留言！' COMMENT '赞助留言',
  `sponsor_amount` decimal(10, 2) NOT NULL COMMENT '赞助金额',
  `pay_type` tinyint(1) NOT NULL COMMENT '支付类型',
  `trade_no` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '支付商订单号',
  `create_time` datetime NOT NULL COMMENT '赞助创建时间',
  `pay_time` datetime NULL DEFAULT NULL COMMENT '支付时间',
  `state` tinyint(1) NOT NULL COMMENT '支付状态',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '赞助信息表' ROW_FORMAT = Dynamic;

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
  `id` bigint(20) NOT NULL COMMENT '用户自增ID',
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户账户',
  `nick_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户昵称',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'https://a.ideaopen.cn/JanYork/YPfetj0a.png' COMMENT '用户头像',
  `email` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户邮箱',
  `phone` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户手机',
  `pwd` varchar(150) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户密码',
  `auth_real_name` tinyint(1) NOT NULL DEFAULT 1 COMMENT '是否实名认证',
  `create_time` datetime NOT NULL COMMENT '用户创建时间',
  `id_card_auth_id` int(11) NULL DEFAULT NULL COMMENT '实名信息ID',
  `state` tinyint(1) NOT NULL DEFAULT 0 COMMENT '用户状态',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `user_for_name`(`name` ASC) USING BTREE COMMENT '用户账户索引',
  UNIQUE INDEX `user_for_email`(`email` ASC) USING BTREE COMMENT '用户邮箱索引',
  UNIQUE INDEX `user_for_phone`(`phone` ASC) USING BTREE COMMENT '用户手机号索引'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '云寄用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1640385966864732160, 'JanYork', '', 'https://a.ideaopen.cn/JanYork/7LlPSYEV.png', '1607693403@qq.com', '16670880818', '$2a$10$NZ2rTuP2LIO8gTF3WOhjJ.r/SKKAUfHCtP9de6YE5fl9oLi0uv0gu', 1, '2023-03-28 00:11:06', NULL, 1);
INSERT INTO `user` VALUES (1646356165694722048, 'GITEE_9735876', 'JanYork', 'https://dlf-1309416366.cos.ap-beijing.myqcloud.com/9735876_1681356476020.png', NULL, '', '$2a$10$I92TqGF8.6NV1sxJjWUnKOQjmFHiYNAivq9WJ.IXpn9thKQPdby3K', 1, '2023-04-13 11:34:32', NULL, 1);
INSERT INTO `user` VALUES (1671179890201858048, '16670887668', '时空穿梭者:G7668', 'https://a.ideaopen.cn/JanYork/YPfetj0a.png', '747945307@qq.com', '16670887668', '$2a$10$2T/lBGePWVd4.slnVDUsquIJqgYqRrbZJyBO8uPJm1rnGYbZ/Wnlq', 1, '2023-06-20 23:35:10', NULL, 0);

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
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '愿望ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `text` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '愿望',
  `image` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '图片',
  `creat_time` datetime NOT NULL COMMENT '创建时间',
  `state` tinyint(1) NOT NULL COMMENT '心愿状态',
  `ai_check_msg` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'AI审核消息',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1003 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '许愿表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wish
-- ----------------------------
INSERT INTO `wish` VALUES (1001, 1640385966864732160, '希望云寄越来越好，越来越优秀', NULL, '2023-06-23 17:49:24', 5, NULL);
INSERT INTO `wish` VALUES (1002, 1640385966864732160, '希望云寄越来越好，越来越优秀', 'https://a.ideaopen.cn/JanYork/93rwjJbz.png', '2023-06-23 17:52:13', 7, '存在水印不合规;存在二维码不合规;');

-- ----------------------------
-- Table structure for wish_orders
-- ----------------------------
DROP TABLE IF EXISTS `wish_orders`;
CREATE TABLE `wish_orders`  (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT '订单ID',
  `orders_serial` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '订单编号',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `date` datetime NOT NULL COMMENT '订单时间',
  `pay_time` datetime NULL DEFAULT NULL COMMENT '支付时间',
  `pay_type` tinyint(1) NOT NULL COMMENT '支付类型',
  `amount` decimal(10, 2) NULL DEFAULT NULL COMMENT '支付金额',
  `trade_no` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '支付商订单号',
  `state` tinyint(1) NOT NULL COMMENT '支付状态',
  `wish_id` bigint(20) NOT NULL COMMENT '心愿ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '信件订单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wish_orders
-- ----------------------------
INSERT INTO `wish_orders` VALUES (1, '168751357378249203', 1640385966864732160, '2023-06-23 17:46:14', '2023-06-23 17:46:34', 2, 0.01, '4200001818202306234048810891', 1, 1000);
INSERT INTO `wish_orders` VALUES (2, '168751377040426813', 1640385966864732160, '2023-06-23 17:49:30', '2023-06-23 17:49:44', 2, 0.01, '4200001838202306232029855014', 1, 1001);
INSERT INTO `wish_orders` VALUES (3, '168751394691701773', 1640385966864732160, '2023-06-23 17:52:27', '2023-06-23 17:52:38', 2, 0.01, '4200001805202306235406328418', 1, 1002);

SET FOREIGN_KEY_CHECKS = 1;
