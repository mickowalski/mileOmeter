package com.briskscript.mileometer.controller;

import com.briskscript.mileometer.entity.Cruise;
import com.briskscript.mileometer.repository.CruiseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/cruises")
public class CruiseController {

    @Autowired
    CruiseRepository cruiseRepository;

    @ModelAttribute("cruises")
    public List<Cruise> crusesList() {
        return cruiseRepository.findAll(new Sort(Sort.Direction.ASC, "start"));
    }

    @GetMapping("")
    public String cruisesMain(@RequestParam(required = false) Long id, Model model) {
        if (id == null) {
            return "cruise/list";
        }
        Cruise cruise = id == -1 ? new Cruise() : cruiseRepository.getOne(id);
        model.addAttribute("cruise", cruise);
        return "cruise/form";
    }

    @PostMapping("")
    public String SaveOrUpdateCruise(@Valid Cruise cruise, BindingResult result) {
        if (result.hasErrors()) {
            return "cruise/form";
        }
        cruiseRepository.save(cruise);
        return "redirect:cruises";
    }

    @GetMapping("/confirmDelete")
    public String confirmDeletion(@RequestParam Long id, @RequestParam String name, Model model) {
        model.addAttribute("id", id);
        model.addAttribute("name", name);
        return "cruise/delete";
    }

    @GetMapping("/delete")
    public String deleteCruise(@RequestParam Long id) {
        cruiseRepository.deleteById(id);
        return "redirect:/cruises";
    }
}
