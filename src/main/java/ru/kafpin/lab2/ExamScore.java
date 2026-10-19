package ru.kafpin.lab2;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import jakarta.validation.constraints.*;

@Entity
@Table(name = "exam_scores")
public class ExamScore {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Название предмета не должно быть пустым")
    private String subject;

    @Min(value = 0, message = "Минимальный балл - 0")
    @Max(value = 100, message = "Максимальный балл - 100")
    private int score;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "enrollee_id", nullable = false)
    private Enrollee enrollee;

    public ExamScore() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }
    public int getScore() { return score; }
    public void setScore(int score) { this.score = score; }
    public Enrollee getEnrollee() { return enrollee; }
    public void setEnrollee(Enrollee enrollee) { this.enrollee = enrollee; }
}

