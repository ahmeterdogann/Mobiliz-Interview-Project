package com.ahmeterdogan.data.repository;

import com.ahmeterdogan.data.entity.UserVehicleAuthorization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface IUserVehicleAuthorizationRepository extends JpaRepository<UserVehicleAuthorization, Long> {
    UserVehicleAuthorization findByUserIdAndVehicleId(Long userId, Long vehicleId);
    List<UserVehicleAuthorization> findAllByUser_Id(long userId);
}
