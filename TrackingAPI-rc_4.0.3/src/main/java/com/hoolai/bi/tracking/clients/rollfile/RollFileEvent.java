package com.hoolai.bi.tracking.clients.rollfile;

public class RollFileEvent {

	private final String category;
	
	private final String event;
	
	private final String body;
	
	public RollFileEvent(String category, String event, String body) {
		super();
		this.category = category;
		this.event = event;
		this.body = body;
	}

	public String getCategory() {
		return category;
	}

	public String getEvent() {
		return event;
	}

	public String getBody() {
		return body;
	}
	
	
	
}
