package com.example.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.SelectedCandidates;
import com.example.service.SelectedCandidatesService;

@RestController
@RequestMapping("/api/v1")
public class SelectedCandidatesController {

    private final SelectedCandidatesService selectedCandidatesService;

    @Autowired
    public SelectedCandidatesController(SelectedCandidatesService selectedCandidatesService) {
        this.selectedCandidatesService = selectedCandidatesService;
    }

    @PostMapping("/addCandidates")
    public ResponseEntity<Object> addSelectedCandidate(@RequestBody SelectedCandidates selectedCandidates) {
        selectedCandidatesService.addSelectedCandidate(selectedCandidates);
        return new ResponseEntity<>("Selected candidate added successfully", HttpStatus.CREATED);
    }

    @GetMapping("/allCandidates")
    public ResponseEntity<Object> getAllSelectedCandidates() {
        List<SelectedCandidates> selectedCandidates = selectedCandidatesService.getAllSelectedCandidates();
        ResponseEntity<Object> entity=new ResponseEntity<>(selectedCandidatesService, HttpStatus.OK);
        return entity;
    }
    
    @GetMapping("/getAllCandidates")
    public ResponseEntity<List<SelectedCandidates>> getAllSelectedCandidate() {
        List<SelectedCandidates> selectedCandidates = selectedCandidatesService.getAllSelectedCandidates();
        return new ResponseEntity<>(selectedCandidates, HttpStatus.OK);
    }

    @GetMapping(value = "/getSelectedCandidate/{id}")
	public ResponseEntity<Object> getSelectedCandidate(@PathVariable("id") int id)
	{
		SelectedCandidates selectedCandidate = selectedCandidatesService.getSelectedCandidateById(id);
		if (selectedCandidate != null) {
			return new ResponseEntity<>(selectedCandidate, HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Selected candidate not found", HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping(value = "/deleteSelectedCandidate/{id}")
	public ResponseEntity<Object> deleteSelectedCandidate(@PathVariable("id") int id)
	{
		boolean deleted = selectedCandidatesService.deleteSelectedCandidate(id);
		if (deleted) {
			return new ResponseEntity<>("Selected candidate deleted successfully", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Selected candidate not found", HttpStatus.NOT_FOUND);
		}
	}
}
