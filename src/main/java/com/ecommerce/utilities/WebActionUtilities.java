package com.ecommerce.utilities;

import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import com.ecommerce.baseClass.BaseDriverClass;


public interface WebActionUtilities {
	
	static Logger logger=LogManager.getLogger(WebActionUtilities.class);
	
	//Fluent Wait for Element
	public static Function<By,WebElement> fluentElementWait=webLocator->{
		WebElement element=null;
		try{
			logger.info("FluentWait Method started");
			Wait<RemoteWebDriver> fluentWait=new FluentWait<>(BaseDriverClass.getDriver())
					.pollingEvery(Duration.ofSeconds(1))
					.withTimeout(Duration.ofSeconds(15))
					.ignoring(NoSuchElementException.class);
			element=fluentWait.until(driver->driver.findElement(webLocator));
		}catch(Exception e){
			logger.fatal("Failure in Fluent Wait for Element method. Exception:"+e.getMessage());
		}
		logger.info("FluentWait Method Ended");
		return element;	
	};
	
	//Fluent Wait for Elements
	public static Function<By,List<WebElement>> fluentElementsWait=webLocator->{
		Wait<RemoteWebDriver> fluentWait=new FluentWait<>(BaseDriverClass.getDriver())
				.pollingEvery(Duration.ofSeconds(1))
				.withTimeout(Duration.ofSeconds(15))
				.ignoring(NoSuchElementException.class);
		return fluentWait.until(driver->driver.findElements(webLocator));
	};
	
	//Fluent or Explicit Wait for Visibility of Element
	public static Function<By,WebElement> waitForElementToBeVisible=webLocator->{
			Wait<RemoteWebDriver> wait=new FluentWait<>(BaseDriverClass.getDriver())
					.pollingEvery(Duration.ofSeconds(1))
					.withTimeout(Duration.ofSeconds(15))
					.ignoring(NoSuchElementException.class);
			return wait.until(ExpectedConditions.visibilityOfElementLocated(webLocator));
	};
	
	public static Function<By,Boolean> waitForElementToBeInVisible=webLocator->{
		Wait<RemoteWebDriver> wait=new FluentWait<>(BaseDriverClass.getDriver())
				.pollingEvery(Duration.ofSeconds(1))
				.withTimeout(Duration.ofSeconds(15))
				.ignoring(NoSuchElementException.class);
		return wait.until(ExpectedConditions.invisibilityOfElementLocated(webLocator));
	};
	
	public static BiConsumer<By,String> sendKeysToElement=new BiConsumer<By,String>(){
		public void accept(By webLocator, String textToEnter) {
			fluentElementWait.apply(webLocator).sendKeys(textToEnter);
		}
	};
	
	public static Consumer<By> clickOnElement=webLocator->fluentElementWait.apply(webLocator).click();
	
	public static BiConsumer<By,Object> selectFromDropDown=(webLocator,optionToSelect)-> {
			if(optionToSelect instanceof String)
				new Select(fluentElementWait.apply(webLocator)).selectByVisibleText(optionToSelect.toString());
			else if(optionToSelect instanceof Integer)
				new Select(fluentElementWait.apply(webLocator)).selectByIndex(((Integer) optionToSelect).intValue());
	};

	public static BiFunction<By,String,Boolean> selectItemFromList=(webLocator,textToBeSelectedFromList)->{
			try{
					fluentElementsWait.apply(webLocator).parallelStream()
					.filter(webElement-> webElement.getText().equalsIgnoreCase(textToBeSelectedFromList))
					.findFirst()
					.get()
					.click();
					return true;
				}catch(Exception e){
				return false;
			}
	};

	
}
