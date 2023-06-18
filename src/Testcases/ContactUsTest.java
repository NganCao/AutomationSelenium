package Testcases;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import Pages.ContactUsPage;
import Pages.HomePage;
import Supports.Setup;
import Supports.SupportMethods;

public class ContactUsTest extends Setup{
	
	private WebDriver driver;
	private ContactUsPage contactpage;
	private HomePage homepage;
	private SupportMethods support;
	private String currentWindow;
	//data test
	private String firstName = "test";
	private String lastName = "auto";
	private String email = "auto@mail.com";
	private String invalid_email = "test";
	private String message = "run demo automation";
	//place holder
	private String firstName_ph = "First Name";
	private String lastName_ph = "Last Name";
	private String email_ph = "Email Address";
	private String message_ph = "Comments";
	
	@BeforeClass
	public void setup() {
		driver = getWebDriver();
		support = new SupportMethods(driver);
	}
	
	@Test (priority = 1)
	public void navigateToContactUs() throws Exception {
		homepage = new HomePage(driver);

		currentWindow = driver.getWindowHandle();
		support.closeAllOpenTabExceptMainTab(currentWindow);
		contactpage = homepage.clickContactForm();
	}
	
	@Test (priority = 2, description = "Check Contact Form")
	public void ContactForm() throws Exception {
		//switch tab
		support.switchTab(currentWindow);
		//get current url
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
		String url = driver.getCurrentUrl();
		
		Assert.assertTrue(contactpage.verifyConTactFormTitle(), "Contact Form Title is not match");
		Assert.assertTrue(contactpage.verifyUrl(), "URL does not contains contract path");
		Assert.assertTrue(contactpage.verifyHeader(), "Header is not match");
		
		contactpage.verifyPlaceHolder("first_name", firstName_ph);
		contactpage.verifyPlaceHolder("last_name", lastName_ph);
		contactpage.verifyPlaceHolder("first_name", firstName_ph);
		contactpage.verifyPlaceHolder("email", email_ph);
		contactpage.verifyPlaceHolder("message", message_ph);
		contactpage.verifyForm_button(); 
		
		contactpage.submitContactForm_successfully(firstName, lastName, email, message);
		driver.navigate().to(url);
		contactpage.verifySubmit_WithoutAll();
		
		driver.navigate().to(url);
		Assert.assertTrue(contactpage.verifyResetBtn(firstName, lastName, email, message), "Reset button is not work.");
		contactpage.verifySubmit_InvalidEmail(firstName, lastName, invalid_email, message);
		driver.navigate().to(url);
		//Check error message when first name is null
		contactpage.verifySubmit_RequiredField_null("", lastName, email, message);
		driver.navigate().to(url);
		//Check error message when last name is null
		contactpage.verifySubmit_RequiredField_null(firstName, "", email, message);
		driver.navigate().to(url);
		//Check error message when email address is null
		contactpage.verifySubmit_RequiredField_null(firstName, lastName, "", message);
		driver.navigate().to(url);
		//Check error message when message is null
		contactpage.verifySubmit_RequiredField_null(firstName, lastName, email, "");
		
		//close tab
		support.switchBackCurrentTab(currentWindow);
	}
}
