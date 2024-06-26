package com.example.service;

import java.util.List;
import java.util.Optional;

import com.example.entity.SelectedCandidates;

public interface SelectedCandidatesService {

	void addSelectedCandidate(SelectedCandidates selectedCandidates);
    List<SelectedCandidates> getAllSelectedCandidates();
    //Optional<SelectedCandidates> getSelectedCandidateById(int id);
    boolean isSelectedCandidateExist(int id);

	SelectedCandidates getSelectedCandidateById(int id);

	boolean updateSelectedCandidate(SelectedCandidates selectedCandidate);
    boolean deleteSelectedCandidate(int id);
}
