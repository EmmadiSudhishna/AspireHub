package com.example.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "testresult")
public class TestResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "resultId")
    private int resultId;

    @ManyToOne
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private JobSeekers jobseekers;

    @ManyToOne
    @JoinColumn(name = "vacancyId")
    private Vacancies vacancies;


    @ManyToOne
    @JoinColumn(name = "questionId")
    private Questions question;

    @Column(name = "selectedOption")
    private String selectedOption;

    @Column(name = "result")
    private String result;

    @Column(name = "score")
    private String score;
}
