package com.heavenhr.interview.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
@Table(name = "joboffer")
public class JobOffer {

	public static final String APPLICATIONS_COUNT_BASE_CACHE_KEY = "applicationscount:";
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique = true)
	private String title;
	
	@Temporal(TemporalType.DATE)
	private Date startDate;
	
	@OneToMany(mappedBy = "jobOffer", fetch = FetchType.LAZY)
	private List<JobApplication> applications;
	
	@Transient
	private int numberOfApplications;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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

	public List<JobApplication> getApplications() {
		return applications;
	}

	public void setApplications(List<JobApplication> applications) {
		this.applications = applications;
	}

	public int getNumberOfApplications() {
		return numberOfApplications;
	}

	public void setNumberOfApplications(int numberOfApplications) {
		this.numberOfApplications = numberOfApplications;
	}
	
	public String getApplicationsCountCacheKey() {
		return APPLICATIONS_COUNT_BASE_CACHE_KEY + id;
	}
}
