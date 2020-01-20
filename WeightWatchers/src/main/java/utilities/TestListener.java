package main.java.utilities;

import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener{

	String filePath = System.getProperty("user.dir")+"/Screenshots/";

	@Override		
	public void onTestFailure(ITestResult result) {	
		try {
		System.out.println("***** Error "+result.getName()+" test has failed *****");
		String methodName=result.getName().toString().trim();
		System.out.println(methodName);
		Object currentClass = result.getInstance();
		WebDriver driver =((ISeleniumTest) currentClass).getWebDriver();
		takeScreenShot(methodName, driver);		
		}
		catch(Exception ex) {
			System.out.println("*********** Exception Occured ***********");
			ex.printStackTrace();
		}
	}	

	public void takeScreenShot(String methodName, WebDriver driver) {
		File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		//The below method will save the screenshot in the project directory with methodname
		try {
			FileUtils.copyFile(scrFile, new File(filePath+methodName+".png"));
			System.out.println("***Placed screen shot in "+filePath+" ***");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
