package com.heavenhr.interview.dao;

import java.util.List;

import com.heavenhr.interview.model.JobApplicationStatusChange;

public interface JobApplicationStatusChangeDao {

	public void registerJobApplicationStatusChange(JobApplicationStatusChange jobApplicationStatusChange);
	public List<JobApplicationStatusChange> getChangesHistory(long jobApplicationId);
	
}
