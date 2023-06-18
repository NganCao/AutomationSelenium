package Pages;

import java.time.Duration;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import Supports.SupportMethods;
import Supports.UploadFile;

public class FileUploadPage {
	private WebDriver driver;
	private SupportMethods sp;
	private UploadFile up;
	
	private String url = "/File-Upload/index.html";
	private String expected_header = "File Upload";
	private String title_txt = "File Upload";
	private String form_title_txt = "Please choose a file to upload:";
	private String alert_required = "You need to select a file to upload!";
	private String alert_success = "Your file has now been uploaded!";
	//xpath
	private By header = By.xpath("//div[@id=\"main-header\"]/h1");
	private By upload_form = By.xpath("//form");
	private By form_title = By.xpath("//form/h2");
	private By form_inputFile = By.xpath("//div[@class=\"thumbnail\"]//input[@id=\"myFile\"]");
	private By form_submit = By.id("submit-button");
	
	public FileUploadPage (WebDriver driver) {
		this.driver = driver;
		sp = new SupportMethods(driver);
		up = new UploadFile(driver);
	}
	
	public boolean verify_Upload_url () {
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(2));
		if (driver.getCurrentUrl().contains(url)) {
			return true;
		} else return false;
	}
	
	public boolean verify_Title () {
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(2));
		if (driver.getTitle().equals(title_txt)) {
			return true;
		} else return false;
	}
	
	public boolean verify_Header () {
		sp.waitForElementVisible(header);
		if (driver.findElement(header).getText().trim().equals(expected_header)) {
			return true;
		} else return false;
	}
	
	public boolean verify_FormTitle () {
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(2));
		if (driver.findElement(upload_form).isDisplayed()) {
			if (driver.findElement(form_title).getText().trim().equals(form_title_txt)) {
				return true;
			} else return false;
		} else return false;
	}
	
	//check input null
	public boolean upload_without_file () {
		sp.waitForElementVisible(upload_form);
		driver.findElement(form_submit).click();
		Alert alert = driver.switchTo().alert();
		if (alert.getText().equals(alert_required)) {
			alert.accept();
			return true;
		} else return false;
	}
	//check upload success
	public boolean upload_file_success (String filePath, String fileName) throws Exception {
		boolean result;
		sp.waitForElementVisible(upload_form);
		up.uploadFile_BySendKeys(form_inputFile, filePath + fileName);
		driver.findElement(form_submit).click();
		Alert alert = driver.switchTo().alert();
		if (alert.getText().equals(alert_success)) {
			result = true;
		} else result = false;
		if (result == true) {
			alert.accept();
			Thread.sleep(2);
			url = "?filename=" + fileName;
			if (driver.getCurrentUrl().contains(url)) {
				return true;
			} else return false;
		} else return false;
	}
}
