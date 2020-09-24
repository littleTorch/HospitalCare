/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50727
Source Host           : localhost:3306
Source Database       : hospital_dr

Target Server Type    : MYSQL
Target Server Version : 50727
File Encoding         : 65001

Date: 2020-09-24 20:27:11
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `permission`
-- ----------------------------
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission` (
  `id` mediumint(11) NOT NULL AUTO_INCREMENT,
  `parent_id` mediumint(11) DEFAULT NULL COMMENT '父权限id',
  `parent_name` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '父权限名',
  `label` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '权限名称',
  `code` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '授权标识符',
  `path` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '路由地址',
  `name` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '路径名',
  `url` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '授权路径',
  `order_num` int(11) DEFAULT NULL COMMENT '序号',
  `type` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '类型（0目录，1菜单，2按钮）',
  `icon` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '图标',
  `remark` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `create_time` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '创建时间',
  `update_time` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '修改时间',
  `is_home` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of permission
-- ----------------------------
INSERT INTO `permission` VALUES ('1', '20', null, '床位管理', 'hc:bedManage', '/bedManage', 'bedManage', '/clientAbout/bedManage/BedManage', '0', '1', 'el-icon-date', null, null, null, null);
INSERT INTO `permission` VALUES ('2', '0', null, '护理相关管理', 'hc:nurseManage', '/nurseManage', null, null, '1', '0', 'el-icon-edit-outline', null, null, null, null);
INSERT INTO `permission` VALUES ('3', '2', null, '护理级别', 'hc:nurseLevel', '/nurseLevel', 'nurseLevel', '/nurseManage/nurseLevel/NurseLevel', '0', '1', 'el-icon-date', null, null, null, null);
INSERT INTO `permission` VALUES ('4', '2', null, '护理内容', 'hc:nurseProject', '/nurseProject', 'nurseProject', '/nurseManage/nurseProject/NurseProject', '1', '1', 'el-icon-document-copy', null, null, null, null);
INSERT INTO `permission` VALUES ('5', '2', null, '护理记录', 'hc:nurseRecord', '/nurseRecord', 'nurseRecord', '/nurseManage/nurseRecord/NurseRecord', '2', '1', 'el-icon-s-goods', null, null, null, null);
INSERT INTO `permission` VALUES ('6', '0', null, '膳食管理', 'hc:foodManage', '/foodManage', null, null, '2', '0', 'el-icon-menu', null, null, null, null);
INSERT INTO `permission` VALUES ('7', '6', null, '膳食日历', 'hc:foodDate', '/foodDate', 'foodDate', '/foodManage/foodDate/FoodDate', '0', '1', 'el-icon-school', null, null, null, null);
INSERT INTO `permission` VALUES ('8', '6', null, '膳食管理', 'hc:foodManage', '/foodManage', 'foodManage', '/foodManage/foodManage/FoodManage', '1', '1', 'el-icon-pie-chart', null, null, null, null);
INSERT INTO `permission` VALUES ('9', '0', null, '住宿与外出', 'hc:checkinOut', '/checkinOut', null, null, '3', '0', 'el-icon-receiving', null, null, null, null);
INSERT INTO `permission` VALUES ('10', '9', null, '入住登记', 'hc:checkin', '/checkin', 'checkin', '/checkinOut/checkin/Checkin', '0', '1', 'el-icon-files', null, null, null, null);
INSERT INTO `permission` VALUES ('11', '9', null, '退住登记', 'hc:checkout', '/checkout', 'checkout', '/checkinOut/checkout/Checkout', '1', '1', 'el-icon-s-operation', null, null, null, null);
INSERT INTO `permission` VALUES ('12', '9', null, '外出登记', 'hc:leave', '/leave', 'leave', '/checkinOut/leave/Leave', '2', '1', 'el-icon-s-operation', null, null, null, null);
INSERT INTO `permission` VALUES ('13', '0', null, '服务与用户', 'hc:serviceUser', '/serviceUser', null, null, '4', '0', 'el-icon-receiving', null, null, null, null);
INSERT INTO `permission` VALUES ('14', '13', null, '设置服务对象', 'hc:serviceObj', '/serviceObj', 'serviceObj', '/serviceUser/serviceObj/ServiceObj', '0', '1', 'el-icon-files', null, null, null, null);
INSERT INTO `permission` VALUES ('15', '13', null, '服务关注', 'hc:serviceFocus', '/seerviceFocus', 'serviceFocus', '/serviceUser/serviceFocus/ServiceFocus', '1', '1', 'el-icon-s-operation', null, null, null, null);
INSERT INTO `permission` VALUES ('16', '13', null, '用户管理', 'hc:user', '/user', 'user', '/serviceUser/user/User', '2', '1', 'el-icon-s-operation', null, null, null, null);
INSERT INTO `permission` VALUES ('17', '0', null, '系统管理', 'hc:system', '/system', null, null, '5', '0', 'el-icon-receiving', null, null, null, null);
INSERT INTO `permission` VALUES ('18', '17', null, '角色管理', 'hc:role', '/role', 'role', '/system/role/Role', '0', '1', 'el-icon-s-operation', null, null, null, null);
INSERT INTO `permission` VALUES ('19', '17', null, '权限管理', 'hc:permission', '/permission', 'permission', '/system/permission/Permission', '1', '1', 'el-icon-s-operation', null, null, null, null);
INSERT INTO `permission` VALUES ('20', '0', null, '客户相关', 'hc:clientAbout', '/clientAbout', '', null, '6', '0', 'el-icon-receiving', null, null, null, null);
INSERT INTO `permission` VALUES ('21', '20', null, '客户管理', 'hc:client', '/client', 'client', '/clientAbout/client/Client', '1', '1', 'el-icon-s-operation', null, null, null, null);
INSERT INTO `permission` VALUES ('22', '6', null, '食物管理', 'hc:food', '/food', 'food', '/foodManage/food/Food', '2', '1', 'el-icon-s-operation', null, null, null, null);

-- ----------------------------
-- Table structure for `role`
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '角色名',
  `remark` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `create_time` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '创建时间',
  `updatae_time` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('1', '超级管理员', '拥有所有的权限', null, null);

-- ----------------------------
-- Table structure for `role_permission`
-- ----------------------------
DROP TABLE IF EXISTS `role_permission`;
CREATE TABLE `role_permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) DEFAULT NULL,
  `permission_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of role_permission
-- ----------------------------
INSERT INTO `role_permission` VALUES ('1', '1', '1');
INSERT INTO `role_permission` VALUES ('2', '1', '2');
INSERT INTO `role_permission` VALUES ('3', '1', '3');
INSERT INTO `role_permission` VALUES ('4', '1', '4');
INSERT INTO `role_permission` VALUES ('5', '1', '5');
INSERT INTO `role_permission` VALUES ('6', '1', '6');
INSERT INTO `role_permission` VALUES ('7', '1', '7');
INSERT INTO `role_permission` VALUES ('8', '1', '8');
INSERT INTO `role_permission` VALUES ('9', '1', '9');
INSERT INTO `role_permission` VALUES ('10', '1', '10');
INSERT INTO `role_permission` VALUES ('11', '1', '11');
INSERT INTO `role_permission` VALUES ('12', '1', '12');
INSERT INTO `role_permission` VALUES ('13', '1', '13');
INSERT INTO `role_permission` VALUES ('14', '1', '14');
INSERT INTO `role_permission` VALUES ('15', '1', '15');
INSERT INTO `role_permission` VALUES ('16', '1', '16');
INSERT INTO `role_permission` VALUES ('17', '1', '17');
INSERT INTO `role_permission` VALUES ('18', '1', '18');
INSERT INTO `role_permission` VALUES ('19', '1', '19');
INSERT INTO `role_permission` VALUES ('20', '1', '20');
INSERT INTO `role_permission` VALUES ('21', '1', '21');
INSERT INTO `role_permission` VALUES ('22', '1', '22');

-- ----------------------------
-- Table structure for `t_bed`
-- ----------------------------
DROP TABLE IF EXISTS `t_bed`;
CREATE TABLE `t_bed` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '床位id',
  `create_time` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '创建者',
  `update_time` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '更新者',
  `floor` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '所属楼房',
  `room_no` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '房间号码',
  `bed_no` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '床位号',
  `position_type` int(11) DEFAULT '0' COMMENT '类型（0普通（默认）、1特殊）',
  `bed_status` int(11) DEFAULT '0' COMMENT '床位状态（0空（默认），1使用中）',
  `remark` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  `is_deleted` int(1) DEFAULT '0' COMMENT '逻辑删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=148 DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of t_bed
-- ----------------------------
INSERT INTO `t_bed` VALUES ('3', null, null, null, null, '1', '101', '1', '0', '0', null, null, '1');
INSERT INTO `t_bed` VALUES ('4', '2020-09-16 21:04:53', 'root', null, null, '1', '101', '1', '0', '0', null, null, '0');
INSERT INTO `t_bed` VALUES ('5', '2020-09-16 21:04:54', 'root', null, null, '1', '101', '2', '0', '0', null, null, '0');
INSERT INTO `t_bed` VALUES ('6', '2020-09-16 21:04:54', 'root', null, null, '1', '101', '3', '0', '0', null, null, '0');
INSERT INTO `t_bed` VALUES ('7', '2020-09-16 21:04:54', 'root', null, null, '1', '101', '4', '0', '0', null, null, '0');
INSERT INTO `t_bed` VALUES ('8', '2020-09-16 21:04:54', 'root', null, null, '1', '102', '1', '0', '0', null, null, '0');
INSERT INTO `t_bed` VALUES ('9', '2020-09-16 21:04:54', 'root', null, null, '1', '102', '2', '0', '0', null, null, '0');
INSERT INTO `t_bed` VALUES ('10', '2020-09-16 21:04:54', 'root', null, null, '1', '102', '3', '0', '0', null, null, '0');
INSERT INTO `t_bed` VALUES ('11', '2020-09-16 21:04:54', 'root', null, null, '1', '102', '4', '0', '0', null, null, '0');
INSERT INTO `t_bed` VALUES ('12', '2020-09-16 21:04:54', 'root', null, null, '1', '103', '1', '0', '0', null, null, '0');
INSERT INTO `t_bed` VALUES ('13', '2020-09-16 21:04:54', 'root', null, null, '1', '103', '2', '0', '0', null, null, '0');
INSERT INTO `t_bed` VALUES ('14', '2020-09-16 21:04:54', 'root', null, null, '1', '103', '3', '0', '0', null, null, '0');
INSERT INTO `t_bed` VALUES ('15', '2020-09-16 21:04:54', 'root', null, null, '1', '103', '4', '0', '0', null, null, '0');
INSERT INTO `t_bed` VALUES ('16', '2020-09-16 21:04:54', 'root', null, null, '1', '104', '1', '0', '0', null, null, '0');
INSERT INTO `t_bed` VALUES ('17', '2020-09-16 21:04:54', 'root', null, null, '1', '104', '2', '0', '0', null, null, '0');
INSERT INTO `t_bed` VALUES ('18', '2020-09-16 21:04:54', 'root', null, null, '1', '104', '3', '0', '0', null, null, '0');
INSERT INTO `t_bed` VALUES ('19', '2020-09-16 21:04:54', 'root', null, null, '1', '104', '4', '0', '0', null, null, '0');
INSERT INTO `t_bed` VALUES ('20', '2020-09-16 21:04:54', 'root', null, null, '1', '105', '1', '0', '0', null, null, '0');
INSERT INTO `t_bed` VALUES ('21', '2020-09-16 21:04:54', 'root', null, null, '1', '105', '2', '0', '0', null, null, '0');
INSERT INTO `t_bed` VALUES ('22', '2020-09-16 21:04:54', 'root', null, null, '1', '105', '3', '0', '0', null, null, '0');
INSERT INTO `t_bed` VALUES ('23', '2020-09-16 21:04:54', 'root', null, null, '1', '105', '4', '0', '0', null, null, '0');
INSERT INTO `t_bed` VALUES ('24', '2020-09-16 21:04:54', 'root', null, null, '1', '106', '1', '0', '0', null, null, '0');
INSERT INTO `t_bed` VALUES ('25', '2020-09-16 21:04:54', 'root', null, null, '1', '106', '2', '0', '0', null, null, '0');
INSERT INTO `t_bed` VALUES ('26', '2020-09-16 21:04:54', 'root', null, null, '1', '106', '3', '0', '0', null, null, '0');
INSERT INTO `t_bed` VALUES ('27', '2020-09-16 21:04:54', 'root', null, null, '1', '106', '4', '0', '0', null, null, '0');
INSERT INTO `t_bed` VALUES ('28', '2020-09-16 21:04:54', 'root', null, null, '1', '107', '1', '0', '0', null, null, '0');
INSERT INTO `t_bed` VALUES ('29', '2020-09-16 21:04:54', 'root', null, null, '1', '107', '2', '0', '0', null, null, '0');
INSERT INTO `t_bed` VALUES ('30', '2020-09-16 21:04:54', 'root', null, null, '1', '107', '3', '0', '0', null, null, '0');
INSERT INTO `t_bed` VALUES ('31', '2020-09-16 21:04:54', 'root', null, null, '1', '107', '4', '0', '0', null, null, '0');
INSERT INTO `t_bed` VALUES ('32', '2020-09-16 21:04:54', 'root', null, null, '1', '108', '1', '0', '0', null, null, '0');
INSERT INTO `t_bed` VALUES ('33', '2020-09-16 21:04:54', 'root', null, null, '1', '108', '2', '0', '0', null, null, '0');
INSERT INTO `t_bed` VALUES ('34', '2020-09-16 21:04:54', 'root', null, null, '1', '108', '3', '0', '0', null, null, '0');
INSERT INTO `t_bed` VALUES ('35', '2020-09-16 21:04:54', 'root', null, null, '1', '108', '4', '0', '0', null, null, '0');
INSERT INTO `t_bed` VALUES ('36', '2020-09-16 21:04:54', 'root', null, null, '1', '109', '1', '0', '0', null, null, '0');
INSERT INTO `t_bed` VALUES ('37', '2020-09-16 21:04:54', 'root', null, null, '1', '109', '2', '0', '0', null, null, '0');
INSERT INTO `t_bed` VALUES ('38', '2020-09-16 21:04:54', 'root', null, null, '1', '109', '3', '0', '0', null, null, '0');
INSERT INTO `t_bed` VALUES ('39', '2020-09-16 21:04:54', 'root', null, null, '1', '109', '4', '0', '0', null, null, '0');
INSERT INTO `t_bed` VALUES ('40', '2020-09-16 21:04:54', 'root', null, null, '2', '201', '1', '0', '0', null, null, '0');
INSERT INTO `t_bed` VALUES ('41', '2020-09-16 21:04:54', 'root', null, null, '2', '201', '2', '0', '0', null, null, '0');
INSERT INTO `t_bed` VALUES ('42', '2020-09-16 21:04:54', 'root', null, null, '2', '201', '3', '0', '0', null, null, '0');
INSERT INTO `t_bed` VALUES ('43', '2020-09-16 21:04:54', 'root', null, null, '2', '201', '4', '0', '0', null, null, '0');
INSERT INTO `t_bed` VALUES ('44', '2020-09-16 21:04:54', 'root', null, null, '2', '202', '1', '0', '0', null, null, '0');
INSERT INTO `t_bed` VALUES ('45', '2020-09-16 21:04:54', 'root', null, null, '2', '202', '2', '0', '0', null, null, '0');
INSERT INTO `t_bed` VALUES ('46', '2020-09-16 21:04:54', 'root', null, null, '2', '202', '3', '0', '0', null, null, '0');
INSERT INTO `t_bed` VALUES ('47', '2020-09-16 21:04:54', 'root', null, null, '2', '202', '4', '0', '0', null, null, '0');
INSERT INTO `t_bed` VALUES ('48', '2020-09-16 21:04:54', 'root', null, null, '2', '203', '1', '0', '0', null, null, '0');
INSERT INTO `t_bed` VALUES ('49', '2020-09-16 21:04:54', 'root', null, null, '2', '203', '2', '0', '0', null, null, '0');
INSERT INTO `t_bed` VALUES ('50', '2020-09-16 21:04:54', 'root', null, null, '2', '203', '3', '0', '0', null, null, '0');
INSERT INTO `t_bed` VALUES ('51', '2020-09-16 21:04:54', 'root', null, null, '2', '203', '4', '0', '0', null, null, '0');
INSERT INTO `t_bed` VALUES ('52', '2020-09-16 21:04:54', 'root', null, null, '2', '204', '1', '0', '0', null, null, '0');
INSERT INTO `t_bed` VALUES ('53', '2020-09-16 21:04:54', 'root', null, null, '2', '204', '2', '0', '0', null, null, '0');
INSERT INTO `t_bed` VALUES ('54', '2020-09-16 21:04:54', 'root', null, null, '2', '204', '3', '0', '0', null, null, '0');
INSERT INTO `t_bed` VALUES ('55', '2020-09-16 21:04:54', 'root', null, null, '2', '204', '4', '0', '0', null, null, '0');
INSERT INTO `t_bed` VALUES ('56', '2020-09-16 21:04:54', 'root', null, null, '2', '205', '1', '0', '0', null, null, '0');
INSERT INTO `t_bed` VALUES ('57', '2020-09-16 21:04:54', 'root', null, null, '2', '205', '2', '0', '0', null, null, '0');
INSERT INTO `t_bed` VALUES ('58', '2020-09-16 21:04:54', 'root', null, null, '2', '205', '3', '0', '0', null, null, '0');
INSERT INTO `t_bed` VALUES ('59', '2020-09-16 21:04:54', 'root', null, null, '2', '205', '4', '0', '0', null, null, '0');
INSERT INTO `t_bed` VALUES ('60', '2020-09-16 21:04:54', 'root', null, null, '2', '206', '1', '0', '0', null, null, '0');
INSERT INTO `t_bed` VALUES ('61', '2020-09-16 21:04:54', 'root', null, null, '2', '206', '2', '0', '0', null, null, '0');
INSERT INTO `t_bed` VALUES ('62', '2020-09-16 21:04:54', 'root', null, null, '2', '206', '3', '0', '0', null, null, '0');
INSERT INTO `t_bed` VALUES ('63', '2020-09-16 21:04:54', 'root', null, null, '2', '206', '4', '0', '0', null, null, '0');
INSERT INTO `t_bed` VALUES ('64', '2020-09-16 21:04:54', 'root', null, null, '2', '207', '1', '0', '0', null, null, '0');
INSERT INTO `t_bed` VALUES ('65', '2020-09-16 21:04:54', 'root', null, null, '2', '207', '2', '0', '0', null, null, '0');
INSERT INTO `t_bed` VALUES ('66', '2020-09-16 21:04:54', 'root', null, null, '2', '207', '3', '0', '0', null, null, '0');
INSERT INTO `t_bed` VALUES ('67', '2020-09-16 21:04:54', 'root', null, null, '2', '207', '4', '0', '0', null, null, '0');
INSERT INTO `t_bed` VALUES ('68', '2020-09-16 21:04:54', 'root', null, null, '2', '208', '1', '0', '0', null, null, '0');
INSERT INTO `t_bed` VALUES ('69', '2020-09-16 21:04:54', 'root', null, null, '2', '208', '2', '0', '0', null, null, '0');
INSERT INTO `t_bed` VALUES ('70', '2020-09-16 21:04:54', 'root', null, null, '2', '208', '3', '0', '0', null, null, '0');
INSERT INTO `t_bed` VALUES ('71', '2020-09-16 21:04:54', 'root', null, null, '2', '208', '4', '0', '0', null, null, '0');
INSERT INTO `t_bed` VALUES ('72', '2020-09-16 21:04:54', 'root', null, null, '2', '209', '1', '0', '0', null, null, '0');
INSERT INTO `t_bed` VALUES ('73', '2020-09-16 21:04:54', 'root', null, null, '2', '209', '2', '0', '0', null, null, '0');
INSERT INTO `t_bed` VALUES ('74', '2020-09-16 21:04:54', 'root', null, null, '2', '209', '3', '0', '0', null, null, '0');
INSERT INTO `t_bed` VALUES ('75', '2020-09-16 21:04:54', 'root', null, null, '2', '209', '4', '0', '0', null, null, '0');
INSERT INTO `t_bed` VALUES ('76', '2020-09-16 21:04:54', 'root', null, null, '3', '301', '1', '0', '0', null, null, '0');
INSERT INTO `t_bed` VALUES ('77', '2020-09-16 21:04:54', 'root', null, null, '3', '301', '2', '0', '0', null, null, '0');
INSERT INTO `t_bed` VALUES ('78', '2020-09-16 21:04:54', 'root', null, null, '3', '301', '3', '0', '0', null, null, '0');
INSERT INTO `t_bed` VALUES ('79', '2020-09-16 21:04:54', 'root', null, null, '3', '301', '4', '0', '0', null, null, '0');
INSERT INTO `t_bed` VALUES ('80', '2020-09-16 21:04:54', 'root', null, null, '3', '302', '1', '0', '0', null, null, '0');
INSERT INTO `t_bed` VALUES ('81', '2020-09-16 21:04:54', 'root', null, null, '3', '302', '2', '0', '0', null, null, '0');
INSERT INTO `t_bed` VALUES ('82', '2020-09-16 21:04:54', 'root', null, null, '3', '302', '3', '0', '0', null, null, '0');
INSERT INTO `t_bed` VALUES ('83', '2020-09-16 21:04:54', 'root', null, null, '3', '302', '4', '0', '0', null, null, '0');
INSERT INTO `t_bed` VALUES ('84', '2020-09-16 21:04:54', 'root', null, null, '3', '303', '1', '0', '0', null, null, '0');
INSERT INTO `t_bed` VALUES ('85', '2020-09-16 21:04:54', 'root', null, null, '3', '303', '2', '0', '0', null, null, '0');
INSERT INTO `t_bed` VALUES ('86', '2020-09-16 21:04:54', 'root', null, null, '3', '303', '3', '0', '0', null, null, '0');
INSERT INTO `t_bed` VALUES ('87', '2020-09-16 21:04:54', 'root', null, null, '3', '303', '4', '0', '0', null, null, '0');
INSERT INTO `t_bed` VALUES ('88', '2020-09-16 21:04:54', 'root', null, null, '3', '304', '1', '0', '0', null, null, '0');
INSERT INTO `t_bed` VALUES ('89', '2020-09-16 21:04:54', 'root', null, null, '3', '304', '2', '0', '0', null, null, '0');
INSERT INTO `t_bed` VALUES ('90', '2020-09-16 21:04:54', 'root', null, null, '3', '304', '3', '0', '0', null, null, '0');
INSERT INTO `t_bed` VALUES ('91', '2020-09-16 21:04:54', 'root', null, null, '3', '304', '4', '0', '0', null, null, '0');
INSERT INTO `t_bed` VALUES ('92', '2020-09-16 21:04:54', 'root', null, null, '3', '305', '1', '0', '0', null, null, '0');
INSERT INTO `t_bed` VALUES ('93', '2020-09-16 21:04:54', 'root', null, null, '3', '305', '2', '0', '0', null, null, '0');
INSERT INTO `t_bed` VALUES ('94', '2020-09-16 21:04:54', 'root', null, null, '3', '305', '3', '0', '0', null, null, '0');
INSERT INTO `t_bed` VALUES ('95', '2020-09-16 21:04:54', 'root', null, null, '3', '305', '4', '0', '0', null, null, '0');
INSERT INTO `t_bed` VALUES ('96', '2020-09-16 21:04:54', 'root', null, null, '3', '306', '1', '0', '0', null, null, '0');
INSERT INTO `t_bed` VALUES ('97', '2020-09-16 21:04:54', 'root', null, null, '3', '306', '2', '0', '0', null, null, '0');
INSERT INTO `t_bed` VALUES ('98', '2020-09-16 21:04:54', 'root', null, null, '3', '306', '3', '0', '0', null, null, '0');
INSERT INTO `t_bed` VALUES ('99', '2020-09-16 21:04:54', 'root', null, null, '3', '306', '4', '0', '0', null, null, '0');
INSERT INTO `t_bed` VALUES ('100', '2020-09-16 21:04:54', 'root', null, null, '3', '307', '1', '0', '0', null, null, '0');
INSERT INTO `t_bed` VALUES ('101', '2020-09-16 21:04:54', 'root', null, null, '3', '307', '2', '0', '0', null, null, '0');
INSERT INTO `t_bed` VALUES ('102', '2020-09-16 21:04:54', 'root', null, null, '3', '307', '3', '0', '0', null, null, '0');
INSERT INTO `t_bed` VALUES ('103', '2020-09-16 21:04:54', 'root', null, null, '3', '307', '4', '0', '0', null, null, '0');
INSERT INTO `t_bed` VALUES ('104', '2020-09-16 21:04:54', 'root', null, null, '3', '308', '1', '0', '0', null, null, '0');
INSERT INTO `t_bed` VALUES ('105', '2020-09-16 21:04:54', 'root', null, null, '3', '308', '2', '0', '0', null, null, '0');
INSERT INTO `t_bed` VALUES ('106', '2020-09-16 21:04:54', 'root', null, null, '3', '308', '3', '0', '0', null, null, '0');
INSERT INTO `t_bed` VALUES ('107', '2020-09-16 21:04:54', 'root', null, null, '3', '308', '4', '0', '0', null, null, '0');
INSERT INTO `t_bed` VALUES ('108', '2020-09-16 21:04:54', 'root', null, null, '3', '309', '1', '0', '0', null, null, '0');
INSERT INTO `t_bed` VALUES ('109', '2020-09-16 21:04:54', 'root', null, null, '3', '309', '2', '0', '0', null, null, '0');
INSERT INTO `t_bed` VALUES ('110', '2020-09-16 21:04:54', 'root', null, null, '3', '309', '3', '0', '0', null, null, '0');
INSERT INTO `t_bed` VALUES ('111', '2020-09-16 21:04:54', 'root', null, null, '3', '309', '4', '0', '0', null, null, '0');
INSERT INTO `t_bed` VALUES ('112', '2020-09-16 21:04:54', 'root', null, null, '4', '401', '1', '0', '0', null, null, '0');
INSERT INTO `t_bed` VALUES ('113', '2020-09-16 21:04:54', 'root', null, null, '4', '401', '2', '0', '0', null, null, '0');
INSERT INTO `t_bed` VALUES ('114', '2020-09-16 21:04:54', 'root', null, null, '4', '401', '3', '0', '0', null, null, '0');
INSERT INTO `t_bed` VALUES ('115', '2020-09-16 21:04:54', 'root', null, null, '4', '401', '4', '0', '0', null, null, '0');
INSERT INTO `t_bed` VALUES ('116', '2020-09-16 21:04:54', 'root', null, null, '4', '402', '1', '0', '0', null, null, '0');
INSERT INTO `t_bed` VALUES ('117', '2020-09-16 21:04:54', 'root', null, null, '4', '402', '2', '0', '0', null, null, '0');
INSERT INTO `t_bed` VALUES ('118', '2020-09-16 21:04:54', 'root', null, null, '4', '402', '3', '0', '0', null, null, '0');
INSERT INTO `t_bed` VALUES ('119', '2020-09-16 21:04:54', 'root', null, null, '4', '402', '4', '0', '0', null, null, '0');
INSERT INTO `t_bed` VALUES ('120', '2020-09-16 21:04:54', 'root', null, null, '4', '403', '1', '0', '0', null, null, '0');
INSERT INTO `t_bed` VALUES ('121', '2020-09-16 21:04:54', 'root', null, null, '4', '403', '2', '0', '0', null, null, '0');
INSERT INTO `t_bed` VALUES ('122', '2020-09-16 21:04:54', 'root', null, null, '4', '403', '3', '0', '0', null, null, '0');
INSERT INTO `t_bed` VALUES ('123', '2020-09-16 21:04:54', 'root', null, null, '4', '403', '4', '0', '0', null, null, '0');
INSERT INTO `t_bed` VALUES ('124', '2020-09-16 21:04:54', 'root', null, null, '4', '404', '1', '0', '0', null, null, '0');
INSERT INTO `t_bed` VALUES ('125', '2020-09-16 21:04:54', 'root', null, null, '4', '404', '2', '0', '0', null, null, '0');
INSERT INTO `t_bed` VALUES ('126', '2020-09-16 21:04:54', 'root', null, null, '4', '404', '3', '0', '0', null, null, '0');
INSERT INTO `t_bed` VALUES ('127', '2020-09-16 21:04:54', 'root', null, null, '4', '404', '4', '0', '0', null, null, '0');
INSERT INTO `t_bed` VALUES ('128', '2020-09-16 21:04:54', 'root', null, null, '4', '405', '1', '0', '0', null, null, '0');
INSERT INTO `t_bed` VALUES ('129', '2020-09-16 21:04:54', 'root', null, null, '4', '405', '2', '0', '0', null, null, '0');
INSERT INTO `t_bed` VALUES ('130', '2020-09-16 21:04:54', 'root', null, null, '4', '405', '3', '0', '0', null, null, '0');
INSERT INTO `t_bed` VALUES ('131', '2020-09-16 21:04:54', 'root', null, null, '4', '405', '4', '0', '0', null, null, '0');
INSERT INTO `t_bed` VALUES ('132', '2020-09-16 21:04:54', 'root', null, null, '4', '406', '1', '0', '0', null, null, '0');
INSERT INTO `t_bed` VALUES ('133', '2020-09-16 21:04:54', 'root', null, null, '4', '406', '2', '0', '0', null, null, '0');
INSERT INTO `t_bed` VALUES ('134', '2020-09-16 21:04:54', 'root', null, null, '4', '406', '3', '0', '0', null, null, '0');
INSERT INTO `t_bed` VALUES ('135', '2020-09-16 21:04:54', 'root', null, null, '4', '406', '4', '0', '0', null, null, '0');
INSERT INTO `t_bed` VALUES ('136', '2020-09-16 21:04:54', 'root', null, null, '4', '407', '1', '0', '0', null, null, '0');
INSERT INTO `t_bed` VALUES ('137', '2020-09-16 21:04:54', 'root', null, null, '4', '407', '2', '0', '0', null, null, '0');
INSERT INTO `t_bed` VALUES ('138', '2020-09-16 21:04:54', 'root', null, null, '4', '407', '3', '0', '0', null, null, '0');
INSERT INTO `t_bed` VALUES ('139', '2020-09-16 21:04:54', 'root', null, null, '4', '407', '4', '0', '0', null, null, '0');
INSERT INTO `t_bed` VALUES ('140', '2020-09-16 21:04:54', 'root', null, null, '4', '408', '1', '0', '0', null, null, '0');
INSERT INTO `t_bed` VALUES ('141', '2020-09-16 21:04:54', 'root', null, null, '4', '408', '2', '0', '0', null, null, '0');
INSERT INTO `t_bed` VALUES ('142', '2020-09-16 21:04:54', 'root', null, null, '4', '408', '3', '0', '0', null, null, '0');
INSERT INTO `t_bed` VALUES ('143', '2020-09-16 21:04:54', 'root', null, null, '4', '408', '4', '0', '0', null, null, '0');
INSERT INTO `t_bed` VALUES ('144', '2020-09-16 21:04:54', 'root', null, null, '4', '409', '1', '0', '0', null, null, '0');
INSERT INTO `t_bed` VALUES ('145', '2020-09-16 21:04:54', 'root', null, null, '4', '409', '2', '0', '0', null, null, '0');
INSERT INTO `t_bed` VALUES ('146', '2020-09-16 21:04:54', 'root', null, null, '4', '409', '3', '0', '0', null, null, '0');
INSERT INTO `t_bed` VALUES ('147', '2020-09-16 21:04:54', 'root', null, null, '4', '409', '4', '0', '0', null, null, '0');

-- ----------------------------
-- Table structure for `t_checkin`
-- ----------------------------
DROP TABLE IF EXISTS `t_checkin`;
CREATE TABLE `t_checkin` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create_by` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `create_time` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `update_by` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `update_time` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `is_deleted` int(11) DEFAULT '0',
  `cus_id` int(11) DEFAULT NULL COMMENT '客户id',
  `bed_id` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '入住床位id',
  `checkin_date` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '入住日期',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of t_checkin
