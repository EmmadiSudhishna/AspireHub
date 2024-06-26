package com.example.entity;

public class TestResult {
	private int resultId;
	private int userId;
	private int vacancyId;
	private int questionId;
	private String selectedOption;
	private String result;
	
	private JobSeekers jobseekers;
	 private Questions question;

	private Vacancies vacancies;
	public Vacancies getVacancies() {
		return vacancies;
	}
	public void setVacancies(Vacancies vacancies) {
		this.vacancies = vacancies;
	}
	
	public Questions getQuestion() {
		return question;
	}
	public void setQuestion(Questions question) {
		this.question = question;
	}
	public JobSeekers getJobseekers() {
		return jobseekers;
	}
	public void setJobseekers(JobSeekers jobseekers) {
		this.jobseekers = jobseekers;
	}
	private String score;
	public int getResultId() {
		return resultId;
	}
	public void setResultId(int resultId) {
		this.resultId = resultId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getVacancyId() {
		return vacancyId;
	}
	public void setVacancyId(int vacancyId) {
		this.vacancyId = vacancyId;
	}
	public int getQuestionId() {
		return questionId;
	}
	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}
	public String getSelectedOption() {
		return selectedOption;
	}
	public void setSelectedOption(String selectedOption) {
		this.selectedOption = selectedOption;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	
	
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	@Override
	public String toString() {
		return "TestResult [resultId=" + resultId + ", userId=" + userId + ", vacancyId=" + vacancyId + ", questionId="
				+ questionId + ", selectedOption=" + selectedOption + ", result=" + result + ", jobseekers="
				+ jobseekers + ", question=" + question + ", vacancies=" + vacancies + ", score=" + score + "]";
	}
	
	
	
	
	
	

}
