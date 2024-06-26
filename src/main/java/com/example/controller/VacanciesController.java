package com.example.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.example.entity.Company;
import com.example.entity.JobSeekers;
import com.example.entity.Questions;
import com.example.entity.Vacancies;

import org.springframework.ui.Model;

import jakarta.servlet.http.HttpSession;

@Controller
public class VacanciesController {
	
	/////////////////////This codes related to the Company to add the vacancy///////////////
	@GetMapping("/addVacancy")
	public ModelAndView addProduct(Model model, HttpSession session) {
	    Vacancies vacancies = new Vacancies();
	    model.addAttribute("vacancies", vacancies);
	    Company company = (Company) session.getAttribute("company");
	    int cid = company.getCompanyId();
	    System.out.println("companyId:::" + cid);
	    model.addAttribute("companyId", company.getCompanyId());
	    ModelAndView view = new ModelAndView("Company/addVacancy");
	    return view;
	}

	@PostMapping("/addVacancy")
	public ModelAndView addVacancy(@ModelAttribute("vacancies") Vacancies vacancy, Model model, HttpSession session) {
	    // Get companyId from session or wherever it's stored
	    int companyId = ((Company) session.getAttribute("company")).getCompanyId();
	    
	    // Check if the Company object in the Vacancy is null, if so, initialize it
	    if (vacancy.getCompany() == null) {
	        vacancy.setCompany(new Company());
	    }
	    
	    // Set companyId in the Vacancy object's company attribute
	    vacancy.getCompany().setCompanyId(companyId);
	    
	    HttpHeaders headers = new HttpHeaders();
	    headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	    HttpEntity<Vacancies> entity = new HttpEntity<Vacancies>(vacancy, headers);
	    
	    RestTemplate restTemplate = new RestTemplate();
	    String url = "http://localhost:8091/AspireHub/api/v1/addVacancy";
	    
	    ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
	    String responseString = response.getBody();
	    
	    
	    //storing the vacancyId in session
	   //int vacancyId = Integer.parseInt(responseString); 
        //session.setAttribute("vacancyId", vacancyId);
       
        
        
	    
	    // Clearing the vacancy object after submission
	    vacancy = new Vacancies();
	    model.addAttribute("vacancies", vacancy);
	    
	    ModelAndView view = new ModelAndView("Company/addVacancy");
	    model.addAttribute("responseString", responseString);

	    return view;
	}
	
	@GetMapping("/deleteVacancies/{vacancyId}")
	public String deleteVacancies(@PathVariable("vacancyId") Integer vacancyId) {
	    RestTemplate restTemplate = new RestTemplate();
	    restTemplate.delete("http://localhost:8091/AspireHub/api/v1/deleteVacancies/" + vacancyId);
	    ModelAndView view = new ModelAndView("/Company/viewVacancy");
	    view.addObject("responseMsg", "vacancy deleted successfully");
	    return "redirect:/viewVacancy";
	}
	
	@GetMapping("/updateVacancies/{vacancyId}")
	public ModelAndView updateVacancies(@PathVariable("vacancyId") int vacancyId) {
	    RestTemplate restTemplate = new RestTemplate();
	    ResponseEntity<Vacancies> response = restTemplate.getForEntity("http://localhost:8091/AspireHub/api/v1/getVacancies/" + vacancyId, Vacancies.class);
	    Vacancies vacancy = response.getBody();
	    
	    ModelAndView view = new ModelAndView("Company/updateVacancies");
	    view.addObject("vacancy", vacancy);
	    return view;
	}
	
	@PostMapping("/updateVacancies")
	public String updateVacancies(@ModelAttribute("vacancy") Vacancies vacancy, Model model, HttpSession session) {
	    // Retrieve the Company object from the session
	    Company company = (Company) session.getAttribute("company");
	    
	    // Set the Company object in the Vacancies object
	    vacancy.setCompany(company);
	    
	    // Proceed with updating the vacancies
	    HttpHeaders headers = new HttpHeaders();
	    headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	    HttpEntity<Vacancies> entity = new HttpEntity<>(vacancy, headers);
	    RestTemplate restTemplate = new RestTemplate();
	    restTemplate.put("http://localhost:8091/AspireHub/api/v1/updateVacancies/" + vacancy.getVacancyId(), entity);
	    
	    // Redirect to the viewVacancy page
	    return "redirect:/viewVacancy";
	}


}
