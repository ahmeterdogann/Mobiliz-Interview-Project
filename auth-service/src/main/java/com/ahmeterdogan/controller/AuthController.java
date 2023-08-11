package com.ahmeterdogan.controller;

import com.ahmeterdogan.dto.request.UserRegisterRequestDto;
import com.ahmeterdogan.dto.response.UserAuthResponseDto;
import com.ahmeterdogan.dto.response.UserRegisterResponseDto;
import com.ahmeterdogan.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/login")
    public ResponseEntity<UserAuthResponseDto> login(@RequestHeader("username") String username,
                                                     @RequestHeader("password") String password) {
        return ResponseEntity.ok(authService.login(username, password));
    }

    @PostMapping("/register")
    public ResponseEntity<UserRegisterResponseDto> register(@RequestBody UserRegisterRequestDto userRegisterRequestDto) {
        return ResponseEntity.ok(authService.register(userRegisterRequestDto));
    }
}
