/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50727
Source Host           : localhost:3306
Source Database       : hospital_dr

Target Server Type    : MYSQL
Target Server Version : 50727
File Encoding         : 65001

Date: 2020-09-21 19:55:55
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `t_nurse_level`
-- ----------------------------
DROP TABLE IF EXISTS `t_nurse_level`;
CREATE TABLE `t_nurse_level` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create_time` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '创建者',
  `update_time` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '更新者',
  `level` int(11) DEFAULT NULL COMMENT '护理等级',
  `project_id` int(11) DEFAULT NULL COMMENT '项目编号',
  `status` int(1) DEFAULT NULL COMMENT '状态',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  `is_deleted` int(1) DEFAULT '0',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of t_nurse_level
-- ----------------------------
INSERT INTO `t_nurse_level` VALUES ('1', '2020-09-15', '0', '2020-09-15', '0', '1', '1', '1', null, '0');
INSERT INTO `t_nurse_level` VALUES ('3', '2020-09-15', '0', '2020-09-15', '0', '1', '2', '1', null, '0');
INSERT INTO `t_nurse_level` VALUES ('4', '2020-09-15', '0', '2020-09-15', '0', '2', '3', '1', null, '1');
INSERT INTO `t_nurse_level` VALUES ('9', '2020-09-17 19:38:44', 'root', '2020-09-17 20:01:06', '', '3', '3', '1', null, '1');
INSERT INTO `t_nurse_level` VALUES ('10', '2020-09-17 20:02:43', 'root', null, null, '1', '3', '1', null, '1');
INSERT INTO `t_nurse_level` VALUES ('11', '2020-09-17 20:04:27', 'root', '2020-09-17 21:19:18', 'root', '1', '3', '1', null, '0');
INSERT INTO `t_nurse_level` VALUES ('12', '2020-09-17 20:04:31', 'root', '2020-09-17 20:13:41', 'root', '1', '2', '1', null, '1');
INSERT INTO `t_nurse_level` VALUES ('13', '2020-09-17 20:51:51', 'root', null, null, '5', '4', '1', null, '1');
