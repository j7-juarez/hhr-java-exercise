package com.heavenhr.interview.api.controllers;

public class JobApplicationCreationRequest {

	private String email;
	
	private String resumeText;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getResumeText() {
		return resumeText;
	}

	public void setResumeText(String resumeText) {
		this.resumeText = resumeText;
	}
	
}
