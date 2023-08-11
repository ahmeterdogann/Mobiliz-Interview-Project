package com.ahmeterdogan.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class AllVehiclesOfUserResponseDto {
    private List<VehicleDTO> vehicles;

    public void addVehicles(List<VehicleDTO> vehicles) {
        vehicles.addAll(vehicles);
    }
}
