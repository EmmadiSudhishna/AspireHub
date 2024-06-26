package com.example.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.TestResult;
import com.example.exception.ResourceNotFoundException;
import com.example.repository.TestResultRepository;
import com.example.service.TestResultService;

@Service
public class TestResultServiceImpl implements TestResultService {
	// private final TestResultRepository testResultRepository;

	    @Autowired
	    TestResultRepository testResultRepository;
	
	@Override
	public TestResult addTestResult(TestResult testResult) {
		// TODO Auto-generated method stub
		return testResultRepository.save(testResult);
	}

	@Override
	public List<TestResult> getAllTestResults() {
		// TODO Auto-generated method stub
		return testResultRepository.findAll();
	}

	

	@Override
	public boolean deleteTestResult(int resultId) {
		// TODO Auto-generated method stub
		 if (isTestResultExist(resultId)) {
	            testResultRepository.deleteById((int) resultId);
	            return true;
	        }
	        return false;

	}

	@Override
	public boolean isTestResultExist(int resultId) {
		// TODO Auto-generated method stub
		return testResultRepository.existsById(resultId);
	}

	@Override
	public boolean updateTestResult(TestResult testResult) {
		// TODO Auto-generated method stub
		if (isTestResultExist(testResult.getResultId())) {
            testResultRepository.save(testResult);
            return true;
        }
        return false;
	}

	@Override
	public TestResult getTestResultById(int resultId) {
		// TODO Auto-generated method stub
		 Optional<TestResult> testResult = testResultRepository.findById((int) resultId);
	        if (testResult.isPresent()) {
	            return testResult.get();
	        } else {
	            throw new ResourceNotFoundException("TestResult", "ResultId", resultId);
	        }
	}

	    	@Override
	    	public List<TestResult> findTestResultsByUserId(int userId) {
	    		
	    		return testResultRepository.findTestResultsByUserId(userId);
	    	}

	}


