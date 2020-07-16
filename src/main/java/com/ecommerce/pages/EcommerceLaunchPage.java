package com.ecommerce.pages;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.function.Supplier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import com.ecommerce.baseClass.BaseDriverClass;
import com.ecommerce.utilities.WebActionUtilities;


public class EcommerceLaunchPage {

	private Logger logger = LogManager.getLogger(EcommerceLaunchPage.class);

	By txt_SearchField_By_ID = By.id("search_query_top");
	By btn_SearchField_By_CSS = By.cssSelector(".button-search");
	By link_SignIn_By_LinkText = By.linkText("Sign in");

	// Function to Click on Sign In Link Text to navigate to Authentication
	// Page.
	// Return a Map of having Key: Page Url and Object of
	// EcommerceAuthenticatePage
	
	public Supplier<Map<String, Object>> fnc_ClickOnSignInLinkText = () -> {
		try {
			logger.info("Test Method to Click On Sign In Link started");
			Map<String, Object> mapResponseObjects = new HashMap<String, Object>();
			WebActionUtilities.clickOnElement.accept(link_SignIn_By_LinkText);
			TimeUnit.SECONDS.sleep(3);
			mapResponseObjects.put("CurrentPageUrl", BaseDriverClass.getDriver().getCurrentUrl());
			mapResponseObjects.put("AuthenticatePageObject", new EcommerceAuthenticatePage());
			return mapResponseObjects;
		} catch (Exception e) {
			logger.fatal("Test Method to Click On Sign In Link has stopped due to failure. Reason:" + e.getMessage());
			throw new RuntimeException(e.fillInStackTrace());
		} finally {
			logger.info("Test Method to Click On Sign In Link ended");
		}
	};
	
	public Function<String,String> fnc_SearchItem=itemToSearch-> {
			try{
					logger.info("Test Method to Search New Item started");
					WebActionUtilities.sendKeysToElement.accept(txt_SearchField_By_ID, itemToSearch);
					WebActionUtilities.clickOnElement.accept(btn_SearchField_By_CSS);
					return BaseDriverClass.getDriver().getCurrentUrl();
			}catch(Exception e){
				logger.fatal("Test Method to Search New Item has stopped due to failure. Reason:" + e.getMessage());
				throw new RuntimeException(e.getMessage());
			}finally{
				logger.info("Test Method to Search New Item ended");
		}
	};

}
