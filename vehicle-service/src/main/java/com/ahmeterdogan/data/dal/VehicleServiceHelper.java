package com.ahmeterdogan.data.dal;

import com.ahmeterdogan.data.entity.UserVehicleAuthorization;
import com.ahmeterdogan.data.entity.Vehicle;
import com.ahmeterdogan.data.repository.IUserVehicleAuthorizationRepository;
import com.ahmeterdogan.data.repository.IVehicleRepository;
import com.ahmeterdogan.mapper.IVehicleMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class VehicleServiceHelper {
    private final IVehicleRepository vehicleRepository;
    private final IUserVehicleAuthorizationRepository userVehicleAuthorizationRepository;
    private final IVehicleMapper vehicleMapper;

    public VehicleServiceHelper(IVehicleRepository vehicleRepository, IUserVehicleAuthorizationRepository userVehicleAuthorizationRepository, IVehicleMapper vehicleMapper) {
        this.vehicleRepository = vehicleRepository;
        this.userVehicleAuthorizationRepository = userVehicleAuthorizationRepository;
        this.vehicleMapper = vehicleMapper;
    }

    public Vehicle saveVehicle(Vehicle vehicle) {
        return vehicleRepository.save(vehicle).builder().build();
    }

    public List<Vehicle> saveAll(List<Vehicle> vehicles) {
        return vehicleRepository.saveAll(vehicles);
    }

    public Optional<Vehicle> getVehicleById(long id) {
        return vehicleRepository.findById(id);
    }

    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }

    public Vehicle updateVehicleById(Vehicle vehicle) {
        return vehicleRepository.updateVehicle(vehicle);
    }

    public int deleteVehicleById(long id) {
        return vehicleRepository.deleteById(id);
    }

    public List<Vehicle> getAllVehiclesByGroupId(long groupId) {
        return vehicleRepository.findAllByGroup_Id(groupId);
    }

    public UserVehicleAuthorization userVehicleAuthRecord(long userId, long vehicleId) {
        return userVehicleAuthorizationRepository.findByUserIdAndVehicleId(userId, vehicleId);
    }
}
