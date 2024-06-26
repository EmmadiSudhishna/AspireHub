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
@Table(name="questions")
public class Questions {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="questionId")
	private int questionId;
	
	@ManyToOne
	@JoinColumn(name="testId", referencedColumnName="testId")
	private Test test;
	
	
	@ManyToOne
	@JoinColumn(name="vacancyId", referencedColumnName="vacancyId")
	private Vacancies vacancies;
	
	@Column(name="question")
	private String question;
	
	@Column(name="option1")
	private String option1;
	
	@Column(name="option2")
	private String option2;
	
	@Column(name="option3")
	private String option3;
	
	@Column(name="option4")
	private String option4;
	
	@Column(name="correct")
	private String correct;
	
	@Column(name="score")
	private String score;

}
