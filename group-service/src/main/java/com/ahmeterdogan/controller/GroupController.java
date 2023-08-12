package com.ahmeterdogan.controller;

import com.ahmeterdogan.dto.response.GroupResponseDTO;
import com.ahmeterdogan.dto.response.GroupVehicleTreeResponseDTO;
import com.ahmeterdogan.dto.response.VehicleResponseDTO;
import com.ahmeterdogan.dto.request.GroupSaveDTO;
import com.ahmeterdogan.service.GroupService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<Set<GroupVehicleTreeResponseDTO>> getTreeOfVehiclesOfUser(@RequestHeader("X-User") String generalRequestHeader) {
        return ResponseEntity.ok(groupService.getTreeOfGroupUserWithVehicle(generalRequestHeader));
    }

    @GetMapping("/user-vehicles-list")
    public ResponseEntity<Set<VehicleResponseDTO>> getListOfVehiclesOfUser(@RequestHeader("X-User") String generalRequestHeader) {
        return ResponseEntity.ok(groupService.getUserVehicleList(generalRequestHeader));
    }

    @GetMapping("/user-vehicles-list-by-user-id")
    public ResponseEntity<Set<VehicleResponseDTO>> getListOfVehiclesOfUser(@RequestHeader("X-User") String generalRequestHeader, @RequestParam Long userId) {
        return ResponseEntity.ok(groupService.getUserVehicleList(generalRequestHeader, userId));
    }

    @GetMapping("/user-groups-list")
    public ResponseEntity<Set<GroupResponseDTO>> getListOfGroupsOfUser(@RequestHeader("X-User") String generalRequestHeader) {
        return ResponseEntity.ok(groupService.getListOfGroupOfUser(generalRequestHeader));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GroupResponseDTO> getGroupById(@RequestHeader("X-User") String generalRequestHeader, @PathVariable("id") long id) {
        return groupService.getGroupByIdAndCompanyId(generalRequestHeader, id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity deleteGroup(@RequestHeader("X-User") String generalRequestHeader, @PathVariable("id") long id) {
        groupService.deleteGroupByIdAndCompanyId(generalRequestHeader,id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
