package com.heavenhr.interview.services.test;

import java.util.Calendar;

import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Test;

import com.heavenhr.interview.dao.JobOfferDao;
import com.heavenhr.interview.model.JobOffer;
import com.heavenhr.interview.services.JobOfferService;
import com.heavenhr.interview.services.exception.BadDataException;
import com.heavenhr.interview.services.exception.DuplicateElementException;

public class JobOfferServiceTest {

	@Test
	public void createJobOfferNullTest(){
		JobOfferService jobOfferService = new JobOfferService();
		
		try {
			jobOfferService.createJobOffer(null);
			Assert.assertTrue(false);
		} catch (DuplicateElementException e) {
			Assert.assertTrue(false);
		} catch (BadDataException e) {
			Assert.assertEquals("Job offer is null", e.getMessage());
		}
	}
	
	@Test
	public void createJobOfferTitleEmptyTest(){
		JobOfferService jobOfferService = new JobOfferService();
		
		JobOffer jobOffer = new JobOffer();
		
		jobOffer.setStartDate(Calendar.getInstance().getTime());
		
		try {
			jobOfferService.createJobOffer(jobOffer);
			Assert.assertTrue(false);
		} catch (DuplicateElementException e) {
			Assert.assertTrue(false);
		} catch (BadDataException e) {
			Assert.assertEquals("Title can not be empty", e.getMessage());
		}
	}
	
	@Test
	public void createJobOfferStartDateEmptyTest(){
		JobOfferService jobOfferService = new JobOfferService();
		
		JobOffer jobOffer = new JobOffer();
		
		jobOffer.setTitle("Title");
		
		try {
			jobOfferService.createJobOffer(jobOffer);
			Assert.assertTrue(false);
		} catch (DuplicateElementException e) {
			Assert.assertTrue(false);
		} catch (BadDataException e) {
			Assert.assertEquals("Start date can not be empty", e.getMessage());
		}
	}
	
	@Test
	public void createJobOfferDuplicateTest(){
		JobOfferDao jobOfferDao = EasyMock.createMock(JobOfferDao.class);
		
		EasyMock.expect(jobOfferDao.getJobOfferByTitle("Title")).andReturn(new JobOffer());
		
		EasyMock.replay(jobOfferDao);
		
		JobOfferService jobOfferService = new JobOfferService();
		jobOfferService.setJobOfferDao(jobOfferDao);
		
		JobOffer jobOffer = new JobOffer();
		
		jobOffer.setTitle("Title");
		jobOffer.setStartDate(Calendar.getInstance().getTime());
		
		try {
			jobOfferService.createJobOffer(jobOffer);
			Assert.assertTrue(false);
		} catch (DuplicateElementException e) {
			Assert.assertEquals("Job offer already exists with title: Title", e.getMessage());
		} catch (BadDataException e) {
			Assert.assertTrue(false);
		}
		
		EasyMock.verify(jobOfferDao);
	}
	
	@Test
	public void createJobOfferSuccessTest(){
		JobOffer jobOffer = new JobOffer();
		
		jobOffer.setTitle("Title");
		jobOffer.setStartDate(Calendar.getInstance().getTime());
		
		JobOfferDao jobOfferDao = EasyMock.createMock(JobOfferDao.class);
		
		EasyMock.expect(jobOfferDao.getJobOfferByTitle("Title")).andReturn(null).once();
		
		EasyMock.expect(jobOfferDao.createJobOffer(jobOffer)).andReturn(new JobOffer(){{this.setId(10L);}}).once();
		
		EasyMock.replay(jobOfferDao);
		
		JobOfferService jobOfferService = new JobOfferService();
		jobOfferService.setJobOfferDao(jobOfferDao);
		
		try {
			JobOffer result = jobOfferService.createJobOffer(jobOffer);
			Assert.assertEquals(10, result.getId().longValue());
		} catch (DuplicateElementException e) {
			Assert.assertEquals("Job offer already exists with title: Title", e.getMessage());
		} catch (BadDataException e) {
			Assert.assertTrue(false);
		}
		
		EasyMock.verify(jobOfferDao);
	}
}
