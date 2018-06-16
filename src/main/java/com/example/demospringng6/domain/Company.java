package com.example.demospringng6.domain;


import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;


@Builder
@ToString
@Getter
@Entity
@NoArgsConstructor(force = true) //sets values to its defaults
@AllArgsConstructor
@EqualsAndHashCode
public class Company {

    @Id
    @GeneratedValue
    private Long id;

    @NonNull
    @Column(unique = true)
    @Size(min = 2, max = 255)
    private final String name;
    @NonNull
    @Size(min = 3, max = 255)
    private final String address;
    @NonNull
    private final String city;
    @NonNull
    private final String country;
    @Email
    private final String email;

    private final String phone;

    @NotEmpty
    private final String owners;

}