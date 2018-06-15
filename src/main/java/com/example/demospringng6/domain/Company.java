package com.example.demospringng6.domain;


import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.List;


@Builder(toBuilder = true)
@ToString
@Getter
@Entity
@NoArgsConstructor(force = true) //sets values to its defaults
@RequiredArgsConstructor
@EqualsAndHashCode
public class Company {

    @Id
    @GeneratedValue
    private final int id;


    @NonNull
    @Column(unique=true)
    @Size(min=2, max=30)
    private final String name;
    @NonNull
    @Size(min=3, max=255)
    private final String address;
    @NonNull
    private final String city;
    @NonNull
    private final String country;
    @Email
    private final String email;

    private final String phone;

    @NonNull
    @Embedded
    @ElementCollection
    @Size(min=1)
    private final List<String> owners;

}