package Testcases;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import Pages.FileUploadPage;
import Pages.HomePage;
import Supports.Setup;
import Supports.SupportMethods;

public class UploadFileTest extends Setup {
	private WebDriver driver;
	private FileUploadPage uploadFile;
	private HomePage homepage;
	private String currentWindow;
	private SupportMethods support;
	private String fileUpload_path = System.getProperty("user.dir") + "\\Data File\\Images\\";
	private String fileName = "uploadFile.jpg";
	
	@BeforeClass
	public void setUp () {
		driver = getWebDriver();
		homepage = new HomePage(driver);
		support = new SupportMethods(driver);
	}
	
	@Test (priority = 1)
	public void navigateToUploadFile () throws Exception {
		currentWindow = driver.getWindowHandle();
		uploadFile = homepage.clickFileUpload();
	}
	
	@Test (priority = 2                                                                                                                                                                                                                                                                                                                                       ) 
	public void check_general_uploadFile_page () throws Exception {
		support.switchTab(currentWindow);
		SoftAssert sa = new SoftAssert();
		sa.assertTrue(uploadFile.verify_Upload_url(), "The URL is incorrect!");
		sa.assertTrue(uploadFile.verify_Title(), "The page title is incorrect!");
		sa.assertTrue(uploadFile.verify_Header(), "The page header is incorrect!");
		sa.assertTrue(uploadFile.verify_FormTitle(), "The form title is incorrect!");
		sa.assertAll("The general information is failed!\nThe URL/page header/page title went wrong somewhere!");
	}
	
	@Test (priority = 3)
	public void check_uploadFile_function () throws Exception {
		Assert.assertTrue(uploadFile.upload_without_file(), "The alert message is wrong when user uploads without file");
		Assert.assertTrue(uploadFile.upload_file_success(fileUpload_path, fileName), "The alert message is wrong when user uploads successfully");
	}
}
