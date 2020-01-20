package main.java.utilities;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.UnreachableBrowserException;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

public abstract class BaseTest implements ISeleniumTest
{
	private WebDriver driver;
	private String testName;

	@Override
	public WebDriver getWebDriver() {
		return driver;
	}

	@Override
	public String getTestName() {
		return testName;
	}

	@BeforeSuite (alwaysRun = true)
	@Parameters({
		"baseUrl"
	})
	public void setUp(String baseUrl) throws Exception{
		String path = System.getProperty("user.dir");
		System.setProperty("webdriver.chrome.driver", path+"//library//chromedriver");
		driver = new ChromeDriver();	
		if(driver == null) {
			System.out.println("The driver could not be intialised");
			throw new UnreachableBrowserException("The driver could not be intialised");

		}
		System.out.println("Chrome Browser Launched");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		System.out.println("baseURL: "+baseUrl);
		driver.get(baseUrl);
		new WebDriverWait(driver, 30).until(webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
		driver.manage().window().maximize();
	}

	@AfterTest(alwaysRun = true)
	public void teardown(){
		try {
			if(driver!=null) {
				driver.quit();
			}
		}
		catch(Exception ex) {
			System.out.println("tearDown - could not teardown due to: "+ex.getMessage());

		}
	}	

}
