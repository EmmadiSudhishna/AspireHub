package com.example.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.entity.Admin;
import com.example.entity.Company;

@Service
public interface CompanyService {
	Company saveCompany(Company company);

	List<Company> getAllCompanies();

	boolean isCompanyExist(int companyId);

	Company getCompanyById(int companyId);

	boolean updateCompany(Company company);
	boolean deleteCompany(int companyId);
	
	//Company loginValidate(Company company);

	Company findCompanyByEmail(String email);
	public Company loginValidate(Company company);
}
