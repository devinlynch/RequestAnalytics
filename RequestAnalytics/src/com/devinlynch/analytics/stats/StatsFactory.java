package com.devinlynch.analytics.stats;

import org.hibernate.SessionFactory;
import org.hibernate.stat.Statistics;

import com.devinlynch.analytics.RequestAnalytics;

public class StatsFactory {

	/**
	 * Creates a {@link Stats} object given a Hibernate {@link SessionFactory} and {@link RequestAnalytics}
	 * @param sessionFactory
	 * @param requestAnalytics
	 * @return
	 */
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
		newStats.setTotalRequestTime(requestAnalytics.getTotalRequestTime());
		
		return newStats;
	}
	
	/**
	 * Resets all Hibernate statistics and {@link RequestAnalytics} analytics
	 * @param sessionFactory
	 * @param requestAnalytics
	 */
	public static void resetStatistics(SessionFactory sessionFactory, RequestAnalytics requestAnalytics) {
		requestAnalytics.reset();
		sessionFactory.getStatistics().clear();
	}
}
