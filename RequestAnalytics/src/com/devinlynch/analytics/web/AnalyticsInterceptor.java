package com.devinlynch.analytics.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.devinlynch.analytics.RequestAnalytics;
import com.devinlynch.analytics.context.PersistedFileAnalyticsContext;

public class AnalyticsInterceptor extends HandlerInterceptorAdapter {
	private RequestAnalytics analytics;
	
	public AnalyticsInterceptor() {
		analytics = new RequestAnalytics(new PersistedFileAnalyticsContext());
	}
	
	
	@Override
	public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler) throws Exception {
		analytics.logRequestStarted(request);
			
		return true;
    }
	
	@Override
	public void postHandle(
			HttpServletRequest request, 
			HttpServletResponse response, 
			Object handler, 
			ModelAndView modelAndView) {
		
		analytics.logRequestFinished(request);
	}
}
