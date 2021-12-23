package base;

import com.relevantcodes.extentreports.ExtentReports;

public class ExtentFactory {
	public static ExtentReports getInstance() {
		ExtentReports extent;
		String Path = "\\Users\\leng0\\Documents\\seleniumLogs\\fantasyBasketball.html";
		// false appends...for the same run.
		extent = new ExtentReports(Path, false);
		extent
	     .addSystemInfo("Selenium Version", "2.52")
	     .addSystemInfo("Platform", "Lenny");

		return extent;
	}
}