package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectorDB {
    // username yang digunakan untuk mengakses database tersebut
    public static String USERNAME = System.getenv("oci_user_sql");
    // password yang digunakan untuk mengakses database tersebut.
    // Jika tidak menggunakan password, kosongkan saja bagian tersebut
    public static String PASSWORD = System.getenv("oci_password_sql");
    // port mysql
    public static int PORT = 3306;
    // database yang akan dikoneksikan
    public static String DATABASE = "fuels";
    // ip address server MySQL. Jika dengan koneksi LAN atau internet ganti dengan
    // nomor ip komputer server tempat dimana menginstal MySQL Server
    public static String HOST = System.getenv("oci_host_sql");

    // Constructor
    public static final Connection connect() {

        // inisialisasi interface Connection
        Connection con = null;
        try {
            // Menyiapkan paramter JDBC untuk koneksi ke datbase
            Class.forName("com.mysql.jdbc.Driver");// load driver
            // menghubungkan database dengan method getConnection menggunakan atribut yang
            // telah didefinisikaniatas
            con = DriverManager.getConnection("jdbc:mysql://" + HOST + ":" + PORT + "/" + DATABASE, USERNAME, PASSWORD);

        } catch (ClassNotFoundException e) {
            System.out.println("Connection Failed !\n" + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Connection Failed !\n" + e.getMessage());
        }
        return con;
    }
}