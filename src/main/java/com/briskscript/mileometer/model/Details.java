package com.briskscript.mileometer.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "cruise_details")
public class Details {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer miles;
    @Column(precision = 8, scale = 2)
    private Double price;
    @Column(precision = 8, scale = 2)
    private Double paid;
    private Integer instalment;
    private Integer instalments;


    @ManyToOne()
    private Customer customer;

    @ManyToOne
    private Cruise cruise;

}
