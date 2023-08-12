package com.ahmeterdogan.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class VehicleSaveDTO {
    private String plateNumber;
    private String brand;
    private String model;
    private int modelYear;
    private String chassisNumber;
    private String label;
    private String groupName;
}