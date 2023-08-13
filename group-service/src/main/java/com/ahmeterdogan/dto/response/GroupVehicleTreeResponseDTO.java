package com.ahmeterdogan.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
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
