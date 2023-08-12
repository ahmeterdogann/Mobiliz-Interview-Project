package com.ahmeterdogan.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class VehicleUpdateDTO{
    private long id;
    private String plateNumber;
    private String brand;
    private String model;
    private int modelYear;
    private String chassisNumber;
    private String label;
    private Long groupId;
    private Long companyId;
}