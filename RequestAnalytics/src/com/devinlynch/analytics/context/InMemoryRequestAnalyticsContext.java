package com.devinlynch.analytics.context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.devinlynch.analytics.util.MathUtils;

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
	public double getAverageRequestTime(String url) {
		List<Integer> list = map.get(url);
		if(list == null)
			return -1;
		
		return MathUtils.calculateAverage(list);
	}
	
	@Override
	public long getMedianRequestTime(String url) {
		List<Integer> list = map.get(url);
		if(list == null)
			return -1;
		
		return MathUtils.calculateMedian(list);
	}
	
	@Override
	public void saveRequestTime(String url, int time) {
		synchronized (map) {
			if(!map.containsKey(url))
				map.put(url, new ArrayList<Integer>());
		}
		map.get(url).add(time);
	}

}
