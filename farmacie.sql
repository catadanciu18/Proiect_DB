-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jan 09, 2025 at 04:59 PM
-- Server version: 11.5.2-MariaDB
-- PHP Version: 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `farmacie`
--

-- --------------------------------------------------------

--
-- Table structure for table `abonament`
--

CREATE TABLE `abonament` (
  `id_abonament` int(11) NOT NULL,
  `id_pacient` int(11) DEFAULT NULL,
  `tip_abonament` varchar(20) DEFAULT NULL,
  `data_incepere` date DEFAULT NULL,
  `data_expirare` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Dumping data for table `abonament`
--

INSERT INTO `abonament` (`id_abonament`, `id_pacient`, `tip_abonament`, `data_incepere`, `data_expirare`) VALUES
(4, 2, '1', '2025-01-01', '2025-07-01'),
(5, 2, 'premiun', '2025-01-01', '2025-07-01');

-- --------------------------------------------------------

--
-- Table structure for table `medicamente`
--

CREATE TABLE `medicamente` (
  `id_medicament` int(11) NOT NULL,
  `denumire` varchar(50) DEFAULT NULL,
  `concentratie` varchar(50) DEFAULT NULL,
  `producator` varchar(50) DEFAULT NULL,
  `data_expirare` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Dumping data for table `medicamente`
--

INSERT INTO `medicamente` (`id_medicament`, `denumire`, `concentratie`, `producator`, `data_expirare`) VALUES
(1, 'Metamizol', '500mg', 'Algocalmin', '2025-12-31'),
(2, 'Ibuprofen', '400mg', 'Nurofen', '2026-06-15');

-- --------------------------------------------------------

--
-- Table structure for table `pacienti`
--

CREATE TABLE `pacienti` (
  `id_pacient` int(11) NOT NULL,
  `nume` varchar(50) DEFAULT NULL,
  `prenume` varchar(50) DEFAULT NULL,
  `nr_telefon` varchar(15) DEFAULT NULL,
  `data_nasterii` date DEFAULT NULL,
  `adresa` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Dumping data for table `pacienti`
--

INSERT INTO `pacienti` (`id_pacient`, `nume`, `prenume`, `nr_telefon`, `data_nasterii`, `adresa`) VALUES
(2, 'Danciu', 'Catalin', '0769741667', '2003-07-18', 'dragos voda 7b'),
(3, 'Bancos', 'Gabriel', '0766305496', '2003-12-23', 'aleea filaturii'),
(4, 'Vint', 'Alexandru', '0760293591', '2004-02-01', 'regele mihai 1 40'),
(6, 'Danciu', 'Rares', '0784813566', '2003-12-11', 'dragos voda 7b');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `abonament`
--
ALTER TABLE `abonament`
  ADD PRIMARY KEY (`id_abonament`),
  ADD KEY `abonament_ibfk_1` (`id_pacient`);

--
-- Indexes for table `medicamente`
--
ALTER TABLE `medicamente`
  ADD PRIMARY KEY (`id_medicament`);

--
-- Indexes for table `pacienti`
--
ALTER TABLE `pacienti`
  ADD PRIMARY KEY (`id_pacient`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `abonament`
--
ALTER TABLE `abonament`
  MODIFY `id_abonament` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `medicamente`
--
ALTER TABLE `medicamente`
  MODIFY `id_medicament` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `pacienti`
--
ALTER TABLE `pacienti`
  MODIFY `id_pacient` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `abonament`
--
ALTER TABLE `abonament`
  ADD CONSTRAINT `abonament_ibfk_1` FOREIGN KEY (`id_pacient`) REFERENCES `pacienti` (`id_pacient`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
