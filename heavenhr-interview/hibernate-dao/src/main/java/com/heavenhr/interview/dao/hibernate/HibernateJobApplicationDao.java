package com.heavenhr.interview.dao.hibernate;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.heavenhr.interview.dao.JobApplicationDao;
import com.heavenhr.interview.dao.hibernate.repositories.JobApplicationRepository;
import com.heavenhr.interview.dao.hibernate.repositories.JobOfferRepository;
import com.heavenhr.interview.model.JobApplication;
import com.heavenhr.interview.model.JobApplicationStatus;
import com.heavenhr.interview.model.JobOffer;

@Service
@Transactional
public class HibernateJobApplicationDao implements JobApplicationDao {

	@Autowired
	private JobOfferRepository jobOfferRepository;
	
	@Autowired
	private JobApplicationRepository jobApplicationRepository;
	
	@Override
	public JobApplication createJobApplication(JobApplication jobApplication) {
		JobOffer jobOffer = jobOfferRepository.findOne(jobApplication.getJobOffer().getId());
		
		jobApplication.setId(null);
		jobApplication.setJobOffer(jobOffer);
		jobApplication.setStatusChanges(null);
		
		jobApplicationRepository.saveAndFlush(jobApplication);
		
		return jobApplication;
	}

	@Override
	public JobApplication getJobApplication(long jobApplicationId) {
		JobApplication jobApplication = jobApplicationRepository.findOne(jobApplicationId);
		jobApplication.getJobOffer().getId();
		return jobApplication;
	}
	
	@Override
	public JobApplication getJobApplicationByJobOfferIdAndCandidateEmail(long offerId, String email) {
		return jobApplicationRepository.findByJobOfferIdAndCandidateEmail(offerId, email);
	}

	@Override
	public List<JobApplication> getAllJobApplications(long offerId) {
		List<JobApplication> jobApplications = jobApplicationRepository.findByJobOfferId(offerId);
		
		jobApplications.forEach(x -> x.getJobOffer().getId());
		
		return jobApplications;
	}

	@Override
	public List<JobApplication> getJobApplications(long offerId, int page, int pageSize) {
		Pageable pageable = new PageRequest(page, pageSize);
		
		Page<JobApplication> pageResult = jobApplicationRepository.findByJobOfferId(offerId, pageable);
		
		List<JobApplication> jobApplications = pageResult.getContent();
		
		jobApplications.forEach(x -> x.getJobOffer().getId());
		
		return jobApplications;
	}

	@Override
	public JobApplication updateJobApplicationStatus(long jobApplicationId, JobApplicationStatus status) {
		JobApplication jobApplication = jobApplicationRepository.findOne(jobApplicationId);
		
		jobApplication.setStatus(status);
		jobApplication.setUpdated(Calendar.getInstance().getTime());
		
		jobApplication = jobApplicationRepository.saveAndFlush(jobApplication);
		
		jobApplication.getJobOffer().getId();
		
		return jobApplication;
	}

	@Override
	public int getJobApplicationsCount(long offerId) {
		return jobApplicationRepository.countJobApplicationsByJobOfferId(offerId);
	}

	public JobOfferRepository getJobOfferRepository() {
		return jobOfferRepository;
	}

	public void setJobOfferRepository(JobOfferRepository jobOfferRepository) {
		this.jobOfferRepository = jobOfferRepository;
	}

	public JobApplicationRepository getJobApplicationRepository() {
		return jobApplicationRepository;
	}

	public void setJobApplicationRepository(JobApplicationRepository jobApplicationRepository) {
		this.jobApplicationRepository = jobApplicationRepository;
	}

}
