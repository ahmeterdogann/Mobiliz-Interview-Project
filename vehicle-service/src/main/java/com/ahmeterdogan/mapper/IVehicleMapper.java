package com.ahmeterdogan.mapper;
import com.ahmeterdogan.data.entity.Vehicle;
import com.ahmeterdogan.dto.VehicleDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(implementationName = "VehicleMapperImpl", componentModel = "spring")
public interface IVehicleMapper{
    @Mapping(source="group.name", target="groupName")
    VehicleDTO toDto(Vehicle vehicle);
    @Mapping(source="groupName", target="group.name")
    Vehicle toEntity(VehicleDTO vehicleDTO);
}
