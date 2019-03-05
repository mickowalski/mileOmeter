package com.briskscript.mileometer.controller;

import com.briskscript.mileometer.entity.Customer;
import com.briskscript.mileometer.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    CustomerRepository customerRepository;

    @ModelAttribute("customers")
    public List<Customer> customersList() {
        return customerRepository.findAll(new Sort(Sort.Direction.ASC, "lastName"));
    }

    @GetMapping("")
    public String customersMain(@RequestParam(required = false) Long id, Model model) {
        if (id == null) {
            return "customer/list";
        }
        Customer customer = id == -1L ? new Customer() : customerRepository.getOne(id);
        model.addAttribute("customer", customer);
        return "customer/form";
    }

    @PostMapping("")
    public String SaveOrUpdateCustomer(@Valid Customer customer, BindingResult result) {
        Customer existingCustomer = customerRepository.findFirstByEmail(customer.getEmail());
        if (existingCustomer != null && customer.getId() == null) {
            FieldError error = new FieldError("customer", "email", "Email must be unique");
            result.addError(error);
        }
        if (result.hasErrors()) {
            return "customer/form";
        }
        customerRepository.save(customer);
        return "redirect:customers";
    }

    @GetMapping("/confirmDelete")
    public String confirmDeletion(@RequestParam Long id, @RequestParam String name, Model model) {
        model.addAttribute("id", id);
        model.addAttribute("name", name);
        return "customer/delete";
    }

    @GetMapping("/delete")
    public String deleteCustomer(@RequestParam Long id) {
        customerRepository.deleteById(id);
        return "redirect:/cruises";
    }
}
