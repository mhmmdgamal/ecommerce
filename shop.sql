-- phpMyAdmin SQL Dump
-- version 4.7.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Sep 25, 2018 at 03:19 PM
-- Server version: 10.1.26-MariaDB
-- PHP Version: 7.1.8

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `shop`
--

-- --------------------------------------------------------

--
-- Table structure for table `categories`
--

CREATE TABLE `categories` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `description` text NOT NULL,
  `parent` int(11) DEFAULT NULL,
  `ordering` int(11) DEFAULT NULL,
  `visibility` tinyint(4) NOT NULL DEFAULT '0',
  `allow_comments` tinyint(4) NOT NULL DEFAULT '0',
  `allow_ads` tinyint(4) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `categories`
--

INSERT INTO `categories` (`id`, `name`, `description`, `parent`, `ordering`, `visibility`, `allow_comments`, `allow_ads`) VALUES
(9, 'Computers', 'Computers Item', 0, 2, 1, 0, 0),
(10, 'Cell Phones', 'Cell Phones', 0, 3, 0, 0, 0),
(11, 'Clothing', 'Clothing And Fashion', 8, 4, 0, 0, 0),
(12, 'Tools', 'Home Tools', 0, 5, 0, 0, 0),
(14, 'Blackberry', 'Blackberry Phones', 10, 2, 0, 0, 0),
(18, 'tes', 'test', NULL, 5, 0, 0, 0);

-- --------------------------------------------------------

--
-- Table structure for table `comments`
--

CREATE TABLE `comments` (
  `id` int(11) NOT NULL,
  `comment` text NOT NULL,
  `status` tinyint(4) NOT NULL,
  `add_date` date NOT NULL,
  `item_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `comments`
--

INSERT INTO `comments` (`id`, `comment`, `status`, `add_date`, `item_id`, `user_id`) VALUES
(2, 'good Item Thanks so much1', 1, '2016-05-11', 18, 28),
(7, 'Very Cool', 1, '2016-06-17', 18, 25),
(8, 'Very Nice This Is The Second Comment', 1, '2016-06-17', 18, 25),
(9, 'This Is Me Turki', 1, '2016-06-17', 18, 30),
(10, 'coooooooooool', 0, '2018-09-24', 18, 1),
(11, 'coooooooooool', 0, '2018-09-24', 18, 1),
(12, 'coooooooooool \r\nnice\r\n', 0, '2018-09-24', 30, 1);

-- --------------------------------------------------------

--
-- Table structure for table `items`
--

CREATE TABLE `items` (
  `id` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `description` text,
  `price` varchar(255) DEFAULT NULL,
  `add_date` date DEFAULT NULL,
  `country_made` varchar(255) DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `rating` smallint(6) DEFAULT NULL,
  `approve` tinyint(4) DEFAULT '0',
  `category_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `tags` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `items`
--

INSERT INTO `items` (`id`, `name`, `description`, `price`, `add_date`, `country_made`, `image`, `status`, `rating`, `approve`, `category_id`, `user_id`, `tags`) VALUES
(18, 'Network Cable', 'Cat 9 Network Cable', '15', '2016-05-09', 'USA', NULL, '1', 0, 1, 9, 25, ''),
(19, 'Game', 'Test Game For Item', '120', '2016-06-17', 'USA', '', '2', 0, 1, 9, 30, ''),
(20, 'iPhone 6s1', 'iPhone 6s Very Cool Phone', '300', '2016-06-17', 'USA', NULL, '2', 0, 1, 10, 30, ''),
(21, 'Hammer', 'A Very Good Iron Hammer', '30', '2016-06-17', 'China', '', '3', 0, 1, 12, 30, ''),
(24, 'Testing Item', 'Testing Description Testing Description', '120', '2016-06-17', 'Korea', '', '3', 0, 1, 10, 30, ''),
(25, 'Osama', 'Osama Osama Elzero Elzero', '100', '2016-06-17', 'Egypt', '', '3', 0, 0, 10, 30, ''),
(26, '12121212', '33333333333', '33333', '2016-06-17', '333333', '', '2', 0, 1, 11, 30, ''),
(27, 'My Item', 'My New Description', '12', '2016-06-17', 'Saudi Arabia', '', '1', 0, 1, 10, 30, 'Test, Discount, Elzero'),
(28, 'infinix hot 4', 'ram=3 gb , memory = 32gb', '250$', '2018-09-19', 'Egypt', NULL, '2', 0, 1, 10, 1, 'infinix , mobile , phone '),
(29, 'car marcides ', '4 wheels ,4 seats , color: black , ', '1000$', '2018-09-21', 'canda', NULL, '2', 0, 1, 18, 38, 'cars '),
(30, 'labtop hp', 'ram=8 , screen = 15.6 inch', '400$', '2018-09-22', 'canda', NULL, '3', 0, 1, 9, 1, 'hp, labtop, computer'),
(31, 'xiamei rdemy not 4', 'ram=1 gb , memory = 16 gb', '100 $', '2018-09-22', 'china', NULL, '1', 0, 1, 10, 1, 'xiomei, mobile'),
(32, 'item 2', 'description test', '1000$', '2018-09-22', 'Egypt12', NULL, '4', 0, 1, 12, 38, 'item'),
(33, 'item3', 'Hand Made Items >>', '100 $', '2018-09-22', 'candan', NULL, '3', 0, 0, 10, 38, 'item, tag');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL COMMENT 'To Identify User',
  `name` varchar(255) NOT NULL COMMENT 'Username To Login',
  `password` varchar(255) NOT NULL COMMENT 'Password To Login',
  `email` varchar(255) NOT NULL,
  `full_name` varchar(255) DEFAULT NULL,
  `group_id` int(11) DEFAULT '0' COMMENT 'Identify User Group',
  `trust_status` int(11) DEFAULT '0' COMMENT 'Seller Rank',
  `register_status` int(11) DEFAULT '0' COMMENT 'User Approval',
  `date` date DEFAULT NULL,
  `avatar` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `name`, `password`, `email`, `full_name`, `group_id`, `trust_status`, `register_status`, `date`, `avatar`) VALUES
