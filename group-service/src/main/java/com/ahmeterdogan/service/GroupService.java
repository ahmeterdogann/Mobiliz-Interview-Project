package com.ahmeterdogan.service;

import com.ahmeterdogan.data.dal.GroupServiceHelper;
import com.ahmeterdogan.data.entity.Group;
import com.ahmeterdogan.data.entity.User;
import com.ahmeterdogan.data.entity.UserGroupAuthorization;
import com.ahmeterdogan.data.enums.Roles;
import com.ahmeterdogan.dto.response.GroupResponseDTO;
import com.ahmeterdogan.dto.request.GeneralRequestHeaderDTO;
import com.ahmeterdogan.dto.GroupVehicleTreeDTO;
import com.ahmeterdogan.dto.VehicleDTO;
import com.ahmeterdogan.dto.request.GroupSaveDTO;
import com.ahmeterdogan.mapper.IGroupMapper;
import com.ahmeterdogan.tree.TreeOfGroupOfUser;
import com.ahmeterdogan.tree.TreeOfGroupOfUserFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class GroupService {
    private final GroupServiceHelper groupServiceHelper;
    private final TreeOfGroupOfUserFactory treeOfGroupOfUserFactory;
    private final ObjectMapper objectMapper;
    private final IGroupMapper groupMapper;

    public GroupService(GroupServiceHelper groupServiceHelper, TreeOfGroupOfUserFactory treeOfGroupOfUserFactory, ObjectMapper objectMapper, IGroupMapper groupMapper) {
        this.groupServiceHelper = groupServiceHelper;
        this.treeOfGroupOfUserFactory = treeOfGroupOfUserFactory;
        this.objectMapper = objectMapper;
        this.groupMapper = groupMapper;
    }

    public GroupResponseDTO saveGroup(String generalRequestHeader, GroupSaveDTO groupSaveDTO) {
        GeneralRequestHeaderDTO generalRequestHeaderDto = generalHeaderRequestConverter(generalRequestHeader);

        if (groupSaveDTO.isRoot()) {
            if (!isAdmin(generalRequestHeaderDto))
                throw new RuntimeException("You are not authorized to create root group");

            groupSaveDTO.setParentId(null);

            Set<GroupResponseDTO> groupResponseDTOSet = groupServiceHelper.findRootGroupsByCompanyId(generalRequestHeaderDto.getCompanyId())
                    .stream()
                    .map(groupMapper::toDto)
                    .collect(Collectors.toSet());

            if (groupResponseDTOSet.stream().anyMatch(groupDto -> groupDto.getName().equals(groupSaveDTO.getName())))
                throw new RuntimeException("Root group with this name already exists");

            Group group = groupMapper.toEntity(groupSaveDTO);
            GroupResponseDTO savedRootGroup = groupMapper.toDto(groupServiceHelper.saveGroup(group));

            saveUserGroupAuthorization(generalRequestHeaderDto, group);

            return savedRootGroup;
        } else {
            GroupResponseDTO parentGroup = groupServiceHelper.getGroupByIdAndCompanyId(groupSaveDTO.getParentId(), generalRequestHeaderDto.getCompanyId())
                    .map(groupMapper::toDto)
                    .orElseThrow(() -> new RuntimeException("Parent group not found"));

            Set<GroupResponseDTO> groupResponseDTOSet = groupServiceHelper.getChildren(groupMapper.toEntity(parentGroup))
                    .stream()
                    .map(groupMapper::toDto)
                    .collect(Collectors.toSet());

            if (groupResponseDTOSet.stream().anyMatch(groupDto -> groupDto.getName().equals(groupSaveDTO.getName())))
                throw new RuntimeException("Group with this name already exists under this group");

            if (isUserAuthorizedParentGroup(generalRequestHeaderDto.getUserId(), parentGroup))
                throw new RuntimeException("You are not authorized to create group under this group");

            GroupResponseDTO savedChildGroup = groupMapper.toDto(groupServiceHelper.saveGroup(groupMapper.toEntity(groupSaveDTO)));

            saveGroupToGroup(parentGroup, savedChildGroup);

            return savedChildGroup;
        }
    }

    public Set<GroupVehicleTreeDTO> getTreeOfGroupUserWithVehicle(long userId) {
        TreeOfGroupOfUser treeOfGroupOfUser = treeOfGroupOfUserFactory.createTreeOfGroupOfUser();
        return treeOfGroupOfUser.buildTreeOfGroupOfUserWithVehicle(userId);
    }


    public Set<VehicleDTO> getUserVehicleList(long userId) {
        TreeOfGroupOfUser treeOfGroupOfUser = treeOfGroupOfUserFactory.createTreeOfGroupOfUser();
        return treeOfGroupOfUser.getListOfVehiclesOfUser(userId);
    }

    public Set<GroupResponseDTO> getListOfGroupOfUser(String generalRequestHeader) {
        try {
            GeneralRequestHeaderDTO generalRequestHeaderDto = objectMapper.readValue(generalRequestHeader, GeneralRequestHeaderDTO.class);
            TreeOfGroupOfUser treeOfGroupOfUser = treeOfGroupOfUserFactory.createTreeOfGroupOfUser();
            return treeOfGroupOfUser.getListOfGroupOfUser(generalRequestHeaderDto.getUserId());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean isAdmin(GeneralRequestHeaderDTO generalRequestHeaderDto) {
        return generalRequestHeaderDto.getRoles() == Roles.COMPANY_ADMIN;
    }

    private GeneralRequestHeaderDTO generalHeaderRequestConverter(String generalRequestHeader) {
        try {
            GeneralRequestHeaderDTO generalRequestHeaderDto = objectMapper.readValue(generalRequestHeader, GeneralRequestHeaderDTO.class);
            return generalRequestHeaderDto;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private void saveUserGroupAuthorization(GeneralRequestHeaderDTO generalRequestHeaderDTO, Group group) {
        User user = new User();
        user.setId(generalRequestHeaderDTO.getUserId());
        UserGroupAuthorization userGroupAuthorization = UserGroupAuthorization.builder()
                .user(user)
                .group(group)
                .build();

        groupServiceHelper.saveUserGroupAuth(userGroupAuthorization);
    }

    private void saveGroupToGroup(GroupResponseDTO parent, GroupResponseDTO child) {
        groupServiceHelper.saveGroupToGroup(groupMapper.toEntity(parent), groupMapper.toEntity(child));
    }


    private boolean isUserAuthorizedParentGroup(long userId, GroupResponseDTO parentGroup) {
        TreeOfGroupOfUser treeOfGroupOfUser = treeOfGroupOfUserFactory.createTreeOfGroupOfUser();

        Set<GroupResponseDTO> groupResponseDTOSet = treeOfGroupOfUser.getListOfGroupOfUser(userId);

        return groupResponseDTOSet.stream().anyMatch(groupDto -> groupDto.getId().equals(parentGroup.getId()));
    }

    public Optional<GroupResponseDTO> getGroupByIdAndCompanyId(String generalRequestHeader, long id) {
        GeneralRequestHeaderDTO generalRequestHeaderDto = generalHeaderRequestConverter(generalRequestHeader);

        return groupServiceHelper.getGroupByIdAndCompanyId(id, generalRequestHeaderDto.getCompanyId()).map(groupMapper::toDto);
    }
}
