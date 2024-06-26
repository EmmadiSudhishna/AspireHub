package com.example.controller;

import java.util.List;

import org.aspectj.weaver.patterns.TypePatternQuestions.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Questions;
import com.example.service.QuestionService;

@RestController
@RequestMapping("/api/v1")
public class QuestionController {
	private final QuestionService questionService;

    @Autowired
    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }
	
	@PostMapping("/addQuestion")
	public ResponseEntity<Object> addQuestion(@RequestBody Questions questions){
		questionService.addQuestion(questions);
		return new ResponseEntity<>("Questions added Successfully", HttpStatus.CREATED);
	}
	
	@GetMapping("/allQuestions")
    public ResponseEntity<Object> getAllQuestions() {
        List<Questions> questions = questionService.getAllQuestions();
        ResponseEntity<Object> entity=new ResponseEntity<>(questions, HttpStatus.OK);
        return entity;
    }
	
	@GetMapping(value = "/getQuestion/{questionId}")
    public ResponseEntity<Object> getQuestion(@PathVariable("questionId") int questionId) {
        Questions question = questionService.getQuestionById(questionId);
        if (question != null) {
            return new ResponseEntity<>(question, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Question not found", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value = "/deleteQuestion/{questionId}")
    public ResponseEntity<Object> deleteQuestion(@PathVariable("questionId") int questionId) {
        boolean deleted = questionService.deleteQuestion(questionId);
        if (deleted) {
            return new ResponseEntity<>("Question deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Question not found", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(value = "/updateQuestion/{questionId}")
    public ResponseEntity<Object> updateQuestion(@PathVariable("questionId") int questionId, @RequestBody Questions question) {
        boolean updated = questionService.updateQuestion(question);
        if (updated) {
            return new ResponseEntity<>("Question updated successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Question not found", HttpStatus.NOT_FOUND);
        }
    }
    
    
    @GetMapping("/getQuestionsByCompanyId")
    public ResponseEntity<Object> getQuestionsByCompanyId(@RequestParam int companyId) {
        List<Questions> questions = questionService.getQuestionsByCompanyId(companyId);
        return new ResponseEntity<>(questions, HttpStatus.OK);
    }
    
    
    @GetMapping("/allQuestions/{vacancyId}")
    public ResponseEntity<List<Questions>> getAllQuestionsByVacancyId(@PathVariable int vacancyId) {
        List<Questions> questions = questionService.getQuestionsByVacancyId(vacancyId);
        return ResponseEntity.ok(questions);
    }


}
