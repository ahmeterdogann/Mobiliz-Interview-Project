package com.ahmeterdogan.util;

import com.ahmeterdogan.data.dal.GroupServiceHelper;
import com.ahmeterdogan.dto.GroupDto;
import com.ahmeterdogan.dto.GroupTreeDto;
import com.ahmeterdogan.dto.GroupVehicleTreeDTO;
import com.ahmeterdogan.dto.VehicleDTO;
import com.ahmeterdogan.feign.IVehicleServiceFeign;
import com.ahmeterdogan.mapper.IGroupMapper;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Scope("prototype")
public class TreeOfGroupOfUser {
    private final GroupServiceHelper groupServiceHelper;
    private final IVehicleServiceFeign vehicleServiceFeign;
    private final IGroupMapper groupMapper;
    private final Set<VehicleDTO> allVehiclesInTree = new HashSet<>();
    private final Set<GroupDto> allGroupsInTree = new HashSet<>();

    public TreeOfGroupOfUser(GroupServiceHelper groupServiceHelper, IVehicleServiceFeign vehicleServiceFeign, IGroupMapper groupMapper) {
        this.groupServiceHelper = groupServiceHelper;
        this.vehicleServiceFeign = vehicleServiceFeign;
        this.groupMapper = groupMapper;
    }

    public Set<GroupDto> getChildren(GroupDto groupDto) {
        return groupServiceHelper.getChildren(groupMapper.toEntity(groupDto)).stream().map(groupMapper::toDto).collect(Collectors.toSet());
    }

    private Set<GroupDto> getUserGroupAuth(long userId) {
        return groupServiceHelper.getUserGroupAuth(userId).stream().map(groupMapper::toDto).collect(Collectors.toSet());
    }

    public Set<GroupVehicleTreeDTO> buildTreeOfGroupOfUserWithVehicle(long userId) {
        Set<GroupDto> groups = getUserGroupAuth(userId);
        Set<GroupVehicleTreeDTO> trees = new HashSet<>();

        for (GroupDto group : groups) {
            allGroupsInTree.add(group);
            trees.add(convertToGroupVehicleDTO(group));
        }

        return trees;
    }

    public Set<GroupTreeDto> buildTreeOfGroupOfUser(long userId) {
        Set<GroupDto> groups = getUserGroupAuth(userId);
        Set<GroupTreeDto> trees = new HashSet<>();

        for (GroupDto group : groups) {
            allGroupsInTree.add(group);
            trees.add(convertToGroupDto(group));
        }

        return trees;
    }

    private GroupVehicleTreeDTO convertToGroupVehicleDTO(GroupDto groupDto) {
        GroupVehicleTreeDTO groupVehicleTreeDTO = new GroupVehicleTreeDTO(groupDto.getId(), groupDto.getName());

        Set<GroupDto> childrenGroups = getChildren(groupDto);
        for (GroupDto childGroup : childrenGroups) {
            allGroupsInTree.add(childGroup);
            GroupVehicleTreeDTO childDto = convertToGroupVehicleDTO(childGroup);
            groupVehicleTreeDTO.addChild(childDto);
        }

        List<VehicleDTO> vehicles = vehicleServiceFeign.getAllVehiclesByGroupId(groupDto.getId()).getBody();
        groupVehicleTreeDTO.setVehiclesOfGroup(vehicles);
        allVehiclesInTree.addAll(vehicles);
        return groupVehicleTreeDTO;
    }

    private GroupTreeDto convertToGroupDto(GroupDto groupDto) {
        GroupTreeDto groupTreeDto = new GroupTreeDto(groupDto.getId(), groupDto.getName());

        Set<GroupDto> childGroups = getChildren(groupDto);
        for (GroupDto childGroup : childGroups) {
            allGroupsInTree.add(childGroup);
            GroupTreeDto childDto = convertToGroupDto(childGroup);
            groupTreeDto.addChild(childDto);
        }

        return groupTreeDto;
    }


    public Set<VehicleDTO> getUserVehicleList(long userId) {
        buildTreeOfGroupOfUserWithVehicle(userId);
        return allVehiclesInTree;
    }

    public Set<GroupDto> getListOfGroupOfUser(long userId) {
        buildTreeOfGroupOfUser(userId);
        return allGroupsInTree;
    }
}