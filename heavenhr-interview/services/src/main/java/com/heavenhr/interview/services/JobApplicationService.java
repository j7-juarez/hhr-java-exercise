package com.heavenhr.interview.services;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.heavenhr.interview.cache.CacheService;
import com.heavenhr.interview.dao.JobApplicationDao;
import com.heavenhr.interview.dao.JobApplicationStatusChangeDao;
import com.heavenhr.interview.model.JobApplication;
import com.heavenhr.interview.model.JobApplicationStatus;
import com.heavenhr.interview.model.JobApplicationStatusChange;
import com.heavenhr.interview.model.JobOffer;
import com.heavenhr.interview.notifications.JobApplicationProgressNotificator;
import com.heavenhr.interview.services.exception.BadDataException;
import com.heavenhr.interview.services.exception.DuplicateElementException;
import com.heavenhr.interview.services.exception.ElementNotFoundException;

@Service
public class JobApplicationService {

	@Autowired
	private JobApplicationDao jobApplicationDao;

	@Autowired
	private JobApplicationStatusChangeDao jobApplicationStatusChangeDao;
	
	@Autowired
	private JobOfferService jobOfferService;
	
	@Autowired
	private JobApplicationProgressNotificator jobApplicationProgressNotificator;
	
	@Autowired
	private CacheService cacheService;
	
	public JobApplication applyToJobOffer(JobApplication jobApplication) throws BadDataException, ElementNotFoundException, DuplicateElementException {
		if(jobApplication == null)
			throw new BadDataException("Job application is null");
		if(jobApplication.getCandidateEmail() == null || jobApplication.getCandidateEmail().isEmpty())
			throw new BadDataException("Candidate email can not be empty");
		if(jobApplication.getResumeText() == null || jobApplication.getResumeText().isEmpty())
			throw new BadDataException("Resume text can not be empty");
		
		JobOffer jobOffer = jobOfferService.getJobOffer(jobApplication.getJobOffer().getId());
		
		if(jobApplicationDao.getJobApplicationByJobOfferIdAndCandidateEmail(jobOffer.getId(), jobApplication.getCandidateEmail()) != null)
			throw new DuplicateElementException("Candidate with email " + jobApplication.getCandidateEmail() + " already aplied for Job offer " + jobOffer.getTitle());
		
		Date now = Calendar.getInstance().getTime();
		
		jobApplication.setCreated(now);
		jobApplication.setStatus(JobApplicationStatus.APPLIED);
		jobApplication.setUpdated(now);
		
		jobApplication = jobApplicationDao.createJobApplication(jobApplication);
		
		String key = jobOffer.getApplicationsCountCacheKey();
		
		cacheService.resetKey(key);
		return jobApplication;
	}
	
	public JobApplication getJobApplication(long jobOfferId, long jobApplicationId) throws ElementNotFoundException{
		JobOffer jobOffer = jobOfferService.getJobOffer(jobOfferId);
		
		JobApplication jobApplication = jobApplicationDao.getJobApplication(jobApplicationId);
		
		if(jobApplication == null)
			throw new ElementNotFoundException("Job Application with id: " + jobApplicationId + " not found");
		
		if(!jobOffer.getId().equals(jobApplication.getJobOffer().getId()))
			throw new ElementNotFoundException("Job application with id: " + jobApplicationId + " not found in Job offer with id: " + jobOfferId);
		
		return jobApplication;
	}
	
	public List<JobApplication> getAllJobApplications(long jobOfferId) throws ElementNotFoundException{
		JobOffer jobOffer = jobOfferService.getJobOffer(jobOfferId);
		
		return jobApplicationDao.getAllJobApplications(jobOffer.getId());
	}
	
	public List<JobApplication> getJobApplications(long jobOfferId, int page, int pageSize) throws ElementNotFoundException {
		JobOffer jobOffer = jobOfferService.getJobOffer(jobOfferId);
		
		return jobApplicationDao.getJobApplications(jobOffer.getId(), page, pageSize);
	}
	
	public JobApplication progressJobApplication(long jobOfferId, long jobApplicationId, JobApplicationStatus status) throws ElementNotFoundException{
		JobOffer jobOffer = jobOfferService.getJobOffer(jobOfferId);
		
		JobApplication jobApplication = jobApplicationDao.getJobApplication(jobApplicationId);
		
		if(jobApplication == null)
			throw new ElementNotFoundException("Job Application with id: " + jobApplicationId + " not found");
		
		if(!jobOffer.getId().equals(jobApplication.getJobOffer().getId()))
			throw new ElementNotFoundException("Job application with id: " + jobApplicationId + " not found in Job offer with id: " + jobOfferId);
		
		JobApplicationStatus actualStatus = jobApplication.getStatus();
		
		jobApplication = jobApplicationDao.updateJobApplicationStatus(jobApplicationId, status);
		
		JobApplicationStatusChange statusChange = new JobApplicationStatusChange();
		
		statusChange.setDate(Calendar.getInstance().getTime());
		statusChange.setFromStatus(actualStatus);
		statusChange.setJobApplication(jobApplication);
		statusChange.setToStatus(status);
		
		jobApplicationStatusChangeDao.registerJobApplicationStatusChange(statusChange);
		
		jobApplicationProgressNotificator.notifyJobApplicationProgress(status, jobApplication.getCandidateEmail(), jobApplication.getJobOffer().getTitle());
		
		return jobApplication;
	}
	
	public List<JobApplicationStatusChange> getChangeHistory(long jobOfferId, long jobApplicationId) throws ElementNotFoundException{
		JobOffer jobOffer = jobOfferService.getJobOffer(jobOfferId);
		
		JobApplication jobApplication = jobApplicationDao.getJobApplication(jobApplicationId);
		
		if(jobApplication == null)
			throw new ElementNotFoundException("Job Application with id: " + jobApplicationId + " not found");
		
		if(!jobOffer.getId().equals(jobApplication.getJobOffer().getId()))
			throw new ElementNotFoundException("Job application with id: " + jobApplicationId + " not found in Job offer with id: " + jobOfferId);
		
		return jobApplicationStatusChangeDao.getChangesHistory(jobApplicationId);
	}
	
	public JobApplicationDao getJobApplicationDao() {
		return jobApplicationDao;
	}

	public void setJobApplicationDao(JobApplicationDao jobApplicationDao) {
		this.jobApplicationDao = jobApplicationDao;
	}

	public JobOfferService getJobOfferService() {
		return jobOfferService;
	}

	public void setJobOfferService(JobOfferService jobOfferService) {
		this.jobOfferService = jobOfferService;
	}

	public JobApplicationStatusChangeDao getJobApplicationStatusChangeDao() {
		return jobApplicationStatusChangeDao;
	}

	public void setJobApplicationStatusChangeDao(JobApplicationStatusChangeDao jobApplicationStatusChangeDao) {
		this.jobApplicationStatusChangeDao = jobApplicationStatusChangeDao;
	}

	public JobApplicationProgressNotificator getJobApplicationProgressNotificator() {
		return jobApplicationProgressNotificator;
	}

	public void setJobApplicationProgressNotificator(JobApplicationProgressNotificator jobApplicationProgressNotificator) {
		this.jobApplicationProgressNotificator = jobApplicationProgressNotificator;
	}

	public CacheService getCacheService() {
		return cacheService;
	}

	public void setCacheService(CacheService cacheService) {
		this.cacheService = cacheService;
	}
	
}
