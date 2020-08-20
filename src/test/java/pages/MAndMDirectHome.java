package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MAndMDirectHome extends BasePage {
	
	private String title = "MandM Direct Ireland | Cheap Mens, Womens & Kids Clothing";
	
//	@FindBy(xpath=".//div[@id='mainHomepagePanel']//a[@title='New Arrivals - Save up to 65%']")
//	private WebElement newArrivalsBanner;
	
	@FindBy(xpath=".//div[@id='homepagePanelContainer']")
	private WebElement homepagePanel;
	
	@FindBy(xpath=".//div[@id='myAccount']")
	private WebElement myAccount;
	
	@FindBy(xpath=".//span[text()='Sign In']")
	private WebElement signInButton;
	
	@FindBy(xpath=".//span/a[text()='Sign Out'][1]")
	private WebElement signOutButton;
	
	private WebDriver driver = null;
	
	public MAndMDirectHome(WebDriver driver) throws Exception {
		super(driver);
		this.driver = driver;
		if(!isHomePageDisplayed()) {
			throw new Exception("Home page not displayed");
		}
	}
	
	public boolean isHomePageDisplayed() {
		return homepagePanel.isDisplayed();
	}
	
	public void navigateToLoginPage() {
//		moveToElement(driver, myAccount);
		signInButton.click();
	}
	
	public boolean verifyLoginError(String errorMessage) {
		return waitElement(driver, By.xpath(".//li[contains(text(), \"" + errorMessage + "\")]"), 20).isDisplayed();
	}

}
