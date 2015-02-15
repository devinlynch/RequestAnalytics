package com.devinlynch.analytics;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import com.devinlynch.analytics.context.LoggingContext;
import com.devinlynch.analytics.context.PersistedFileAnalyticsContext;
import com.devinlynch.analytics.context.RequestAnalyticsContext;
import com.devinlynch.analytics.context.SystemLoggingContext;
import com.devinlynch.analytics.util.MathUtils;

/**
 * Used for logging when a {@link HttpServletRequest} starts being processed and when it finishes.  Call
 * {@link RequestAnalytics#logRequestStarted(HttpServletRequest)} when the request first starts being processed
 * and call {@link RequestAnalytics#logRequestFinished(HttpServletRequest)} when it is finished.
 * @author devinlynch
 *
 */
public class RequestAnalytics {
	private int idCounter = 0;
	private static final String SESSION_ATTRIBUTE_NAME_ID = "__COM.DEVINLYNCH.ANALYTICS.REQUEST_ANALYTICS_ID__";
	private static final String SESSION_ATTRIBUTE_NAME_START_TIME = "__COM.DEVINLYNCH.ANALYTICS.REQUEST_ANALYTICS_START_TIME__";
	private RequestAnalyticsContext context;
	private LoggingContext loggingContext;
	
	public RequestAnalytics(RequestAnalyticsContext context) {
		if(context == null)
			throw new RuntimeException("Context cannt be null");
		this.context = context;
	}
	
	private static RequestAnalytics _instance;
	private static Object monitor = new Object();
	public static RequestAnalytics getPersistedInstance() {
		synchronized (monitor) {
			if(_instance == null) {
				_instance = new RequestAnalytics(new PersistedFileAnalyticsContext());
			}
		}
		return _instance;
	}
	
	public void logRequestStarted(HttpServletRequest request) {
		String id = getNewId();
		Long startTime = new Date().getTime();
		request.setAttribute(SESSION_ATTRIBUTE_NAME_ID, id);
		request.setAttribute(SESSION_ATTRIBUTE_NAME_START_TIME, startTime);

		String url = getFullURL(request);
		getLoggingContext().log("REQUEST ["+id+"] STARTED - URL ["+url+"]");
	}
	
	public void logRequestFinished(HttpServletRequest request) {
		String id = (String) request.getAttribute(SESSION_ATTRIBUTE_NAME_ID);
		if(id == null)
		 	return;
		
		long startTime = (Long) request.getAttribute(SESSION_ATTRIBUTE_NAME_START_TIME);
		long time = new Date().getTime() - startTime;
		String url = getFullURL(request);

		context.saveRequestTime(url, (int)time);
		getLoggingContext().log("REQUEST ["+id+"] ENDED - TOOK ["+time+"ms] - AVERAGE ["+getAverageRequestTime(url)+"ms] MEDIAN ["+getMedianRequestTime(url)+"ms]");
	}
	
	private static String getFullURL(HttpServletRequest request) {
	    StringBuffer requestURL = request.getRequestURL();
	    String queryString = request.getQueryString();

	    if (queryString == null) {
	        return requestURL.toString();
	    } else {
	        return requestURL.append('?').append(queryString).toString();
	    }
	}
	
	private synchronized String getNewId() {
		idCounter++;
		return (new Date()).getTime() + "-"+ idCounter + "-" + UUID.randomUUID().toString();
	}

	public LoggingContext getLoggingContext() {
		if(loggingContext == null)
			return new SystemLoggingContext();
		return loggingContext;
	}

	public void setLoggingContext(LoggingContext loggingContext) {
		this.loggingContext = loggingContext;
	}
	
	public double getAverageRequestTime(String url) {
		List<Integer> list = context.getRequestTimes().get(url);
		if(list == null)
			return -1;
		
		return MathUtils.calculateAverage(list);
	}
	
	public int getMedianRequestTime(String url) {
		List<Integer> list = context.getRequestTimes().get(url);
		if(list == null)
			return -1;
		
		return MathUtils.calculateMedian(list);
	}
	
	public double getAverageRequestTime() {
		return MathUtils.calculateAverage(getAllRequestTimes());
	}
	public int getMedianRequestTime(){
		return MathUtils.calculateMedian(getAllRequestTimes());
	}
	public String getSlowestRequestUrl(){
		int slowestRequestTime = 0;
		String slowestRequestUrl = null;
		for(Entry<String, Integer> e: getBreakdownOfRequestUrlsToMedianRequestTimes().entrySet()) {
			int median = e.getValue();
			if(median > slowestRequestTime) {
				slowestRequestTime = median;
				slowestRequestUrl = e.getKey();
			}
		}
		return slowestRequestUrl;
	}
	public int getSlowestRequestTime(){
		String slowestRequestUrl = getSlowestRequestUrl();
		if(slowestRequestUrl == null)
			return -1;
		
		List<Integer> list = context.getRequestTimes().get(slowestRequestUrl);
		if(list == null)
			return -1;
		return MathUtils.calculateMedian(list);
	}
	private List<Integer> getAllRequestTimes() {
		List<Integer> allRequestTimes = new ArrayList<Integer>();
		for(Entry<String, List<Integer>> e: context.getRequestTimes().entrySet()) {
			allRequestTimes.addAll(e.getValue());
		}
		return allRequestTimes;
	}
	
	public String getFastestRequestUrl(){
		int fastestRequestTime = Integer.MAX_VALUE;
		String fastestRequestUrl = null;
		for(Entry<String, Integer> e: getBreakdownOfRequestUrlsToMedianRequestTimes().entrySet()) {
			int median = e.getValue();
			if(median < fastestRequestTime) {
				fastestRequestTime = median;
				fastestRequestUrl = e.getKey();
			}
		}
		return fastestRequestUrl;
	}
	public int getFastestRequestTime(){
		String fastestRequestUrl = getFastestRequestUrl();
		if(fastestRequestUrl == null)
			return -1;
		
		List<Integer> list = context.getRequestTimes().get(fastestRequestUrl);
		if(list == null)
			return -1;
		return MathUtils.calculateMedian(list);
	}
	
	public Map<String, Integer> getBreakdownOfRequestUrlsToMedianRequestTimes() {
		Map<String, Integer> map = new HashMap<String, Integer>();
		for(Entry<String, List<Integer>> e: context.getRequestTimes().entrySet()) {
			int median = MathUtils.calculateMedian(e.getValue());
			map.put(e.getKey(), median);
		}
		return map;
	}

}
