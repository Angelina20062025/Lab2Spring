package ru.kafpin.lab2;

import jakarta.persistence.*;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "enrollees")
public class Enrollee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Фамилия обязательна")
    @Size(min = 2, max = 40, message = "Некорректная длина фамилии")
    private String lastName;

    @NotBlank(message = "Имя обязательно")
    private String firstName;

    private String middleName;

    @NotBlank(message = "Укажите пол")
    private String gender;

    @NotBlank(message = "Укажите национальность")
    private String nationality;

    @NotNull(message = "Дата рождения обязательна")
    @Past(message = "Дата рождения должна быть в прошлом")
    private LocalDate birthDate;

    @Min(value = 0, message = "Проходной балл не может быть отрицательным")
    @Max(value = 400, message = "Превышен максимальный балл")
    private int passingScore;

    @Valid
    @OneToOne(cascade = CascadeType.PERSIST, orphanRemoval = true)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;

    @OneToMany(mappedBy = "enrollee", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<ExamScore> examScores = new ArrayList<>();

    public void addExamScore(ExamScore score) {
        examScores.add(score);
        score.setEnrollee(this);
    }

    public Enrollee() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getMiddleName() { return middleName; }
    public void setMiddleName(String middleName) { this.middleName = middleName; }
    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }
    public String getNationality() { return nationality; }
    public void setNationality(String nationality) { this.nationality = nationality; }
    public LocalDate getBirthDate() { return birthDate; }
    public void setBirthDate(LocalDate birthDate) { this.birthDate = birthDate; }
    public int getPassingScore() { return passingScore; }
    public void setPassingScore(int passingScore) { this.passingScore = passingScore; }
    public Address getAddress() { return address; }
    public void setAddress(Address address) { this.address = address; }
    public List<ExamScore> getExamScores() { return examScores; }
    public void setExamScores(List<ExamScore> examScores) { this.examScores = examScores; }
}

//@Entity
//@Table(name = "enrollees")
//public class Enrollee {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    private String lastName;
//    private String firstName;
//    private String middleName;
//    private String gender;
//    private String nationality;
//    private LocalDate birthDate;
//    private String examScores;
//    private int passingScore;
//
//    public Enrollee() {}
//
//    public Long getId() { return id; }
//    public void setId(Long id) { this.id = id; }
//    public String getLastName() { return lastName; }
//    public void setLastName(String lastName) { this.lastName = lastName; }
//    public String getFirstName() { return firstName; }
//    public void setFirstName(String firstName) { this.firstName = firstName; }
//    public String getMiddleName() { return middleName; }
//    public void setMiddleName(String middleName) { this.middleName = middleName; }
//    public String getGender() { return gender; }
//    public void setGender(String gender) { this.gender = gender; }
//    public String getNationality() { return nationality; }
//    public void setNationality(String nationality) { this.nationality = nationality; }
//    public LocalDate getBirthDate() { return birthDate; }
//    public void setBirthDate(LocalDate birthDate) { this.birthDate = birthDate; }
//    public String getExamScores() { return examScores; }
//    public void setExamScores(String examScores) { this.examScores = examScores; }
//    public int getPassingScore() { return passingScore; }
//    public void setPassingScore(int passingScore) { this.passingScore = passingScore; }
//}