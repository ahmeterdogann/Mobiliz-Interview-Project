package com.ahmeterdogan.controller;

import com.ahmeterdogan.dto.request.UserRegisterRequestDto;
import com.ahmeterdogan.dto.response.GeneralRequestHeaderDTO;
import com.ahmeterdogan.dto.response.UserRegisterResponseDTO;
import com.ahmeterdogan.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.ahmeterdogan.constants.ApiUrl.*;

import java.util.List;

@RestController
@RequestMapping(AUTH)
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping(LOGIN)
    public ResponseEntity<GeneralRequestHeaderDTO> login(@RequestHeader("username") String username,
                                                         @RequestHeader("password") String password) {
        return ResponseEntity.ok(authService.login(username, password));
    }

    @PostMapping(REGISTER)
    public ResponseEntity<UserRegisterResponseDTO> register(@RequestHeader("X-User") String generalRequestHeader,
                                                            @RequestBody @Valid UserRegisterRequestDto userRegisterRequestDto) {
        return ResponseEntity.ok(authService.register(generalRequestHeader, userRegisterRequestDto));
    }

    @PostMapping(REGISTER_ALL)
    public ResponseEntity<List<UserRegisterResponseDTO>> registerAll(@RequestHeader("X-User") String generalRequestHeader,
                                                            @RequestBody @Valid List<UserRegisterRequestDto> userRegisterRequestDtoList) {
        //int k = 7 / 0;
        return ResponseEntity.ok(authService.registerAll(generalRequestHeader, userRegisterRequestDtoList));
    }
}
