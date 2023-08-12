package com.ahmeterdogan.mapper;

import com.ahmeterdogan.data.entity.Group;
import com.ahmeterdogan.dto.response.GroupResponseDTO;
import com.ahmeterdogan.dto.request.GroupSaveDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, implementationName = "GroupMapperImpl", componentModel = "spring")
public interface IGroupMapper {
    @Mapping(target = "companyId", source = "company.id")
    GroupResponseDTO toDto(Group group);

    @Mapping(target = "company.id", source = "companyId")
    Group toEntity(GroupResponseDTO groupResponseDTO);

    @Mapping(target = "company.id", source = "companyId")
    Group toEntity(GroupSaveDTO groupSaveDTO);
}
