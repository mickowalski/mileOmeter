package com.briskscript.mileometer.DTO;

import com.briskscript.mileometer.validators.FieldMatch;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Setter
@Getter
@FieldMatch.List({
        @FieldMatch(first = "password", second = "confirmPassword", message = "Hasła muszą się zgadzać"),
        @FieldMatch(first = "email", second = "confirmEmail", message = "Email musi się zgadzać")
})
public class UserRegistrationDto {

    private Long id;
    @NotEmpty
    private String userName;
    @NotEmpty
    private String password;
    @NotEmpty
    private String confirmPassword;
    @Email
    @NotEmpty
    private String email;
    @Email
    @NotEmpty
    private String confirmEmail;
}
