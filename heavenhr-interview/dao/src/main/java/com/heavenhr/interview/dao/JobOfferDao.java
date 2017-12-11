package com.heavenhr.interview.dao;

import java.util.List;

import com.heavenhr.interview.model.JobOffer;

public interface JobOfferDao {

	public JobOffer createJobOffer(JobOffer jobOffer);
	public JobOffer getJobOffer(long jobOfferId);
	public JobOffer getJobOfferByTitle(String title);
	public List<JobOffer> getAllJobOffers();
	public List<JobOffer> getJobOffers(int page, int pageSize);
	
}
