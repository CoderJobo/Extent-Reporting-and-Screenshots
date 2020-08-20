package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MAndMDirectWelcome extends BasePage {
	
	private WebDriver driver = null;
	private String title = "MandM Direct Welcome";
	
	@FindBy(xpath=".//input[@id='hiddenBreadcrumb']")
	private WebElement hiddenBreadcrumb; 
	
	@FindBy(xpath=".//span[@id='welcome']//a[text()='Sign Out'][1]")
	private WebElement signOutButton;
	
	@FindBy(xpath=".//div[@id='myAccount']//span[text()='Sign In']")
	private WebElement signInButton;
	
	public MAndMDirectWelcome(WebDriver driver) throws Exception {
		super(driver);
		this.driver = driver;
		if(!isWelcomePageDisplayed()) {
			throw new Exception("Welcome page is not displayed");
		}
	}
	
	public boolean isWelcomePageDisplayed() {
		return driver.getTitle().equals(title);
	}
	
	public void performLogout() throws Exception {
//		moveToElement(driver, welcome);
		signOutButton.click();
		if(!wasLogoutSuccessful()) {
			throw new Exception("Logout was not successful");
		}
	}
	
	public boolean wasLogoutSuccessful() {
		return signInButton.isDisplayed();
	}

}
