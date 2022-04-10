/*
 Navicat MySQL Data Transfer

 Source Server         : 腾讯云服务器个人博客
 Source Server Type    : MySQL
 Source Server Version : 50650
 Source Host           : 175.178.65.191:3306
 Source Schema         : myblog

 Target Server Type    : MySQL
 Target Server Version : 50650
 File Encoding         : 65001

 Date: 10/04/2022 15:55:40
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for b_day_count
-- ----------------------------
DROP TABLE IF EXISTS `b_day_count`;
CREATE TABLE `b_day_count`  (
  `id` int(11) NOT NULL COMMENT '主键',
  `day` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '记录当前日期',
  `count` int(11) NULL DEFAULT NULL COMMENT '记录当前日期的浏览数',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

SET FOREIGN_KEY_CHECKS = 1;
