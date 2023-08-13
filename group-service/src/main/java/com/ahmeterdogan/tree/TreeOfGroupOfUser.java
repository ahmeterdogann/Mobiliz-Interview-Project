package com.ahmeterdogan.tree;

import com.ahmeterdogan.data.dal.GroupServiceHelper;
import com.ahmeterdogan.dto.response.GroupResponseDTO;
import com.ahmeterdogan.dto.GroupTreeDto;
import com.ahmeterdogan.dto.response.GroupVehicleTreeResponseDTO;
import com.ahmeterdogan.dto.response.VehicleResponseDTO;
import com.ahmeterdogan.feign.IVehicleServiceFeign;
import com.ahmeterdogan.mapper.IGroupMapper;
import lombok.Getter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Scope("prototype")
@Getter
public class TreeOfGroupOfUser {
    private final GroupServiceHelper groupServiceHelper;
    private final IVehicleServiceFeign vehicleServiceFeign;
    private final IGroupMapper groupMapper;
    private final Set<VehicleResponseDTO> allVehiclesInTree = new HashSet<>();
    private final Set<GroupResponseDTO> allGroupsInTree = new HashSet<>();
    private String generalRequestHeader;

    public TreeOfGroupOfUser(GroupServiceHelper groupServiceHelper, IVehicleServiceFeign vehicleServiceFeign, IGroupMapper groupMapper) {
        this.groupServiceHelper = groupServiceHelper;
        this.vehicleServiceFeign = vehicleServiceFeign;
        this.groupMapper = groupMapper;
    }

    public Set<GroupResponseDTO> getChildren(GroupResponseDTO groupResponseDTO) {
        return groupServiceHelper.getChildren(groupMapper.toEntity(groupResponseDTO)).stream().map(groupMapper::toDto).collect(Collectors.toSet());
    }

    private Set<GroupResponseDTO> getUserGroupAuth(long userId) {
        return groupServiceHelper.getUserGroupAuth(userId).stream().map(groupMapper::toDto).collect(Collectors.toSet());
    }

    public Set<GroupVehicleTreeResponseDTO> buildTreeOfGroupOfUserWithVehicle(long userId) {
        Set<GroupResponseDTO> groups = getUserGroupAuth(userId);
        Set<GroupVehicleTreeResponseDTO> trees = new HashSet<>();

        groups.forEach(group -> {
            allGroupsInTree.add(group);
            trees.add(convertToGroupVehicleDTO(group));
        });

        return trees;
    }

    private GroupVehicleTreeResponseDTO convertToGroupVehicleDTO(GroupResponseDTO groupResponseDTO) {
        GroupVehicleTreeResponseDTO groupVehicleTreeResponseDTO = new GroupVehicleTreeResponseDTO(groupResponseDTO.getId(), groupResponseDTO.getName());
        Set<GroupResponseDTO> childrenGroups = getChildren(groupResponseDTO);

        childrenGroups.forEach(childGroup -> {
            allGroupsInTree.add(childGroup);
            GroupVehicleTreeResponseDTO childDto = convertToGroupVehicleDTO(childGroup);
            groupVehicleTreeResponseDTO.addChild(childDto);
        });

        List<VehicleResponseDTO> vehicles = vehicleServiceFeign.getAllVehiclesByGroupId(generalRequestHeader, groupResponseDTO.getId());
        groupVehicleTreeResponseDTO.setVehiclesOfGroup(vehicles);
        allVehiclesInTree.addAll(vehicles);
        return groupVehicleTreeResponseDTO;
    }

    public Set<GroupTreeDto> buildTreeOfGroupOfUser(long userId) {
        Set<GroupResponseDTO> groups = getUserGroupAuth(userId);
        Set<GroupTreeDto> trees = new HashSet<>();

        groups.forEach(group -> {
            allGroupsInTree.add(group);
            trees.add(convertToGroupDto(group));
        });

        return trees;
    }

    private GroupTreeDto convertToGroupDto(GroupResponseDTO groupResponseDTO) {
        GroupTreeDto groupTreeDto = new GroupTreeDto(groupResponseDTO.getId(), groupResponseDTO.getName());

        Set<GroupResponseDTO> childGroups = getChildren(groupResponseDTO);

        childGroups.forEach(childGroup -> {
            allGroupsInTree.add(childGroup);
            GroupTreeDto childDto = convertToGroupDto(childGroup);
            groupTreeDto.addChild(childDto);
        });

        return groupTreeDto;
    }

    public void buildTreeOfGroupForSpecificGroup(GroupResponseDTO groupResponseDTO) {
        Set<GroupResponseDTO> groups = groupServiceHelper.getChildren(groupMapper.toEntity(groupResponseDTO))
                .stream()
                .map(groupMapper::toDto)
                .collect(Collectors.toSet());

        Set<GroupTreeDto> trees = new HashSet<>();

        groups.forEach(group -> {
                    allGroupsInTree.add(group);
                    trees.add(convertToGroupDto(groupResponseDTO));

        });
    }

    public Set<VehicleResponseDTO> getListOfVehiclesOfUser(long userId) {
        buildTreeOfGroupOfUserWithVehicle(userId);
        Set<VehicleResponseDTO> allVehiclesOfUser = new HashSet<>(allVehiclesInTree);

        List<VehicleResponseDTO> directlyAuthorizedVehicles = vehicleServiceFeign.getDirectlyAuthorizedVehicle(generalRequestHeader);
        allVehiclesOfUser.addAll(directlyAuthorizedVehicles);

        return allVehiclesOfUser;
    }

    public Set<VehicleResponseDTO> getListOfVehiclesOfUserForSpecificUser(long userId) {
        buildTreeOfGroupOfUserWithVehicle(userId);
        Set<VehicleResponseDTO> allVehiclesOfUser = new HashSet<>(allVehiclesInTree);

        List<VehicleResponseDTO> directlyAuthorizedVehicles = vehicleServiceFeign.getDirectlyAuthorizedVehicle(generalRequestHeader, userId);
        allVehiclesOfUser.addAll(directlyAuthorizedVehicles);

        return allVehiclesOfUser;
    }

    public Set<GroupResponseDTO> getListOfGroupOfUser(long userId) {
        buildTreeOfGroupOfUser(userId);
        return allGroupsInTree;
    }

    public void setGeneralRequestHeader(String generalRequestHeader) {
        this.generalRequestHeader = generalRequestHeader;
    }
}