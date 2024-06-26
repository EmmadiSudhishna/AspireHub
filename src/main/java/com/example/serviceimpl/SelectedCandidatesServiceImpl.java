package com.example.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.SelectedCandidates;
import com.example.exception.ResourceNotFoundException;
import com.example.repository.SelectedCandidatesRepository;
import com.example.service.SelectedCandidatesService;

@Service
public class SelectedCandidatesServiceImpl implements SelectedCandidatesService {

    private final SelectedCandidatesRepository selectedCandidatesRepository;

    @Autowired
    public SelectedCandidatesServiceImpl(SelectedCandidatesRepository selectedCandidatesRepository) {
        this.selectedCandidatesRepository = selectedCandidatesRepository;
    }

	

	@Override
	public List<SelectedCandidates> getAllSelectedCandidates() {
		// TODO Auto-generated method stub
		return selectedCandidatesRepository.findAll();
	}

	public boolean isSelectedCandidateExist(int id) {
		return selectedCandidatesRepository.existsById(id);
	}
	
	@Override
	public SelectedCandidates getSelectedCandidateById(int id) {
		Optional<SelectedCandidates> selectedCandidate = selectedCandidatesRepository.findById(id);
		if (selectedCandidate.isPresent()) {
			return selectedCandidate.get();
		} else {
			throw new ResourceNotFoundException("SelectedCandidate", "Id", id);
		}
	}

	@Override
	public boolean updateSelectedCandidate(SelectedCandidates selectedCandidate) {
		if (isSelectedCandidateExist(selectedCandidate.getId())) {
			selectedCandidatesRepository.save(selectedCandidate);
			return true;
		}
		return false;
	}

	@Override
	public boolean deleteSelectedCandidate(int id) {
		if (isSelectedCandidateExist(id)) {
			selectedCandidatesRepository.deleteById(id);
			return true;
		}
		return false;
	}



	@Override
	public void addSelectedCandidate(SelectedCandidates selectedCandidates) {
		// TODO Auto-generated method stub
		selectedCandidatesRepository.save(selectedCandidates);
		
		
	}
}
