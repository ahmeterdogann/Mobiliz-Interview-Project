package com.ahmeterdogan.mapper;

import com.ahmeterdogan.data.entity.User;
import com.ahmeterdogan.dto.UserResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE, componentModel = "spring", uses = {})
public interface IUserMapper{
    @Mapping(source = "company.id", target = "companyId")
    @Mapping(source = "company.name", target = "companyName")
    UserResponseDTO toDto(User user);
}