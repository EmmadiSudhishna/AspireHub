package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.entity.TestResult;

@Repository
public interface TestResultRepository extends JpaRepository<TestResult, Integer> {
	@Query("SELECT tr FROM TestResult tr JOIN tr.jobseekers js WHERE js.userId = :userId")
	List<TestResult> findTestResultsByUserId(int userId); 

	
}
