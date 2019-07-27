package com.briskscript.mileometer.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "role")
    private String name;

    public Role() {
    }
    public Role(String name) {
        this.name = name;
    }
}
