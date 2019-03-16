package com.briskscript.mileometer.entity;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Details> getDetails() {
        return details;
    }

    public void setDetails(List<Details> details) {
        this.details = details;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public Integer getMaxPpl() {
        return maxPpl;
    }

    public void setMaxPpl(Integer maxPpl) {
        this.maxPpl = maxPpl;
    }

    public String getYachtName() {
        return yachtName;
    }

    public void setYachtName(String yachtName) {
        this.yachtName = yachtName;
    }

    public String getYachtDesc() {
        return yachtDesc;
    }

    public void setYachtDesc(String yachtDesc) {
        this.yachtDesc = yachtDesc;
    }

    public Boolean getArchive() {
        return archive;
    }

    public void setArchive(Boolean archive) {
        this.archive = archive;
    }
}
