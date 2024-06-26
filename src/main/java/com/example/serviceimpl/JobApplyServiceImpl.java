package com.example.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.entity.JobApply;
import com.example.repository.JobApplyRepository;
import com.example.service.JobApplyService;

@Service
public class JobApplyServiceImpl implements JobApplyService {

	
	private final JobApplyRepository jobApplyRepository;
	
	@Autowired
    public JobApplyServiceImpl(JobApplyRepository jobApplyRepository) {
        this.jobApplyRepository = jobApplyRepository;
    }

	@Override
	public JobApply applyJob(JobApply jobApply) {
		// TODO Auto-generated method stub
		return jobApplyRepository.save(jobApply) ;
	}

	@Override
	public List<JobApply> getAllApplications() {
		// TODO Auto-generated method stub
		return jobApplyRepository.findAll();
	}

	@Override
	public Optional<JobApply> getApplicationById(int applyId) {
		// TODO Auto-generated method stub
		return jobApplyRepository.findById(applyId);
	}

	@Override
	public void deleteApplication(int applyId) {
		// TODO Auto-generated method stub
		jobApplyRepository.deleteById(applyId);
		
	}

	@Override
	public List<JobApply> getAllJobApplicationsByUserId(int userId) {
		return jobApplyRepository.findAllByJobseekersUserId(userId);
	}

	@Override
	@Transactional
	public void updateStatusAndFinalScoreByUserIdAndVacancyId(int userId, int vacancyId, String status,String finalScore)
	{

		jobApplyRepository.updateStatusAndFinalScoreByUserIdAndVacancyId(userId, vacancyId, status, finalScore);

	}
	
	@Override
	public List<JobApply> findJobApplyByCompanyId(int companyId)
	{
		return jobApplyRepository.findJobApplyByCompanyId(companyId);
	}
	
	
}
