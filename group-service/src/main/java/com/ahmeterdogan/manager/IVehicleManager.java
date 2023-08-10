package com.ahmeterdogan.manager;

import com.ahmeterdogan.dto.VehicleDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(url = "http://localhost:8081/api/vehicles",name = "vehicle-service")
public interface IVehicleManager {

    @GetMapping("/get-vehicles-by-group-id/{groupId}")
    public ResponseEntity<List<VehicleDTO>> getAllVehiclesByGroupId(@PathVariable("groupId") long groupId);

}