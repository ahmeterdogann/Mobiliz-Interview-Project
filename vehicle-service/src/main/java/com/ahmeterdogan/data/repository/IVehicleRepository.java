package com.ahmeterdogan.data.repository;

import com.ahmeterdogan.data.entity.Vehicle;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Repository
public interface IVehicleRepository extends JpaRepository<Vehicle, Long> {
    int deleteById(long id);

    @Modifying
    @Query("UPDATE Vehicle v SET v.plateNumber = :#{#vehicle.plateNumber}, v.brand = :#{#vehicle.brand}, v.model = :#{#vehicle.model}, v.modelYear = :#{#vehicle.modelYear}, v.chassisNumber = :#{#vehicle.chassisNumber}, v.label = :#{#vehicle.label}, v.group.id = :#{#vehicle.group.id} WHERE v.id = :#{#vehicle.id}")
    Vehicle updateVehicle(@Param("vehicle") Vehicle vehicle);

    List<Vehicle> findAllByGroup_Id(Long groupId);
}

