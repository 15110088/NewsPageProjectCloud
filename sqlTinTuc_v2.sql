/*
SQLyog Enterprise - MySQL GUI v6.15
MySQL - 5.7.19 : Database - demo
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

create database if not exists `demo`;

USE `demo`;

/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

/*Table structure for table `attach_file` */

DROP TABLE IF EXISTS `attach_file`;

CREATE TABLE `attach_file` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `post_id` int(11) DEFAULT NULL,
  `link` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_tt_att_idx` (`post_id`),
  CONSTRAINT `FK_tt_att` FOREIGN KEY (`post_id`) REFERENCES `tintuc` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

/*Data for the table `attach_file` */

insert  into `attach_file`(`id`,`post_id`,`link`,`name`) values (1,2,'sdg','sdg');

/*Table structure for table `page1` */

DROP TABLE IF EXISTS `page1`;

CREATE TABLE `page1` (
  `id` int(11) DEFAULT NULL,
  `tieude` varchar(200) DEFAULT NULL,
  `noidung` longtext,
  `link1` varchar(200) DEFAULT NULL,
  `link2` varchar(200) DEFAULT NULL,
  `link3` varchar(200) DEFAULT NULL,
  `link4` varchar(200) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

/*Data for the table `page1` */

insert  into `page1`(`id`,`tieude`,`noidung`,`link1`,`link2`,`link3`,`link4`) values (1,'WELCOME','<p>System Science and Engineering has emerged as a research field that covers a wide spectrum of modern technology. A system can be considered as a collection of entities and their interrelationships gathered together to form a whole greater than the sum of the entities. It also involves people, organizations, cultures, activities and interrelationships among them. While systems composed of autonomous subsystems are not new, increased data density, connectivity and ubiquitous computational resources have increased their interdependence and interaction complexity. This has in turn made the already difficult job of planning, developing and deploying complex systems even more difficult.</p>\r\n','ICSSE 2010: http://ieeexplore.ieee.org/xpl/mostRecentIssue.jsp?punumber=5540658','ICSSE 2012: http://ieeexplore.ieee.org/xpl/mostRecentIssue.jsp?punumber=6246741','ICSSE 2014: http://ieeexplore.ieee.org/xpl/mostRecentIssue.jsp?punumber=6879759','ICSSE 2016: http://ieeexplore.ieee.org/xpl/mostRecentIssue.jsp?punumber=7548195');

/*Table structure for table `taikhoan` */

DROP TABLE IF EXISTS `taikhoan`;

CREATE TABLE `taikhoan` (
  `name` varchar(20) NOT NULL,
  `pass` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `taikhoan` */

insert  into `taikhoan`(`name`,`pass`) values ('1','1');

/*Table structure for table `tintuc` */

DROP TABLE IF EXISTS `tintuc`;

CREATE TABLE `tintuc` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `tieude` varchar(20) DEFAULT NULL,
  `noidung` longtext,
  `mota` varchar(20) DEFAULT NULL,
  `tacgia` varchar(20) DEFAULT NULL,
  `ngaytao` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;

/*Data for the table `tintuc` */

insert  into `tintuc`(`ID`,`tieude`,`noidung`,`mota`,`tacgia`,`ngaytao`) values (5,'trung','<p>dkll&nbsp; &nbsp;&acirc;dasdasd</p>\r\n','dashdas','dasdad',NULL),(6,'nghia','<p>123</p>\r\n','trung ','nghia','23-3-2013'),(8,'hom nat chu nhat','<p>Conference committee are glad to inform that total pages of full paper is around from four to six (4-6) pages length instead of 2-4 pages as previous annoucement. That means all final versions of accepted papers are limited from four to six US letter-size double-column pages without extra charge.&nbsp;<br />\r\n&nbsp;</p>\r\n','laaaaa','n',NULL);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
