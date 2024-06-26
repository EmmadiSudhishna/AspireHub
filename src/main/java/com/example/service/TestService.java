package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.entity.Test;
import com.example.entity.Vacancies;

@Service
public interface TestService {
	Test addTest(Test test);
	
	List<Test> getAllTests();
	
	boolean isTestExist(int testId);
	
	Test getTestById(int testId);
	Boolean deleteTest(int testId);
	boolean updateTest(Test test);

	List<Test> getTestsByVacancyIdIn(List<Integer> vacancyIds);

	Test findTestByVacancyId(int vacancyId);
	
	//Vacancies getVacancyByTestId(int testId); // New method to retrieve vacancy information for a given test
	
	
	 //Test updateTest(Test test);
	    
	    // Get tests by vacancyId
	    //List<Test> getTestsByVacancyId(Integer vacancyId);

		

		
	    
	    
	
}
