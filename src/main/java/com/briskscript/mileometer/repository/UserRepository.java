package com.briskscript.mileometer.repository;

import com.briskscript.mileometer.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findFirstByEmail(String email);
}
