package com.devinlynch.analytics.hibernate;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.hibernate.Interceptor;

public class StatsHibernateInterceptor implements InvocationHandler {
	private Interceptor interceptor;
	private static long numberOfQueries = 0;
	private static Object monitor = new Object();
	
	/**
	 * Wraps a Hibernate interceptor for monitoring of prepared statements to be able to count
	 * the total number of prepared statements.
	 * @param i
	 * @return
	 */
	public static Interceptor wrapHibernateInterceptorForStats(Interceptor i) {
		StatsHibernateInterceptor s = new StatsHibernateInterceptor();
		s.interceptor = i;
		return (Interceptor) Proxy.newProxyInstance(i.getClass().getClassLoader(), new Class[]{Interceptor.class}, s);
	}
	
	public static long getNumberOfPreparedStatements() {
		return numberOfQueries;
	}
	
	public static void resetPreparedStatementCount() {
		synchronized (monitor) {
			numberOfQueries = 0;
		}
	}
	
	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		
		if(method.getName().equals("onPrepareStatement")) {
			synchronized (monitor) {
				numberOfQueries++;
			}
		}

		return method.invoke(interceptor, args);
	}
	
}
