package com.hoolai.bi.collectdata.client.metrics;

public interface ITracker {

	/**
	 * 
	 * @return event type of each log
	 */
	public String get_event();

	/**
	 * 
	 * @return (category, messages) which needs logged
	 */
	public String get_messages();

}
