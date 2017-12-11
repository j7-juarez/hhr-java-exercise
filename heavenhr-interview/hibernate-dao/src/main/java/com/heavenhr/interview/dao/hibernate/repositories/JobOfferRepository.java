package com.heavenhr.interview.dao.hibernate.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.heavenhr.interview.model.JobOffer;

@Repository
@Transactional
public interface JobOfferRepository extends JpaRepository<JobOffer, Long>{

	public JobOffer findByTitle(String title);
	
}
