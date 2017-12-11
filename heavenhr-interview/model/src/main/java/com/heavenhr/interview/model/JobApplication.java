package com.heavenhr.interview.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "jobapplication", uniqueConstraints = {
		@UniqueConstraint(columnNames = {"jobofferid", "candidateemail"})
})
public class JobApplication {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "jobofferid")
	private JobOffer jobOffer;
	
	@Column(name = "candidateemail")
	private String candidateEmail;
	
	@Column(length=4096)
	private String resumeText;
	
	@Temporal(TemporalType.DATE)
	private Date created;
	
	private JobApplicationStatus status;
	
	@Temporal(TemporalType.DATE)
	private Date updated;
	
	@OneToMany(mappedBy = "jobApplication", fetch = FetchType.LAZY)
	private List<JobApplicationStatusChange> statusChanges;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCandidateEmail() {
		return candidateEmail;
	}

	public void setCandidateEmail(String candidateEmail) {
		this.candidateEmail = candidateEmail;
	}

	public JobOffer getJobOffer() {
		return jobOffer;
	}

	public void setJobOffer(JobOffer jobOffer) {
		this.jobOffer = jobOffer;
	}

	public String getResumeText() {
		return resumeText;
	}

	public void setResumeText(String resumeText) {
		this.resumeText = resumeText;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public JobApplicationStatus getStatus() {
		return status;
	}

	public void setStatus(JobApplicationStatus status) {
		this.status = status;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	public List<JobApplicationStatusChange> getStatusChanges() {
		return statusChanges;
	}

	public void setStatusChanges(List<JobApplicationStatusChange> statusChanges) {
		this.statusChanges = statusChanges;
	}
	
}
