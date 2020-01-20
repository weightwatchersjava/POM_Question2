package main.java.test;

import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import main.java.pageobjects.HomePage;
import main.java.pageobjects.Meetings;
import main.java.utilities.BaseTest;

public class WWTest extends BaseTest{

	HomePage homePage;
	Meetings meetings;

	@Parameters({"zipCode", "day"})
	@Test
	public void testWeightWatchers(String zipCode,String day) throws Exception{
		homePage = new HomePage(getWebDriver());
		meetings = new Meetings(getWebDriver());
		/* Verify 'Homepage' title */
		String homePageTitle = homePage.getHomePageTitle();
		Assert.assertTrue(homePageTitle.equals(HomePage.pageTitle));
		System.out.println("***************** Home Page Title Verified *****************");

		/* Clicking on 'Find a Studio' */
		homePage.clickToFindStudio();

		/* Verify 'Find a Meeting' page title */
		String meetingPageTitle = meetings.getPageTitle();
		Assert.assertTrue(meetingPageTitle.equals(Meetings.pageTitle));
		System.out.println("***************** Find a Meeting Page Title Verified *****************");

		/* Search ZipCode */
		meetings.enterZipCodeToFindLocation(zipCode);

		/* Printing the first LocationName and LocationDistance */
		meetings.getLocationNameAndDistance();
		System.out.println("***************** Printing the first LocationName and LocationDistance *****************");
		System.out.println("Meeting Location Name: "+Meetings.meetingLocationName);
		System.out.println("Meeting Location Distance: "+Meetings.meetingLocationDistance);

		/* Click on first search result */
		meetings.clickFirstSearchResult();
		Assert.assertTrue((meetings.getLocationName()).equals(Meetings.meetingLocationName));
		System.out.println("***************** Display Location Name and Search Result Verified  *****************");

		/* Print Today's Hours of operation */
		System.out.println("Today's Hours of Opertaion: "+meetings.getTodayHoursOfOperation());

		/* To print number of meetings for each person based of day (passed as a parameter from testng.xml) */
		meetings.printMeetings(day);

	}
}
