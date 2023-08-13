package com.ahmeterdogan.controller;

import com.ahmeterdogan.data.entity.Company;
import com.ahmeterdogan.service.CompanyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.ahmeterdogan.constants.ApiUrl.*;

@RestController
@RequestMapping(COMPANIES)
public class CompanyController {
    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping(FIND_BY_NAME)
    public ResponseEntity<Company> getCompanyByName(@RequestParam("companyName") String companyName) {
        return ResponseEntity.ok(companyService.getCompanyByName(companyName));
    }

}
