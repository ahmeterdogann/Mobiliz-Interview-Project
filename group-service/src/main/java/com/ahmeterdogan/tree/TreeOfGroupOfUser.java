package com.ahmeterdogan.tree;

import com.ahmeterdogan.data.dal.GroupServiceHelper;
import com.ahmeterdogan.dto.response.GroupResponseDTO;
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
    private final Set<GroupResponseDTO> allGroupsInTree = new HashSet<>();

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

    public Set<GroupVehicleTreeDTO> buildTreeOfGroupOfUserWithVehicle(long userId) {
        Set<GroupResponseDTO> groups = getUserGroupAuth(userId);
        Set<GroupVehicleTreeDTO> trees = new HashSet<>();

        for (GroupResponseDTO group : groups) {
            allGroupsInTree.add(group);
            trees.add(convertToGroupVehicleDTO(group));
        }

        return trees;
    }

    public Set<GroupTreeDto> buildTreeOfGroupOfUser(long userId) {
        Set<GroupResponseDTO> groups = getUserGroupAuth(userId);
        Set<GroupTreeDto> trees = new HashSet<>();

        for (GroupResponseDTO group : groups) {
            allGroupsInTree.add(group);
            trees.add(convertToGroupDto(group));
        }

        return trees;
    }

    private GroupVehicleTreeDTO convertToGroupVehicleDTO(GroupResponseDTO groupResponseDTO) {
        GroupVehicleTreeDTO groupVehicleTreeDTO = new GroupVehicleTreeDTO(groupResponseDTO.getId(), groupResponseDTO.getName());

        Set<GroupResponseDTO> childrenGroups = getChildren(groupResponseDTO);
        for (GroupResponseDTO childGroup : childrenGroups) {
            allGroupsInTree.add(childGroup);
            GroupVehicleTreeDTO childDto = convertToGroupVehicleDTO(childGroup);
            groupVehicleTreeDTO.addChild(childDto);
        }

        List<VehicleDTO> vehicles = vehicleServiceFeign.getAllVehiclesByGroupId(groupResponseDTO.getId()).getBody();
        groupVehicleTreeDTO.setVehiclesOfGroup(vehicles);
        allVehiclesInTree.addAll(vehicles);
        return groupVehicleTreeDTO;
    }

    private GroupTreeDto convertToGroupDto(GroupResponseDTO groupResponseDTO) {
        GroupTreeDto groupTreeDto = new GroupTreeDto(groupResponseDTO.getId(), groupResponseDTO.getName());

        Set<GroupResponseDTO> childGroups = getChildren(groupResponseDTO);
        for (GroupResponseDTO childGroup : childGroups) {
            allGroupsInTree.add(childGroup);
            GroupTreeDto childDto = convertToGroupDto(childGroup);
            groupTreeDto.addChild(childDto);
        }

        return groupTreeDto;
    }


    public Set<VehicleDTO> getListOfVehiclesOfUser(long userId) {
        buildTreeOfGroupOfUserWithVehicle(userId);
        return allVehiclesInTree;
    }

    public Set<GroupResponseDTO> getListOfGroupOfUser(long userId) {
        buildTreeOfGroupOfUser(userId);
        return allGroupsInTree;
    }
}