<?php
header('Content-type:application/json;charset=utf-8');
require "connection.php";

if (isset($_POST['username']) && isset($_POST['password'])) {
    
        // Mengambil data dari POST request
    $username = $_POST['username'];
    $password = password_hash($_POST['password'], PASSWORD_BCRYPT);
    $nama_depan = $_POST['nama_depan'];
    $nama_belakang = $_POST['nama_belakang'];
    $jenis_kelamin = $_POST['jenis_kelamin'];
    $nik = $_POST['nik'];
    $alamat = $_POST['alamat'];
    $tempat_lahir = $_POST['tempat_lahir'];
    $tanggal_lahir = $_POST['tanggal_lahir'];
    $no_telp = $_POST['no_telp'];
    
    // Buat Parameter
    $params = array(
        ":username" => $username,
        ":password" => $password,
        ":nama_depan" => $nama_depan,
        ":nama_belakang" => $nama_belakang,
        ":jenis_kelamin" => $jenis_kelamin,
        ":nik" => $nik,
        ":alamat" => $alamat,
        ":tempat_lahir" => $tempat_lahir,
        ":tanggal_lahir" => $tanggal_lahir,
        ":no_telp" => $no_telp
    );
    
    // Buat SQL Query
    $sql = "INSERT INTO employee (username, password, nama_depan, nama_belakang, jenis_kelamin, nik, alamat, tempat_lahir, tanggal_lahir, no_telp) VALUES (:username, :password, :nama_depan, :nama_belakang, :jenis_kelamin, :nik, :alamat, :tempat_lahir, :tanggal_lahir, :no_telp)";
    
    // Jalankan SQL Query
    $stmt = $pdo->prepare($sql);
    if ($stmt -> execute($params)) {
        $response["success"] = 1;
        $response["message"] = "Sukses Buat Akun Baru";
        echo json_encode($response);
    } else {
        $response["success"] = 0;
        $response["message"] = "Gagal Buat Akun Baru";
        echo json_encode($response);
    }
} else {
    $response["success"] = -1;
    $response["message"] = "Data Kosong";
    echo json_encode($response);
}