-- ----------------------------
INSERT INTO `t_checkin` VALUES ('1', null, null, null, null, '1', '5', '4', '2020-09-21', null);
INSERT INTO `t_checkin` VALUES ('2', null, null, null, null, '1', '1', '5', '2020-09-21', null);
INSERT INTO `t_checkin` VALUES ('3', null, '2020-09-21 22:45:47', 'root', null, '1', '2', '6', null, null);
INSERT INTO `t_checkin` VALUES ('4', null, '2020-09-21 22:55:24', 'root', null, '1', '3', '7', '2020-09-21 22:55:24', null);
INSERT INTO `t_checkin` VALUES ('5', null, '2020-09-21 23:00:00', 'root', '2020-09-24 19:17:26', '0', '1', '4', '2020-09-26', null);
INSERT INTO `t_checkin` VALUES ('6', null, '2020-09-21 23:00:06', 'root', '2020-09-24 19:17:44', '0', '2', '5', '2020-09-20', null);
INSERT INTO `t_checkin` VALUES ('7', null, '2020-09-21 23:00:56', 'root', null, '1', '1', '8', '2020-09-21 23:00:56', null);
INSERT INTO `t_checkin` VALUES ('8', null, '2020-09-21 23:01:03', 'root', null, '1', '5', '8', '2020-09-21 23:01:03', null);
INSERT INTO `t_checkin` VALUES ('9', null, '2020-09-21 23:14:43', 'root', '2020-09-24 19:17:52', '0', '3', '6', '2020-09-20', null);
INSERT INTO `t_checkin` VALUES ('10', null, '2020-09-21 23:14:52', 'root', '2020-09-21 23:57:18', '0', '5', '12', '2020-09-09', null);
INSERT INTO `t_checkin` VALUES ('11', null, '2020-09-24 19:17:16', 'root', null, '0', '4', '7', '2020-09-24', null);

