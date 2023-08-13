package com.ahmeterdogan.controller;

import com.ahmeterdogan.dto.response.GroupResponseDTO;
import com.ahmeterdogan.dto.response.GroupVehicleTreeResponseDTO;
import com.ahmeterdogan.dto.response.VehicleResponseDTO;
import com.ahmeterdogan.dto.request.GroupSaveDTO;
import com.ahmeterdogan.service.GroupService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Set;

import static com.ahmeterdogan.constants.ApiUrl.*;
import static com.ahmeterdogan.constants.SwaggerDescriptions.*;

@RestController
@RequestMapping(GROUPS)
public class GroupController {
    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @PostMapping(SAVE)
    @Operation(description = DESCRIPTION1)
    public ResponseEntity<GroupResponseDTO> save(@RequestHeader("X-User") String generalRequestHeader, @RequestBody GroupSaveDTO groupSaveDTO) {
        return ResponseEntity.ok(groupService.saveGroup(generalRequestHeader, groupSaveDTO));
    }

    @PostMapping(AUTHORIZE_USER_TO_GROUP)
    @Operation(summary = "Grup yetkisi ver", description = DESCRIPTION2)
    public ResponseEntity authorizeUserToGroup(@RequestHeader("X-User") String generalRequestHeader, @RequestParam("userId") Long userId, @RequestParam("groupId") Long groupId) {
        groupService.authorizeUserToGroup(generalRequestHeader, userId, groupId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping(FIND_BY_ID)
    public ResponseEntity<GroupResponseDTO> getGroupById(@RequestHeader("X-User") String generalRequestHeader, @PathVariable("id") long id) {
        return ResponseEntity.ok(groupService.getGroupByIdAndCompanyId(generalRequestHeader, id));
    }

    @GetMapping(USER_VEHICLES_TREE)
    @Operation(summary = "Araç ağacı", description = DESCRIPTION3)
    public ResponseEntity<Set<GroupVehicleTreeResponseDTO>> getTreeOfVehiclesOfUser(@RequestHeader("X-User") String generalRequestHeader) {
        return ResponseEntity.ok(groupService.getTreeOfGroupUserWithVehicle(generalRequestHeader));
    }

    @GetMapping(USER_VEHICLES_LIST)
    @Operation(summary = "Araç listesi", description = DESCRIPTION4)
    public ResponseEntity<Set<VehicleResponseDTO>> getListOfVehiclesOfUser(@RequestHeader("X-User") String generalRequestHeader) {
        return ResponseEntity.ok(groupService.getUserVehicleList(generalRequestHeader));
    }

    @GetMapping(USER_VEHICLES_LIST_BY_USER_ID)
    @Operation(description = DESCRIPTION5)
    public ResponseEntity<Set<VehicleResponseDTO>> getListOfVehiclesOfUser(@RequestHeader("X-User") String generalRequestHeader, @RequestParam Long userId) {
        return ResponseEntity.ok(groupService.getUserVehicleList(generalRequestHeader, userId));
    }

    @GetMapping(USER_GROUPS_LIST)
    @Operation(description = DESCRIPTION6)
    public ResponseEntity<Set<GroupResponseDTO>> getListOfGroupsOfUser(@RequestHeader("X-User") String generalRequestHeader) {
        return ResponseEntity.ok(groupService.getListOfGroupOfUser(generalRequestHeader));
    }

    @DeleteMapping(DELETE)
    @Operation(description = DESCRIPTION7)
    public ResponseEntity deleteGroup(@RequestHeader("X-User") String generalRequestHeader, @RequestParam("id") long id) {
        groupService.deleteGroupByIdAndCompanyId(generalRequestHeader,id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
