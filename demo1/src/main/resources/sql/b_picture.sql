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

 Date: 10/04/2022 15:56:13
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for b_picture
-- ----------------------------
DROP TABLE IF EXISTS `b_picture`;
CREATE TABLE `b_picture`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `date` datetime(0) NULL DEFAULT NULL COMMENT '图片拍摄日期',
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图片标题',
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图片拍摄地址',
  `info` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图片简介',
  `picture` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图片链接地址(暂时)',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间(用于排序)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

SET FOREIGN_KEY_CHECKS = 1;
