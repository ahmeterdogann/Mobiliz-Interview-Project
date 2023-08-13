package com.ahmeterdogan.controller;

import com.ahmeterdogan.dto.response.GroupResponseDTO;
import com.ahmeterdogan.dto.response.GroupVehicleTreeResponseDTO;
import com.ahmeterdogan.dto.response.VehicleResponseDTO;
import com.ahmeterdogan.dto.request.GroupSaveDTO;
import com.ahmeterdogan.service.GroupService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Set;

import static com.ahmeterdogan.constants.ApiUrl.*;

@RestController
@RequestMapping(GROUPS)
public class GroupController {
    private final GroupService groupService;
    private final ObjectMapper objectMapper;

    public GroupController(GroupService groupService, ObjectMapper objectMapper) {
        this.groupService = groupService;
        this.objectMapper = objectMapper;
    }

    @PostMapping(SAVE)
    public ResponseEntity<GroupResponseDTO> save(@RequestHeader("X-User") String generalRequestHeader, @RequestBody GroupSaveDTO groupSaveDTO) {
        return ResponseEntity.ok(groupService.saveGroup(generalRequestHeader, groupSaveDTO));
    }

    @GetMapping(USER_VEHICLES_TREE)
    public ResponseEntity<Set<GroupVehicleTreeResponseDTO>> getTreeOfVehiclesOfUser(@RequestHeader("X-User") String generalRequestHeader) {
        return ResponseEntity.ok(groupService.getTreeOfGroupUserWithVehicle(generalRequestHeader));
    }

    @GetMapping(USER_VEHICLES_LIST)
    public ResponseEntity<Set<VehicleResponseDTO>> getListOfVehiclesOfUser(@RequestHeader("X-User") String generalRequestHeader) {
        return ResponseEntity.ok(groupService.getUserVehicleList(generalRequestHeader));
    }

    @GetMapping(USER_VEHICLES_LIST_BY_USER_ID)
    public ResponseEntity<Set<VehicleResponseDTO>> getListOfVehiclesOfUser(@RequestHeader("X-User") String generalRequestHeader, @RequestParam Long userId) {
        return ResponseEntity.ok(groupService.getUserVehicleList(generalRequestHeader, userId));
    }

    @GetMapping(USER_GROUPS_LIST)
    public ResponseEntity<Set<GroupResponseDTO>> getListOfGroupsOfUser(@RequestHeader("X-User") String generalRequestHeader) {
        return ResponseEntity.ok(groupService.getListOfGroupOfUser(generalRequestHeader));
    }

    @GetMapping(FIND_BY_ID)
    public ResponseEntity<GroupResponseDTO> getGroupById(@RequestHeader("X-User") String generalRequestHeader, @PathVariable("id") long id) {
        return ResponseEntity.ok(groupService.getGroupByIdAndCompanyId(generalRequestHeader, id));
    }


    @PostMapping(AUTHORIZE_USER_TO_GROUP)
    public ResponseEntity authorizeUserToGroup(@RequestHeader("X-User") String generalRequestHeader, @RequestParam("userId") Long userId, @RequestParam("groupId") Long groupId) {
        groupService.authorizeUserToGroup(generalRequestHeader, userId, groupId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping(DELETE)
    public ResponseEntity deleteGroup(@RequestHeader("X-User") String generalRequestHeader, @PathVariable("id") long id) {
        groupService.deleteGroupByIdAndCompanyId(generalRequestHeader,id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
