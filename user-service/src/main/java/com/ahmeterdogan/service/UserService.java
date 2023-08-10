package com.ahmeterdogan.service;

import com.ahmeterdogan.data.entity.User;
import com.ahmeterdogan.dto.response.UserDTO;
import com.ahmeterdogan.mapper.IUserMapper;
import org.springframework.stereotype.Service;
import com.ahmeterdogan.data.dal.UserServiceHelper;

import java.util.Optional;

@Service
public class UserService {
    private final UserServiceHelper userServiceHelper;
    private final IUserMapper userMapper;

    public UserService(UserServiceHelper userServiceHelper, IUserMapper userMapper) {
        this.userServiceHelper = userServiceHelper;
        this.userMapper = userMapper;
    }

    public Optional<UserDTO> getUserById(long id) {
        return userServiceHelper.getUserById(id).map(userMapper::toDto);
    }
}
