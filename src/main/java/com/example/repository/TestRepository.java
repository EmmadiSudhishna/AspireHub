package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.entity.Test;

@Repository
public interface TestRepository extends JpaRepository<Test, Integer> {

	List<Test> findByVacancies_VacancyId(Integer vacancyId);

	List<Test> findByVacancies_VacancyIdIn(List<Integer> vacancyIds);


	@Query("SELECT t FROM Test t WHERE t.vacancies.vacancyId = :vacancyId")
    Test findTestByVacancyId(int vacancyId);

}
