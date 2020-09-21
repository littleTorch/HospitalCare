/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50727
Source Host           : localhost:3306
Source Database       : hospital_dr

Target Server Type    : MYSQL
Target Server Version : 50727
File Encoding         : 65001

Date: 2020-09-21 19:56:10
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `t_nurse_record`
-- ----------------------------
DROP TABLE IF EXISTS `t_nurse_record`;
CREATE TABLE `t_nurse_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create_time` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '创建者',
  `update_time` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '更新者',
  `cus_id` int(11) DEFAULT NULL COMMENT '客户id',
  `project_id` int(11) DEFAULT NULL COMMENT '护理项目id',
  `num` int(11) DEFAULT NULL COMMENT '护理次数',
  `project_time` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '开始护理时间',
  `remark` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `emp_id` int(11) DEFAULT NULL COMMENT '护理人员id',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  `is_deleted` int(1) DEFAULT '0',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of t_nurse_record
-- ----------------------------
INSERT INTO `t_nurse_record` VALUES ('1', '2020-02-15', '0', '2020-09-17', '0', '1', '1', '2', '2020-10-05', '隔一个月理一次发', '3', null, '0');
INSERT INTO `t_nurse_record` VALUES ('2', '2020-09-18', '0', null, null, '2', '1', '1', '2020-10-05', '需要细剪', '3', null, '0');
INSERT INTO `t_nurse_record` VALUES ('3', '2020-09-21 19:42:01', 'root', null, null, '1', '2', '1', '2020-09-21T16:00:00.000Z', '手脚都需', '3', null, '1');
INSERT INTO `t_nurse_record` VALUES ('4', '2020-09-21 19:44:39', 'root', null, null, '2', '4', '1', '2020-09-22T02:00:00.000Z', '无', '3', null, '1');
INSERT INTO `t_nurse_record` VALUES ('5', '2020-09-21 19:47:11', 'root', '2020-09-21 19:51:13', 'root', '3', '1', '2', '2020-09-22 02:02:00', '333', '3', null, '1');
