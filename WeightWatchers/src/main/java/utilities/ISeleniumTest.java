package main.java.utilities;

import org.openqa.selenium.WebDriver;

public interface ISeleniumTest {
	WebDriver getWebDriver();
	String getTestName();
}
