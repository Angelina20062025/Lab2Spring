package ru.kafpin.lab2.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.kafpin.lab2.Student;

@Repository
public interface StudentRepository extends CrudRepository<Student, Long> {
}
