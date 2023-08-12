package com.ahmeterdogan.data.dal;

import com.ahmeterdogan.data.entity.User;
import com.ahmeterdogan.data.entity.UserVehicleAuthorization;
import com.ahmeterdogan.data.entity.Vehicle;
import com.ahmeterdogan.data.repository.IUserVehicleAuthorizationRepository;
import com.ahmeterdogan.data.repository.IVehicleRepository;
import com.ahmeterdogan.mapper.IVehicleMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Component
public class VehicleServiceHelper {
    private final IVehicleRepository vehicleRepository;
    private final IUserVehicleAuthorizationRepository userVehicleAuthorizationRepository;
    private final IVehicleMapper vehicleMapper;

    public VehicleServiceHelper(IVehicleRepository vehicleRepository, IUserVehicleAuthorizationRepository userVehicleAuthorizationRepository, IVehicleMapper vehicleMapper) {
        this.vehicleRepository = vehicleRepository;
        this.userVehicleAuthorizationRepository = userVehicleAuthorizationRepository;
        this.vehicleMapper = vehicleMapper;
    }

    public Vehicle saveVehicle(Vehicle vehicle) {
        return vehicleRepository.save(vehicle);
    }

    public Optional<Vehicle> getVehicleByIdAndCompanyId(Long id, Long companyId) {
        return vehicleRepository.findByIdAndCompany_Id(id, companyId);
    }

    public List<Vehicle> getAllVehicles(Long companyId) {
        return vehicleRepository.findAllByCompany_Id(companyId);
    }

    public List<Vehicle> getAllVehiclesByCompanyIdAndGroupId(long companyId, long groupId) {
        return vehicleRepository.findAllByCompany_IdAndGroup_Id(companyId, groupId);
    }

    public int deleteVehicleByIdAndCompanyId(long id, long companyId) {
        return vehicleRepository.deleteByIdAndCompany_Id(id, companyId);
    }

    public void saveUserVehicleAuth(Vehicle vehicle, User user) {
        UserVehicleAuthorization userVehicleAuthorization = UserVehicleAuthorization.builder()
                .user(user)
                .vehicle(vehicle)
                .build();

        userVehicleAuthorizationRepository.save(userVehicleAuthorization);
    }

    public List<UserVehicleAuthorization> getDirectlyAuthorizedVehicles(long userId) {
        return userVehicleAuthorizationRepository.findAllByUser_Id(userId);
    }
}
