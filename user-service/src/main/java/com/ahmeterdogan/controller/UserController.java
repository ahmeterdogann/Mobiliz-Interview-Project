package com.ahmeterdogan.controller;

import com.ahmeterdogan.dto.request.UserSaveRequestDto;
import com.ahmeterdogan.dto.response.UserResponseDto;
import com.ahmeterdogan.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable long id) {
        return userService.getUserById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/save")
    public ResponseEntity<UserResponseDto> save(@RequestBody UserSaveRequestDto userSaveRequestDto) {
        return ResponseEntity.ok(userService.save(userSaveRequestDto));
    }
}
