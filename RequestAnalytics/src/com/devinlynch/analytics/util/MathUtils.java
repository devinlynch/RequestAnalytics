package com.devinlynch.analytics.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MathUtils {
	
	public static double calculateAverage(List<Integer> marks) {
		  Integer sum = 0;
		  if(!marks.isEmpty()) {
		    for (Integer mark : marks) {
		        sum += mark;
		    }
		    return sum.doubleValue() / marks.size();
		  }
		  return sum;
	}
	
	public static int calculateMedian(List<Integer> marks) {
		List<Integer> temp = new ArrayList<Integer>(marks);
		Collections.sort(temp);
		if(temp.isEmpty())
			return -1;
		
		return temp.get(temp.size()/2);
	}
	
}
