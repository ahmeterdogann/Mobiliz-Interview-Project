package com.ahmeterdogan.controller;

import com.ahmeterdogan.dto.request.UserRegisterRequestDto;
import com.ahmeterdogan.dto.response.GeneralRequestHeaderDTO;
import com.ahmeterdogan.dto.response.UserRegisterResponseDTO;
import com.ahmeterdogan.service.AuthService;
import jakarta.validation.Valid;
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
    public ResponseEntity<GeneralRequestHeaderDTO> login(@RequestHeader("username") String username,
                                                         @RequestHeader("password") String password) {
        return ResponseEntity.ok(authService.login(username, password));
    }

    @PostMapping("/register")
    public ResponseEntity<UserRegisterResponseDTO> register(@RequestHeader("X-User") String generalRequestHeader,
                                                            @RequestBody @Valid UserRegisterRequestDto userRegisterRequestDto) {
        return ResponseEntity.ok(authService.register(generalRequestHeader, userRegisterRequestDto));
    }
}
