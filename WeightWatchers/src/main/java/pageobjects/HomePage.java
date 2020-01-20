package main.java.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.FindBy;

public class HomePage {
	WebDriver driver;

	public static String pageTitle ="WW (Weight Watchers): Weight Loss & Wellness Help";

	@FindBy(className="find-a-meeting")
	WebElement findAStudio;

	public HomePage(WebDriver driver){
		this.driver = driver;
		//This initElements method will create all WebElements
		PageFactory.initElements(driver, this);
	}

	// Click on 'Find a Studio'
	public void clickToFindStudio(){
		findAStudio.click();
	}  

	// Get the title of Home Page
	public String getHomePageTitle(){
		return driver.getTitle();
	}

}
