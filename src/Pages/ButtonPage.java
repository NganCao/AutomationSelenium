package Pages;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.asserts.SoftAssert;

import Supports.ExcelHelpers;
import Supports.SupportMethods;

public class ButtonPage {

	private WebDriver driver;
	private SupportMethods sp;
	private ExcelHelpers excel;
	private String page_url = "/Click-Buttons/index.html";
	
	//define elements
	private By header = By.xpath("//div[@id=\"main-header\"]/h1");
	private String thumbnail_xpath = "//div[@class=\"thumbnail\"]";
	private By heading_xpath = By.xpath(thumbnail_xpath + "/h2");
	private By title_xpath = By.xpath(thumbnail_xpath + "//ol");
	private By button_xpath = By.xpath(thumbnail_xpath + "//span[starts-with(@id,\"button\")]");
	
	//data test
	private String header_txt = "Lets Get Clicking!";
	private String title = "WebDriver | Button Clicks";
	private int expected_thumbnail_size;
	
	public ButtonPage(WebDriver driver) {
		this.driver = driver;
		sp = new SupportMethods(driver);
		excel = new ExcelHelpers();
	}
	
	public boolean verifyUrl () {
		return sp.verifyUrl(page_url);
	}
	
	public boolean verifyHeader() {
		return sp.verifyText(header, header_txt);
	}
	
	public boolean verifyTitle() {
		return sp.getTitle().equals(title);
	}

