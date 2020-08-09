/*
Navicat MySQL Data Transfer

Source Server         : Tosh
Source Server Version : 50711
Source Host           : localhost:3306
Source Database       : kaleido

Target Server Type    : MYSQL
Target Server Version : 50711
File Encoding         : 65001

Date: 2020-08-09 00:37:53
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_banner
-- ----------------------------
DROP TABLE IF EXISTS `t_banner`;
CREATE TABLE `t_banner` (
  `id` varchar(64) NOT NULL COMMENT '主键ID',
  `banner_name` varchar(64) DEFAULT NULL COMMENT '轮播图名称',
  `image_size` varchar(64) DEFAULT NULL COMMENT '图片尺寸',
  `picture_url` varchar(256) DEFAULT NULL COMMENT '图片URL',
  `hyper_link` varchar(256) DEFAULT NULL COMMENT '超链接',
  `sort_no` varchar(32) DEFAULT NULL COMMENT '顺序',
  `creater` varchar(64) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updater` varchar(64) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `publisher` varchar(64) DEFAULT NULL COMMENT '发布人',
  `publish_time` datetime DEFAULT NULL COMMENT '发布时间',
  `status` varchar(2) DEFAULT NULL COMMENT '状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='横幅表';

-- ----------------------------
-- Records of t_banner
-- ----------------------------
INSERT INTO `t_banner` VALUES ('1289495567090753538', 'Banner', '500*333', 'http://47.106.242.120:8080/work/3130de87311d4a72aeb53c74eab7f175.jpg', 'https://baidu.com', '1', 'sysadmin', '2020-08-01 17:37:55', 'sysadmin', '2020-08-01 17:37:55', 'sysadmin', '2020-08-01 17:37:55', '1');
