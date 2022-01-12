package pageclasses;

import java.sql.Array;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import utilities.GenericMethods;
import base.*;

public class Page_Team {

	ExtentTest test;
	WebDriver driver = null;
	GenericMethods gm;
	PlayerNBA player;

	private static final Logger log = LogManager.getLogger(Page_Team.class.getName());

	@FindBy(id = "yui_3_18_1_2_1638642550386_4179")
	WebElement addPlayerBtn;

	@FindBy(css = "//a[@class='_yb_145wq']")
	WebElement loginBtn;

	@FindBy(id = "login-username")
	WebElement usernameField;

	@FindBy(id = "login-passwd")
	WebElement passwordField;

	@FindBy(id = "login-signin")
	WebElement nextLoginBtn;

	@FindBy(xpath = "//a[text()=\"Create Trade\"]")
	WebElement createTradeBtn;

	@FindBy(id = "statTable0")
	WebElement playerGrid;

	// Constructor
	public Page_Team(WebDriver driver, ExtentTest test) {
		this.driver = driver;
		this.test = test;
		this.gm = new GenericMethods(this.driver);
		PageFactory.initElements(driver, this);
	}

	/***
	 * 
	 * @param username
	 * @param password
	 */
	public void Login(String username, String password) {

		gm.clickWhenReady(By.className("_yb_145wq"), 5);
		log.debug("In login method...");
		usernameField.sendKeys(username);
		nextLoginBtn.click();
		passwordField.sendKeys(password);
		nextLoginBtn.click();
	}
/***
 * 
 */
	public void clickCreateTradeBtn() {
		createTradeBtn.click();
		test.log(LogStatus.INFO, "Clicked on the 'Add Player' button");
	}
/***
 * 
 * @return
 */
	public List<String> getPlayersName() {

		List<String> playerlist = new ArrayList<String>();

		gm.waitForElement(By.id("statTable0"), 5);

		List<WebElement> trElements = playerGrid
				.findElements(By.xpath("//div[starts-with(@class, 'ysf-player-name')]//a"));

		for (WebElement element : trElements) {
			playerlist.add(element.getText());
		}

		return playerlist;
	}
/***
 * 
 * @param filterTeamStatsBy
 * @return
 * @throws InterruptedException
 */
	public Map<String, PlayerNBA> getMapPlayers(String filterTeamStatsBy) throws InterruptedException {
		Map<String, String> playerMap = new LinkedHashMap<String, String>();
		Map<String, PlayerNBA> teamMap = new LinkedHashMap<String, PlayerNBA>();

		// Click on the filter parameter to filter players
		String tempXPath = "//div[@id='statsubnav']//a[contains(text(), '" + filterTeamStatsBy + "')]";
		driver.findElement(By.xpath(tempXPath)).click();
		Thread.sleep(1000);

		// First grab all the player names on the team and put it in a list
		List<String> listplayer = this.getPlayersName();
		// Then iterate over the list of names putting each in a map
		for (int i = 0; i < listplayer.size(); i++) {
			playerMap = this.getMapPlayerInfo(listplayer.get(i));
			player = new PlayerNBA(playerMap);
			teamMap.put(listplayer.get(i), player);
		}

		return teamMap;
	}
/***
 * 
 * @param player
 * @return
 */
	private Map<String, String> getMapPlayerInfo(String player) {
		String baseXPath;
		
		// The baseXPath should be at the Position button
		baseXPath = "//a[text()=" + "\"" + player + "\""
				+ "]//parent::div//parent::div//parent::div//parent::td//preceding-sibling::td";

		Map<String, String> playerMap = new LinkedHashMap<String, String>();
		// First add the player name to the Map
		playerMap.put("Name", player);
		// Grab the Current position and put in Map
		playerMap.put("CurrentPosition", driver.findElement(By.xpath(baseXPath)).getText());

		// Need a special XPath to get the Eligible Positions of the Player
		// note: Probably want to change this bit as we're only grabbing UI positions, good enough for now
		String posXPath = baseXPath
				+ "//following-sibling::td//following-sibling::td//following-sibling::div//following-sibling::div//span";

		playerMap.put("EligiblePositions", driver.findElement(By.xpath(posXPath)).getText());
		
		// Now move down the grid to grab stats
		baseXPath = baseXPath
				+ "//following-sibling::td//following-sibling::td//following-sibling::td//following-sibling::td//following-sibling::td";

		for (int i = 0; i < 14; i++) {
			baseXPath = baseXPath + "//following-sibling::td";

			switch (i) {
			case 0:
				playerMap.put("Rank", driver.findElement(By.xpath(baseXPath)).getText());
				break;

			case 2:
				playerMap.put("MPG", driver.findElement(By.xpath(baseXPath)).getText());
				break;

			case 3:
				playerMap.put("FG M/A", driver.findElement(By.xpath(baseXPath)).getText());
				break;

			case 4:
				playerMap.put("FG%", driver.findElement(By.xpath(baseXPath)).getText());
				break;

			case 5:
				playerMap.put("FT M/A", driver.findElement(By.xpath(baseXPath)).getText());
				break;

			case 6:
				playerMap.put("FT%", driver.findElement(By.xpath(baseXPath)).getText());
				break;

			case 7:
				playerMap.put("3PM", driver.findElement(By.xpath(baseXPath)).getText());
				break;

			case 8:
				playerMap.put("PTS", driver.findElement(By.xpath(baseXPath)).getText());
				break;

			case 9:
				playerMap.put("REB", driver.findElement(By.xpath(baseXPath)).getText());
				break;

			case 10:
				playerMap.put("AST", driver.findElement(By.xpath(baseXPath)).getText());
				break;

			case 11:
				playerMap.put("ST", driver.findElement(By.xpath(baseXPath)).getText());
				break;

			case 12:
				playerMap.put("BLK", driver.findElement(By.xpath(baseXPath)).getText());
				break;

			case 13:
				playerMap.put("TO", driver.findElement(By.xpath(baseXPath)).getText());
				break;
			}
		}
		System.out.println("Player Map is " + playerMap);
		return playerMap;
	}
/***
 * Deprecate!!
 * @param playerInputMap
 * @param inputSortCategory
 */
	private void logTeam(Map<String, Map> playerInputMap, String inputSortCategory) {

		Map<String, Map> value = new LinkedHashMap<String, Map>();
		test.log(LogStatus.INFO, "Team stats for " + inputSortCategory + " are: ");
		System.out.println("Team stats for " + inputSortCategory + " are: ");

		for (String playerName : playerInputMap.keySet()) {
			String tempy = playerName + ": ";
			value = playerInputMap.get(playerName);

			for (String stat : value.keySet()) {

				tempy = tempy + stat + ":" + value.get(stat) + " ";
			}

			System.out.println(tempy);
			test.log(LogStatus.INFO, tempy);
		}

	}
/***
 * Deprecate!!
 * @param player
 */
	public void selectPlayer(String player) {

		WebElement playerGrid = driver.findElement(By.id("statTable0"));
		List<WebElement> trElements = playerGrid.findElements(By.tagName("a"));

		for (WebElement element : trElements) {
			if (element.getText().contains(player)) {
				System.out.println(element.getText());
				String playerHref = element.getAttribute("href");
				System.out.println(playerHref);
				String playerAlertXref = "//a[@href='" + playerHref + "']//parent::div//preceding-sibling::span//a";
				System.out.println(playerAlertXref);
				WebElement playerAlert = element.findElement(By.xpath(playerAlertXref));
				playerAlert.click();
			}
		}
	}
}
