	package com.example.entity;
	
	public class Certificates {
		private int documentId;
		private int userId;
		private String documentType;
		private String documentFile;
		private JobSeekers jobseekers;
		public int getDocumentId() {
			return documentId;
		}
		public void setDocumentId(int documentId) {
			this.documentId = documentId;
		}
		public int getUserId() {
			return userId;
		}
		public JobSeekers getJobseekers() {
			return jobseekers;
		}
		public void setJobseekers(JobSeekers jobseekers) {
			this.jobseekers = jobseekers;
		}
		public void setUserId(int userId) {
			this.userId = userId;
		}
		public String getDocumentType() {
			return documentType;
		}
		public void setDocumentType(String documentType) {
			this.documentType = documentType;
		}
		public String getDocumentFile() {
			return documentFile;
		}
		public void setDocumentFile(String documentFile) {
			this.documentFile = documentFile;
		}
		@Override
		public String toString() {
			return "Certificates [documentId=" + documentId + ", userId=" + userId + ", documentType=" + documentType
					+ ", documentFile=" + documentFile + ", jobseekers=" + jobseekers + "]";
		}
		
		
		
	
	}
