package com.example.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.JobSeekers;
import com.example.exception.ResourceNotFoundException;
import com.example.repository.JobSeekersRepository;
import com.example.service.JobSeekersServcie;

@Service
public class JobSeekersServiceImpl implements JobSeekersServcie {

   // private final JobSeekersRepository jobSeekerRepository;

    @Autowired
    JobSeekersRepository jobSeekersRepository;

	@Override
	public JobSeekers addJobSeekers(JobSeekers jobSeekers) {
		// TODO Auto-generated method stub
		return jobSeekersRepository.save(jobSeekers) ;
	}

	@Override
	public List<JobSeekers> getAllJobSeekers() {
		// TODO Auto-generated method stub
		return jobSeekersRepository.findAll();
	}

	@Override
	public JobSeekers getJobSeekersById(int userId) {
		// TODO Auto-generated method stub
		 Optional<JobSeekers> jobseeker = jobSeekersRepository.findById((int) userId);
	        if (jobseeker.isPresent()) {
	            return jobseeker.get();
	        } else {
	            throw new ResourceNotFoundException("Jobseeker", "UserId", userId);
	        }

	}

	@Override
	public boolean deleteJobSeekers(int userId) {
		// TODO Auto-generated method stub
		if (isJobSeekersExist(userId)) {
            jobSeekersRepository.deleteById((int) userId);
            return true;
        }
        return false;

		
	}

	@Override
	public boolean isJobSeekersExist(int userId) {
		// TODO Auto-generated method stub
		return jobSeekersRepository.existsById(userId);

	}

	@Override
	public boolean updateJobSeekers(JobSeekers jobSeekers) {
		// TODO Auto-generated method stub
		if (isJobSeekersExist(jobSeekers.getUserId())) {
            jobSeekersRepository.save(jobSeekers);
            return true;
        }
        return false;

		
	}

	@Override
	public JobSeekers loginValidate(JobSeekers jobseekers) {
		// TODO Auto-generated method stub
		JobSeekers jobseekers1=jobSeekersRepository.findByEmailAndPassword(jobseekers.getEmail(), jobseekers.getPassword());
		System.out.println("what is there in Jobseekers=" + jobseekers1);
        return jobseekers1;
	}
	
}
