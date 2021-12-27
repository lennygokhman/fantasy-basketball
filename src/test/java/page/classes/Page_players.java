package page.classes;

import java.sql.Array;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.swing.text.AttributeSet;
import javax.swing.text.html.Option;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import utilities.GenericMethods;

public class Page_Players {

	ExtentTest test;
	WebDriver driver = null;
	GenericMethods gm;

	private static final Logger log = LogManager.getLogger(Page_Players.class.getName());

	@FindBy(id = "statselect")
	WebElement statsDropdown;

	@FindBy(id = "posselect")
	WebElement positionDropdown;

	@FindBy(id = "statusselect")
	WebElement statusDropdown;

	@FindBy(xpath = "//input[@data-slk='players_filter_click']")
	WebElement filterBtn;

	@FindBy(id = "players-table")
	WebElement playersTable;

	// Constructor
	public Page_Players(WebDriver driver, ExtentTest test) {
		this.driver = driver;
		this.test = test;
		this.gm = new GenericMethods(this.driver);
		PageFactory.initElements(driver, this);
	}

	public void clickFilterBtn() {
		test.log(LogStatus.INFO, "Clicking on 'Filter' button on the Players page");
		filterBtn.click();
	}

	public void setStatsDropdown(String value) {

		Select sel = new Select(statsDropdown);
		try {
			sel.selectByVisibleText(value);
		} catch (Exception e) {
			System.out.println("Invalid dropdown selection entered: " + value);
		}

		this.clickFilterBtn();
	}

	public void sortPlayers(String sortParameter) {

		WebElement sortLink = driver
				.findElement(By.xpath("//a[@data-slk='players_sort_click' and text()='" + sortParameter + "']"));
		sortLink.click();
	}

	public Map<String, Map> getMapPlayerSet(Integer numOfPlayers) {

		Map<String, String> playerMap = new LinkedHashMap<String, String>();
		Map<String, Map> playerSetMap = new LinkedHashMap<String, Map>();

		for (int i = 1; i <= numOfPlayers; i++) {
			String baseXPath = "//div[@class='players']//table//tbody//tr[" + i + "]";
			String playerNameXPath = baseXPath
					+ "//following-sibling::td[starts-with(@class,'player')]//div//div//div//div[starts-with(@class, 'ysf-player-name')]//a";
			// Grab the player name and use it as a key value
			String playerName = driver.findElement(By.xpath(playerNameXPath)).getText();
			// Get a map of player stats
			playerMap = getMapPlayerStats(playerName, baseXPath);
			// Now put playMap of player stats into playerSetMap
			playerSetMap.put(playerName, playerMap);
		}

		return playerSetMap;
	}

	private Map<String, String> getMapPlayerStats(String player, String baseXPath) {

		Map<String, String> playerMap = new LinkedHashMap<String, String>();
		List<WebElement> divElements = new ArrayList<WebElement>();

		WebElement playerElement = driver.findElement(By.xpath(baseXPath));
		divElements = playerElement.findElements(By.tagName("div"));

		for (int i1 = 15; i1 < (divElements.size() - 1); i1++) {

			switch (i1) {

			case 15:
				playerMap.put("FG M/A", divElements.get(i1).getText());
				break;

			case 16:
				playerMap.put("FG%", divElements.get(i1).getText());
				break;

			case 17:
				playerMap.put("FT M/A", divElements.get(i1).getText());
				break;

			case 18:
				playerMap.put("FT%", divElements.get(i1).getText());
				break;

			case 19:
				playerMap.put("3PM", divElements.get(i1).getText());
				break;

			case 20:
				playerMap.put("PTS", divElements.get(i1).getText());
				break;

			case 21:
				playerMap.put("REB", divElements.get(i1).getText());
				break;

			case 22:
				playerMap.put("AST", divElements.get(i1).getText());
				break;

			case 23:
				playerMap.put("ST", divElements.get(i1).getText());
				break;

			case 24:
				playerMap.put("BLK", divElements.get(i1).getText());
				break;

			case 25:
				playerMap.put("TOs", divElements.get(i1).getText());
				break;
			}

		}
		return playerMap;
	}

	public void logLeaders(Map<String, Map> playerInputMap, String inputSortCategory) {
		Map<String, Map> value = new LinkedHashMap<String, Map>();
		test.log(LogStatus.INFO, "The " + inputSortCategory + " leaders are: ");
		System.out.println("The free agent " + inputSortCategory + " leaders are: ");
		
		for (String playerName : playerInputMap.keySet()) {
			String tempy = null;
			value = playerInputMap.get(playerName);
		
			for (String stat : value.keySet()) {
				if (stat == inputSortCategory)
					tempy = playerName + " had " + value.get(stat) + " " + stat + "\n";
				
				tempy = tempy + stat + ": " + value.get(stat) + " ";
			}
			
			System.out.println(tempy);
			test.log(LogStatus.INFO, tempy);
		}

	}
}
