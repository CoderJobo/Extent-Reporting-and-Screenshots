package pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePage {
	
	public BasePage(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}
	
	public void moveToElement(WebDriver driver, WebElement ele) {
		Actions act = new Actions(driver);
		act.moveToElement(ele).build().perform();
	}
	
	public WebElement waitElement(WebDriver driver, By element, long timeout) {
		return new WebDriverWait(driver, timeout)
				.ignoring(NoSuchElementException.class)
				.pollingEvery(Duration.ofMillis(500))
				.until(ExpectedConditions.elementToBeClickable(element));
	}

}