-- ----------------------------
-- Table structure for `t_checkout`
-- ----------------------------
DROP TABLE IF EXISTS `t_checkout`;
CREATE TABLE `t_checkout` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create_time` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '创建者',
  `update_time` varchar(0) COLLATE utf8_bin DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '更新者',
  `is_deleted` int(1) DEFAULT '0' COMMENT '逻辑删除',
  `cus_id` int(11) DEFAULT NULL COMMENT '客户id',
  `checkout_date` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '退住时间',
  `checkout_type` int(1) DEFAULT NULL COMMENT '退住类型（0回家、1逝世、2换院）',
  `checkout_reason` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '退住原因',
  `status` int(1) DEFAULT '0' COMMENT '0处理中、1已退住',
  `audit_person` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '审核人',
  `audit` int(255) DEFAULT '0' COMMENT '审核情况（0未审核、1审核通过、2审核不通过）',
  `audit_time` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '审核时间',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of t_checkout
-- ----------------------------

-- ----------------------------
-- Table structure for `t_customer`
-- ----------------------------
DROP TABLE IF EXISTS `t_customer`;
CREATE TABLE `t_customer` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create_by` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `create_time` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `update_by` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `update_time` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `is_deleted` int(11) DEFAULT '0',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  `cus_name` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '客户名',
  `cus_sex` int(11) DEFAULT '0' COMMENT '性别（默认0男，1女）',
  `cus_age` int(11) DEFAULT NULL COMMENT '年龄',
  `cus_idcard` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '身份证号',
  `record_id` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '档案号',
  `elder_type` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '老人类型',
  `expiration_date` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '合同到期日期',
  `phone` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '联系电话',
  `psychological_status` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '身心状况',
  `attentin` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '注意',
  `birthday` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '出生年月',
  `height` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '身高',
  `weight` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '体重',
  `marital_status` int(11) DEFAULT '0' COMMENT '婚姻状态（0已婚（默认）、1丧偶/丧夫、2未婚）',
  `blood_type` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '血型',
  `head_img` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '头像存储',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of t_customer
