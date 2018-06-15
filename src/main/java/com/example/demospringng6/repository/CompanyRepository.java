package com.example.demospringng6.repository;

import com.example.demospringng6.domain.Company;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CompanyRepository extends CrudRepository<Company, Integer> {

    List<Company> findAll();

    Optional<Company> findCompanyByName(String name);
}
