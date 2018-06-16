package com.example.demospringng6.domain;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class CompanyInfo {

    @NotNull
    @Size(min = 2, max = 30)
    private String name;

    @Size(min = 3, max = 255)
    private String address;

    @NotNull
    private String city;
    @NotNull
    private String country;
    @Email
    private String email;
    private String phone;
    @NotNull
    @Size(min = 1)
    private List<String> owners;
}
