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

import com.example.entity.JobApply;
import com.example.service.JobApplyService;

@RestController
@RequestMapping("/api/v1")
public class JobApplyController {

    private final JobApplyService jobApplyService;

    @Autowired
    public JobApplyController(JobApplyService jobApplyService) {
        this.jobApplyService = jobApplyService;
    }

    @PostMapping("/applyJob")
    public ResponseEntity<JobApply> applyJob(@RequestBody JobApply jobApply) {
        JobApply appliedJob = jobApplyService.applyJob(jobApply);
        return new ResponseEntity<>(appliedJob, HttpStatus.CREATED);
    }

    @GetMapping("/allJobs")
    public ResponseEntity<Object> getAllApplications() {
        List<JobApply> applications = jobApplyService.getAllApplications();
        ResponseEntity<Object> entity=new ResponseEntity<>(jobApplyService, HttpStatus.OK);
        return entity;
    }
    
    
   

    @GetMapping("/getApplication/{applyId}")
    public ResponseEntity<JobApply> getApplicationById(@PathVariable int applyId) {
        Optional<JobApply> application = jobApplyService.getApplicationById(applyId);
        return application.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/deleteApplication/{applyId}")
    public ResponseEntity<Void> deleteApplication(@PathVariable int applyId) {
        jobApplyService.deleteApplication(applyId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
    
    @GetMapping(value = "/allJobApplications")
    public ResponseEntity<Object> getAllJobApplications() {
        return new ResponseEntity<>(jobApplyService.getAllApplications(), HttpStatus.OK);
    }
    
    @GetMapping(value = "/allJobApplications/{userId}")
    public ResponseEntity<Object> getAllJobApplicationsByUserId(@PathVariable int userId) {
        List<JobApply> jobApplications = jobApplyService.getAllJobApplicationsByUserId(userId);
        if (jobApplications != null && !jobApplications.isEmpty()) {
            return new ResponseEntity<>(jobApplications, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Job applications not found for user ID: " + userId, HttpStatus.NOT_FOUND);
        }
    }
    
    @PutMapping("/updateStatus/{userId}/{vacancyId}/{status}/{finalScore}")
	public ResponseEntity<String> updateStatusByUserId(@PathVariable("userId") int userId,
			@PathVariable("vacancyId") int vacancyId, @PathVariable("status") String status,
			@PathVariable("finalScore") String finalScore) {
		jobApplyService.updateStatusAndFinalScoreByUserIdAndVacancyId(userId, vacancyId, status, finalScore);

		return ResponseEntity.ok("Status updated Succesfully");
	}
    
    @GetMapping("/findJobApplyByCompanyId/{companyId}")
    public List<JobApply> findJobApplyByCompanyId(@PathVariable int companyId)
	{
        return jobApplyService.findJobApplyByCompanyId(companyId);
    }
    
    
    
   
}
