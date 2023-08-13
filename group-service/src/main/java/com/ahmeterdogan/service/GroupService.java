package com.ahmeterdogan.service;

import com.ahmeterdogan.data.dal.GroupServiceHelper;
import com.ahmeterdogan.data.entity.Group;
import com.ahmeterdogan.data.entity.User;
import com.ahmeterdogan.data.entity.UserGroupAuthorization;
import com.ahmeterdogan.data.enums.Roles;
import com.ahmeterdogan.dto.request.VehicleGroupUpdateDTO;
import com.ahmeterdogan.dto.response.GroupResponseDTO;
import com.ahmeterdogan.dto.request.GeneralRequestHeaderDTO;
import com.ahmeterdogan.dto.response.GroupVehicleTreeResponseDTO;
import com.ahmeterdogan.dto.response.VehicleResponseDTO;
import com.ahmeterdogan.dto.request.GroupSaveDTO;
import com.ahmeterdogan.exception.GroupServiceException;
import com.ahmeterdogan.feign.IUserServiceFeign;
import com.ahmeterdogan.feign.IVehicleServiceFeign;
import com.ahmeterdogan.mapper.IGroupMapper;
import com.ahmeterdogan.mapper.IUserMapper;
import com.ahmeterdogan.tree.TreeOfGroupOfUser;
import com.ahmeterdogan.tree.TreeOfGroupOfUserFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.ahmeterdogan.exception.ApiError.*;

@Service
public class GroupService {
    private final GroupServiceHelper groupServiceHelper;
    private final TreeOfGroupOfUserFactory treeOfGroupOfUserFactory;
    private final IVehicleServiceFeign vehicleServiceFeign;
    private final IUserServiceFeign userServiceFeign;
    private final ObjectMapper objectMapper;
    private final IGroupMapper groupMapper;
    private final IUserMapper userMapper;

    public GroupService(GroupServiceHelper groupServiceHelper, TreeOfGroupOfUserFactory treeOfGroupOfUserFactory, IVehicleServiceFeign vehicleServiceFeign, IUserServiceFeign userServiceFeign, ObjectMapper objectMapper, IGroupMapper groupMapper, IUserMapper userMapper) {
        this.groupServiceHelper = groupServiceHelper;
        this.treeOfGroupOfUserFactory = treeOfGroupOfUserFactory;
        this.vehicleServiceFeign = vehicleServiceFeign;
        this.userServiceFeign = userServiceFeign;
        this.objectMapper = objectMapper;
        this.groupMapper = groupMapper;
        this.userMapper = userMapper;
    }

    public GroupResponseDTO saveGroup(String generalRequestHeader, GroupSaveDTO groupSaveDTO) {
        GeneralRequestHeaderDTO generalRequestHeaderDto = generalHeaderRequestConverter(generalRequestHeader);

        if (groupSaveDTO.isRoot()) {
            if (!isAdmin(generalRequestHeaderDto))
                throw new GroupServiceException(CREATE_ROOT_GROUP_NOT_ALLOWED);

            groupSaveDTO.setParentId(null);

            Set<GroupResponseDTO> groupResponseDTOSet = groupServiceHelper.findRootGroupsByCompanyId(generalRequestHeaderDto.getCompanyId())
                    .stream()
                    .map(groupMapper::toDto)
                    .collect(Collectors.toSet());

            if (groupResponseDTOSet.stream().anyMatch(groupDto -> groupDto.getName().equals(groupSaveDTO.getName())))
                throw new GroupServiceException(GROUP_WITH_THIS_NAME_ALREADY_EXISTS);

            Group group = groupMapper.toEntity(groupSaveDTO);
            GroupResponseDTO savedRootGroup = groupMapper.toDto(groupServiceHelper.saveGroup(group));

            saveUserGroupAuthorization(generalRequestHeaderDto, group);

            return savedRootGroup;
        } else {
            GroupResponseDTO parentGroup = groupServiceHelper.getGroupByIdAndCompanyId(groupSaveDTO.getParentId(), generalRequestHeaderDto.getCompanyId())
                    .map(groupMapper::toDto)
                    .orElseThrow(() -> new GroupServiceException(PARENT_GROUP_NOT_FOUND));

            Set<GroupResponseDTO> groupResponseDTOSet = groupServiceHelper.getChildren(groupMapper.toEntity(parentGroup))
                    .stream()
                    .map(groupMapper::toDto)
                    .collect(Collectors.toSet());

            if (groupResponseDTOSet.stream().anyMatch(groupDto -> groupDto.getName().equals(groupSaveDTO.getName())))
                throw new GroupServiceException(GROUP_WITH_THIS_NAME_ALREADY_EXISTS);

            if (isUserAuthorizedParentGroup(generalRequestHeader, parentGroup))
                throw new GroupServiceException(USER_NOT_AUTHORIZED_PARENT_GROUP);

            GroupResponseDTO savedChildGroup = groupMapper.toDto(groupServiceHelper.saveGroup(groupMapper.toEntity(groupSaveDTO)));

            saveGroupToGroup(parentGroup, savedChildGroup);

            return savedChildGroup;
        }
    }

