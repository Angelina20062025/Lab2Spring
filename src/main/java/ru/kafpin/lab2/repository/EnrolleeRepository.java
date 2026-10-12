package ru.kafpin.lab2.repository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.kafpin.lab2.Enrollee;

import java.util.List;

@Repository
public interface EnrolleeRepository extends CrudRepository<Enrollee, Long> {
    List<Enrollee> findByLastName(String lastName);
    List<Enrollee> findByAddressCity(String city);
    List<Enrollee> findByPassingScoreGreaterThanEqual(int score);
}