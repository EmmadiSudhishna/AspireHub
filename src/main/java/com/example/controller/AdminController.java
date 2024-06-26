package com.example.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.example.entity.Admin;
import com.example.entity.Company;
import com.example.entity.JobSeekers;

import com.example.entity.Vacancies;


import org.springframework.ui.Model;

@Controller
public class AdminController {
	
	
	@GetMapping("/title")
	public ModelAndView title() {
		ModelAndView view = new ModelAndView("title");
		return view;
	}
	
	@GetMapping("/menu")
	public ModelAndView menu() {
		ModelAndView view=new ModelAndView("menu");
		return view;
	}
	
	@GetMapping("/index")
	public ModelAndView index() {
		ModelAndView view=new ModelAndView("index");
		return view;
	}
	@GetMapping("/About")
	public ModelAndView About() {
		ModelAndView view=new ModelAndView("About");
		return view;
	}
	@GetMapping("/contact")
	public ModelAndView contact() {
		ModelAndView view=new ModelAndView("contact");
		return view;
	}
	@GetMapping("/logout")
	public ModelAndView logout() {
		ModelAndView view=new ModelAndView("logout");
		return view;
	}
	/*@GetMapping("/")
	public String index1() {
	    return "index";
	}*/

	@GetMapping("/adminLogin")
    public ModelAndView adminLogin(Model model) {
        Admin admin = new Admin();
        model.addAttribute("admin", admin);
        ModelAndView view = new ModelAndView("Admin/adminLogin");
        return view;
    }
	
	@PostMapping("/ValidateAdminLogin")
	public ModelAndView validateAdminLogin(@ModelAttribute("admin")Admin admin, Model model) {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		
		HttpEntity<Admin> entity = new HttpEntity<Admin>(admin, headers);
		
		RestTemplate restTemplate = new RestTemplate();
		
		   
		
		  
		ResponseEntity<Boolean> response = restTemplate.exchange("http://localhost:8091/AspireHub/api/v1/adminLogin",
				HttpMethod.POST, entity, Boolean.class);
		
		boolean responseFlag = response.getBody();
		//System.out.println("responseFlag= "+responseFlag);
		
		if(responseFlag == true) {
			ModelAndView view = new ModelAndView("Admin/adminHome");
			return view;
		}
		else {
			  model.addAttribute("responseText","Username/Password incorrect...");

			ModelAndView view = new ModelAndView("Admin/adminLogin");
			return view;
		}
		
	}
	
	@GetMapping("/adminHome")
	public String adminHome() {
	    return "Admin/adminHome"; 
	}


	@GetMapping("/jobseekersList")
    public ModelAndView viewJobSeekers(Model model) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<JobSeekers[]> responseEntity = restTemplate.getForEntity("http://localhost:8091/AspireHub/api/v1/allJobSeekers", JobSeekers[].class);
        JobSeekers[] responseBody = responseEntity.getBody();
        List<JobSeekers> jobseekersList = Arrays.asList(responseBody);
        model.addAttribute("jobseekersList", jobseekersList);
        ModelAndView view = new ModelAndView("Admin/jobseekersList");
        return view;
    }
	
	@GetMapping("/viewCompanies")
	 public ModelAndView viewCompanies(Model model) {
	     RestTemplate restTemplate = new RestTemplate();
	     ResponseEntity<Company[]> responseEntity = restTemplate.getForEntity("http://localhost:8091/AspireHub/api/v1/allCompanies", Company[].class);
	     Company[] responseBody = responseEntity.getBody();
	     List<Company> companyList = Arrays.asList(responseBody);
	     model.addAttribute("companyList", companyList);
	     ModelAndView view = new ModelAndView("Admin/companyList");
	     return view;
	 }
	
	@GetMapping("/viewVacancies")
	 public ModelAndView viewVacancies(Model model) {
	     RestTemplate restTemplate = new RestTemplate();
	     ResponseEntity<Vacancies[]> responseEntity = restTemplate.getForEntity("http://localhost:8091/AspireHub/api/v1/allVacancies", Vacancies[].class);
	     Vacancies[] responseBody = responseEntity.getBody();
	     List<Vacancies> vacanciesList = Arrays.asList(responseBody);
	     model.addAttribute("vacanciesList", vacanciesList);
	     ModelAndView view = new ModelAndView("Admin/vacanciesList");
	     return view;
	 }
}
