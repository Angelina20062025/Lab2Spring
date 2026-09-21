package ru.kafpin.lab2.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.kafpin.lab2.Enrollee;

@Repository
public interface EnrolleeRepository extends CrudRepository<Enrollee, Long> {
}