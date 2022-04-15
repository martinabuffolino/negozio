-- Creazione tabella `cliente`
--
CREATE TABLE `cliente` (
  `CODICEFISCALE` varchar(16) NOT NULL,
  `NOME` varchar(45) DEFAULT NULL,
  `COGNOME` varchar(45) DEFAULT NULL,
  `EMAIL` varchar(45) DEFAULT NULL,
  `PASSWORD` varchar(45) DEFAULT NULL,
  `CELLULARE` varchar(12) DEFAULT NULL,
  PRIMARY KEY (`CODICEFISCALE`),
  UNIQUE KEY `EMAIL_UNIQUE` (`EMAIL`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
--
-- Popolamento tabella `cliente`
--
INSERT INTO `negozio`.`cliente` (`CODICEFISCALE`, `NOME`, `COGNOME`, `EMAIL`, `PASSWORD`, `CELLULARE`) VALUES
('DNGRNI06L64A944K', 'Giovanna', 'Di Palo', 'giovannadipalo@libero.it', 'password', '3286534009'),
('FRTZCR07T02F205L', 'Giuseppe', 'La Russa', 'giuseppelarussa@gmail.com', 'password', '3561913270'),
('NTLLNI10D54L219L', 'Giacomo', 'Carrato', 'giacomocarrato@gmail.com', 'password', '3930811987'),
('RNCCMN06M25F205S', 'Luigi', 'Iovino', 'luigiiovino@gmail.com', 'password', '3334347890'),
('RSOGMM04T62C351N', 'Carmen', 'De Giacomo', 'carmendegiacom@gmail.com', 'password', '3338890888');
--
-----------------------------------------------------------------------------------------------------
-- Creazione tabella `tecnico`
--
CREATE TABLE `tecnico` (
  `CODICETECNICO` int NOT NULL,
  `NOMETECNICO` varchar(30) DEFAULT NULL,
  `COGNOMETECNICO` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`CODICETECNICO`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
--
-- Popolamento tabella `tecnico`
--
INSERT INTO `negozio`.`tecnico` (`CODICETECNICO`, `NOMETECNICO`, `COGNOMETECNICO`) VALUES
(70, 'Marco', 'Faraone'),
(71, 'Giovanni', 'Cortazzo'),
(72, 'Francesco', 'Palmieri'),
(73, 'Carmen', 'Nicoletti'),
(74, 'Laura', 'Russo');
--
-----------------------------------------------------------------------------------------------------
-- Creazione tabella `smartphone`
--
CREATE TABLE `smartphone` (
  `SERIALE` varchar(8) NOT NULL,
  `MARCA` varchar(20) DEFAULT NULL,
  `MODELLO` varchar(45) DEFAULT NULL,
  `COLORE` varchar(45) DEFAULT NULL,
  `CODFISCALE` varchar(16) DEFAULT NULL,
  PRIMARY KEY (`SERIALE`),
  KEY `CODFISCALE_idx` (`CODFISCALE`),
  CONSTRAINT `CODFISCALE` FOREIGN KEY (`CODFISCALE`) REFERENCES `cliente` (`CODICEFISCALE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
--
-- Popolamento tabella `smartphone`
--
 INSERT INTO `negozio`.`smartphone` (`SERIALE`, `MARCA`, `MODELLO`, `COLORE`, `CODFISCALE`) VALUES
('zmPXJbp7', 'Iphone', '13 Pro Max', 'Verde', 'DNGRNI06L64A944K'),
('VEGwFaZi','Samsung',  'S22+', 'Bianco', 'FRTZCR07T02F205L'),
('nQZJXxKm','Iphone', 'SE', 'Oro', 'NTLLNI10D54L219L'),
('9MMoboWo','Samsung', 'A53', 'Blu', 'RNCCMN06M25F205S'),
('XzStAybW','Iphone', '12 Pro', 'Nero', 'RSOGMM04T62C351N');
--
-----------------------------------------------------------------------------------------------------
-- Creazione tabella `riparazione`
--
CREATE TABLE `riparazione` (
  `CODRIP` varchar(4) NOT NULL,
  `TIPO` varchar(45) DEFAULT NULL,
  `STATO` varchar(45) DEFAULT NULL,
  `SERIALE` varchar(8) DEFAULT NULL,
  `CODTECNICO` int DEFAULT NULL,
  PRIMARY KEY (`CODRIPARAZIONE`),
  KEY `SERIALE_idx` (`SERIALE`),
  KEY `CODTECNICO_idx` (`CODTECNICO`),
  CONSTRAINT `CODTECNICO` FOREIGN KEY (`CODTECNICO`) REFERENCES `tecnico` (`CODICETECNICO`),
  CONSTRAINT `SERIALE` FOREIGN KEY (`SERIALE`) REFERENCES `smartphone` (`SERIALE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
--
-- Popolamento tabella `riparazione`
--
INSERT INTO `negozio`.`riparazione` (`CODRIP`, `TIPO`, `STATO`, `SERIALE`, `CODTECNICO`) VALUES
(1000, 'Sostituzione Schermo', 'In Corso', 'zmPXJbp7', 70),
(1001, 'Sostituzione Batteria', 'In Corso', 'VEGwFaZi', 73),
(1002, 'Sostituzione Fotocamera', 'In Corso', 'nQZJXxKm', 71),
(1003, 'Sostituzione Touch Id', 'In Corso', '9MMoboWo', 74),
(1004, 'Sostituzione Jack Alimentazione', 'In Corso', 'XzStAybW', 72);
--
-----------------------------------------------------------------------------------------------------
-- Creazione tabella `ricevuta`
--
CREATE TABLE `ricevuta` (
  `CODRICEVUTA` int NOT NULL,
  `COSTORIPARAZIONE` int DEFAULT NULL,
  `CODRIPARAZIONE` int DEFAULT NULL,
  `SERIALESMARTPHONE` varchar(17) DEFAULT NULL,
  `CODICETECNICO` int DEFAULT NULL,
  `EMAILCLIENTE` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`CODRICEVUTA`),
  KEY `CODICETECNICO_idx` (`CODICETECNICO`),
  KEY `CODRIPARAZIONE_idx` (`CODRIPARAZIONE`),
  KEY `SERIALESMARTPHONE_idx` (`SERIALESMARTPHONE`),
  KEY `EMAILCLIENTE_idx` (`EMAILCLIENTE`),
  CONSTRAINT `CODICETECNICO` FOREIGN KEY (`CODICETECNICO`) REFERENCES `tecnico` (`CODICETECNICO`),
  CONSTRAINT `CODRIPARAZIONE` FOREIGN KEY (`CODRIPARAZIONE`) REFERENCES `riparazione` (`CODRIPARAZIONE`),
  CONSTRAINT `EMAILCLIENTE` FOREIGN KEY (`EMAILCLIENTE`) REFERENCES `cliente` (`EMAIL`),
  CONSTRAINT `SERIALESMARTPHONE` FOREIGN KEY (`SERIALESMARTPHONE`) REFERENCES `smartphone` (`SERIALE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
--
-- Popolamento tabella `ricevuta`
--
INSERT INTO `negozio`.`ricevuta` (`CODRICEVUTA`, `COSTORIPARAZIONE`, `CODRIPARAZIONE`, `SERIALESMARTPHONE`, `CODICETECNICO`, `EMAILCLIENTE`) VALUES
(10, 50, 1000, 'zmPXJbp7', 70, 'giovannadipalo@libero.it'),
(20, 70, 1001, 'VEGwFaZi', 71, 'giuseppelarussa@gmail.com'),
(30, 50, 1002, 'nQZJXxKm', 72, 'giacomocarrato@gmail.com'),
(40, 20, 1003, '9MMoboWo', 73, 'luigiiovino@gmail.com'),
(50, 20, 1004, 'XzStAybW', 74, 'carmendegiacom@gmail.com');
--
-----------------------------------------------------------------------------------------------------
-- Creazione tabella `categoria`
--
CREATE TABLE `negozio`.`categoria` (
  `NOME` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`NOME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ciENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci

--
-- Popolamento tabella `categoria`
--
INSERT INTO `negozio`.`categoria` (NOME) VALUES  
('Cavo'),
('Cover');
--
-----------------------------------------------------------------------------------------------------
-- Creazione tabella `accessori`
--
CREATE TABLE `negozio`.`accessori` (
`CODICE` INT NOT NULL,
`NOME` VARCHAR(45) NULL,
`MODELLO` VARCHAR(45) NULL,
`MARCA` VARCHAR(45) NULL,
`QUANTITA` VARCHAR(45) NULL,
`VENDUTI` VARCHAR(45) NULL,  
`PREZZO` decimal(5,0) DEFAULT NULL,
  PRIMARY KEY (`CODICE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
--
-- Popolamento tabella `accessori`
--
INSERT INTO `negozio`.`accessori` (CODICE, NOME, MODELLO, MARCA, QUANTITA, VENDUTI, PREZZO) VALUES 
(5001, 'Cover Bianco', '12', 'iPhone', 20, 3, 10),
(5002, 'Cover Verde', '12 Pro', 'iPhone', 20, 5, 13),
(5003, 'Cavo Ricarica', 'Lightning/USB', 'iPhone', 15, 6, 15),
(5004, 'Cover Lilla', '13 ProMax', 'iPhone', 20, 10, 15),
(5005, 'Cover Nero', 'S22', null, 20, 11, 11),
(5006, 'Cavo Ricarica', 'Lightning/USB Type-C', 'iPhone', 15, 6, 25),
(5007, 'Cover Blu', '21Ultra', 'Samsung', 20, 15, 15),
(5008, 'Cavo Ricarica', 'USB-Micro/USB', 'Samsung', 15, 7, 10),
(5009, 'Cavo Adattatore Cuffie', 'Lightning/Mini-Jack', 'iPhone', 15, 2, 10),
(5010, 'Cavo Adattatore Cuffie', 'USB Type-C/Mini-Jack', 'Samsung', 15, 1, 10);
--
-----------------------------------------------------------------------------------------------------
-- Creazione tabella `access_categ`
--
CREATE TABLE `access_categ` (
  `ACCESS` int NOT NULL,
  `CATEG` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`ACCESS`),
  KEY `CATEG_idx` (`CATEG`),
  CONSTRAINT `ACCESS` FOREIGN KEY (`ACCESS`) REFERENCES `accessori` (`CODICE`),
  CONSTRAINT `CATEG` FOREIGN KEY (`CATEG`) REFERENCES `categoria` (`NOME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
--
-- Popolamento tabella `access_categ`
--
INSERT INTO `negozio`.`accessori` (ACCESS, CATEG) VALUES 
(5001, 'Cover'),
(5002, 'Cover'),
(5003, 'Cavo'),
(5004, 'Cover'),
(5005, 'Cover'),
(5006, 'Cavo'),
(5007, 'Cover'),
(5008, 'Cavo'),
(5009, 'Cavo'),
(5010, 'Cavo');