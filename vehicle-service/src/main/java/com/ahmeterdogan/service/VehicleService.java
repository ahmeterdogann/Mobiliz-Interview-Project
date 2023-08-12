package com.ahmeterdogan.service;

import com.ahmeterdogan.data.entity.Company;
import com.ahmeterdogan.data.entity.Group;
import com.ahmeterdogan.data.entity.User;
import com.ahmeterdogan.data.entity.Vehicle;
import com.ahmeterdogan.data.dal.VehicleServiceHelper;
import com.ahmeterdogan.data.enums.Roles;
import com.ahmeterdogan.dto.GroupDTO;
import com.ahmeterdogan.dto.request.*;
import com.ahmeterdogan.dto.response.UserResponseDTO;
import com.ahmeterdogan.dto.response.VehicleResponseDTO;
import com.ahmeterdogan.exception.VehicleServiceException;
import com.ahmeterdogan.feign.IGroupServiceFeign;
import com.ahmeterdogan.mapper.IUserMapper;
import com.ahmeterdogan.feign.IUserServiceFeign;
import com.ahmeterdogan.mapper.IGroupMapper;
import com.ahmeterdogan.mapper.IVehicleMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

import static com.ahmeterdogan.exception.ApiErrorMessages.*;

@Service
public class VehicleService {
    private final VehicleServiceHelper vehicleServiceHelper;
    private final IVehicleMapper vehicleMapper;
    private final IGroupMapper groupMapper;
    private final IUserMapper userMapper;
    private final ObjectMapper objectMapper;
    private final IGroupServiceFeign groupServiceFeign;
    private final IUserServiceFeign userServiceFeign;

    public VehicleService(VehicleServiceHelper vehicleServiceHelper, IVehicleMapper vehicleMapper, IGroupMapper groupMapper, IUserMapper userMapper, ObjectMapper objectMapper, IGroupServiceFeign groupServiceFeign, IUserServiceFeign userServiceFeign) {
        this.vehicleServiceHelper = vehicleServiceHelper;
        this.vehicleMapper = vehicleMapper;
        this.groupMapper = groupMapper;
        this.userMapper = userMapper;
        this.objectMapper = objectMapper;
        this.groupServiceFeign = groupServiceFeign;
        this.userServiceFeign = userServiceFeign;
    }

    public VehicleResponseDTO saveVehicle(String generalRequestHeader, VehicleSaveDTO vehicleSaveDTO) {
        GeneralRequestHeaderDTO generalRequestHeaderDto = generalHeaderRequestConverter(generalRequestHeader);

        if (generalRequestHeaderDto.getRole() != Roles.COMPANY_ADMIN)
            throw new VehicleServiceException(USER_NOT_COMPANY_ADMIN);

        GroupDTO groupDTO = groupServiceFeign.getById(generalRequestHeader, vehicleSaveDTO.getGroupId());

        Vehicle vehicle = vehicleMapper.toEntity(vehicleSaveDTO);
        vehicle.setGroup(groupMapper.toEntity(groupDTO));
        Company company = new Company();
        company.setId(generalRequestHeaderDto.getCompanyId());
        vehicle.setCompany(company);

        return vehicleMapper.toDto(vehicleServiceHelper.saveVehicle(vehicle));

    }

    public List<VehicleResponseDTO> saveAll(String generalRequestHeader, List<VehicleSaveDTO> vehicleSaveDTOList) {
        return vehicleSaveDTOList.stream().map(vehicleSaveDTO -> saveVehicle(generalRequestHeader, vehicleSaveDTO)).collect(Collectors.toList());
    }

    public List<VehicleResponseDTO> getAllVehicles(String generalRequestHeader) {

        GeneralRequestHeaderDTO generalRequestHeaderDto = generalHeaderRequestConverter(generalRequestHeader);

        if (generalRequestHeaderDto.getRole() != Roles.COMPANY_ADMIN)
            throw new VehicleServiceException(USER_NOT_COMPANY_ADMIN);

        return vehicleServiceHelper.getAllVehicles(generalRequestHeaderDto.getCompanyId()).stream().map(vehicleMapper::toDto).collect(Collectors.toList());
    }

