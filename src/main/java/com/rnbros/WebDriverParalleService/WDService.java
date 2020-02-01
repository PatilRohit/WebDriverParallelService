package com.rnbros.WebDriverParalleService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.WebDriver;

public class WDService {	
	public static final String CHROME = "CHROME";
	public static final String FF = "FF";
	public static final String IE = "IE";
	public static String ChromeDriver = "driver/chromedriver.exe";
	public static String FFDriver = "driver/GeckoDriver.exe";
	public static String IEDriver = "driver/IEDriverServer.exe";
	private static int threadCount;
	private static List<Driver> dL;
	
	public WDService(int threadCount,String type, PreStep ps) {
		System.setProperty("webdriver.chrome.driver", ChromeDriver);
		System.setProperty("webdriver.ie.driver", IEDriver);
		System.setProperty("webdriver.gecko.driver", FFDriver);
		this.threadCount = threadCount;
		dL = new ArrayList<>();
		for(int count=0; count < threadCount; count++)
			dL.add(new Driver(type,ps));		
	}
	
	public static WebDriver getDriver() {
		
		for(int count=0; count < threadCount; count++) {
			Driver driver = dL.get(count);
				synchronized(driver) {
				if(driver.getStatus().equals("null")){
					driver.setStatus(new Throwable().getStackTrace()[1].getMethodName());
					if(!driver.isInitiated())
						driver.initiate();									
					return driver.getDr();
				}
			}
		}
		return null;
	}
	
	public static synchronized void releaseDriver(String method) {
		for(int count=0; count < threadCount; count++) {
			Driver driver = dL.get(count);
			if(driver.getStatus().equals(method)){
				driver.setStatus("null");
				return;
			}
		}
	}
	
	public static void close() {
		for(int count=0; count < threadCount; count++)
			dL.get(count).getDr().quit();
	}
	
}
