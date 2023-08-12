package com.ahmeterdogan.feign;

import com.ahmeterdogan.dto.request.VehicleGroupUpdateDTO;
import com.ahmeterdogan.dto.response.VehicleResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(url = "http://localhost:8081/api/v1/vehicles",name = "vehicle-service")
public interface IVehicleServiceFeign {

    @GetMapping("/get-vehicles-by-group-id/{groupId}")
    List<VehicleResponseDTO> getAllVehiclesByGroupId(@RequestHeader("X-User") String generalRequestHeader, @PathVariable("groupId") long groupId);

    @GetMapping("/get-directly-authorized-vehicle")
    List<VehicleResponseDTO> getDirectlyAuthorizedVehicle(@RequestHeader("X-User") String generalRequestHeader);

    @GetMapping("/get-directly-authorized-vehicles-by-user-id")
    List<VehicleResponseDTO> getDirectlyAuthorizedVehicle(@RequestHeader("X-User") String generalRequestHeader, @RequestParam Long userId);

    @PutMapping("/update-vehicle-group")
    VehicleResponseDTO updateVehicleGroup(@RequestHeader("X-User") String generalRequestHeader, @RequestBody VehicleGroupUpdateDTO vehicleGroupUpdateDTO);
}