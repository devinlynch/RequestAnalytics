package com.devinlynch.analytics.stats;

import org.hibernate.SessionFactory;
import org.hibernate.stat.Statistics;

import com.devinlynch.analytics.RequestAnalytics;

public class StatsFactory {

	public static Stats createStats(SessionFactory sessionFactory, RequestAnalytics requestAnalytics) {
		Statistics statistics = sessionFactory.getStatistics();
		
		Stats newStats = new Stats();
		newStats.setDatabaseQueryCacheHitCount(statistics.getQueryCacheHitCount());
		newStats.setDatabaseQueryExecutionCount(statistics.getQueryExecutionCount());
		newStats.setDatabaseQueryExecutionMaxTime(statistics.getQueryExecutionMaxTime());
		newStats.setSlowestDatabaseQuery(statistics.getQueryExecutionMaxTimeQueryString());
		
		newStats.setMeanUrlRequestTime(requestAnalytics.getAverageRequestTime());
		newStats.setMedianUrlRequestTime(requestAnalytics.getMedianRequestTime());
		newStats.setSlowestUrlRequest(requestAnalytics.getSlowestRequestUrl());
		newStats.setSlowestUrlRequestMedianTime(requestAnalytics.getSlowestRequestTime());
		newStats.setFastestUrlRequest(requestAnalytics.getFastestRequestUrl());
		newStats.setFastestUrlRequestMedianTime(requestAnalytics.getFastestRequestTime());
		
		return newStats;
	}
}
