package com.hoolai.bi.tracking.service.impl;

import com.hoolai.bi.tracking.clients.rollfile.RollFileClient;
import com.hoolai.bi.tracking.service.TrackingService;

public class RollFileTrackingServiceImpl implements TrackingService {
	
	private RollFileClient rollFileClient;

	public RollFileTrackingServiceImpl() {
		super();
		this.rollFileClient=new RollFileClient();
	}

	@Override
	public boolean send(String category, String event, String body) {
		return this.rollFileClient.send(category, event, body);
	}

}
