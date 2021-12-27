package base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

import utilities.GenericMethods;


public class BaseTestSuite {
	protected WebDriver driver;
	protected ExtentTest test;
	private String baseUrl;
	GenericMethods gm;
	ExtentReports report;
	
	
	
	@BeforeSuite
	public void beforeSuite() {

	}
	
	@Parameters({"browser", "leagueid", "teamid"})
	@BeforeClass
	public void beforeClass(String browser, String leagueid, String teamid) {
		String browserValue = browser.toString();
		String leagueidValue = leagueid.toString();
		String teamidValue = teamid.toString();
		
		report = ExtentFactory.getInstance();
		test = report.startTest("Smoke test Fantasy Basketball");
		if (browserValue.equals("firefox")) {
			driver = new FirefoxDriver();
		}
		else if (browserValue.equals("chrome")){
			driver = new ChromeDriver();
		}		
		gm = new GenericMethods(driver);
		
		baseUrl = "https://basketball.fantasysports.yahoo.com/nba/" + leagueidValue + "/" + teamidValue;
		gm.startBrowser(baseUrl, 10);

	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	
//		report.endTest(test);
//		report.flush();
	}

	@AfterSuite
	public void afterSuite() {
		report.endTest(test);
		report.flush();
	}

}
