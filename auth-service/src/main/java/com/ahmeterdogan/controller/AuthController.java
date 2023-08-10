package com.ahmeterdogan.controller;

import com.ahmeterdogan.dto.response.UserAuthDTO;
import com.ahmeterdogan.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/login")
    public ResponseEntity<UserAuthDTO> login(@RequestHeader("username") String username,
                                             @RequestHeader("password") String password) {
        return ResponseEntity.ok(authService.login(username, password));
    }
}
