package com.example;

import java.util.Scanner;
import java.math.BigDecimal;
// import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.Instant;

public class App {
    static Instant now = Instant.now();
    static Scanner input = new Scanner(System.in);;
    static ResultSet rs;
    static AllData as;
    static Statement stmt;
    // Declare Variables
    static String fuelStation = "", fuelType = "", selectedType = "", selectedColumnFuelStation = "";
    static int fuelTypeChoice, fuelStationChoice;
    static BigDecimal dayFuelCost;

    public static void main(String[] args) {
        try {
            while (!ConnectorDB.connect().isClosed()) {

                System.out.println("Connected to Database !!!");
                showMenu();
            }

            ConnectorDB.connect().close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Show Menu
    static void showMenu() {
        System.out.println("\n========= MENU UTAMA =========");
        System.out.println("1. Insert Data");
        System.out.println("2. Show Data");
        System.out.println("3. Edit Data");
        System.out.println("4. Delete Data");
        System.out.println("5. Insert New Data Fuel Station Price");
        System.out.println("6. Excel Report");
        System.out.println("0. Keluar");
        System.out.print("\n");
        System.out.print("PILIHAN> ");

        try {
            int pilihan = input.nextInt();
            switch (pilihan) {
                case 0:
                    System.exit(0);
                    break;
                case 1:
                    insertDataPetrolCosts();
                    break;
                case 2:
                    showDataPetrolCosts();
                    break;
                case 3:
                    // updateDataPetrolCosts();
                    break;
                case 4:
                    // deleteDataPetrolCosts();
                    break;
                case 5:
                    // insertFuelStationPrices();
                    break;
                case 6:
                    // exportToExcel();
                    break;
                default:
                    System.out.println("Pilihan salah!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Insert Data
    static void insertDataPetrolCosts() {
        try {
            // ambil input dari user
            System.out.println("Pilih pengisian stasiun bahan bakar: ");
            System.out.println("1. Pertamina");
            System.out.println("2. Shell");
            System.out.print("Masukan Pilihan Anda : ");
            fuelStationChoice = input.nextInt();

            while (fuelStationChoice < 1 || fuelStationChoice > 2) {
                System.out.println("\n");
                System.out
                        .println("Masukan Pilihan yang sudah disediakan dan Pilihlah pengisian stasiun bahan bakar: ");
                System.out.println("1. Pertamina");
                System.out.println("2. Shell");
                System.out.print("Masukan Pilihan Anda : ");
                fuelStationChoice = input.nextInt();
            }

            // PERTAMINA
            if (fuelStationChoice == 1) {
                fuelStation = "Pertamina";
                selectedColumnFuelStation = "pertaminaPrices";
                System.out.println("Pilih pengisian tipe bahan bakar Pertamina: ");
                System.out.println("1. Pertalite");
                System.out.println("2. Pertamax");
                System.out.println("3. Pertamax Turbo");
                System.out.print("Masukan Pilihan anda : ");
                fuelTypeChoice = input.nextInt();
                System.out.print("\n");

                while (fuelTypeChoice < 1 || fuelTypeChoice > 3) {
                    System.out.println("Pilih pengisian tipe bahan bakar Pertamina: ");
                    System.out.println("1. Pertalite");
                    System.out.println("2. Pertamax");
                    System.out.println("3. Pertamax Turbo");
                    System.out.print("Masukan Pilihan anda : ");
                    fuelTypeChoice = input.nextInt();
                    System.out.print("\n");
                }

                switch (fuelTypeChoice) {
                    case 1:
                        fuelType = "Pertalite";
                        selectedType = "pertalite_price";
                        break;
                    case 2:
                        fuelType = "Pertamax";
                        selectedType = "pertamax_price";
                        break;
                    case 3:
                        fuelType = "Pertamax Turbo";
                        selectedType = "pertamax_turbo_price";
                        break;
                }
                System.out
                        .print("Anda menggunakan Bahan Bakar " + fuelType.toUpperCase()
                                + " Masukan Pengeluaran anda Rp. /L : Rp. ");
                dayFuelCost = input.nextBigDecimal();
            }

            // SHELL
            if (fuelStationChoice == 2) {
                fuelStation = "Shell";
                selectedColumnFuelStation = "shellPrices";
                System.out.println("Pilih pengisian tipe bahan bakar Shell : ");
                System.out.println("1. Super");
                System.out.println("2. V Power");
                System.out.print("Masukan Pilihan anda : ");
                fuelTypeChoice = input.nextInt();
                System.out.print("\n");

                while (fuelTypeChoice < 1 || fuelTypeChoice > 2) {
                    System.out.println("Pilih pengisian tipe bahan bakar Shell : ");
                    System.out.println("1. Super");
                    System.out.println("2. V Power");
                    System.out.print("Masukan Pilihan anda : ");
                    fuelTypeChoice = input.nextInt();
                    System.out.print("\n");
                }

                switch (fuelTypeChoice) {
                    case 1:
                        fuelType = "Super";
                        selectedType = "super_price";
                        break;
                    case 2:
                        fuelType = "V Power";
                        selectedType = "vpower_price";
                        break;
                }
                System.out
                        .print("Anda menggunakan Bahan Bakar " + fuelType.toUpperCase()
                                + " Masukan Pengeluaran anda Rp. /L : Rp. ");
                dayFuelCost = input.nextBigDecimal();
            }

            // get epochValue using getEpochSecond
            long createdAtTime = now.getEpochSecond();
            long updatedAtTime = now.getEpochSecond();

            // Query Select Database
            String sqlQuery = ("SELECT " + selectedType + " FROM "
                    + selectedColumnFuelStation
                    + " ORDER BY created_at DESC LIMIT 1");
            PreparedStatement pr = ConnectorDB.connect().prepareStatement(sqlQuery);
            ResultSet rs = pr.executeQuery();

            if (rs.next()) {
                float getFuelPrice = rs.getInt(selectedType);
                float resultFuelPrice = dayFuelCost.floatValue() / getFuelPrice;

                // Query Insert Database
                String sql = "INSERT INTO fuelCosts (fuel_station,fuel_type,fuel_cost,fuel_liter,created_at,updated_at) VALUES (?,?,?,?,?,?)";

                PreparedStatement ExecuteInsertQuery = ConnectorDB.connect().prepareStatement(sql);
                ExecuteInsertQuery.setString(1, fuelStation);
                ExecuteInsertQuery.setString(2, fuelType);
                ExecuteInsertQuery.setBigDecimal(3, dayFuelCost);
                ExecuteInsertQuery.setFloat(4, resultFuelPrice);
                ExecuteInsertQuery.setLong(5, createdAtTime);
                ExecuteInsertQuery.setLong(6, updatedAtTime);

                int status = ExecuteInsertQuery.executeUpdate();
                if (status > 0) {
                    System.out.println("Data berhasil ditambahkan !!!");
                    ConnectorDB.connect().close();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
        }
    }

    // Show Data
    static void showDataPetrolCosts() {

        String sqlQuery = "SELECT * FROM fuelCosts DESCY ORDER BY id_fuel DESC";

        try {
            PreparedStatement pr = ConnectorDB.connect().prepareStatement(sqlQuery);
            ResultSet rs = pr.executeQuery();

            System.out.println("+--------------------------------+");
            System.out.println("|    PENGELUARAN BIAYA BENSIN    |");
            System.out.println("+--------------------------------+");

            while (rs.next()) {
                as = new AllData();
                as.setFuelCost(rs.getBigDecimal("fuel_cost"));
                as.setFuelLiter(rs.getFloat("fuel_liter"));
                as.setCreatedAtTime(rs.getInt("created_at"));
                String date = new java.text.SimpleDateFormat("dd-MM-yyyy HH:mm:ss")
                        .format(new java.util.Date(as.getCreatedAtTime() * 1000));
                System.out.printf("Pengeluaran pada tanggal %s sebanyak Rp. %s untuk %s liter %n", date,
                        as.getFuelCost(), as.getFuelLiter());
            }
            pr.close();
            rs.close();
        } catch (Exception e) {
            e.getMessage();
        }
    }
}
