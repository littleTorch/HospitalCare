/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50727
Source Host           : localhost:3306
Source Database       : hospital_dr

Target Server Type    : MYSQL
Target Server Version : 50727
File Encoding         : 65001

Date: 2020-09-21 21:48:58
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `t_leave`
-- ----------------------------
DROP TABLE IF EXISTS `t_leave`;
CREATE TABLE `t_leave` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create_time` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '创建者',
  `update_time` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '更新者',
  `cus_id` int(11) DEFAULT NULL COMMENT '客户id',
  `leave_reason` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '外出事由',
  `leave_time` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '外出时间',
  `expect_time` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '预计回院时间',
  `actual_time` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '实际回院时间',
  `accompany` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '陪同人',
  `accompany_phone` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '陪同人联系电话',
  `audit_person` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '审批人',
  `audit` int(1) DEFAULT NULL COMMENT '审核状态',
  `audit_time` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '审核时间',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  `is_deleted` int(1) DEFAULT '0',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of t_leave
-- ----------------------------
INSERT INTO `t_leave` VALUES ('1', '2020-09-21', '0', null, null, '1', '健身运动', '2020-09-21 17:30:00', '2020-09-21 19:00:00', '2020-09-21 18:45:00', '刘大哥', '123456789', '0', '1', '2020-09-21 15:00:00', null, '0');
INSERT INTO `t_leave` VALUES ('3', '2020-09-22', '0', null, null, '2', '去医院', '2020-09-21 17:30:00', '2020-09-21 19:00:00', '', '张大叔', '123456798', '0', '2', '2020-09-21 15:00:00', null, '0');
INSERT INTO `t_leave` VALUES ('5', '2020-09-21 21:06:10', 'root', '2020-09-21 21:45:55', 'root', '3', '0.0.', '2020-09-24 00:00:00', '2020-09-26 00:00:00', '', '刘xx', '123564498', '', null, '', null, '1');
INSERT INTO `t_leave` VALUES ('6', '2020-09-21 21:07:51', 'root', '2020-09-21 21:40:06', '', '3', '13', '2020-08-11 00:00:00', '2020-09-23 00:00:00', '', 'xx', '131654', 'root', '2', '2020-09-21 21:40:06', null, '1');
