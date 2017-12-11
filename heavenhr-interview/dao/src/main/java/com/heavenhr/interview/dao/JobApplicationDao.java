package com.heavenhr.interview.dao;

import java.util.List;

import com.heavenhr.interview.model.JobApplication;
import com.heavenhr.interview.model.JobApplicationStatus;

public interface JobApplicationDao {

	public JobApplication createJobApplication(JobApplication jobApplication);
	public JobApplication getJobApplication(long jobApplicationId);
	public JobApplication getJobApplicationByJobOfferIdAndCandidateEmail(long offerId, String email);
	public List<JobApplication> getAllJobApplications(long offerId);
	public List<JobApplication> getJobApplications(long offerId, int page, int pageSize);
	public JobApplication updateJobApplicationStatus(long jobApplicationId, JobApplicationStatus status);
	public int getJobApplicationsCount(long offerId);
	
}
