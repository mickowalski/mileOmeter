package com.briskscript.mileometer.repository;

import com.briskscript.mileometer.entity.Cruise;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CruiseRepository extends JpaRepository<Cruise, Long> {

    List<Cruise> findCruisesByArchiveFalseOrderByStartAsc();

    List<Cruise> findCruisesByArchiveTrueOrderByEndDesc();
}
