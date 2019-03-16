package com.briskscript.mileometer.controller;

import com.briskscript.mileometer.entity.Cruise;
import com.briskscript.mileometer.repository.CruiseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/cruises")
public class CruiseController {

    final private CruiseRepository cruiseRepository;

    @Autowired
    public CruiseController(CruiseRepository cruiseRepository) {
        this.cruiseRepository = cruiseRepository;
    }

    @ModelAttribute("cruises")
    public List<Cruise> crusesList() {
        return cruiseRepository.findCruisesByArchiveFalseOrderByStartAsc();
    }

    @ModelAttribute("archive")
    public List<Cruise> archiveList() {
        return cruiseRepository.findCruisesByArchiveTrueOrderByEndDesc();
    }

    @GetMapping("/archive")
    public String archive() {
        return "cruise/archive";
    }

    @GetMapping("/toArchive")
    public String toArchive(@RequestParam Long id) {
        Optional<Cruise> cruise = cruiseRepository.findById(id);
        if (cruise.isPresent()) {
            cruise.get().setArchive(true);
            cruiseRepository.save(cruise.get());
        }
        return "redirect:/cruises";
    }

    @GetMapping("")
    public String cruisesMain(@RequestParam(required = false) Long id, Model model) {
        if (id == null) {
            return "cruise/list";
        }
        Cruise cruise = new Cruise();
        Optional<Cruise> optionalCruise = cruiseRepository.findById(id);
        if (optionalCruise.isPresent()) {
            cruise = optionalCruise.get();
            cruise.setArchive(false);
        }
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
