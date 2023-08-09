package com.ahmeterdogan.mapper;
import com.ahmeterdogan.data.entity.Vehicle;
import com.ahmeterdogan.dto.VehicleDTO;
import org.mapstruct.Mapper;

@Mapper(implementationName = "VehicleMapperImpl", componentModel = "spring")
public interface IVehicleMapper extends EntityMapper<VehicleDTO, Vehicle>{
}
