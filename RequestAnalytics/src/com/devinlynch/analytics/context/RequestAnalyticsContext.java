package com.devinlynch.analytics.context;

import java.util.List;
import java.util.Map;

/**
 * Implementations of this handle storing request times per URL and being able
 * to retrieve statistics of these stored values.
 * @author devinlynch
 *
 */
public interface RequestAnalyticsContext {
	/**
	 * Saves the request time for the given URL to the existing list of
	 * request times for that URL
	 * @param url
	 * @param time
	 */
	public void saveRequestTime(String url, int time);
	
	/**
	 * Returns all request times as a map with the keys being the URL's 
	 * and the values being a list of request times for it
	 * @return
	 */
	public Map<String, List<Integer>> getRequestTimes();
	
	/**
	 * Deletes all stored request times.  <b>WARNING:</b> this is not 
	 * reversible
	 */
	public void reset();
}
