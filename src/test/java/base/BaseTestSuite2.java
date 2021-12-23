package base;

import utilities.ExcelUtility;
import java.time.Duration;
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

import utilities.Constants;
import utilities.ExcelUtility;


public class BaseTestSuite2 {
	public WebDriver driver;
	public String baseUrl;

	
	@BeforeClass
	public void beforeClass() throws Exception {
		System.out.println("BaseTestSuite_Fantasy Basketball -> before class");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		
		// Tell the code about the location of Excel file
		ExcelUtility.setExcelFile(Constants.File_Path + Constants.File_Name, "LoginTests");
	}

	
	@BeforeSuite
	public void beforeSuite() {
		System.out.println("BaseTestSuite_Fantasy Basketball -> before suite");
	}

	@AfterSuite
	public void afterSuite() {
		System.out.println("BaseTestSuite_Fantasy Basketball -> after suite");
	}

}
