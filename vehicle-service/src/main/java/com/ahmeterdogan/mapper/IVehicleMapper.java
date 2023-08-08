package com.ahmeterdogan.mapper;
import com.ahmeterdogan.data.entity.Fleet;
import com.ahmeterdogan.data.entity.Group;
import com.ahmeterdogan.data.entity.Vehicle;
import com.ahmeterdogan.dto.VehicleDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(implementationName = "MemberMapperImpl", componentModel = "spring")
public interface IVehicleMapper {
    Vehicle toVehicle(VehicleDTO vehicleDTO);
    @Mapping(source = "group.id", target = "groupId")
    @Mapping(source = "fleet.id", target = "fleetId")
    VehicleDTO toVehicleDTO(Vehicle vehicle);

    default Long mapGroupToGroupId(Group group) {
        return group != null ? group.getId() : null;
    }

    default Long mapFleetToFleetId(Fleet fleet) {
        return fleet != null ? fleet.getId() : null;
    }
}
