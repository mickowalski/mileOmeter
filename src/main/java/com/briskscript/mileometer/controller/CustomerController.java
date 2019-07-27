package com.briskscript.mileometer.controller;

import com.briskscript.mileometer.model.Customer;
import com.briskscript.mileometer.repository.CustomerRepository;
import com.briskscript.mileometer.repository.DetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
public class CustomerController {

    final private CustomerRepository customerRepository;

    final private DetailsRepository detailsRepository;

    @Autowired
    public CustomerController(CustomerRepository customerRepository, DetailsRepository detailsRepository) {
        this.customerRepository = customerRepository;
        this.detailsRepository = detailsRepository;
    }

    @ModelAttribute("customers")
    public List<Customer> customersList() {
        return customerRepository.findAll(new Sort(Sort.Direction.ASC, "lastName"));
    }

    //    Customer
    @GetMapping("/customers")
    public String customersMain(@RequestParam(required = false) Long id, Model model) {
        if (id == null) {
            return "customer/list";
        }
        Customer customer = new Customer();
        Optional<Customer> optionalCustomer = customerRepository.findById(id);
        if (optionalCustomer.isPresent()) {
            customer = optionalCustomer.get();
        }
        model.addAttribute("customer", customer);
        return "customer/form";
    }

    @PostMapping("/customers")
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
        return "redirect:/customers";
    }

    @GetMapping("/customers/confirmDelete")
    public String confirmDeletion(@RequestParam Long id, @RequestParam String name, Model model) {
        model.addAttribute("id", id);
        model.addAttribute("name", name);
        return "customer/delete";
    }

    @GetMapping("/customers/delete")
    public String deleteCustomer(@RequestParam Long id) {
        customerRepository.deleteById(id);
        return "redirect:/customers";
    }

    //    Mileometer
    @GetMapping("/")
    public String mileometer(Model model) {
        Date presentDate = new Date();
        model.addAttribute("mileometer", detailsRepository.dataForMileometer(presentDate));
        return "customer/mileometer";
    }
}
