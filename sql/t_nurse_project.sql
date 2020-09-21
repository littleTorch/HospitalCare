/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50727
Source Host           : localhost:3306
Source Database       : hospital_dr

Target Server Type    : MYSQL
Target Server Version : 50727
File Encoding         : 65001

Date: 2020-09-21 19:56:04
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `t_nurse_project`
-- ----------------------------
DROP TABLE IF EXISTS `t_nurse_project`;
CREATE TABLE `t_nurse_project` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `create_time` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '创建者',
  `update_time` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '更新者',
  `project_name` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '护理项目名称',
  `price` mediumint(9) DEFAULT NULL COMMENT '价格',
  `details` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '描述',
  `add_service` int(1) DEFAULT NULL COMMENT '是否增值服务0否1是',
  `status` int(1) DEFAULT NULL COMMENT '状态1启用2停用',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  `is_deleted` int(1) DEFAULT '0',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of t_nurse_project
-- ----------------------------
INSERT INTO `t_nurse_project` VALUES ('1', '2020-09-15', '0', '2020-09-15', '0', '理发', '15', '单剪', '0', '1', null, '0');
INSERT INTO `t_nurse_project` VALUES ('2', '2020-09-15', '0 ', '2020-09-15', '0 ', '指甲护理', '10', '修理手脚指甲', '0', '1', null, '0');
INSERT INTO `t_nurse_project` VALUES ('3', '2020-09-15', '0', '2020-09-15', '0', '身体清洁', '20', '搓澡', '1', '1', null, '0');
INSERT INTO `t_nurse_project` VALUES ('4', '2020-09-17 20:51:13', 'root', '2020-09-17 21:04:19', 'root', '三高检查', '100', '检查老人的高血压、高血糖、高血脂项目', '1', '2', null, '0');
