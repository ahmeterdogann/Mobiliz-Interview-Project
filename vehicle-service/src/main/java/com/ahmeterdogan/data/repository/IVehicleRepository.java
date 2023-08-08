package com.ahmeterdogan.data.repository;

import com.ahmeterdogan.data.entity.Fleet;
import com.ahmeterdogan.data.entity.Vehicle;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IVehicleRepository extends JpaRepository<Vehicle, Long> {
    // Araç ekleme işlemi
    Vehicle save(Vehicle vehicle);

    // Araç silme işlemi
    void deleteById(long id);

    // Id'ye göre araç getirme işlemi
    Optional<Vehicle> findById(long id);

    // Tüm araçları listeleme işlemi
    List<Vehicle> findAll();

    Fleet getFleetById(Long fleetId);
}

