package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.entity.JobSeekers;

@Service
public interface JobSeekersServcie {
	
	JobSeekers addJobSeekers(JobSeekers jobSeekers);
	
	List<JobSeekers> getAllJobSeekers();
	
	boolean  isJobSeekersExist(int userId);
	JobSeekers getJobSeekersById(int userId);
	
	boolean updateJobSeekers(JobSeekers jobSeekers);
	boolean deleteJobSeekers(int userId);
	
	public JobSeekers loginValidate(JobSeekers jobseekers);
}
