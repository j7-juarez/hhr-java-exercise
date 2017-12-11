package com.heavenhr.interview.dao.hibernate.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.heavenhr.interview.model.JobApplication;

@Repository
@Transactional
public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {

	public List<JobApplication> findByJobOfferId(Long jobOfferId);
	
	public Page<JobApplication> findByJobOfferId(Long jobOfferId, Pageable page);
	
	public JobApplication findByJobOfferIdAndCandidateEmail(Long jobOfferId, String candidateEmail);
	
	@Query("select count(a) from JobApplication a where a.jobOffer.id = :offerId")
	public Integer countJobApplicationsByJobOfferId(@Param("offerId") Long jobOfferId);
}
