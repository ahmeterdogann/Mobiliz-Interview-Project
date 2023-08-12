package com.ahmeterdogan.feign;

import com.ahmeterdogan.dto.response.UserResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(url = "http://localhost:8083/api/v1/users",name = "user-service")
public interface IUserServiceFeign {
    @GetMapping("{id}")
    ResponseEntity<UserResponseDTO> getUserById(@PathVariable("id") Long id);
}
