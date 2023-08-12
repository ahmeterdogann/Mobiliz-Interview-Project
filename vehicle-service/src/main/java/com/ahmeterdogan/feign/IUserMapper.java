package com.ahmeterdogan.feign;

import com.ahmeterdogan.data.entity.User;
import com.ahmeterdogan.dto.response.UserResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE, componentModel = "spring", uses = {})
public interface IUserMapper{
    @Mapping(source = "company.id", target = "companyId")
    @Mapping(source = "company.name", target = "companyName")
    UserResponseDTO toDto(User user);

    @Mapping(source = "companyId", target = "company.id")
    @Mapping(source = "companyName", target = "company.name")
    User toEntity(UserResponseDTO userResponseDTO);
}