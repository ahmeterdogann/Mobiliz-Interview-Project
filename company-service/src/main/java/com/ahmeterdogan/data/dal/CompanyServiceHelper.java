package com.ahmeterdogan.data.dal;

import com.ahmeterdogan.data.entity.Company;
import com.ahmeterdogan.data.repository.ICompanyRepository;
import org.springframework.stereotype.Component;

@Component
public class CompanyServiceHelper {
    private final ICompanyRepository companyRepository;

    public CompanyServiceHelper(ICompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public Company getCompanyByName(String companyName) {
        return companyRepository.findCompanyByName(companyName);
    }

}
