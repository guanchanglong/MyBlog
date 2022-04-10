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

 Date: 10/04/2022 15:55:32
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for b_comment_relation
-- ----------------------------
DROP TABLE IF EXISTS `b_comment_relation`;
CREATE TABLE `b_comment_relation`  (
  `child_comment_id` int(11) NOT NULL COMMENT '子评论id',
  `father_comment_id` int(11) NOT NULL COMMENT '父评论id',
  PRIMARY KEY (`child_comment_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

SET FOREIGN_KEY_CHECKS = 1;
