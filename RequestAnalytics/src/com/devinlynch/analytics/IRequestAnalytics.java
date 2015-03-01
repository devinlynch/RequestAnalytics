package com.devinlynch.analytics;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.devinlynch.analytics.context.LoggingContext;

public interface IRequestAnalytics {

	/**
	 * Call this when a {@link HttpServletRequest} is beginning to be processed
	 * @param request
	 */
	public abstract void logRequestStarted(HttpServletRequest request);

	/**
	 * Call this when a {@link HttpServletRequest} is finished being processed
	 * @param request
	 */
	public abstract void logRequestFinished(HttpServletRequest request);

	/**
	 * Returns the average request time for the given URL
	 * @param url
	 * @return
	 */
	public abstract double getAverageRequestTime(String url);

	/**
	 * Returns the median request time for the given URL
	 * @param url
	 * @return
	 */
	public abstract int getMedianRequestTime(String url);

	/**
	 * Returns the average request time of all URLs logged
	 * @return
	 */
	public abstract double getAverageRequestTime();

	/**
	 * Returns the median request time of all URLs logged
	 * @return
	 */
	public abstract int getMedianRequestTime();

	/**
	 * Returns the URL of that has the slowest median request time
	 * @return
	 */
	public abstract String getSlowestRequestUrl();

	/**
	 * Returns the time of the slowest median request time
	 * @return
	 */
	public abstract int getSlowestRequestTime();

	/**
	 * Returns the URL that has the fastest median request time
	 * @return
	 */
	public abstract String getFastestRequestUrl();

	/**
	 * Returns the time of the fastest median request time
	 * @return
	 */
	public abstract int getFastestRequestTime();

	/**
	 * Returns a map with the keys being all logged URLs and the values being the median request
	 * times of those URLs
	 * @return
	 */
	public abstract Map<String, Integer> getBreakdownOfRequestUrlsToMedianRequestTimes();

	/**
	 * Deletes all stored request times.  <b>WARNING:</b> this is not 
	 * reversible
	 */
	public abstract void reset();

	public abstract LoggingContext getLoggingContext();

	public abstract long getTotalRequestTime();

}