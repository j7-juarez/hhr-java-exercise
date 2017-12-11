package com.heavenhr.interview.dao.hibernate;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.heavenhr.interview.dao.JobApplicationStatusChangeDao;
import com.heavenhr.interview.dao.hibernate.repositories.JobApplicationRepository;
import com.heavenhr.interview.dao.hibernate.repositories.JobApplicationStatusChangeRepository;
import com.heavenhr.interview.model.JobApplication;
import com.heavenhr.interview.model.JobApplicationStatusChange;

@Service
@Transactional
public class HibernateJobApplicationStatusChangeDao implements JobApplicationStatusChangeDao {

	@Autowired
	private JobApplicationRepository jobApplicationRepository;
	
	@Autowired
	private JobApplicationStatusChangeRepository jobApplicationStatusChangeRepository;
	
	@Override
	public void registerJobApplicationStatusChange(JobApplicationStatusChange jobApplicationStatusChange) {
		JobApplication jobApplication = jobApplicationRepository.findOne(jobApplicationStatusChange.getJobApplication().getId());
		
		jobApplicationStatusChange.setId(null);
		jobApplicationStatusChange.setJobApplication(jobApplication);
		
		jobApplicationStatusChangeRepository.saveAndFlush(jobApplicationStatusChange);
	}

	@Override
	public List<JobApplicationStatusChange> getChangesHistory(long jobApplicationId) {
		return jobApplicationStatusChangeRepository.findByJobApplicationId(jobApplicationId);
	}

	public JobApplicationRepository getJobApplicationRepository() {
		return jobApplicationRepository;
	}

	public void setJobApplicationRepository(JobApplicationRepository jobApplicationRepository) {
		this.jobApplicationRepository = jobApplicationRepository;
	}

	public JobApplicationStatusChangeRepository getJobApplicationStatusChangeRepository() {
		return jobApplicationStatusChangeRepository;
	}

	public void setJobApplicationStatusChangeRepository(JobApplicationStatusChangeRepository jobApplicationStatusChangeRepository) {
		this.jobApplicationStatusChangeRepository = jobApplicationStatusChangeRepository;
	}

}
