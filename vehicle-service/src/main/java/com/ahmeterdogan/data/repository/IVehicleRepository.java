package com.ahmeterdogan.data.repository;

import com.ahmeterdogan.data.entity.Vehicle;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IVehicleRepository extends JpaRepository<Vehicle, Long> {

    List<Vehicle> findAllByCompany_Id(Long companyId);

    List<Vehicle> findAllByCompany_IdAndGroup_Id(Long companyId, Long groupId);

    Optional<Vehicle> findByCompany_Id(Long companyId);

    Optional<Vehicle> findByIdAndCompany_Id(Long id, Long companyId);

    int deleteByIdAndCompany_Id(Long id, Long companyId);
}

