package com.ahmeterdogan.mapper;

import com.ahmeterdogan.data.entity.Group;
import com.ahmeterdogan.dto.GroupDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IGroupMapper {
    @Mapping(target = "companyId", source = "company.id")
    GroupDto toDto(Group group);

    @Mapping(target = "company.id", source = "companyId")
    Group toEntity(GroupDto groupDto);
}
