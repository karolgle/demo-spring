package com.example.demospringng6.controller;

import com.example.demospringng6.domain.Company;
import com.example.demospringng6.domain.CompanyInfo;
import com.example.demospringng6.helper.CompanyHelper;
import com.example.demospringng6.service.CompanyService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CompanyControllerIntegTest {

    @MockBean
    private CompanyService companyService;

    @Autowired
    private TestRestTemplate restTemplate;

    private List<Company> testCompanies;

    @Before
    public void setUp() {
        prepareTestData();
    }

    @Test
    public void shouldGetCompany() throws Exception {
        // given
        given(companyService.getCompanyByName(any(String.class)))
                .willReturn(Optional.of(testCompanies.get(0)));
        CompanyInfo companyInfo = CompanyHelper.convertToCompanyInfo(testCompanies.get(0));

        // when
        ResponseEntity<CompanyInfo> getCompanyResponse = restTemplate.getForEntity("/companies/{name}", CompanyInfo.class, "1");
        //then
        assertThat(getCompanyResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getCompanyResponse.getBody()).isEqualTo(companyInfo);
    }

    @Test
    public void shouldNotGetCompany() throws Exception {
        // given
        given(companyService.getCompanyByName(any(String.class)))
                .willReturn(Optional.empty());
        CompanyInfo companyInfo = CompanyHelper.convertToCompanyInfo(testCompanies.get(0));

        // when
        ResponseEntity<CompanyInfo> getCompanyResponse = restTemplate.getForEntity("/companies/{name}", CompanyInfo.class, "1");
        //then
        assertThat(getCompanyResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(getCompanyResponse.getBody()).isNull();
    }

    @Test
    public void shouldGetCompaniesList() throws Exception {

        // given
        given(companyService.getAllCompanies()).willReturn(testCompanies);

        // when
        ResponseEntity<List> getCompaniesResponse = restTemplate.getForEntity("/companies", List.class);

        //then
        List<String> companyNames = ((List<String>) getCompaniesResponse.getBody());
        assertThat(getCompaniesResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(companyNames).contains(this.testCompanies.get(0)
                                                            .getName(), this.testCompanies.get(0)
                                                                                          .getName());
    }

    @Test
    public void shouldNotCreateCompany() {

        // given
        given(companyService.getCompanyByName(any(String.class))).willReturn(Optional.ofNullable(testCompanies.get(0)));

        CompanyInfo companyInfo = CompanyHelper.convertToCompanyInfo(testCompanies.get(0));
        // when
        ResponseEntity<CompanyInfo> createCompanyResponse = restTemplate.postForEntity("/companies",
                companyInfo, CompanyInfo.class);

        // then
        assertThat(createCompanyResponse.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
    }

    @Test
    public void shouldCreateCompany() {

        // given
        given(companyService.getCompanyByName(any(String.class))).willReturn(Optional.empty());
        given(companyService.save(any(CompanyInfo.class))).willReturn(testCompanies.get(0));
        CompanyInfo companyInfo = CompanyHelper.convertToCompanyInfo(testCompanies.get(0));
        // when
        ResponseEntity<CompanyInfo> createCompanyResponse = restTemplate.postForEntity("/companies",
                companyInfo, CompanyInfo.class);

        // then
        assertThat(createCompanyResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(createCompanyResponse.getBody()).isEqualTo(companyInfo);
    }

    @Test
    public void shouldNotCreateCompanyBecauseToShortAddress() {

        // given
        Company invalidCompany = Company.builder()
                                          .id(3)
                                          .name("Compan1")
                                          .address("Ad")
                                          .city("New City")
                                          .country("New Country")
                                          .email("new.email@aaa.pl")
                                          .phone("+48 44444444")
                                          .owners(Arrays.asList("Joe Briks", "Mary Bee"))
                                          .build();

        given(companyService.getCompanyByName(any(String.class))).willReturn(Optional.empty());
        given(companyService.save(any(CompanyInfo.class))).willReturn(invalidCompany);
        CompanyInfo companyInfo = CompanyHelper.convertToCompanyInfo(invalidCompany);
        // when
        ResponseEntity<String> createCompanyResponse = restTemplate.postForEntity("/companies",
                companyInfo, String.class);

        // then
        assertThat(createCompanyResponse.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(createCompanyResponse.getBody()).isEqualTo("size must be between 3 and 255");
    }

    @Test
    public void shouldNotCreateCompanyBecauseZeroOwners() {

        // given
        Company invalidCompany = Company.builder()
                                        .id(3)
                                        .name("Compan1")
                                        .address("Address")
                                        .city("New City")
                                        .country("New Country")
                                        .email("new.email@aaa.pl")
                                        .phone("+48 44444444")
                                        .owners(new ArrayList<>())
                                        .build();

        given(companyService.getCompanyByName(any(String.class))).willReturn(Optional.empty());
        given(companyService.save(any(CompanyInfo.class))).willReturn(invalidCompany);
        CompanyInfo companyInfo = CompanyHelper.convertToCompanyInfo(invalidCompany);
        // when
        ResponseEntity<String> createCompanyResponse = restTemplate.postForEntity("/companies",
                companyInfo, String.class);

        // then
        assertThat(createCompanyResponse.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(createCompanyResponse.getBody()).isEqualTo("size must be between 1 and 2147483647");
    }

    @Test
    public void shouldNotCreateCompanyBecauseTooShortName() {

        // given
        Company invalidCompany = Company.builder()
                                        .id(3)
                                        .name("C")
                                        .address("Address")
                                        .city("New City")
                                        .country("New Country")
                                        .email("new.email@aaa.pl")
                                        .phone("+48 44444444")
                                        .owners(Arrays.asList("Joe Briks", "Mary Bee"))
                                        .build();

        given(companyService.getCompanyByName(any(String.class))).willReturn(Optional.empty());
        given(companyService.save(any(CompanyInfo.class))).willReturn(invalidCompany);
        CompanyInfo companyInfo = CompanyHelper.convertToCompanyInfo(invalidCompany);
        // when
        ResponseEntity<String> createCompanyResponse = restTemplate.postForEntity("/companies",
                companyInfo, String.class);

        // then
        assertThat(createCompanyResponse.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(createCompanyResponse.getBody()).isEqualTo("size must be between 2 and 30");
    }

    @Test
    public void shouldNotCreateCompanyBecauseBadEmail() {

        // given
        Company invalidCompany = Company.builder()
                                        .id(3)
                                        .name("Company 1")
                                        .address("Address")
                                        .city("New City")
                                        .country("New Country")
                                        .email("new.email")
                                        .phone("+48 44444444")
                                        .owners(Arrays.asList("Joe Briks", "Mary Bee"))
                                        .build();

        given(companyService.getCompanyByName(any(String.class))).willReturn(Optional.empty());
        given(companyService.save(any(CompanyInfo.class))).willReturn(invalidCompany);
        CompanyInfo companyInfo = CompanyHelper.convertToCompanyInfo(invalidCompany);
        // when
        ResponseEntity<String> createCompanyResponse = restTemplate.postForEntity("/companies",
                companyInfo, String.class);

        // then
        assertThat(createCompanyResponse.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(createCompanyResponse.getBody()).isEqualTo("must be a well-formed email address");
    }

    @Test
    public void shouldUpdateCompany() {

        // given
        Company companyForUpdate = Company.builder()
                                          .id(2)
                                          .name("Company Name 2")
                                          .address("New Address 2")
                                          .city("New City")
                                          .country("New Country")
                                          .email("new.email@aaa.pl")
                                          .phone("+48 44444444")
                                          .owners(Arrays.asList("Joe Briks", "Mary Bee"))
                                          .build();

        CompanyInfo updateCompanyInfo = CompanyHelper.convertToCompanyInfo(companyForUpdate);

        given(companyService.getCompanyByName(any(String.class))).willReturn(Optional.ofNullable(testCompanies.get(1)));
        given(companyService.save(any(Company.class))).willReturn(companyForUpdate);

        // when
        ResponseEntity<CompanyInfo> createCompanyResponse =
                restTemplate.exchange("/companies", HttpMethod.PUT, new HttpEntity<>(updateCompanyInfo), CompanyInfo.class);

        // then
        assertThat(createCompanyResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(createCompanyResponse.getBody()).isEqualTo(updateCompanyInfo);
    }

    @Test
    public void shouldUpdateOwnersForCompany() {

        // given
        Company companyForUpdate = Company.builder()
                                          .id(2)
                                          .name("Company Name 2")
                                          .address("New Address 2")
                                          .city("New City")
                                          .country("New Country")
                                          .email("new.email@aaa.pl")
                                          .phone("+48 44444444")
                                          .owners(Arrays.asList("Joe Briks", "Mary Bee"))
                                          .build();

        Company alteredCompany = Company.builder()
                                  .id(2)
                                  .name("Company Name 2")
                                  .address("New Address 2")
                                  .city("New City")
                                  .country("New Country")
                                  .email("new.email@aaa.pl")
                                  .phone("+48 44444444")
                                  .owners(Arrays.asList("New Owner Kate Mentis", "Joe Briks", "Mary Bee"))
                                  .build();


        CompanyInfo updateCompanyInfo = CompanyHelper.convertToCompanyInfo(companyForUpdate);

        given(companyService.getCompanyByName(any(String.class))).willReturn(Optional.ofNullable(testCompanies.get(1)));
        given(companyService.save(any(Company.class))).willReturn(alteredCompany);

        // when
        ResponseEntity<CompanyInfo> createCompanyResponse =
                restTemplate.exchange("/companies", HttpMethod.PUT, new HttpEntity<>(updateCompanyInfo), CompanyInfo.class);

        ResponseEntity<CompanyInfo> companyWithUpdatedOwners = restTemplate.postForEntity("/companies/{name}/owner",
                "New Owner Kate Mentis", CompanyInfo.class, "Company Name 2");

        // then
        assertThat(companyWithUpdatedOwners.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(createCompanyResponse.getBody()).isEqualTo(CompanyHelper.convertToCompanyInfo(alteredCompany));
    }

    private void prepareTestData() {
        Company company1 = Company.builder()
                                  .id(1)
                                  .name("Company Name 1")
                                  .address("Address 1")
                                  .city("Vien")
                                  .country("Poland")
                                  .email("aaa@aaa.pl")
                                  .phone("+48 556 222 333")
                                  .owners(Arrays.asList("Jenny Bliz", "John Fritz"))
                                  .build();

        Company company2 = Company.builder()
                                  .id(2)
                                  .name("Company Name 2")
                                  .address("Adress 2")
                                  .city("Warsaw")
                                  .country("Poland")
                                  .email("aaa@aaa.pl")
                                  .phone("+48 123456798")
                                  .owners(Arrays.asList("Joe Klis", "Mary Bee"))
                                  .build();

        this.testCompanies = Arrays.asList(company1, company2);
    }
}
