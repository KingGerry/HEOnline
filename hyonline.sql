/*
 Navicat Premium Data Transfer

 Source Server         : hy
 Source Server Type    : MySQL
 Source Server Version : 80040

 Target Server Type    : MySQL
 Target Server Version : 80040
 File Encoding         : 65001

 Date: 05/11/2024 09:43:26
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for hy_board
-- ----------------------------
DROP TABLE IF EXISTS `hy_board`;
CREATE TABLE `hy_board`  (
  `content` varchar(255) CHARACTER SET gbk COLLATE gbk_chinese_ci NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = gbk COLLATE = gbk_chinese_ci COMMENT = '系统公告表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of hy_board
-- ----------------------------
INSERT INTO `hy_board` VALUES ('欢迎来到我的新服');
INSERT INTO `hy_board` VALUES ('这里是属于我的世界');

-- ----------------------------
-- Table structure for hy_object
-- ----------------------------
DROP TABLE IF EXISTS `hy_object`;
CREATE TABLE `hy_object`  (
  `Id` int(0) NOT NULL COMMENT 'ObjectID',
  `Name` varchar(20) CHARACTER SET gbk COLLATE gbk_chinese_ci NULL DEFAULT NULL COMMENT '物品名称',
  `Weight` int(0) NULL DEFAULT NULL COMMENT '重量',
  `Icon` blob NULL COMMENT '图标',
  `Type` int(0) NULL DEFAULT NULL COMMENT '类型（武器、工具、任务道具、造型等等）',
  PRIMARY KEY (`Id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = gbk COLLATE = gbk_chinese_ci COMMENT = '物品道具表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of hy_object
-- ----------------------------

-- ----------------------------
-- Table structure for hy_role
-- ----------------------------
DROP TABLE IF EXISTS `hy_role`;
CREATE TABLE `hy_role`  (
  `RoleId` int(0) NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `UserId` int(0) NULL DEFAULT NULL COMMENT '账号ID',
  `RoleIndex` int(0) NULL DEFAULT NULL COMMENT '角色index(1-3)',
  `Lv` int(0) NULL DEFAULT 0 COMMENT '等级',
  `Name` varchar(10) CHARACTER SET gbk COLLATE gbk_chinese_ci NULL DEFAULT NULL COMMENT '角色名',
  `NickName` varchar(10) CHARACTER SET gbk COLLATE gbk_chinese_ci NULL DEFAULT NULL COMMENT '昵称',
  `R1` int(0) NULL DEFAULT NULL,
  `R2` int(0) NULL DEFAULT NULL,
  `R3` int(0) NULL DEFAULT NULL,
  `R4` int(0) NULL DEFAULT NULL,
  `RoleSex` int(0) NULL DEFAULT NULL COMMENT '0男 1女',
  `nowExp` int(0) NULL DEFAULT NULL COMMENT '当前经验值',
  `nowHp` int(0) NULL DEFAULT NULL COMMENT '当前气血',
  `nowMp` int(0) NULL DEFAULT NULL COMMENT '当前内力值',
  `nowSp` int(0) NULL DEFAULT NULL COMMENT '当前真气值',
  `maxSp` int(0) NULL DEFAULT NULL COMMENT '最大真气值',
  `nowMap` int(0) NULL DEFAULT NULL COMMENT '当前所处地图',
  `shengYu` int(0) NULL DEFAULT NULL COMMENT '当前声誉值',
  `L` int(0) NULL DEFAULT NULL COMMENT '力',
  `M` int(0) NULL DEFAULT NULL COMMENT '敏',
  `T` int(0) NULL DEFAULT NULL COMMENT '体',
  `Z` int(0) NULL DEFAULT NULL COMMENT '真',
  `J` int(0) NULL DEFAULT NULL COMMENT '精',
  `weiWang` int(0) NULL DEFAULT NULL COMMENT '威望',
  `nowMoney` int(0) NULL DEFAULT NULL COMMENT '金钱',
  `onlineTime` int(0) NULL DEFAULT NULL COMMENT '在线时长',
  `propPoint` int(0) NULL DEFAULT NULL COMMENT '属性点',
  `kongfuPoint` int(0) NULL DEFAULT NULL COMMENT '武点',
  `nowFz` int(0) NULL DEFAULT NULL COMMENT '当前负重',
  `maxFz` int(0) NULL DEFAULT NULL COMMENT '最大负重',
  `X` int(0) NULL DEFAULT NULL COMMENT '坐标X',
  `Y` int(0) NULL DEFAULT NULL COMMENT '坐标Y',
  PRIMARY KEY (`RoleId`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = gbk COLLATE = gbk_chinese_ci COMMENT = '角色表（每个账号最多有三个角色）' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of hy_role
-- ----------------------------
INSERT INTO `hy_role` VALUES (100000001, 1, 1, 349, '在线GM', '排1', 0, 0, 0, 0, 0, 42354233, 6322, 32, 6543, 9000, 1100, 354, 33, 44, 55, 66, 77, 6000, 3123, NULL, 59, 96236, NULL, 6963, 6104, 14000);
INSERT INTO `hy_role` VALUES (100000002, 1, 3, 0, '节制3', '排3', 0, 0, 0, 0, 0, 42354233, 8321, 32, 6543, 9000, 1100, 354, 33, 44, 55, 66, 77, 6000, 3123, NULL, NULL, NULL, NULL, NULL, 6104, 14552);
INSERT INTO `hy_role` VALUES (100000003, 2, 1, 999, '好宝贝', '排3', 0, 0, 0, 0, 0, 42354233, 8321, 32, 6543, 9000, 1100, 354, 33, 44, 55, 66, 77, 6000, 3123, NULL, NULL, NULL, NULL, NULL, 6204, 14000);

-- ----------------------------
-- Table structure for hy_role_bag
-- ----------------------------
DROP TABLE IF EXISTS `hy_role_bag`;
CREATE TABLE `hy_role_bag`  (
  `bagid` int(0) NOT NULL AUTO_INCREMENT,
  `RoleId` int(0) NOT NULL,
  `Btype` int(0) NOT NULL COMMENT '类型0装备 1背包 2镖局 10钱庄 11商城（钱庄和商城时，是属于User的，装备背包标局是属于角色的）',
  `No` int(0) NULL DEFAULT NULL COMMENT '序号',
  `ObjId` int(0) NULL DEFAULT NULL COMMENT '物品ID',
  `Count` int(0) NULL DEFAULT NULL COMMENT '数量',
  `ObjValue` int(0) NULL DEFAULT NULL COMMENT '物品值 武防攻击防御值  龙加几 宝石加几 战骑加几',
  `Add1` int(0) NULL DEFAULT NULL COMMENT '特殊属性1',
  `Add2` int(0) NULL DEFAULT NULL COMMENT '特殊属性2',
  `Add3` int(0) NULL DEFAULT NULL COMMENT '特殊属性3',
  `Stone1` int(0) NULL DEFAULT NULL COMMENT '镶嵌的宝石1',
  `Stone2` int(0) NULL DEFAULT NULL COMMENT '镶嵌的宝石2',
  `Stone3` int(0) NULL DEFAULT NULL COMMENT '镶嵌的宝石3',
  `Random1` int(0) NULL DEFAULT NULL COMMENT '随机效果1',
  `NowDurability` int(0) NULL DEFAULT NULL COMMENT '当前耐久',
  `MaxDurability` int(0) NULL DEFAULT NULL COMMENT '最大耐久',
  PRIMARY KEY (`bagid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = gbk COLLATE = gbk_chinese_ci COMMENT = '角色武器装备面板、背包、镖局、钱庄、商城表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of hy_role_bag
-- ----------------------------
INSERT INTO `hy_role_bag` VALUES (1, 100000001, 1, 1, 10189, 1, 1687, 111, 122, 133, 111, 122, 133, 12, 249, 249);
INSERT INTO `hy_role_bag` VALUES (2, 100000001, 1, 2, 10189, 1, 1687, 111, 122, 133, 111, 122, 133, 12, 249, 249);
INSERT INTO `hy_role_bag` VALUES (3, 100000001, 1, 3, 10189, 1, 1687, 111, 122, 133, 111, 122, 133, 12, 249, 249);
INSERT INTO `hy_role_bag` VALUES (4, 100000001, 1, 4, 10189, 1, 1687, 111, 122, 133, 111, 122, 133, 12, 249, 249);
INSERT INTO `hy_role_bag` VALUES (5, 100000001, 0, 1, 10189, 1, 1537, 111, 122, 133, 111, 122, 133, 12, 249, 249);
INSERT INTO `hy_role_bag` VALUES (6, 100000001, 0, 2, 61055, 0, 10, 0, 0, 0, 0, 0, 0, 0, 249, 249);
INSERT INTO `hy_role_bag` VALUES (7, 100000001, 0, 3, 24095, 0, 0, 0, 0, 0, 0, 0, 0, 0, 249, 249);
INSERT INTO `hy_role_bag` VALUES (8, 100000001, 0, 4, 22016, 0, 0, 0, 0, 0, 0, 0, 0, 0, 249, 249);
INSERT INTO `hy_role_bag` VALUES (9, 100000001, 0, 5, 31003, 0, 0, 0, 0, 0, 0, 0, 0, 0, 249, 249);
INSERT INTO `hy_role_bag` VALUES (10, 100000001, 0, 6, 28017, 0, 0, 0, 0, 0, 0, 0, 0, 0, 249, 249);
INSERT INTO `hy_role_bag` VALUES (11, 100000001, 0, 7, 26999, 0, 22, 0, 0, 0, 0, 0, 0, 0, 249, 249);
INSERT INTO `hy_role_bag` VALUES (12, 100000001, 0, 8, 30803, 0, 0, 0, 0, 0, 0, 0, 0, 0, 249, 249);
INSERT INTO `hy_role_bag` VALUES (13, 100000001, 0, 9, 29803, 0, 22, 0, 0, 0, 0, 0, 0, 0, 249, 249);
INSERT INTO `hy_role_bag` VALUES (14, 100000002, 1, 1, 10189, 1, 1687, 111, 122, 133, 111, 122, 133, 12, 249, 249);
INSERT INTO `hy_role_bag` VALUES (15, 100000002, 1, 2, 10189, 1, 1687, 111, 122, 133, 111, 122, 133, 12, 249, 249);
INSERT INTO `hy_role_bag` VALUES (16, 100000002, 1, 3, 10189, 1, 1687, 111, 122, 133, 111, 122, 133, 12, 249, 249);
INSERT INTO `hy_role_bag` VALUES (17, 100000002, 1, 4, 10189, 1, 1687, 111, 122, 133, 111, 122, 133, 12, 249, 249);
INSERT INTO `hy_role_bag` VALUES (18, 100000002, 0, 1, 10189, 1, 1687, 111, 122, 133, 111, 122, 133, 12, 249, 249);
INSERT INTO `hy_role_bag` VALUES (19, 100000002, 0, 2, 61055, 0, 10, 0, 0, 0, 0, 0, 0, 0, 249, 249);
INSERT INTO `hy_role_bag` VALUES (20, 100000002, 0, 3, 24095, 0, 0, 0, 0, 0, 0, 0, 0, 0, 249, 249);
INSERT INTO `hy_role_bag` VALUES (21, 100000002, 0, 4, 22016, 0, 0, 0, 0, 0, 0, 0, 0, 0, 249, 249);
INSERT INTO `hy_role_bag` VALUES (22, 100000002, 0, 5, 31003, 0, 0, 0, 0, 0, 0, 0, 0, 0, 249, 249);
INSERT INTO `hy_role_bag` VALUES (23, 100000002, 0, 6, 28017, 0, 0, 0, 0, 0, 0, 0, 0, 0, 249, 249);
INSERT INTO `hy_role_bag` VALUES (24, 100000002, 0, 7, 26999, 0, 22, 0, 0, 0, 0, 0, 0, 0, 249, 249);
INSERT INTO `hy_role_bag` VALUES (25, 100000002, 0, 8, 30803, 0, 0, 0, 0, 0, 0, 0, 0, 0, 249, 249);
INSERT INTO `hy_role_bag` VALUES (26, 100000002, 0, 9, 29803, 0, 22, 0, 0, 0, 0, 0, 0, 0, 249, 249);
INSERT INTO `hy_role_bag` VALUES (27, 100000003, 1, 1, 10189, 1, 1687, 111, 122, 133, 111, 122, 133, 12, 249, 249);
INSERT INTO `hy_role_bag` VALUES (28, 100000003, 1, 2, 10189, 1, 1687, 111, 122, 133, 111, 122, 133, 12, 249, 249);
INSERT INTO `hy_role_bag` VALUES (29, 100000003, 1, 3, 10189, 1, 1687, 111, 122, 133, 111, 122, 133, 12, 249, 249);
INSERT INTO `hy_role_bag` VALUES (30, 100000003, 1, 4, 10189, 1, 1687, 111, 122, 133, 111, 122, 133, 12, 249, 249);
INSERT INTO `hy_role_bag` VALUES (31, 100000003, 0, 1, 10189, 1, 1687, 111, 122, 133, 111, 122, 133, 12, 249, 249);
INSERT INTO `hy_role_bag` VALUES (32, 100000003, 0, 2, 61055, 0, 10, 0, 0, 0, 0, 0, 0, 0, 249, 249);
INSERT INTO `hy_role_bag` VALUES (33, 100000003, 0, 3, 24095, 0, 0, 0, 0, 0, 0, 0, 0, 0, 249, 249);
INSERT INTO `hy_role_bag` VALUES (34, 100000003, 0, 4, 22016, 0, 0, 0, 0, 0, 0, 0, 0, 0, 249, 249);
INSERT INTO `hy_role_bag` VALUES (35, 100000003, 0, 5, 31003, 0, 0, 0, 0, 0, 0, 0, 0, 0, 249, 249);
INSERT INTO `hy_role_bag` VALUES (36, 100000003, 0, 6, 28017, 0, 0, 0, 0, 0, 0, 0, 0, 0, 249, 249);
INSERT INTO `hy_role_bag` VALUES (37, 100000003, 0, 7, 26999, 0, 22, 0, 0, 0, 0, 0, 0, 0, 249, 249);
INSERT INTO `hy_role_bag` VALUES (38, 100000003, 0, 8, 30803, 0, 0, 0, 0, 0, 0, 0, 0, 0, 249, 249);
INSERT INTO `hy_role_bag` VALUES (39, 100000003, 0, 9, 29803, 0, 22, 0, 0, 0, 0, 0, 0, 0, 249, 249);

-- ----------------------------
-- Table structure for hy_user
-- ----------------------------
DROP TABLE IF EXISTS `hy_user`;
CREATE TABLE `hy_user`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `UserName` varchar(18) CHARACTER SET gbk COLLATE gbk_chinese_ci NOT NULL COMMENT '用户名',
  `PassWord` varchar(32) CHARACTER SET gbk COLLATE gbk_chinese_ci NULL DEFAULT NULL COMMENT '密码',
  `LoginIp` varchar(15) CHARACTER SET gbk COLLATE gbk_chinese_ci NULL DEFAULT NULL COMMENT '登录IP',
  `LastLoginIp` varchar(15) CHARACTER SET gbk COLLATE gbk_chinese_ci NULL DEFAULT NULL COMMENT '上次登录IP',
  `TransPass` varchar(16) CHARACTER SET gbk COLLATE gbk_chinese_ci NULL DEFAULT NULL COMMENT '交易密码',
  `StorePass` varchar(16) CHARACTER SET gbk COLLATE gbk_chinese_ci NULL DEFAULT NULL COMMENT '仓库密码',
  `InsertTime` datetime(0) NULL DEFAULT NULL COMMENT '注册时间',
  `UpdateTime` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`, `UserName`) USING BTREE,
  UNIQUE INDEX `userName`(`UserName`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 4 CHARACTER SET = gbk COLLATE = gbk_chinese_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of hy_user
-- ----------------------------
INSERT INTO `hy_user` VALUES (1, 'hy00001', 'e10adc3949ba59abbe56e057f20f883e', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `hy_user` VALUES (2, '1234567', 'e10adc3949ba59abbe56e057f20f883e', NULL, NULL, NULL, NULL, NULL, NULL);

SET FOREIGN_KEY_CHECKS = 1;
