package com.example;

import java.util.Scanner;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.Instant;

public class App {

    static Instant now = Instant.now();
    static Scanner input = new Scanner(System.in);;

    static AllData as;
    static Statement stmt;
    static PreparedStatement pr;
    static ResultSet rs;
    static Boolean statusQuery;

    // Declare Variables
    static String fuelStation = "", fuelType = "", selectedType = "", selectedColumnFuelStation = "";
    static int fuelTypeChoice, fuelStationChoice;
    static BigDecimal dayFuelCost;

    public static void main(String[] args) {
        try {
            while (ConnectorDB.connect() != null) {
                System.out.println("\n");
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
        // input.
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
                    updateDataPetrolCosts();
                    break;
                case 4:
                    deleteDataPetrolCosts();
                    break;
                case 5:
                    // insertFuelStationPrices();
                    break;
                case 6:
                    // showFuelStationPrices();
                    break;
                case 7:
                    // deleteFuelStationPrices();
                    break;
                case 8:
                    // updateFuelStationPrices();
                    break;
                case 9:
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
        long startSecond = System.currentTimeMillis();
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
                    System.out.print("\n");
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
                    System.out.print("\n");
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
            pr = ConnectorDB.connect().prepareStatement(sqlQuery);
            rs = pr.executeQuery();

            if (rs.next()) {
                float getFuelPrice = rs.getInt(selectedType);
                float resultFuelPrice = dayFuelCost.floatValue() / getFuelPrice;

                // Query Insert Database
                String sql = "INSERT INTO fuelCosts (fuel_station,fuel_type,fuel_cost,fuel_liter,created_at,updated_at) VALUES (?,?,?,?,?,?)";

                pr = ConnectorDB.connect().prepareStatement(sql);
                pr.setString(1, fuelStation);
                pr.setString(2, fuelType);
                pr.setBigDecimal(3, dayFuelCost);
                pr.setFloat(4, resultFuelPrice);
                pr.setLong(5, createdAtTime);
                pr.setLong(6, updatedAtTime);

                int status = pr.executeUpdate();
                if (status > 0) {
                    System.out.println("Data berhasil ditambahkan !!!");
                    ConnectorDB.connect().close();
                    System.out.println("\n");

                    // finding the time difference and converting it into seconds
                    long endSecond = System.currentTimeMillis();
                    float sec = (endSecond - startSecond) / 1000F;
                    System.out.println("Elapsed time in seconds " + sec);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
        }
    }

    // Show Data
    static void showDataPetrolCosts() {
        long startSecond = System.currentTimeMillis();

        String SQL_SELECT = "SELECT * FROM fuelCosts DESCY ORDER BY id_fuel DESC";

        try {
            pr = ConnectorDB.connect().prepareStatement(SQL_SELECT);
            rs = pr.executeQuery();
            String date = "";

            System.out.println("+---------------------------------+");
            System.out.println("| DAFTAR PENGELUARAN BIAYA BENSIN |");
            System.out.println("+---------------------------------+");

            while (rs.next()) {
                as = new AllData();
                as.setIdFuel(rs.getInt("id_fuel"));
                as.setFuelStation(rs.getString("fuel_station"));
                as.setFuelType(rs.getString("fuel_type"));
                as.setFuelCost(rs.getBigDecimal("fuel_cost"));
                as.setFuelLiter(rs.getFloat("fuel_liter"));
                as.setCreatedAtTime(rs.getInt("created_at"));
                date = new java.text.SimpleDateFormat("dd-MM-yyyy HH:mm:ss")
                        .format(new java.util.Date(as.getCreatedAtTime() * 1000));
                printFullTable(0, as.getFuelStation(), as.getFuelType(), as.getFuelCost(), as.getFuelLiter(), date);
            }

            ConnectorDB.connect().close();

            System.out.println("\n");
            // finding the time difference and converting it into seconds
            long endSecond = System.currentTimeMillis();
            float sec = (endSecond - startSecond) / 1000F;
            System.out.println("Elapsed time in seconds " + sec);

        } catch (Exception e) {
            e.getMessage();
        }
    }

    // Delete Data
    static void deleteDataPetrolCosts() {
        long startSecond = System.currentTimeMillis();

        String SQL_SELECT = "SELECT * FROM fuelCosts DESCY ORDER BY id_fuel DESC"; // Query SELECT
        String SQL_DELETE = "DELETE FROM fuelCosts WHERE id_fuel = ?"; // Query DELETE

        try {
            pr = ConnectorDB.connect().prepareStatement(SQL_SELECT);
            rs = pr.executeQuery();

            System.out.println("+---------------------------------+");
            System.out.println("| DAFTAR PENGELUARAN BIAYA BENSIN |");
            System.out.println("+---------------------------------+");

            while (rs.next()) {
                as = new AllData();
                as.setIdFuel(rs.getInt("id_fuel"));
                as.setFuelStation(rs.getString("fuel_station"));
                as.setFuelType(rs.getString("fuel_type"));
                as.setFuelCost(rs.getBigDecimal("fuel_cost"));
                as.setFuelLiter(rs.getFloat("fuel_liter"));
                as.setCreatedAtTime(rs.getInt("created_at"));
                String date = new java.text.SimpleDateFormat("dd-MM-yyyy HH:mm:ss")
                        .format(new java.util.Date(as.getCreatedAtTime() * 1000));
                printFullTable(as.getIdFuel(), as.getFuelStation(), as.getFuelType(), as.getFuelCost(),
                        as.getFuelLiter(), date);
            }

            System.out.println();
            // Get Data Id From User
            System.out.print("Enter the id for the data to be deleted : ");
            int idFuel = input.nextInt();

            pr = ConnectorDB.connect().prepareStatement(SQL_DELETE);
            pr.setInt(1, idFuel);

            if (pr.executeUpdate() > 0) {
                System.out.println("Data telah terhapus...");
                ConnectorDB.connect().close();
                System.out.println("\n");
                // finding the time difference and converting it into seconds
                long endSecond = System.currentTimeMillis();
                float sec = (endSecond - startSecond) / 1000F;
                System.out.println("Elapsed time in seconds " + sec);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Update Data
    static void updateDataPetrolCosts() {
        int i = 0;
        long startSecond = System.currentTimeMillis();

        String SQL_UPDATE = "UPDATE fuelCosts SET fuel_station=?, fuel_type=?, fuel_cost=?, fuel_liter=?, updated_at=? WHERE id_fuel=?";
        String SQL_SELECT = "SELECT * FROM fuelCosts";

        // List<String> arrDataStation = new ArrayList<String>();

        String[] arrDataStation = { "Fuel Station", "Fuel Type", "Fuel Cost", "Fuel Liter" };

        try {

            pr = ConnectorDB.connect().prepareStatement(SQL_SELECT);
            rs = pr.executeQuery();
            System.out.println("What data do you want to update ? ");
            while (i < arrDataStation.length) {

                System.out.println((i + 1) + " " + arrDataStation[i]);
                i++;
            }

            // pr.setString(1, stockLevel);
            // pr.setInt(2, recievedStock);
            // pr.setString(3, averageUsage);
            // pr.setInt(4, partNumber);
            System.out.println("\n");
            // finding the time difference and converting it into seconds
            long endSecond = System.currentTimeMillis();
            float sec = (endSecond - startSecond) / 1000F;
            System.out.println("Elapsed time in seconds " + sec);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    static void printFullTable(int getId, String getFuelStation, String getFuelType, BigDecimal getFuelCost,
            Float getFuelLiter, String getTimestamp) {

        if (getId != 0) {
            System.out.println();
            System.out.printf("Id %s\t - %s Top Up at %s station as much Rp. %s for %s liters on %s", getId,
                    getFuelType, getFuelStation, getFuelCost, getFuelLiter, getTimestamp);
        } else {
            System.out.println();
            System.out.printf("- %s Top Up at %s station as much Rp. %s for %s liters on %s",
                    getFuelType, getFuelStation, getFuelCost, getFuelLiter, getTimestamp);
        }

    }

}
