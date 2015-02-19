package com.devinlynch.analytics.context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Stores the values in memory, nothing is persisted.
 * @author devinlynch
 *
 */
public class InMemoryRequestAnalyticsContext implements RequestAnalyticsContext {
	protected Map<String, List<Integer>> map;
	
	public InMemoryRequestAnalyticsContext() {
		this.map = new HashMap<String, List<Integer>>();
	}
	
	@Override
	public void saveRequestTime(String url, int time) {
		synchronized (map) {
			if(!map.containsKey(url))
				map.put(url, new ArrayList<Integer>());
		}
		map.get(url).add(time);
	}

	@Override
	public Map<String, List<Integer>> getRequestTimes() {
		return map;
	}

	@Override
	public void reset() {
		map.clear();
	}

}