-- ----------------------------
INSERT INTO `t_customer` VALUES ('1', '0', '2020-06-23', '0', '2020-06-23', '0', null, '张三', '0', '60', '445556xxxxxxxxxx', 'xxx001', '患病老人', '2022-12-31', '123456789000', '轻度老年痴呆', '偶尔会有轻度症状发生，需要专人照看', '1960-06-30', '168cm', '58kg', '0', 'o', null);
INSERT INTO `t_customer` VALUES ('2', '0', '2020-06-23', null, '2020-09-24 19:42:21', '0', null, '李四', '0', '62', '423191xxxxxxxxxxxx', 'xxx002', '1', '2022-12-31', '1234567890', '正常', '无', '1958-09-25', '155cm', '45kg', '1', 'a', null);
INSERT INTO `t_customer` VALUES ('3', '0', '2020-07-01', '0', '2020-07-01', '0', null, '王五', '1', '60', '448521xxxxxxxxxx', 'xxx003', '患病老人', '2024-12-31', '1234569870', '腿部三级残疾', '腿脚不便，需要搀扶', '1960-09-25', '148cm', '40kg', '0', 'ab', null);
INSERT INTO `t_customer` VALUES ('4', '0', '2020-07-01', '0', '2020-07-01', '0', null, '马六', '0', '60', '448521xxxxxxxxxx', 'xxx005', '正常老人', '2024-12-31', '1234569870', '正常', '无', '1958-09-25', '148cm', '40kg', '0', 'a', null);
INSERT INTO `t_customer` VALUES ('5', '0', '2020-07-01', '0', '2020-07-01', '0', null, '田七', '0', '62', '448521xxxxxxxxxx', 'xxx005', '正常老人', '2024-12-31', '1234569870', '正常', '无', '1958-09-25', '148cm', '40kg', '0', 'a', null);

