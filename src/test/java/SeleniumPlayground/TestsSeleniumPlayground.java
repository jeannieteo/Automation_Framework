package SeleniumPlayground;
//This is the practical test for TEstNg certification from lambdatest.
//To use send the tests to lambdatest for cross browser tests run parallel
//they required to run from testng.xml
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.AbstractDriverOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

public class TestsSeleniumPlayground {
	public String gridURL = "@hub.lambdatest.com/wd/hub";
	private String username = "jeannieteo78";
	private String accessKey = "YaCWIHFZXOBJCM7xphKhTCxINnG5hJkmWz7Z0QqcZUxxaH7YWF";
	
	public static RemoteWebDriver driver = null;
	
	@BeforeMethod
	@Parameters(value={"TestURL", "Browser", "Version", "Platform"})
    public void setUp(String testURL, String browser, String version, String platform) throws Exception	{
		AbstractDriverOptions<?> options = null;
		HashMap<String, Object> ltOptions = new HashMap<String, Object>();
		//ltOptions.put("username", username);
		//ltOptions.put("accessKey", accessKey);
		ltOptions.put("visual", true);
		ltOptions.put("video", true);
		ltOptions.put("network", true);
		ltOptions.put("build", "TestNgCert1");
		ltOptions.put("project", "JeannieTestNgCertTestA");
		ltOptions.put("selenium_version", "4.0.0");
		ltOptions.put("w3c", true);
		
		if 	(browser.equals("Chrome"))	{
			options = new ChromeOptions();
		}else if 	(browser.equals("Firefox"))	{
			options = new FirefoxOptions();
		}else if	(browser.equals("Edge"))	{
			options = new EdgeOptions();
		}else if	(browser.equals("IE"))	{
			options = new InternetExplorerOptions();
		}
		options.setCapability("platformName", platform);
		options.setCapability("browserVersion", version);
		options.setPageLoadStrategy(PageLoadStrategy.EAGER);
	    options.setCapability("LT:Options", ltOptions);
		
		try {
            driver = new RemoteWebDriver(new URL("https://" + username + ":" + accessKey + gridURL), options);
        } catch (MalformedURLException e) {
            System.out.println("Invalid grid URL");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
		
		driver.get(testURL);
	}

	 @Test(timeOut = 20000)
	 public void testScenario1_explicitWait() {
		 SoftAssert softAssert = new SoftAssert();
		 //Do Explicit wait on JS page load
		 WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		 wait.until(ExpectedConditions.jsReturnsValue(("if (document.readyState === \"complete\") { return \"ok\"; }"))); 	
		 //get title and test
		String actualtitle = driver.getTitle();
		softAssert.assertEquals(actualtitle, "LambdaTest", "Page Title is not 'LambdaTest'." );
		System.out.println("Actual Page Title is: " + actualtitle);
		softAssert.assertAll();
	    }
	 
	 @Test(timeOut = 20000)
	    public void testScenario2_SingleCheckBox() {
		 SoftAssert softAssert = new SoftAssert();
		 //click Checkbox Demo
		 driver.findElement(By.linkText("Checkbox Demo")).click();
		 	
		//click checkbox under Single and check TEXT
		driver.findElement(By.id("isAgeSelected")).click();
		String actualSuccessMessage = driver.findElement(By.id("txtAge")).getText();
		softAssert.assertEquals(actualSuccessMessage, "Success - Check box is checked", "testScenario2_SingleCheckBox: Checkbox was not checked.");
		 	
		//click checkbox under Single and check not displayed
		driver.findElement(By.id("isAgeSelected")).click();
		softAssert.assertFalse(driver.findElement(By.id("txtAge")).isDisplayed());
		softAssert.assertAll();
	    }
	 
	 @Test(timeOut = 20000)
	    public void testScenario3_JavascriptAlert() {
		 
		 //click Javascript Alerts
		 driver.findElement(By.linkText("Javascript Alerts")).click();
		 //click 'click me' for Javascript Alerts
		 driver.findElement(By.xpath("//div[text()='Java Script Alert Box']/following-sibling::button")).click();
		 //get the text if the alert
		 String actualAlert = driver.switchTo().alert().getText();
		 System.out.println("The alert text is: "+ actualAlert);
		 Assert.assertEquals(actualAlert, "I am an alert box!", "testScenario3_JavascriptAlert: Alert is not 'I am an alert box!'");
		 driver.switchTo().alert().accept();
	 }
	 @AfterMethod
	    public void tearDown() throws Exception {
	            driver.quit();
	    }
}
