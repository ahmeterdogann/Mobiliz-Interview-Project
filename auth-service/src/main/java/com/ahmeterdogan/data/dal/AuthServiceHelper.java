package com.ahmeterdogan.data.dal;

import com.ahmeterdogan.data.entity.AuthTable;
import com.ahmeterdogan.data.repository.IUserAuthRepository;
import org.springframework.stereotype.Component;

@Component
public class AuthServiceHelper {
    private final IUserAuthRepository userAuthRepository;

    public AuthServiceHelper(IUserAuthRepository userAuthRepository) {
        this.userAuthRepository = userAuthRepository;
    }

    public AuthTable findByUsernameAndPassword(String username, String password) {
        return userAuthRepository.findByUsernameAndPassword(username, password);
    }
}
