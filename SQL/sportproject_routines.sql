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
-- Temporary view structure for view `porderdetail`
--

DROP TABLE IF EXISTS `porderdetail`;
/*!50001 DROP VIEW IF EXISTS `porderdetail`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `porderdetail` AS SELECT 
 1 AS `porderno`,
 1 AS `orderdate`,
 1 AS `memberno`,
 1 AS `membername`,
 1 AS `employno`,
 1 AS `employname`,
 1 AS `productno`,
 1 AS `productname`,
 1 AS `price`,
 1 AS `amount`,
 1 AS `subtotal`,
 1 AS `stockquantity`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `pordersummary`
--

DROP TABLE IF EXISTS `pordersummary`;
/*!50001 DROP VIEW IF EXISTS `pordersummary`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `pordersummary` AS SELECT 
 1 AS `porderno`,
 1 AS `memberno`,
 1 AS `membername`,
 1 AS `employno`,
 1 AS `employname`,
 1 AS `products`,
 1 AS `totalprice`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `revenue`
--

DROP TABLE IF EXISTS `revenue`;
/*!50001 DROP VIEW IF EXISTS `revenue`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `revenue` AS SELECT 
 1 AS `year`,
 1 AS `month`,
 1 AS `revenue`*/;
SET character_set_client = @saved_cs_client;

--
-- Final view structure for view `porderdetail`
--

/*!50001 DROP VIEW IF EXISTS `porderdetail`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `porderdetail` AS select `p`.`porderno` AS `porderno`,str_to_date(substr(`p`.`porderno`,2,8),'%Y%m%d') AS `orderdate`,`p`.`memberno` AS `memberno`,`m`.`name` AS `membername`,`p`.`employno` AS `employno`,`e`.`name` AS `employname`,`p`.`productno` AS `productno`,`pr`.`productname` AS `productname`,`pr`.`price` AS `price`,`p`.`amount` AS `amount`,(`pr`.`price` * `p`.`amount`) AS `subtotal`,`pr`.`quantity` AS `stockquantity` from (((`porder` `p` join `member` `m` on((`p`.`memberno` = `m`.`memberno`))) join `employ` `e` on((`p`.`employno` = `e`.`employno`))) join `product` `pr` on((`p`.`productno` = `pr`.`productno`))) order by `p`.`porderno` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `pordersummary`
--

/*!50001 DROP VIEW IF EXISTS `pordersummary`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `pordersummary` AS select `o`.`porderno` AS `porderno`,`m`.`memberno` AS `memberno`,`m`.`name` AS `membername`,`e`.`employno` AS `employno`,`e`.`name` AS `employname`,group_concat(concat(`p`.`productname`,' x',`o`.`amount`) order by `p`.`productno` ASC separator ', ') AS `products`,sum((`o`.`amount` * `p`.`price`)) AS `totalprice` from (((`porder` `o` join `member` `m` on((`o`.`memberno` = `m`.`memberno`))) join `employ` `e` on((`o`.`employno` = `e`.`employno`))) join `product` `p` on((`o`.`productno` = `p`.`productno`))) group by `o`.`porderno`,`m`.`memberno`,`m`.`name`,`e`.`employno`,`e`.`name` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `revenue`
--

/*!50001 DROP VIEW IF EXISTS `revenue`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `revenue` AS select substr(`p`.`porderno`,2,4) AS `year`,substr(`p`.`porderno`,6,2) AS `month`,sum((`p`.`amount` * `pr`.`price`)) AS `revenue` from (`porder` `p` join `product` `pr` on((`p`.`productno` = `pr`.`productno`))) group by `year`,`month` order by `year`,`month` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-02-23  0:14:08
