package ru.kafpin.lab2;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.kafpin.lab2.repository.EnrolleeRepository;

import java.util.List;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/api/enrollees")
public class EnrolleeRestController {

    @Autowired
    private EnrolleeRepository enrolleeRepository;

    @GetMapping
    public List<EnrolleeDTO> getEnrollees(@RequestParam(value = "searchType", required = false) String searchType,
                                          @RequestParam(value = "keyword", required = false) String keyword) {
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
                        int score = Integer.parseInt(keyword.trim());
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

        return java.util.stream.StreamSupport.stream(enrollees.spliterator(), false)
                .map(EnrolleeDTO::new)
                .toList();
    }

    @GetMapping("/{id}")
    public EnrolleeDTO getEnrolleeById(@PathVariable("id") Long id) {
        Enrollee enrollee = enrolleeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Абитуриент с ID " + id + " не найден"));
        return new EnrolleeDTO(enrollee);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EnrolleeDTO createEnrollee(
            @RequestBody
            @Valid
            EnrolleeDTO dto) {
        Enrollee enrollee = new Enrollee();
        enrollee.setLastName(dto.getLastName());
        enrollee.setFirstName(dto.getFirstName());
        enrollee.setMiddleName(dto.getMiddleName());
        enrollee.setGender(dto.getGender());
        enrollee.setNationality(dto.getNationality());
        enrollee.setBirthDate(dto.getBirthDate());
        enrollee.setPassingScore(dto.getPassingScore());

        Address address = new Address();
        address.setZipCode(dto.getZipCode());
        address.setCountry(dto.getCountry());
        address.setCity(dto.getCity());
        address.setStreet(dto.getStreet());
        address.setHouse(dto.getHouse());
        address.setApartment(dto.getApartment());

        enrollee.setAddress(address);

        Enrollee savedEnrollee = enrolleeRepository.save(enrollee);
        return new EnrolleeDTO(savedEnrollee);
    }

    @PutMapping("/{id}")
    public EnrolleeDTO updateEnrollee(@PathVariable("id") Long id, @RequestBody @Valid EnrolleeDTO dto) {
        Enrollee enrollee = enrolleeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Абитуриент с ID " + id + " не найден"));

        enrollee.setLastName(dto.getLastName());
        enrollee.setFirstName(dto.getFirstName());
        enrollee.setMiddleName(dto.getMiddleName());
        enrollee.setGender(dto.getGender());
        enrollee.setNationality(dto.getNationality());
        enrollee.setBirthDate(dto.getBirthDate());
        enrollee.setPassingScore(dto.getPassingScore());

        if (enrollee.getAddress() == null) {
            enrollee.setAddress(new Address());
        }
        enrollee.getAddress().setZipCode(dto.getZipCode());
        enrollee.getAddress().setCountry(dto.getCountry());
        enrollee.getAddress().setCity(dto.getCity());
        enrollee.getAddress().setStreet(dto.getStreet());
        enrollee.getAddress().setHouse(dto.getHouse());
        enrollee.getAddress().setApartment(dto.getApartment());

        Enrollee updatedEnrollee = enrolleeRepository.save(enrollee);
        return new EnrolleeDTO(updatedEnrollee);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEnrollee(@PathVariable("id") Long id) {
        if (!enrolleeRepository.existsById(id)) {
            throw new EntityNotFoundException("Абитуриент с ID " + id + " не найден");
        }
        enrolleeRepository.deleteById(id);
    }
}