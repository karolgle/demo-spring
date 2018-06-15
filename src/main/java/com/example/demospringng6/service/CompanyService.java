package com.example.demospringng6.service;

import com.example.demospringng6.domain.Company;
import com.example.demospringng6.domain.CompanyInfo;
import com.example.demospringng6.helper.CompanyHelper;
import com.example.demospringng6.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {

    private CompanyRepository companyRepository;

    @Autowired
    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public Company save(CompanyInfo companyInfo) {
        return this.save(CompanyHelper.convertToCompany(companyInfo));
    }


    public Company save(Company company) {
        return companyRepository.save(company);
    }

    public Optional<Company> getCompanyById(int id) {
        return companyRepository.findById(id);
    }

    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    public Optional<Company> getCompanyByName(String name) {
        return companyRepository.findCompanyByName(name);
    }
}
