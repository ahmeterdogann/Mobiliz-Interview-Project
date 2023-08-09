package com.ahmeterdogan.data.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(
        name = "vehicles"
)
public class Vehicle {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;
    @Column(
            name = "plate_number",
            length = 20,
            nullable = false
    )
    private String plateNumber;
    @Column(
            name = "brand",
            length = 100,
            nullable = false
    )
    private String brand;
    @Column(
            name = "model",
            length = 100,
            nullable = false
    )
    private String model;
    @Column(
            name = "model_year",
            nullable = false
    )
    private int modelYear;
    @Column(
            name = "chassis_number",
            length = 50
    )
    private String chassisNumber;
    @Column(
            name = "label",
            length = 100
    )
    private String label;
    @ManyToOne
    @JoinColumn(
            name = "group_id"
    )
    private Group group;
    @ManyToOne
    @JoinColumn(
            name = "company_id"
    )
    private Company company;

    public Vehicle(Long id, String plateNumber, String brand, String model, int modelYear, String chassisNumber, String label, Group group, Company company) {
        this.id = id;
        this.plateNumber = plateNumber;
        this.brand = brand;
        this.model = model;
        this.modelYear = modelYear;
        this.chassisNumber = chassisNumber;
        this.label = label;
        this.group = group;
        this.company = company;
    }

    public Vehicle() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Group getGroup() {
        return this.group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Company getCompany() {
        return this.company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPlateNumber() {
        return this.plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public String getBrand() {
        return this.brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return this.model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getModelYear() {
        return this.modelYear;
    }

    public void setModelYear(int modelYear) {
        this.modelYear = modelYear;
    }

    public String getChassisNumber() {
        return this.chassisNumber;
    }

    public void setChassisNumber(String chassisNumber) {
        this.chassisNumber = chassisNumber;
    }

    public String getLabel() {
        return this.label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Company getCompanyId() {
        return this.company;
    }

    public void setCompanyId(Company company) {
        this.company = company;
    }

    public Group getGroupId() {
        return this.group;
    }

    public void setGroupId(Group group) {
        this.group = group;
    }
}