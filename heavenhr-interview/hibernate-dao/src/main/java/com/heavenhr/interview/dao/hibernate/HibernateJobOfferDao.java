package com.heavenhr.interview.dao.hibernate;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.heavenhr.interview.dao.JobOfferDao;
import com.heavenhr.interview.dao.hibernate.repositories.JobOfferRepository;
import com.heavenhr.interview.model.JobOffer;

@Service
@Transactional
public class HibernateJobOfferDao implements JobOfferDao {

	@Autowired
	private JobOfferRepository jobOfferRepository;
	
	@Override
	public JobOffer createJobOffer(JobOffer jobOffer) {
		jobOffer.setId(null);
		jobOffer.setApplications(null);
		
		jobOfferRepository.saveAndFlush(jobOffer);
		
		return jobOffer;
	}

	@Override
	public JobOffer getJobOffer(long jobOfferId) {
		return jobOfferRepository.findOne(jobOfferId);
	}

	@Override
	public JobOffer getJobOfferByTitle(String title) {
		return jobOfferRepository.findByTitle(title);
	}
	
	@Override
	public List<JobOffer> getAllJobOffers() {
		return jobOfferRepository.findAll();
	}

	@Override
	public List<JobOffer> getJobOffers(int page, int pageSize) {
		Pageable pageable = new PageRequest(page, pageSize);
		
		Page<JobOffer> jobOffers = jobOfferRepository.findAll(pageable);
		
		return jobOffers.getContent();
	}

	public JobOfferRepository getJobOfferRepository() {
		return jobOfferRepository;
	}

	public void setJobOfferRepository(JobOfferRepository jobOfferRepository) {
		this.jobOfferRepository = jobOfferRepository;
	}

}
