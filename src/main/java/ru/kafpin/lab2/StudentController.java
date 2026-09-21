package ru.kafpin.lab2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kafpin.lab2.repository.StudentRepository;

import java.util.Optional;

@Controller
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;

    @GetMapping("/main")
    public String mainPage(Model model) {
        model.addAttribute("students", studentRepository.findAll());
        return "students_main";
    }

    @GetMapping("/details/{id}")
    public String details(Model model, @PathVariable("id") Long id) {
        Optional<Student> student = studentRepository.findById(id);
        if (student.isEmpty()) {
            return "redirect:/students/main";
        }
        model.addAttribute("selectedStudent", student.get());
        return "student_details";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("student", new Student());
        return "edit_student";
    }

    @GetMapping("/update/{id}")
    public String editStudent(Model model,
                              @PathVariable("id") Long id) {
        Optional<Student> optionalStudent =
                studentRepository.findById(id);
        if (optionalStudent.isEmpty()) {
            return "redirect:/students/main";
        }
        model.addAttribute("student", optionalStudent.get());
        return "edit_student";
    }

    @PostMapping("/save")
    public String saveStudent(@ModelAttribute Student student) {
        studentRepository.save(student);
        return "redirect:/students/main";
    }

    @GetMapping("/delete/{id}")
    public String delete(Model model, @PathVariable("id") Long id) {
        if (studentRepository.existsById(id)) {
            studentRepository.deleteById(id);
        }
        return "redirect:/students/main";
    }
}