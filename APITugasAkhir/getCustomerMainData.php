<?php
header('Content-type:application/json;charset=utf-8');
require "connection.php";

if (isset($_POST['ID_customer'])) {
    $id = $_POST['ID_customer'];
    $params = array(
        ":id" => $id
    );
    
    // Membuat Query SQL untuk mengambil data profil customer berdasarkan ID
    $sqlProfil = "SELECT nama_depan, nama_belakang, jenis_kelamin, nik, alamat, tempat_lahir, tanggal_lahir, no_telp FROM customer WHERE ID_customer = :id";
    // Membuat Query SQL untuk mengambil data transaksi customer berdasarkan ID
    $sqlTransaksi = "SELECT t.ID_transaksi, t.uang_muka, t.tenor, t.angsuran_per_bulan, t.status, p.nama_produk, p.harga_otr, p.bunga, e.nama_depan, e.nama_belakang FROM transaksi t, produk p, employee e WHERE t.ID_produk = p.ID_produk AND t.ID_employee = e.id_employee AND t.ID_customer = :id";
    // Membuat Query SQL untuk mengambil detil produk
    $sqlProduk = "SELECT * from produk";

    // Menjalankan Query SQL
    $stmtProfil = $pdo->prepare($sqlProfil);
    $stmtTransaksi = $pdo->prepare($sqlTransaksi);
    $stmtProduk = $pdo->prepare($sqlProduk);

    $stmtProfil -> execute($params);
    $stmtTransaksi -> execute($params);
    $stmtProduk -> execute();

    // Ambil Data dari Database
    if ($hasilTransaksi = $stmtTransaksi->fetchAll()) {
        // Buat Response
        $response["credit_list"] = array();
        foreach ($hasilTransaksi as $row) {
            // Ambil data dari hasilTransaksi
            $r = array();
            $r["ID_transaksi"] = $row["ID_transaksi"];
            $r["uang_muka"] = $row["uang_muka"];
            $r["tenor"] = $row["tenor"];
            $r["angsuran_per_bulan"] = $row["angsuran_per_bulan"];
            $r["status"] = $row["status"];
            $r["nama_produk"] = $row["nama_produk"];
            $r["harga_otr"] = $row["harga_otr"];
            $r["bunga"] = $row["bunga"];
            $r["nama_depan_emp"] = $row["nama_depan"];
            $r["nama_belakang_emp"] = $row["nama_belakang"];
            array_push($response["credit_list"], $r);
        }
        $response["transaksi"] = "ada";
    } else {
        $response['transaksi'] = "kosong";
    }

    if ($hasilProfil = $stmtProfil->fetch()) {
        // Ambil data dari hasilProfil
        $p = array();
        $p["nama_depan_cust"] = $hasilProfil["nama_depan"];
        $p["nama_belakang_cust"] = $hasilProfil["nama_belakang"];
        $p["jenis_kelamin"] = $hasilProfil["jenis_kelamin"];
        $p["nik"] = $hasilProfil["nik"];
        $p["alamat"] = $hasilProfil["alamat"];
        $p["tempat_lahir"] = $hasilProfil["tempat_lahir"];
        $p["tanggal_lahir"] = $hasilProfil["tanggal_lahir"];
        $p["no_telp"] = $hasilProfil["no_telp"];
        $response["profile"] = $p;
    }

    // Code untuk Ambil data produk
    if ($hasilProduk = $stmtProduk -> fetchAll()) {
        $response["product_list"] = array();
        foreach ($hasilProduk as $row) {
            // Ambil data dari hasilProduk
            $r = array();
            $r["ID_produk"] = $row["ID_produk"];
            $r["nama_produk"] = $row["nama_produk"];
            $r["harga_otr"] = $row["harga_otr"];
            $r["bunga"] = $row["bunga"];
            array_push($response["product_list"], $r);
        }
    }

    $response["success"] = 1;
    $response["message"] = "Data CustomerMain berhasil dibaca";
    echo json_encode($response);
} else {
    // Code bila tidak ditemukan data
    $response["success"] = -2;
    $response["message"] = "Data Kosong";
    echo json_encode($response);
}
