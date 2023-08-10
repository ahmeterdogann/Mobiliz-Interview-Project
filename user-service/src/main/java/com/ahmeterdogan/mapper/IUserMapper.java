package com.ahmeterdogan.mapper;

import com.ahmeterdogan.data.entity.User;
import com.ahmeterdogan.dto.response.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {})
public interface IUserMapper extends EntityMapper<UserDTO, User> {
    @Mapping(source = "company.id", target = "companyId")
    @Mapping(source = "company.name", target = "companyName")
    UserDTO toDto(User user);
}
