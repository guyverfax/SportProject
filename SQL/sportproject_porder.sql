CREATE DATABASE  IF NOT EXISTS `sportproject` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `sportproject`;
-- MySQL dump 10.13  Distrib 8.0.41, for Win64 (x86_64)
--
-- Host: localhost    Database: sportproject
-- ------------------------------------------------------
-- Server version	8.0.41

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `porder`
--

DROP TABLE IF EXISTS `porder`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `porder` (
  `id` int NOT NULL AUTO_INCREMENT,
  `porderno` varchar(50) NOT NULL,
  `productno` varchar(50) NOT NULL,
  `memberno` varchar(50) NOT NULL,
  `employno` varchar(50) NOT NULL,
  `amount` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=141 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `porder`
--

LOCK TABLES `porder` WRITE;
/*!40000 ALTER TABLE `porder` DISABLE KEYS */;
INSERT INTO `porder` VALUES (9,'O2025021412000101','P001','M001','E001',3),(10,'O2025021412000101','P002','M001','E001',2),(11,'O2025021412000102','P002','M001','E002',7),(12,'O2025021412000103','P003','M002','E003',3),(13,'O2025021412000104','P001','M002','E001',1),(14,'O2025021412000104','P003','M002','E001',1),(15,'O2025021412000104','P005','M002','E001',1),(16,'O2025021412000105','P005','M003','E002',4),(17,'O2025021412000106','P006','M003','E003',2),(18,'O2025021412000107','P007','M004','E001',1),(19,'O2025021412000108','P008','M004','E002',3),(20,'O2025021412000109','P009','M005','E003',1),(21,'O2025021412000110','P010','M005','E001',2),(23,'O2025021412000112','P002','M005','E003',1),(24,'O2025021412000113','P003','M005','E001',2),(25,'O2025021412000114','P004','M005','E002',3),(26,'O2025021412000115','P002','M005','E003',4),(27,'O2025021412000115','P004','M005','E003',4),(28,'O2025021412000115','P006','M005','E003',4),(29,'O2025021412000115','P005','M005','E003',4),(30,'O2025021412000116','P006','M005','E001',1),(31,'O2025021412000117','P007','M005','E002',4),(32,'O2025021412000118','P008','M005','E003',3),(33,'O2025021412000119','P009','M005','E001',1),(34,'O2025021412000120','P010','M005','E002',2),(35,'O2025021412000121','P001','M001','E003',1),(36,'O2025021412000122','P002','M002','E001',2),(37,'O2025021412000123','P003','M003','E002',3),(38,'O2025021412000124','P004','M004','E003',4),(39,'O2025021412000125','P005','M005','E001',1),(40,'O2025021412000126','P006','M005','E002',2),(41,'O2025021412000127','P007','M005','E003',3),(42,'O2025021412000128','P008','M005','E001',4),(43,'O2025021412000129','P009','M005','E002',1),(44,'O2025021412000130','P010','M005','E003',2),(45,'O2025021412000131','P001','M001','E001',3),(47,'O2025021412000133','P003','M003','E003',2),(48,'O2025021412000134','P004','M004','E001',4),(49,'O2025021412000135','P005','M005','E002',1),(50,'O2025021412000136','P006','M005','E003',2),(51,'O2025021412000137','P007','M005','E001',3),(52,'O2025021412000138','P008','M005','E002',4),(54,'O2025021412000140','P010','M005','E001',2),(55,'O2025021412000141','P001','M001','E002',3),(56,'O2025021412000142','P002','M002','E003',1),(57,'O2025021412000143','P003','M003','E001',2),(58,'O2025021412000144','P004','M004','E002',4),(59,'O2025021412000145','P005','M005','E003',1),(60,'O2025021412000146','P006','M005','E001',2),(61,'O2025021412000147','P007','M005','E002',3),(62,'O2025021412000148','P008','M005','E003',4),(63,'O2025021412000149','P009','M005','E001',1),(85,'O2025021412000151','P001','M001','E001',1),(122,'O2025021621553001','P002','M002','E002',3),(123,'O2025021621553001','P003','M002','E002',2),(124,'O2025021911370201','P001','M004','E003',2),(125,'O2025021911370201','P009','M004','E003',4),(126,'O2025021911370201','P010','M004','E003',2),(127,'O2025021911460401','P002','M002','E004',2),(128,'O2025021911460401','P004','M002','E004',5),(129,'O2025021912262301','P003','M001','E004',3),(130,'O2025021912262301','P005','M001','E004',5),(131,'O2025021912262301','P010','M001','E004',5),(132,'O2025021912344201','P001','M002','E002',1),(133,'O2025021912344201','P008','M002','E002',2),(134,'O2025021912344201','P007','M002','E002',3),(135,'O2025021913051401','P001','M005','E101',2),(136,'O2025021913051401','P002','M005','E101',3),(137,'O2025021913051401','P003','M005','E101',1),(138,'O2025022010532701','P001','M001','E101',1),(139,'O2025022010532701','P002','M001','E101',2),(140,'O2025022010532701','P005','M001','E101',3);
/*!40000 ALTER TABLE `porder` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-02-20 11:41:25
