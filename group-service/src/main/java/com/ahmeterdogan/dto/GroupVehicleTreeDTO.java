package com.ahmeterdogan.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupVehicleTreeDTO {
    private Long id;
    private String name;
    private List<VehicleDTO> vehiclesOfGroup;
    private Set<GroupVehicleTreeDTO> children;
    public static List<VehicleDTO> allVehiclesInTree = new ArrayList<>();

    public GroupVehicleTreeDTO(Long id, String name) {
        this.id = id;
        this.name = name;
        this.children = new HashSet<>();
        vehiclesOfGroup = new ArrayList<>();
        allVehiclesInTree = new ArrayList<>();
    }

    public void addChild(GroupVehicleTreeDTO groupVehicleTreeDTO) {
        children.add(groupVehicleTreeDTO);
    }

    public static List<VehicleDTO> getAllVehiclesInTree() {
        return allVehiclesInTree;
    }

    public static void setAllVehiclesInTree(List<VehicleDTO> allVehiclesInTree) {
        GroupVehicleTreeDTO.allVehiclesInTree = allVehiclesInTree;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GroupVehicleTreeDTO that = (GroupVehicleTreeDTO) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
