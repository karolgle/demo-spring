package com.example.demospringng6.helper;

import com.example.demospringng6.domain.Company;
import com.example.demospringng6.domain.CompanyInfo;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

public class CompanyHelper {

    public static Company convertToCompany(CompanyInfo companyInfo){
        return Company.builder()
                          .name(companyInfo.getName())
                          .address(companyInfo.getAddress())
                          .city(companyInfo.getName())
                          .country(companyInfo.getCountry())
                          .email(companyInfo.getEmail())
                          .phone(companyInfo.getPhone())
                          .owners(companyInfo.getOwners())
                          .build();
    }

    public static CompanyInfo convertToCompanyInfo(Company company){
        return CompanyInfo.builder()
                          .name(company.getName())
                          .address(company.getAddress())
                          .city(company.getName())
                          .country(company.getCountry())
                          .email(company.getEmail())
                          .phone(company.getPhone())
                          .owners(company.getOwners())
                          .build();
    }

    public boolean isValidEmailAddress(String email) {
        boolean result = true;
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException ex) {
            result = false;
        }
        return result;
    }
}
