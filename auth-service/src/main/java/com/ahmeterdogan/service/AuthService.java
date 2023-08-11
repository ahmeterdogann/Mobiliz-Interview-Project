package com.ahmeterdogan.service;

import com.ahmeterdogan.data.dal.AuthServiceHelper;
import com.ahmeterdogan.data.entity.AuthTable;
import com.ahmeterdogan.data.entity.Company;
import com.ahmeterdogan.data.entity.User;
import com.ahmeterdogan.dto.request.UserRegisterRequestDto;
import com.ahmeterdogan.dto.request.UserSaveDto;
import com.ahmeterdogan.dto.response.UserAuthResponseDto;
import com.ahmeterdogan.dto.response.UserRegisterResponseDto;
import com.ahmeterdogan.dto.response.UserResponseDto;
import com.ahmeterdogan.feign.ICompanyServiceFeign;
import com.ahmeterdogan.feign.IUserServiceFeign;
import com.ahmeterdogan.mapper.IAuthMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class AuthService {
    private final AuthServiceHelper authServiceHelper;
    private final IUserServiceFeign userServiceFeign;
    private final ICompanyServiceFeign companyServiceFeign;
    private final IAuthMapper authMapper;

    public AuthService(AuthServiceHelper authServiceHelper, IUserServiceFeign userServiceFeign, ICompanyServiceFeign companyServiceFeign, IAuthMapper authMapper) {
        this.authServiceHelper = authServiceHelper;
        this.userServiceFeign = userServiceFeign;
        this.companyServiceFeign = companyServiceFeign;
        this.authMapper = authMapper;
    }

    public UserAuthResponseDto login(String username, String password) {
        Optional<AuthTable> optionalAuthTable = Optional.ofNullable(authServiceHelper.findByUsernameAndPassword(username, password));
        UserAuthResponseDto userAuthResponseDto = new UserAuthResponseDto();
        if (optionalAuthTable.isPresent()) {
            AuthTable authTable = optionalAuthTable.get();
            UserResponseDto userResponseDto = userServiceFeign.getUserById(authTable.getUser().getId()).orElse(null);
            if (userResponseDto != null) {
                userAuthResponseDto = UserAuthResponseDto.builder()
                        .id(userResponseDto.getId())
                        .name(userResponseDto.getName())
                        .surname(userResponseDto.getSurname())
                        .companyId(userResponseDto.getCompanyId())
                        .companyName(userResponseDto.getCompanyName())
                        .roles(authTable.getRole())
                        .build();
            }
        }
        else {
            throw new RuntimeException("User not found");
        }

        return userAuthResponseDto;
    }

    //Transaction çalışmıyor anlayamadım bir türlü sanırım başka bir mikroservis çağrısındaki ayrı bir transaction'ı rollback etmesini beklediğim için
    @Transactional
    public UserRegisterResponseDto register(UserRegisterRequestDto userRegisterRequestDto) {
         Company company = companyServiceFeign.getCompanyByName(userRegisterRequestDto.getCompanyName());
         UserResponseDto userResponseDto = userServiceFeign.save(UserSaveDto.builder()
                 .name(userRegisterRequestDto.getName())
                 .surname(userRegisterRequestDto.getSurname())
                 .companyId(company.getId())
                 .companyName(company.getName())
                 .build());

         User user = authMapper.toUserEntity(userResponseDto);
         AuthTable authTable = authServiceHelper.save(userRegisterRequestDto.getUsername(), userRegisterRequestDto.getPassword(), user, userRegisterRequestDto.getRole());

         return UserRegisterResponseDto.builder()
                 .userId(user.getId())
                 .username(user.getName())
                 .name(user.getName())
                 .surname(user.getSurname())
                 .companyId(company.getId())
                 .companyName(company.getName())
                 .roles(authTable.getRole())
                 .build();

    }
}
