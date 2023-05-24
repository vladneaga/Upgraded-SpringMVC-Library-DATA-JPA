-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: May 24, 2023 at 04:29 AM
-- Server version: 8.0.30
-- PHP Version: 7.2.34

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `spring_mvc_app`
--

-- --------------------------------------------------------

--
-- Table structure for table `book`
--

CREATE TABLE `book` (
  `id` bigint UNSIGNED NOT NULL,
  `title` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `author` varchar(20) NOT NULL,
  `year` int NOT NULL,
  `person_id` bigint UNSIGNED DEFAULT '1'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `book`
--

INSERT INTO `book` (`id`, `title`, `author`, `year`, `person_id`) VALUES
(26, 'A Tale of Two Cities', 'Charles Dickens ', 1859, 9),
(27, 'The Little Prince', 'A. de Saint-Exupery', 1943, 9),
(28, 'Dream of the Chamber', 'Cao Xueqin', 1791, 11),
(29, 'The Hobbit', 'J.R.R. Tolkien', 1937, 10),
(30, 'The Da Vinci Code', 'Dan Brown', 2003, 14),
(31, 'Heidi', 'Johanna Spyri', 1880, 12),
(32, 'Lolita', 'Vladimir Nabokov', 1955, 10),
(33, 'The Hite Report', 'Shere Hite', 1976, 20),
(44, 'To Kill a Mockingbird', 'Harper Lee', 1960, 9),
(45, '1984', 'George Orwell', 1949, 20),
(46, 'Pride and Prejudice', 'Jane Austen', 1813, 11),
(47, 'The Great Gatsby', 'F. Scott Fitzgerald', 1925, 9),
(48, 'The Catcher in the Rye', 'J.D. Salinger ', 1951, 11),
(49, 'Moby-Dick', 'Herman Melville', 1851, 12),
(50, 'The Lord of the Rings', 'J.R.R. Tolkien', 1955, NULL),
(51, 'Brave New World', 'Aldous Huxley', 1932, 21);

-- --------------------------------------------------------

--
-- Table structure for table `person`
--

CREATE TABLE `person` (
  `id` bigint UNSIGNED NOT NULL,
  `name` varchar(20) NOT NULL,
  `age` int NOT NULL,
  `email` varchar(30) NOT NULL,
  `address` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `person`
--

INSERT INTO `person` (`id`, `name`, `age`, `email`, `address`) VALUES
(1, 'NONE', 0, 'NONE@', 'NONE'),
(9, 'Tim Jones', 22, 'timjones@gmail.com', 'Heilbronn, Germany, 740720'),
(10, 'Henry Gilbert', 33, 'henrygilber@gmail.com', 'NewYork, USA, 100010'),
(11, 'Grigg Harris', 19, 'harrisgrig@gmail.com', 'Ohatchee, USA, 362710'),
(12, 'Jensen Andersen', 49, 'jensenandersen@gmail.com', 'Copenhagen, Denmark, 105000'),
(14, 'Asahi Hiroshi', 38, 'asashihiroshi@gmail.com', 'Tokyo, Japan, 100005'),
(20, 'Vlad Neaga', 19, 'vlad.neag40@gmail.com', 'Heilbronn, Germany, 999999'),
(21, 'Jim Peter', 1978, 'jimpeter@mail.com', 'London, UK, 100010');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `book`
--
ALTER TABLE `book`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `id` (`id`),
  ADD KEY `Txt` (`person_id`);

--
-- Indexes for table `person`
--
ALTER TABLE `person`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `id` (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `book`
--
ALTER TABLE `book`
  MODIFY `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=52;

--
-- AUTO_INCREMENT for table `person`
--
ALTER TABLE `person`
  MODIFY `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=23;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `book`
--
ALTER TABLE `book`
  ADD CONSTRAINT `Txt` FOREIGN KEY (`person_id`) REFERENCES `person` (`id`) ON DELETE SET NULL;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
