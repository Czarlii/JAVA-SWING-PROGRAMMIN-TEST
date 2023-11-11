-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: localhost    Database: quiz_db
-- ------------------------------------------------------
-- Server version	8.1.0

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
-- Table structure for table `questions`
--

DROP TABLE IF EXISTS `questions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `questions` (
  `id` int NOT NULL AUTO_INCREMENT,
  `question` varchar(255) NOT NULL,
  `answer1` varchar(255) NOT NULL,
  `answer2` varchar(255) NOT NULL,
  `answer3` varchar(255) NOT NULL,
  `answer4` varchar(255) NOT NULL,
  `correct_answer` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=54 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `questions`
--

LOCK TABLES `questions` WRITE;
/*!40000 ALTER TABLE `questions` DISABLE KEYS */;
INSERT INTO `questions` VALUES (1,'Jakie jest główne przeznaczenie Javy?','Tworzenie gier','Odtwarzanie muzyki','Programowanie aplikacji','Edycja tekstu',3),(3,'Jakie są dwa główne typy danych w Javie?','Prymitywne i obiektowe','Stałe i zmienne','Liczbowe i tekstowe','Publiczne i prywatne',1),(4,'Jakie jest domyślne wartość dla zmiennej typu int?','1','0','-1','Nieokreślona',2),(5,'Który z tych języków jest językiem obiektowym?','Python','HTML','CSS','SQL',1),(6,'Jakie są różnice między == a equals()?','Porównuje wartości','Porównuje referencje','Porównuje typy','Porównuje wartości dla obiektów i referencje dla typów prymitywnych.',4),(7,'Który z tych języków jest językiem programowania ogólnego przeznaczenia?','HTML','CSS','Python','SQL',3),(8,'Co to jest interfejs?','Klasa z metodami','Kolekcja stałych','Typ danych','Deklaracja metod bez implementacji.',4),(9,'Który tag HTML służy do tworzenia linków?','<a>','<link>','<href>','<url>',1),(10,'Który z tych języków jest używany do stylizacji stron internetowych?','Java','Python','CSS','PHP',3),(11,'Co to jest wyjątek w Javie?','Błąd kompilacji','Błąd wykonania.','Ostrzeżenie','Informacja',2),(12,'Który z tych języków jest językiem skryptowym?','C++','Python','Java','C#',2),(13,'Co to jest garbage collection?','Usuwanie niepotrzebnych plików','Automatyczne czyszczenie pamięci.','Zbieranie śmieci','Optymalizacja kodu',2),(14,'Jakie są różnice między throw i throws?','Rzucanie wyjątku','Deklarowanie wyjątku.','Obsługa wyjątku','Wszystkie powyższe',2),(15,'Co to jest try-with-resources?','Próba z zasobami','Automatyczne zamykanie zasobów.','Blok kodu','Metoda',2),(16,'Która z tych bibliotek jest używana z językiem JavaScript?','Pandas','NumPy','jQuery','TensorFlow',3),(17,'Jakie są różnice między Error a Exception?','Błąd kompilacji','Błąd wykonania.','Ostrzeżenie','Informacja',2),(18,'Jakie są różnice między StringBuilder a StringBuffer?','Synchronizacja.','Bez synchronizacji','Optymalizacja','Wszystkie powyższe',1),(19,'Jakie są różnice między static a final?','Stała wartość','Niezmienialna wartość','Zmienna klasowa.','Zmienna instancji',3),(20,'Która z tych technologii jest systemem kontroli wersji?','Docker','Kubernetes','Git','Node.js',3),(21,'Który z tych języków jest językiem wysokiego poziomu?','Assembly','Machine Code','Python','Binary',3),(22,'Jakie są różnice między sleep a wait?','Wstrzymuje wątek.','Czeka na obiekt','Zatrzymuje wątek','Wszystkie powyższe',1),(23,'Który z tych języków jest językiem kompilowanym?','Python','JavaScript','C++','Ruby',3),(24,'Która z tych technologii jest frameworkiem dla JavaScript?','Spring','Django','Vue.js','Flask',3),(25,'Która z tych technologii jest językiem zapytań do baz danych?','SQL','HTML','CSS','JS',1),(26,'Która z tych technologii służy do konteneryzacji aplikacji?','Docker','Kubernetes','Git','Node.js',1),(27,'Która z tych technologii jest językiem programowania?','HTML','CSS','Python','XML',3),(28,'Która z tych technologii jest językiem opisu danych?','JSON','Python','Java','C++',1),(29,'Który z tych języków jest językiem programowania systemowego?','Python','JavaScript','C','Ruby',3),(30,'Który operator służy do porównywania dwóch wartości?','=','==','===','!=',2),(31,'Która pętla wykonuje się przynajmniej raz, niezależnie od warunku?','for','while','do-while','foreach',3),(32,'Który operator służy do inkrementacji wartości?','--','++','-=','+=',2),(33,'Która instrukcja służy do przerwania pętli?','break','continue','exit','return',1),(34,'Który operator służy do negacji logicznej?','!','&&','||','==',1),(35,'Która z tych wartości jest fałszywa w języku JavaScript?','0','\'0\'','[]','{}',1),(36,'Która funkcja służy do wydrukowania wartości w języku Python?','print()','echo()','printf()','write()',1),(37,'Który z tych jest operatorem przypisania?','==','!=','===','=',4),(38,'Która funkcja służy do wydrukowania wartości w języku PHP?','print()','echo()','printf()','write()',2),(39,'Która z tych wartości jest prawdziwa w języku JavaScript?','0','\'0\'','[]','{}',2),(40,'Która pętla wykonuje się dopóki warunek jest prawdziwy?','for','while','do-while','foreach',2),(41,'Który z tych operatorów służy do dodawania w większości języków programowania?','-','/','+','*',3),(42,'Która z poniższych struktur jest używana do przechowywania wielu wartości?','Zmienna','Funkcja','Operator','Tablica',4),(43,'Która pętla w języku Python nie wymaga nawiasów?','for','while','do-while','foreach',2);
/*!40000 ALTER TABLE `questions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `scores`
--

DROP TABLE IF EXISTS `scores`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `scores` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL,
  `score` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `scores`
--

LOCK TABLES `scores` WRITE;
/*!40000 ALTER TABLE `scores` DISABLE KEYS */;
/*!40000 ALTER TABLE `scores` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-11-11 16:11:54
