package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;


import com.example.entity.Company;


public interface CompanyRepository extends JpaRepository<Company,Integer>{

public	Company findByEmailAndPassword(String email, String password);
	
	Company findFirstByEmail(String email);
	
}
