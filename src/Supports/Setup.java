package Supports;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

public class Setup {
 
	private WebDriver driver;
	private String driverPath = "C:\\Users\\Administrator\\Desktop\\Auto\\chromedriver.exe";
//	private String baseURL = "http://webdriveruniversity.com";
	
	public WebDriver getWebDriver() {
		return driver;
	}
	
	private void setDriver(String baseURL) {
		System.setProperty("webdriver.chrome.driver", driverPath);
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.navigate().to(baseURL);
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
		
	}
	
	@Parameters("baseURL")
	@BeforeClass
	public void setupBrowser(String baseURL) {
		try {
			setDriver(baseURL);
		} catch (Exception e) {
			System.out.print("Error " + e.getStackTrace());
		}
	}
	
	@AfterClass
	public void end() throws Exception {
		driver.quit();
	}
	
}
