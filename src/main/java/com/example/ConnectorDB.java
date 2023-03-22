package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectorDB {

    public static String USERNAME = System.getenv("oci_user_sql");
    public static String PASSWORD = System.getenv("oci_password_sql");
    public static int PORT = 3306;
    public static String DATABASE = "operational";
    public static String HOST = System.getenv("oci_host_sql");

    // Constructor
    public static final Connection connect() {
        // inisialisasi interface Connection
        Connection con = null;
        try {
            // Menyiapkan paramter JDBC untuk koneksi ke datbase
            Class.forName("com.mysql.cj.jdbc.Driver");// load driver
            con = DriverManager.getConnection("jdbc:mysql://" + HOST + ":" + PORT + "/" + DATABASE, USERNAME, PASSWORD);

        } catch (ClassNotFoundException e) {
            System.out.println("Error Code : " + e.getMessage());

        } catch (SQLException e) {
            System.out.println("SQL Exception " + e.getMessage());
            System.out.println("SQL State : " + e.getSQLState());
            System.out.println("Error Code : " + e.getErrorCode());
        }
        return con;
    }
}
