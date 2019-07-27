package com.briskscript.mileometer.controller;

import com.briskscript.mileometer.DTO.UserRegistrationDto;
import com.briskscript.mileometer.model.User;
import com.briskscript.mileometer.repository.UserRepository;
import com.briskscript.mileometer.service.UserService;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;


@Controller
public class UserController {


    private final UserRepository userRepository;
    private final UserService userService;

    @Autowired
    public UserController(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @ModelAttribute("users")
    public List<User> usersList() {
        return userRepository.findAll(new Sort(Sort.Direction.ASC, "userName"));
    }

    @GetMapping("/users")
    public String users(@RequestParam(required = false) Long id, Model model) {
        if (id == null) {
            return "user/list";
        }
        User user = new User();
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            user = userOptional.get();
        }
        Mapper mapper = new DozerBeanMapper();
        UserRegistrationDto userDto =
                mapper.map(user, UserRegistrationDto.class);
        model.addAttribute("user", userDto);
        return "user/form";
    }

    @PostMapping("/users")
    public String saveUser(@ModelAttribute("user") @Valid UserRegistrationDto userDto, BindingResult result) {

        User existing = userService.findByEmail(userDto.getEmail());
        if (existing != null) {
            result.rejectValue("userName", null, "Istnieje już użytkownik  tej nazwie.");
        }
        if (result.hasErrors()) {
            return "user/form";
        }

        userService.save(userDto);
        return "redirect:/users";
    }


    @GetMapping("/users/confirmDelete")
    public String confirmDeletion(@RequestParam Long id, @RequestParam String userName, Model model) {
        model.addAttribute("id", id);
        model.addAttribute("userName", userName);
        return "user/delete";
    }

    @GetMapping("/users/delete")
    public String deleteUser(@RequestParam Long id) {
        userRepository.deleteById(id);
        return "redirect:/users";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
