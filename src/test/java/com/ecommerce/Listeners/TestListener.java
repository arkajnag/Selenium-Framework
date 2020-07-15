package com.ecommerce.Listeners;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.ecommerce.baseClass.BaseDriverClass;
import com.ecommerce.utilities.CommonUtilities;



public class TestListener implements ITestListener {
	
	private Logger logger=LogManager.getLogger(TestListener.class);

	@Override
	public void onTestStart(ITestResult result) {
		logger.info("Test Execution for Test Method:"+result.getMethod().getMethodName()+" has started!!");

	}

	@Override
	public void onTestSuccess(ITestResult result) {
		logger.info("Test Execution for Test Method:"+result.getMethod().getMethodName()+" has ended successfully!!");

	}

	@Override
	public void onTestFailure(ITestResult result) {
		logger.fatal("Test Execution for Test Method:"+result.getMethod().getMethodName()+" has FAILED!!");
		Map<String,String> allParameters=result.getTestContext().getCurrentXmlTest().getAllParameters();
		File srcFile=((TakesScreenshot)BaseDriverClass.getDriver()).getScreenshotAs(OutputType.FILE);
		String screenShotPath="Screenshots"
		+File.separator+CommonUtilities.formattedDateString.apply("dd-MM-yyyy-hh-mm-ss")
		+File.separator+allParameters.get("locale")
		+File.separator+allParameters.get("browser")
		+File.separator+result.getMethod().getMethodName()+".png";
		try {
				FileUtils.copyFile(srcFile, new File(screenShotPath));
			} catch (IOException e) {
				e.printStackTrace();
		}

	}

	@Override
	public void onTestSkipped(ITestResult result) {
		logger.warn("Test Execution for Test Method:"+result.getMethod().getMethodName()+" has Skipped!!");
		logger.warn("Exception Resaon:"+result.getThrowable());
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		

	}

	@Override
	public void onStart(ITestContext context) {
		logger.info("Test Execution of Suite:"+context.getSuite().getName()+" has started!!");

	}

	@Override
	public void onFinish(ITestContext context) {
		logger.info("Test Execution of Suite:"+context.getSuite().getName()+" has ended!!");

	}

}
