package com.ahmeterdogan.service;

import com.ahmeterdogan.data.dal.GroupServiceHelper;
import com.ahmeterdogan.data.entity.Group;
import com.ahmeterdogan.dto.GroupVehicleTreeDTO;
import com.ahmeterdogan.dto.VehicleDTO;
import com.ahmeterdogan.manager.IVehicleManager;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GroupService {
    private final GroupServiceHelper groupServiceHelper;
    private final IVehicleManager vehicleManager;

    public GroupService(GroupServiceHelper groupServiceHelper, IVehicleManager vehicleManager) {
        this.groupServiceHelper = groupServiceHelper;
        this.vehicleManager = vehicleManager;
    }

    public Group getParent(Group group) {
        return groupServiceHelper.getParent(group);
    }

    public List<Group> getChilds(Group group) {
        return groupServiceHelper.getChilds(group);
    }

    private List<Group> getUserGroupAuth(long userId) {
        return groupServiceHelper.getUserGroupAuth(userId);
    }

    public List<GroupVehicleTreeDTO> getUserVehicleTrees(long userId) {
        List<Group> groups = getUserGroupAuth(userId);
        List<GroupVehicleTreeDTO> trees = new ArrayList<>();

        for (Group group : groups) {
            trees.add(convertToGroupVehicleDTO(group));
        }

        return trees;
    }

    private GroupVehicleTreeDTO convertToGroupVehicleDTO(Group group) {
        GroupVehicleTreeDTO dto = new GroupVehicleTreeDTO(group.getId(), group.getName());

        List<Group> childGroups = getChilds(group);
        for (Group childGroup : childGroups) {
            GroupVehicleTreeDTO childDto = convertToGroupVehicleDTO(childGroup);
            dto.addChild(childDto);
        }

                List<VehicleDTO> vehicles = vehicleManager.getAllVehiclesByGroupId(group.getId()).getBody();
                dto.setVehicles(vehicles);

        return dto;
    }

    public List<VehicleDTO> getUserVehicleList(long userId) {
        return new ArrayList<VehicleDTO>();
    }
}
