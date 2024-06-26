package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.entity.JobApply;

import jakarta.transaction.Transactional;


@Repository
public interface JobApplyRepository extends JpaRepository<JobApply, Integer> {
	
	List<JobApply> findAllByJobseekersUserId(int userId);
	/*@Query("SELECT ja FROM JobApply ja JOIN ja.vacancy v JOIN v.company c WHERE c.companyId = :companyId")
	List<JobApply> findJobApplyByCompanyId(int companyId);*/
	
	@Modifying
	@Transactional
	@Query("UPDATE JobApply j SET j.status = :status, j.finalScore = :finalScore WHERE j.jobseekers.userId = :userId AND j.vacancies.vacancyId = :vacancyId")
	void updateStatusAndFinalScoreByUserIdAndVacancyId(@Param("userId") int userId, @Param("vacancyId") int vacancyId,
			@Param("status") String status, @Param("finalScore") String finalScore);
	
	
	@Query("SELECT ja FROM JobApply ja JOIN ja.vacancies v JOIN v.company c WHERE c.companyId = :companyId")
	List<JobApply> findJobApplyByCompanyId(int companyId);


}