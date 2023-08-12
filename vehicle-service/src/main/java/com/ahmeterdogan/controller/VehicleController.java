package com.ahmeterdogan.controller;

import com.ahmeterdogan.dto.request.AuthorizeUserToVehicleRequestDTO;
import com.ahmeterdogan.dto.request.VehicleGroupUpdateDTO;
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

    @GetMapping("/get-vehicles-by-group-id/{groupId}")
    List<VehicleResponseDTO> getAllVehiclesByGroupId(@RequestHeader("X-User") String generalRequestHeader, @PathVariable("groupId") long groupId) {
        return vehicleService.getAllVehiclesByGroupId(generalRequestHeader, groupId);
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

    @GetMapping("/get-directly-authorized-vehicle")
    public ResponseEntity<List<VehicleResponseDTO>> getDirectlyAuthorizedVehicle(@RequestHeader("X-User") String generalRequestHeader) {
        return ResponseEntity.ok(vehicleService.getDirectlyAuthorizedVehicle(generalRequestHeader));
    }

    @PostMapping("/authorize-user-to-vehicle")
    public ResponseEntity authorizeUserToVehicle(@RequestHeader("X-User") String generalRequestHeader, @RequestBody AuthorizeUserToVehicleRequestDTO authorizeUserToVehicleRequestDTO) {
        vehicleService.authorizeUserToVehicle(generalRequestHeader, authorizeUserToVehicleRequestDTO);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<VehicleResponseDTO> updateVehicle(@RequestHeader("X-User") String generalRequestHeader, @RequestBody VehicleUpdateDTO vehicleUpdateDTO) {
        return ResponseEntity.ok(vehicleService.updateVehicle(generalRequestHeader, vehicleUpdateDTO));
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Integer> deleteVehicleById(@RequestHeader("X-User") String generalRequestHeader, @PathVariable long id) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(vehicleService.deleteVehicleByIdAndCompanyId(generalRequestHeader,id));
    }

    @GetMapping("/get-directly-authorized-vehicles-by-user-id")
    List<VehicleResponseDTO> getDirectlyAuthorizedVehicle(@RequestHeader("X-User") String generalRequestHeader, @RequestParam Long userId) {
        return vehicleService.getDirectlyAuthorizedVehicle(generalRequestHeader, userId);
    }

    @PutMapping("/update-vehicle-group")
    public ResponseEntity<VehicleResponseDTO> updateVehicleGroup(@RequestHeader("X-User") String generalRequestHeader, @RequestBody VehicleGroupUpdateDTO vehicleGroupUpdateDTO) {
        return ResponseEntity.ok(vehicleService.updateVehicleGroup(generalRequestHeader, vehicleGroupUpdateDTO));
    }
}

