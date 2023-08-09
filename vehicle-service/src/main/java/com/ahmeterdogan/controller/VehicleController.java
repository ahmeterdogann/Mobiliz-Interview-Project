package com.ahmeterdogan.controller;

import com.ahmeterdogan.data.entity.Vehicle;
import com.ahmeterdogan.dto.VehicleDTO;
import com.ahmeterdogan.service.VehicleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/vehicles")
public class VehicleController {

    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @PostMapping("/save")
    public ResponseEntity<VehicleDTO> save(@RequestBody Vehicle vehicle) {
        VehicleDTO savedVehicle = vehicleService.saveVehicle(vehicle);
        return ResponseEntity.ok(savedVehicle);
    }


    @PostMapping("/saveAll")
    public ResponseEntity<List<VehicleDTO>> saveAll(@RequestBody List<Vehicle> vehicles) {
        List<VehicleDTO> vehicleDTOS= vehicleService.saveAll(vehicles);
        return ResponseEntity.ok(vehicleDTOS);
    }

    @GetMapping("/all")
    public ResponseEntity<List<VehicleDTO>> getAllVehicles() {
        List<VehicleDTO> vehicles = vehicleService.getAllVehicles();
        return ResponseEntity.ok(vehicles);
    }

    @GetMapping("/get-vehicles-by-group-id")
    public ResponseEntity<List<VehicleDTO>> getAllVehiclesByGroupId(@RequestParam("groupId") long groupId) {
        return ResponseEntity.ok(vehicleService.getAllVehiclesByGroupId(groupId));
    }

    @GetMapping("/auth-control")
    public ResponseEntity<Boolean> isUserAuthToVehicle(@RequestParam long userId, long vehicleId) {
        return ResponseEntity.ok(vehicleService.isUserAuthToVehicle(userId, vehicleId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<VehicleDTO> getVehicleById(@PathVariable long id) {
        Optional<VehicleDTO> vehicleDTO = vehicleService.getVehicleById(id);
        return vehicleDTO.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<VehicleDTO> updateVehicle(@PathVariable("id") long id, @RequestBody Vehicle vehicle) {
        Optional<VehicleDTO> vehicleDTO = vehicleService.getVehicleById(id);
        return vehicleDTO.map(v -> ResponseEntity.ok(vehicleService.updateVehicleById(vehicle))).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Integer> deleteVehicleById(@PathVariable long id) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(vehicleService.deleteVehicleById(id));
    }


}

