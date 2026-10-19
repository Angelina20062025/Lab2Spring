package ru.kafpin.lab2;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

public class EnrolleeDTO {
    private Long id;

    @NotBlank(message = "Фамилия обязательна")
    private String lastName;

    @NotBlank(message = "Имя обязательно")
    private String firstName;

    private String middleName;

    @NotBlank(message = "Укажите пол")
    private String gender;

    @NotBlank(message = "Укажите национальность")
    private String nationality;

    @NotNull(message = "Дата рождения обязательна")
    private LocalDate birthDate;

    private int passingScore;

    @NotBlank(message = "Почтовый индекс обязателен")
    private String zipCode;

    @NotBlank(message = "Страна обязательна")
    private String country;

    @NotBlank(message = "Город обязателен")
    private String city;

    @NotBlank(message = "Улица обязательна")
    private String street;

    @NotBlank(message = "Номер дома обязателен")
    private String house;

    private String apartment;

    public EnrolleeDTO() {}

    public EnrolleeDTO(Enrollee enrollee) {
        this.id = enrollee.getId();
        this.lastName = enrollee.getLastName();
        this.firstName = enrollee.getFirstName();
        this.middleName = enrollee.getMiddleName();
        this.gender = enrollee.getGender();
        this.nationality = enrollee.getNationality();
        this.birthDate = enrollee.getBirthDate();
        this.passingScore = enrollee.getPassingScore();

        if (enrollee.getAddress() != null) {
            this.zipCode = enrollee.getAddress().getZipCode();
            this.country = enrollee.getAddress().getCountry();
            this.city = enrollee.getAddress().getCity();
            this.street = enrollee.getAddress().getStreet();
            this.house = enrollee.getAddress().getHouse();
            this.apartment = enrollee.getAddress().getApartment();
        } else {
            this.zipCode = "Не указан";
            this.country = "Не указана";
            this.city = "Не указан";
            this.street = "Не указан";
            this.house = "Не указан";
            this.apartment = "";
        }
    }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }
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
    public String getZipCode() { return zipCode; }
    public void setZipCode(String zipCode) { this.zipCode = zipCode; }
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
    public String getStreet() { return street; }
    public void setStreet(String street) { this.street = street; }
    public String getHouse() { return house; }
    public void setHouse(String house) { this.house = house; }
    public String getApartment() { return apartment; }
    public void setApartment(String apartment) { this.apartment = apartment; }
}