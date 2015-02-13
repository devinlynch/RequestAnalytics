package com.devinlynch.analytics.context;

/**
 * Handles logging when a request is started or finished. 
 * @author devinlynch
 *
 */
public interface LoggingContext {
	public void log(String message);
}
