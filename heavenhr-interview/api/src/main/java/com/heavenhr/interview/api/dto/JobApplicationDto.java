package com.heavenhr.interview.api.dto;

import com.heavenhr.interview.model.JobApplication;
import com.heavenhr.interview.model.JobApplicationStatus;

public class JobApplicationDto {

	private long id;
	
	private long jobOfferId;
	
	private String candidateEmail;
	
	private String resumeText;
	
	private JobApplicationStatus status;

	public static JobApplicationDto fromJobApplication(JobApplication jobApplication) {
		JobApplicationDto dto = new JobApplicationDto();
		
		dto.setCandidateEmail(jobApplication.getCandidateEmail());
		dto.setId(jobApplication.getId());
		dto.setJobOfferId(jobApplication.getJobOffer().getId());
		dto.setResumeText(jobApplication.getResumeText());
		dto.setStatus(jobApplication.getStatus());
		
		return dto;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getJobOfferId() {
		return jobOfferId;
	}

	public void setJobOfferId(long jobOfferId) {
		this.jobOfferId = jobOfferId;
	}

	public String getCandidateEmail() {
		return candidateEmail;
	}

	public void setCandidateEmail(String candidateEmail) {
		this.candidateEmail = candidateEmail;
	}

	public String getResumeText() {
		return resumeText;
	}

	public void setResumeText(String resumeText) {
		this.resumeText = resumeText;
	}

	public JobApplicationStatus getStatus() {
		return status;
	}

	public void setStatus(JobApplicationStatus status) {
		this.status = status;
	}
	
}
