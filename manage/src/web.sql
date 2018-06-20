/*
SQLyog 企业版 - MySQL GUI v8.14 
MySQL - 5.0.37-community-nt : Database - web
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`web` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `web`;

/*Table structure for table `classes` */

DROP TABLE IF EXISTS `classes`;

CREATE TABLE `classes` (
  `id` int(11) unsigned NOT NULL auto_increment COMMENT '班级Id',
  `name` varchar(20) NOT NULL COMMENT '名称',
  `grade` int(11) default NULL COMMENT '年级',
  `major` varchar(20) default NULL COMMENT '所属专业',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `classes` */

insert  into `classes`(`id`,`name`,`grade`,`major`) values (1,'计科161',16,'计算机科学与技术'),(2,'计科162',16,'计算机科学与技术'),(3,'计科163',16,'计算机科学与技术');

/*Table structure for table `course` */

DROP TABLE IF EXISTS `course`;

CREATE TABLE `course` (
  `id` varchar(10) NOT NULL COMMENT '课程id',
  `name` varchar(30) default NULL COMMENT '课程名',
  `credithour` float default NULL COMMENT '学分',
  `classhour` int(11) default NULL COMMENT '讲授学时',
  `practicehour` int(11) default NULL COMMENT '实验学时',
  `remark` text COMMENT '备注',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `course` */

insert  into `course`(`id`,`name`,`credithour`,`classhour`,`practicehour`,`remark`) values ('42001','java',4,48,48,''),('42002','c/c++',4,48,48,''),('42003','计算机网络',3,24,24,''),('42004','操作系统',3,24,24,''),('42204','高等数学A',4,48,48,'');

/*Table structure for table `log` */

DROP TABLE IF EXISTS `log`;

CREATE TABLE `log` (
  `id` int(11) NOT NULL auto_increment,
  `stid` varchar(8) default NULL,
  `class` varchar(255) default NULL,
  `method` varchar(50) default NULL,
  `createtime` varchar(50) default NULL,
  `loglevel` varchar(50) default NULL,
  `msg` text,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `log` */

/*Table structure for table `score` */

DROP TABLE IF EXISTS `score`;

CREATE TABLE `score` (
  `id` bigint(20) NOT NULL COMMENT '序号',
  `studentId` varchar(8) NOT NULL COMMENT '学生学号',
  `courseId` varchar(10) NOT NULL COMMENT '课程编号',
  `semester` varchar(15) default NULL COMMENT '开课学期',
  `score1` float default NULL COMMENT '一考成绩',
  `score2` float default NULL COMMENT '二考成绩',
  `score3` float default NULL COMMENT '三考成绩',
  PRIMARY KEY  (`studentId`,`courseId`),
  KEY `FK_score2` (`studentId`),
  KEY `FK_score1` (`courseId`),
  KEY `FK_score3` (`studentId`),
  KEY `FK_score4` (`courseId`),
  CONSTRAINT `FK_score3` FOREIGN KEY (`studentId`) REFERENCES `student` (`id`),
  CONSTRAINT `FK_score4` FOREIGN KEY (`courseId`) REFERENCES `course` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `score` */

insert  into `score`(`id`,`studentId`,`courseId`,`semester`,`score1`,`score2`,`score3`) values (1642200142001,'16422001','42001','1',90,NULL,NULL),(1642200142002,'16422001','42002','1',59,90,NULL),(1642200142003,'16422001','42003','1',80,NULL,NULL),(1642200142004,'16422001','42004','1',90,NULL,NULL),(1642200242001,'16422002','42001','1',59,59,60),(1642200242002,'16422002','42002','1',90,NULL,NULL),(1642200242004,'16422002','42004','1',100,NULL,NULL),(1642200242204,'16422002','42204','1',100,NULL,NULL);

/*Table structure for table `student` */

DROP TABLE IF EXISTS `student`;

CREATE TABLE `student` (
  `id` varchar(8) NOT NULL COMMENT '学号',
  `name` varchar(20) NOT NULL COMMENT '姓名',
  `pwd` varchar(32) NOT NULL default '' COMMENT '密码',
  `classesId` int(11) unsigned NOT NULL COMMENT '班级Id',
  `roleId` int(4) unsigned NOT NULL default '0' COMMENT '0-一般用户 1-管理员',
  `flag` int(11) default NULL COMMENT '0-注销 1-激活',
  PRIMARY KEY  (`id`),
  KEY `FK_student` (`classesId`),
  CONSTRAINT `FK_student` FOREIGN KEY (`classesId`) REFERENCES `classes` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `student` */

insert  into `student`(`id`,`name`,`pwd`,`classesId`,`roleId`,`flag`) values ('16422000','admin','e10adc3949ba59abbe56e057f20f883e',1,1,1),('16422001','张三','e10adc3949ba59abbe56e057f20f883e',1,0,1),('16422002','李四','e10adc3949ba59abbe56e057f20f883e',2,0,1);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
