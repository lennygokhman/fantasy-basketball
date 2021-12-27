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

public class Page_Team {

	ExtentTest test;
	WebDriver driver = null;
	GenericMethods gm;

	private static final Logger log = LogManager.getLogger(Page_Team.class.getName());

	@FindBy(id = "yui_3_18_1_2_1638642550386_4179")
	WebElement addPlayerBtn;

	@FindBy(css = "//a[@class='_yb_145wq']") // "//div[@role=\"toolbar\"]//div[@class='_yb_188ra']//a[text()='Sign
												// in']")
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

	public void Login(String username, String password) {

		gm.clickWhenReady(By.className("_yb_145wq"), 5);
		log.debug("In login method...");
		usernameField.sendKeys(username);
		nextLoginBtn.click();
		passwordField.sendKeys(password);
		nextLoginBtn.click();
	}

	public void clickCreateTradeBtn() {
		createTradeBtn.click();
		test.log(LogStatus.INFO, "Clicked on the 'Add Player' button");
	}

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

	public Map<String, Map> getMapPlayerSet(String filterTeamStatsBy) throws InterruptedException {

		Map<String, String> playerMap = new LinkedHashMap<String, String>();
		Map<String, Map> teamMap = new LinkedHashMap<String, Map>();

		// Click on the filter parameter to filter players
		String tempXPath = "//div[@id='statsubnav']//a[contains(text(), '" + filterTeamStatsBy + "')]";
		driver.findElement(By.xpath(tempXPath)).click();
		Thread.sleep(1000);

		// First grab all the player names on the team and put it in a list
		List<String> listplayer = this.getPlayersName();
		// Then iterate over the list of names putting each in a map
		for (int i = 0; i < listplayer.size(); i++) {
			playerMap = this.getMapPlayerInfo(listplayer.get(i));
			teamMap.put(listplayer.get(i), playerMap);
		}

		// System.out.println(teamMap);
		this.logTeam(teamMap, filterTeamStatsBy);
		return teamMap;
	}

	private Map<String, String> getMapPlayerInfo(String player) {
		String baseXPath;

		baseXPath = "//a[text()=" + "\"" + player + "\""
				+ "]//parent::div//parent::div//parent::div//parent::td//parent::tr//td//following-sibling::td//following-sibling::td//following-sibling::td//following-sibling::td//following-sibling::td";

		Map<String, String> playerMap = new LinkedHashMap<String, String>();

		for (int i1 = 0; i1 < 11; i1++) {
			baseXPath = baseXPath + "//following-sibling::td";

			switch (i1) {
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
		return playerMap;
	}
	
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
