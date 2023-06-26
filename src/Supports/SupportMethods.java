package Supports;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class SupportMethods {

	private WebDriver driver;
	private WebDriverWait wait;

	public SupportMethods(WebDriver driver) {
		this.driver = driver;
		wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	}

	public void waitForElementVisible(By testObject) {
		wait.until(ExpectedConditions.visibilityOfElementLocated(testObject));
	}

	public void waitFoElementClickable(WebElement testObject) {
		wait.until(ExpectedConditions.elementToBeClickable(testObject));
	}

	public String getTitle() {
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
		return driver.getTitle();
	}

	public List<WebElement> getListElements(By testObject) {
		List<WebElement> list = new ArrayList<>();
		try {
			wait.until(ExpectedConditions.visibilityOfAllElements(driver.findElements(testObject)));
			for (WebElement e : driver.findElements(testObject)) {
				if (e.isDisplayed()) {
					list.add(e);
				} else
					list.add(null);
			}
		} catch (Exception e) {
			list = null;
			System.out.println(e);
		}
		return list;
	}

	public WebElement getElement(By testObject) {
		wait.until(ExpectedConditions.visibilityOfElementLocated(testObject));
		return driver.findElement(testObject);
	}

	public boolean checkElementIsdisplay(By testObject) {
		WebElement e = getElement(testObject);
		return e.isDisplayed();
	}

	public List<WebElement> getChildElementByParent(List<WebElement> parent_list, By child) {
		List<WebElement> child_list = new ArrayList<>();
		WebElement element;
		for (WebElement e : parent_list) {
			try {
				wait.until(ExpectedConditions.visibilityOf(e.findElement(child)));
				element = e.findElement(child);
			} catch (NoSuchElementException err) {
				element = null;
				err.printStackTrace();
			}
			child_list.add(element);
		}
		return child_list;
	}

	/**
	 * click the specified button
	 * 
	 * @param testObject
	 */
	public void clickBtn(By testObject) {
		wait.until(ExpectedConditions.visibilityOfElementLocated(testObject));
		driver.findElement(testObject).click();
	}

	/**
	 * 
	 * @param testObject
	 * @param key
	 */
	public void enterData(By testObject, String key) {
		wait.until(ExpectedConditions.visibilityOfElementLocated(testObject));
		driver.findElement(testObject).sendKeys(key);
	}

	public void closeAllOpenTabExceptMainTab(String currentTab) {
		// get all new open tab
		Set<String> openTabs = driver.getWindowHandles();

		for (String tab : openTabs) {
			if (!tab.equals(currentTab)) {
				driver.switchTo().window(tab);
				driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(2));
				driver.close();
			}
		}
		driver.switchTo().window(currentTab);
	}

	public void switchTab(String currentTab) {
		// get all new open tab
		Set<String> openTabs = driver.getWindowHandles();

		for (String tab : openTabs) {
			if (!tab.equals(currentTab)) {
				driver.switchTo().window(tab);
				driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(2));
			}
		}
	}

	public void switchBackCurrentTab(String currentTab) {
		driver.close();
		driver.switchTo().window(currentTab);
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(2));
	}

	/**
	 * verify the current url contains the base url
	 * 
	 * @param url
	 * @return
	 */
	public boolean verifyUrl(String url) {
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
		return driver.getCurrentUrl().contains(url);
	}

	/**
	 * verify text of the checking element
	 * 
	 * @param testObject
	 * @param expected
	 * @return
	 */
	public boolean verifyText(By testObject, String expected) {
		wait.until(ExpectedConditions.visibilityOfElementLocated(testObject));
		return driver.findElement(testObject).getText().equals(expected);
	}

	private String getPlaceHolder(By testObject) {
		return driver.findElement(testObject).getAttribute("placeholder");
	}

	public void verifyPlaceHolder(By testObject, String expectedText) {
		Assert.assertEquals(getPlaceHolder(testObject), expectedText);
	}

	private String getButton_value(By testObject) {
		waitForElementVisible(testObject);
		return driver.findElement(testObject).getAttribute("value");
	}

	public void verifyButton_value(By testObject, String expectedValue) {
		Assert.assertEquals(getButton_value(testObject), expectedValue);
	}

	/**
	 * 
	 * @param str
	 * @param replaceOld
	 * @param replaceNew
	 * @return
	 */
	public String replaceString(String str, String replaceOld, String replaceNew) {
		return str.replaceAll(replaceOld, replaceNew);
	}

	public void compare_2_list_strings(List<String> list1, List<String> list2, int startPosition,
			String equalsNotEquals) {
		if (list1.size() == list2.size()) {
			if (equalsNotEquals.equals("equals")) {
				for (int i = startPosition; i <= list1.size(); i++) {
					Assert.assertEquals(list1.get(i).trim(), list2.get(i).trim(),
							"In position " + i + " doesn't match.");
				}
			} else {
				for (int i = startPosition; i < list1.size(); i++) {
					Assert.assertEquals(list1.get(i).trim(), list2.get(i).trim(),
							"In position " + i + " doesn't match.");
				}
			}
		}
	}

}
