package com.example.entity;

public class Questions {
	private int questionId;
	
	private Test test;

	private Vacancies vacancies;
	
	private String question;
	private String option1;
	private String option2;
	private String option3;
	private String option4;
	private String correct;
	private String score;
	
	
	
	public Test getTest() {
		return test;
	}
	public void setTest(Test test) {
		this.test = test;
	}
	
	public Vacancies getVacancies() {
		return vacancies;
	}
	public void setVacancies(Vacancies vacancies) {
		this.vacancies = vacancies;
	}
	
	
	public int getQuestionId() {
		return questionId;
	}
	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}
	
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public String getOption1() {
		return option1;
	}
	public void setOption1(String option1) {
		this.option1 = option1;
	}
	public String getOption2() {
		return option2;
	}
	public void setOption2(String option2) {
		this.option2 = option2;
	}
	public String getOption3() {
		return option3;
	}
	public void setOption3(String option3) {
		this.option3 = option3;
	}
	public String getOption4() {
		return option4;
	}
	public void setOption4(String option4) {
		this.option4 = option4;
	}
	public String getCorrect() {
		return correct;
	}
	public void setCorrect(String correct) {
		this.correct = correct;
	}
	
	
	
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	@Override
	public String toString() {
		return "Questions [questionId=" + questionId + ", test=" + test + ", vacancies=" + vacancies + ", question="
				+ question + ", option1=" + option1 + ", option2=" + option2 + ", option3=" + option3 + ", option4="
				+ option4 + ", correct=" + correct + ", score=" + score + "]";
	}
	
	
	

}
