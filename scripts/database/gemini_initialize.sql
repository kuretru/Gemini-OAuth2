/*
 Navicat Premium Data Transfer

 Source Server         : ali-hgh
 Source Server Type    : MySQL
 Source Server Version : 80030 (8.0.30)
 Source Host           : localhost:3306
 Source Schema         : prod_gemini

 Target Server Type    : MySQL
 Target Server Version : 80030 (8.0.30)
 File Encoding         : 65001

 Date: 07/02/2023 16:43:56
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for oauth_application
-- ----------------------------
DROP TABLE IF EXISTS `oauth_application`;
CREATE TABLE `oauth_application`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '物理主键，自增',
  `uuid` char(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '业务逻辑主键',
  `create_time` datetime NOT NULL COMMENT '记录主动创建的时刻',
  `update_time` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '记录上一次被动更新的时刻',
  `name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '应用名称',
  `avatar` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '应用头像URL',
  `description` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '应用描述',
  `homepage` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '应用主页地址',
  `callback` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '回调地址',
  `client_id` char(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '客户端ID',
  `client_secret` char(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '客户端Secret',
  `secret_create_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '客户端Secret创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_uuid`(`uuid` ASC) USING BTREE,
  UNIQUE INDEX `idx_client_id`(`client_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'OAuth应用表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for oauth_permission
-- ----------------------------
DROP TABLE IF EXISTS `oauth_permission`;
CREATE TABLE `oauth_permission`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '物理主键，自增',
  `uuid` char(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '业务逻辑主键',
  `create_time` datetime NOT NULL COMMENT '记录主动创建的时刻',
  `update_time` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '记录上一次被动更新的时刻',
  `application_id` char(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '应用ID',
  `user_id` char(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '用户ID',
  `permissions` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '用户给应用授予的权限列表',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_uuid`(`uuid` ASC) USING BTREE,
  UNIQUE INDEX `uk_application_user`(`application_id` ASC, `user_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'OAuth权限表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '物理主键，自增',
  `uuid` char(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '业务逻辑主键',
  `create_time` datetime NOT NULL COMMENT '记录主动创建的时刻',
  `update_time` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '记录上一次被动更新的时刻',
  `username` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '用户名',
  `password` char(68) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '密码',
  `nickname` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '昵称',
  `avatar` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '头像URL',
  `email` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '电子邮箱',
  `mobile` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '手机号码',
  `last_login` datetime NOT NULL COMMENT '上一次登录的时间',
  `is_admin` bit(1) NOT NULL COMMENT '是否是管理员',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_uuid`(`uuid` ASC) USING BTREE,
  INDEX `idx_username_email_mobile`(`username` ASC, `email` ASC, `mobile` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户表' ROW_FORMAT = DYNAMIC;

SET FOREIGN_KEY_CHECKS = 1;
