package com.ahmeterdogan.controller;

import com.ahmeterdogan.dto.GroupDto;
import com.ahmeterdogan.dto.GroupVehicleTreeDTO;
import com.ahmeterdogan.dto.VehicleDTO;
import com.ahmeterdogan.service.GroupService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Set;

@RestController
@RequestMapping("api/v1/groups")
public class GroupController {
    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping("/user-vehicles-tree")
    public ResponseEntity<Set<GroupVehicleTreeDTO>> getTreeOfVehiclesOfUser(@RequestParam("userId") long userId) {
        return ResponseEntity.ok(groupService.getTreeOfGroupUserWithVehicle(userId));
    }

    @GetMapping("/user-vehicles-list")
    public ResponseEntity<Set<VehicleDTO>> getListOfVehiclesOfUser(@RequestParam long userId) {
        return ResponseEntity.ok(groupService.getUserVehicleList(userId));
    }

    @GetMapping("/user-groups-list")
    public ResponseEntity<Set<GroupDto>> getListOfGroupsOfUser(@RequestParam long userId) {
        return ResponseEntity.ok(groupService.getListOfGroupOfUser(userId));
    }

//    @PostMapping("/user-group-auth")
//    public ResponseEntity<Set<GroupDto>> authUserToGroup(@Req long userId) {
//        return ResponseEntity.ok(groupService.getListOfGroupOfUser(userId));
//    }

}
