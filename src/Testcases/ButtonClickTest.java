package Testcases;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import Pages.ButtonPage;
import Pages.HomePage;
import Supports.ExcelHelpers;
import Supports.Setup;
import Supports.SupportMethods;

public class ButtonClickTest extends Setup {

	private WebDriver driver;
	private HomePage homePage;
	private ButtonPage btnPage;
	private SupportMethods support;
	private String currentWindow;
	//excel file
	private String fileName = "DataTest.xlsx";
	private String excelPath = System.getProperty("user.home") + "\\eclipse-workspace\\AutomationSelenium\\Data File\\";
	private String sheetName = "Button Clicks";
	
	@BeforeClass
	public void setUp() {
		this.driver = getWebDriver();
		homePage = new HomePage(driver);
		btnPage = new ButtonPage(driver);
		support = new SupportMethods(driver);
	}
	
	@Test (priority = 1)
	public void navigateToButtonPage() throws Exception {
		currentWindow = driver.getWindowHandle();
		support.closeAllOpenTabExceptMainTab(currentWindow);
		btnPage = homePage.clickButtonClicks();
	}
	
	@Test (priority = 2) 
	public void check_general_infor () throws Exception {
		support.switchTab(currentWindow);
		driver.getCurrentUrl();
		System.out.println("Verify that the general information is correct");
		SoftAssert softAssert = new SoftAssert();
//		System.out.println("Verify that the URL is correct");
		softAssert.assertTrue(btnPage.verifyUrl());
//		System.out.println("Verify that the page header is correct");
		softAssert.assertTrue(btnPage.verifyHeader());
//		System.out.println("Verify that the page title is correct");
		softAssert.assertTrue(btnPage.verifyTitle());
		softAssert.assertAll("The general information is failed!\nThe URL/page header/page title went wrong somewhere!");
	}
	
	@Test (priority = 3) 
	public void check_thumbnail_sections () throws Exception {
		System.out.println("Verify that the thumbnail content is correct");
//		excel.readExcelFile(excelPath + fileName, sheetName);
		Assert.assertTrue(btnPage.verify_thumbnail_content(excelPath + fileName, sheetName)
				, "Thumbnail content went wrong somewhere! Please check the error message on " + fileName);
	}
}
