package com.example;

import java.math.BigDecimal;
import java.time.Instant;

public class AllData {
    static Instant now = Instant.now();

    private int IdFuel;
    private String FuelStation;
    private String FuelType;
    private BigDecimal FuelCost;
    private float FuelLiter;
    private long createdAtTime = now.getEpochSecond(); // Create Epoch Time
    private long updatedAtTime = now.getEpochSecond(); // Update Epoch Time

    // GET & SET ID FUEL STATION
    public int getIdFuel() {
        return IdFuel;
    }

    public void setIdFuel(int IdFuel) {
        this.IdFuel = IdFuel;
    }

    // GET & SET FUEL STATION
    public String getFuelStation() {
        return FuelStation;
    }

    public void setFuelStation(String FuelStation) {
        this.FuelStation = FuelStation;
    }

    // GET & SET FUEL TYPE
    public String getFuelType() {
        return FuelType;
    }

    public void setFuelType(String FuelType) {
        this.FuelType = FuelType;
    }

    // GET & SET FUEL COST
    public BigDecimal getFuelCost() {
        return FuelCost;
    }

    public void setFuelCost(BigDecimal FuelCost) {
        this.FuelCost = FuelCost;
    }

    // GET & SET FUEL LITER
    public float getFuelLiter() {
        return FuelLiter;
    }

    public void setFuelLiter(float FuelLiter) {
        this.FuelLiter = FuelLiter;
    }

    // GET & SET CREATED AT
    public long getCreatedAtTime() {
        return createdAtTime;
    }

    public void setCreatedAtTime(int createdAtTime) {
        this.createdAtTime = createdAtTime;
    }

    // GET & SET UPDATED AT
    public long getUpdateAtTime() {
        return updatedAtTime;
    }

    public void setUpdatedAtTime(int updatedAtTime) {
        this.updatedAtTime = updatedAtTime;
    }
}
