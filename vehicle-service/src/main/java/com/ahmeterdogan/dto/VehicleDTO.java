package com.ahmeterdogan.dto;

public class VehicleDTO {
    private long id;
    private String plateNumber;
    private String brand;
    private String model;
    private int modelYear;
    private String chassisNumber;
    private String label;
    private long companyId;
    private long groupId;
    private long fleetId;

    public VehicleDTO(long id, String plateNumber, String brand, String model, int modelYear, String chassisNumber, String label, long companyId, long groupId, long fleetId) {
        this.id = id;
        this.plateNumber = plateNumber;
        this.brand = brand;
        this.model = model;
        this.modelYear = modelYear;
        this.chassisNumber = chassisNumber;
        this.label = label;
        this.companyId = companyId;
        this.groupId = groupId;
        this.fleetId = fleetId;
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

    public String getChassisNumber() {
        return chassisNumber;
    }

    public void setChassisNumber(String chassisNumber) {
        this.chassisNumber = chassisNumber;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(long companyId) {
        this.companyId = companyId;
    }

    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }

    public long getFleetId() {
        return fleetId;
    }

    public void setFleetId(long fleetId) {
        this.fleetId = fleetId;
    }
}