    public Optional<VehicleResponseDTO> getVehicleById(String generalRequestHeader, long vehicleId) {
        GeneralRequestHeaderDTO generalRequestHeaderDto = generalHeaderRequestConverter(generalRequestHeader);

        if (isAdmin(generalRequestHeaderDto))
            return vehicleServiceHelper.getVehicleByIdAndCompanyId(generalRequestHeaderDto.getCompanyId(), vehicleId).map(vehicleMapper::toDto);
        else {
            Set<VehicleResponseDTO> userVehicles = groupServiceFeign.getVehiclesListByUser(generalRequestHeader);

            return userVehicles.stream().filter(vehicleDTO -> vehicleDTO.getId() == vehicleId).findFirst();
        }
    }

    public List<VehicleResponseDTO> getAllVehiclesByGroupName(String generalRequestHeader, String groupName) {

        GroupDTO groupDTO = groupServiceFeign.getGroupByName(generalRequestHeader, groupName);
        GeneralRequestHeaderDTO generalRequestHeaderDto = generalHeaderRequestConverter(generalRequestHeader);

        if (isAdmin(generalRequestHeaderDto))
            vehicleServiceHelper.getAllVehiclesByCompanyIdAndGroupId(generalRequestHeaderDto.getCompanyId(), groupDTO.getId()).stream()
                    .map(vehicleMapper::toDto).collect(Collectors.toList());
        else
            getAllVehiclesByGroupNameWorkForStandardUser(generalRequestHeaderDto, groupDTO);

        return getAllVehiclesByGroupNameWorkForStandardUser(generalRequestHeaderDto, groupDTO);

    }

    private List<VehicleResponseDTO> getAllVehiclesByGroupNameWorkForStandardUser(GeneralRequestHeaderDTO generalRequestHeader, GroupDTO groupDTO) {
        Set<GroupDTO> userGroups = groupServiceFeign.getGroupListByUser(generalRequestHeader.toString());

        if (!userGroups.contains(groupDTO))
            throw new VehicleServiceException(USER_NOT_AUTHORIZED_TO_GROUP);
        else
            return vehicleServiceHelper.getAllVehiclesByCompanyIdAndGroupId(generalRequestHeader.getCompanyId(), groupDTO.getId()).stream()
                    .map(vehicleMapper::toDto).collect(Collectors.toList());
    }

    public VehicleResponseDTO updateVehicle(String generalRequestHeader, VehicleUpdateDTO vehicleUpdateDTO) {

        GeneralRequestHeaderDTO generalRequestHeaderDto = generalHeaderRequestConverter(generalRequestHeader);

        if (!isAdmin(generalRequestHeaderDto))
            throw new VehicleServiceException(USER_NOT_COMPANY_ADMIN);

        Optional<Vehicle> vehicleOptional = vehicleServiceHelper.getVehicleByIdAndCompanyId(vehicleUpdateDTO.getId(),generalRequestHeaderDto.getCompanyId());

        if (vehicleOptional.isPresent())
            return vehicleMapper.toDto(vehicleServiceHelper.saveVehicle(vehicleMapper.toEntity(vehicleUpdateDTO)));
        else
            throw new VehicleServiceException(VEHICLE_NOT_FOUND);

    }

    public int deleteVehicleByIdAndCompanyId(String generalRequestHeader, long id) {
        GeneralRequestHeaderDTO generalRequestHeaderDto = generalHeaderRequestConverter(generalRequestHeader);

        if (!isAdmin(generalRequestHeaderDto))
            throw new VehicleServiceException(USER_NOT_COMPANY_ADMIN);

        return vehicleServiceHelper.deleteVehicleByIdAndCompanyId(generalRequestHeaderDto.getCompanyId(), id);
    }


    private boolean isAdmin(GeneralRequestHeaderDTO generalRequestHeaderDto) {
        return generalRequestHeaderDto.getRole() == Roles.COMPANY_ADMIN;
    }

