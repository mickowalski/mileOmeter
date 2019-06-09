package com.briskscript.mileometer.repository;

import com.briskscript.mileometer.model.Cruise;
import com.briskscript.mileometer.model.Details;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface DetailsRepository extends JpaRepository<Details, Long> {

    List<Details> findAllByCruise(Cruise cruise);

    Long countAllByCruise(Cruise cruise);

    @Query(value = "SELECT cu.firstName, cu.lastName, SUM (dt.miles) AS sumed FROM Details dt JOIN dt.customer cu " +
            "JOIN dt.cruise cr WHERE cr.end<=?1 AND cr.archive=true GROUP BY cu.id ORDER BY sumed DESC ")
    List<Object[]> dataForMileometer(Date from);

}
