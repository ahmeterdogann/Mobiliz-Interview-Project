package com.ahmeterdogan.data.repository;

import com.ahmeterdogan.data.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IGroupRepository extends JpaRepository<Group, Long> {

}
