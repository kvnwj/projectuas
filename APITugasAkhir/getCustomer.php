<?php
header('Content-type:application/json;charset=utf-8');
require "connection.php";

// Membuat Query SQL
$sql = "SELECT p.nama_produk as nama_produk, c.nama_depan as nama_depan, c.nama_belakang as nama_belakang, t.ID_transaksi as ID_transaksi FROM produk p, customer c, transaksi t WHERE t.status='WAIT' AND t.ID_produk = p.ID_produk AND t.ID_customer = c.ID_customer";

// Menjalankan Query SQL
$stmt = $pdo->prepare($sql);
$stmt -> execute();
$hasil = $stmt->fetchAll();

if ($hasil != null) {
    $response["data"] = array();
    foreach ($hasil as $row) {
        $t = array();
        $t["nama_produk"] = $row["nama_produk"];
        $t["nama_depan"] = $row["nama_depan"];
        $t["nama_belakang"] = $row["nama_belakang"];
        $t["ID_transaksi"] = $row["ID_transaksi"];
        array_push($response["data"], $t);
    }
    $response["success"] = 1;
    $response["message"] = "Data Transaksi dalam Status WAIT berhasil dibaca";
} else {
    $response["success"] = 0;
    $response["message"] = "Tidak ada data";
}
echo json_encode($response);
// Kevin Widjaja 00000027219
