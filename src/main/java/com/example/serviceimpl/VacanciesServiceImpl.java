package com.example.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Vacancies;
import com.example.exception.ResourceNotFoundException;
import com.example.repository.VacanciesRepository;
import com.example.service.VacanciesService;

@Service
public class VacanciesServiceImpl implements VacanciesService {

	//private final VacanciesRepository vacanciesRepository;
	
	@Autowired
	VacanciesRepository vacanciesRepository;
	@Override
	public Vacancies addVacancies(Vacancies vacancies) {
		// TODO Auto-generated method stub
		return vacanciesRepository.save(vacancies);
	}
	@Override
	public List<Vacancies> getAllVacancies() {
		// TODO Auto-generated method stub
		return vacanciesRepository.findAll();
	}
	@Override
	public boolean isVacanciesExist(int vacancyId) {
		// TODO Auto-generated method stub
		return vacanciesRepository.existsById(vacancyId);
	}
	@Override
	public Vacancies getVacanciesById(int vacancyId) {
	    // Search for the Vacancies by ID
	    Optional<Vacancies> vacanciesOptional = vacanciesRepository.findById(vacancyId);

	    // Check if Vacancies exists
	    if (vacanciesOptional.isPresent()) {
	        // If exists, return it
	        return vacanciesOptional.get();
	    } else {
	        // If not, throw ResourceNotFoundException
	        throw new ResourceNotFoundException("Vacancies", "VacancyId", vacancyId);
	    }
	}
	
	@Override
	public List<Vacancies> getVacanciesByCompanyId(int companyId) {
		// TODO Auto-generated method stub
		return vacanciesRepository.findByCompanyId(companyId);
	}


	@Override
	public boolean deleteVacancies(int vacancyId) {
		// TODO Auto-generated method stub
		if(vacanciesRepository.existsById(vacancyId)) {
			vacanciesRepository.deleteById(vacancyId);
			return true;
		}
		return false;
	}
	@Override
	public boolean updateVacancies(Vacancies vacancy) {
		// TODO Auto-generated method stub
		if (isVacanciesExist(vacancy.getVacancyId())) {
            vacanciesRepository.save(vacancy);
            return true;
        }

		
		
		return false;
	}

}
