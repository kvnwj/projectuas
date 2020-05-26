<?php
header('Content-type:application/json;charset=utf-8');
require "connection.php";

if (isset($_POST['ID_transaksi'])) {
    // Mengambil data dari POST request
    $ID_transaksi = $_POST['ID_transaksi'];
    
    // Buat Parameter
    $params = array(
        ":ID_transaksi" => $ID_transaksi
    );

    // Buat SQL Query
    $sql = "DELETE FROM transaksi WHERE ID_transaksi = :ID_transaksi";

    // Jalankan SQL Query
    $stmt = $pdo->prepare($sql);
    if ($stmt -> execute($params)) {
        $response["success"] = 1;
        $response["message"] = "Sukses Hapus Kredit";
        echo json_encode($response);
    } else {
        $response["success"] = 0;
        $response["message"] = "Gagal Hapus Kredit";
        echo json_encode($response);
    }
} else {
    $response["success"] = -1;
    $response["message"] = "Data Kosong";
    echo json_encode($response);
}
