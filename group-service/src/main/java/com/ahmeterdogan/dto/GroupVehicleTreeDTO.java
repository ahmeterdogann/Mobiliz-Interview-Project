package com.ahmeterdogan.dto;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GroupVehicleTreeDTO {
    private Long id;
    private String name;
    private List<VehicleDTO> vehicles;
    private Set<GroupVehicleTreeDTO> subGroups;

    public GroupVehicleTreeDTO(Long id, String name) {
        this.id = id;
        this.name = name;
        this.subGroups = new HashSet<>();
        vehicles = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addChild(GroupVehicleTreeDTO groupVehicleTreeDTO) {
        subGroups.add(groupVehicleTreeDTO);
    }

    public Set<GroupVehicleTreeDTO> getSubGroups() {
        return subGroups;
    }

    public void setSubGroups(Set<GroupVehicleTreeDTO> subGroups) {
        this.subGroups = subGroups;
    }

    public List<VehicleDTO> getVehicles() {
        return vehicles;
    }

    public void setVehicles(List<VehicleDTO> vehicles) {
        this.vehicles = vehicles;
    }
}
