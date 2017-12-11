package com.heavenhr.interview.notifications;

public interface JobApplicationProgressNotificationSender {

	public void sendNotification(String message, String email);
	
}
