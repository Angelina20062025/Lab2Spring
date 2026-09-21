package ru.kafpin.lab2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kafpin.lab2.repository.EnrolleeRepository;

import java.util.Optional;

@Controller
@RequestMapping("/enrollees")
public class EnrolleeController {

    @Autowired
    private EnrolleeRepository enrolleeRepository;

    @GetMapping("/main")
    public String mainPage(Model model) {
        model.addAttribute("enrollees", enrolleeRepository.findAll());
        return "main";
    }

    @GetMapping("/details/{id}")
    public String detailsPage(@PathVariable("id") Long id, Model model) {
        Optional<Enrollee> enrollee = enrolleeRepository.findById(id);
        if (enrollee.isEmpty()) {
            return "redirect:/enrollees/main";
        }
        model.addAttribute("enrollee", enrollee.get());
        return "details";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("enrollee", new Enrollee());
        return "edit_enrollee";
    }

    @GetMapping("/update/{id}")
    public String updateForm(@PathVariable("id") Long id, Model model) {
        Optional<Enrollee> enrollee = enrolleeRepository.findById(id);
        if (enrollee.isEmpty()) {
            return "redirect:/enrollees/main";
        }
        model.addAttribute("enrollee", enrollee.get());
        return "edit_enrollee";
    }

    @PostMapping("/save")
    public String saveEnrollee(@ModelAttribute Enrollee enrollee) {
        enrolleeRepository.save(enrollee);
        return "redirect:/enrollees/main";
    }

    @GetMapping("/delete/{id}")
    public String deleteEnrollee(@PathVariable("id") Long id) {
        if (enrolleeRepository.existsById(id)) {
            enrolleeRepository.deleteById(id);
        }
        return "redirect:/enrollees/main";
    }
}