package com.firebase.dto;

public class NotificationTemplate {

	private String to;
	

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	private FirebaseNotification notification;

	public FirebaseNotification getNotification() {
		return notification;
	}

	public void setNotification(FirebaseNotification notification) {
		this.notification = notification;
	}
}