    public Set<GroupVehicleTreeResponseDTO> getTreeOfGroupUserWithVehicle(String generalRequestHeader) {
        GeneralRequestHeaderDTO generalRequestHeaderDTO = generalHeaderRequestConverter(generalRequestHeader);
        TreeOfGroupOfUser treeOfGroupOfUser = treeOfGroupOfUserFactory.createTreeOfGroupOfUser(generalRequestHeader);

        return treeOfGroupOfUser.buildTreeOfGroupOfUserWithVehicle(generalRequestHeaderDTO.getUserId());
    }

        public GroupResponseDTO getGroupByIdAndCompanyId(String generalRequestHeader, long id) {
        GeneralRequestHeaderDTO generalRequestHeaderDto = generalHeaderRequestConverter(generalRequestHeader);

        return groupServiceHelper.getGroupByIdAndCompanyId(id, generalRequestHeaderDto.getCompanyId())
                .map(groupMapper::toDto)
                .orElseThrow(() -> new GroupServiceException(GROUP_NOT_FOUND));
    }


    public Set<VehicleResponseDTO> getUserVehicleList(String generalRequestHeader, long userId) {
        GeneralRequestHeaderDTO generalRequestHeaderDTO = generalHeaderRequestConverter(generalRequestHeader);

        if (generalRequestHeaderDTO.getRole() != Roles.COMPANY_ADMIN)
            throw new GroupServiceException(USER_NOT_COMPANY_ADMIN);

        TreeOfGroupOfUser treeOfGroupOfUser = treeOfGroupOfUserFactory.createTreeOfGroupOfUser(generalRequestHeader);

        return treeOfGroupOfUser.getListOfVehiclesOfUserForSpecificUser(userId);
    }

    public Set<VehicleResponseDTO> getUserVehicleList(String generalRequestHeader) {
        GeneralRequestHeaderDTO generalRequestHeaderDTO = generalHeaderRequestConverter(generalRequestHeader);
        TreeOfGroupOfUser treeOfGroupOfUser = treeOfGroupOfUserFactory.createTreeOfGroupOfUser(generalRequestHeader);

        return treeOfGroupOfUser.getListOfVehiclesOfUser(generalRequestHeaderDTO.getUserId());
    }

