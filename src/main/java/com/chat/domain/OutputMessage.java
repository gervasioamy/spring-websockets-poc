package com.chat.domain;

import java.util.Date;

/**
 * Output message to be sent back to the client
 * @author gervasio.amy
 */
public class OutputMessage {

	private String from;
    private String message;
    private String topic;
    private Date time;
    
    public OutputMessage() {
	}
    
	public OutputMessage(String from, String message, String topic) {
		this(from, message, topic, new Date());
	}
	
	public OutputMessage(String from, String message, String topic, Date time) {
		this();
		this.from = from;
		this.message = message;
		this.topic = topic;
		this.time = time;
	}
	
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getTopic() {
		return topic;
	}
	public void setTopic(String topic) {
		this.topic = topic;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
    
}
