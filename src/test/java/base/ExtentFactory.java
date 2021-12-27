package base;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.relevantcodes.extentreports.ExtentReports;

public class ExtentFactory {
	static String path = "\\Users\\leng0\\Documents\\seleniumLogs\\fantasyBasketball.html";
	
	public static ExtentReports getInstance() {
		ExtentReports extent;
		
		
		
		// false appends...for the same run.
		extent = new ExtentReports(path, false);
		extent
	     .addSystemInfo("Selenium Version", "2.52")
	     .addSystemInfo("Platform", "Lenny");

		return extent;
	}
	
	public static void clearLog() {
		Path filepath = Paths.get(path); 
		try {
			  
	            Files.deleteIfExists(filepath);
	        }
	        catch (IOException e) {
	  
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	}
}