    public Set<GroupResponseDTO> getListOfGroupOfUser(String generalRequestHeader) {
        try {
            GeneralRequestHeaderDTO generalRequestHeaderDto = objectMapper.readValue(generalRequestHeader, GeneralRequestHeaderDTO.class);
            TreeOfGroupOfUser treeOfGroupOfUser = treeOfGroupOfUserFactory.createTreeOfGroupOfUser(generalRequestHeader);
            return treeOfGroupOfUser.getListOfGroupOfUser(generalRequestHeaderDto.getUserId());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
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


    private boolean isUserAuthorizedParentGroup(String generalRequestHeader, GroupResponseDTO parentGroup) {
        TreeOfGroupOfUser treeOfGroupOfUser = treeOfGroupOfUserFactory.createTreeOfGroupOfUser(generalRequestHeader);
        GeneralRequestHeaderDTO generalRequestHeaderDto = generalHeaderRequestConverter(generalRequestHeader);

        Set<GroupResponseDTO> groupResponseDTOSet = treeOfGroupOfUser.getListOfGroupOfUser(generalRequestHeaderDto.getUserId());

        return groupResponseDTOSet.stream().anyMatch(groupDto -> groupDto.getId().equals(parentGroup.getId()));
    }

    @Transactional //transactional burda da çalışmıyor
    //bu metodu yazmaya çalışırken kriz geçirdim :)
    public void deleteGroupByIdAndCompanyId(String generalRequestHeader, long id) {
        GeneralRequestHeaderDTO generalRequestHeaderDto = generalHeaderRequestConverter(generalRequestHeader);

        if (!isAdmin(generalRequestHeaderDto))
            throw new GroupServiceException(USER_NOT_COMPANY_ADMIN);

        Group group = groupServiceHelper.getGroupByIdAndCompanyId(id, generalRequestHeaderDto.getCompanyId())
                .orElseThrow(() -> new GroupServiceException(GROUP_NOT_FOUND));

        if (group.isRoot())
            throw new GroupServiceException(CANNOT_DELETE_ROOT_GROUP);
        else {
            Group parent = groupServiceHelper.getParent(group)
                    .orElseThrow(() -> new GroupServiceException(PARENT_GROUP_NOT_FOUND));

            Set<Group> children = groupServiceHelper.getChildren(group);
            updateVehiclesByParentId(generalRequestHeader, parent, group);

            if (!children.isEmpty()) {
                children.forEach(child -> groupServiceHelper.saveGroupToGroup(parent, child));
            }

            groupServiceHelper.deleteGroupToGroupByParentIdOrChildId(group, group);
            groupServiceHelper.deleteUserGroupAuthByGroupId(group.getId());
            groupServiceHelper.deleteGroup(group);
        }
    }

    private void updateVehiclesByParentId(String generalRequestHeader, Group parentGroup, Group childGroup) {
        List<VehicleGroupUpdateDTO> vehicleGroupUpdateDTOList = vehicleServiceFeign.getAllVehiclesByGroupId(generalRequestHeader, childGroup.getId())
                .stream()
                .map(v -> VehicleGroupUpdateDTO.builder()
                        .groupId(parentGroup.getId())
                        .id(v.getId())
                        .build())
                .toList();

        vehicleGroupUpdateDTOList.stream()
                .forEach(vehicleGroupUpdateDTO -> vehicleServiceFeign.updateVehicleGroup(generalRequestHeader, vehicleGroupUpdateDTO));
    }

    public void authorizeUserToGroup(String generalRequestHeader, Long userId, Long groupId) {
        GeneralRequestHeaderDTO generalRequestHeaderDto = generalHeaderRequestConverter(generalRequestHeader);

        if (!isAdmin(generalRequestHeaderDto))
            throw new GroupServiceException(USER_NOT_COMPANY_ADMIN);

        User user = userServiceFeign.getUserById(userId)
                .map(userMapper::toEntity)
                .orElseThrow(() -> new GroupServiceException(USER_NOT_FOUND));

        long adminCompanyId = generalRequestHeaderDto.getCompanyId();
        long userCompanyId = user.getCompany().getId();

        if (adminCompanyId != userCompanyId)
            throw new GroupServiceException(USER_NOT_FOUND_IN_COMPANY);

        Group group = groupServiceHelper.getGroupByIdAndCompanyId(groupId, generalRequestHeaderDto.getCompanyId())
                .orElseThrow(() -> new GroupServiceException(GROUP_NOT_FOUND));

        GroupResponseDTO dto = groupMapper.toDto(group);
        //Kullanıcının hali hazırda yetkili olduğu bir grubun parent'ına yetki verince o parent altındaki tüm children'lara yönelik yetki kaydı siliniyor.
        removeAuthForAllChildren(generalRequestHeader, dto);

        TreeOfGroupOfUser treeOfGroupOfUser = treeOfGroupOfUserFactory.createTreeOfGroupOfUser(generalRequestHeader);

        treeOfGroupOfUser.getListOfGroupOfUser(userId)
                .stream()
                .filter(groupDto -> groupDto.getId().equals(groupId))
                .findFirst()
                .ifPresent(groupDto -> {
                    throw new GroupServiceException(USER_ALREADY_AUTHORIZED_TO_GROUP);
                });

        UserGroupAuthorization userGroupAuthorization = UserGroupAuthorization.builder()
                .user(user)
                .group(group)
                .build();

        groupServiceHelper.saveUserGroupAuth(userGroupAuthorization);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void removeAuthForAllChildren(String generalRequestHeader, GroupResponseDTO parent) {
        TreeOfGroupOfUser treeOfGroupOfUser = treeOfGroupOfUserFactory.createTreeOfGroupOfUser(generalRequestHeader);

        treeOfGroupOfUser.buildTreeOfGroupForSpecificGroup(parent);
        Set<GroupResponseDTO> allChildren = treeOfGroupOfUser.getAllGroupsInTree();

        allChildren.forEach(child -> {
                    if (parent.getId() != child.getId())
                        groupServiceHelper.deleteUserGroupAuthByGroupId(child.getId());
                });
    }
}
