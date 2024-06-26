package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.query.Param;

import com.example.entity.JobApply;

public interface JobApplyService {

	 JobApply applyJob(JobApply jobApply);
	    
	    List<JobApply> getAllApplications();
	    
	    Optional<JobApply> getApplicationById(int applyId);
	    
	    void deleteApplication(int applyId);
	    
	    List<JobApply> getAllJobApplicationsByUserId(int userId);
	    
	   // List<JobApply>findJobApplyByCompanyId(int companyId);
	    
	    void updateStatusAndFinalScoreByUserIdAndVacancyId(@Param("userId") int userId, @Param("vacancyId") int vacancyId,
				@Param("status") String status, @Param("finalScore") String finalScore);
	
	    List<JobApply>findJobApplyByCompanyId(int companyId);
}