	public boolean verify_thumbnail_content(String dataFilePath, String sheetName) throws Exception {
		//get title list
		List<WebElement> heading_list = sp.getListElements(heading_xpath);
		//get des list
		List<WebElement> title_list = sp.getChildElementByParent(heading_list, By.xpath("./following-sibling::div//ol"));
		//get button list
		List<WebElement> button_list = sp.getChildElementByParent(heading_list, By.xpath("./following-sibling::div//span[starts-with(@id,\"button\")]"));
		
		//get test data from excel file
		Map<String, Integer> rowCellMap = excel.getExcelFile(dataFilePath, sheetName);
		//headers with index: 0 
		expected_thumbnail_size = rowCellMap.get("row");
		int thumbnails_size = driver.findElements(By.xpath(thumbnail_xpath)).size();
		Boolean result = true;
		if (expected_thumbnail_size == thumbnails_size) {
			//get list of header data
			List<String> heading_data_list = excel.getDataList_byColumn(dataFilePath, sheetName, 0);
			List<String> title_data_list = excel.getDataList_byColumn(dataFilePath, sheetName, 1);
			List<String> button_data_list = excel.getDataList_byColumn(dataFilePath, sheetName, 2);
			List<String> modal_data_ID = excel.getDataList_byColumn(dataFilePath, sheetName, 3);
			List<String> modal_data_title = excel.getDataList_byColumn(dataFilePath, sheetName, 4);
			List<String> modal_data_des = excel.getDataList_byColumn(dataFilePath, sheetName, 5);
			//get modal ID list
//			List<WebElement> modal_ID_list = getModalID_list(modal_data_ID);
			WebElement modal_ID;
			WebElement modal_title;
			WebElement modal_des;
			WebElement modal_button;
			
			Boolean flag = false;
			Map<Integer, Boolean> check = new HashMap<>(heading_data_list.size());
			for(int i = 0; i < heading_data_list.size(); i++) {
				flag = false;
				if (heading_data_list.get(i) == null || heading_data_list.get(i) == "") {
//					flag = false; 
					System.out.println("the heading data is null: row " + (i + 1));
				} else {
					for (int j = 0; j < heading_list.size(); j++) {
						if(heading_data_list.get(i).trim().equals(heading_list.get(j).getText().trim())) {
							flag = true;
							check.put(1, flag); //heading
							if (title_data_list.get(i) == "" && title_list.get(j) != null) {
								check.put(2, false); //title
								System.out.println("Heading: " + heading_data_list.get(i) + ": Wrong title. Expected: null");
							} else if(!title_data_list.get(i).trim().equals(title_list.get(j).getText().trim())) {
								System.out.println("Heading: " + heading_data_list.get(i) + ": Wrong title. Expected: \n" + title_data_list.get(i));
								check.put(2, false); //title
							} else {
								check.put(2, true); //title
							}
							if (button_data_list.get(i) == "" && button_list.get(j) != null) {
								System.out.println("Heading: " + heading_data_list.get(i) + ": Wrong button text. Expected: null");
								check.put(3, false); //title
							} else if(!button_data_list.get(i).equals(button_list.get(j).getText())) {
								System.out.println("Heading: " + heading_data_list.get(i) + ": Wrong button text. Expected: " + button_data_list.get(i) 
									+ "\tActual: " + button_list.get(j).getText());
								check.put(3, false); //button
							} else {
								check.put(3, true); //button
								//check modal content
								button_list.get(j).click();
								if (modal_data_ID.get(i) == null || modal_data_ID.get(i) == "") {
									System.out.println("the modal data is null: row " + (i + 1));
								} else {
									modal_ID = driver.findElement(By.xpath(modal_data_ID.get(i)));
									sp.waitForElementVisible(By.xpath(modal_data_ID.get(i)));									
									if (modal_ID.isDisplayed() == true) {
										check.put(4, true);
										try {
											modal_title = modal_ID.findElement(By.xpath(".//h4"));
										} catch (NoSuchElementException e) {
											modal_title = null;
										}
										if (modal_data_title.get(i) == "" && modal_title != null) {
											System.out.println("the modal title is null: row " + (i + 1));
											check.put(5, false);
										} else if (!modal_data_title.get(i).trim().equals(modal_title.getText().trim())){
											check.put(5, false);
											System.out.println("Heading: " + heading_data_list.get(i) + ": Wrong modal title. Expected: " + modal_data_title.get(i)
											+ " Actual: " + modal_title.getText());
										} else {
											check.put(5, true);
										}
										try {
											modal_des = modal_ID.findElement(By.xpath(".//div[@class=\"modal-body\"]"));
										} catch (NoSuchElementException e) {
											modal_des = null;
										}
										if (modal_data_des.get(i) == "" && modal_des != null) {
											System.out.println("the modal description is null: row " + (i + 1));
											check.put(6, false);
										} else if (!modal_data_des.get(i).trim().equals(modal_des.getText().trim())){
											check.put(6, false);
											System.out.println("Heading: " + heading_data_list.get(i) + ": Wrong modal description. Expected: " + modal_data_des.get(i)
											+ " Actual: " + modal_des.getText());
										} else {
											check.put(6, true);
										}
										try {
											modal_button = modal_ID.findElement(By.xpath(".//button[text()=\"Close\"]"));
										} catch (NoSuchElementException e) {
											modal_button = null;
										}
										if (!modal_button.isDisplayed()) {
											System.out.println("the modal button is not displayed: row " + (i + 1));
											check.put(7, false); //modal button
										} else {
											check.put(7, true);
											modal_button.click();
										}
									} else {
										System.out.println("Heading: " + heading_data_list.get(i) + ": Modal wasn't displayed.");
										check.put(4, false);
										check.put(5, false);
										check.put(6, false);
										check.put(7, false);
									}
								}
//								modal_ID = null;
//								modal_title = null;
//								modal_des = null;
//								modal_button = null;
//								break;//end loop for j
							}
						}
					}
				}
				if (flag == false) {
					System.out.println("Expcetd heading: " + heading_data_list.get(i) + " is not match/exist in UI.");
					check.put(1, flag); //heading
					check.put(2, flag);
					check.put(3, flag);
					check.put(4, false);
					check.put(5, false);
					check.put(6, false);
					check.put(7, false);
				}
				for (int stt = 1; stt <= rowCellMap.get("cell"); stt++) {
					if (check.get(stt).equals(false)) {
						result = false;
					}
				}
				if(result) {
					excel.writeExcelFileAtPosition(dataFilePath, sheetName, i+1, 6, "PASSED"); //i is counted from array list
				} else {
					excel.writeExcelFileAtPosition(dataFilePath, sheetName, i+1, 6, "FAILED");
				}
			}
		} else {
			System.out.println("The number of thumbnails displayed is not complete!");
			result = false;
		}
//		System.out.println(result);
		return result;
	}
}
