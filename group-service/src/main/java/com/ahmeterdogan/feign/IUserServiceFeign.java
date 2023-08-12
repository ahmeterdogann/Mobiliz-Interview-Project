package com.ahmeterdogan.feign;

import com.ahmeterdogan.dto.UserResponseDTO;
import com.ahmeterdogan.dto.VehicleDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(url = "http://localhost:8083/api/v1/users",name = "user-service")
public interface IUserServiceFeign {

    @GetMapping("/{id}")
    UserResponseDTO getUserById(@PathVariable("userId") long userId);

}