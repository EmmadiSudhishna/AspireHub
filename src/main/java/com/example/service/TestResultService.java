package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.entity.TestResult;

@Service
public interface TestResultService {
	TestResult addTestResult(TestResult testResult);

    List<TestResult> getAllTestResults();

    TestResult getTestResultById(int resultId);
boolean isTestResultExist(int resultId);
    boolean updateTestResult(TestResult testResult);
    boolean deleteTestResult(int resultId);
    
    List <TestResult> findTestResultsByUserId(int userId);
}
