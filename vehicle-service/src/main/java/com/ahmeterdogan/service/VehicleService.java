package com.ahmeterdogan.service;

import com.ahmeterdogan.data.entity.Company;
import com.ahmeterdogan.data.entity.Vehicle;
import com.ahmeterdogan.data.dal.VehicleServiceHelper;
import com.ahmeterdogan.data.enums.Roles;
import com.ahmeterdogan.dto.GroupDTO;
import com.ahmeterdogan.dto.response.VehicleResponseDTO;
import com.ahmeterdogan.dto.request.GeneralRequestHeaderDto;
import com.ahmeterdogan.dto.request.VehicleUpdateDTO;
import com.ahmeterdogan.dto.request.VehicleSaveDTO;
import com.ahmeterdogan.feign.IGroupServiceFeign;
import com.ahmeterdogan.mapper.IGroupMapper;
import com.ahmeterdogan.mapper.IVehicleMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class VehicleService {
    private final VehicleServiceHelper vehicleServiceHelper;
    private final IVehicleMapper vehicleMapper;
    private final IGroupMapper groupMapper;
    private final ObjectMapper objectMapper;
    private final IGroupServiceFeign groupServiceFeign;

    public VehicleService(VehicleServiceHelper vehicleServiceHelper, IVehicleMapper vehicleMapper, IGroupMapper groupMapper, ObjectMapper objectMapper, IGroupServiceFeign groupServiceFeign) {
        this.vehicleServiceHelper = vehicleServiceHelper;
        this.vehicleMapper = vehicleMapper;
        this.groupMapper = groupMapper;
        this.objectMapper = objectMapper;
        this.groupServiceFeign = groupServiceFeign;
    }

    public VehicleResponseDTO saveVehicle(String generalRequestHeader, VehicleSaveDTO vehicleSaveDTO) {
        GeneralRequestHeaderDto generalRequestHeaderDto = generalHeaderRequestConverter(generalRequestHeader);

        if (generalRequestHeaderDto.getRoles() != Roles.COMPANY_ADMIN)
            throw new RuntimeException("User is not authorized to save vehicle");

        GroupDTO groupDTO = groupServiceFeign.getGroupByName(generalRequestHeader, vehicleSaveDTO.getGroupName());

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

        GeneralRequestHeaderDto generalRequestHeaderDto = generalHeaderRequestConverter(generalRequestHeader);

        if (generalRequestHeaderDto.getRoles() != Roles.COMPANY_ADMIN)
            throw new RuntimeException("User is not authorized to get all vehicles");

        return vehicleServiceHelper.getAllVehicles(generalRequestHeaderDto.getCompanyId()).stream().map(vehicleMapper::toDto).collect(Collectors.toList());


    }

    public Optional<VehicleResponseDTO> getVehicleById(String generalRequestHeader, long vehicleId) {
        GeneralRequestHeaderDto generalRequestHeaderDto = generalHeaderRequestConverter(generalRequestHeader);

        if (isAdmin(generalRequestHeaderDto))
            return vehicleServiceHelper.getVehicleByIdAndCompanyId(generalRequestHeaderDto.getCompanyId(), vehicleId).map(vehicleMapper::toDto);
        else {
            Set<VehicleResponseDTO> userVehicles = groupServiceFeign.getVehiclesListByUser(generalRequestHeader);

            return userVehicles.stream().filter(vehicleDTO -> vehicleDTO.getId() == vehicleId).findFirst();
        }
    }

    public List<VehicleResponseDTO> getAllVehiclesByGroupName(String generalRequestHeader, String groupName) {

        GroupDTO groupDTO = groupServiceFeign.getGroupByName(generalRequestHeader, groupName);
        GeneralRequestHeaderDto generalRequestHeaderDto = generalHeaderRequestConverter(generalRequestHeader);

        if (isAdmin(generalRequestHeaderDto))
            vehicleServiceHelper.getAllVehiclesByCompanyIdAndGroupId(generalRequestHeaderDto.getCompanyId(), groupDTO.getId()).stream()
                    .map(vehicleMapper::toDto).collect(Collectors.toList());
        else
            getAllVehiclesByGroupNameWorkForStandardUser(generalRequestHeaderDto, groupDTO);

        return getAllVehiclesByGroupNameWorkForStandardUser(generalRequestHeaderDto, groupDTO);

    }

    private List<VehicleResponseDTO> getAllVehiclesByGroupNameWorkForStandardUser(GeneralRequestHeaderDto generalRequestHeader, GroupDTO groupDTO) {
        Set<GroupDTO> userGroups = groupServiceFeign.getGroupListByUser(generalRequestHeader.toString());

        if (!userGroups.contains(groupDTO))
            throw new RuntimeException("User is not authorized to get vehicles for this group");
        else
            return vehicleServiceHelper.getAllVehiclesByCompanyIdAndGroupId(generalRequestHeader.getCompanyId(), groupDTO.getId()).stream()
                    .map(vehicleMapper::toDto).collect(Collectors.toList());
    }

    public VehicleResponseDTO updateVehicle(String generalRequestHeader, VehicleUpdateDTO vehicleUpdateDTO) {

        GeneralRequestHeaderDto generalRequestHeaderDto = generalHeaderRequestConverter(generalRequestHeader);

        if (!isAdmin(generalRequestHeaderDto))
            throw new RuntimeException("User is not authorized to update vehicle");

        Optional<Vehicle> vehicleOptional = vehicleServiceHelper.getVehicleByIdAndCompanyId(generalRequestHeaderDto.getCompanyId(), vehicleUpdateDTO.getId());

        if (vehicleOptional.isPresent())
            return vehicleMapper.toDto(vehicleServiceHelper.saveVehicle(vehicleMapper.toEntity(vehicleUpdateDTO)));
        else
            throw new RuntimeException("Vehicle not found");

    }

    public int deleteVehicleByIdAndCompanyId(String generalRequestHeader, long id) {
        GeneralRequestHeaderDto generalRequestHeaderDto = generalHeaderRequestConverter(generalRequestHeader);

        if (!isAdmin(generalRequestHeaderDto))
            throw new RuntimeException("User is not authorized to delete vehicle");

        return vehicleServiceHelper.deleteVehicleByIdAndCompanyId(generalRequestHeaderDto.getCompanyId(), id);
    }


    private boolean isAdmin(GeneralRequestHeaderDto generalRequestHeaderDto) {
        return generalRequestHeaderDto.getRoles() == Roles.COMPANY_ADMIN;
    }

    private GeneralRequestHeaderDto generalHeaderRequestConverter(String generalRequestHeader) {
        try {
            GeneralRequestHeaderDto generalRequestHeaderDto = objectMapper.readValue(generalRequestHeader, GeneralRequestHeaderDto.class);
            return generalRequestHeaderDto;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
