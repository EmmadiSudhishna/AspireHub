package com.example.entity;

import java.util.Date;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="test")
public class Test {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="testId")
	private int testId;
	
	@Column(name="testDate")
	private String testDate;
	
	@Column(name="testDuration")
	private int testDuration;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="vacancyId")
	private Vacancies vacancies;

}
