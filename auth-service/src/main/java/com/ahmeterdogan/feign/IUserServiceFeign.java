package com.ahmeterdogan.feign;

import com.ahmeterdogan.dto.request.UserSaveDto;
import com.ahmeterdogan.dto.response.UserResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@FeignClient(url = "http://localhost:8083/api/v1/users",name = "user-service")
public interface IUserServiceFeign {
    @GetMapping("{id}")
    Optional<UserResponseDto> getUserById(@PathVariable Long id);

    @PostMapping("/save")
    UserResponseDto save(UserSaveDto userSaveDto);
}
