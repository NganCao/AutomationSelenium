package Pages;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.Color;

import Supports.ExcelHelpers;
import Supports.SupportMethods;

public class ActionsPage {
	private WebDriver driver;
	private SupportMethods sp;
	private Actions action;
	private ExcelHelpers excel;
	private String url = "/Actions/index.html";
	private String header_txt = "The Key to Success is to take massive ACTION!";
	private String title_txt = "WebDriver | Actions";
	private String drag_able_txt = "DRAG ME TO MY TARGET!";
	private String drop_able_txt = "DROP HERE!";
	private String double_click_txt = "Double Click Me!";
	private String click_box_txt = "Click and Hold!";
	private List<String> btn_hover_str_list = new ArrayList<>();
	// xpath
	private By header = By.id("main-header");
	private By drag_able = By.id("draggable");
	private By drag_able_txt_xpath = By.xpath("//div[@id=\"draggable\"]//b");
	private By drop_able = By.id("droppable");
	private By drop_able_txt_xpath = By.xpath("//div[@id=\"droppable\"]//b");
	private By double_click = By.id("double-click");
	private By double_click_txt_xpath = By.xpath("//div[@id=\"double-click\"]/h2");
	private By div_hover = By.id("div-hover");
	private By div_hover_list = By.xpath("//div[@id=\"div-hover\"]/div");
	private By btn_hover_list = By.xpath("//div[@id=\"div-hover\"]//button");
	private By click_box = By.id("click-box");
	private By click_box_txt_xpath = By.xpath("//div[@id=\"click-box\"]/p");

	public ActionsPage(WebDriver driver) {
		this.driver = driver;
		sp = new SupportMethods(driver);
		action = new Actions(driver);
		excel = new ExcelHelpers();
	}

	private List<String> getList_btn_hover() {
		btn_hover_str_list.add("Hover Over Me First!");
		btn_hover_str_list.add("Hover Over Me Second!");
		btn_hover_str_list.add("Hover Over Me Third!");

		return btn_hover_str_list;
	}

	public boolean verifyURL() {
		return sp.verifyUrl(url);
	}

	public boolean verifyTitle() {
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(2));
		return title_txt.equals(driver.getTitle().trim());
	}

	public boolean verifyHeader() {
		return sp.verifyText(header, header_txt);
	}

	public boolean verifyText_DragElemennt() {
		return sp.verifyText(drag_able_txt_xpath, drag_able_txt);
	}

	public boolean verifyText_DropElemennt() {
		return sp.verifyText(drop_able_txt_xpath, drop_able_txt);
	}

	public boolean verifyText_doubleClick() {
		return sp.verifyText(double_click_txt_xpath, double_click_txt);
	}

	public boolean verifyText_clickAndHold() {
		return sp.verifyText(click_box_txt_xpath, click_box_txt);
	}

	public boolean verifyDragAndDrop() throws Exception {
		try {
			action.dragAndDrop(driver.findElement(drag_able), driver.findElement(drop_able)).build().perform();
			action.release().build().perform();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean verifyDoubleClick() throws Exception {
		WebElement e = driver.findElement(double_click);
		boolean rs = false;
		// click
		sp.waitFoElementClickable(e);
		try {
			action.click(e).perform();
		} catch (Exception err) {
			err.printStackTrace();
			action.click(e).perform();
		}
		Thread.sleep(500);
		if (!e.getAttribute("class").equals(e.getAttribute("class") + " double")) {
			if (!"#93CB5A".equalsIgnoreCase(Color.fromString(e.getCssValue("background-color")).asHex())) {
				rs = true;
			} else
				rs = false;
		}
		// double click
		if (rs == true) {
			rs = false;
			action.doubleClick(e).perform();
			Thread.sleep(500);
			if (e.getAttribute("class").equals("div-double-click double")) {
				if ("#93CB5A".equalsIgnoreCase(Color.fromString(e.getCssValue("background-color")).asHex())) {
					rs = true;
				} else
					rs = false;
			} else
				rs = false;
		}
		return rs;
	}

	/**
	 * check hover action
	 * 
	 * @return
	 * @throws InterruptedException
	 */
	public boolean verifyHoverOver() throws InterruptedException {
		getList_btn_hover(); // get list str button
		List<Boolean> result = new ArrayList<>();
		Alert alert;
		// get hover btn list
		List<WebElement> btn_element_list = driver.findElements(btn_hover_list); // **get list hover element
		List<WebElement> dropdown_navbar_list = null;
		dropdown_navbar_list = new ArrayList<>();
		if (btn_hover_str_list.size() == btn_element_list.size()) {
			// i: expected string list - j: actual text list
			for (int i = 0; i < btn_hover_str_list.size(); i++) {
				// check text
				for (int j = 0; j < btn_element_list.size(); j++) {
					if (btn_hover_str_list.get(i).equals(btn_element_list.get(j).getText().trim())) {
						try {
							dropdown_navbar_list = btn_element_list.get(j)
									.findElements(By.xpath(".//following-sibling::div[@class=\"dropdown-content\"]/a"));
						} catch (Exception e) {
							dropdown_navbar_list = null;
							e.printStackTrace();
						}
						// hover
						if (dropdown_navbar_list != null) {
							try {
								action.moveToElement(btn_element_list.get(j)).build().perform();
								for (WebElement e : dropdown_navbar_list) {
//									System.out.println(e.getText());
									try {
										sp.waitFoElementClickable(e);
									} catch (Exception err) {
										err.printStackTrace();
										action.moveToElement(btn_element_list.get(j)).build().perform();
										sp.waitFoElementClickable(e);
									}
									// e.click();
									action.click(e).build().perform();
									Thread.sleep(200);
									alert = driver.switchTo().alert();
									alert.accept();
									Thread.sleep(2000);
								}
								result.add(true);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
				}
			}
		}
		boolean temp = true;
		if (result.size() == btn_hover_str_list.size()) {
			for (boolean b : result) {
				if (b == false) {
					temp = false;
				}
			}

			if (temp == false) {
				return false;
			} else
				return true;
		} else
			return false;
	}

	public boolean verifyClickAndHold() throws Exception {
		// check Text if release/click/double click
		sp.waitForElementVisible(click_box);
		WebElement e = driver.findElement(click_box);
		boolean rs = false;
		try {
			action.click(e).build().perform();
		} catch (Exception err) {
			sp.waitForElementVisible(click_box);
			action.click(e).perform();
		}
		Thread.sleep(500);
		if ("Dont release me!!!".equals(driver.findElement(click_box).getText().trim()) && Color.fromString("tomato")
				.asRgb().equals(Color.fromString(e.getCssValue("background-color")).asRgb())) {
			try {
				action.doubleClick(e).perform();
			} catch (Exception err) {
				sp.waitForElementVisible(click_box);
				action.doubleClick(e).perform();
			}
			Thread.sleep(500);
			if ("Dont release me!!!".equals(driver.findElement(click_box).getText().trim()) && Color
					.fromString("tomato").asRgb().equals(Color.fromString(e.getCssValue("background-color")).asRgb())) {
				action.clickAndHold(e).perform();
				Thread.sleep(500);
				if ("Well done! keep holding that click now.....".equals(driver.findElement(click_box).getText().trim())
						&& "#00ff00".equalsIgnoreCase(Color.fromString(e.getCssValue("background-color")).asHex())) {
					rs = true;
				} else
					rs = false;
			} else
				rs = false;
		}
		return rs;
	}
}