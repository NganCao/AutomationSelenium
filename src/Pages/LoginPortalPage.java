package Pages;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import Supports.SupportMethods;

public class LoginPortalPage {
	private WebDriver driver;
	private String url = "Login-Portal/index.html";
	SupportMethods sp;
	//define elements
	private By username_field = By.id("text");
	private By password_field = By.id("password");
	private By submit_btn = By.id("login-button");
	
	//data test
	private String title = "WebDriver | Login Portal";
	private String username_ph = "Username";
	private String password_ph = "Password";
	private String submit_value = "Login";
	
	public LoginPortalPage(WebDriver driver) {
		this.driver = driver;
		sp = new SupportMethods(this.driver);
	}
	
	public boolean verifyUrl() {
		return sp.verifyUrl(url);
	}
	
	public boolean verifyTitle() {
		return sp.getTitle().equals(title);
	}
	
	public void verifyPlaceholder_allfield() {
		sp.verifyPlaceHolder(username_field, username_ph);
		sp.verifyPlaceHolder(password_field, password_ph);
	}
	
	public void verifySubmit_value() {
		sp.verifyText(submit_btn, submit_value);
	}
	
	private void login(String username, String password) {
		sp.enterData(username_field, username);
		sp.enterData(password_field, password);
		sp.clickBtn(submit_btn);
	}
	
	public void verifyLogin_successfully(String username, String password) {
		login(username, password);
		Alert alert = driver.switchTo().alert();
		Assert.assertEquals(alert.getText(), "validation succeeded");
		alert.accept();
	}
	
	//because this function has 2 cases: success or failure whether it's wrong password/wrong username or null
	public void verifyLogin_wrongData() {
		login("wrong", "wrong");
		Alert alert = driver.switchTo().alert();
		Assert.assertEquals(alert.getText(), "validation failed");
		alert.accept();
	}
}
