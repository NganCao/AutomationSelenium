package Supports;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;
import org.testng.ITestResult;
import org.testng.Reporter;

public class CaptureHelpers {

	private WebDriver driver;
	private String image_report_folder = System.getProperty("user.dir");
	private SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH-mm-ss");
	private String folderScreenshot = "\\Report\\Screenshot";
	private String folderRecord = "\\Report\\Record";
	
	public CaptureHelpers (WebDriver driver) {
		this.driver = driver;
	}
	
	public void takeScreenshot(ITestResult result, String screenName) throws InterruptedException {
        //passed = SUCCESS và failed = FAILURE
        if (ITestResult.FAILURE == result.getStatus()) {
            try {
                TakesScreenshot ts = (TakesScreenshot) driver;
                File source = ts.getScreenshotAs(OutputType.FILE);
                File theDir = new File(image_report_folder + folderScreenshot);
                if (!theDir.exists()) {
                    theDir.mkdirs(); //create new folder
                }
                FileHandler.copy(source, new File(image_report_folder + folderScreenshot + "\\" + screenName + "_" + dateFormat.format(new Date()) + ".png"));
                System.out.println("Screenshot taken: " + screenName);
        		Reporter.log("Screenshot taken current URL: " + driver.getCurrentUrl(), true);
            } catch (Exception e) {
                System.out.println("Exception while taking screenshot " + e.getMessage());
            }
        }
    }
}
