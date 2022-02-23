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

 Date: 22/02/2022 12:32:27
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for b_blog
-- ----------------------------
DROP TABLE IF EXISTS `b_blog`;
CREATE TABLE `b_blog`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `appreciation` bit(1) NOT NULL COMMENT '是否开启赞赏',
  `commentable` bit(1) NOT NULL COMMENT '是否开启评论',
  `published` bit(1) NOT NULL COMMENT '是否公开',
  `recommend` bit(1) NOT NULL COMMENT '是否开启推荐',
  `share_statement` bit(1) NOT NULL COMMENT '是否开启分享转载提示',
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文章标题',
  `create_time` datetime(6) NULL DEFAULT NULL COMMENT '文章创建时间',
  `update_time` datetime(6) NULL DEFAULT NULL COMMENT '文章更新时间',
  `content` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '文章内容',
  `first_picture` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文章封面图片',
  `views` int(11) NULL DEFAULT 0 COMMENT '文章浏览人数',
  `type_id` int(11) NULL DEFAULT NULL COMMENT '文章类型id',
  `user_id` int(11) NULL DEFAULT NULL COMMENT '文章作者id',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文章简介',
  `flag` int(1) NULL DEFAULT 0 COMMENT '博客的原创(0)、转载(1)和翻译(2)标识',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 33 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

SET FOREIGN_KEY_CHECKS = 1;
