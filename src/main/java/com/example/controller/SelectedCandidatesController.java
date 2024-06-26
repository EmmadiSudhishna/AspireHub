package com.example.controller;

import java.util.Arrays;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.example.entity.Company;
import com.example.entity.EMail;
import com.example.entity.JobApply;
import com.example.entity.SelectedCandidates;
import com.example.entity.Test;

import jakarta.servlet.http.HttpSession;

@Controller
public class SelectedCandidatesController {
	
	
	@GetMapping("/selectCandidate/{applyId}")
	public String getJobSeekersByApplyId(@PathVariable int applyId, HttpSession session, Model model)
	{
		Company company1 = new Company();
		int companyId = ((Company) session.getAttribute("company")).getCompanyId();
		
	    // Make a request to the backend controller to retrieve JobSeekers by applyId
	    RestTemplate restTemplate = new RestTemplate();
	    
	    ResponseEntity<JobApply> response = restTemplate.exchange("http://localhost:8091/AspireHub/api/v1/getApplication/" + applyId,
	    		HttpMethod.GET, null,JobApply.class);
	    
	    	JobApply JobApply = response.getBody();
	        // Add the JobSeekers object to the model
	    	
	    	RestTemplate restTemplate1 = new RestTemplate();
		    ResponseEntity<Test> response1 = restTemplate.exchange("http://localhost:8091/AspireHub/api/v1/findTestByVacancyId/" +JobApply.getVacancies().getVacancyId(),
		    		HttpMethod.GET, null, Test.class);
		    
			model.addAttribute("companyId", companyId);
			
	        Test Test=response1.getBody();
	        model.addAttribute("Test",Test);
	        
	        SelectedCandidates selectedcandidates = new SelectedCandidates();
	        EMail email = new EMail();
	        email.setBody("ij8yth7e3366");
	        email.setSubject("demo");
	        email.setToMail(JobApply.getJobseekers().getEmail());
	        selectedcandidates.setVacancies(JobApply.getVacancies());
	        selectedcandidates.setStatus(JobApply.getStatus());
	        selectedcandidates.setTest(Test);
	        selectedcandidates.setJobseekers(JobApply.getJobseekers());
	        selectedcandidates.setScore(JobApply.getFinalScore());
	        //selectedcandidates.setScore(JobApply.getFinalScore());// Set up RestTemplate and HTTP headers
	        
	        HttpHeaders headers = new HttpHeaders();
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
			HttpEntity<SelectedCandidates> entity = new HttpEntity<SelectedCandidates>(selectedcandidates, headers);
			
			RestTemplate restTemplate3 = new RestTemplate();
			ResponseEntity<String> response3 = restTemplate.exchange("http://localhost:8091/AspireHub/api/v1/addCandidates", HttpMethod.POST,
					entity, String.class);
			String responseMsg = response3.getBody();
			/////////////send mail code 
			HttpHeaders headers1 = new HttpHeaders();
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
			HttpEntity<EMail> entity1 = new HttpEntity<>(email, headers);
			RestTemplate restTemplate4 = new RestTemplate();
			ResponseEntity<String> response4 = restTemplate.exchange("http://localhost:8091/AspireHub/sendMail", HttpMethod.POST,
					entity1, String.class);
			String responseMsg2 = response3.getBody();
	        // Return the ModelAndView with the name of the HTML view
	        return "redirect:/companyhome";
	   
	    }
	

	@PostMapping("/addSelectedCandidates")
	public ModelAndView addSelectedCandidates(@ModelAttribute("selectedCandidates") SelectedCandidates selectedCandidates, HttpSession session)
	{
	    // Retrieve the company object from the session
	    Company company = (Company) session.getAttribute("company");
	   
	    RestTemplate restTemplate = new RestTemplate();
	    
	    HttpHeaders headers = new HttpHeaders();
	    HttpEntity<SelectedCandidates> requestEntity = new HttpEntity<>(selectedCandidates, headers);
	    
	    restTemplate.exchange("http://localhost:8091/AspireHub/api/v1/addCandidates", HttpMethod.POST, requestEntity, Void.class);
	    
	    // Redirect to a success page or return a ModelAndView with appropriate view and model attributes
	    return new ModelAndView("redirect:/companyhome");
	}
	

}
