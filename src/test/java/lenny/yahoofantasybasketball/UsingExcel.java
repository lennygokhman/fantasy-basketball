package lenny.yahoofantasybasketball;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import base.BaseTestSuite2;
import base.ExtentFactory;
import page.classes.Page_team;
import utilities.Constants;
import utilities.ExcelUtility;

public class UsingExcel extends BaseTestSuite2 {
	ExtentReports report;
	ExtentTest test;
	Page_team tp;

	public void setUp() throws Exception {
		report = ExtentFactory.getInstance();
		test = report.startTest("Smoke test Fantasy Basketball");

		test.log(LogStatus.INFO, "Browser Started...");
	}
	
	@DataProvider(name = "loginData")
	public Object[][] dataProvider() {
		Object[][] testData = ExcelUtility.getTestData("Player_Click");
		return testData;
	}

	@Test(dataProvider="loginData")
	public void testUsingExcel(String LeagueId, String TeamId, String Player) throws Exception {
		
		baseUrl = Constants.URL + LeagueId + "/" + TeamId;
		driver.get(baseUrl);
		
		tp = new Page_team(driver, test);
		
		Thread.sleep(3000);
		
		tp.Login("pedrog064", "5279316Lg");
		
		List<String> listplayer = tp.getPlayersName();
		
		for (int i = 0; i < listplayer.size(); i++ ) {
			System.out.println(listplayer.get(i));
			
			}
	}
	
	@AfterClass
	public void tearDown() throws Exception {
	    //driver.quit();
	}
}