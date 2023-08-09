package com.ahmeterdogan.service;

import com.ahmeterdogan.data.entity.UserVehicleAuthorization;
import com.ahmeterdogan.data.entity.Vehicle;
import com.ahmeterdogan.data.dal.VehicleServiceHelper;
import com.ahmeterdogan.dto.VehicleDTO;
import com.ahmeterdogan.mapper.IVehicleMapper;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class VehicleService {
    private final VehicleServiceHelper vehicleServiceHelper;
    private final IVehicleMapper vehicleMapper;

    public VehicleService(VehicleServiceHelper vehicleServiceHelper, IVehicleMapper vehicleMapper) {
        this.vehicleServiceHelper = vehicleServiceHelper;
        this.vehicleMapper = vehicleMapper;
    }

    public VehicleDTO saveVehicle(Vehicle vehicle) {
        return vehicleMapper.toDto(vehicleServiceHelper.saveVehicle(vehicle));
    }

    public List<VehicleDTO> saveAll(List<Vehicle> vehicles) {
        return vehicleServiceHelper.saveAll(vehicles).stream().map(vehicleMapper::toDto).collect(Collectors.toList());
    }

    public Optional<VehicleDTO> getVehicleById(long id) {
        return vehicleServiceHelper.getVehicleById(id).map(vehicleMapper::toDto);
    }

    public List<VehicleDTO> getAllVehicles() {
        return vehicleServiceHelper.getAllVehicles().stream().map(vehicleMapper::toDto).collect(Collectors.toList());
    }

    public VehicleDTO updateVehicleById(Vehicle vehicle) {
        return vehicleMapper.toDto(vehicleServiceHelper.updateVehicleById(vehicle));
    }

    public int deleteVehicleById(long id) {
        return vehicleServiceHelper.deleteVehicleById(id);
    }

    public List<VehicleDTO> getAllVehiclesByGroupId(long id) {
        return vehicleServiceHelper.getAllVehiclesByGroupId(id).stream().map(vehicleMapper::toDto).collect(Collectors.toList());
    }

    public boolean isUserAuthToVehicle(long userId, long vehicleId) {
        UserVehicleAuthorization userVehicleAuthorization = vehicleServiceHelper.userVehicleAuthRecord(userId, vehicleId);

        if (userVehicleAuthorization.getUser().getId() != null && userVehicleAuthorization.getVehicle().getId() != null)
            return true;
        else
            return false;
    }

}
