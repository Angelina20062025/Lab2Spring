package ru.kafpin.lab2;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kafpin.lab2.repository.EnrolleeRepository;

import java.util.Optional;

@Controller
@RequestMapping("/enrollees")
public class EnrolleeController {

    @Autowired
    private EnrolleeRepository enrolleeRepository;

    @GetMapping("/main")
    public String mainPage(@RequestParam(value = "searchType", required = false) String searchType,
                           @RequestParam(value = "keyword", required = false) String keyword,
                           Model model) {
        Iterable<Enrollee> enrollees;

        if (keyword != null && !keyword.trim().isEmpty()) {
            switch (searchType) {
                case "lastName":
                    enrollees = enrolleeRepository.findByLastName(keyword);
                    break;
                case "city":
                    enrollees = enrolleeRepository.findByAddressCity(keyword);
                    break;
                case "score":
                    try {
                        int score = Integer.parseInt(keyword);
                        enrollees = enrolleeRepository.findByPassingScoreGreaterThanEqual(score);
                    } catch (NumberFormatException e) {
                        enrollees = enrolleeRepository.findAll();
                    }
                    break;
                default:
                    enrollees = enrolleeRepository.findAll();
            }
        } else {
            enrollees = enrolleeRepository.findAll();
        }

        model.addAttribute("enrollees", enrollees);
        return "main";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        Enrollee enrollee = new Enrollee();
        enrollee.setAddress(new Address());
        model.addAttribute("enrollee", enrollee);
        return "edit_enrollee";
    }

    @PostMapping("/save")
    public String saveEnrollee(@ModelAttribute("enrollee") @Valid Enrollee enrollee,
                               BindingResult result) {
        if (result.hasErrors()) {
            return "edit_enrollee";
        }
        enrolleeRepository.save(enrollee);
        return "redirect:/enrollees/main";
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

//    @GetMapping("/create")
//    public String createForm(Model model) {
//        model.addAttribute("enrollee", new Enrollee());
//        return "edit_enrollee";
//    }

    @GetMapping("/update/{id}")
    public String updateForm(@PathVariable("id") Long id, Model model) {
        Optional<Enrollee> enrollee = enrolleeRepository.findById(id);
        if (enrollee.isEmpty()) {
            return "redirect:/enrollees/main";
        }
        model.addAttribute("enrollee", enrollee.get());
        return "edit_enrollee";
    }

//    @PostMapping("/save")
//    public String saveEnrollee(@ModelAttribute Enrollee enrollee) {
//        enrolleeRepository.save(enrollee);
//        return "redirect:/enrollees/main";
//    }

    @GetMapping("/delete/{id}")
    public String deleteEnrollee(@PathVariable("id") Long id) {
        if (enrolleeRepository.existsById(id)) {
            enrolleeRepository.deleteById(id);
        }
        return "redirect:/enrollees/main";
    }
}