-- ----------------------------
-- Table structure for `t_emp`
-- ----------------------------
DROP TABLE IF EXISTS `t_emp`;
CREATE TABLE `t_emp` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create_by` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `create_time` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `update_by` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `update_time` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `is_deleted` int(1) DEFAULT '0',
  `emp_type` int(1) DEFAULT NULL COMMENT '员工类型（0管理人员、1医务人员、2护理人员）',
  `emp_name` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '员工名',
  `sex` int(11) DEFAULT '0' COMMENT '性别（默认0男、1女）',
  `entry_date` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '入职日期',
  `operate_name` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '操作人',
  `duty` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '职务',
  `possional_name` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '职称',
  `brief` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '简介',
  `phone` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '联系号码',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of t_emp
-- ----------------------------
INSERT INTO `t_emp` VALUES ('1', '0', '2020-01-01', '0', '2020-01-01', '0', '0', '詹zz', '0', '2020-01-01', '0', '院长', '东软颐养中心院长', '颐养中心创始人', '123456789', null);
INSERT INTO `t_emp` VALUES ('2', '0', '2020-01-01', '0', '2020-01-01', '0', '1', '张zz', '1', '2020-01-01', '0', '医务人员', '医务组主任', '颐养中心的第一位常驻医生', '123456789', null);
INSERT INTO `t_emp` VALUES ('3', '0', '2020-01-01', '0', '2020-01-01', '0', '2', '刘zz', '0', '2020-01-01', '0', '护理人员', '护理士长', '颐养中心的组织者，也是第一位常驻护理人员', '1236458979', null);
INSERT INTO `t_emp` VALUES ('4', '0', '2020-09-16', '0', '2020-09-16', '0', '0', '张大陆', '0', '2020-09-16', '0', '技术管理人员', '打工崽', '给程序员测试数据用的专业打工崽', '10086', null);

-- ----------------------------
-- Table structure for `t_food`
-- ----------------------------
DROP TABLE IF EXISTS `t_food`;
CREATE TABLE `t_food` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create_time` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '创建者',
  `update_time` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '更新者',
  `is_deleted` int(11) DEFAULT NULL COMMENT '逻辑删除',
  `food_name` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '食物名称',
  `food_type` int(1) DEFAULT NULL COMMENT '食物类型（0-1大小荤、2素菜、3甜点、4水果、5套餐）',
  `food_tag` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '食物标签',
  `food_price` mediumint(9) DEFAULT NULL COMMENT '食物价格',
  `food_pic` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '食物图片',
  `is_steam` int(11) DEFAULT NULL COMMENT '是否清真0否1是',
  `sort` int(1) DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of t_food
