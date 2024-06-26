package com.example.entity;

import java.util.Date;

import lombok.Data;

@Data
public class Test {
	private int testId;
	private String testDate;
	private int testDuration;
	private int vacancyId;
	private Vacancies vacancies;
	
	

}
