package com.ecommerce.testcases;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import com.ecommerce.baseClass.BaseDriverClass;

public class BaseTest extends BaseDriverClass{

	
	@Parameters({"browser","locale"})
	@BeforeMethod
	public void setUpTestCase(String browser,String locale){
		setUpDriver.accept(browser, locale);
	}
	
	@AfterMethod
	public void tearDownTestCase(){
		tearDownDriver.accept(getDriver());
	}
}
