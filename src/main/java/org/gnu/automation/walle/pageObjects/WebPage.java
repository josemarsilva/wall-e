package org.gnu.automation.walle.pageObjects;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;

/*
 * WebPage class is responsible for interact with Selenium web driver, 
 * instanciate driver, get url, find web elements, do actions on web 
 * elements
 * 
 * @author josemarsilva
 * 
 */
public class WebPage {
	
	// private properties ...
	private static WebDriver driver;
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
			ChromeOptions options = new ChromeOptions();
			options.addArguments("start-maximized");
			driver = new ChromeDriver(options);
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
	 * WebElement getCurrentWebElement() - Return current webElement found
	 */
	public WebElement getCurrentWebElement() {
		return webElement;
	}
	
	/*
	 * sendKeys( keys) - Send a sequence of keys pressed ...
	 */
	public void sendKeys(String keys) {
		if (webElement != null) {
			webElement.sendKeys(keys);  
		}
	}
	
	
	/*
	 * sendKeyEnter() - Send ENTER key pressed ...
	 */
	public void sendKeyEnter() {
		if (webElement != null) {
			webElement.sendKeys(Keys.RETURN);  
		}
	}
	
	
	/*
	 * sendKeyTab() - Send TAB key pressed ...
	 */
	public void sendKeyTab() {
		if (webElement != null) {
			webElement.sendKeys(Keys.TAB);  
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
	
	/*
	 * selectOptionBy() - Select option by
	 */
	public void selectOptionBy(String... args) {
		if (webElement != null) {
			Select select=new Select(webElement);
			if (args != null) {
				if (args.length == 2) {
					if (args[0].toUpperCase().equals("INDEX")) {
						select.selectByIndex(Integer.parseInt(args[1]));
						
					} else if (args[0].toUpperCase().equals("INDEX")) {
						select.selectByValue(args[1]);
						
					} else if (args[0].toUpperCase().equals("VISIABLETEXT")) {
						select.selectByVisibleText(args[1]);
						
					}
				}
			}
		}
		
	}
	
	
	/*
	 * quit() - calls driver.dispose method which in turn closes all the browser windows and ends the WebDriver session gracefully.
	 */
	public void quit() {
		if (driver!=null) {
			driver.quit();			
		}
	}


}
