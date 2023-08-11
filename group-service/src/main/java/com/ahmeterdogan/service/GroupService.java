package com.ahmeterdogan.service;

import com.ahmeterdogan.data.dal.GroupServiceHelper;
import com.ahmeterdogan.data.entity.Group;
import com.ahmeterdogan.dto.GroupDto;
import com.ahmeterdogan.dto.GroupVehicleTreeDTO;
import com.ahmeterdogan.dto.VehicleDTO;
import com.ahmeterdogan.util.TreeOfGroupOfUser;
import com.ahmeterdogan.util.TreeOfGroupOfUserFactory;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class GroupService {
    private final GroupServiceHelper groupServiceHelper;
    private final TreeOfGroupOfUserFactory treeOfGroupOfUserFactory;

    public GroupService(GroupServiceHelper groupServiceHelper, TreeOfGroupOfUserFactory treeOfGroupOfUserFactory) {
        this.groupServiceHelper = groupServiceHelper;
        this.treeOfGroupOfUserFactory = treeOfGroupOfUserFactory;
    }

    private Set<Group> getUserGroupAuth(long userId) {
        return groupServiceHelper.getUserGroupAuth(userId);
    }

    public Set<GroupVehicleTreeDTO> getTreeOfGroupUserWithVehicle(long userId) {
        TreeOfGroupOfUser treeOfGroupOfUser = treeOfGroupOfUserFactory.createTreeOfGroupOfUser();
        return treeOfGroupOfUser.buildTreeOfGroupOfUserWithVehicle(userId);
    }


    public Set<VehicleDTO> getUserVehicleList(long userId) {
        TreeOfGroupOfUser treeOfGroupOfUser = treeOfGroupOfUserFactory.createTreeOfGroupOfUser();
        return treeOfGroupOfUser.getUserVehicleList(userId);
    }

    public Set<GroupDto> getListOfGroupOfUser(long userId) {
        TreeOfGroupOfUser treeOfGroupOfUser = treeOfGroupOfUserFactory.createTreeOfGroupOfUser();
        return treeOfGroupOfUser.getListOfGroupOfUser(userId);
    }
}
