package com.ahmeterdogan.data.repository;

import com.ahmeterdogan.data.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICompanyRepository extends JpaRepository<Company, Long> {
    Company findCompanyByName(String companyName);
}
