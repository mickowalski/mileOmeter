package com.briskscript.mileometer.model;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "cruises")
public class Cruise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty
    @Size(max = 25)
    @Column(nullable = false, length = 25)
    private String name;
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date start;
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date end;
    @Max(30)
    private Integer maxPpl;
    @Column(name = "yacht_name")
    private String yachtName;
    @Column(name = "yacht_desc")
    private String yachtDesc;
    private Boolean archive;

    @OneToMany(mappedBy = "cruise", cascade = CascadeType.REMOVE)
    private List<Details> details;

    public Cruise() {
        this.maxPpl = 10;
        this.yachtName = "BEE WET";
        this.yachtDesc = "Bavaria 49";
        this.archive = false;
    }

}
