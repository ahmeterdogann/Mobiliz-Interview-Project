package com.ahmeterdogan.controller;

import com.ahmeterdogan.data.entity.Group;
import com.ahmeterdogan.dto.GroupVehicleTreeDTO;
import com.ahmeterdogan.dto.VehicleDTO;
import com.ahmeterdogan.service.GroupService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/groups")
public class GroupController {
    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping("/find-parent")
    public ResponseEntity<Group> getParent(@RequestBody Group group) {
        return ResponseEntity.ok(groupService.getParent(group));
    }

    @GetMapping("/find-childs")
    public ResponseEntity<List<Group>> getChilds(@RequestBody Group group) {
        return ResponseEntity.ok(groupService.getChilds(group));
    }

    @GetMapping("/user-vehicles-tree")
    public ResponseEntity<List<GroupVehicleTreeDTO>> getUserVehicleTree(@RequestParam long userId) {
        return ResponseEntity.ok(groupService.getUserVehicleTrees(userId));
    }

    @GetMapping("/user-vehicles-list")
    public ResponseEntity<List<VehicleDTO>> getTree(@RequestParam long userId) {
        return ResponseEntity.ok(groupService.getUserVehicleList(userId));
    }

}