    private GeneralRequestHeaderDTO generalHeaderRequestConverter(String generalRequestHeader) {
        try {
            GeneralRequestHeaderDTO generalRequestHeaderDto = objectMapper.readValue(generalRequestHeader, GeneralRequestHeaderDTO.class);
            return generalRequestHeaderDto;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public void authorizeUserToVehicle(String generalRequestHeader, AuthorizeUserToVehicleRequestDTO authorizeUserToVehicleRequestDTO) {
        GeneralRequestHeaderDTO generalRequestHeaderDto = generalHeaderRequestConverter(generalRequestHeader);

        if (!isAdmin(generalRequestHeaderDto))
            throw new VehicleServiceException(USER_NOT_COMPANY_ADMIN);

        ResponseEntity<UserResponseDTO> userResponseDTOResponseEntity = userServiceFeign.getUserById(authorizeUserToVehicleRequestDTO.getUserId());

        if (userResponseDTOResponseEntity.getStatusCode().isError())
            throw new VehicleServiceException(USER_NOT_FOUND);

        Set<VehicleResponseDTO> vehiclesListByUser = groupServiceFeign.getVehiclesListByUserId(generalRequestHeader, authorizeUserToVehicleRequestDTO.getUserId());

        if (vehiclesListByUser.stream()
                .anyMatch(vehicleResponseDTO -> vehicleResponseDTO.getId() == authorizeUserToVehicleRequestDTO.getVehicleId()))
            throw new VehicleServiceException(USER_ALREADY_AUTHORIZED_TO_VEHICLE);

        User user = userMapper.toEntity(userResponseDTOResponseEntity.getBody());

        Optional<Vehicle> vehicleOptional = vehicleServiceHelper.getVehicleByIdAndCompanyId(authorizeUserToVehicleRequestDTO.getVehicleId(), generalRequestHeaderDto.getCompanyId());

        if (vehicleOptional.isPresent()) {
            Vehicle vehicle = vehicleOptional.get();
            vehicleServiceHelper.saveUserVehicleAuth(vehicle, user);

        } else
            throw new VehicleServiceException(VEHICLE_NOT_FOUND);

    }

    public List<VehicleResponseDTO> getAllVehiclesByGroupId(String generalRequestHeader, long groupId) {
        GeneralRequestHeaderDTO generalRequestHeaderDto = generalHeaderRequestConverter(generalRequestHeader);

        if (isUserAuthorizedToGroup(generalRequestHeader, groupId))
            return vehicleServiceHelper.getAllVehiclesByCompanyIdAndGroupId(generalRequestHeaderDto.getCompanyId(), groupId).stream()
                    .map(vehicleMapper::toDto).collect(Collectors.toList());
        else
            throw new VehicleServiceException(USER_NOT_AUTHORIZED_TO_GROUP);

    }

    private boolean isUserAuthorizedToGroup(String generalRequestHeader, long groupId) {
        Set<GroupDTO> userGroups = groupServiceFeign.getGroupListByUser(generalRequestHeader);
        return userGroups.stream().anyMatch(groupDTO -> groupDTO.getId() == groupId);
    }

    public List<VehicleResponseDTO> getDirectlyAuthorizedVehicle(String generalRequestHeader) {
        GeneralRequestHeaderDTO generalRequestHeaderDto = generalHeaderRequestConverter(generalRequestHeader);

        return vehicleServiceHelper.getDirectlyAuthorizedVehicles(generalRequestHeaderDto.getUserId()).stream()
                .map(userVehicleAuthorization -> vehicleMapper.toDto(userVehicleAuthorization.getVehicle())).collect(Collectors.toList());
    }

    public List<VehicleResponseDTO> getDirectlyAuthorizedVehicle(String generalRequestHeader, long userId) {
        GeneralRequestHeaderDTO generalRequestHeaderDto = generalHeaderRequestConverter(generalRequestHeader);

        if (!isAdmin(generalRequestHeaderDto))
            throw new VehicleServiceException(USER_NOT_COMPANY_ADMIN);

        return vehicleServiceHelper.getDirectlyAuthorizedVehicles(userId).stream()
                .map(userVehicleAuthorization -> vehicleMapper.toDto(userVehicleAuthorization.getVehicle())).collect(Collectors.toList());
    }

    public VehicleResponseDTO updateVehicleGroup(String generalRequestHeader, VehicleGroupUpdateDTO vehicleGroupUpdateDTO) {
        Optional<Vehicle> existingVehicle = vehicleServiceHelper.getVehicleByIdAndCompanyId(vehicleGroupUpdateDTO.getId(), generalHeaderRequestConverter(generalRequestHeader).getCompanyId());

        existingVehicle.ifPresent(v -> v.setGroup(Group.builder().id(vehicleGroupUpdateDTO.getGroupId()).build()));

        VehicleUpdateDTO vehicleUpdateDTO = vehicleMapper.toUpdateDto(existingVehicle.get());

        return updateVehicle(generalRequestHeader, vehicleUpdateDTO);
    }
}