package main.java.pageobjects;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Meetings {
	WebDriver driver;

	public static String pageTitle ="Find WW Studios & Meetings Near You | WW USA";
	public static String meetingLocationName;
	public static String meetingLocationDistance;

	@FindBy(name="meetingSearch")
	WebElement findLocation;

	@FindBy(className="btn spice-translated")
	WebElement searchSubmit;

	@FindBy(className="bx-close-xsvg")
	WebElement closePopUp;

	@FindBy(className="location__name")
	List<WebElement> searchByLocationName;

	@FindBy(className="location__distance")
	List<WebElement> searchByLocationDistance;

	@FindBy(className="location__name")
	WebElement locationName;

	@FindBy(className="location__distance")
	WebElement locationDistance;

	@FindBy(xpath="//div[@class='hours-list-item-wrapper hours-list--currentday']/div")
	List<WebElement> todayHoursOfOperation;

	@FindBy(xpath ="//div[@class='schedule-detailed-day-label']")
	List<WebElement> scheduledDays;

	@FindBy(xpath ="//div[@class='schedule-detailed-day-label']//following-sibling::div")
	List<WebElement> scheduledTimeAndPerson;


	public Meetings(WebDriver driver){
		this.driver = driver;
		//This initElements method will create all WebElements
		PageFactory.initElements(driver, this);
	}

	// Enter ZipCode on 'Find Location' search box
	public void enterZipCodeToFindLocation(String zipCode){
		findLocation.sendKeys(zipCode);
		findLocation.sendKeys(Keys.ENTER);
	} 

	// Get the title of Meeting Page
	public String getPageTitle(){
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
		wait.until(ExpectedConditions.elementToBeClickable(By.className("bx-close-xsvg")));
		if(closePopUp.isDisplayed()) {
			closePopUp.click();
		}else {
			System.out.println("Pop-up is not visible");
		}		
		return driver.getTitle();
	}

	private By By(WebElement closePopUp2) {
		// TODO Auto-generated method stub
		return null;
	}

	// Get the Location Name and Distance of first result after search
	public void getLocationNameAndDistance(){
		meetingLocationName = getFirstElement(searchByLocationName).getText();
		meetingLocationDistance = getFirstElement(searchByLocationDistance).getText();
	}

	// Click on the first search result
	public void clickFirstSearchResult()
	{
		getFirstElement(searchByLocationName).click();
	}

	// Get the Location Name after clicking first search result
	public String getLocationName(){
		return locationName.getText();
	}

	// Get TODAY's hours of operation
	public String getTodayHoursOfOperation() {
		return operatingHours(todayHoursOfOperation);
	}

	// Print today's hours of operation
	public String operatingHours(List<WebElement> todayHoursOfOperation) {
		String operationalHours = null;
		if(todayHoursOfOperation.size()!=0) {
			operationalHours = todayHoursOfOperation.get(1).getText();
		}
		else {
			operationalHours ="No Scheduled Hours for today";
		}
		return operationalHours;
	}

	// To get the first element from the list
	public WebElement getFirstElement(List<WebElement> locator)
	{
		return locator.get(0);
	}

	// To calculate the number of meeting on a particular day of the week
	public void printMeetings(String day) {
		String item[] = getScheduleByDay(scheduledDays,day,scheduledTimeAndPerson);
		HashMap<String, Integer> map = new HashMap<>();
		if(item!=null) {
			for (String t : item) {
				if(t.matches("^[A-Z].*$")) {
					if (map.containsKey(t)) {
						map.put(t, map.get(t) + 1);

					} else {
						map.put(t, 1);
					}
				}
			}
			Set<String> keys = map.keySet();
			System.out.println("Number of Meetings scheduled for each person on "+day+" :");
			for (String key : keys) {
				System.out.println(key+" "+map.get(key));
			}
		}
	}

	// To get the Scheduled List from UI based on the Day of the week passed as param
	public String[] getScheduleByDay(List<WebElement> scheduledDays,String day,List<WebElement> scheduledTimeAndPerson) {
		String al[] = null;
		String schedule = null;
		for(int i=0;i<scheduledDays.size();i++) {
			if((scheduledDays.get(i).getText()).equalsIgnoreCase(day)) {
				schedule = scheduledTimeAndPerson.get(i).getText();
				if(!schedule.isEmpty())
					return al = schedule.split("\n");
				else {
					schedule = "No Meeting Scheduled on "+day;
					System.out.println(schedule);
				}
			}
		}
		return al;
	}
}
