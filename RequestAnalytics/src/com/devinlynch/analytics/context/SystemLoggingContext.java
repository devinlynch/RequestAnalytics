package com.devinlynch.analytics.context;

/**
 * Just does a System.out.println() for logging.
 * @author devinlynch
 *
 */
public class SystemLoggingContext implements LoggingContext {

	@Override
	public void log(String message) {
		System.out.println(message);
	}

}
