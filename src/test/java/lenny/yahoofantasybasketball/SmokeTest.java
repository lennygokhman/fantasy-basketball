package lenny.yahoofantasybasketball;


import base.BaseTestSuite;
import base.ExtentFactory;
import base.Player;
import pageclasses.*;
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
	Page_Team pageteam;
	Page_Players pageplayers;
	Player player;

	@BeforeClass
	public void beforeClass() {
		
		pageteam = new Page_Team(driver, test);
		pageplayers = new Page_Players(driver, test);
		player = new Player();

	}
	
	@Test
	 public void smoketest() throws Exception {
		
		Map<String, Map> playerMapPlayerPage = new LinkedHashMap<String, Map>();
		
		pageteam.Login("pedrog064", "5279316Lg");
		
		pageteam = new Page_Team(driver, test);
		
		pageteam.getMapPlayerSet("Last 7 Days");
	
		
		WebElement temp = driver.findElement( By.xpath("//a[text()='Players']"));
		temp.click();
		
		// pageplayers.setStatsDropdown("Today (live)");
		Thread.sleep(2000);

		pageplayers.sortPlayers("PTS");
		
		Thread.sleep(3000);
		pageplayers = new Page_Players(driver, test);

		playerMapPlayerPage = pageplayers.getMapPlayerSet(5);
		
		pageplayers.logLeaders(playerMapPlayerPage, "PTS");
	
	}	
	
	public void tearDown(ITestResult testResult) throws IOException {
		if (testResult.getStatus() == ITestResult.FAILURE) {
			String path = Screenshots.takeScreenshot(driver, testResult.getName());
			String imagePath = test.addScreenCapture(path);
			test.log(LogStatus.FAIL, "Verify Welcome Text Failed", imagePath);
		}
	}
}