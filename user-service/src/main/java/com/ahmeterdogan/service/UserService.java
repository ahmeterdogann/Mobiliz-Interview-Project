package com.ahmeterdogan.service;

import com.ahmeterdogan.dto.request.UserSaveRequestDto;
import com.ahmeterdogan.dto.response.UserResponseDTO;
import com.ahmeterdogan.mapper.IUserMapper;
import org.springframework.stereotype.Service;
import com.ahmeterdogan.data.dal.UserServiceHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserServiceHelper userServiceHelper;
    private final IUserMapper userMapper;

    public UserService(UserServiceHelper userServiceHelper, IUserMapper userMapper) {
        this.userServiceHelper = userServiceHelper;
        this.userMapper = userMapper;
    }

    public Optional<UserResponseDTO> getUserById(long id) {
        return userServiceHelper.getUserById(id).map(userMapper::toDto);
    }

    public UserResponseDTO save(UserSaveRequestDto userSaveRequestDto) {
         return userMapper.toDto(userServiceHelper.save(userMapper.toEntity(userSaveRequestDto)));
    }

    public List<UserResponseDTO> saveAll(List<UserSaveRequestDto> userSaveRequestDto) {
        List<UserResponseDTO> savedUser = new ArrayList<>();

        userSaveRequestDto.forEach(userSaveDto -> {
            savedUser.add(userMapper.toDto(userServiceHelper.save(userMapper.toEntity(userSaveDto))));
        });

        return savedUser;
    }
}
