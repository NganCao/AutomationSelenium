package Pages;


import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import Supports.SupportMethods;

public class ContactUsPage {

	private WebDriver driver;
	private SupportMethods sp;
	
	//Define elements
	private By Header = By.name("contactme");
	private By firstName_field = By.name("first_name");
	private By lastName_field = By.name("last_name");
	private By email_field = By.name("email");
	private By message_field = By.name("message");
	private By reset_btn = By.xpath("//div[@id=\"form_buttons\"]/input[@value=\"RESET\"]");
	private By submit_btn = By.xpath("//div[@id=\"form_buttons\"]/input[@value=\"SUBMIT\"]");
	private By errorMsgText = By.xpath("//body");
	//Thank you page
	private By thankYou_header = By.id("contact_reply");
	private String thankYou_txt = "Thank You for your Message!";
	
	/*@FindBy(name = "contactme")
	private WebElement header;*/
	
	private String urlPath = "Contact-Us/contactus.html";
	private String url_thankyou = "/Contact-Us/contact-form-thank-you.html";
	private String err_msg_without_all = "Error: all fields are required Error: Invalid email address";
	private String err_msg_invalid_email = "Error: Invalid email address";
	private String err_msg_required_field = "Error: all fields are required";
	
	//define data
	private String header_str = "CONTACT US";
		
	public ContactUsPage(WebDriver driver) {
		this.driver = driver;
		sp = new SupportMethods(this.driver);
//		PageFactory.initElements(driver, this);
	}
	
	public boolean verifyConTactFormTitle () {
		String expected = "WebDriver | Contact Us";
		return driver.getTitle().contains(expected);
	}
	
	public void verifySubmit_WithoutAll() {
		sp.waitForElementVisible(submit_btn);
		driver.findElement(submit_btn).click();
		String actualMsg = sp.replaceString(getErrorMsg(), "\n|\r", " ").trim();
		Assert.assertEquals(actualMsg, err_msg_without_all);	
	}
	
	private String getErrorMsg() {
		sp.waitForElementVisible(errorMsgText);
		WebElement errorMsg = driver.findElement(errorMsgText);
		return errorMsg.getText();
	}
	
	public boolean verifyHeader() {
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
		return sp.verifyText(Header, header_str);
	}
	
	public boolean verifyUrl() {
		return sp.verifyUrl(urlPath);
	}
	
	public void verifyPlaceHolder(String key, String expectedText) {
		switch (key) {
		case "first_name":
			sp.verifyPlaceHolder(firstName_field, expectedText);
			break;
		case "last_name":
			sp.verifyPlaceHolder(lastName_field, expectedText);
			break;
		case "email":
			sp.verifyPlaceHolder(email_field, expectedText);
			break;
		case "message":
			sp.verifyPlaceHolder(message_field, expectedText);
			break;
		default:
			System.out.println("Contact Form includes fields: first_name, last_name, email, message. Your key is not match.");
		}
	}
	
	public void verifyForm_button() {
		sp.verifyButton_value(submit_btn, "SUBMIT");
		sp.verifyButton_value(reset_btn, "RESET");
	}
	
	private void submitContactForm(String firstName, String lastName, String email, String message) {
		sp.enterData(firstName_field, firstName);
		sp.enterData(lastName_field, lastName);
		sp.enterData(email_field, email);
		sp.enterData(message_field, message);
		sp.clickBtn(submit_btn);
	}
	
	public boolean verifyResetBtn(String firstName, String lastName, String email, String message) {
		Boolean result = false, rs_ln = false, rs_fn = false, rs_em = false, rs_msg = false;
		//submit form
		sp.enterData(firstName_field, firstName);
		sp.enterData(lastName_field, lastName);
		sp.enterData(email_field, email);
		sp.enterData(message_field, message);
		
		
		String firstName_txt, lastName_txt, email_txt, message_txt;
		//get the entered text
		firstName_txt = driver.findElement(firstName_field).getAttribute("value");
		lastName_txt = driver.findElement(lastName_field).getAttribute("value");
		email_txt = driver.findElement(email_field).getAttribute("value");
		message_txt = driver.findElement(message_field).getAttribute("value");
		
		//verifyText entered
		Assert.assertEquals(firstName_txt, firstName);
		Assert.assertEquals(lastName_txt, lastName);
		Assert.assertEquals(email_txt, email);
		Assert.assertEquals(message_txt, message);
		
		//click reset btn then verify fields is null
		sp.clickBtn(reset_btn);
		if (driver.findElement(firstName_field).getText().isEmpty()) {
			rs_fn = true;
		} else {
			System.out.println("Firstname is not clear. Actual: " + driver.findElement(message_field).getText());
		}
		if (driver.findElement(lastName_field).getText().isEmpty()) {
			rs_ln = true;
		} else {
			System.out.println("Lastname is not clear. Actual: " + driver.findElement(message_field).getText());
		}
		if (driver.findElement(message_field).getText().isEmpty()) {
			rs_msg = true;
		} else {
			System.out.println("Message is not clear. Actual: " + driver.findElement(message_field).getText());
		}
		if (driver.findElement(email_field).getText().isEmpty()) {
			rs_em = true;
		} else {
			System.out.println("Email is not clear. Actual: " + driver.findElement(message_field).getText());
		}
		if (rs_fn.equals(true) && rs_ln.equals(true) && rs_em.equals(true) && rs_msg.equals(true)) {
			result = true;
		}
			
		return result;
	}
	
	public void verifySubmit_InvalidEmail(String firstName, String lastName, String invalid_email, String message) {
		//submit form with invalid email address
		submitContactForm(firstName, lastName, invalid_email, message);
		//get error msg
		String actualMsg = sp.replaceString(getErrorMsg(), "\n|\r", " ").trim();
		
		//verify error msg
		Assert.assertEquals(actualMsg, err_msg_invalid_email);
	}
	
	public void verifySubmit_RequiredField_null(String firstName, String lastName, String email, String message) {
		//submit form with invalid email address
		submitContactForm(firstName, lastName, email, message);
		//get error msg
		String actualMsg = sp.replaceString(getErrorMsg(), "\n|\r", " ").trim();
		
		if (firstName.equals("") || lastName.equals("") || message.equals("")) {
			Assert.assertEquals(actualMsg, err_msg_required_field);
		}
		if (email.equals("")) {
			Assert.assertEquals(actualMsg, err_msg_without_all);
		}
	}
	
	public boolean submitContactForm_successfully (String firstName, String lastName, String email, String message) {
		//submit form
		submitContactForm(firstName, lastName, email, message);
		//verify header
		Assert.assertEquals(driver.findElement(thankYou_header).getText().trim(), thankYou_txt);
		return sp.verifyUrl(url_thankyou);
	}
}
