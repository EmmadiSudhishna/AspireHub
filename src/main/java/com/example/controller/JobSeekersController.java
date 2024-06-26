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

import com.example.entity.Company;
import com.example.entity.JobSeekers;

import com.example.service.JobSeekersServcie;

@RestController
@RequestMapping("/api/v1")
public class JobSeekersController {
	
	//private final JobSeekersServcie jobSeekersService;

	 @Autowired
	   JobSeekersServcie jobSeekersServcie;
	 
	
	 @PostMapping("/addJobSeekers")
	    public ResponseEntity<Object> addJobSeeker(@RequestBody JobSeekers jobSeekers) {
	        jobSeekersServcie.addJobSeekers(jobSeekers);
	        

			

	        // Return the response
	        return new ResponseEntity<>("Job seeker added successfully", HttpStatus.CREATED);
		}
	        
	       
	 
	 @PostMapping(value="/jobseekersLogin")
		public ResponseEntity<Object> jobseekersLogin(@RequestBody JobSeekers jobseekers){
			JobSeekers jobseekers1 = jobSeekersServcie.loginValidate(jobseekers);
			return new ResponseEntity<>(jobseekers1,HttpStatus.OK);
		}

	    @GetMapping("/allJobSeekers")
	    public ResponseEntity<Object> getAllJobSeekers() {
	        List<JobSeekers> jobSeekers = jobSeekersServcie.getAllJobSeekers();
	        ResponseEntity<Object> entity=new ResponseEntity<>(jobSeekers, HttpStatus.OK);
	        return entity;
	    }

	    @GetMapping("/getJobSeekers/{userId}")
	    public ResponseEntity<Object> getJobseeker(@PathVariable("userId") int userId) {
	        JobSeekers jobseeker = jobSeekersServcie.getJobSeekersById(userId);
	        if (jobseeker != null) {
	            return new ResponseEntity<>(jobseeker, HttpStatus.OK);
	        } else {
	            return new ResponseEntity<>("Jobseeker not found", HttpStatus.NOT_FOUND);
	        }
	    }

	    @DeleteMapping("/deleteJobSeekers/{userId}")
	    public ResponseEntity<Object> deleteJobseeker(@PathVariable("userId") int userId) {
	        boolean deleted = jobSeekersServcie.deleteJobSeekers(userId);
	        if (deleted) {
	            return new ResponseEntity<>("Jobseeker deleted successfully", HttpStatus.OK);
	        } else {
	            return new ResponseEntity<>("Jobseeker not found", HttpStatus.NOT_FOUND);
	        }
	    }
	    
	    @PutMapping(value = "/updateJobSeekers/{userId}")
	    public ResponseEntity<Object> updateJobseeker(@PathVariable("userId") int userId, @RequestBody JobSeekers jobseeker) {
	        boolean updated = jobSeekersServcie.updateJobSeekers(jobseeker);
	        if (updated) {
	            return new ResponseEntity<>("Jobseeker updated successfully", HttpStatus.OK);
	        } else {
	            return new ResponseEntity<>("Jobseeker not found", HttpStatus.NOT_FOUND);
	        }
	    }


}
