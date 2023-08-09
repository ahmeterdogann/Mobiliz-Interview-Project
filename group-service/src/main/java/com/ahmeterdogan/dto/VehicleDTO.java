package com.ahmeterdogan.dto;

public class VehicleDTO {
    private long id;
    private String plateNumber;
    private String brand;
    private String model;
    private int modelYear;

    public VehicleDTO(long id, String plateNumber, String brand, String model, int modelYear) {
        this.id = id;
        this.plateNumber = plateNumber;
        this.brand = brand;
        this.model = model;
        this.modelYear = modelYear;
    }

    public VehicleDTO() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getModelYear() {
        return modelYear;
    }

    public void setModelYear(int modelYear) {
        this.modelYear = modelYear;
    }
}