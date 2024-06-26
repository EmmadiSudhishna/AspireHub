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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.example.entity.Company;
import com.example.entity.Questions;
import com.example.entity.Test;
import com.example.entity.Vacancies;

import jakarta.servlet.http.HttpSession;

@Controller
public class QuestionController {

	@GetMapping("/addQuestion")
	public ModelAndView addQuestion(@RequestParam("testId") int testId, @RequestParam("vacancyId") int vacancyId,
			Model model, HttpSession session) {
		System.out.println("testid="+testId);
		Questions question = new Questions();
		

		Test test = new Test();
		test.setTestId(testId);
		//model.addAttribute("testId", testId);

		Vacancies vacancy = new Vacancies();
		vacancy.setVacancyId(vacancyId);
		//model.addAttribute("vacancyId", vacancyId);

		question.setTest(test);
		question.setVacancies(vacancy);
		
		
		model.addAttribute("question1", question);
		ModelAndView view = new ModelAndView("Company/addQuestion");
		return view;
	}

	@PostMapping("/saveQuestion")
	public String saveQuestion(@ModelAttribute("question") Questions question, @RequestParam("testId") int testId, @RequestParam("vacancyId") int vacancyId,Model model) {
		
		Test test = new Test();
		test.setTestId(testId);
		//model.addAttribute("testId", testId);

		Vacancies vacancy = new Vacancies();
		vacancy.setVacancyId(vacancyId);
		//model.addAttribute("vacancyId", vacancyId);

		question.setTest(test);
		question.setVacancies(vacancy);
		
		System.out.println("question==" + question);
		

		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<Questions> entity = new HttpEntity<>(question, headers);
		RestTemplate restTemplate = new RestTemplate();
		String url = "http://localhost:8091/AspireHub/api/v1/addQuestion";

		ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
		String responseString = responseEntity.getBody();
		model.addAttribute("responseString", responseString);

		///cleaning questions after submit
		question = new Questions();
		model.addAttribute("question", question);
		
		//ModelAndView view = new ModelAndView("Company/companyhome");
		model.addAttribute("responseString", responseString);

		

		//model.addAttribute("responseString", "addQuestion occurred while saving question.");

		model.addAttribute("successMessage", "Question added successfully!");

		return "redirect:/addQuestion?testId="+testId+"&vacancyId="+vacancyId+"&res=1";
	}
	
	
	@GetMapping("/viewQuestions")
    public String getAllQuestions(Model model, HttpSession session) {
        // Extract companyId from session
		int companyId = ((Company) session.getAttribute("company")).getCompanyId();
       
        // Send companyId along with the request to fetch all questions
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Questions[]> response = restTemplate.getForEntity( "http://localhost:8091/AspireHub/api/v1/getQuestionsByCompanyId?companyId=" + companyId, Questions[].class);
        Questions[] questionsArray = response.getBody();
        // Initialize Test and Vacancies objects for each Questions object
        if (questionsArray != null) {
            for (Questions question : questionsArray) {
                if (question.getTest() == null) {
                    question.setTest(new Test());
                }
                if (question.getVacancies() == null) {
                    question.setVacancies(new Vacancies());
                }
            }
        }
        // Add the list of questions to the model
        model.addAttribute("questions", Arrays.asList(questionsArray));       
        // Return the view
        return "Company/viewQuestion";
    }
	
	
	@GetMapping("/deleteQuestion/{questionId}")
	public String deleteQuestion(@PathVariable("questionId") Integer questionId) {
	    RestTemplate restTemplate = new RestTemplate();
	    restTemplate.delete("http://localhost:8091/AspireHub/api/v1/deleteQuestion/" + questionId);
	    ModelAndView view = new ModelAndView("/Company/viewQuestion");
	    view.addObject("responseMsg", "Question deleted successfully");
	    return "redirect:/viewQuestions";
	}
	
	
	@GetMapping("/updateQuestion/{questionId}")
	public ModelAndView updateQuestion(@PathVariable("questionId") int questionId) {
	    RestTemplate restTemplate = new RestTemplate();
	    ResponseEntity<Questions> response = restTemplate.getForEntity("http://localhost:8091/AspireHub/api/v1/getQuestion/" + questionId, Questions.class);
	    Questions question = response.getBody();
	    
	    ModelAndView view = new ModelAndView("Company/updateQuestions");
	    view.addObject("question", question);
	    return view;
	}
	
	@PostMapping("/updateQuestion")
	public String updateQuestion(@ModelAttribute("questionId") Questions question, Model model) {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<Questions> entity = new HttpEntity<>(question, headers);
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.put("http://localhost:8091/AspireHub/api/v1/updateQuestion/" + question.getQuestionId(), entity);
		ModelAndView view = new ModelAndView("Company/viewQuestion");
		view.addObject("responseMsg", "Question updated successfully");
		return "redirect:/viewQuestions";
	}

	
	
	
	
}