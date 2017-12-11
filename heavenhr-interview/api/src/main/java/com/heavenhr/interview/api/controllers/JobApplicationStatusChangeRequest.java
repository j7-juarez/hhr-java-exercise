package com.heavenhr.interview.api.controllers;

import com.heavenhr.interview.model.JobApplicationStatus;

public class JobApplicationStatusChangeRequest {

	private JobApplicationStatus status;

	public JobApplicationStatus getStatus() {
		return status;
	}

	public void setStatus(JobApplicationStatus status) {
		this.status = status;
	}
	
}
