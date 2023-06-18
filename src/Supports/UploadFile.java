package Supports;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class UploadFile {

	private WebDriver driver;
	
	public UploadFile (WebDriver driver) {
		this.driver = driver;
	}
	
	public void uploadFile_ByRobot (String filePath) throws InterruptedException {
		Robot rb = null;
		try {
			rb = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}
		Thread.sleep(1);
		//Copy filePath to clipboard
		StringSelection path = new StringSelection(filePath);
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(path, null);
		//Paste => ctrl + V
		rb.keyPress(KeyEvent.VK_CONTROL);
		rb.keyRelease(KeyEvent.VK_CONTROL);
		rb.keyPress(KeyEvent.VK_V);
		rb.keyRelease(KeyEvent.VK_V);
		//Enter
		Thread.sleep(1);
		rb.keyPress(KeyEvent.VK_ENTER);
		rb.keyRelease(KeyEvent.VK_ENTER);
		Thread.sleep(2);
		System.out.println("uploaded");
	}
	
	public void uploadFile_BySendKeys (By inputFile, String filePath) throws Exception {
		Thread.sleep(1);
		driver.findElement(inputFile).sendKeys(filePath);
	}
}
