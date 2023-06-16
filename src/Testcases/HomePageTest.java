package Testcases;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import Pages.HomePage;
import Supports.Setup;

public class HomePageTest extends Setup{
	
	private WebDriver driver;
	private HomePage homepage;
	
	@BeforeClass
	public void setup() {
		driver = getWebDriver();
	}
	
	@Test 
	public void HomePage() throws Exception {
		homepage = new HomePage(driver);

		Assert.assertTrue(homepage.verifyHomePageTitle(), "Homepage's title is not match ");
		Assert.assertTrue(homepage.verifySectionContent());
	}
	
}
