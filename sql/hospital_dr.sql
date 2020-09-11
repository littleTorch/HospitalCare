/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 80018
 Source Host           : localhost:3306
 Source Schema         : hospital_dr

 Target Server Type    : MySQL
 Target Server Version : 80018
 File Encoding         : 65001

 Date: 11/09/2020 20:31:58
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for bed
-- ----------------------------
DROP TABLE IF EXISTS `bed`;
CREATE TABLE `bed`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '床位id',
  `create_time` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '创建者',
  `update_time` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '更新者',
  `floor` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '所属楼房',
  `room_no` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '房间号码',
  `bed_no` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '床位号',
  `position_type` int(11) NULL DEFAULT 0 COMMENT '类型（0普通（默认）、1特殊）',
  `bed_status` int(11) NULL DEFAULT 0 COMMENT '床位状态（0空（默认），1使用中）',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '备注',
  `sort` int(11) NULL DEFAULT NULL COMMENT '排序',
  `is_deleted` int(1) NULL DEFAULT NULL COMMENT '逻辑删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of bed
-- ----------------------------

-- ----------------------------
-- Table structure for checkin
-- ----------------------------
DROP TABLE IF EXISTS `checkin`;
CREATE TABLE `checkin`  (
  `id` int(11) NOT NULL,
  `create_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `create_time` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `update_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `update_time` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `is_deleted` int(11) NULL DEFAULT NULL,
  `cus_id` int(11) NULL DEFAULT NULL COMMENT '客户id',
  `floor` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '所属楼房',
  `room` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '入住房间号',
  `bed` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '入住床位号',
  `checkin_date` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '入住日期',
  `sort` int(11) NULL DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of checkin
-- ----------------------------

-- ----------------------------
-- Table structure for customer
-- ----------------------------
DROP TABLE IF EXISTS `customer`;
CREATE TABLE `customer`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `create_time` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `update_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `update_time` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `is_deleted` int(11) NULL DEFAULT NULL,
  `sort` int(11) NULL DEFAULT NULL COMMENT '排序',
  `cus_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '客户名',
  `cus_sex` int(11) NULL DEFAULT 0 COMMENT '性别（默认0男，1女）',
  `cus_age` int(11) NULL DEFAULT NULL COMMENT '年龄',
  `cus_idcard` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '身份证号',
  `record_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '档案号',
  `elder_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '老人类型',
  `expiration_date` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '合同到期日期',
  `phone` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '联系电话',
  `psychological_status` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '身心状况',
  `attentin` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '注意',
  `birthday` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '出生年月',
  `height` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '身高',
  `weight` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '体重',
  `marital_status` int(11) NULL DEFAULT 0 COMMENT '婚姻状态（0已婚（默认）、1丧偶/丧夫、2未婚）',
  `blood_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '血型',
  `head_img` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '头像存储',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of customer
-- ----------------------------
INSERT INTO `customer` VALUES (1, '0', '2020-06-23', '0', '2020-06-23', 0, NULL, '张三', 0, 60, '445556xxxxxxxxxx', 'xxx001', '患病老人', '2022-12-31', '123456789000', '轻度老年痴呆', '偶尔会有轻度症状发生，需要专人照看', '1960-06-30', '168cm', '58kg', 0, 'o', NULL);
INSERT INTO `customer` VALUES (2, '0', '2020-06-23', '0', '2020-06-23', 0, NULL, '李四', 0, 62, '423191xxxxxxxxxxxx', 'xxx002', '正常老人', '2022-12-31', '1234567890', '正常', '无', '1958-09-25', '155cm', '45kg', 1, 'a', NULL);
INSERT INTO `customer` VALUES (3, '0', '2020-07-01', '0', '2020-07-01', 0, NULL, '王五', 1, 60, '448521xxxxxxxxxx', 'xxx003', '患病老人', '2024-12-31', '1234569870', '腿部三级残疾', '腿脚不便，需要搀扶', '1960-09-25', '148cm', '40kg', 0, 'ab', NULL);

-- ----------------------------
-- Table structure for emp
-- ----------------------------
DROP TABLE IF EXISTS `emp`;
CREATE TABLE `emp`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `create_time` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `update_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `update_time` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `is_deleted` int(11) NULL DEFAULT NULL,
  `emp_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '员工类型',
  `emp_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '员工名',
  `sex` int(11) NULL DEFAULT 0 COMMENT '性别（默认0男、1女）',
  `entry_date` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '入职日期',
  `operate_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '操作人',
  `duty` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '职务',
  `possional_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '职称',
  `brief` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '简介',
  `phone` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '联系号码',
  `sort` int(11) NULL DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of emp
-- ----------------------------
INSERT INTO `emp` VALUES (1, '0', '2020-01-01', '0', '2020-01-01', 0, '管理人员', '詹zz', 0, '2020-01-01', '0', '院长', '东软颐养中心院长', '颐养中心创始人', '123456789', NULL);
INSERT INTO `emp` VALUES (2, '0', '2020-01-01', '0', '2020-01-01', 0, '专业技术人员', '张zz', 1, '2020-01-01', '0', '医务人员', '医务组主任', '颐养中心的第一位常驻医生', '123456789', NULL);
INSERT INTO `emp` VALUES (3, '0', '2020-01-01', '0', '2020-01-01', 0, '服务人员', '刘zz', 0, '2020-01-01', '0', '护理人员', '护理士长', '颐养中心的组织者，也是第一位常驻护理人员', '1236458979', NULL);

-- ----------------------------
-- Table structure for exits
-- ----------------------------
DROP TABLE IF EXISTS `exits`;
CREATE TABLE `exits`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create_time` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '创建者',
  `update_time` varchar(0) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '更新者',
  `cus_id` int(11) NULL DEFAULT NULL COMMENT '客户id',
  `exit_date` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '退住时间',
  `exit_type` int(1) NULL DEFAULT NULL COMMENT '退住类型（0回家、1逝世、2换院）',
  `exit_reason` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '退住原因',
  `status` int(1) NULL DEFAULT 0 COMMENT '0处理中、1已退住',
  `audit_person` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '审核人',
  `audit` int(255) NULL DEFAULT 0 COMMENT '审核情况（0未审核、1审核通过、2审核不通过）',
  `audit_time` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '审核时间',
  `sort` int(11) NULL DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of exits
-- ----------------------------

-- ----------------------------
-- Table structure for food_date
-- ----------------------------
DROP TABLE IF EXISTS `food_date`;
CREATE TABLE `food_date`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create_time` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '创建者',
  `update_time` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '更新者',
  `date` int(11) NULL DEFAULT NULL COMMENT '星期1234567',
  `day` int(11) NULL DEFAULT NULL COMMENT '早中晚012',
  `food_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '食品名',
  `food_tag` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '食品标签',
  `food_price` mediumint(9) NULL DEFAULT NULL COMMENT '食品价格',
  `food_pic` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '食品图片',
  `is_steam` int(1) NULL DEFAULT NULL COMMENT '是否清真0否1是',
  `sort` int(11) NULL DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of food_date
-- ----------------------------

-- ----------------------------
-- Table structure for food_manage
-- ----------------------------
DROP TABLE IF EXISTS `food_manage`;
CREATE TABLE `food_manage`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create_time` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '创建者',
  `update_time` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '更新者',
  `food_id` int(11) NULL DEFAULT NULL COMMENT '膳食日历id',
  `cus_id` int(11) NULL DEFAULT NULL COMMENT '客户id',
  `sort` int(11) NULL DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of food_manage
-- ----------------------------

-- ----------------------------
-- Table structure for nurse_level
-- ----------------------------
DROP TABLE IF EXISTS `nurse_level`;
CREATE TABLE `nurse_level`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create_time` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '创建者',
  `update_time` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '更新者',
  `level` int(11) NULL DEFAULT NULL COMMENT '护理等级',
  `project_id` int(11) NULL DEFAULT NULL COMMENT '项目编号',
  `project_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '护理项目名称',
  `status` int(1) NULL DEFAULT NULL COMMENT '状态',
  `sort` int(11) NULL DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of nurse_level
-- ----------------------------

-- ----------------------------
-- Table structure for nurse_project
-- ----------------------------
DROP TABLE IF EXISTS `nurse_project`;
CREATE TABLE `nurse_project`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `create_time` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '创建者',
  `update_time` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '更新者',
  `level` int(11) NULL DEFAULT NULL COMMENT '护理等级',
  `project_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '护理项目名称',
  `price` mediumint(9) NULL DEFAULT NULL COMMENT '价格',
  `details` varchar(9) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '描述',
  `add_service` int(1) NULL DEFAULT NULL COMMENT '是否增值服务0否1是',
  `status` int(1) NULL DEFAULT NULL COMMENT '状态1启用2停用',
  `sort` int(11) NULL DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of nurse_project
-- ----------------------------

-- ----------------------------
-- Table structure for nurse_record
-- ----------------------------
DROP TABLE IF EXISTS `nurse_record`;
CREATE TABLE `nurse_record`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create_time` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '创建者',
  `update_time` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '更新者',
  `cus_id` int(11) NULL DEFAULT NULL COMMENT '客户id',
  `project_id` int(11) NULL DEFAULT NULL COMMENT '护理项目id',
  `project_time` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '开始护理时间',
  `num` int(11) NULL DEFAULT NULL COMMENT '护理次数',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '备注',
  `emp_id` int(11) NULL DEFAULT NULL COMMENT '护理人员id',
  `sort` int(11) NULL DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of nurse_record
-- ----------------------------

-- ----------------------------
-- Table structure for out
-- ----------------------------
DROP TABLE IF EXISTS `out`;
CREATE TABLE `out`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create_time` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '创建者',
  `update_time` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '更新者',
  `cus_id` int(11) NULL DEFAULT NULL COMMENT '客户id',
  `out_reason` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '外出事由',
  `out_time` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '外出时间',
  `expect_time` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '预计回院时间',
  `actual_time` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '实际回院时间',
  `accompany` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '陪同人',
  `accompany_phone` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '陪同人联系电话',
  `audit_person` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '审批人',
  `audit` int(1) NULL DEFAULT NULL COMMENT '审核状态',
  `audit_time` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '审核时间',
  `sort` int(11) NULL DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of out
-- ----------------------------

-- ----------------------------
-- Table structure for permission
-- ----------------------------
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission`  (
  `id` mediumint(11) NOT NULL AUTO_INCREMENT,
  `parent_id` mediumint(11) NULL DEFAULT NULL COMMENT '父权限id',
  `parent_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '父权限名',
  `label` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '权限名称',
  `code` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '授权标识符',
  `path` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '路由地址',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '路径名',
  `url` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '授权路径',
  `order_num` int(11) NULL DEFAULT NULL COMMENT '序号',
  `type` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '类型（0目录，1菜单，2按钮）',
  `icon` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '图标',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '备注',
  `create_time` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '修改时间',
  `is_home` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 20 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of permission
-- ----------------------------
INSERT INTO `permission` VALUES (1, 0, NULL, '床位管理', NULL, '/bedManage', 'bedManage', '/BedManage/BedManage', 0, '1', 'el-icon-date', NULL, NULL, NULL, NULL);
INSERT INTO `permission` VALUES (2, 0, NULL, '护理相关管理', NULL, '/nurseManage', NULL, NULL, 1, '0', 'el-icon-edit-outline', NULL, NULL, NULL, NULL);
INSERT INTO `permission` VALUES (3, 2, NULL, '护理级别', NULL, '/nurseLevel', 'nurseLevel', '/nurseManage/nurseLevel/NurseLevel', 0, '1', 'el-icon-date', NULL, NULL, NULL, NULL);
INSERT INTO `permission` VALUES (4, 2, NULL, '护理内容', NULL, '/nurseProject', 'nurseProject', '/nurseManage/nurseProject/NurseProject', 1, '1', 'el-icon-document-copy', NULL, NULL, NULL, NULL);
INSERT INTO `permission` VALUES (5, 2, NULL, '护理记录', NULL, '/nurseRecord', 'nurseRecord', '/nurseManage/nurseRecord/NurseRecord', 2, '1', 'el-icon-s-goods', NULL, NULL, NULL, NULL);
INSERT INTO `permission` VALUES (6, 0, NULL, '膳食管理', NULL, '/foodManage', NULL, NULL, 2, '0', 'el-icon-menu', NULL, NULL, NULL, NULL);
INSERT INTO `permission` VALUES (7, 6, NULL, '膳食日历', NULL, '/foodDate', 'foodDate', '/foodManage/foodDate/FoodDate', 0, '1', 'el-icon-school', NULL, NULL, NULL, NULL);
INSERT INTO `permission` VALUES (8, 6, NULL, '膳食管理', NULL, '/foodManage', 'foodManage', '/foodManage/foodManage/FoodManage', 1, '1', 'el-icon-pie-chart', NULL, NULL, NULL, NULL);
INSERT INTO `permission` VALUES (9, 0, NULL, '住宿与外出', NULL, '/checkinOut', NULL, NULL, 3, '0', 'el-icon-receiving', NULL, NULL, NULL, NULL);
INSERT INTO `permission` VALUES (10, 9, NULL, '入住登记', NULL, '/checkin', 'checkin', '/checkinOut/checkin/Checkin', 0, '1', 'el-icon-files', NULL, NULL, NULL, NULL);
INSERT INTO `permission` VALUES (11, 9, NULL, '退住登记', NULL, '/exits', 'exits', '/checkinOut/exits/Exits', 1, '1', 'el-icon-s-operation', NULL, NULL, NULL, NULL);
INSERT INTO `permission` VALUES (12, 9, NULL, '外出登记', NULL, '/out', 'out', '/checkinOut/out/Out', 2, '1', 'el-icon-s-operation', NULL, NULL, NULL, NULL);
INSERT INTO `permission` VALUES (13, 0, NULL, '服务与用户', NULL, '/serviceUser', NULL, NULL, 4, '0', 'el-icon-receiving', NULL, NULL, NULL, NULL);
INSERT INTO `permission` VALUES (14, 13, NULL, '设置服务对象', NULL, '/serviceObj', 'serviceObj', '/serviceUser/serviceObj/ServiceObj', 0, '1', 'el-icon-files', NULL, NULL, NULL, NULL);
INSERT INTO `permission` VALUES (15, 13, NULL, '服务关注', NULL, '/serviceSer', 'serviceSer', '/serviceUser/serviceSer/ServiceSer', 1, '1', 'el-icon-s-operation', NULL, NULL, NULL, NULL);
INSERT INTO `permission` VALUES (16, 13, NULL, '用户管理', NULL, '/user', 'user', '/serviceUser/user/User', 2, '1', 'el-icon-s-operation', NULL, NULL, NULL, NULL);
INSERT INTO `permission` VALUES (17, 0, NULL, '系统管理', NULL, '/system', NULL, NULL, 5, '0', 'el-icon-receiving', NULL, NULL, NULL, NULL);
INSERT INTO `permission` VALUES (18, 17, NULL, '角色管理', NULL, '/role', 'role', '/system/role/Role', 0, '1', 'el-icon-s-operation', NULL, NULL, NULL, NULL);
INSERT INTO `permission` VALUES (19, 17, NULL, '权限管理', NULL, '/permission', 'permission', '/system/permission/Permission', 1, '1', 'el-icon-s-operation', NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色名',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '备注',
  `create_time` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '创建时间',
  `updatae_time` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role
-- ----------------------------

-- ----------------------------
-- Table structure for role_permission
-- ----------------------------
DROP TABLE IF EXISTS `role_permission`;
CREATE TABLE `role_permission`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) NULL DEFAULT NULL,
  `permission_id` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role_permission
-- ----------------------------

-- ----------------------------
-- Table structure for service_obj
-- ----------------------------
DROP TABLE IF EXISTS `service_obj`;
CREATE TABLE `service_obj`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create_time` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '创建者',
  `update_time` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '更新者',
  `emp_id` int(11) NULL DEFAULT NULL COMMENT '管家id',
  `cus_id` int(11) NULL DEFAULT NULL COMMENT '用户id',
  `sort` int(11) NULL DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of service_obj
-- ----------------------------

-- ----------------------------
-- Table structure for service_ser
-- ----------------------------
DROP TABLE IF EXISTS `service_ser`;
CREATE TABLE `service_ser`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create_time` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '创建者',
  `update_time` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '更新者',
  `project_id` int(11) NULL DEFAULT NULL COMMENT '护理项目id',
  `cus_id` int(11) NULL DEFAULT NULL COMMENT '用户id',
  `serve_start` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '服务购买日期',
  `serve_end` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '服务到期日期',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '备注',
  `sort` int(11) NULL DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of service_ser
-- ----------------------------

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `create_time` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(11) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '0' COMMENT '创建者',
  `update_time` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '最新一次修改时间',
  `update_by` varchar(11) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '0' COMMENT '修改者',
  `is_deleted` int(11) NULL DEFAULT 0 COMMENT '0未删除（默认），1已删除',
  `username` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `password` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `sex` int(1) NULL DEFAULT NULL COMMENT '0男，1女',
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `phone` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `user_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NULL DEFAULT NULL COMMENT '用户id',
  `role_id` int(11) NULL DEFAULT NULL COMMENT '权限id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_role
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
