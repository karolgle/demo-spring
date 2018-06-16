package com.example.demospringng6.controller;

import com.example.demospringng6.domain.Company;
import com.example.demospringng6.domain.CompanyInfo;
import com.example.demospringng6.helper.CompanyHelper;
import com.example.demospringng6.service.CompanyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/")
public class CompanyController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CompanyController.class);

    private final CompanyService companyService;


    @Autowired
    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping()
    public String index() {
        return "Greetings from Spring Boot!";
    }

    @GetMapping("/companies/{name}")
    ResponseEntity<?> getCompany(@PathVariable("name") String companyName) {

        Optional<Company> company = companyService.getCompanyByName(companyName);

        if (company.isPresent()) {
            return ResponseEntity.ok(CompanyHelper.convertToCompanyInfo(company.get()));
        }

        return ResponseEntity.notFound()
                             .build();
    }

    @GetMapping("/companies")
    ResponseEntity<?> getCompanies() {
        return ResponseEntity.ok(companyService.getAllCompanies()
                                               .stream()
                                               .map(Company::getName)
                                               .collect(Collectors.toList()));
    }

    @PostMapping("/companies")
    ResponseEntity<?> createCompany(@RequestBody @Valid CompanyInfo companyInfo, BindingResult bindingResult) {
        Optional<Company> existingCompany = companyService.getCompanyByName(companyInfo.getName());

        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body(bindingResult.getAllErrors()
                                                    .get(0)
                                                    .getDefaultMessage());
        }

        if (existingCompany.isPresent()) {
            LOGGER.info("Company with such name already exists!!!");
            return ResponseEntity.status(HttpStatus.CONFLICT)
                                 .build();
        }

        CompanyInfo savedCompanyInfo = CompanyHelper.convertToCompanyInfo(companyService.save(companyInfo));

        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(savedCompanyInfo);
    }

    @PutMapping("/companies")
    ResponseEntity<?> updateCompany(@RequestBody @Valid CompanyInfo companyInfo, BindingResult bindingResult) {
        Optional<Company> existingCompany = companyService.getCompanyByName(companyInfo.getName());

        if (!existingCompany.isPresent()) {
            LOGGER.info("Company not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .build();
        }

        Company companyToUpdate = Company.builder()
                                         .id(existingCompany.get()
                                                            .getId())
                                         .name(existingCompany.get()
                                                              .getName())
                                         .address(companyInfo.getAddress())
                                         .city(companyInfo.getCity())
                                         .country(companyInfo.getCountry())
                                         .email(companyInfo.getEmail())
                                         .phone(companyInfo.getPhone())
                                         .owners(CompanyHelper.ownersToString(companyInfo.getOwners()))
                                         .build();

        CompanyInfo savedCompanyInfo = CompanyHelper.convertToCompanyInfo(companyService.save(companyToUpdate));

        return ResponseEntity.ok(savedCompanyInfo);
    }

    @PostMapping("/companies/{name}/owner")
    ResponseEntity<?> addOwner(@PathVariable("name") String companyName, @RequestBody String newOwner) {
        Optional<Company> existingCompany = companyService.getCompanyByName(companyName);

        if (!existingCompany.isPresent()) {
            LOGGER.info("Company with such name does not exist!!!");
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .build();
        }


        if (CompanyHelper.ownersToList(existingCompany.get()
                                                      .getOwners())
                         .stream()
                         .noneMatch(owner -> owner.equalsIgnoreCase(newOwner))) {
            List<String> ownersList = new ArrayList<String>();
            ownersList.add(newOwner);
            ownersList.addAll(CompanyHelper.ownersToList(existingCompany.get()
                                                                        .getOwners()));

            Company companyToUpdate = Company.builder()
                                             .id(existingCompany.get()
                                                                .getId())
                                             .name(existingCompany.get()
                                                                  .getName())
                                             .address(existingCompany.get()
                                                                     .getAddress())
                                             .city(existingCompany.get()
                                                                  .getCity())
                                             .country(existingCompany.get()
                                                                     .getCountry())
                                             .email(existingCompany.get()
                                                                   .getEmail())
                                             .phone(existingCompany.get()
                                                                   .getPhone())
                                             .owners(CompanyHelper.ownersToString(ownersList))
                                             .build();

            return ResponseEntity.status(HttpStatus.CREATED)
                                 .body(CompanyHelper.convertToCompanyInfo(companyService.save(companyToUpdate)));
        }


        return ResponseEntity.badRequest()
                             .build();
    }
}
