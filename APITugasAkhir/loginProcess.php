<?php
header('Content-type:application/json;charset=utf-8');
require 'connection.php';

if (isset($_POST['username']) && isset($_POST['password'])) {
    // Ambil data dari POST Request
    $username = $_POST['username'];
    $password = $_POST['password'];

    // Buat Parameter
    $params = array(
        ":username"=>$username
    );

    $sqlCust = "SELECT ID_customer, password FROM customer WHERE username=:username";
    $sqlEmp = "SELECT id_employee, password FROM employee WHERE username=:username";

    $stmtCust = $pdo->prepare($sqlCust);
    $stmtEmp = $pdo->prepare($sqlEmp);

    $stmtCust->execute($params);
    $stmtEmp->execute($params);

    if ($user = $stmtCust->fetch()) {
        // Code bila SQL berjalan baik, dan user adalah Customer
        // Cek Password
        if (password_verify($password, $user['password'])) {
            // Bila Password Benar
            $response["userType"] = "customer";
            $response["ID"] = $user["ID_customer"];
            $response["success"] = 1;
            $response["message"] = "Customer Sukses Login";
            echo json_encode($response);
        } else {
            // Bila Password Salah
            $response["success"] = 0;
            $response["message"] = "Password Customer Salah";
            echo json_encode($response);
        }
    } elseif ($user = $stmtEmp->fetch()) {
        // Code bila SQL berjalan baik, dan user adalah Employee
        // Cek Password
        if (password_verify($password, $user['password'])) {
            // Bila Password Benar
            $response["userType"] = "employee";
            $response["ID"] = $user["id_employee"];
            $response["success"] = 1;
            $response["message"] = "Employee Sukses Login";
            echo json_encode($response);
        } else {
            // Bila Password Salah
            $response["success"] = 0;
            $response["message"] = "Password Employee Salah";
            echo json_encode($response);
        }
    } else {
        // Code bila SQL tidak berjalan baik
        $response["success"] = -1;
        $response["message"] = "User Tidak Ditemukan";
        echo json_encode($response);
    }
} else {
    // Code bila tidak ditemukan data 
    $response["success"] = -2;
    $response["message"] = "Data Kosong";
    echo json_encode($response);
}
