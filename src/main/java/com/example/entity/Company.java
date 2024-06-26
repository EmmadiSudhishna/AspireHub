package com.example.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="company")
public class Company {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="companyId")
	private int companyId;
	
	@Column(name="companyName")
	private String companyName;
	
	@Column(name="regDate")
	private String regDate;
	
	@Column(name="email")
	private String email;
	
	@Column(name="mobile")
	private String mobile;
	
	@Column(name="location")
	private String location;
	
	@Column(name="city")
	private String city;
	
	@Column(name="state")
	private String state;
	
	@Column(name="companyLevel")
	private String companyLevel;
	
	@Column(name="url")
	private String url;
	
	@Column(name="logo")
	private String logo;
	
	@Column(name="password")
	private String password;
	


}
