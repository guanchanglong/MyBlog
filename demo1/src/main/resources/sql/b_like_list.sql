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

 Date: 10/04/2022 15:55:54
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for b_like_list
-- ----------------------------
DROP TABLE IF EXISTS `b_like_list`;
CREATE TABLE `b_like_list`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `blog_id` int(11) NULL DEFAULT NULL COMMENT '博客id',
  `viewer_ip` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '访问者的ip地址',
  `like` bit(1) NULL DEFAULT b'0' COMMENT '点赞(1是点赞，0是没点赞)',
  `un_like` bit(1) NULL DEFAULT b'0' COMMENT '踩(1是踩，0是没踩)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

SET FOREIGN_KEY_CHECKS = 1;