-- ----------------------------
INSERT INTO `t_food` VALUES ('1', null, null, null, null, null, '早餐套餐', '5', '少脂、少糖', '22', 'breakfast.jpg', '1', null);
INSERT INTO `t_food` VALUES ('2', null, null, null, null, null, '午餐套餐', '5', '多脂', '40', 'lunch.jpg', '0', null);
INSERT INTO `t_food` VALUES ('3', null, null, null, null, null, '晚餐套餐', '5', '多脂', '51', 'dinner.jpg', '0', null);
INSERT INTO `t_food` VALUES ('4', null, null, null, null, null, '早餐套餐', '5', '少脂、少糖', '22', 'breakfast.jpg', '1', null);
INSERT INTO `t_food` VALUES ('5', null, null, null, null, null, '早餐套餐', '5', '少脂、少糖', '22', 'breakfast.jpg', '1', null);
INSERT INTO `t_food` VALUES ('6', null, null, null, null, null, '午餐套餐', '5', '多脂', '40', 'lunch.jpg', '0', null);
INSERT INTO `t_food` VALUES ('7', null, null, null, null, null, '午餐套餐', '5', '多脂', '40', 'lunch.jpg', '0', null);
INSERT INTO `t_food` VALUES ('8', null, null, null, null, null, '晚餐套餐', '5', '多糖', '51', 'dinner.jpg', '0', null);
INSERT INTO `t_food` VALUES ('9', null, null, null, null, null, '晚餐套餐', '5', '多脂', '55', 'lunch.jpg', '0', null);
INSERT INTO `t_food` VALUES ('22', '2020-09-22 13:07:08', null, null, null, null, 'xx', '2', 'xxx', '10', 'undefined', '0', null);

