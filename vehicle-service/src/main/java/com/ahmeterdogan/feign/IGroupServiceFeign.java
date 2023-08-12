package com.ahmeterdogan.feign;

import com.ahmeterdogan.dto.GroupDTO;
import com.ahmeterdogan.dto.response.VehicleResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Set;

@FeignClient(name = "group-service", url = "http://localhost:8082/api/v1/groups")
public interface IGroupServiceFeign {
    @GetMapping("/search-by-name")
    GroupDTO getGroupByName(@RequestHeader("X-User") String generalRequestHeader, @RequestParam("groupName") String groupName);

    @GetMapping("/user-groups-list")
    Set<GroupDTO> getGroupListByUser(@RequestHeader("X-User") String generalRequestHeader);

    @GetMapping("/user-vehicles-list")
    Set<VehicleResponseDTO> getVehiclesListByUser(@RequestHeader("X-User") String generalRequestHeader);

    @GetMapping("/user-vehicles-list-by-user-id")
    Set<VehicleResponseDTO> getVehiclesListByUserId(@RequestHeader("X-User") String generalRequestHeader, @RequestParam("userId") Long userId);

    @GetMapping("/{id}")
    GroupDTO getById(@RequestHeader("X-User") String generalRequestHeader, @PathVariable("id") Long id);
}
