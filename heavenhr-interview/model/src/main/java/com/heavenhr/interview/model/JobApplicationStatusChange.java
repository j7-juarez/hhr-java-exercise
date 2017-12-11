package com.heavenhr.interview.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "jobapplicationstatuschange")
public class JobApplicationStatusChange {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "jobapplicationid")
	private JobApplication jobApplication;
	
	private JobApplicationStatus fromStatus;
	
	private JobApplicationStatus toStatus;
	
	@Temporal(TemporalType.DATE)
	private Date date;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public JobApplication getJobApplication() {
		return jobApplication;
	}

	public void setJobApplication(JobApplication jobApplication) {
		this.jobApplication = jobApplication;
	}

	public JobApplicationStatus getFromStatus() {
		return fromStatus;
	}

	public void setFromStatus(JobApplicationStatus fromStatus) {
		this.fromStatus = fromStatus;
	}

	public JobApplicationStatus getToStatus() {
		return toStatus;
	}

	public void setToStatus(JobApplicationStatus toStatus) {
		this.toStatus = toStatus;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
}
