package com.example.controller;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Admin;
import com.example.entity.Company;

import com.example.repository.CompanyRepository;

import com.example.service.CompanyService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/v1")
public class CompanyController {
	
	
	@Autowired
	CompanyService companyService;
	
	@PostMapping(value="/addCompany")
	public ResponseEntity<Object> addCompany(@RequestBody Company company){
	    try {
	        companyService.saveCompany(company);
	        return new ResponseEntity<>("Company added successfully", HttpStatus.CREATED);
	    } catch (Exception e) {
	        return new ResponseEntity<>("Failed to add company: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}

	
	
	



	@PostMapping(value="/companyLogin")
	public ResponseEntity<Object> companyLogin(@RequestBody Company company){
	    Company company1 = companyService.loginValidate(company);
	    return new ResponseEntity<>(company1, HttpStatus.OK);
	}

	
	
	 
	    @GetMapping(value = "/getCompanyByEmail/{email}")
	    public Company getCompanyByEmail(@PathVariable("email") String email) {
	        return companyService.findCompanyByEmail(email);
	    }

	
	@GetMapping("/allCompanies")
	public ResponseEntity<Object> getallCompanies(){
		List<Company> company=companyService.getAllCompanies();
		ResponseEntity<Object> entity=new ResponseEntity<>(company, HttpStatus.OK);
		return entity;
		
	}
	
	
	@GetMapping(value = "/getCompany/{companyId}")
	public ResponseEntity<Object> getCompany(@PathVariable("companyId") Integer companyId) {
	    // Check if companyId is a valid positive integer
	    if (companyId <= 0) {
	        // Handle the case where companyId is not a valid integer
	        return ResponseEntity.badRequest().body("Invalid companyId: " + companyId);
	    }
	    
	    try {
	        Company company = companyService.getCompanyById(companyId);
	        if (company != null) {
	            return new ResponseEntity<>(company, HttpStatus.OK);
	        } else {
	            return new ResponseEntity<>("Company not found", HttpStatus.NOT_FOUND);
	        }
	    } catch (NumberFormatException e) {
	        // Handle the case where companyId is not a valid integer
	        return ResponseEntity.badRequest().body("Invalid companyId: " + companyId);
	    }
	}

		/*Company company;
		if(companyService.isCompanyExist(companyId)) {
			company =companyService.getCompanyById(companyId);
		}else {
			company=null;
		}
		ResponseEntity<Object>entity=new ResponseEntity<>(company, HttpStatus.OK);
		return entity;*/
		
	
	
	@DeleteMapping("/deleteCompany/{companyId}")
	public ResponseEntity<Object> deleteCompany(@PathVariable("companyId")int companyId){
		boolean flag;
		if(companyService.isCompanyExist(companyId)) {
			flag=companyService.deleteCompany(companyId);
			
		}else {
			flag=false;
		}
		return new ResponseEntity<>(flag, HttpStatus.OK);
		
	}
	
	
	
	 @PutMapping(value = "/updateCompany/{companyId}")
	    public ResponseEntity<Object> updateCompany(@PathVariable("companyId") int companyId, @RequestBody Company company) {
	        boolean updated = companyService.updateCompany(company);
	        if (updated) {
	            return new ResponseEntity<>("Company updated successfully", HttpStatus.OK);
	        } else {
	            return new ResponseEntity<>("Company not found", HttpStatus.NOT_FOUND);
	        }
	    }

	
	
	
	


}