-- ----------------------------
-- Table structure for `t_food_date`
-- ----------------------------
DROP TABLE IF EXISTS `t_food_date`;
CREATE TABLE `t_food_date` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create_time` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '创建者',
  `update_time` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '更新者',
  `date` int(11) DEFAULT NULL COMMENT '星期0123456',
  `day` int(11) DEFAULT NULL COMMENT '早中晚012',
  `food_id` int(11) DEFAULT NULL COMMENT '食物id',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  `is_deleted` int(1) DEFAULT '0',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of t_food_date
-- ----------------------------
INSERT INTO `t_food_date` VALUES ('1', null, null, null, null, '0', '0', '1', null, '0');
INSERT INTO `t_food_date` VALUES ('2', null, null, null, null, '0', '1', '2', null, '0');
INSERT INTO `t_food_date` VALUES ('3', null, null, null, null, '0', '2', '3', null, '0');
INSERT INTO `t_food_date` VALUES ('4', null, null, null, null, '0', '0', '4', null, '0');
INSERT INTO `t_food_date` VALUES ('5', null, null, null, null, '0', '0', '5', null, '0');
INSERT INTO `t_food_date` VALUES ('6', null, null, null, null, '0', '1', '6', null, '0');
INSERT INTO `t_food_date` VALUES ('7', null, null, null, null, '0', '1', '7', null, '0');
INSERT INTO `t_food_date` VALUES ('8', null, null, null, null, '0', '2', '8', null, '0');
INSERT INTO `t_food_date` VALUES ('9', null, null, null, null, '0', '2', '9', null, '0');
INSERT INTO `t_food_date` VALUES ('12', '2020-09-22 13:07:08', null, null, null, '0', '0', '22', null, '0');

-- ----------------------------
-- Table structure for `t_food_manage`
-- ----------------------------
DROP TABLE IF EXISTS `t_food_manage`;
CREATE TABLE `t_food_manage` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create_time` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '创建者',
  `update_time` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '更新者',
  `food_date_id` int(11) DEFAULT NULL COMMENT '膳食日历id',
  `cus_id` int(11) DEFAULT NULL COMMENT '客户id',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  `is_deleted` int(1) DEFAULT '0',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of t_food_manage
