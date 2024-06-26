package com.example.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;

@Data
@Entity
@Table(name = "jobapply")
public class JobApply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "applyId")
    private int applyId;
    @CreationTimestamp
    @Column(name = "applyDate")
    private String applyDate;
    
    @Column(name = "finalScore")
    private String finalScore;
    
    @Column(name = "status")
    private String status;
    
    

    @ManyToOne
    @JoinColumn(name="userId")
    private JobSeekers jobseekers;

    @ManyToOne
    @JoinColumn(name = "vacancyid")
    private Vacancies vacancies;
}
