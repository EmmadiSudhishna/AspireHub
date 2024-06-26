package com.example.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.entity.Company;
import com.example.entity.Vacancies;

@Service
public interface VacanciesService {
	Vacancies addVacancies(Vacancies vacancies);

	List<Vacancies> getAllVacancies();
	
	boolean isVacanciesExist(int vacancyId);

	Vacancies getVacanciesById(int vacancyId);

	boolean updateVacancies(Vacancies vacancy);
	boolean deleteVacancies(int vacancyId);
	
	List<Vacancies> getVacanciesByCompanyId(int companyId);
}
