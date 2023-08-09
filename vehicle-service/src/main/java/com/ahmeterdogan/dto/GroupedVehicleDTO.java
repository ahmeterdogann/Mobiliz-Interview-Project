package com.ahmeterdogan.dto;

import java.util.List;

public class GroupedVehicleDTO {
    public String name;
    public GroupDTO groupDTO;
    public List<VehicleDTO> vehicles;

    public GroupedVehicleDTO(String name, GroupDTO groupDTO, List<VehicleDTO> vehicles) {
        this.name = name;
        this.groupDTO = groupDTO;
        this.vehicles = vehicles;
    }

    public GroupedVehicleDTO(){};

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GroupDTO getGroupDTO() {
        return groupDTO;
    }

    public void setGroupDTO(GroupDTO groupDTO) {
        this.groupDTO = groupDTO;
    }

    public List<VehicleDTO> getVehicles() {
        return vehicles;
    }

    public void setVehicles(List<VehicleDTO> vehicles) {
        this.vehicles = vehicles;
    }
}
