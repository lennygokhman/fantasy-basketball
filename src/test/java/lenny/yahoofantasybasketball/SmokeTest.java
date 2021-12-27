package lenny.yahoofantasybasketball;


import base.BaseTestSuite;
import base.ExtentFactory;
import base.Player;
import page.classes.*;
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
	Page_team pageteam;
	Page_players pageplayers;
	Player player;

	@BeforeClass
	public void beforeClass() {

		
		pageteam = new Page_team(driver, test);
		pageplayers = new Page_players(driver, test);
		player = new Player();
		
		test.log(LogStatus.INFO, "Starting test...");

	}
	
	@Test
	 public void smoketest() throws Exception {
		
		// pageteam.Login("pedrog064", "5279316Lg");
		
		List<String> listplayer = pageteam.getPlayersName();

		Map<String, ArrayList> teamMap = new LinkedHashMap<String, ArrayList>();
		Map<String, Map> playerMapPlayerPage = new LinkedHashMap<String, Map>();
		ArrayList<String> playerList = new ArrayList<String>();
		
		driver.findElement(By.xpath("//div[@id='statsubnav']//a[contains(text(), 'Last 30 Days')]")).click();
		Thread.sleep(1000);
		
		pageteam = new Page_team(driver, test);
		
		for (int i = 0; i < listplayer.size(); i++ ) {			
			playerList = pageteam.getListPlayerInfo(listplayer.get(i));
			teamMap.put(listplayer.get(i), playerList);
			}
		// teamMap now contains map of players on the team
		
		System.out.println("Team Map: " + teamMap);
		test.log(LogStatus.PASS, "The test passed!!");
		
		WebElement temp = driver.findElement( By.xpath("//a[text()='Players']"));
		temp.click();
		
		pageplayers.setStatsDropdown("Today (live)");
		Thread.sleep(2000);

		pageplayers.sortPlayers("PTS");
		
		Thread.sleep(3000);
		pageplayers = new Page_players(driver, test);

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