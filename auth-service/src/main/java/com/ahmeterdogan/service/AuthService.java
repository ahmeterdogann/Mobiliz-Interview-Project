package com.ahmeterdogan.service;

import com.ahmeterdogan.data.dal.AuthServiceHelper;
import com.ahmeterdogan.data.entity.AuthTable;
import com.ahmeterdogan.data.entity.User;
import com.ahmeterdogan.data.enums.Roles;
import com.ahmeterdogan.dto.request.UserRegisterRequestDto;
import com.ahmeterdogan.dto.request.UserSaveDTO;
import com.ahmeterdogan.dto.response.GeneralRequestHeaderDTO;
import com.ahmeterdogan.dto.response.UserRegisterResponseDTO;
import com.ahmeterdogan.dto.response.UserResponseDTO;
import com.ahmeterdogan.exception.AuthServiceException;
import com.ahmeterdogan.feign.IUserServiceFeign;
import com.ahmeterdogan.mapper.IAuthMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ahmeterdogan.exception.ApiErrorMessages;

import static com.ahmeterdogan.exception.ApiErrorMessages.*;

import java.util.Optional;

@Service
public class AuthService {
    private final AuthServiceHelper authServiceHelper;
    private final IUserServiceFeign userServiceFeign;
    private final IAuthMapper authMapper;
    private final ObjectMapper objectMapper;

    public AuthService(AuthServiceHelper authServiceHelper, IUserServiceFeign userServiceFeign, IAuthMapper authMapper, ObjectMapper objectMapper) {
        this.authServiceHelper = authServiceHelper;
        this.userServiceFeign = userServiceFeign;
        this.authMapper = authMapper;
        this.objectMapper = objectMapper;
    }

    public GeneralRequestHeaderDTO login(String username, String password) {
        Optional<AuthTable> optionalAuthTable = Optional.ofNullable(authServiceHelper.findByUsernameAndPassword(username, password));
        GeneralRequestHeaderDTO generalRequestHeaderDTO = new GeneralRequestHeaderDTO();
        if (optionalAuthTable.isPresent()) {
            AuthTable authTable = optionalAuthTable.get();
            UserResponseDTO userResponseDto = userServiceFeign.getUserById(authTable.getUser().getId()).orElse(null);
            if (userResponseDto != null) {
                generalRequestHeaderDTO = GeneralRequestHeaderDTO.builder()
                        .userId(userResponseDto.getId())
                        .name(userResponseDto.getName())
                        .surname(userResponseDto.getSurname())
                        .companyId(userResponseDto.getCompanyId())
                        .companyName(userResponseDto.getCompanyName())
                        .role(authTable.getRole())
                        .build();
            }
        }
        else {
            throw new AuthServiceException(ApiErrorMessages.USER_NOT_FOUND);
        }

        return generalRequestHeaderDTO;
    }

    //Transaction çalışmıyor anlayamadım bir türlü sanırım başka bir mikroservis çağrısındaki ayrı bir transaction'ı rollback etmesini beklediğim için
    @Transactional
    public UserRegisterResponseDTO register(String generalRequestHeader, UserRegisterRequestDto userRegisterRequestDto) {
        GeneralRequestHeaderDTO generalRequestHeaderDTO = generalHeaderRequestConverter(generalRequestHeader);

        if (!isAdmin(generalRequestHeaderDTO))
            throw new AuthServiceException(REGISTER_NOT_ALLOWED);

        if (!userRegisterRequestDto.getCompanyName().equals(generalRequestHeaderDTO.getCompanyName()))
            throw new AuthServiceException(COMPANY_NAME_NOT_MATCH);


         UserResponseDTO userResponseDTO = userServiceFeign.save(UserSaveDTO.builder()
                 .name(userRegisterRequestDto.getName())
                 .surname(userRegisterRequestDto.getSurname())
                 .companyId(generalRequestHeaderDTO.getCompanyId())
                 .companyName(generalRequestHeaderDTO.getCompanyName())
                 .build());

         User user = authMapper.toUserEntity(userResponseDTO);
         AuthTable authTable = authServiceHelper.save(userRegisterRequestDto.getUsername(), userRegisterRequestDto.getPassword(), user, userRegisterRequestDto.getRole());

         return UserRegisterResponseDTO.builder()
                 .userId(user.getId())
                 .username(user.getName())
                 .name(user.getName())
                 .surname(user.getSurname())
                 .companyId(generalRequestHeaderDTO.getCompanyId())
                 .companyName(generalRequestHeaderDTO.getCompanyName())
                 .roles(authTable.getRole())
                 .build();

    }

    private boolean isAdmin(GeneralRequestHeaderDTO generalRequestHeaderDto) {
        return generalRequestHeaderDto.getRole() == Roles.COMPANY_ADMIN;
    }

    private GeneralRequestHeaderDTO generalHeaderRequestConverter(String generalRequestHeader) {
        try {
            GeneralRequestHeaderDTO generalRequestHeaderDto = objectMapper.readValue(generalRequestHeader, GeneralRequestHeaderDTO.class);
            return generalRequestHeaderDto;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
