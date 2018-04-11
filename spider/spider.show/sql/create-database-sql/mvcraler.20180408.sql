/*
SQLyog Ultimate v12.08 (64 bit)
MySQL - 5.7.19 : Database - mvcrawler
*********************************************************************
*/


/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
/*Table structure for table `t_mv_info` */

DROP TABLE IF EXISTS `t_mv_info`;

CREATE TABLE `t_mv_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `created_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '入库时间',
  `updated_time` datetime DEFAULT NULL COMMENT '最后修改时间',
  `enable_flag` tinyint(1) NOT NULL DEFAULT '1' COMMENT '可用标识，0-不可用，1-可用',
  `mv_id` varchar(32) NOT NULL COMMENT '影片id',
  `name` varchar(64) NOT NULL COMMENT '影片名称',
  `brief` varchar(512) DEFAULT NULL COMMENT '影片简介',
  `show_date` varchar(10) DEFAULT NULL COMMENT '上映日期',
  `details` varchar(1024) DEFAULT NULL COMMENT '影片详情',
  `player` varchar(512) DEFAULT NULL COMMENT '演员',
  `director` varchar(128) DEFAULT NULL COMMENT '导演',
  `poster` varchar(256) DEFAULT NULL COMMENT '海报地址',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_mvid` (`mv_id`),
  UNIQUE KEY `uk_name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=5649 DEFAULT CHARSET=utf8 COMMENT='影片基本信息表';

/*Table structure for table `t_mv_source` */

DROP TABLE IF EXISTS `t_mv_source`;

CREATE TABLE `t_mv_source` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `created_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '入库时间',
  `updated_time` datetime DEFAULT NULL COMMENT '最后修改时间',
  `enable_flag` tinyint(1) NOT NULL DEFAULT '1' COMMENT '可用标识，0-不可用，1-可用',
  `mv_id` varchar(32) NOT NULL COMMENT '影片id',
  `website_id` varchar(32) DEFAULT NULL COMMENT '网站id',
  `source_id` varchar(64) DEFAULT NULL COMMENT '影片源id',
  `source_url` varchar(256) NOT NULL COMMENT '影片播放地址',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4965 DEFAULT CHARSET=utf8 COMMENT='影片播放源信息表';

/*Table structure for table `t_task_config` */

DROP TABLE IF EXISTS `t_task_config`;

CREATE TABLE `t_task_config` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `created_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '入库时间',
  `updated_time` datetime DEFAULT NULL COMMENT '最后修改时间',
  `enable_flag` tinyint(1) NOT NULL DEFAULT '1' COMMENT '可用标识，0-不可用，1-可用',
  `website_id` varchar(32) NOT NULL COMMENT '网站id',
  `task_id` varchar(32) NOT NULL COMMENT '任务id',
  `task_name` varchar(64) NOT NULL COMMENT '任务名称',
  `description` varchar(128) DEFAULT NULL COMMENT '描述',
  `target` varchar(256) DEFAULT NULL COMMENT '目标页面地址',
  `regex` varchar(256) DEFAULT NULL COMMENT '目标地址正则',
  `mode` tinyint(1) NOT NULL DEFAULT '1' COMMENT '爬取模式，1-详情页，2-列表页',
  `target_area` varchar(256) DEFAULT NULL COMMENT '当按列表页查询的时候，单个影片信息区域',
  `min_pagenum` int(11) NOT NULL DEFAULT '1' COMMENT '最小页码',
  `max_pagenum` int(11) NOT NULL DEFAULT '1' COMMENT '最大页码',
  `page_size` int(11) DEFAULT NULL COMMENT '每页内容数',
  `run_status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '运行状态，1-待启动，2-已启动，3-正在运行，4-正在停止，5-已停止',
  `specials` varchar(512) DEFAULT NULL COMMENT '特例地址，多个用逗号分隔',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_taskid` (`task_id`),
  UNIQUE KEY `uk_taskname` (`task_name`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8 COMMENT='爬虫任务配置表';

/*Table structure for table `t_task_rule` */

DROP TABLE IF EXISTS `t_task_rule`;

CREATE TABLE `t_task_rule` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `created_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '入库时间',
  `updated_time` datetime DEFAULT NULL COMMENT '最后修改时间',
  `enable_flag` tinyint(1) NOT NULL DEFAULT '1' COMMENT '可用标识，0-不可用，1-可用',
  `task_id` varchar(32) NOT NULL COMMENT '任务id',
  `field_name` varchar(32) NOT NULL COMMENT '爬取的字段名',
  `description` varchar(128) NOT NULL COMMENT '描述',
  `rule` varchar(256) NOT NULL COMMENT '爬取规则',
  `type` tinyint(1) NOT NULL DEFAULT '1' COMMENT '数据类型，1-单文本，2-List文本',
  `item_rule` varchar(256) DEFAULT NULL COMMENT '对于List文本类型，单个值的规则',
  `allow_blank` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否允许为空，0-不允许，1-允许',
  `condition` tinyint(1) DEFAULT NULL COMMENT '匹配条件，1-包含于，2-不包含于，3-大于，4-小于，5-相似',
  `match_value` varchar(128) DEFAULT NULL COMMENT '匹配的值',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=59 DEFAULT CHARSET=utf8 COMMENT='任务抓取规则表';

/*Table structure for table `t_website_info` */

DROP TABLE IF EXISTS `t_website_info`;

CREATE TABLE `t_website_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `created_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '入库时间',
  `updated_time` datetime DEFAULT NULL COMMENT '最后修改时间',
  `enable_flag` tinyint(1) NOT NULL DEFAULT '1' COMMENT '可用标识，0-不可用，1-可用',
  `website_id` varchar(32) NOT NULL COMMENT '网站id',
  `name` varchar(64) NOT NULL COMMENT '网站名称',
  `description` varchar(128) DEFAULT NULL COMMENT '描述',
  `address` varchar(256) NOT NULL COMMENT '网站地址',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_websiteid` (`website_id`),
  UNIQUE KEY `uk_name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8 COMMENT='网站信息表';

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
