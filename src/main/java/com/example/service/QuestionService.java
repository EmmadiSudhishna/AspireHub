package com.example.service;

import java.util.List;
import java.util.Optional;

import org.aspectj.weaver.patterns.TypePatternQuestions.Question;
import org.springframework.stereotype.Service;

import com.example.entity.Questions;

@Service
public interface QuestionService {
	Questions addQuestion(Questions question);
	
	List<Questions> getAllQuestions();
	
	boolean isQuestionExist(int questionId);
    Questions getQuestionById(int questionId);
    boolean updateQuestion(Questions question);
    boolean deleteQuestion(int questionId);

    List<Questions> getQuestionsByTestId(int testId);
    List<Questions> getQuestionsByVacancyId(int vacancyId);
    List<Questions> getQuestionsByCompanyId(int companyId);
	//List<Questions> getQuestionsByTestId(int testId);
}
