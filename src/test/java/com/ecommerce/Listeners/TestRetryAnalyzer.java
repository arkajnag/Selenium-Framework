package com.ecommerce.Listeners;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class TestRetryAnalyzer implements IRetryAnalyzer {
	private int initialCount=0;
	private int finalCount=2;
	@Override
	public boolean retry(ITestResult result) {
		if(initialCount<finalCount){
			initialCount++;
			return true;
		}
		return false;
	}
}
