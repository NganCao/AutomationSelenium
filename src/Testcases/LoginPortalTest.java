package Testcases;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import Pages.HomePage;
import Pages.LoginPortalPage;
import Supports.Setup;
import Supports.SupportMethods;

public class LoginPortalTest extends Setup{
	private WebDriver driver;
	private HomePage homePage;
	private LoginPortalPage loginPage;
	private SupportMethods support;
	//data test
	private String currentWindow;
	private String username = "webdriver";
	private String password = "webdriver123";
	
	@BeforeClass
	public void setUp() {
		driver = getWebDriver();
		support = new SupportMethods(driver);
	}
	
	@Test (priority = 1) 
	public void HomePage() throws Exception {
		homePage = new HomePage(driver);
		currentWindow = driver.getWindowHandle();
		support.closeAllOpenTabExceptMainTab(currentWindow);
		loginPage = homePage.clickLogInPortal();
	}
	
	@Test (priority = 2)
	public void LoginPortal() throws Exception {
		//switch tab
		support.switchTab(currentWindow);
//		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
		
		Assert.assertTrue(loginPage.verifyTitle(), "Login portal title is not match");
		Assert.assertTrue(loginPage.verifyUrl(), "URL does not contains contract path");
		loginPage.verifyPlaceholder_allfield();
		loginPage.verifySubmit_value();
		loginPage.verifyLogin_wrongData();
		loginPage.verifyLogin_successfully(username, password);
	}
}
