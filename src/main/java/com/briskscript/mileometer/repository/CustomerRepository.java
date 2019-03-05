package com.briskscript.mileometer.repository;

import com.briskscript.mileometer.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Customer findFirstByEmail(String email);
}
