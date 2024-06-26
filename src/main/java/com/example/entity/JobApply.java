package com.example.entity;



import lombok.Data;

public class JobApply {
	private int applyId;
	private String applyDate;
	private JobSeekers jobseekers;
	private Vacancies vacancies;
	private String status;
	 private String finalScore;
	 
	 
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getFinalScore() {
		return finalScore;
	}
	public void setFinalScore(String finalScore) {
		this.finalScore = finalScore;
	}
	public int getApplyId() {
		return applyId;
	}
	public void setApplyId(int applyId) {
		this.applyId = applyId;
	}
	public String getApplyDate() {
		return applyDate;
	}
	public void setApplyDate(String applyDate) {
		this.applyDate = applyDate;
	}
	public JobSeekers getJobseekers() {
		return jobseekers;
	}
	public void setJobseekers(JobSeekers jobseekers) {
		this.jobseekers = jobseekers;
	}
	public Vacancies getVacancies() {
		return vacancies;
	}
	public void setVacancies(Vacancies vacancies) {
		this.vacancies = vacancies;
	}
	@Override
	public String toString() {
		return "JobApply [applyId=" + applyId + ", applyDate=" + applyDate + ", jobseekers=" + jobseekers
				+ ", vacancies=" + vacancies + ", status=" + status + ", finalScore=" + finalScore + "]";
	}
	
	
	
	

}
