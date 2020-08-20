package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MAndMDirectLogin extends BasePage {
	
	private String title = "MandM Direct Sign In";
	
	@FindBy(xpath=".//div[@id='SignInDetails']")
	private WebElement signInDetails;
	
//	@FindBy(xpath=".//div[@id='SignInDetails']/h1")
//	private WebElement signInHeader;
	
	@FindBy(xpath=".//input[@id='EmailAddress']")
	private WebElement emailInput;
	
	@FindBy(xpath=".//input[@id='Password']")
	private WebElement passwordInput;
	
	@FindBy(xpath=".//div[@id='SignInContinue']/input")
	private WebElement continueButton;
	
	private WebDriver driver = null;
	
	public MAndMDirectLogin(WebDriver driver) throws Exception {
		super(driver);
		this.driver = driver;
		Thread.sleep(1000);
		if(!isLoginPageDisplayed()) {
			throw new Exception("Login page not displayed");
		}
	}
	
	public boolean isLoginPageDisplayed() {
		return signInDetails.isDisplayed();
	}
	
	public void performLogin(String email, String password) throws Exception {
		emailInput.sendKeys(email);
		Thread.sleep(1000);
		passwordInput.sendKeys(password);
		Thread.sleep(1000);
		continueButton.click();
		Thread.sleep(1000);
	}
	

}
