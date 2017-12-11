package com.heavenhr.interview.api.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.heavenhr.interview.api.dto.JobOfferDto;
import com.heavenhr.interview.api.exception.BadDataApiException;
import com.heavenhr.interview.api.exception.DuplicateElementApiException;
import com.heavenhr.interview.api.exception.ElementNotFoundApiException;
import com.heavenhr.interview.model.JobOffer;
import com.heavenhr.interview.services.JobOfferService;
import com.heavenhr.interview.services.exception.BadDataException;
import com.heavenhr.interview.services.exception.DuplicateElementException;
import com.heavenhr.interview.services.exception.ElementNotFoundException;

@RestController
public class JobOfferController {

	@Autowired
	private JobOfferService jobOfferService;
	
	@RequestMapping(value = "/joboffer", method = RequestMethod.POST)
	@ResponseBody
	public JobOfferDto createJobOffer(@RequestBody JobOfferCreationRequest request) {
		JobOffer jobOffer = new JobOffer();
		
		jobOffer.setTitle(request.getJobTitle());
		jobOffer.setStartDate(request.getStartDate());
		
		try {
			jobOffer = jobOfferService.createJobOffer(jobOffer);
		} catch (DuplicateElementException e) {
			throw new DuplicateElementApiException(e.getMessage());
		} catch (BadDataException e) {
			throw new BadDataApiException(e.getMessage());
		}
		
		return JobOfferDto.fromJobOffer(jobOffer);
	}
	
	@RequestMapping(value = "/joboffers/{offerId}", method = RequestMethod.GET)
	@ResponseBody
	public JobOfferDto getJobOffer(@PathVariable("offerId") long offerId){
		try {
			JobOffer jobOffer = jobOfferService.getJobOffer(offerId);
			
			return JobOfferDto.fromJobOffer(jobOffer);
			
		} catch (ElementNotFoundException e) {
			throw new ElementNotFoundApiException(e.getMessage());
		}
	}
	
	@RequestMapping(value = "/joboffers", method = RequestMethod.GET)
	@ResponseBody
	public List<JobOfferDto> getJobOffers(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "pagesize", required = false) Integer pageSize){
		List<JobOffer> jobOffers;
		
		if(page != null && page >= 0 && pageSize != null && pageSize >= 0)
			jobOffers = jobOfferService.getJobOffers(page, pageSize);
		else
			jobOffers = jobOfferService.getAllJobOffers();
		
		List<JobOfferDto> jobOfferDtos = new ArrayList<>();
		
		jobOffers.forEach(x -> jobOfferDtos.add(JobOfferDto.fromJobOffer(x)));
		
		return jobOfferDtos;
	}

	@RequestMapping(value = "/joboffers/{offerId}/numberofapplications", method = RequestMethod.GET)
	@ResponseBody
	public int getJobOfferApplicationsCount(@PathVariable("offerId") long offerId){
		try {
			return jobOfferService.getApplicationsCountofJobOffer(offerId);
		} catch (ElementNotFoundException e) {
			throw new ElementNotFoundApiException(e.getMessage());
		}
	}
	
	public JobOfferService getJobOfferService() {
		return jobOfferService;
	}

	public void setJobOfferService(JobOfferService jobOfferService) {
		this.jobOfferService = jobOfferService;
	}
}
