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

 Date: 10/04/2022 15:56:40
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for b_user
-- ----------------------------
DROP TABLE IF EXISTS `b_user`;
CREATE TABLE `b_user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `avatar` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '头像',
  `create_time` datetime(6) NULL DEFAULT NULL COMMENT '创建时间',
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `nickname` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '昵称',
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
  `update_time` datetime(6) NULL DEFAULT NULL COMMENT '修改日期',
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名(登录用)',
  `about_me` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '个人简介',
  `role` int(11) NULL DEFAULT NULL COMMENT '用户类型',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

SET FOREIGN_KEY_CHECKS = 1;
