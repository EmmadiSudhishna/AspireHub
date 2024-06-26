package com.example.controller;

import java.util.List;
import java.util.Optional;

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

import com.example.entity.TestResult;
import com.example.service.TestResultService;

@RestController
@RequestMapping("/api/v1")
public class TestResultController {

    private final TestResultService testResultService;

    @Autowired
    public TestResultController(TestResultService testResultService) {
        this.testResultService = testResultService;
    }

    @PostMapping(value = "/addTestResult")
    public ResponseEntity<Object> addTestResult(@RequestBody TestResult testResult) {
        testResultService.addTestResult(testResult);
        return new ResponseEntity<>("Test result added successfully", HttpStatus.CREATED);
    }
    @GetMapping("/allTestResults")
    public ResponseEntity<Object> getAllTestResults() {
        List<TestResult> testResults = testResultService.getAllTestResults();
        ResponseEntity<Object> entity=new ResponseEntity<>(testResultService, HttpStatus.OK);
        return entity;
        
    }

    @GetMapping(value = "/getTestResult/{resultId}")
    public ResponseEntity<Object> getTestResult(@PathVariable("resultId") int resultId) {
        TestResult testResult = testResultService.getTestResultById(resultId);
        if (testResult != null) {
            return new ResponseEntity<>(testResult, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Test result not found", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value = "/deleteTestResult/{resultId}")
    public ResponseEntity<Object> deleteTestResult(@PathVariable("resultId") int resultId) {
        boolean deleted = testResultService.deleteTestResult(resultId);
        if (deleted) {
            return new ResponseEntity<>("Test result deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Test result not found", HttpStatus.NOT_FOUND);
        }
    }
    
    @PutMapping(value = "/updateTestResult/{resultId}")
    public ResponseEntity<Object> updateTestResult(@PathVariable("resultId") int resultId, @RequestBody TestResult testResult) {
        boolean updated = testResultService.updateTestResult(testResult);
        if (updated) {
            return new ResponseEntity<>("Test result updated successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Test result not found", HttpStatus.NOT_FOUND);
        }
    }
    
    @GetMapping("/findTestResultsByUserId/{userId}")
    public List<TestResult> findTestResultsByUserId(@PathVariable("userId") int userId)
    {
        return testResultService.findTestResultsByUserId(userId);
    }


}