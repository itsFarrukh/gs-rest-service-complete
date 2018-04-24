package com.firebase.dto;

public class FirebaseMessage {
	private String topic;
	private FirebaseNotification notification;
	public String getTopic() {
		return topic;
	}
	public void setTopic(String topic) {
		this.topic = topic;
	}
	public FirebaseNotification getNotification() {
		return notification;
	}
	public void setNotification(FirebaseNotification notification) {
		this.notification = notification;
	}

}
