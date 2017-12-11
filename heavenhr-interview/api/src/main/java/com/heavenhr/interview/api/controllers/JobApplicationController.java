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

import com.heavenhr.interview.api.dto.JobApplicationDto;
import com.heavenhr.interview.api.dto.JobApplicationStatusChangeDto;
import com.heavenhr.interview.api.exception.BadDataApiException;
import com.heavenhr.interview.api.exception.DuplicateElementApiException;
import com.heavenhr.interview.api.exception.ElementNotFoundApiException;
import com.heavenhr.interview.model.JobApplication;
import com.heavenhr.interview.model.JobApplicationStatusChange;
import com.heavenhr.interview.model.JobOffer;
import com.heavenhr.interview.services.JobApplicationService;
import com.heavenhr.interview.services.exception.BadDataException;
import com.heavenhr.interview.services.exception.DuplicateElementException;
import com.heavenhr.interview.services.exception.ElementNotFoundException;

@RestController
public class JobApplicationController {

	@Autowired
	private JobApplicationService jobApplicationService;
	
	@RequestMapping(value = "/joboffers/{offerId}/apply", method = RequestMethod.POST)
	@ResponseBody
	public JobApplicationDto applyToJobOffer(@RequestBody JobApplicationCreationRequest request, @PathVariable("offerId") long offerId) {
		JobOffer jobOffer = new JobOffer();
		
		jobOffer.setId(offerId);
		
		JobApplication jobApplication = new JobApplication();
		
		jobApplication.setCandidateEmail(request.getEmail());
		jobApplication.setResumeText(request.getResumeText());
		jobApplication.setJobOffer(jobOffer);
		
		try {
			jobApplication = jobApplicationService.applyToJobOffer(jobApplication);
		} catch (DuplicateElementException e) {
			throw new DuplicateElementApiException(e.getMessage());
		} catch (BadDataException e) {
			throw new BadDataApiException(e.getMessage());
		} catch (ElementNotFoundException e) {
			throw new ElementNotFoundApiException(e.getMessage());
		}
		
		return JobApplicationDto.fromJobApplication(jobApplication);
	}
	
	@RequestMapping(value = "/joboffers/{offerId}/applications/{applicationId}", method = RequestMethod.GET)
	@ResponseBody
	public JobApplicationDto getJobApplication(@PathVariable("offerId") long offerId, @PathVariable("applicationId") long applicationId){
		try {
			JobApplication jobApplication = jobApplicationService.getJobApplication(offerId, applicationId);
			
			return JobApplicationDto.fromJobApplication(jobApplication);
			
		} catch (ElementNotFoundException e) {
			throw new ElementNotFoundApiException(e.getMessage());
		}
	}
	
	@RequestMapping(value = "/joboffers/{offerId}/applications", method = RequestMethod.GET)
	@ResponseBody
	public List<JobApplicationDto> getJobApplications(@PathVariable("offerId") long offerId, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "pagesize", required = false) Integer pageSize){
		List<JobApplication> jobApplications;
		
		try {
			if(page != null && page >= 0 && pageSize != null && pageSize >= 0)
				jobApplications = jobApplicationService.getJobApplications(offerId, page, pageSize);
			else
				jobApplications = jobApplicationService.getAllJobApplications(offerId);
		} catch (ElementNotFoundException e) {
			throw new ElementNotFoundApiException(e.getMessage());
		}
		
		List<JobApplicationDto> jobApplicationDtos = new ArrayList<>();
		
		jobApplications.forEach(x -> jobApplicationDtos.add(JobApplicationDto.fromJobApplication(x)));
		
		return jobApplicationDtos;
	}

	@RequestMapping(value = "/joboffers/{offerId}/applications/{applicationId}/status", method = RequestMethod.PUT)
	@ResponseBody
	public JobApplicationDto updateJobApplicationStatus(@PathVariable("offerId") long offerId, @PathVariable("applicationId") long applicationId, @RequestBody JobApplicationStatusChangeRequest request){
		try {
			JobApplication jobApplication = jobApplicationService.progressJobApplication(offerId, applicationId, request.getStatus());
			
			return JobApplicationDto.fromJobApplication(jobApplication);
			
		} catch (ElementNotFoundException e) {
			throw new ElementNotFoundApiException(e.getMessage());
		}
	}
	
	@RequestMapping(value = "/joboffers/{offerId}/applications/{applicationId}/history", method = RequestMethod.GET)
	@ResponseBody
	public List<JobApplicationStatusChangeDto> getJobApplicationStatusHistory(@PathVariable("offerId") long offerId, @PathVariable("applicationId") long applicationId){
		try {
			List<JobApplicationStatusChange> jobApplicationStatusChanges = jobApplicationService.getChangeHistory(offerId, applicationId);
			
			List<JobApplicationStatusChangeDto> jobApplicationStatusChangeDtos = new ArrayList<>();
			
			jobApplicationStatusChanges.forEach(x -> jobApplicationStatusChangeDtos.add(JobApplicationStatusChangeDto.fromJobApplicationStatusChange(x)));
			
			return jobApplicationStatusChangeDtos;
			
		} catch (ElementNotFoundException e) {
			throw new ElementNotFoundApiException(e.getMessage());
		}
	}
	
	public JobApplicationService getJobApplicationService() {
		return jobApplicationService;
	}

	public void setJobApplicationService(JobApplicationService jobApplicationService) {
		this.jobApplicationService = jobApplicationService;
	}
}
