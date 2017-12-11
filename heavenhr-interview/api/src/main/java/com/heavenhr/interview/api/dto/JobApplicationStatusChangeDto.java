package com.heavenhr.interview.api.dto;

import java.util.Date;

import com.heavenhr.interview.model.JobApplicationStatus;
import com.heavenhr.interview.model.JobApplicationStatusChange;

public class JobApplicationStatusChangeDto {

	private long id;
	
	private JobApplicationStatus fromStatus;
	
	private JobApplicationStatus toStatus;
	
	private Date date;

	public static JobApplicationStatusChangeDto fromJobApplicationStatusChange(JobApplicationStatusChange statusChange){
		JobApplicationStatusChangeDto dto = new JobApplicationStatusChangeDto();
		
		dto.setDate(statusChange.getDate());
		dto.setId(statusChange.getId());
		dto.setFromStatus(statusChange.getFromStatus());
		dto.setToStatus(statusChange.getToStatus());
		
		return dto;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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
