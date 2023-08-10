package com.ahmeterdogan.feign;

import com.ahmeterdogan.dto.request.UserDTORequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@FeignClient(url = "http://localhost:8083/api/v1/users",name = "user-service")
public interface IUserServiceFeign {
    @GetMapping("{id}")
    public Optional<UserDTORequest> getUserById(@PathVariable Long id);
}
