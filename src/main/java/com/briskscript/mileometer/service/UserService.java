package com.briskscript.mileometer.service;

import com.briskscript.mileometer.DTO.UserRegistrationDto;
import com.briskscript.mileometer.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    User findByEmail(String email);

    User save(UserRegistrationDto user);
}
