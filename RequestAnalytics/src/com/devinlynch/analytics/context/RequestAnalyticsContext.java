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
	public void saveRequestTime(String url, int time);
	public Map<String, List<Integer>> getRequestTimes();
}
