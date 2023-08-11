package com.ahmeterdogan.feign;

import com.ahmeterdogan.data.entity.Company;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "company-service", url = "http://localhost:8085/api/v1/companies")
public interface ICompanyServiceFeign {
    @GetMapping("/find-by-name")
    Company getCompanyByName(@RequestParam("companyName") String companyName);
}
