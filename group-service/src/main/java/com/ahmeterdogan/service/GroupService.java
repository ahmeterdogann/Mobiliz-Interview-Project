package com.ahmeterdogan.service;

import com.ahmeterdogan.call.ServiceCall;
import com.ahmeterdogan.data.dal.GroupServiceHelper;
import com.ahmeterdogan.data.entity.Group;
import com.ahmeterdogan.dto.GroupVehicleTreeDTO;
import com.ahmeterdogan.dto.VehicleDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GroupService {
    private final GroupServiceHelper groupServiceHelper;
    private final ServiceCall serviceCall;

    public GroupService(GroupServiceHelper groupServiceHelper, ServiceCall serviceCall) {
        this.groupServiceHelper = groupServiceHelper;
        this.serviceCall = serviceCall;
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

                List<VehicleDTO> vehicles = serviceCall.getVehiclesByGroupId(group.getId());
                dto.setVehicles(vehicles);

        return dto;
    }

    public List<VehicleDTO> getUserVehicleList(long userId) {
        return new ArrayList<VehicleDTO>();
    }
}
