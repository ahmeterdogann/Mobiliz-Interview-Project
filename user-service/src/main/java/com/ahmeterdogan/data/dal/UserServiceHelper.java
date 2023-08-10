package com.ahmeterdogan.data.dal;

import com.ahmeterdogan.data.entity.User;
import com.ahmeterdogan.data.repository.IUserRepository;
import jakarta.persistence.Column;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserServiceHelper {
    private final IUserRepository userRepository;

    public UserServiceHelper(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> getUserById(long id) {
        return userRepository.findById(id);
    }
}
