package com.rnbros.WebDriverParalleService;

import java.util.HashMap;
import java.util.Map;
import org.openqa.selenium.WebDriver;

public class WDService {
	public static final String CHROME = "CHROME";
	public static final String FF = "FF";
	public static final String IE = "IE";
	public static String ChromeDriver = "driver/chromedriver.exe";
	public static String FFDriver = "driver/GeckoDriver.exe";
	public static String IEDriver = "driver/IEDriverServer.exe";
	private static Map<String, Driver> dL = new HashMap<>();
	private static String type;
	private static PreStep ps;

	public WDService(String type, PreStep ps) {
		System.setProperty("webdriver.chrome.driver", ChromeDriver);
		System.setProperty("webdriver.ie.driver", IEDriver);
		System.setProperty("webdriver.gecko.driver", FFDriver);
		WDService.type = type;
		WDService.ps = ps;
	}

	public static WebDriver getDriver() {
		String threadName = Thread.currentThread().getName();
		Driver dr = null;
		synchronized (dL) {
			dr = dL.get(threadName);
		}
		if (dr == null) {		
			dr = new Driver(type, ps);
			synchronized (dL) {
				dL.put(threadName, dr);
			}
		}
		if (!dr.isInitiated())
			dr.initiate();
		return dr.getDr();
	}

	public static void close() {
		for (Map.Entry<String, Driver> dr : dL.entrySet())
			dr.getValue().getDr().quit();
	}

}
