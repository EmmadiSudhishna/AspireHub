package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.JobSeekers;

@Repository
public interface JobSeekersRepository extends JpaRepository<JobSeekers, Integer>{
	JobSeekers findByEmailAndPassword(String email, String password);
}
