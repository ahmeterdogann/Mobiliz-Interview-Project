package com.ahmeterdogan.service;

import com.ahmeterdogan.data.entity.Vehicle;
import com.ahmeterdogan.data.repository.IVehicleRepository;
import com.ahmeterdogan.dto.VehicleDTO;
import com.ahmeterdogan.mapper.IVehicleMapper;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VehicleService {

    private final IVehicleRepository vehicleRepository;
    private final IVehicleMapper vehicleMapper;

    public VehicleService(IVehicleRepository vehicleRepository, IVehicleMapper vehicleMapper) {
        this.vehicleRepository = vehicleRepository;
        this.vehicleMapper = vehicleMapper;
    }

    public VehicleDTO saveVehicle(Vehicle vehicle) {
        return vehicleMapper.toVehicleDTO(vehicleRepository.save(vehicle));
    }

    // Araç silme işlemi
    public void deleteVehicle(long id) {
        vehicleRepository.deleteById(id);
    }

    // Id'ye göre araç getirme işlemi
    public Optional<VehicleDTO> getVehicleById(long id) {
        return vehicleRepository.findById(id).map(vehicleMapper::toVehicleDTO);
    }

    // Tüm araçları listeleme işlemi
    public List<VehicleDTO> getAllVehicles() {
        return vehicleRepository.findAll().stream().map(vehicleMapper::toVehicleDTO).collect(Collectors.toList());
    }

}
