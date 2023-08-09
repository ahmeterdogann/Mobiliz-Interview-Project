package com.ahmeterdogan.data.repository;

import com.ahmeterdogan.data.entity.UserVehicleAuthorization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserVehicleAuthorizationRepository extends JpaRepository<UserVehicleAuthorization, Long> {
    UserVehicleAuthorization findByUserIdAndVehicleId(Long userId, Long vehicleId);
}
