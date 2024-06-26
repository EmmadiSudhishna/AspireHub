package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.entity.Questions;

@Repository
public interface QuestionRepository extends JpaRepository<Questions, Integer> {

	
	 @Query("SELECT q FROM Questions q WHERE q.test.testId = :testId")
	    List<Questions> findByTestId(@Param("testId") int testId);
		
		 @Query("SELECT q FROM Questions q JOIN q.vacancies v WHERE v.company.companyId = :companyId")
		    List<Questions> findByCompanyId(@Param("companyId") int companyId);
		 
		 @Query("SELECT q FROM Questions q WHERE q.vacancies.vacancyId = :vacancyId")
		    List<Questions> findByVacancyId(@Param("vacancyId") int vacancyId);


}
