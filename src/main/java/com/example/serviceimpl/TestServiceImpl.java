package com.example.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Test;
import com.example.repository.TestRepository;
import com.example.service.TestService;

@Service
public class TestServiceImpl implements TestService {
	
	//private final TestRepository testRepository;
	
	@Autowired
	
	TestRepository testRepository;
	
	@Override
	public Test addTest(Test test) {
		// TODO Auto-generated method stub
		return testRepository.save(test);
	}

	@Override
	public List<Test> getAllTests() {
		// TODO Auto-generated method stub
		return testRepository.findAll();
	}

	
	@Override
	public boolean isTestExist(int testId) {
		// TODO Auto-generated method stub
		return testRepository.existsById(testId);
	}
	@Override
	public Test getTestById(int testId) {
		Optional<Test> optionalTest = testRepository.findById(testId);
		return optionalTest.orElse(null); // Return null if the test is not found
	}

	@Override
	public Boolean deleteTest(int testId) {
		// TODO Auto-generated method stub
		testRepository.deleteById(testId);
		return false;
	}
	 @Override
	    public boolean updateTest(Test test) {
	       
	        return testRepository.save(test) != null;
	    }

	@Override
	public List<Test> getTestsByVacancyIdIn(List<Integer> vacancyIds) {
		// TODO Auto-generated method stub
		 return testRepository.findByVacancies_VacancyIdIn(vacancyIds);

	}
	
	@Override
	public Test findTestByVacancyId(int vacancyId)
	{
		return testRepository.findTestByVacancyId(vacancyId);
		
	}
}