-- ----------------------------
INSERT INTO `t_food_manage` VALUES ('1', null, null, null, null, '1', '1', null, '0');
INSERT INTO `t_food_manage` VALUES ('2', null, null, null, null, '2', '1', null, '0');
INSERT INTO `t_food_manage` VALUES ('3', null, null, null, null, '3', '1', null, '0');
INSERT INTO `t_food_manage` VALUES ('4', null, null, null, null, '4', '1', null, '0');
INSERT INTO `t_food_manage` VALUES ('5', null, null, null, null, '5', '1', null, '0');
INSERT INTO `t_food_manage` VALUES ('6', null, null, null, null, '6', '1', null, '0');
INSERT INTO `t_food_manage` VALUES ('7', null, null, null, null, '7', '1', null, '0');
INSERT INTO `t_food_manage` VALUES ('8', null, null, null, null, '8', '1', null, '0');
INSERT INTO `t_food_manage` VALUES ('9', null, null, null, null, '9', '1', null, '0');
INSERT INTO `t_food_manage` VALUES ('10', '2020-09-22 13:07:08', null, null, null, '12', '1', null, '0');

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
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of t_leave
-- ----------------------------
INSERT INTO `t_leave` VALUES ('1', '2020-09-21', '0', null, null, '1', '健身运动', '2020-09-21 17:30:00', '2020-09-21 19:00:00', '2020-09-21 18:45:00', '刘大哥', '123456789', '0', '1', '2020-09-21 15:00:00', null, '0');
INSERT INTO `t_leave` VALUES ('3', '2020-09-22', '0', null, null, '2', '去医院', '2020-09-21 17:30:00', '2020-09-21 19:00:00', '', '张大叔', '123456798', '0', '2', '2020-09-21 15:00:00', null, '0');
INSERT INTO `t_leave` VALUES ('5', '2020-09-21 21:06:10', 'root', '2020-09-21 21:45:55', 'root', '3', '0.0.', '2020-09-24 00:00:00', '2020-09-26 00:00:00', '', '刘xx', '123564498', '', null, '', null, '1');
INSERT INTO `t_leave` VALUES ('6', '2020-09-21 21:07:51', 'root', '2020-09-21 21:40:06', '', '3', '13', '2020-08-11 00:00:00', '2020-09-23 00:00:00', '', 'xx', '131654', 'root', '2', '2020-09-21 21:40:06', null, '0');
INSERT INTO `t_leave` VALUES ('7', '2020-09-24 19:27:00', 'root', '2020-09-24 19:33:10', 'root', '4', '逛街', '2020-09-24 19:25:37', '2020-09-26 00:00:00', '2020-09-25 00:00:00', 'erhahan', '100010', 'root', '1', '2020-09-24 19:27:14', null, '0');

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
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  `is_deleted` int(1) DEFAULT '0',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of t_nurse_level
-- ----------------------------
INSERT INTO `t_nurse_level` VALUES ('1', '2020-09-15', '0', '2020-09-15', '0', '1', '1', null, '0');
INSERT INTO `t_nurse_level` VALUES ('3', '2020-09-15', '0', '2020-09-15', '0', '1', '2', null, '0');
INSERT INTO `t_nurse_level` VALUES ('4', '2020-09-15', '0', '2020-09-15', '0', '2', '3', null, '1');
INSERT INTO `t_nurse_level` VALUES ('9', '2020-09-17 19:38:44', 'root', '2020-09-17 20:01:06', '', '3', '3', null, '1');
INSERT INTO `t_nurse_level` VALUES ('10', '2020-09-17 20:02:43', 'root', null, null, '1', '3', null, '1');
INSERT INTO `t_nurse_level` VALUES ('11', '2020-09-17 20:04:27', 'root', '2020-09-17 21:19:18', 'root', '1', '3', null, '0');
INSERT INTO `t_nurse_level` VALUES ('12', '2020-09-17 20:04:31', 'root', '2020-09-17 20:13:41', 'root', '1', '2', null, '1');
INSERT INTO `t_nurse_level` VALUES ('13', '2020-09-17 20:51:51', 'root', null, null, '5', '4', null, '1');
INSERT INTO `t_nurse_level` VALUES ('14', '2020-09-22 10:58:28', 'root', null, null, '3', '4', null, '0');
INSERT INTO `t_nurse_level` VALUES ('15', '2020-09-24 19:13:25', 'root', null, null, '2', '4', null, '0');

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
  `price` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '价格',
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
INSERT INTO `t_nurse_project` VALUES ('1', '2020-09-15', '0', '2020-09-15', '0', '理发', '15元/次', '单剪', '0', '1', null, '0');
INSERT INTO `t_nurse_project` VALUES ('2', '2020-09-15', '0 ', '2020-09-15', '0 ', '指甲护理', '10元/次', '修理手脚指甲', '0', '1', null, '0');
INSERT INTO `t_nurse_project` VALUES ('3', '2020-09-15', '0', '2020-09-24 19:13:54', 'root', '身体清洁', '20元/次', '搓澡', '1', '1', null, '0');
INSERT INTO `t_nurse_project` VALUES ('4', '2020-09-17 20:51:13', 'root', '2020-09-24 19:13:49', 'root', '三高检查', '50元/次', '检查老人的高血压、高血糖、高血脂项目', '1', '1', null, '0');

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
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of t_nurse_record
-- ----------------------------
INSERT INTO `t_nurse_record` VALUES ('1', '2020-02-15', '0', '2020-09-17', '0', '1', '1', '2', '2020-10-05', '隔一个月理一次发', '3', null, '0');
INSERT INTO `t_nurse_record` VALUES ('2', '2020-09-18', '0', null, null, '2', '1', '1', '2020-10-05', '需要细剪', '3', null, '0');
INSERT INTO `t_nurse_record` VALUES ('3', '2020-09-21 19:42:01', 'root', null, null, '1', '2', '1', '2020-09-21T16:00:00.000Z', '手脚都需', '3', null, '1');
INSERT INTO `t_nurse_record` VALUES ('4', '2020-09-21 19:44:39', 'root', null, null, '2', '4', '1', '2020-09-22T02:00:00.000Z', '无', '3', null, '1');
INSERT INTO `t_nurse_record` VALUES ('5', '2020-09-21 19:47:11', 'root', '2020-09-21 19:51:13', 'root', '3', '1', '2', '2020-09-22 02:02:00', '333', '3', null, '1');
INSERT INTO `t_nurse_record` VALUES ('6', '2020-09-24 19:14:19', 'root', null, null, '3', '4', '1', '2020-09-24 19:14:14', null, '3', null, '0');
INSERT INTO `t_nurse_record` VALUES ('7', '2020-09-24 19:18:15', 'root', null, null, '4', '3', '1', '2020-09-24 19:18:12', null, '3', null, '0');
INSERT INTO `t_nurse_record` VALUES ('8', '2020-09-24 19:38:47', 'root', null, null, '1', '4', '1', '2020-09-09 00:00:00', null, '3', null, '0');

-- ----------------------------
-- Table structure for `t_service_focus`
-- ----------------------------
DROP TABLE IF EXISTS `t_service_focus`;
CREATE TABLE `t_service_focus` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create_time` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '创建者',
  `update_time` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '更新者',
  `project_id` int(11) DEFAULT NULL COMMENT '护理项目id',
  `cus_id` int(11) DEFAULT NULL COMMENT '用户id',
  `serve_start` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '服务购买日期',
  `serve_end` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '服务到期日期',
  `remark` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  `is_deleted` int(1) DEFAULT '0',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of t_service_focus
-- ----------------------------
INSERT INTO `t_service_focus` VALUES ('1', '2020-09-24 19:36:02', 'root', '2020-09-24 19:39:37', '', '4', '1', '2020-09-23 00:00:00', '2020-10-04 00:00:00', '', null, '0');
INSERT INTO `t_service_focus` VALUES ('2', '2020-09-24 19:39:01', 'root', '2020-09-24 19:39:40', '', '2', '2', '2020-09-11 00:00:00', '2020-10-23 00:00:00', '', null, '0');
INSERT INTO `t_service_focus` VALUES ('3', '2020-09-24 19:59:02', 'root', null, null, '2', '4', '2020-09-01 00:00:00', '2020-10-31 00:00:00', null, null, '0');

-- ----------------------------
-- Table structure for `t_service_obj`
-- ----------------------------
DROP TABLE IF EXISTS `t_service_obj`;
CREATE TABLE `t_service_obj` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create_time` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '创建者',
  `update_time` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '更新者',
  `emp_id` int(11) DEFAULT NULL COMMENT '管家id',
  `cus_id` int(11) DEFAULT NULL COMMENT '用户id',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  `is_deleted` int(1) DEFAULT '0',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of t_service_obj
-- ----------------------------

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `create_time` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(11) COLLATE utf8_bin DEFAULT '0' COMMENT '创建者',
  `update_time` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '最新一次修改时间',
  `update_by` varchar(11) COLLATE utf8_bin DEFAULT '0' COMMENT '修改者',
  `is_deleted` int(11) DEFAULT '0' COMMENT '0未删除（默认），1已删除',
  `username` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `password` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `sex` int(1) DEFAULT NULL COMMENT '0男，1女',
  `email` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `phone` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `emp_id` int(11) DEFAULT NULL COMMENT '员工id',
  `user_icon` varchar(255) COLLATE utf8_bin DEFAULT 'tx.png',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', null, '0', null, '0', '0', 'root', '$2a$10$WK3SjM1S0j5rSh1.3u2iruLWVxoUiX1Ve76cBAMO9S2rre4/mqJzS', null, null, null, null, 'tx.png');

-- ----------------------------
-- Table structure for `user_role`
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `role_id` int(11) DEFAULT NULL COMMENT '权限id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES ('1', '1', '1');
