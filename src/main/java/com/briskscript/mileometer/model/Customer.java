package com.briskscript.mileometer.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @Column(nullable = false)
    private String firstName;
    @NotBlank
    @Column(nullable = false)
    private String lastName;
    private String pesel;
    private String street;
    private String zipCode;
    private String city;
    @NotBlank
    @Email
    @Column(nullable = false, unique = true)
    private String email;
    private String phone;
    private String shirt;

    @OneToMany(mappedBy = "customer")
    private List<Details> details;

}
