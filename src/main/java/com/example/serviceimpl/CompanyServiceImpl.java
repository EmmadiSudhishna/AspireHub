package com.example.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Company;
import com.example.repository.CompanyRepository;
import com.example.service.CompanyService;

@Service
public class CompanyServiceImpl implements CompanyService {
	//private final CompanyRepository companyRepository;
	
	@Autowired 
    CompanyRepository companyRepository;


	
	@Override
	public Company saveCompany(Company company) {
		// TODO Auto-generated method stub
		return companyRepository.save(company);
	}


	@Override
	public List<Company> getAllCompanies() {
		// TODO Auto-generated method stub
		return companyRepository.findAll();
	}


	@Override
	public boolean isCompanyExist(int companyId) {
		// TODO Auto-generated method stub
		return companyRepository.existsById(companyId);
	}


	@Override
	public Company getCompanyById(int companyId) {
		// TODO Auto-generated method stub
		return companyRepository.findById(companyId).orElse(null);
	}


	@Override
	public boolean deleteCompany(int companyId) {
		// TODO Auto-generated method stub
		
		 if (companyRepository.existsById(companyId)) { // Check if company with given ID exists
	            companyRepository.deleteById(companyId); // Delete company by ID
	            return true;
	        }
		return false;
	}


	@Override
	public boolean updateCompany(Company company) {
		// TODO Auto-generated method stub
		if (isCompanyExist(company.getCompanyId())) {
            companyRepository.save(company);
            return true;
        }

		return false;
	}


	

	



	@Override
	public Company findCompanyByEmail(String email) {
		// TODO Auto-generated method stub
		return companyRepository.findFirstByEmail(email);
	}


	/*@Override
	public boolean loginValidate(Company company) {
		// TODO Auto-generated method stub
		return false;
	}*/


	@Override
	public Company loginValidate(Company company) {
		// TODO Auto-generated method stub
		
		Company company1 = companyRepository.findByEmailAndPassword(company.getEmail(), company.getPassword());
        System.out.println("what is there in company=" + company1);
        return company1;
	}
	
}


