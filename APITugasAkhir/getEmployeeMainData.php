<?php
header('Content-type:application/json;charset=utf-8');
require "connection.php";

if (isset($_POST['ID_employee'])) {
    $id = $_POST['ID_employee'];
    $params = array(
        ":id" => $id
    );

    // Membuat Query SQL untuk mengambil data profil Employee
    $sqlProfil = "SELECT nama_depan, nama_belakang, jenis_kelamin, nik, alamat, tempat_lahir, tanggal_lahir, no_telp FROM employee WHERE ID_employee = :id";

    // Membuat Query SQL untuk mengambil data transaksi customer berstatus WAIT
    $sqlTransaksi = "SELECT c.nama_depan as nama_depan_cust, c.nama_belakang as nama_belakang_cust, c.nik, c.alamat, c.tempat_lahir, c.tanggal_lahir, c.no_telp, c.jenis_kelamin, p.nama_produk, p.harga_otr, t.uang_muka, t.tenor, p.bunga, t.angsuran_per_bulan, t.status, t.ID_transaksi
    FROM customer c, produk p, transaksi t
    WHERE c.ID_customer = t.ID_customer AND p.ID_produk = t.ID_produk";

    // Menjalankan Query SQL
    $stmtTransaksi = $pdo->prepare($sqlTransaksi);
    $stmtProfil = $pdo->prepare($sqlProfil);

    $stmtTransaksi -> execute();
    $stmtProfil -> execute($params);

    // Ambil data dari Database
    if ($hasilTransaksi = $stmtTransaksi->fetchAll()) {
        $response["dataTransaksi"] = array();
        foreach ($hasilTransaksi as $row) {
            $t = array();
            $t["nama_depan_cust"] = $row["nama_depan_cust"];
            $t["nama_belakang_cust"] = $row["nama_belakang_cust"];
            $t["nik"] = $row["nik"];
            $t["alamat"] = $row["alamat"];
            $t["tempat_lahir"] = $row["tempat_lahir"];
            $t["tanggal_lahir"] = $row["tanggal_lahir"];
            $t["no_telp"] = $row["no_telp"];
            $t["jenis_kelamin"] = $row["jenis_kelamin"];
            $t["nama_produk"] = $row["nama_produk"];
            $t["harga_otr"] = $row["harga_otr"];
            $t["uang_muka"] = $row["uang_muka"];
            $t["tenor"] = $row["tenor"];
            $t["bunga"] = $row["bunga"];
            $t["angsuran_per_bulan"] = $row["angsuran_per_bulan"];
            $t["status"] = $row["status"];
            $t["ID_transaksi"] = $row["ID_transaksi"];
            array_push($response["dataTransaksi"], $t);
        }
        $response["adaTransaksi"] = "ada";
    } else {
        $response["adaTransaksi"] = "tidak";
    }

    if ($hasilProfil = $stmtProfil->fetch()) {
        // Ambil data dari hasilProfil
        $p = array();
        $p["nama_depan_emp"] = $hasilProfil["nama_depan"];
        $p["nama_belakang_emp"] = $hasilProfil["nama_belakang"];
        $p["jenis_kelamin"] = $hasilProfil["jenis_kelamin"];
        $p["nik"] = $hasilProfil["nik"];
        $p["alamat"] = $hasilProfil["alamat"];
        $p["tempat_lahir"] = $hasilProfil["tempat_lahir"];
        $p["tanggal_lahir"] = $hasilProfil["tanggal_lahir"];
        $p["no_telp"] = $hasilProfil["no_telp"];
        $response["profile"] = $p;
    }
    
    $response["success"] = 1;
    $response["message"] = "Data EmployeeMain berhasil dibaca";
    echo json_encode($response);
} else {
    // Code bila tidak ditemukan data
    $response["success"] = -2;
    $response["message"] = "Data Kosong";
    echo json_encode($response);
}
