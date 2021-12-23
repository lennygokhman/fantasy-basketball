package lenny.yahoofantasybasketball;

import base.BaseTestSuite;
import base.ExtentFactory;
import base.Player;
import page.classes.TeamPage;
import utilities.GenericMethods;
import utilities.Screenshots;

import java.io.IOException;
import java.sql.Array;
import java.time.Duration;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class SmokeTest extends BaseTestSuite {
//	ExtentReports report;
//	ExtentTest test;
	TeamPage tp;
	Player player;

	@BeforeClass
	public void beforeClass() {
//		report = ExtentFactory.getInstance();
//		test = report.startTest("Smoke test Fantasy Basketball");
		
		tp = new TeamPage(driver, test);
		player = new Player();
		
		test.log(LogStatus.INFO, "Starting test...");

	}
	
	@Test
	 public void smoketest() throws Exception {
		
		tp.Login("pedrog064", "5279316Lg");
		
		List<String> listplayer = tp.getPlayersName();

		Map<String, ArrayList> teamMap = new LinkedHashMap<String, ArrayList>();

		ArrayList<String> playerList = new ArrayList<String>();
		
		driver.findElement(By.xpath("//div[@id='statsubnav']//a[contains(text(), 'Last 30 Days')]")).click();
		Thread.sleep(1000);
		
		tp = new TeamPage(driver, test);
		
		for (int i = 0; i < listplayer.size(); i++ ) {
			//System.out.println(listplayer.get(i));
			//teamMap.put(i, listplayer.get(i));				
			
			playerList = tp.getListPlayerInfo(listplayer.get(i));
			teamMap.put(listplayer.get(i), playerList);
			//System.out.println("Player List: " + teamMap2);
			}
		// teamMap now contains map of players on the team
		
		System.out.println("Team Map: " + teamMap);
		test.log(LogStatus.PASS, "The test passed!!");
		
	}	
	
	public void tearDown(ITestResult testResult) throws IOException {
		if (testResult.getStatus() == ITestResult.FAILURE) {
			String path = Screenshots.takeScreenshot(driver, testResult.getName());
			String imagePath = test.addScreenCapture(path);
			test.log(LogStatus.FAIL, "Verify Welcome Text Failed", imagePath);
		}
	}
}