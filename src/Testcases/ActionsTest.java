package Testcases;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import Pages.ActionsPage;
import Pages.HomePage;
import Supports.Setup;
import Supports.SupportMethods;

public class ActionsTest extends Setup {
	private WebDriver driver;
	private HomePage homepage;
	private ActionsPage page;
	private SupportMethods sp;

	private String currentWindow;

	@BeforeClass
	public void setUp() {
		driver = getWebDriver();
		homepage = new HomePage(driver);
		sp = new SupportMethods(driver);
	}

	@Test(priority = 1)
	public void naviagteTo_ActionPage() throws Exception {
		currentWindow = driver.getWindowHandle();
		page = homepage.clickActions();
		sp.switchTab(currentWindow);
	}

	@Test(priority = 2)
	public void check_general_information() throws Exception {
		SoftAssert sa = new SoftAssert();
		sa.assertTrue(page.verifyURL(), "URL is wrong");
		sa.assertTrue(page.verifyTitle(), "Title is wrong");
		sa.assertTrue(page.verifyHeader(), "Header is wrong");
		sa.assertAll();
	}

	@Test(priority = 3, description = "drag and drop button")
	public void check_DragAndDrop() throws Exception {
		Assert.assertTrue(page.verifyText_DragElemennt());
		Assert.assertTrue(page.verifyText_DropElemennt());
		Assert.assertTrue(page.verifyDragAndDrop(), "Cannot drag and drop");
	}

	@Test(priority = 4, description = "double click button")
	public void check_DoubleClick() throws Exception {
		Assert.assertTrue(page.verifyText_doubleClick());
		Assert.assertTrue(page.verifyDoubleClick(), "Double click is wrong");
	}

	/**
	 * hover button
	 * 
	 * @throws Exception
	 */
	@Test(priority = 5, description = "hover button")
	public void check_hoverBtn() throws Exception {
		Assert.assertTrue(page.verifyHoverOver(), "Hover text is wrong");
	}

	@Test(priority = 6, description = "click and hold button")
	public void check_clickAndHoldBtn() throws Exception {
		Assert.assertTrue(page.verifyText_clickAndHold(), "Click and Hold text is wrong");
		Assert.assertTrue(page.verifyClickAndHold(), "Click and Hold is failed");
	}

}
