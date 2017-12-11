package com.heavenhr.interview.notifications;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class LogJobApplicationProgressNotificationSender implements JobApplicationProgressNotificationSender {

	private static final Logger logger = LogManager.getLogger("JobApplicationProgressNotificationLog");
	
	@Override
	public void sendNotification(String message, String email) {
		logger.trace(email + "::" + message);
	}

}
