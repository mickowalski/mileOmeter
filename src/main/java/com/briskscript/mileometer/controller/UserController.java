package com.briskscript.mileometer.controller;

import com.briskscript.mileometer.entity.User;
import com.briskscript.mileometer.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/users")
public class UserController {

    final private UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @ModelAttribute("users")
    public List<User> usersList() {
        return userRepository.findAll(new Sort(Sort.Direction.ASC, "nick"));
    }

    @GetMapping("")
    public String users(@RequestParam(required = false) Long id, Model model) {
        if (id == null) {
            return "user/list";
        }
        User user = new User();
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            user = userOptional.get();
        }
        model.addAttribute("user", user);
        return "user/form";
    }

    @PostMapping("")
    public String SaveOrUpdateUser(@Valid User user, BindingResult result) {
        User existingUser = userRepository.findFirstByEmail(user.getEmail());
        if (existingUser != null && user.getId() == null) {
            FieldError error = new FieldError("user", "email", "Email must be unique");
            result.addError(error);
        }
        if (!user.getPassword().equals(user.getCheckPassword())) {
            FieldError error = new FieldError("user", "password", "Passwords don't match");
            result.addError(error);
        }
        if (result.hasErrors()) {
            return "user/form";
        }
        userRepository.save(user);
        return "redirect:/users";
    }

    @GetMapping("/confirmDelete")
    public String confirmDeletion(@RequestParam Long id, @RequestParam String nick, Model model) {
        model.addAttribute("id", id);
        model.addAttribute("nick", nick);
        return "user/delete";
    }

    @GetMapping("/delete")
    public String deleteUser(@RequestParam Long id) {
        userRepository.deleteById(id);
        return "redirect:/users";
    }
}
