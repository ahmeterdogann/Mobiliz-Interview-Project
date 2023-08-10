package com.ahmeterdogan.service;

import com.ahmeterdogan.data.dal.AuthServiceHelper;
import com.ahmeterdogan.data.entity.AuthTable;
import com.ahmeterdogan.dto.request.UserDTORequest;
import com.ahmeterdogan.dto.response.UserAuthDTO;
import com.ahmeterdogan.feign.IUserServiceFeign;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    private final AuthServiceHelper authServiceHelper;
    private final IUserServiceFeign userServiceFeign;

    public AuthService(AuthServiceHelper authServiceHelper, IUserServiceFeign userServiceFeign) {
        this.authServiceHelper = authServiceHelper;
        this.userServiceFeign = userServiceFeign;
    }

    public UserAuthDTO login(String username, String password) {
        Optional<AuthTable> optionalAuthTable = Optional.ofNullable(authServiceHelper.findByUsernameAndPassword(username, password));
        UserAuthDTO userAuthDTO = new UserAuthDTO();
        if (optionalAuthTable.isPresent()) {
            AuthTable authTable = optionalAuthTable.get();
            UserDTORequest userDTORequest = userServiceFeign.getUserById(authTable.getUser().getId()).orElse(null);
            if (userDTORequest != null) {
                userAuthDTO = UserAuthDTO.builder()
                        .id(userDTORequest.getId())
                        .name(userDTORequest.getName())
                        .surname(userDTORequest.getSurname())
                        .companyId(userDTORequest.getCompanyId())
                        .companyName(userDTORequest.getCompanyName())
                        .roles(authTable.getRole())
                        .build();
            }
        }
        else {
            throw new RuntimeException("User not found");
        }

        return userAuthDTO;
    }
}