(1, 'mhmmd', '1234', 'mhmmd.gamal@yahoo.com', 'Mohamed Gamal', 1, 0, 1, '2016-05-06', ''),
(25, 'Gamal', '601f1889667efaebb33b8c12572835da3f027f78', 'Gamal@mmm.com', 'Gamal Ahmed', 0, 0, 1, '2016-05-06', ''),
(26, 'Sameh', '601f1889667efaebb33b8c12572835da3f027f78', 's123@s.com', 'Sameh Ahmed', 0, 0, 1, '2016-05-08', ''),
(27, 'Application', '40bd001563085fc35165329ea1ff5c5ecbdbbeef', 'app@app.com', 'Application API', 0, 0, 1, '2016-05-11', ''),
(28, 'Khalid', '601f1889667efaebb33b8c12572835da3f027f78', 'kh@kh.com', 'Khalid Ahmed', 0, 0, 1, '2016-05-04', ''),
(30, 'Turki1', '', 'Turki@turki.com', 'hgfhg', 0, 0, 1, '2016-06-16', ''),
(31, 'ghjghjghjgjhgj', '', 'hg@khk.ghjg', 'jghhjgjgjhg', 0, 0, 1, '2018-09-06', NULL),
(32, 'cghgf', 'ghhgfhjhgf', 'dffsd@fg.ghjhg', 'fdgfghgfh', 0, 0, 1, '2018-09-10', NULL),
(33, 'jhkjhkjh', 'jhkjhkjh', 'jhkjh@jjkl.kjklj', 'lkjkljlk', 0, 0, 1, '2018-09-11', NULL),
(38, 'ahmed', 'ahmed', 'ahmedRamadan@gmai.com', 'ahmed ', 0, 0, 1, '2018-09-21', NULL),
(39, 'tarek', 'ahmed', 'tarektarek@gmai.com', 'tarek', 0, 0, 1, '2018-09-21', NULL),
(40, 'Momen', 'ahmed', 'MomenHesham@gmai.com', 'Momen', 0, 0, 1, '2018-09-21', NULL),
(41, 'hamdy', 'ahmed', 'hamdyhamdy@gmai.com', 'hamdy', 0, 0, 1, '2018-09-21', NULL),
(42, 'fdway', 'ahmed', 'ahmedRamadan@gmai.com', 'fdway', 0, 0, 1, '2018-09-21', NULL),
(43, 'ahmedTELEB', 'ahmed', 'ahmedRamadantELEB@gmai.com', NULL, 0, 0, 0, '2018-09-23', NULL),
(44, 'ahmedrrr', 'ahmed', 'ahmedRamadantELEB@gmai.com', NULL, 0, 0, 0, '2018-09-23', NULL),
(45, 'ahmed mohmed', 'ahmed', 'ahmedRamadana@gmai.com', NULL, 0, 0, 0, '2018-09-23', NULL),
(46, 'aderhman', 'ahmed', 'abderhman@gmai.com', 'aderhman', 0, 0, 0, '2018-09-23', NULL);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `categories`
--
ALTER TABLE `categories`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `Name` (`name`);

--
-- Indexes for table `comments`
--
ALTER TABLE `comments`
  ADD PRIMARY KEY (`id`),
  ADD KEY `items_comment` (`item_id`),
  ADD KEY `comment_user` (`user_id`);

--
-- Indexes for table `items`
--
ALTER TABLE `items`
  ADD PRIMARY KEY (`id`),
  ADD KEY `member_1` (`user_id`),
  ADD KEY `cat_1` (`category_id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `Username` (`name`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `categories`
--
ALTER TABLE `categories`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;
--
-- AUTO_INCREMENT for table `comments`
--
ALTER TABLE `comments`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;
--
-- AUTO_INCREMENT for table `items`
--
ALTER TABLE `items`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=34;
--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'To Identify User', AUTO_INCREMENT=47;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `comments`
--
ALTER TABLE `comments`
  ADD CONSTRAINT `comment_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `items_comment` FOREIGN KEY (`item_id`) REFERENCES `items` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `items`
--
ALTER TABLE `items`
  ADD CONSTRAINT `cat_1` FOREIGN KEY (`category_id`) REFERENCES `categories` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `user_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
