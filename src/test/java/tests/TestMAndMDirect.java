package tests;

import java.util.Map;

import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import pages.MAndMDirectHome;
import pages.MAndMDirectLogin;
import pages.MAndMDirectWelcome;

public class TestMAndMDirect extends TestBase {
	
	private MAndMDirectHome home = null;
	private MAndMDirectLogin login = null;
	private MAndMDirectWelcome welcome = null;
	private String loginError = "You've entered an incorrect email address or password";
	
	
	@Test(dataProvider = "getDataFromDataSource")
	public void LoginToMAndMDirect(int itr, Map<String, String> data) throws Exception {
		try {
			System.out.println("Running itr " + itr);
			home = new MAndMDirectHome(getDriver());
			home.navigateToLoginPage();
			reporter().report(LogStatus.INFO, "Checking navigation to MAndMDirect login page", "Navigation to login page is successful");
			login = new MAndMDirectLogin(getDriver());
			System.out.println("Username = " + data.get("Username") + ", Password = " + data.get("Password"));
			login.performLogin(data.get("Username"), data.get("Password"));
			reporter().report(LogStatus.PASS, "Checking login to MAndMDirect", "Login to M And M Direct is successful", true);
			if(itr == 1) {
				welcome = new MAndMDirectWelcome(getDriver());
				System.out.println("Login was successful");
				Thread.sleep(2000);
				welcome.performLogout();
			}
			if(itr > 1) {
				if(home.verifyLoginError(loginError)) {
					reporter().report(LogStatus.PASS, "Checking for account does not exist error", "Account does not exist - correct error message was displayed");
				}
				else {
					reporter().report(LogStatus.FAIL, "Checking for account does not exist error", "Account does not exist - error was not visible", true);
				}
			}
		}catch(Exception e) {
			reporter().report(LogStatus.FAIL, "Exception occurred", e.getMessage(), true);
		}
	}

}
