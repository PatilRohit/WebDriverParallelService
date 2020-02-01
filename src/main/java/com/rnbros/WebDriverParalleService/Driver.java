package com.rnbros.WebDriverParalleService;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

public class Driver {

	private WebDriver dr;
	private String method = "null";
	private boolean initiated = false;
	private PreStep ps;
	private String type;
	public Driver(String type, PreStep ps) {
		this.ps = ps;
		this.type = type;	
	}

	public void initiate() {
		initiated = true;		
			switch (type) {
			case "CHROME":
				dr = new ChromeDriver();
				break;
			case "FF":
				dr = new FirefoxDriver();
				break;
			case "IE":
				dr = new InternetExplorerDriver();
				break;
			default:
				dr = new ChromeDriver();
			}		
		if (ps != null)
			ps.initDriver(dr);

	}

	public boolean isInitiated() {
		return initiated;
	}

	public WebDriver getDr() {		
		return dr;
	}

	public String getStatus() {
		return method;
	}

	public void setStatus(String method) {
		this.method = method;
	}

}
