<?php
// Tahap 1, membuat koneksi
$dbName = "inkomtek_kvnwj_smartleasing";
$serverName = "inkomtek.co.id";
$port = "2083";
$username = "inkomtek_root";
$password = "MultimediaNusantara";
try {
    $pdo = new PDO("mysql:hostname=$serverName;dbname=$dbName;port=$port", $username, $password);
} catch (PDOException $e) {
    echo "Connection failed: " . $e->getMessage();
}