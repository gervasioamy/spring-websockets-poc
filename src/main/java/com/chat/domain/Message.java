package com.chat.domain;

/**
 * Defined to read messages from the client.
 * 
 * @author gervasio.amy
 */
public class Message {

	private String from;
	private String text;
	
	public Message() {
	}

	public Message(String from, String text) {
		this();
		this.from = from;
		this.text = text;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
