package com.heavenhr.interview.dao.hibernate.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.heavenhr.interview.model.JobApplicationStatusChange;

@Repository
@Transactional
public interface JobApplicationStatusChangeRepository extends JpaRepository<JobApplicationStatusChange, Long> {

	public List<JobApplicationStatusChange> findByJobApplicationId(Long jobApplicationId);
	
}
