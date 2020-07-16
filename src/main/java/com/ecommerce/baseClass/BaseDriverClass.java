package com.ecommerce.baseClass;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.ecommerce.utilities.CommonUtilities;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseDriverClass {

	//Creating the ThreadLocal variables for maintaining Thread Safety.
	private static Logger logger = LogManager.getLogger(BaseDriverClass.class);
	private static ThreadLocal<RemoteWebDriver> driver = new ThreadLocal<RemoteWebDriver>();
	private static ThreadLocal<Properties> props = new ThreadLocal<Properties>();

	// Getters and Setters
	public static void setDriver(RemoteWebDriver driver1) {
		driver.set(driver1);
	}

	public static void setProps(Properties props1) {
		props.set(props1);
	}

	public static RemoteWebDriver getDriver() {
		return driver.get();
	}

	public static Properties getProps() {
		return props.get();
	}
	
	//Functional Interface to setUp the Driver based on @Parameters passed for Browser (Chrome or Firefox) and Locale (Remote or Localhost)
	//Using WebDriverManager for setting up the Localhost ChromeDriver and FirefoxDriver instances.
	//Using ThreadLocal<RemoteWebDriver> and ThreadLocal<Properties> for Global Variables to ensure Thread-safe behavior during Multi-threading.
	//Docker-Compose.yml to set up Selenium Grid in Local Docker Containers for Parallel Execution
	//LambdaTest for setting up Cloud Testing
	public static BiConsumer<String, String> setUpDriver = (browserName, locale)-> {
			try {
				String logfilePath="Logs"+File.separator+CommonUtilities.formattedDateString.apply("dd-MM-yyyy-hh-mm-ss")+File.separator+locale+File.separator+browserName;
				File logFile=new File(logfilePath);
				synchronized (logFile) {
					if(!logFile.exists())
						logFile.mkdirs();
				}
				ThreadContext.put("ROUTINGKEY", logfilePath);
				logger.info("Execution started to set up the Selenium Test Driver");
				setProps(new Properties());
				FileInputStream fis=new FileInputStream(System.getProperty("user.dir")+"/src/main/java/com/ecommerce/config/config.properties");
				getProps().load(fis);
				if (locale != null) {
					switch (locale.toLowerCase()) {
					case "remote":
						if (browserName != null) {
							switch(browserName.toLowerCase()){
							case "chrome":
								DesiredCapabilities caps_chrome=DesiredCapabilities.chrome();
								caps_chrome.setAcceptInsecureCerts(true);
								setDriver(new RemoteWebDriver(new URL(getProps().getProperty("REMOTE_DOCKER_URL")), caps_chrome));
								break;
							case "firefox":
								DesiredCapabilities caps_firefox=DesiredCapabilities.firefox();
								caps_firefox.setAcceptInsecureCerts(true);
								setDriver(new RemoteWebDriver(new URL(getProps().getProperty("REMOTE_DOCKER_URL")), caps_firefox));
								break;
							default:
								logger.fatal("Browser Driver doesn't match existing requirements. Please provide correct Browser Name.");
								throw new Exception("Browser Driver doesn't match existing requirements. Please provide correct Browser Name.");
							}
						}else {
							logger.fatal("Null Value for Browser not accepted. Driver Set up will exit.");
							throw new NullPointerException("Browser can't be set to Null. Please review your test input");
						}
						break;
					case "local":
						if (browserName != null) {
							switch(browserName.toLowerCase()){
							case "chrome":
								WebDriverManager.chromedriver().setup();
								setDriver(new ChromeDriver());
								break;
							case "firefox":
								WebDriverManager.firefoxdriver().setup();
								setDriver(new FirefoxDriver());
								break;
							default:
								logger.fatal("Browser Driver doesn't match existing requirements. Please provide correct Browser Name.");
								throw new Exception("Browser Driver doesn't match existing requirements. Please provide correct Browser Name.");
							}
						} else {
							logger.fatal("Null Value for Browser not accepted. Driver Set up will exit.");
							throw new NullPointerException("Browser can't be set to Null. Please review your test input");
						}
						break;
					case "cloud":
						if (browserName != null) {
							switch(browserName.toLowerCase()){
							case "chrome":
								DesiredCapabilities caps_chrome=DesiredCapabilities.chrome();
								caps_chrome.setAcceptInsecureCerts(true);
								caps_chrome.setCapability("build", "AutomationPractise Remote Test Build");
								caps_chrome.setCapability("name", "AutomationPractise-Test");
								caps_chrome.setCapability("visual", true);
								setDriver(new RemoteWebDriver(new URL(getProps().getProperty("CLOUD_URL")), caps_chrome));
								break;
							case "firefox":
								DesiredCapabilities caps_firefox=DesiredCapabilities.firefox();
								caps_firefox.setAcceptInsecureCerts(true);
								caps_firefox.setCapability("build", "AutomationPractise Remote Test Build");
								caps_firefox.setCapability("name", "AutomationPractise-Test");
								caps_firefox.setCapability("visual", true);
								setDriver(new RemoteWebDriver(new URL(getProps().getProperty("CLOUD_URL")), caps_firefox));
								break;
							default:
								logger.fatal("Browser Driver doesn't match existing requirements. Please provide correct Browser Name.");
								throw new Exception("Browser Driver doesn't match existing requirements. Please provide correct Browser Name.");
							}
						}else {
							logger.fatal("Null Value for Browser not accepted. Driver Set up will exit.");
							throw new NullPointerException("Browser can't be set to Null. Please review your test input");
						}
						break;
					default:
						logger.fatal("Locale Option doesn't match with desired Available Options. Please review our test input");
						throw new Exception("Locale Option doesn't match with desired Available Options. Please review our test input");
					}
				} else {
					logger.fatal("Null Value for Locale not accepted. Driver Set up will exit.");
					throw new NullPointerException("Null Value is not allowed for Locale. Please review your test input");
				}
				logger.info("Selenium Test Driver set up completed.");
				getDriver().manage().window().maximize();
				getDriver().manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
				getDriver().manage().deleteAllCookies();
				getDriver().navigate().to(getProps().getProperty("APPLICATION_URL"));
				logger.info("Opening the Application in Browser");
			} catch (Exception e) {
				logger.fatal("Exception in '"+Thread.currentThread().getStackTrace()[2].getMethodName()+"' is:"+e.getMessage()+" Details are:"+e.getStackTrace());
				getDriver().quit();
				logger.info("Closing down the Driver after failing to initialise the Driver Setup Process");
		}
	};

	//Functional Interface to tearDown the Selenium Test Driver.
	public static Consumer<RemoteWebDriver> tearDownDriver=driver-> {
			try{
				if(driver!=null){
					driver.quit();
					logger.info("Driver is exiting after Test Execution completion");
				}
			}catch(Exception e){
				logger.fatal("Failure in exiting Driver. Exception:"+e.getMessage()+"\n Details:"+e.getStackTrace());
		}
	};
	
}
