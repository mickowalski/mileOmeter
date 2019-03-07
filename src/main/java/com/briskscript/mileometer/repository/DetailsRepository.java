package com.briskscript.mileometer.repository;

import com.briskscript.mileometer.entity.Cruise;
import com.briskscript.mileometer.entity.Details;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DetailsRepository extends JpaRepository<Details, Long> {

    List<Details> findAllByCruise(Cruise cruise);

    Long countAllByCruise(Cruise cruise);
}
