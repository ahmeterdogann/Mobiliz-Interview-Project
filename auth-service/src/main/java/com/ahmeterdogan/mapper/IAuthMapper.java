package com.ahmeterdogan.mapper;

import com.ahmeterdogan.data.entity.AuthTable;
import com.ahmeterdogan.data.entity.User;
import com.ahmeterdogan.dto.request.UserRegisterRequestDto;
import com.ahmeterdogan.dto.response.UserResponseDto;
import org.mapstruct.Mapper;

@Mapper(unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE, componentModel = "spring")
public interface IAuthMapper {
    AuthTable toAuth(UserRegisterRequestDto dto);

    User toUserEntity(UserResponseDto dto);
}
