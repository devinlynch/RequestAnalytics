package com.devinlynch.analytics.stats;

import com.devinlynch.analytics.RequestAnalytics;

/**
 * Contains statistics about request times of the server calculated and logged
 * using {@link RequestAnalytics} and also has information about database 
 * statistics.
 * @author devinlynch
 *
 */
public class Stats {
	private long databaseQueryExecutionCount;
	private long databaseQueryExecutionMaxTime;
	private long databaseQueryCacheHitCount;
	private String slowestDatabaseQuery;
	private long medianUrlRequestTime;
	private double meanUrlRequestTime;
	private String slowestUrlRequest;
	private long slowestUrlRequestMedianTime;
	private String fastestUrlRequest;
	private long fastestUrlRequestMedianTime;
	private long totalRequestTime;
	private long databaseNumberOfPreparedStatements;
	
	public long getDatabaseQueryExecutionCount() {
		return databaseQueryExecutionCount;
	}
	public void setDatabaseQueryExecutionCount(long databaseQueryExecutionCount) {
		this.databaseQueryExecutionCount = databaseQueryExecutionCount;
	}
	public long getDatabaseQueryExecutionMaxTime() {
		return databaseQueryExecutionMaxTime;
	}
	public void setDatabaseQueryExecutionMaxTime(long databaseQueryExecutionMaxTime) {
		this.databaseQueryExecutionMaxTime = databaseQueryExecutionMaxTime;
	}
	public long getDatabaseQueryCacheHitCount() {
		return databaseQueryCacheHitCount;
	}
	public void setDatabaseQueryCacheHitCount(long databaseQueryCacheHitCount) {
		this.databaseQueryCacheHitCount = databaseQueryCacheHitCount;
	}
	public String getSlowestDatabaseQuery() {
		return slowestDatabaseQuery;
	}
	public void setSlowestDatabaseQuery(String slowestDatabaseQuery) {
		this.slowestDatabaseQuery = slowestDatabaseQuery;
	}
	public long getMedianUrlRequestTime() {
		return medianUrlRequestTime;
	}
	public void setMedianUrlRequestTime(long medianUrlRequestTime) {
		this.medianUrlRequestTime = medianUrlRequestTime;
	}
	public double getMeanUrlRequestTime() {
		return meanUrlRequestTime;
	}
	public void setMeanUrlRequestTime(double meanUrlRequestTime) {
		this.meanUrlRequestTime = meanUrlRequestTime;
	}
	public String getSlowestUrlRequest() {
		return slowestUrlRequest;
	}
	public void setSlowestUrlRequest(String slowestUrlRequest) {
		this.slowestUrlRequest = slowestUrlRequest;
	}
	public String getFastestUrlRequest() {
		return fastestUrlRequest;
	}
	public void setFastestUrlRequest(String fastestUrlRequest) {
		this.fastestUrlRequest = fastestUrlRequest;
	}
	public long getFastestUrlRequestMedianTime() {
		return fastestUrlRequestMedianTime;
	}
	public void setFastestUrlRequestMedianTime(long fastestUrlRequestMedianTime) {
		this.fastestUrlRequestMedianTime = fastestUrlRequestMedianTime;
	}
	public long getSlowestUrlRequestMedianTime() {
		return slowestUrlRequestMedianTime;
	}
	public void setSlowestUrlRequestMedianTime(long slowestUrlRequestMedianTime) {
		this.slowestUrlRequestMedianTime = slowestUrlRequestMedianTime;
	}
	public long getTotalRequestTime() {
		return totalRequestTime;
	}
	public void setTotalRequestTime(long totalRequestTime) {
		this.totalRequestTime = totalRequestTime;
	}
	public long getDatabaseNumberOfPreparedStatements() {
		return databaseNumberOfPreparedStatements;
	}
	public void setDatabaseNumberOfPreparedStatements(
			long databaseNumberOfPreparedStatements) {
		this.databaseNumberOfPreparedStatements = databaseNumberOfPreparedStatements;
	}
	
	
}
