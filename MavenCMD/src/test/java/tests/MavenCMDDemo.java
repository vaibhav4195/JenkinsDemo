package tests;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

public class MavenCMDDemo {
	public String getScreenshotPath() throws IOException {
		Date d = new Date();	
		SimpleDateFormat sdf = new SimpleDateFormat("ddMMYYYY_HHmmsss");
		String timestamp = sdf.format(d);
		
		TakesScreenshot tc = (TakesScreenshot)driver;
		File src = tc.getScreenshotAs(OutputType.FILE);
		String path = "C:\\Cdac_Preparation\\Java\\JavaWorkPlace\\MavenCMD\\Screenshots"+timestamp +".png";
		FileUtils.copyFile(src,new File(path));
		return path;
		
	}
	
	
	public static WebDriver driver;
	ExtentHtmlReporter htmlReporter;
	ExtentReports extent;
	ExtentTest logger;
	
	@BeforeSuite(alwaysRun=true)
	public WebDriver beforeSuite() {
		
		htmlReporter = new ExtentHtmlReporter("extentScreenshot.html");
		extent = new ExtentReports();
		extent.attachReporter(htmlReporter);
		logger = extent.createTest("JenkinsTest");

		System.setProperty("webdriver.chrome.driver","C:\\Cdac_Preparation\\Java\\JavaWorkPlace\\MavenCMD\\WebDriver\\chromedriver.exe");
		driver=new ChromeDriver();
		logger.log(Status.INFO, "Starting Test Cases");
		driver.get("https://mail.aqmtechnologies.com/action/login/aqmtechnologies.com");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
		driver.manage().window().maximize();
		logger.pass("Navigated to Aqm website");
		return driver;
	}
	
	@Test
	public void titleTest() {
		System.out.println("In title test case");
		String title = driver.getTitle();
		Assert.assertTrue(title.contains("Welcome"));
		logger.pass("Title Test Completed");
	}
	
	@Test
	public void userSpaceTest() {
		System.out.println("In userSpaceTest case");
		boolean userName = driver.findElement(By.name("custom_input")).isDisplayed();
		Assert.assertEquals(userName, true);
		logger.pass("User Space Test Completed");
	}

	
	@AfterSuite(alwaysRun=true)
	public void afterSuite(ITestResult result) throws Exception {
		
//		if(result.getStatus() == ITestResult.FAILURE) {
//			logger.fail(result.getThrowable().getMessage(), MediaEntityBuilder.createScreenCaptureFromPath(getScreenshotPath()).build());
//		}
//		if(result.getStatus() == ITestResult.SUCCESS) {
//			logger.pass("Test Case Pass Successfully");
//		}
		logger.info("All Test Completed");
		extent.flush();
		driver.close();
		driver.quit();
		System.out.println("Browser Closing Successfully");

	}
	
	
}
