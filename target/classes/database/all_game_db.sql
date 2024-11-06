-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: game_db
-- ------------------------------------------------------
-- Server version	8.0.33

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
-- Table structure for table `alphabet`
--

DROP TABLE IF EXISTS `alphabet`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `alphabet` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `value` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `alphabet`
--

LOCK TABLES `alphabet` WRITE;
/*!40000 ALTER TABLE `alphabet` DISABLE KEYS */;
INSERT INTO `alphabet` VALUES (1,'A'),(2,'B'),(3,'C'),(4,'D'),(5,'E');
/*!40000 ALTER TABLE `alphabet` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `game`
--

DROP TABLE IF EXISTS `game`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `game` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `end_date` datetime(6) DEFAULT NULL,
  `start_date` datetime(6) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `winner` bigint DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `game`
--

LOCK TABLES `game` WRITE;
/*!40000 ALTER TABLE `game` DISABLE KEYS */;
INSERT INTO `game` VALUES (1,'2024-10-06 15:00:00.000000','2024-10-06 14:00:00.000000','completed','type1',1),(2,'2024-10-06 16:00:00.000000','2024-10-06 15:00:00.000000','completed','type2',2),(3,'2024-10-06 17:00:00.000000','2024-10-06 16:00:00.000000','ongoing','type3',NULL),(4,'2024-10-06 18:00:00.000000','2024-10-06 17:00:00.000000','planned','type4',NULL),(5,'2024-10-06 19:00:00.000000','2024-10-06 18:00:00.000000','planned','type5',NULL);
/*!40000 ALTER TABLE `game` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `game_round`
--

DROP TABLE IF EXISTS `game_round`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `game_round` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `point` double DEFAULT NULL,
  `time_round` datetime(6) DEFAULT NULL,
  `alphabet_id` bigint DEFAULT NULL,
  `game_id` bigint DEFAULT NULL,
  `word_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKrgsvnps6vx6agnecrj65oadq1` (`alphabet_id`),
  KEY `FKoy982xqm8lmwtwackq4y4jct1` (`game_id`),
  KEY `FKqs3ehaxd7i57rkks135e7fkf3` (`word_id`),
  CONSTRAINT `FKoy982xqm8lmwtwackq4y4jct1` FOREIGN KEY (`game_id`) REFERENCES `game` (`id`),
  CONSTRAINT `FKqs3ehaxd7i57rkks135e7fkf3` FOREIGN KEY (`word_id`) REFERENCES `word` (`id`),
  CONSTRAINT `FKrgsvnps6vx6agnecrj65oadq1` FOREIGN KEY (`alphabet_id`) REFERENCES `alphabet` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `game_round`
--

LOCK TABLES `game_round` WRITE;
/*!40000 ALTER TABLE `game_round` DISABLE KEYS */;
INSERT INTO `game_round` VALUES (1,10,'2024-10-06 14:10:00.000000',1,1,1),(2,20,'2024-10-06 14:20:00.000000',2,2,2),(3,30,'2024-10-06 14:30:00.000000',3,3,3),(4,40,'2024-10-06 14:40:00.000000',4,4,4),(5,50,'2024-10-06 14:50:00.000000',5,5,5);
/*!40000 ALTER TABLE `game_round` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `join_playing`
--

DROP TABLE IF EXISTS `join_playing`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `join_playing` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `game_id` bigint NOT NULL,
  `player_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKoy7eh0d1qcuv1gjwr3qxxubd6` (`game_id`),
  KEY `FKrrwi9lcvk2l1y2q1q4kk3b7yx` (`player_id`),
  CONSTRAINT `FKoy7eh0d1qcuv1gjwr3qxxubd6` FOREIGN KEY (`game_id`) REFERENCES `game` (`id`),
  CONSTRAINT `FKrrwi9lcvk2l1y2q1q4kk3b7yx` FOREIGN KEY (`player_id`) REFERENCES `player` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `join_playing`
--

LOCK TABLES `join_playing` WRITE;
/*!40000 ALTER TABLE `join_playing` DISABLE KEYS */;
INSERT INTO `join_playing` VALUES (1,1,1),(2,2,2),(3,3,3),(4,4,4),(5,5,5);
/*!40000 ALTER TABLE `join_playing` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `list_friends`
--

DROP TABLE IF EXISTS `list_friends`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `list_friends` (
  `player_id` bigint NOT NULL,
  `friend_id` bigint NOT NULL,
  PRIMARY KEY (`player_id`,`friend_id`),
  KEY `FKqqwr8pocmk67qobq5ijtof7j5` (`friend_id`),
  CONSTRAINT `FKqqwr8pocmk67qobq5ijtof7j5` FOREIGN KEY (`friend_id`) REFERENCES `player` (`id`),
  CONSTRAINT `FKstm85ro2d0til9ekg2fd8qgfa` FOREIGN KEY (`player_id`) REFERENCES `player` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `list_friends`
--

LOCK TABLES `list_friends` WRITE;
/*!40000 ALTER TABLE `list_friends` DISABLE KEYS */;
INSERT INTO `list_friends` VALUES (2,1),(3,1),(1,2),(4,2),(1,3),(2,4);
/*!40000 ALTER TABLE `list_friends` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `player`
--

DROP TABLE IF EXISTS `player`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `player` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `player_name` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `total_game` bigint DEFAULT NULL,
  `total_game_won` bigint DEFAULT NULL,
  `total_point` double DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKj57d4kgk8qu7i73lu7xsbq8fb` (`user_id`),
  CONSTRAINT `FKj57d4kgk8qu7i73lu7xsbq8fb` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `player`
--

LOCK TABLES `player` WRITE;
/*!40000 ALTER TABLE `player` DISABLE KEYS */;
INSERT INTO `player` VALUES (1,'player1','Offline',10,5,100,1),(2,'player2','Online',12,6,120,2),(3,'player3','Offline',8,4,80,3),(4,'player4','Online',6,2,60,4),(5,'player5','Offline',15,9,150,5);
/*!40000 ALTER TABLE `player` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `player_answer_in_game`
--

DROP TABLE IF EXISTS `player_answer_in_game`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `player_answer_in_game` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `is_solve` varchar(255) DEFAULT NULL,
  `time_answer` datetime(6) DEFAULT NULL,
  `word_answer` varchar(255) DEFAULT NULL,
  `game_round_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK9py95w4ce5hw9cwcrepalhkhp` (`game_round_id`),
  CONSTRAINT `FK9py95w4ce5hw9cwcrepalhkhp` FOREIGN KEY (`game_round_id`) REFERENCES `game_round` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `player_answer_in_game`
--

LOCK TABLES `player_answer_in_game` WRITE;
/*!40000 ALTER TABLE `player_answer_in_game` DISABLE KEYS */;
INSERT INTO `player_answer_in_game` VALUES (1,'yes','2024-10-06 14:15:00.000000','apple',1),(2,'no','2024-10-06 14:25:00.000000','banana',2),(3,'yes','2024-10-06 14:35:00.000000','cherry',3),(4,'no','2024-10-06 14:45:00.000000','date',4),(5,'yes','2024-10-06 14:55:00.000000','elderberry',5);
/*!40000 ALTER TABLE `player_answer_in_game` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `email` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'user1@example.com','password1','1234567890','admin','user1'),(2,'user2@example.com','password2','1234567891','user','user2'),(3,'user3@example.com','password3','1234567892','user','user3'),(4,'user4@example.com','password4','1234567893','user','user4'),(5,'user5@example.com','password5','1234567894','user','user5'),(6,'user@example.com','123123','090131231','admin','user');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `word`
--

DROP TABLE IF EXISTS `word`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `word` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `word` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `word`
--

LOCK TABLES `word` WRITE;
/*!40000 ALTER TABLE `word` DISABLE KEYS */;
INSERT INTO `word` VALUES (1,'apple'),(2,'banana'),(3,'cherry'),(4,'date'),(5,'elderberry');
/*!40000 ALTER TABLE `word` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'game_db'
--
/*!50003 DROP PROCEDURE IF EXISTS `ADD_FRIEND` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `ADD_FRIEND`(
    IN p_player_id INT,
    IN p_friend_id INT
)
BEGIN
    IF EXISTS (SELECT 1 FROM player WHERE id = p_friend_id) THEN
        IF NOT EXISTS (SELECT 1 FROM list_friends WHERE (player_id = p_player_id AND friend_id = p_friend_id) OR (player_id = p_friend_id AND friend_id = p_player_id)) THEN
            BEGIN
                DECLARE EXIT HANDLER FOR SQLEXCEPTION
                BEGIN
                    SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Error occurred while adding friend!';
                END;

                INSERT INTO list_friends (player_id, friend_id)
                VALUES (p_player_id, p_friend_id);

                INSERT INTO list_friends (player_id, friend_id)
                VALUES (p_friend_id, p_player_id);
            END;
        ELSE
			SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Friend already exists in the list!';
        END IF;
    ELSE
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Friend ID does not exist!';
    END IF;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `GET_PLAYER_FRIENDS` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `GET_PLAYER_FRIENDS`(IN playerId INT)
BEGIN
    SELECT p.id, p.player_name,p.status
    FROM list_friends f
    JOIN player p ON f.friend_id = p.id
    WHERE f.player_id = playerId;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `RANKING` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `RANKING`()
BEGIN
    SELECT id,player_name,(total_game_won*100/total_game) as percent_win,total_point FROM player
    order BY total_game desc ,total_game_won desc,total_point desc;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `REGISTER_USER` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `REGISTER_USER`(
    IN p_user_name VARCHAR(50),
    IN p_email VARCHAR(100),
    IN p_password VARCHAR(255),
    IN p_phone VARCHAR(15),
    IN p_role VARCHAR(20)
)
BEGIN
    -- Kiểm tra nếu email đã tồn tại
    IF EXISTS (SELECT 1 FROM user WHERE email = p_email)or  (SELECT 1 FROM user WHERE user_name=p_user_name) THEN
        SIGNAL SQLSTATE '45000' 
        SET MESSAGE_TEXT = 'Email đã tồn tại!';
    ELSE
        -- Thêm người dùng mới vào bảng user
        INSERT INTO user (user_name, email, password, phone, role)
        VALUES (p_user_name, p_email, p_password, p_phone, p_role);
    END IF;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `SEARCH_PLAYER` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `SEARCH_PLAYER`(
    IN input_player_id BIGINT,
    IN p_player_name VARCHAR(255)
)
BEGIN
    SELECT *,
           CASE
               WHEN EXISTS (
                   SELECT 1
                   FROM list_friends lf
                   WHERE (lf.player_id = input_player_id AND lf.friend_id = p.id)
                      OR (lf.friend_id = input_player_id AND lf.player_id = p.id)
               )
               THEN 'Yes'
               ELSE 'No'
           END AS is_friend
    FROM player AS p
    WHERE p.player_name LIKE CONCAT('%', p_player_name, '%')
      AND p.id != input_player_id;  -- Loại bỏ chính người chơi khỏi kết quả
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-10-08  0:04:40
