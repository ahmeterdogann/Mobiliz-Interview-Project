package com.ahmeterdogan.data.repository;

import com.ahmeterdogan.data.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface IGroupRepository extends JpaRepository<Group, Long> {
    Optional<Group> findByIdAndCompany_Id(Long groupId, Long companyId);

    Set<Group> findAllByCompany_IdAndAndRootIsTrue(Long companyId);

    void deleteByIdAndCompany_Id(Long id, Long companyId);
}
