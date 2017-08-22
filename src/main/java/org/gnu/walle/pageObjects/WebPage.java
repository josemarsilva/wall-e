package org.gnu.walle.pageObjects;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WebPage {
	
	// private properties ...
	private static WebDriver driver;
	private WebDriverWait webDriverWait;
	private WebElement webElement;
	
	
	/*
	 * Constructor - no parameters
	 */
	public WebPage() {
	}
	
	
	/*
	 * WebDriver getWebDriver() - Get webDriver 
	 */
	public WebDriver getWebDriver() {
		if (driver==null) {
			System.setProperty("webdriver.chrome.driver", "resource\\chromedriver.exe");
			driver = new ChromeDriver();
		}
		return driver;
	}
	
	
	/*
	 * get(url) - Get a url
	 */
	public void get(String url) {
		getWebDriver().get(url);
	}
	
	
	/*
	 * wait(milliseconds) - Wait implicity <milliseconds>
	 */
	public void wait(String milliseconds) {
		getWebDriver().manage().timeouts().implicitlyWait(Integer.parseInt(milliseconds), TimeUnit.MILLISECONDS);
	}


	/*
	 * findElementByxpath( xpath ) - Find an element by xpath and store into current webElement
	 */
	public void findElementByxpath(String xpath) {
		webElement = driver.findElement(By.xpath(xpath));
	}
	
	
	/*
	 * sendKeys( keys) - Find an element by xpath and store into current webElement
	 */
	public void sendKeys(String keys) {
		if (webElement != null) {
			webElement.sendKeys(keys);  
		}
	}
	
	
	/*
	 * click() - Click current webElement selected
	 */
	public void click() {
		if (webElement != null) {
			webElement.click();  
		}
	}
	
	
	/*
	 * clear() - Clear current webElement selected
	 */
	public void clear() {
		if (webElement != null) {
			webElement.clear();  
		}
	}
	
	
	/*
	 * getText() - Get current webElement text value
	 */
	public String getText() {
		if (webElement != null) {
			return webElement.getText();  
		} else {
			return new String("");
		}
	}
	
	
	/*
	 * setText() - Set current webElement text value
	 */
	public void setText(String txt) {
		if (webElement != null) {
			sendKeys(txt);  
		}
	}
	
	
	/*
	 * close() - Close WebDriver instances
	 */
	public void close(){
		if (driver!=null) {
			driver.close();
		}
	}
	
	
}
