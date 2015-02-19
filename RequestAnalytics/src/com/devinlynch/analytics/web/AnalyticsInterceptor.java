package com.devinlynch.analytics.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.devinlynch.analytics.RequestAnalytics;

public class AnalyticsInterceptor extends HandlerInterceptorAdapter {
	private RequestAnalytics analytics;
	private static String ANALYTICS_IGNORE = "ANALYTICS_IGNORE";
	
	public AnalyticsInterceptor() {
		analytics = RequestAnalytics.getPersistedInstance();
	}
	
	
	@Override
	public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler) throws Exception {
		if(shouldNotLog(request)) {
			return true;
		}
		analytics.logRequestStarted(request);
			
		return true;
    }
	
	@Override
	public void postHandle(
			HttpServletRequest request, 
			HttpServletResponse response, 
			Object handler, 
			ModelAndView modelAndView) {
		if(shouldNotLog(request)) {
			return;
		}
		analytics.logRequestFinished(request);
	}
	
	public boolean shouldNotLog(HttpServletRequest request) {
		return request.getParameter(ANALYTICS_IGNORE) != null && request.getParameter(ANALYTICS_IGNORE).equals("true");
	}
}
