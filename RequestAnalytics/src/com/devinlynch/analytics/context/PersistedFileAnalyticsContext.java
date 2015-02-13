package com.devinlynch.analytics.context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.Map;

/**
 * Will persist the times of requests to a file specified in the constructor.
 * @author devinlynch
 *
 */
public class PersistedFileAnalyticsContext extends InMemoryRequestAnalyticsContext {
	private String filePath;
	private static Persister PERSISTER;
	private static Object PERSISTER_MONITOR = new Object();
	private long millisecondWaitBetweenPersists;
	private static Boolean MAP_CHANGED = false;
	
	public PersistedFileAnalyticsContext(String filePath, long millisecondWaitBetweenPersists) {
		super();
		try {
			this.millisecondWaitBetweenPersists = millisecondWaitBetweenPersists;
			this.filePath = filePath;
			File file = new File(filePath);
		    FileInputStream f = new FileInputStream(file);
		    ObjectInputStream s = new ObjectInputStream(f);
		    @SuppressWarnings("unchecked")
			Map<String, List<Integer>> map = (Map<String, List<Integer>>) s.readObject();
		    this.map = map;
		    s.close();
		    synchronized (PERSISTER_MONITOR) {
		    	if(PERSISTER == null) {
		    		PERSISTER = new Persister();
		    		PERSISTER.start();
		    	}
			}
		} catch(Exception e) {
			System.out.println("Unable to read in existing file ["+filePath+"]");
		}
		
	}
	
	@Override
	public void saveRequestTime(String url, int time) {
		MAP_CHANGED = true;
		super.saveRequestTime(url, time);
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
	private class Persister extends Thread {
		@Override 
		public void run() {
			while(true) {
				try{
					if(MAP_CHANGED) {
						File file = new File(filePath);
				        FileOutputStream f = new FileOutputStream(file, false);
				        ObjectOutputStream s = new ObjectOutputStream(f);
				        s.writeObject(map);
				        s.close();
				        System.out.println("Persisted Map");
				        MAP_CHANGED = false;
					}
				} catch(Exception e) {
					e.printStackTrace();
				}
				try {
					Thread.sleep(millisecondWaitBetweenPersists);
				} catch (InterruptedException e) {
					break;
				}
				
			}
		}
	}
}
