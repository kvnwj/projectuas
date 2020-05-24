-- phpMyAdmin SQL Dump
-- version 4.9.4
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: May 24, 2020 at 04:49 PM
-- Server version: 10.1.44-MariaDB
-- PHP Version: 7.3.6

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `inkomtek_kvnwj_smartleasing`
--

-- --------------------------------------------------------

--
-- Table structure for table `customer`
--

CREATE TABLE `customer` (
  `ID_customer` int(5) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(100) NOT NULL,
  `nama_depan` varchar(50) NOT NULL,
  `nama_belakang` varchar(50) NOT NULL,
  `jenis_kelamin` enum('L','P','X') NOT NULL,
  `nik` varchar(20) NOT NULL,
  `alamat` varchar(255) NOT NULL,
  `tempat_lahir` varchar(50) NOT NULL,
  `tanggal_lahir` date NOT NULL,
  `no_telp` varchar(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `customer`
--

INSERT INTO `customer` (`ID_customer`, `username`, `password`, `nama_depan`, `nama_belakang`, `jenis_kelamin`, `nik`, `alamat`, `tempat_lahir`, `tanggal_lahir`, `no_telp`) VALUES
(1, 'dummyCustomer', '12345', 'dummy', 'customer', 'L', '1234567890123456', 'Jakarta', 'Banten', '2000-04-02', '081234567890'),
(6, 'kvnwj', '$2y$10$Or16I5T4KN6oMeOzP6YcR.CKSiZI4STVSACp2XthbE/2h7sf5Iyh2', 'Kevin', 'Widjaja', 'L', '1234567890', 'Jakarta', 'Jakarta', '2020-03-11', '081234567890');

-- --------------------------------------------------------

--
-- Table structure for table `employee`
--

CREATE TABLE `employee` (
  `id_employee` int(5) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(100) NOT NULL,
  `nama_depan` varchar(50) NOT NULL,
  `nama_belakang` varchar(50) NOT NULL,
  `jenis_kelamin` enum('L','P','X') NOT NULL,
  `nik` varchar(20) NOT NULL,
  `alamat` varchar(255) NOT NULL,
  `tempat_lahir` varchar(50) NOT NULL,
  `tanggal_lahir` date NOT NULL,
  `no_telp` varchar(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `employee`
--

INSERT INTO `employee` (`id_employee`, `username`, `password`, `nama_depan`, `nama_belakang`, `jenis_kelamin`, `nik`, `alamat`, `tempat_lahir`, `tanggal_lahir`, `no_telp`) VALUES
(1, 'dummyEmployee', '12345', 'dummy', 'employee', 'L', '6543210987654321', 'Makassar', 'Papua', '2000-03-05', '087654321234'),
(2, 'admin', '$2y$10$VBMQjEbbWCpkU99PyQ.0YO7ShA39cQ255j1.OIPHNbt8hz9vRf7Me', 'admin', 'admin', 'L', '1234567890', 'Jakarta', 'Jakarta', '2020-05-01', '081234567890');

-- --------------------------------------------------------

--
-- Table structure for table `produk`
--

CREATE TABLE `produk` (
  `ID_produk` int(5) NOT NULL,
  `nama_produk` varchar(50) NOT NULL,
  `harga_otr` double NOT NULL,
  `bunga` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `produk`
--

INSERT INTO `produk` (`ID_produk`, `nama_produk`, `harga_otr`, `bunga`) VALUES
(1, 'Wuling Almaz', 300000000, 0.05),
(2, 'Nissan Terra', 500000000, 0.06),
(5, 'Toyota Rush', 276000000, 0.05),
(6, 'Suzuki Jimny', 385000000, 0.075),
(7, 'Toyota Fortuner', 650000000, 0.08);

-- --------------------------------------------------------

--
-- Table structure for table `transaksi`
--

CREATE TABLE `transaksi` (
  `ID_transaksi` int(5) NOT NULL,
  `ID_produk` int(5) NOT NULL,
  `ID_customer` int(5) NOT NULL,
  `ID_employee` int(5) NOT NULL,
  `uang_muka` double NOT NULL,
  `tenor` int(5) NOT NULL,
  `angsuran_per_bulan` double NOT NULL,
  `status` enum('WAIT','OK','REJECTED') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `transaksi`
--

INSERT INTO `transaksi` (`ID_transaksi`, `ID_produk`, `ID_customer`, `ID_employee`, `uang_muka`, `tenor`, `angsuran_per_bulan`, `status`) VALUES
(2, 1, 1, 1, 10000000, 12, 9999, 'WAIT'),
(3, 2, 1, 1, 22222222, 12, 8888, 'WAIT'),
(4, 2, 1, 1, 33333333, 12, 7777, 'WAIT'),
(5, 5, 6, 1, 10000000, 12, 1500000, 'WAIT'),
(6, 7, 6, 2, 33333333, 12, 77776, 'WAIT');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `customer`
--
ALTER TABLE `customer`
  ADD PRIMARY KEY (`ID_customer`),
  ADD UNIQUE KEY `username` (`username`);

--
-- Indexes for table `employee`
--
ALTER TABLE `employee`
  ADD PRIMARY KEY (`id_employee`),
  ADD UNIQUE KEY `username` (`username`);

--
-- Indexes for table `produk`
--
ALTER TABLE `produk`
  ADD PRIMARY KEY (`ID_produk`);

--
-- Indexes for table `transaksi`
--
ALTER TABLE `transaksi`
  ADD PRIMARY KEY (`ID_transaksi`),
  ADD KEY `ID_produk` (`ID_produk`),
  ADD KEY `ID_customer` (`ID_customer`),
  ADD KEY `ID_employee` (`ID_employee`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `customer`
--
ALTER TABLE `customer`
  MODIFY `ID_customer` int(5) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `employee`
--
ALTER TABLE `employee`
  MODIFY `id_employee` int(5) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `produk`
--
ALTER TABLE `produk`
  MODIFY `ID_produk` int(5) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `transaksi`
--
ALTER TABLE `transaksi`
  MODIFY `ID_transaksi` int(5) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `transaksi`
--
ALTER TABLE `transaksi`
  ADD CONSTRAINT `transaksi_ibfk_1` FOREIGN KEY (`ID_produk`) REFERENCES `produk` (`ID_produk`),
  ADD CONSTRAINT `transaksi_ibfk_2` FOREIGN KEY (`ID_employee`) REFERENCES `employee` (`id_employee`),
  ADD CONSTRAINT `transaksi_ibfk_3` FOREIGN KEY (`ID_customer`) REFERENCES `customer` (`ID_customer`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
