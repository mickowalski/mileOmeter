package com.briskscript.mileometer.controller;

import com.briskscript.mileometer.DTO.UserRegistrationDto;
import com.briskscript.mileometer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/registration")
public class UserRegistrationController {

    private final UserService userService;

    @Autowired
    UserRegistrationController(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute("user")
    public UserRegistrationDto userRegistrationDto() {
        return new UserRegistrationDto();
    }

    @GetMapping
    public String showRegistrationForm(Model model) {
        return "registration";
    }

//    @PostMapping
//    public String registerUserAccount(@ModelAttribute("user") @Valid UserRegistrationDto userDto, BindingResult result) {
//
//        User existing = userService.findByUserName(userDto.getUserName());
//        if (existing != null) {
//            result.rejectValue("email", null, "Istnieje już konto zarejetrowane na podany email");
//        }
//        if (result.hasErrors()) {
//            return "registration";
//        }
//
//        userService.save(userDto);
//        return "redirect:/registration?success";
//    }
}
