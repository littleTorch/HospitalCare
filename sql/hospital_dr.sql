/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50727
Source Host           : localhost:3306
Source Database       : hospital_dr

Target Server Type    : MYSQL
Target Server Version : 50727
File Encoding         : 65001

Date: 2020-09-15 15:06:02
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
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of permission
-- ----------------------------

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
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of role
-- ----------------------------

-- ----------------------------
-- Table structure for `role_permission`
-- ----------------------------
DROP TABLE IF EXISTS `role_permission`;
CREATE TABLE `role_permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) DEFAULT NULL,
  `permission_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of role_permission
-- ----------------------------

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
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of t_bed
-- ----------------------------

-- ----------------------------
-- Table structure for `t_checkin`
-- ----------------------------
DROP TABLE IF EXISTS `t_checkin`;
CREATE TABLE `t_checkin` (
  `id` int(11) NOT NULL,
  `create_by` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `create_time` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `update_by` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `update_time` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `is_deleted` int(11) DEFAULT '0',
  `cus_id` int(11) DEFAULT NULL COMMENT '客户id',
  `bed_id` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '入住床位id',
  `checkin_date` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '入住日期',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of t_checkin
-- ----------------------------

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
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

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
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of t_customer
-- ----------------------------
INSERT INTO `t_customer` VALUES ('1', '0', '2020-06-23', '0', '2020-06-23', '0', null, '张三', '0', '60', '445556xxxxxxxxxx', 'xxx001', '患病老人', '2022-12-31', '123456789000', '轻度老年痴呆', '偶尔会有轻度症状发生，需要专人照看', '1960-06-30', '168cm', '58kg', '0', 'o', null);
INSERT INTO `t_customer` VALUES ('2', '0', '2020-06-23', '0', '2020-06-23', '0', null, '李四', '0', '62', '423191xxxxxxxxxxxx', 'xxx002', '正常老人', '2022-12-31', '1234567890', '正常', '无', '1958-09-25', '155cm', '45kg', '1', 'a', null);
INSERT INTO `t_customer` VALUES ('3', '0', '2020-07-01', '0', '2020-07-01', '0', null, '王五', '1', '60', '448521xxxxxxxxxx', 'xxx003', '患病老人', '2024-12-31', '1234569870', '腿部三级残疾', '腿脚不便，需要搀扶', '1960-09-25', '148cm', '40kg', '0', 'ab', null);

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
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of t_emp
-- ----------------------------
INSERT INTO `t_emp` VALUES ('1', '0', '2020-01-01', '0', '2020-01-01', '0', '0', '詹zz', '0', '2020-01-01', '0', '院长', '东软颐养中心院长', '颐养中心创始人', '123456789', null);
INSERT INTO `t_emp` VALUES ('2', '0', '2020-01-01', '0', '2020-01-01', '0', '1', '张zz', '1', '2020-01-01', '0', '医务人员', '医务组主任', '颐养中心的第一位常驻医生', '123456789', null);
INSERT INTO `t_emp` VALUES ('3', '0', '2020-01-01', '0', '2020-01-01', '0', '2', '刘zz', '0', '2020-01-01', '0', '护理人员', '护理士长', '颐养中心的组织者，也是第一位常驻护理人员', '1236458979', null);

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
  `date` int(11) DEFAULT NULL COMMENT '星期1234567',
  `day` int(11) DEFAULT NULL COMMENT '早中晚012',
  `food_name` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '食品名',
  `food_tag` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '食品标签',
  `food_price` mediumint(9) DEFAULT NULL COMMENT '食品价格',
  `food_pic` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '食品图片',
  `is_steam` int(1) DEFAULT NULL COMMENT '是否清真0否1是',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  `is_deleted` int(1) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of t_food_date
-- ----------------------------

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
  `food_id` int(11) DEFAULT NULL COMMENT '膳食日历id',
  `cus_id` int(11) DEFAULT NULL COMMENT '客户id',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  `is_deleted` int(1) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of t_food_manage
-- ----------------------------

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
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of t_leave
-- ----------------------------

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
  `project_name` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '护理项目名称',
  `status` int(1) DEFAULT NULL COMMENT '状态',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  `is_deleted` int(1) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of t_nurse_level
-- ----------------------------

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
  `level` int(11) DEFAULT NULL COMMENT '护理等级',
  `project_name` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '护理项目名称',
  `price` mediumint(9) DEFAULT NULL COMMENT '价格',
  `details` varchar(9) COLLATE utf8_bin DEFAULT NULL COMMENT '描述',
  `add_service` int(1) DEFAULT NULL COMMENT '是否增值服务0否1是',
  `status` int(1) DEFAULT NULL COMMENT '状态1启用2停用',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  `is_deleted` int(1) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of t_nurse_project
-- ----------------------------

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
  `project_time` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '开始护理时间',
  `num` int(11) DEFAULT NULL COMMENT '护理次数',
  `remark` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `emp_id` int(11) DEFAULT NULL COMMENT '护理人员id',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  `is_deleted` int(1) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of t_nurse_record
-- ----------------------------

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
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of t_service_focus
-- ----------------------------

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
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

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
  `password` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `sex` int(1) DEFAULT NULL COMMENT '0男，1女',
  `email` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `phone` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `user_code` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of user
-- ----------------------------

-- ----------------------------
-- Table structure for `user_role`
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `role_id` int(11) DEFAULT NULL COMMENT '权限id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of user_role
-- ----------------------------
