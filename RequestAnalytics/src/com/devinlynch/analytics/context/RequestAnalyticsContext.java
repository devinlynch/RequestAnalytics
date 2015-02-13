package com.devinlynch.analytics.context;

/**
 * Implementations of this handle storing request times per URL and being able
 * to retrieve statistics of these stored values.
 * @author devinlynch
 *
 */
public interface RequestAnalyticsContext {
	public void saveRequestTime(String url, int time);
	public double getAverageRequestTime(String url);
	public long getMedianRequestTime(String url);
}
