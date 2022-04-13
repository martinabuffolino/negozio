SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
--
-- Database: `negozio`
--
-- --------------------------------------------------------
-- Struttura della tabella `TECNICO`
--
DROP TABLE IF EXISTS `TECNICO`;
CREATE TABLE `TECNICO` (
  `CODICETECNICO` int(2) NOT NULL,
  `NOMETECNICO` varchar(30) NOT NULL,
  `COGNOMETECNICO` varchar(30) NOT NULL,
  PRIMARY KEY (`CODICETECNICO`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
--
-- Dump dei dati per la tabella `TECNICO`
--
INSERT INTO `TECNICO` (`CODICETECNICO`, `NOMETECNICO`, `COGNOMETECNICO`) VALUES
(70, 'Marco', 'Faraone'),
(71, 'Giovanni', 'Cortazzo'),
(72, 'Francesco', 'Palmieri'),
(73, 'Carmen', 'Nicoletti'),
(74, 'Laura', 'Russo');
--
--
--
-- --------------------------------------------------------
-- Struttura della tabella `CLIENTE`
--
DROP TABLE IF EXISTS `CLIENTE`;
CREATE TABLE `CLIENTE` (
  `CODICEFISCALE` char(16) NOT NULL,
  `NOME` varchar(30) NOT NULL,
  `COGNOME` varchar(30) NOT NULL,
  `EMAIL` varchar(45) NOT NULL,
  `PASSWORD` varchar(50) NOT NULL,
  `CELLULARE` varchar(12) DEFAULT NULL,
  PRIMARY KEY (`CODICEFISCALE`),
  UNIQUE KEY `EMAIL_UNIQUE` (`EMAIL`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
--
-- Dump dei dati per la tabella `CLIENTE`
--
INSERT INTO `CLIENTE` (`CODICEFISCALE`, `NOME`, `COGNOME`, `EMAIL`, `PASSWORD`, `CELLULARE`) VALUES
('DNGRNI06L64A944K', 'Giovanna', 'Di Palo', 'giovannadipalo@libero.it', 'password1', '3286534009'),
('FRTZCR07T02F205L', 'Giuseppe', 'La Russa', 'giuseppelarussa@gmail.com', 'password2', '3561913270'),
('NTLLNI10D54L219L', 'Giacomo', 'Carrato', 'giacomocarrato@gmail.com', 'password3', '3930811987'),
('RNCCMN06M25F205S', 'Luigi', 'Iovino', 'luigiiovino@gmail.com', 'password4', '3334347890'),
('RSOGMM04T62C351N', 'Carmen', 'De Giacomo', 'carmendegiacom@gmail.com', 'password5', '3338890888');
--
--
--
-- --------------------------------------------------------
-- Struttura della tabella `RICEVUTA`
--
DROP TABLE IF EXISTS `RICEVUTA`;
CREATE TABLE `RICEVUTA` (
  `CODRICEVUTA` int(6) NOT NULL  int(6) NOT NULL,
  `COSTORIPARAZIONE` int(10) NOT NULL,
  `CODRIPARAZIONE` int(4) NOT NULL,
  `SERIALESMARTPHONE` varchar(17) NOT NULL,
  `CODICETECNICO` int(2) NOT NULL,
  `EMAILCLIENTE` varchar(45) NOT NULL,
  PRIMARY KEY (`CODRICEVUTA`),
  KEY `FK_CODRIP` (`CODRIPARAZIONE`),
  KEY `FK_SERSMART` (`SERIALESMARTPHONE`),
  KEY `FK_CODTEC` (`CODICETECNICO`),
  KEY `FK_EMAILCLI` (`EMAILCLIENTE`),
  CONSTRAINT `FK_COD_TEC` FOREIGN KEY (`CODICETECNICO`) REFERENCES `TECNICO` (`CODICETECNICO`),
  CONSTRAINT `FK_COD_RIP` FOREIGN KEY (`CODRIPARAZIONE`) REFERENCES `RIPARAZIONE` (`CODRIPARAZIONE`),
  CONSTRAINT `FK_SER_SMART` FOREIGN KEY (`SERIALESMARTPHONE`) REFERENCES `SMARTPHONE` (`SERIALESMARTPHONE`),
  CONSTRAINT `FK_EMAIL_CLI` FOREIGN KEY (`EMAILCLIENTE`) REFERENCES `CLIENTE` (`EMAILCLIENTE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
--
-- Dump dei dati per la tabella `RICEVUTA`
--
INSERT INTO `RICEVUTA` (`CODRICEVUTA`, `COSTORIPARAZIONE`, `CODRIPARAZIONE`, `SERIALESMARTPHONE`, `CODICETECNICO`, `EMAILCLIENTE`) VALUES
(10, 1, 1032, 'UJD00021HNS21LO43', 102, 'danyru@gmail.com'),
(11, 3962, 1034, 'BES111JMN90I12KDL', 108, 'espangelo68@gmail.com'),
(12, 48, 1035, 'TGFH123567YHDVB12', 100, 'giuliom@gmail.com'),
(13, 50, 1039, 'FGSBJU887JA2306N7', 100, 'luigip@motortrucks.com'),
(14, 487, 1040, 'REF876HN239000T69', 106, 'luigip@motortrucks.com');
--
--
--
-- --------------------------------------------------------
-- Struttura della tabella `RIPARAZIONE`
--
DROP TABLE IF EXISTS `RIPARAZIONE`;
CREATE TABLE `RIPARAZIONE` (
  `CODRIPARAZIONE` int(4) NOT NULL,
  `TIPORIPARAZIONE` varchar(20) NOT NULL,
  `STATO` varchar(40) DEFAULT NULL,
  `SERIALE` varchar(8) NOT NULL,
  `CODTECNICO` int(2) NOT NULL,
  PRIMARY KEY (`CODRIPARAZIONE`),
  KEY `FK_SERSMART` (`SERIALE`),
  KEY `FK_CODTEC` (`CODTECNICO`),
  CONSTRAINT `FK_SER_SMART` FOREIGN KEY (`SERIALE`) REFERENCES `SMARTPHONE` (`SERIALE`),
  CONSTRAINT `FK_COD_TEC` FOREIGN KEY (`CODTECNICO`) REFERENCES `TECNICO` (`CODICETECNICO`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
--
-- Dump dei dati per la tabella `RIPARAZIONE`
--
INSERT INTO `RIPARAZIONE` (`CODRIPARAZIONE`, `TIPORIPARAZIONE`, `STATO`, `SERIALE`, `CODTECNICO`) VALUES
(1000, 'Sostituzione Schermo', 'In Corso', 'zmPXJbp7', 70),
(1001, 'Sostituzione Batteria', 'In Corso', 'VEGwFaZi', 73),
(1002, 'Sostituzione Fotocamera', 'In Corso', 'nQZJXxKm', 71),
(1003, 'Sostituzione Touch Id', 'In Corso', '9MMoboWo', 74),
(1004, 'Sostituzione Jack Alimentazione', 'In Corso', 'XzStAybW', 72);
--
--
--
-- --------------------------------------------------------
-- Struttura della tabella `SMARTPHONE`
--
DROP TABLE IF EXISTS `SMARTPHONE`;
CREATE TABLE `SMARTPHONE` (
  `SERIALE` varchar(8) NOT NULL,
  `MARCA` varchar(20) NOT NULL,
  `MODELLO` varchar(20) NOT NULL,
  `COLORE` varchar(20) DEFAULT NULL,
  `CODFISCALE` varchar(16) NOT NULL,
  PRIMARY KEY (`SERIALE`),
  KEY `FK_CF` (`CODFISCALE`),
  ADD CONSTRAINT `FK_C_F` FOREIGN KEY (`CODFISCALE`) REFERENCES `CLIENTE` (`CODFISCALE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
--
-- Dump dei dati per la tabella `SMARTPHONE`
--
INSERT INTO `SMARTPHONE` (`SERIALE`, `MARCA`, `MODELLO`, `COLORE`, `CODFISCALE`) VALUES
('zmPXJbp7', 'Iphone', '13 Pro Max', 'Verde', 'DNGRNI06L64A944K'),
('VEGwFaZi','Samsung',  'S22+', 'Bianco', 'FRTZCR07T02F205L'),
('nQZJXxKm','Iphone', 'SE', 'Oro', 'NTLLNI10D54L219L'),
('9MMoboWo','Samsung', 'A53', 'Blu', 'RNCCMN06M25F205S'),
('XzStAybW','Iphone', '12 Pro', 'Nero', 'RSOGMM04T62C351N');
--
--
--
-- --------------------------------------------------------
-- Struttura della tabella CATEGORIA
-- 
DROP TABLE IF EXISTS `CATEGORIA`;
CREATE TABLE `CATEGORIA` (
  `NOME` varchar(20) NOT NULL,
  PRIMARY KEY (`NOME`),
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
--
-- Dump dei dati per la tabella categoria
--
INSERT INTO `CATEGORIA` VALUES ('Cover'),('Cavo');
--
--
--
-- --------------------------------------------------------
-- Struttura della tabella ACCESS_CATEG
-- 
DROP TABLE IF EXISTS `ACCESS_CATEG`;
CREATE TABLE `ACCESS_CATEG` (
  `ACCESS` int(5) DEFAULT NULL,
  `CATEG` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
--
-- Dump dei dati per la tabella ACCESS_CATEG
--
INSERT INTO `ACCESS_CATEG` (`ACCESS`, `CATEG`) VALUES 
(20001,'Cover'),
(20002,'Cover'),
(20003,'Cavo'),
(20004,'Cover'),
(20005,'Cover'),
(20006,'Cavo'),
(20007,'Cover'),
(20008,'Cavo'),
(20009,'Cavo'),
(20010,'Cavo');
--
--
-- --------------------------------------------------------
--Struttura della tabella ACCESSORI
--
--
DROP TABLE IF EXISTS `ACCESSORI`;
CREATE TABLE `ACCESSORI`(
  `CODICE` int(5) NOT NULL,
  `NOME` varchar(50),
  `MODELLO` varchar(40),
  `MARCA` varchar(10),
  `QUANTITA` int(5) NOT NULL,
  `VENDUTI` int(5) NOT NULL,
  `PREZZO` double(10),
  PRIMARY KEY (`CODICE`),
 )ENGINE=InnoDB DEFAULT CHARSET=utf8;
--
--Dump dei dati per la tabella ACCESSORI
--
--
INSERT INTO `ACCESSORI` (`CODICE`, `NOME`, `MODELLO`, `MARCA`, `QUANTITA`,  `VENDUTI`, `PREZZO`) VALUES
(20001,'Cover Bianco','12','iPhone',20,3,5.54),
(20002,'Cover Verde','12 Pro','iPhone',20,5,9.99),
(20003,'Cavo Ricarica', 'Lightning/USB','iPhone',15,6,15.00),
(20004,'Cover Lilla','13 ProMax','iPhone',20,10,12.99),
(20005,'Cover Nero', 'S22','Samsung',20,11,11.50),
(20006,'Cavo Ricarica', 'Lightning/USB Type-C','iPhone',15,6,25.00),
(20007,'Cover Blu','21Ultra','Samsung',20,6,15.20),
(20008,'Cavo Ricarica', 'USB-Micro/USB','Samsung',15,7,10.00),
(20009,'Cavo Adattatore Cuffie', 'Lightning/Mini-Jack','iPhone',15,2,10.00),
(20010,'Cavo Adattatore Cuffie', 'USB Type-C/Mini-Jack','Samsung',15,1,10.00);
--
--
--
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
