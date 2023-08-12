package com.ahmeterdogan.mapper;
import com.ahmeterdogan.data.entity.Vehicle;
import com.ahmeterdogan.dto.request.VehicleGroupUpdateDTO;
import com.ahmeterdogan.dto.response.VehicleResponseDTO;
import com.ahmeterdogan.dto.request.VehicleUpdateDTO;
import com.ahmeterdogan.dto.request.VehicleSaveDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, implementationName = "VehicleMapperImpl", componentModel = "spring")
public interface IVehicleMapper{
    @Mapping(source="group.name", target="groupName")
    VehicleResponseDTO toDto(Vehicle vehicle);
    @Mapping(source="groupName", target="group.name")
    Vehicle toEntity(VehicleResponseDTO vehicleResponseDTO);

    @Mapping(source="groupId", target="group.id")
    Vehicle toEntity(VehicleSaveDTO vehicleSaveDTO);
    @Mapping(source="groupId", target="group.id")
    @Mapping(source="companyId", target="company.id")
    Vehicle toEntity(VehicleUpdateDTO vehicleUpdateDTO);

    @Mapping(source="groupId", target="group.id")
    Vehicle toEntity(VehicleGroupUpdateDTO vehicleGroupUpdateDTO);

    @Mapping(source="group.id", target="groupId")
    @Mapping(source="company.id", target="companyId")
    VehicleUpdateDTO toUpdateDto(Vehicle vehicle);
}
