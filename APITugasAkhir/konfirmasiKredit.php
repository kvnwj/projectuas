<?php
header('Content-type:application/json;charset=utf-8');
require "connection.php";

if (isset($_POST['ID_employee']) && isset($_POST['status']) && isset($_POST["ID_transaksi"])) {
    // Mengambil data dari POST request
    $ID_employee = $_POST['ID_employee'];
    $status = $_POST['status'];
    $ID_transaksi = $_POST['ID_transaksi'];
    
    // Buat Parameter
    $params = array(
        ":ID_employee" => $ID_employee,
        ":status" => $status,
        ":ID_transaksi" => $ID_transaksi
    );

    // Buat SQL Query
    $sql = "UPDATE transaksi SET status = :status, ID_employee = :ID_employee WHERE ID_transaksi = :ID_transaksi";

    // Jalankan SQL Query
    $stmt = $pdo->prepare($sql);
    if ($stmt -> execute($params)) {
        $response["success"] = 1;
        $response["message"] = "Sukses Konfirmasi Kredit";
        echo json_encode($response);
    } else {
        $response["success"] = 0;
        $response["message"] = "Gagal Konfirmasi Kredit";
        echo json_encode($response);
    }
} else {
    $response["success"] = -1;
    $response["message"] = "Data Kosong";
    echo json_encode($response);
}