package com.devinlynch.analytics;

import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import com.devinlynch.analytics.context.LoggingContext;
import com.devinlynch.analytics.context.RequestAnalyticsContext;
import com.devinlynch.analytics.context.SystemLoggingContext;

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
		getLoggingContext().log("REQUEST ["+id+"] ENDED - TOOK ["+time+"ms] - AVERAGE ["+context.getAverageRequestTime(url)+"ms] MEDIAN ["+context.getMedianRequestTime(url)+"ms]");
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

}
