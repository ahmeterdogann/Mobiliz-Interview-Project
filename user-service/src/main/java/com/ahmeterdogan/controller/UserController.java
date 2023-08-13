package com.ahmeterdogan.controller;

import com.ahmeterdogan.dto.request.UserSaveRequestDto;
import com.ahmeterdogan.dto.response.UserResponseDTO;
import com.ahmeterdogan.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.ahmeterdogan.constants.ApiUrl.*;

@RestController
@RequestMapping(USERS)
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(FIND_BY_ID)
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable long id) {
        return userService.getUserById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(SAVE)
    public ResponseEntity<UserResponseDTO> save(@RequestBody UserSaveRequestDto userSaveRequestDto) {
        return ResponseEntity.ok(userService.save(userSaveRequestDto));
    }

    @PostMapping(SAVE_ALL)
    public ResponseEntity<List<UserResponseDTO>> save(@RequestBody List<UserSaveRequestDto> userSaveRequestDto) {
        return ResponseEntity.ok(userService.saveAll(userSaveRequestDto));
    }
}
