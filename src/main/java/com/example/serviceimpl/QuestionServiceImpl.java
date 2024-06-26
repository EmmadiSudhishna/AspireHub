package com.example.serviceimpl;

import com.example.entity.Questions; // Correct import
import com.example.exception.ResourceNotFoundException;
import com.example.repository.QuestionRepository;
import com.example.service.QuestionService;

import org.aspectj.weaver.patterns.TypePatternQuestions.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuestionServiceImpl implements QuestionService {

    //private final QuestionRepository questionRepository;

    @Autowired
    QuestionRepository questionRepository;
	@Override
	public Questions addQuestion(Questions question) {
		// TODO Auto-generated method stub
		return questionRepository.save(question);
	}

	@Override
	public List<Questions> getAllQuestions() {
		// TODO Auto-generated method stub
		return questionRepository.findAll();
	}
	@Override
	public boolean isQuestionExist(int questionId) {
		// TODO Auto-generated method stub
		return questionRepository.existsById(questionId);
	}
	@Override
	public Questions getQuestionById(int questionId) {
		// TODO Auto-generated method stub
		Optional<Questions> question = questionRepository.findById((int) questionId);
        if (question.isPresent()) {
            return question.get();
        } else {
            throw new ResourceNotFoundException("Question", "QuestionId", questionId);
        }

		
	}
	@Override
	public boolean updateQuestion(Questions question) {
		 int questionId = question.getQuestionId();
	        if ( isQuestionExist(questionId)) {
	            // Reattach the entity to the persistence context
	            Questions existingQuestion = getQuestionById(questionId);
	            existingQuestion.setQuestion(question.getQuestion());
	            existingQuestion.setOption1(question.getOption1());
	            existingQuestion.setOption2(question.getOption2());
	            existingQuestion.setOption3(question.getOption3());
	            existingQuestion.setOption4(question.getOption4());
	            existingQuestion.setCorrect(question.getCorrect());
	            existingQuestion.setScore(question.getScore());
	            
	            // Now save the updated entity
	            questionRepository.save(existingQuestion);
	            return true;
	        }
	        return false;

	}

	@Override
	public boolean deleteQuestion(int questionId) {
		// TODO Auto-generated method stub
		 if (isQuestionExist(questionId)) {
	            questionRepository.deleteById((int) questionId);
	            return true;
	        }
	        return false;

		
	}

	@Override
	public List<Questions> getQuestionsByTestId(int testId) {
		return questionRepository.findByTestId(testId);
	}

	@Override
	public List<Questions> getQuestionsByVacancyId(int vacancyId) {
		return questionRepository.findByVacancyId(vacancyId);
	}

	@Override
	public List<Questions> getQuestionsByCompanyId(int companyId) {
		return questionRepository.findByCompanyId(companyId);
	}

	

   
}
