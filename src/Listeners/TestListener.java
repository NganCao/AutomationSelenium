package Listeners;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import Supports.CaptureHelpers;
import Supports.Setup;

public class TestListener implements ITestListener {
	@Override		
	public void onFinish(ITestContext arg0) {					
		// TODO Auto-generated method stub				
	      		
	}		

	@Override		
    public void onStart(ITestContext arg0) {					
        // TODO Auto-generated method stub				
        		
    }		

    @Override		
    public void onTestFailedButWithinSuccessPercentage(ITestResult arg0) {					
        // TODO Auto-generated method stub				
        		
    }		

    @Override		
    public void onTestFailure(ITestResult result) {					
        // TODO Auto-generated method stub				
    	System.out.println("Failed: " + result.getName());
    	CaptureHelpers captureHelpers = new CaptureHelpers(Setup.getWebDriver());
        try {
            captureHelpers.takeScreenshot(result, result.getName());
        } catch (Exception e) {
            System.out.println("Exception while taking screenshot " + e.getMessage());
        }
    }		

    @Override		
    public void onTestSkipped(ITestResult arg0) {					
        // TODO Auto-generated method stub				
        		
    }		

    @Override		
    public void onTestStart(ITestResult arg0) {					
        // TODO Auto-generated method stub				
        		
    }		

    @Override		
    public void onTestSuccess(ITestResult result) {					
        // TODO Auto-generated method stub				
    	System.out.println(result.getInstanceName() + " " + result.getName() + " is Passed!");
    }
}
