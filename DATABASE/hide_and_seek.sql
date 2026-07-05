-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Dec 27, 2025 at 01:57 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `hide_and_seek`
--
CREATE DATABASE IF NOT EXISTS `hide_and_seek` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `hide_and_seek`;

-- --------------------------------------------------------

--
-- Table structure for table `tbenefit`
--

CREATE TABLE `tbenefit` (
  `username` varchar(50) NOT NULL,
  `skor` int(11) DEFAULT 0,
  `peluru_meleset` int(11) DEFAULT 0,
  `sisa_peluru` int(11) DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tbenefit`
--

INSERT INTO `tbenefit` (`username`, `skor`, `peluru_meleset`, `sisa_peluru`) VALUES
('Consistent', 700, 7000, 40),
('Courage', 800, 1000, 80),
('Creativity', 1000, 3000, 100),
('l', 200, 301, 238),
('lalala', 300, 93, 42),
('M', 700, 140, 78),
('mmm', 200, 143, 105),
('n', 600, 95, 50),
('tes1', 0, 8, 1),
('tes2', 0, 15, 6),
('tes3', 200, 136, 79),
('tes4', 400, 164, 80),
('tes5', 400, 67, 43),
('tes6', 100, 91, 57),
('tes7', 100, 62, 33),
('tes8', 500, 91, 57),
('tes9', 600, 125, 59),
('test', 0, 111, 56),
('uma', 400, 25, 2),
('Umar', 0, 13, 7),
('X', 0, 18, 12),
('xer', 100, 76, 42),
('xeramu', 300, 129, 92);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `tbenefit`
--
ALTER TABLE `tbenefit`
  ADD PRIMARY KEY (`username`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
