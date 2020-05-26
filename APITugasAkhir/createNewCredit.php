<?php
header('Content-type:application/json;charset=utf-8');
require "connection.php";

if (isset($_POST['ID_produk']) && isset($_POST['ID_customer'])) {
    // Mengambil data dari POST request
    $ID_produk = $_POST['ID_produk'];
    $ID_customer = $_POST['ID_customer'];
    $uang_muka = $_POST['uang_muka'];
    $tenor = $_POST['tenor'];
    $angsuran_per_bulan = $_POST['angsuran_per_bulan'];

    // Buat Parameter
    $params = array(
        ":ID_produk" => $ID_produk,
        ":ID_customer" => $ID_customer,
        ":uang_muka" => $uang_muka,
        ":tenor" => $tenor,
        ":angsuran_per_bulan" => $angsuran_per_bulan
    );

    // Buat SQL Query
    $sql = "INSERT INTO transaksi(ID_transaksi, ID_produk, ID_customer, ID_employee, uang_muka, tenor, angsuran_per_bulan, status) VALUES(null, :ID_produk, :ID_customer, 2, :uang_muka, :tenor, :angsuran_per_bulan, 'WAIT')";

    // Jalankan SQL Query
    $stmt = $pdo->prepare($sql);
    if ($stmt -> execute($params)) {
        $response["success"] = 1;
        $response["message"] = "Sukses Buat Transaksi Kredit Baru";
        echo json_encode($response);
    } else {
        $response["success"] = 0;
        $response["message"] = "Gagal Buat Transaksi Kredit Baru";
        echo json_encode($response);
    }
} else {
    $response["success"] = -1;
    $response["message"] = "Data Kosong";
    echo json_encode($response);
}
