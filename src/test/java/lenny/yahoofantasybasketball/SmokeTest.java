package lenny.yahoofantasybasketball;


import base.BaseTestSuite;
import base.ExtentFactory;
import base.PlayerNBA;
import base.Team;
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
	PlayerNBA player;

	@BeforeClass
	public void beforeClass() {
		
		pageteam = new Page_Team(driver, test);
		pageplayers = new Page_Players(driver, test);
		//player = new Player();

	}
	
	@Test
	 public void smoketest() throws Exception {
		
		Map<String, PlayerNBA> playerMapPlayerPage = new LinkedHashMap<String, PlayerNBA>();
		Map<String, PlayerNBA> teamMap = new LinkedHashMap<String, PlayerNBA>();
		PlayerNBA player;
		Team team;
		
		List<String> playerList = new ArrayList<String>();
		
		pageteam.Login("pedrog064", "5279316Lg");
		
		pageteam = new Page_Team(driver, test);

		teamMap = pageteam.getMapPlayers("Last 7 Days");
		
		team = new Team(teamMap);
		
		System.out.println(teamMap);
//		System.out.println("PG is " + team.posPG.name);
//		System.out.println("SG is " + team.posSG.name);
//		System.out.println("G is " + team.posG.name);
//		System.out.println("SF is " + team.posSF.name);
//		System.out.println("PF is " + team.posPF.name);
//		System.out.println("F is " + team.posF.name);
//		System.out.println("C1 is " + team.posC1.name);
//		System.out.println("C2 is " + team.posC2.name);
//		System.out.println("Util1 is " + team.posUtil1.name);
//		System.out.println("Util2 is " + team.posUtil2.name);
//		System.out.println("Bench1 is " + team.posBench1.name);
//		System.out.println("Bench2 is " + team.posBench2.name);
//		System.out.println("Bench3 is " + team.posBench3.name);
	//	System.out.println("Bench4 is " + team.posBench4.name);
		System.out.println("IL1 is " + team.posIL1.name);
		System.out.println("IL2 is " + team.posIL2.name);
		System.out.println("IL1 is " + team.posIL3.name);
		
		System.out.println("Team map is: " + teamMap);
		System.out.println("Team is: " + team);
		
		playerList = pageteam.getPlayersName();
		
		for (String element : playerList) {
			player = teamMap.get(element);
			//int i=Integer.parseInt(player.assists);
			System.out.println(player.name + " is at " + player.currentposition );
			
		}
	
		WebElement temp = driver.findElement( By.xpath("//a[text()='Players']"));
		temp.click();
		
		// pageplayers.setStatsDropdown("Today (live)");
		Thread.sleep(2000);

		pageplayers.sortPlayers("PTS");
		
		Thread.sleep(3000);
		pageplayers = new Page_Players(driver, test);

		// playerMapPlayerPage = pageplayers.getMapPlayerSet(5);
		playerMapPlayerPage = pageplayers.getMapPlayerSet(10);
		//pageplayers.logLeaders(playerMapPlayerPage, "PTS");
		System.out.println(playerMapPlayerPage);
		player = playerMapPlayerPage.get("C. Duarte");
		System.out.println("Yo " + player.fieldGoalPer);
		
	
	}	
	
	public void tearDown(ITestResult testResult) throws IOException {
		if (testResult.getStatus() == ITestResult.FAILURE) {
			String path = Screenshots.takeScreenshot(driver, testResult.getName());
			String imagePath = test.addScreenCapture(path);
			test.log(LogStatus.FAIL, "Verify Welcome Text Failed", imagePath);
		}
	}
}