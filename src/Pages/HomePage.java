package Pages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import Supports.SupportMethods;

public class HomePage {
	//
	private WebDriver driver;
	private SupportMethods sp;
	
	//define elements
//	private String baseURL = "http://webdriveruniversity.com";
	private By contractUsForm = By.xpath("//a[@id=\"contact-us\"]");
	private By container_list = By.xpath("//div[@class=\"section-title\"]/h1");
	private By caption_title_list = By.xpath("//div[@class=\"caption\"]/h4");
	private By caption_des_list = By.xpath("//div[@class=\"caption\"]/p[1]");
	private By loginPortal = By.xpath("//a[@id=\"login-portal\"]");
	private By buttonClicks = By.xpath("//a[@id=\"button-clicks\"]");
	private By toDoList = By.xpath("//a[@id=\"to-do-list\"]");
	private By pageObjectModel = By.xpath("//a[@href=\"Page-Object-Model/index.html\"]");
	private By accordion = By.xpath("//a[@href=\"Accordion/index.html\"]");
	private By dropdownBtn = By.xpath("//a[@id=\"dropdown-checkboxes-radiobuttons\"]");
	//
	private By ajaxLoader = By.id("ajax-loader");
	private By actions = By.id("actions");
	private By scrollAround = By.id("scrolling-around");
	private By popup_Alerts = By.id("popup-alerts");
	private By iframe = By.id("iframe");
	private By hidden_elements = By.id("hidden-elements");
	private By dataTable = By.id("data-table");
	private By autocomplete_textfield = By.id("autocomplete-textfield");
	private By file_upload = By.id("file-upload");
	private By datePicker = By.id("datepicker");
	
	public HomePage (WebDriver driver) {
		this.driver = driver;
		sp = new SupportMethods(this.driver);
	}
		
	public boolean verifyHomePageTitle () {
		String expected = "WebDriverUniversity.comm";
		return sp.getTitle().contains(expected);
	}
	
	private boolean verifyText (String actualText, String expectedText) {
		return actualText.trim().equals(expectedText.trim());
	}
	
	//need update data is get in the data file
	public boolean verifySectionContent() throws Exception {
		boolean rs = false;
		int passed = 0, failed = 0;
		
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
		
		// Title 
		List<WebElement> elementsTitle = driver.findElements(container_list);
		int size = elementsTitle.size();
		
		//Caption title
		List<WebElement> elementsCaptionTitle = driver.findElements(caption_title_list);
		int elementsCaption_size = elementsCaptionTitle.size();
		
		//Description
		List<WebElement> elementsDescription = driver.findElements(caption_des_list);
		int elementsDescription_size = elementsDescription.size();

		if(size == 17 && elementsCaption_size == 17 && elementsDescription_size == 17) {
			for (int i = 0; i< 3; i++) {
				if (elementsTitle.get(i).getText().trim().equals("CONTACT US")) {
					if (verifyText(elementsCaptionTitle.get(i).getText(), "Contact Us Form")) {
						String actualDes = sp.replaceString(elementsDescription.get(i).getText(), "\n|\r", " ").trim();
						String expectedDes = "Need to perfect your Webdriver Cucumber skills? BDD also referred to as ‘Behaviour Driven Development’ is a great way to test and simulate different user scenarios, for example what happens if you try to use the following contact us form (Click the button to access the challenge) to simulate user(s) inputting different types of data or how about attempting to simulate a user submitting information to the form using an email address in the incorrect ‘Mandatory’ format?";
						Assert.assertEquals(actualDes, expectedDes);
						if (actualDes.equals(expectedDes)) {
							rs = true; 
							passed ++;
						}
						else {
							System.out.println("Contact form : Description is wrong");
							System.out.println(elementsDescription.get(i).getText());
						}
					}
					else {
						System.out.println("Contact form: Caption title is wrong. Actual: " + elementsCaptionTitle.get(i).getText() 
								+ "\tExpected: Contact Us Form");
						rs = false;
						failed ++;
					}
				}
				else if (elementsTitle.get(i).getText().trim().equals("LOGIN PORTAL")) {
					if (verifyText(elementsCaptionTitle.get(i).getText(), "Login Portal")) {
						rs = true; 
						passed ++;
					}
					else {
						rs = false;
						failed ++;
						System.out.println("Login Portal: Caption title is wrong. Actual: " + elementsCaptionTitle.get(i).getText() 
								+ "\tExpected: Login Portal");
					}
				}
				else if (elementsTitle.get(i).getText().trim().equals("BUTTON CLICKS")) {
					if (verifyText(elementsCaptionTitle.get(i).getText(), "WebElement Click, JavaScript Click, Actions Move & Click!")) {
						rs = true; 
						passed ++;
					}
					else {
						rs = false;
						failed ++;
						System.out.println("Button clicks: Caption title is wrong. Actual: " + elementsCaptionTitle.get(i).getText() 
								+ "\tExpected: WebElement Click, JavaScript Click, Actions Move & Click!");
					}
				}
				else {
					System.out.println("Title is wrong. Actual: " + elementsTitle.get(i).getText());
					rs = false;
					failed ++;
				}
			}
		}
		
		System.out.println("passed: " + passed + "\tfailed: " + failed);
		return rs;
	}
				
	public ContactUsPage clickContactForm() {
		sp.clickBtn(contractUsForm);
		return new ContactUsPage(driver);
	}
	
	public LoginPortalPage clickLogInPortal() {
		sp.clickBtn(loginPortal);
		return new LoginPortalPage(driver);
	}
	
	public ButtonPage clickButtonClicks() {
		sp.clickBtn(buttonClicks);
		return new ButtonPage(driver);
	}
	
	public void clickToDoList() {
		sp.clickBtn(toDoList);
	}
	
	public void clickPageObjectModel() {
		sp.clickBtn(pageObjectModel);
	}
	
	public void clickAccordion() {
		sp.clickBtn(accordion);
	}
	
	public void clickDropdown() {
		sp.clickBtn(dropdownBtn);
	}
	
	public void clickAjaxLoader() {
		sp.clickBtn(ajaxLoader);
	}
	
	public ActionsPage clickActions() {
		sp.clickBtn(actions);
		return new ActionsPage(driver);
	}
	
	public void clickScrollAround() {
		sp.clickBtn(scrollAround);
	}
	
	public void clickPopupAlert() {
		sp.clickBtn(popup_Alerts);
	}
	
	public void clickIframe() {
		sp.clickBtn(iframe);
	}
	
	public void clickHiddenElements() {
		sp.clickBtn(hidden_elements);
	}
	
	public void clickDataTable() {
		sp.clickBtn(dataTable);
	}
	
	public void clickAutoComplete() {
		sp.clickBtn(autocomplete_textfield);
	}
	
	public FileUploadPage clickFileUpload() {
		sp.clickBtn(file_upload);
		return new FileUploadPage(driver);
	}
	
	public void clickDatePicker() {
		sp.clickBtn(datePicker);
	}
}
