package com.briskscript.mileometer.controller;

import com.briskscript.mileometer.entity.Cruise;
import com.briskscript.mileometer.entity.Details;
import com.briskscript.mileometer.repository.CruiseRepository;
import com.briskscript.mileometer.repository.CustomerRepository;
import com.briskscript.mileometer.repository.DetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("cruises/{cruiseId}")
public class DetailsController {

    final private DetailsRepository detailsRepository;

    final private CruiseRepository cruiseRepository;

    final private CustomerRepository customerRepository;

    @Autowired
    public DetailsController(DetailsRepository detailsRepository, CruiseRepository cruiseRepository, CustomerRepository customerRepository) {
        this.detailsRepository = detailsRepository;
        this.cruiseRepository = cruiseRepository;
        this.customerRepository = customerRepository;
    }

    @GetMapping("")
    public String detailsList(@PathVariable(value = "cruiseId") Long cruiseId, @RequestParam(required = false) Long id, Model model) {
        Optional<Cruise> cruise = cruiseRepository.findById(cruiseId);
        if (!cruise.isPresent()) {
            return "redirect:/cruises";
        }
        if (id == null) {
            List<Details> detailsList = detailsRepository.findAllByCruise(cruise.get());
            model.addAttribute("detailsList", detailsList);
            model.addAttribute("cruise", cruise.get());
            model.addAttribute("count", detailsRepository.countAllByCruise(cruise.get()));
            return "details/list";
        }
        Details details = new Details();
        Optional<Details> optionalDetails = detailsRepository.findById(id);
        if (optionalDetails.isPresent()) {
            details = optionalDetails.get();
        }
        details.setCruise(cruise.get());
        model.addAttribute("detail", details);
        model.addAttribute("customers", customerRepository.findAll(new Sort(Sort.Direction.ASC, "lastName")));
        return "details/form";

    }

    @PostMapping("")
    public String SaveOrUpdateDetails(@PathVariable Long cruiseId, @Valid Details details, BindingResult result) {
        if (result.hasErrors()) {
            return "details/form";
        }
        detailsRepository.save(details);
        return "redirect:/cruises/" + cruiseId;
    }

    @GetMapping("/confirmDelete")
    public String confirmDeletion(@PathVariable Long cruiseId, @RequestParam Long id, Model model) {
        model.addAttribute("id", id);
        model.addAttribute("cruiseId", cruiseId);
        return "details/delete";
    }

    @GetMapping("/delete")
    public String deleteDetails(@PathVariable Long cruiseId, @RequestParam Long id) {
        detailsRepository.deleteById(id);
        return "redirect:/cruises/" + cruiseId;
    }
}
