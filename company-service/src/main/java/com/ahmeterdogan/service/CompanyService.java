package com.ahmeterdogan.service;

import com.ahmeterdogan.data.dal.CompanyServiceHelper;
import com.ahmeterdogan.data.entity.Company;
import org.springframework.stereotype.Service;

@Service
public class CompanyService {
    private final CompanyServiceHelper companyServiceHelper;

    public CompanyService(CompanyServiceHelper companyServiceHelper) {
        this.companyServiceHelper = companyServiceHelper;
    }

   public Company getCompanyByName(String companyName) {
        return companyServiceHelper.getCompanyByName(companyName);
   }
}
