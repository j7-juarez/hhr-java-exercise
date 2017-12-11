package com.heavenhr.interview.api.dto;

import java.util.Date;

import com.heavenhr.interview.model.JobOffer;

public class JobOfferDto {

	private long id;
	
	private String title;
	
	private Date startDate;
	
	private int numberOfApplications;
	
	public static JobOfferDto fromJobOffer(JobOffer jobOffer){
		JobOfferDto dto = new JobOfferDto();
		
		dto.setId(jobOffer.getId());
		dto.setNumberOfApplications(jobOffer.getNumberOfApplications());
		dto.setStartDate(jobOffer.getStartDate());
		dto.setTitle(jobOffer.getTitle());
		
		return dto;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public int getNumberOfApplications() {
		return numberOfApplications;
	}

	public void setNumberOfApplications(int numberOfApplications) {
		this.numberOfApplications = numberOfApplications;
	}
	
}
