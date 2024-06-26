package com.example.entity;

import java.util.Date;

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
@Table(name="vacancies")
public class Vacancies {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	
	@Column(name="vacancyId")
	private int vacancyId;
	
	@ManyToOne
	@JoinColumn(name="companyId")
	private Company company; //getting the  company object to maintain the foreignKey relation
	//@Column(name="companyId")
	//private int companyId;
	
	@Column(name="postDate")
	private String postDate;
	
	@Column(name="jobTitle")
	private String jobTitle;
	
	@Column(name="description")
	private String description;
	
	@Column(name="requirements")
	private String requirements;
	
	@Column(name="noOfVacancies")
	private int noOfVacancies;
	
	@Column(name="openDate")
	private String openDate;
	
	@Column(name="closeDate")
	private String closeDate;


	


}
