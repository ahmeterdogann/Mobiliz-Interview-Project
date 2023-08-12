package com.ahmeterdogan.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupVehicleTreeResponseDTO {
    private Long id;
    private String name;
    private List<VehicleResponseDTO> vehiclesOfGroup;
    private Set<GroupVehicleTreeResponseDTO> children;
    public static List<VehicleResponseDTO> allVehiclesInTree = new ArrayList<>();

    public GroupVehicleTreeResponseDTO(Long id, String name) {
        this.id = id;
        this.name = name;
        this.children = new HashSet<>();
        vehiclesOfGroup = new ArrayList<>();
        allVehiclesInTree = new ArrayList<>();
    }

    public void addChild(GroupVehicleTreeResponseDTO groupVehicleTreeResponseDTO) {
        children.add(groupVehicleTreeResponseDTO);
    }

    public static List<VehicleResponseDTO> getAllVehiclesInTree() {
        return allVehiclesInTree;
    }

    public static void setAllVehiclesInTree(List<VehicleResponseDTO> allVehiclesInTree) {
        GroupVehicleTreeResponseDTO.allVehiclesInTree = allVehiclesInTree;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GroupVehicleTreeResponseDTO that = (GroupVehicleTreeResponseDTO) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
