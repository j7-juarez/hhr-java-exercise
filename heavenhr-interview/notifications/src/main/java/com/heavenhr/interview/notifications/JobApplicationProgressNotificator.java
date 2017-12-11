package com.heavenhr.interview.notifications;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.heavenhr.interview.model.JobApplicationStatus;

@Service
public class JobApplicationProgressNotificator {

	@Autowired
	private JobApplicationProgressNotificationSender notificationSender;
	
	public void notifyJobApplicationProgress(JobApplicationStatus status, String candidateEmail, String jobTitle) {
		String notificationMessage = buildMessageFromStatus(status, jobTitle);
		
		if(notificationMessage != null)
			notificationSender.sendNotification(notificationMessage, candidateEmail);
	}

	private String buildMessageFromStatus(JobApplicationStatus status, String jobTitle) {
		switch (status) {
		case APPLIED:
			return null;
		case HIRED:
			return "Congratulations!. You just were hired for position " + jobTitle;
		case INVITED:
			return "We would like to invite you to an interview about your application for " + jobTitle;
		case REJECTED:
			return "Thanks for your application. We already fill the position " + jobTitle + ", but want to keep you on the map.";
		default:
			return null;
		}
	}

	public JobApplicationProgressNotificationSender getNotificationSender() {
		return notificationSender;
	}

	public void setNotificationSender(JobApplicationProgressNotificationSender notificationSender) {
		this.notificationSender = notificationSender;
	}
}
