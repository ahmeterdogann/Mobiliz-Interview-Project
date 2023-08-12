package com.ahmeterdogan.controller;

import com.ahmeterdogan.dto.response.GroupResponseDTO;
import com.ahmeterdogan.dto.GroupVehicleTreeDTO;
import com.ahmeterdogan.dto.VehicleDTO;
import com.ahmeterdogan.dto.request.GroupSaveDTO;
import com.ahmeterdogan.service.GroupService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Set;

@RestController
@RequestMapping("api/v1/groups")
public class GroupController {
    private final GroupService groupService;
    private final ObjectMapper objectMapper;

    public GroupController(GroupService groupService, ObjectMapper objectMapper) {
        this.groupService = groupService;
        this.objectMapper = objectMapper;
    }

    @PostMapping("/save")
    public ResponseEntity<GroupResponseDTO> save(@RequestHeader("X-User") String generalRequestHeader, @RequestBody GroupSaveDTO groupSaveDTO) {
        return ResponseEntity.ok(groupService.saveGroup(generalRequestHeader, groupSaveDTO));
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
    public ResponseEntity<Set<GroupResponseDTO>> getListOfGroupsOfUser(@RequestHeader("X-User") String generalRequestHeader) {
        return ResponseEntity.ok(groupService.getListOfGroupOfUser(generalRequestHeader));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GroupResponseDTO> getGroupById(@RequestHeader String generalRequestHeader, @PathVariable("id") long id) {
        return groupService.getGroupByIdAndCompanyId(generalRequestHeader, id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
}
