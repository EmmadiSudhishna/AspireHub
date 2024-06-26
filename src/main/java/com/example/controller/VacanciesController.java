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

import com.example.entity.Company;
import com.example.entity.Vacancies;
import com.example.service.VacanciesService;

@RestController
@RequestMapping("api/v1")
public class VacanciesController {
	@Autowired
	DataSource dataSource;
	@Autowired
	private final VacanciesService vacanciesService;
	
	 public VacanciesController(VacanciesService vacanciesService) {
	        this.vacanciesService = vacanciesService;
	    }
	
	 @PostMapping("/addVacancy")
	 public ResponseEntity<Object> addVacancies(@RequestBody Vacancies vacancies) {
	     vacanciesService.addVacancies(vacancies); // Call the method on the service instance
	     return new ResponseEntity<>("Vacancy added Successfully", HttpStatus.CREATED);
	 }

	@GetMapping("/allVacancies")
	public ResponseEntity<Object> getAllVacancies(){
		List<Vacancies> vacancies=vacanciesService.getAllVacancies();
		ResponseEntity<Object> entity=new ResponseEntity<>(vacancies, HttpStatus.OK);
		return entity;
		
	}
	
	@GetMapping("/getVacanciesByCompanyId/{companyId}")
    public List<Vacancies> getVacanciesByCompanyId(@PathVariable("companyId") int companyId) {
        return vacanciesService.getVacanciesByCompanyId(companyId);
 }
	
	@GetMapping("/getVacancies/{vacancyId}")
	public ResponseEntity<Object> getVacancies(@PathVariable("vacancyId")int vacancyId){
		Vacancies vacancies;
		if(vacanciesService.isVacanciesExist(vacancyId)) {
			vacancies =vacanciesService.getVacanciesById(vacancyId);
		}else {
			vacancies=null;
		}
		ResponseEntity<Object>entity=new ResponseEntity<>(vacancies, HttpStatus.OK);
		return entity;
		
	}
	
	
	
	
	 @DeleteMapping(value = "/deleteVacancies/{vacancyId}")
	    public ResponseEntity<Object> deleteVacancies(@PathVariable("vacancyId") int vacancyId) {
	        boolean deleted = vacanciesService.deleteVacancies(vacancyId);
	        if (deleted) {
	            return new ResponseEntity<>("Vacancy deleted successfully", HttpStatus.OK);
	        } else {
	            return new ResponseEntity<>("Vacancy not found", HttpStatus.NOT_FOUND);
	        }
	    }

	    @PutMapping(value = "/updateVacancies/{vacancyId}")
	    public ResponseEntity<Object> updateVacancies(@PathVariable("vacancyId") int vacancyId, @RequestBody Vacancies vacancy) {
	        boolean updated = vacanciesService.updateVacancies(vacancy);
	        if (updated) {
	            return new ResponseEntity<>("Vacancy updated successfully", HttpStatus.OK);
	        } else {
	            return new ResponseEntity<>("Vacancy not found", HttpStatus.NOT_FOUND);
	        }
	    }

}
