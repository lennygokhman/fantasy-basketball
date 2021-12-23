package page.classes;

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


public class TeamPage {
	
	ExtentTest test;
	WebDriver driver = null;
	GenericMethods gm;
	
	private static final Logger log = LogManager.getLogger(TeamPage.class.getName());
	
	@FindBy(id="yui_3_18_1_2_1638642550386_4179")
	WebElement addPlayerBtn;
	
	@FindBy (xpath="//div[@role=\"toolbar\"]//div[@class='_yb_188ra']//a[text()='Sign in']")
	WebElement loginBtn;
	
	@FindBy (id="login-username")
	WebElement usernameField;
	
	@FindBy (id="login-passwd")
	WebElement passwordField;
	
	@FindBy (id="login-signin")
	WebElement nextLoginBtn;
	
	@FindBy(xpath="//a[text()=\"Create Trade\"]")
	WebElement createTradeBtn;
	
	@FindBy (id="statTable0")
	WebElement playerGrid;

	// Constructor
	public TeamPage(WebDriver driver, ExtentTest test) {
		this.driver = driver;
		this.test = test;
		this.gm = new GenericMethods(this.driver);
		PageFactory.initElements(driver, this);
	}
	
	
	public void Login(String username, String password) {
		
		gm.clickWhenReady(By.xpath("//div[@role=\"toolbar\"]//div[@class='_yb_188ra']//a[text()='Sign in']"), 5);

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

		List<WebElement> trElements = playerGrid.findElements(By.xpath("//div[@class='ysf-player-name Nowrap Grid-u Relative Lh-xs Ta-start']//a"));
		
		for (WebElement element : trElements) {
			playerlist.add(element.getText());	
		}
		
		return playerlist;
	}
	
	public Map<Integer, String> getPlayerInfo(String player) {
		
		Map<Integer, String> playerMap = new LinkedHashMap<Integer, String>();
		
		gm.waitForElement(By.id("statTable0"), 5);
			
		String baseXPath = "//a[text()='" + player  + "']//parent::div//parent::div//parent::div//parent::td//parent::tr//td//following-sibling::td//following-sibling::td//following-sibling::td";		
		
		// Insert player name at 0
		playerMap.put(0, player);
		
		for (int i = 1; i <= 16; i++) {
			baseXPath = baseXPath + "//following-sibling::td";
			playerMap.put(i, driver.findElement(By.xpath(baseXPath)).getText());	
		}

		return playerMap;
	}
	
	public ArrayList<String> getListPlayerInfo(String player) {	
		String baseXPath;

		baseXPath = "//a[text()=" + "\"" + player + "\"" + "]//parent::div//parent::div//parent::div//parent::td//parent::tr//td//following-sibling::td//following-sibling::td//following-sibling::td";
			
		ArrayList<String> playerList = new ArrayList<String>();
			
		playerList.add(player);
		for (int i = 1; i <= 16; i++) {
			baseXPath = baseXPath + "//following-sibling::td";
			playerList.add(driver.findElement(By.xpath(baseXPath)).getText());
		}
		test.log(LogStatus.INFO, "In getListPlayerInfo");
		return playerList;
	}
	
		
	public void selectPlayer(String player) {
		
		WebElement playerGrid = driver.findElement(By.id("statTable0"));
		List<WebElement> trElements = playerGrid.findElements(By.tagName("a"));
		
		for (WebElement element : trElements) {
			if (element.getText().contains(player))
			{
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
