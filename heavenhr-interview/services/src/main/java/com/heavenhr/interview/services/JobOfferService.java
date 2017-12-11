package com.heavenhr.interview.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.heavenhr.interview.cache.CacheService;
import com.heavenhr.interview.dao.JobApplicationDao;
import com.heavenhr.interview.dao.JobOfferDao;
import com.heavenhr.interview.model.JobOffer;
import com.heavenhr.interview.services.exception.BadDataException;
import com.heavenhr.interview.services.exception.DuplicateElementException;
import com.heavenhr.interview.services.exception.ElementNotFoundException;

@Service
public class JobOfferService {

	@Autowired
	private JobOfferDao jobOfferDao;

	@Autowired
	private JobApplicationDao jobApplicationDao;
	
	@Autowired
	private CacheService cacheService;
	
	public JobOffer createJobOffer(JobOffer jobOffer) throws DuplicateElementException, BadDataException {
		if(jobOffer == null)
			throw new BadDataException("Job offer is null");
		if(jobOffer.getTitle() == null || jobOffer.getTitle().isEmpty())
			throw new BadDataException("Title can not be empty");
		if(jobOffer.getStartDate() == null)
			throw new BadDataException("Start date can not be empty");
		
		if(jobOfferDao.getJobOfferByTitle(jobOffer.getTitle()) != null)
			throw new DuplicateElementException("Job offer already exists with title: " + jobOffer.getTitle());
		
		jobOffer =  jobOfferDao.createJobOffer(jobOffer);
		
		return jobOffer;
	}
	
	public JobOffer getJobOffer(long jobOfferId) throws ElementNotFoundException {
		JobOffer jobOffer = jobOfferDao.getJobOffer(jobOfferId);
		
		if(jobOffer == null)
			throw new ElementNotFoundException("Job Offer with id: " + jobOfferId + " not found");
		
		int applicationsCount = getApplicationsCountofJobOffer(jobOffer);
		
		jobOffer.setNumberOfApplications(applicationsCount);
		
		return jobOffer;
	}
	
	public int getApplicationsCountofJobOffer(long jobOfferId) throws ElementNotFoundException {
		JobOffer jobOffer = jobOfferDao.getJobOffer(jobOfferId);
		
		if(jobOffer == null)
			throw new ElementNotFoundException("Job Offer with id: " + jobOfferId + " not found");
		
		int applicationsCount = getApplicationsCountofJobOffer(jobOffer);
		
		return applicationsCount;
	}
	
	public List<JobOffer> getAllJobOffers() {
		List<JobOffer> jobOffers = jobOfferDao.getAllJobOffers();
		
		for (JobOffer jobOffer : jobOffers) {
			jobOffer.setNumberOfApplications(getApplicationsCountofJobOffer(jobOffer));
		}
		
		return jobOffers;
	}
	
	public List<JobOffer> getJobOffers(int page, int pageSize) {
		List<JobOffer> jobOffers = jobOfferDao.getJobOffers(page, pageSize);
		
		for (JobOffer jobOffer : jobOffers) {
			jobOffer.setNumberOfApplications(getApplicationsCountofJobOffer(jobOffer));
		}
		
		return jobOffers;
	}
	
	private int getApplicationsCountofJobOffer(JobOffer jobOffer) {
		String key = jobOffer.getApplicationsCountCacheKey();
		
		Object cached = cacheService.getValue(key);
		
		if(cached != null)
			return ((Integer)cached).intValue();
		
		int count = jobApplicationDao.getJobApplicationsCount(jobOffer.getId());
		
		cacheService.setKey(key, new Integer(count));
		
		return count;
	}
	
	public JobOfferDao getJobOfferDao() {
		return jobOfferDao;
	}

	public void setJobOfferDao(JobOfferDao jobOfferDao) {
		this.jobOfferDao = jobOfferDao;
	}

	public JobApplicationDao getJobApplicationDao() {
		return jobApplicationDao;
	}

	public void setJobApplicationDao(JobApplicationDao jobApplicationDao) {
		this.jobApplicationDao = jobApplicationDao;
	}

	public CacheService getCacheService() {
		return cacheService;
	}

	public void setCacheService(CacheService cacheService) {
		this.cacheService = cacheService;
	}
	
}
