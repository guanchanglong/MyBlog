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

 Date: 10/04/2022 15:56:32
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for b_type
-- ----------------------------
DROP TABLE IF EXISTS `b_type`;
CREATE TABLE `b_type`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '类型名称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

SET FOREIGN_KEY_CHECKS = 1;
