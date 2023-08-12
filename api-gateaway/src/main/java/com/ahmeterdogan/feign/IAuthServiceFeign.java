package com.ahmeterdogan.feign;

import com.ahmeterdogan.dto.request.GeneralRequestHeaderDto;
import com.ahmeterdogan.feign.config.FeignConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(url = "http://localhost:8084/api/v1/auth",name = "auth-service", configuration = FeignConfiguration.class)
public interface IAuthServiceFeign {
    @GetMapping("/login")
    public GeneralRequestHeaderDto login(@RequestHeader String username, @RequestHeader String password);
}
