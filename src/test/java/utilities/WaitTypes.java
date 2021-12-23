package utilities;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

@SuppressWarnings("deprecation")
public class WaitTypes {
	WebDriver driver;
	
	public WaitTypes(WebDriver driver) {
		this.driver = driver;
	}
	public WebElement waitForElement(By locator, int timeout) {
		WebElement element = null;
		try {
			System.out.println("Waiting for a maximum of " + timeout + " for element to be available");
			
			WebDriverWait wait = new WebDriverWait(driver, timeout);
			element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
			System.out.println("Element was found on the webpage");
			
		} catch(Exception e) {
			System.out.println("Element not found");
		}
		return element;
	}
	
	public void clickWhenReady(By locator, int timeout) {
		try {
			WebElement element = null;
			System.out.println("Waiting for a maximum of " + timeout + " for element to be clickable");
			
			WebDriverWait wait = new WebDriverWait(driver, timeout);
			element = wait.until(ExpectedConditions.elementToBeClickable(locator));
			element.click();
			System.out.println("Element was clicked on the webpage");
			
		} catch(Exception e) {
			System.out.println("Element not found");
		}

	}

}
