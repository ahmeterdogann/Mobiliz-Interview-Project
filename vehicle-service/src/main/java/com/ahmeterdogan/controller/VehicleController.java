package com.ahmeterdogan.controller;

import com.ahmeterdogan.dto.response.VehicleResponseDTO;
import com.ahmeterdogan.dto.request.VehicleUpdateDTO;
import com.ahmeterdogan.dto.request.VehicleSaveDTO;
import com.ahmeterdogan.service.VehicleService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/vehicles")
public class VehicleController {

    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService, ObjectMapper objectMapper) {
        this.vehicleService = vehicleService;
    }

    @PostMapping("/save")
    public ResponseEntity<VehicleResponseDTO> save(@RequestHeader("X-User") String generalRequestHeader, @RequestBody VehicleSaveDTO vehicleSaveDTO) {
        VehicleResponseDTO savedVehicle = vehicleService.saveVehicle(generalRequestHeader, vehicleSaveDTO);
        return ResponseEntity.ok(savedVehicle);
    }


    @PostMapping("/saveAll")
    public ResponseEntity<List<VehicleResponseDTO>> saveAll(@RequestHeader("X-User") String generalRequestHeader, @RequestBody List<VehicleSaveDTO> vehicleSaveDTOList) {
        List<VehicleResponseDTO> vehicleResponseDTOList= vehicleService.saveAll(generalRequestHeader, vehicleSaveDTOList);
        return ResponseEntity.ok(vehicleResponseDTOList);
    }

    @GetMapping("/all-for-company-admin")
    public ResponseEntity<List<VehicleResponseDTO>> getAllVehiclesForCompanyAdmin(@RequestHeader("X-User") String generalRequestHeader) {
        List<VehicleResponseDTO> vehicles = vehicleService.getAllVehicles(generalRequestHeader);
        return ResponseEntity.ok(vehicles);
    }

    @GetMapping("/search-vehicles-by-group-name")
    public ResponseEntity<List<VehicleResponseDTO>> getAllVehiclesByGroupId(@RequestHeader("X-User") String generalRequestHeader, @RequestParam("groupName") String groupName) {
        return ResponseEntity.ok(vehicleService.getAllVehiclesByGroupName(generalRequestHeader, groupName));
    }

    @GetMapping("/{id}")
    public ResponseEntity<VehicleResponseDTO> getVehicleById(@RequestHeader("X-User") String generalRequestHeader, @PathVariable("id") Long vehicleId) {
        return vehicleService.getVehicleById(generalRequestHeader, vehicleId).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<VehicleResponseDTO> updateVehicle(@RequestHeader("X-User") String generalRequestHeader, @RequestBody VehicleUpdateDTO vehicleUpdateDTO) {
        return ResponseEntity.ok(vehicleService.updateVehicle(generalRequestHeader, vehicleUpdateDTO));
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Integer> deleteVehicleById(@RequestHeader("X-User") String generalRequestHeader, @PathVariable long id) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(vehicleService.deleteVehicleByIdAndCompanyId(generalRequestHeader,id));
    }


}

