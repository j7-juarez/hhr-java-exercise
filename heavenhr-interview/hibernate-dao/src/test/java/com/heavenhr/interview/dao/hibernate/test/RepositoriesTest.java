package com.heavenhr.interview.dao.hibernate.test;

import java.util.Calendar;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.heavenhr.interview.dao.hibernate.repositories.JobApplicationRepository;
import com.heavenhr.interview.dao.hibernate.repositories.JobOfferRepository;
import com.heavenhr.interview.model.JobApplication;
import com.heavenhr.interview.model.JobApplicationStatus;
import com.heavenhr.interview.model.JobOffer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestContextConfiguration.class)
public class RepositoriesTest {

	@Autowired
	private JobOfferRepository jobOfferRepository;
	
	@Autowired
	private JobApplicationRepository jobApplicationRepository;
	
	@Test
	public void jobOfferInsertTest() {
		JobOffer jobOffer = new JobOffer();
		
		jobOffer.setStartDate(Calendar.getInstance().getTime());
		jobOffer.setTitle("Title 1");
		
		jobOfferRepository.saveAndFlush(jobOffer);
		
		jobOffer = jobOfferRepository.findByTitle("Title 1");
		
		Assert.assertNotNull(jobOffer);
		
		jobOffer = jobOfferRepository.findByTitle("Title 2");
		
		Assert.assertNull(jobOffer);
	}
	
	@Test(expected = DataIntegrityViolationException.class)
	public void jobOfferUniqueTitleTest() {
		JobOffer jobOffer = new JobOffer();
		
		jobOffer.setStartDate(Calendar.getInstance().getTime());
		jobOffer.setTitle("Unique");
		
		jobOfferRepository.saveAndFlush(jobOffer);
		
		jobOffer = jobOfferRepository.findByTitle("Title 1");
		
		Assert.assertNotNull(jobOffer);
		
		jobOffer = new JobOffer();
		
		jobOffer.setStartDate(Calendar.getInstance().getTime());
		jobOffer.setTitle("Unique");
		
		jobOfferRepository.saveAndFlush(jobOffer);
	}
	
	@Test
	public void jobApplicationInsertTest() {
		JobOffer jobOffer = new JobOffer();
		
		jobOffer.setStartDate(Calendar.getInstance().getTime());
		jobOffer.setTitle("Job 1");
		
		jobOfferRepository.saveAndFlush(jobOffer);
		
		JobApplication jobApplication = new JobApplication();
		
		jobApplication.setCandidateEmail("email1@mail.com");
		jobApplication.setCreated(Calendar.getInstance().getTime());
		jobApplication.setJobOffer(jobOffer);
		jobApplication.setResumeText("Resume");
		jobApplication.setStatus(JobApplicationStatus.APPLIED);
		jobApplication.setUpdated(Calendar.getInstance().getTime());
		
		jobApplicationRepository.saveAndFlush(jobApplication);
		
		jobApplication = jobApplicationRepository.findByJobOfferIdAndCandidateEmail(jobOffer.getId(), "email1@mail.com");
		
		Assert.assertNotNull(jobApplication);
		
		jobApplication = jobApplicationRepository.findByJobOfferIdAndCandidateEmail(jobOffer.getId(), "email2@mail.com");
		
		Assert.assertNull(jobApplication);
	}
	
	@Test(expected = DataIntegrityViolationException.class)
	public void jobApplicationUniqueEmailPerJobOfferTest() {
		JobOffer jobOffer = new JobOffer();
		
		jobOffer.setStartDate(Calendar.getInstance().getTime());
		jobOffer.setTitle("Job 2");
		
		jobOfferRepository.saveAndFlush(jobOffer);
		
		JobApplication jobApplication = new JobApplication();
		
		jobApplication.setCandidateEmail("email1@mail.com");
		jobApplication.setCreated(Calendar.getInstance().getTime());
		jobApplication.setJobOffer(jobOffer);
		jobApplication.setResumeText("Resume");
		jobApplication.setStatus(JobApplicationStatus.APPLIED);
		jobApplication.setUpdated(Calendar.getInstance().getTime());
		
		jobApplicationRepository.saveAndFlush(jobApplication);
		
		jobApplication = jobApplicationRepository.findByJobOfferIdAndCandidateEmail(jobOffer.getId(), "email1@mail.com");
		
		Assert.assertNotNull(jobApplication);
		
		jobApplication = new JobApplication();
		
		jobApplication.setCandidateEmail("email1@mail.com");
		jobApplication.setCreated(Calendar.getInstance().getTime());
		jobApplication.setJobOffer(jobOffer);
		jobApplication.setResumeText("Resume");
		jobApplication.setStatus(JobApplicationStatus.APPLIED);
		jobApplication.setUpdated(Calendar.getInstance().getTime());
		
		jobApplicationRepository.saveAndFlush(jobApplication);
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
