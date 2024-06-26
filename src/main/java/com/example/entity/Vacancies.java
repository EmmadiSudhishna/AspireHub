package com.example.entity;

public class Vacancies {
	private int vacancyId;
	private int companyId;
	private String postDate;
	private String jobTitle;
	private String description;
	private String requirements;
	private String noOfVacancies;
	private String openDate;
	private String closeDate;
	private Company company;
	public int getVacancyId() {
		return vacancyId;
	}
	public void setVacancyId(int vacancyId) {
		this.vacancyId = vacancyId;
	}
	public int getCompanyId() {
		return companyId;
	}
	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}
	public String getPostDate() {
		return postDate;
	}
	public void setPostDate(String postDate) {
		this.postDate = postDate;
	}
	public String getJobTitle() {
		return jobTitle;
	}
	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getRequirements() {
		return requirements;
	}
	public void setRequirements(String requirements) {
		this.requirements = requirements;
	}
	public String getNoOfVacancies() {
		return noOfVacancies;
	}
	public void setNoOfVacancies(String noOfVacancies) {
		this.noOfVacancies = noOfVacancies;
	}
	public String getOpenDate() {
		return openDate;
	}
	public void setOpenDate(String openDate) {
		this.openDate = openDate;
	}
	public String getCloseDate() {
		return closeDate;
	}
	public void setCloseDate(String closeDate) {
		this.closeDate = closeDate;
	}
	
	
	

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
	@Override
	public String toString() {
		return "Vacancies [vacancyId=" + vacancyId + ", companyId=" + companyId + ", postDate=" + postDate
				+ ", jobTitle=" + jobTitle + ", description=" + description + ", requirements=" + requirements
				+ ", noOfVacancies=" + noOfVacancies + ", openDate=" + openDate + ", closeDate=" + closeDate + "]";
	}
	
	
	

}
