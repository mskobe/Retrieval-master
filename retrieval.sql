-- MySQL dump 10.13  Distrib 5.5.9, for Win32 (x86)
--
-- Host: localhost    Database: retrieval
-- ------------------------------------------------------
-- Server version	5.5.20

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Current Database: `retrieval`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `retrieval` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `retrieval`;

--
-- Table structure for table `fish`
--

DROP TABLE IF EXISTS `fish`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fish` (
  `id` varchar(40) NOT NULL,
  `name` varchar(45) DEFAULT NULL,
  `name_en` varchar(45) DEFAULT NULL,
  `uri` varchar(45) DEFAULT NULL,
  `uri_name` varchar(45) DEFAULT NULL,
  `parent_id` varchar(40) DEFAULT NULL,
  `images` varchar(100) DEFAULT NULL,
  `user_fields` varchar(100) DEFAULT NULL,
  `owl` varchar(10000) DEFAULT NULL,
  `detail_type` int(11) DEFAULT '1',
  `contact` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fish`
--

LOCK TABLES `fish` WRITE;
/*!40000 ALTER TABLE `fish` DISABLE KEYS */;
INSERT INTO `fish` VALUES ('14657e76-3a36-4695-ab4c-2890e1e58a8d','超级本','Ultrabook','http://Ultrabook.org','http://Ultrabook.org#Ultrabook','529e3481-0989-475b-ac4f-c2adb42bdcf9','images/a874f618-d29c-4ea3-b712-bfe9846809ba.jpg',NULL,'<rdf:RDF xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xmlns:owl=\"http://www.w3.org/2002/07/owl#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\"><owl:Class rdf:ID=\"http://Ultrabook.org#Ultrabook\"><rdfs:subClassOf rdf:resource=\"\"/><rdfs:label/><desc>超级本</desc><images><item>images/a874f618-d29c-4ea3-b712-bfe9846809ba.jpg</item></images><userfields/><attributes/><matrix/><childNodes/></owl:Class></rdf:RDF>',1,NULL),('1860c770-8638-45d3-9c34-ba9920851e16','台式计算机','Desktop PC','http://Desktop-PC.org','http://Desktop-PC.org#Desktop PC','529e3481-0989-475b-ac4f-c2adb42bdcf9','images/a874f618-d29c-4ea3-b712-bfe9846809ba.jpg',NULL,'<rdf:RDF xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xmlns:owl=\"http://www.w3.org/2002/07/owl#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\"><owl:Class rdf:ID=\"http://Desktop-PC.org#Desktop PC\"><rdfs:subClassOf rdf:resource=\"\"/><rdfs:label/><desc>台式计算机</desc><images><item>images/a874f618-d29c-4ea3-b712-bfe9846809ba.jpg</item></images><userfields/><attributes/><matrix/><childNodes/></owl:Class></rdf:RDF>',1,NULL),('294b05e1-23b2-4b1b-a0a2-f3027db545d3','生化计算机','biochemistry computer',NULL,NULL,'529e3481-0989-475b-ac4f-c2adb42bdcf9',NULL,NULL,NULL,2,'北京生化计算机研究所 赵博士'),('529e3481-0989-475b-ac4f-c2adb42bdcf9','计算机','Computer','http://Computer.org','http://Computer.org#computer','virtual_node','images/55990bc5-ede7-4d32-a06e-4e244cd27101.jpg',NULL,'<rdf:RDF xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xmlns:owl=\"http://www.w3.org/2002/07/owl#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\"><owl:Class rdf:ID=\"http://Computer.org#Computer\"><rdfs:subClassOf rdf:resource=\"\"/><rdfs:label/><desc>计算机介绍</desc><images><item>images/55990bc5-ede7-4d32-a06e-4e244cd27101.jpg</item></images><userfields><field key=\"是否根元素\">是</field></userfields><attributes><attribute enName=\"Small volume\" index=\"0\" name=\"体积小\"><desc>体积小</desc><image>3bd58d08-040a-4b53-aa95-981e53bfbe08.jpg</image><userfields><field key=\"体积小Key\">体积小Value</field></userfields></attribute><attribute enName=\"civil\" index=\"1\" name=\"民用\"><desc>民用</desc><image/><userfields/></attribute><attribute enName=\"performance ok\" index=\"2\" name=\"性能达标\"><desc>性能达标</desc><image/><userfields/></attribute><attribute enName=\"ultrathin\" index=\"3\" name=\"超薄\"><desc>超薄</desc><image>a874f618-d29c-4ea3-b712-bfe9846809ba.jpg</image><userfields/></attribute><attribute enName=\"performance not ok\" index=\"4\" name=\"性能差\"><desc>性能差</desc><image>f2517fb8-c5b3-43a5-bead-b5a09850849e.jpg</image><userfields/></attribute><attribute enName=\"big\" index=\"5\" name=\"体积大\"><desc>体积大描述</desc><image>bf186fb7-180e-4491-9e8e-7e6a77117d46.jpg</image><userfields/></attribute><attribute enName=\"commercial\" index=\"6\" name=\"商用\"><desc>商用</desc><image>6c7602a4-4576-4f46-89f5-72dd9af4e97f.jpg</image><userfields/></attribute><attribute enName=\"performance power\" index=\"7\" name=\"性能超强\"><desc>性能超强</desc><image>9d868967-53c2-4d57-bec1-5819bf19b6fe.jpg</image><userfields/></attribute><attribute enName=\"small power\" index=\"8\" name=\"功耗小\"><desc>功耗小</desc><image>8e9f9862-1dfc-4b67-9612-8c1177b191b7.jpg</image><userfields/></attribute></attributes><matrix><row index=\"0\">222000000</row><row index=\"1\">222200000</row><row index=\"2\">221120000</row><row index=\"3\">122112000</row><row index=\"4\">111112220</row><row index=\"5\">212211222</row></matrix><childNodes><node index=\"0\">e6fbec8a-4039-4316-9a5f-2aef2393f9bd</node><node index=\"1\">14657e76-3a36-4695-ab4c-2890e1e58a8d</node><node index=\"2\">be82bd26-c598-4213-a08e-ae612334227a</node><node index=\"3\">1860c770-8638-45d3-9c34-ba9920851e16</node><node index=\"4\">f47482a8-8e42-460d-85e2-5c2d67e19f7f</node><node index=\"5\">294b05e1-23b2-4b1b-a0a2-f3027db545d3</node></childNodes></owl:Class></rdf:RDF>',1,NULL),('be82bd26-c598-4213-a08e-ae612334227a','上网本','Notebook','http://Notebook.org','http://Notebook.org#Notebook','529e3481-0989-475b-ac4f-c2adb42bdcf9','images/a874f618-d29c-4ea3-b712-bfe9846809ba.jpg',NULL,'<rdf:RDF xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xmlns:owl=\"http://www.w3.org/2002/07/owl#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\"><owl:Class rdf:ID=\"http://Notebook.org#Notebook\"><rdfs:subClassOf rdf:resource=\"\"/><rdfs:label/><desc>超级本</desc><images><item>images/a874f618-d29c-4ea3-b712-bfe9846809ba.jpg</item></images><userfields/><attributes/><matrix/><childNodes/></owl:Class></rdf:RDF>',1,NULL),('e6fbec8a-4039-4316-9a5f-2aef2393f9bd','膝上计算机','Laptop','http://laptop.org','http://laptop.org#Laptop','529e3481-0989-475b-ac4f-c2adb42bdcf9','images/1aaca81d-414b-4a77-9397-c61a03d4ce6e.jpg;images/eb7a86d8-0707-4d43-bb96-153a9b3461ec.jpg',NULL,'<rdf:RDF xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xmlns:owl=\"http://www.w3.org/2002/07/owl#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\"><owl:Class rdf:ID=\"http://laptop.org#Laptop\"><rdfs:subClassOf rdf:resource=\"\"/><rdfs:label/><desc>膝上计算机介绍</desc><images><item>images/1aaca81d-414b-4a77-9397-c61a03d4ce6e.jpg</item><item>images/eb7a86d8-0707-4d43-bb96-153a9b3461ec.jpg</item></images><userfields><field key=\"膝上计算机Key1\">膝上计算机Value1</field><field key=\"膝上计算机Key0\">膝上计算机Value0</field></userfields><attributes/><matrix/><childNodes/></owl:Class></rdf:RDF>',1,NULL),('f47482a8-8e42-460d-85e2-5c2d67e19f7f','超级计算机','Super PC','http://Super-PC.org','http://Super-PC.org#Super PC','529e3481-0989-475b-ac4f-c2adb42bdcf9','images/a874f618-d29c-4ea3-b712-bfe9846809ba.jpg',NULL,'<rdf:RDF xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xmlns:owl=\"http://www.w3.org/2002/07/owl#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\"><owl:Class rdf:ID=\"http://Super-PC.org#Super PC\"><rdfs:subClassOf rdf:resource=\"\"/><rdfs:label/><desc>超级计算机</desc><images><item>images/a874f618-d29c-4ea3-b712-bfe9846809ba.jpg</item></images><userfields/><attributes/><matrix/><childNodes/></owl:Class></rdf:RDF>',1,NULL),('virtual_node','','','','','-1',NULL,NULL,'<rdf:RDF xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xmlns:owl=\"http://www.w3.org/2002/07/owl#\" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema#\"><owl:Class rdf:ID=\"#\"><rdfs:subClassOf rdf:resource=\"\"/><rdfs:label/><desc/><images/><userfields/><attributes/><matrix/><childNodes><node index=\"0\">529e3481-0989-475b-ac4f-c2adb42bdcf9</node></childNodes></owl:Class></rdf:RDF>',1,NULL);
/*!40000 ALTER TABLE `fish` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `su`
--

DROP TABLE IF EXISTS `su`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `su` (
  `id` varchar(40) NOT NULL,
  `name` varchar(20) NOT NULL,
  `password` varchar(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `su`
--

LOCK TABLES `su` WRITE;
/*!40000 ALTER TABLE `su` DISABLE KEYS */;
INSERT INTO `su` VALUES ('946d775b-c566-4bdc-ad81-0d1a00ea3dd1','admin','admin');
/*!40000 ALTER TABLE `su` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` varchar(40) NOT NULL,
  `name` varchar(20) NOT NULL,
  `password` varchar(20) NOT NULL,
  `isActive` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES ('61ca958d-5027-4ec6-ab7e-7ef93838d830','zhaojie','123',1),('sdfsdv23421','su','123',0);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2012-02-11 15:34:48
