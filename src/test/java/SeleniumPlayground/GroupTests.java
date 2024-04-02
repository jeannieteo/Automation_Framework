package SeleniumPlayground;

//This is a series of test for testing out the selenium on various types of web objects
//hovers, keypresss, cookies, attributes,

import org.openqa.selenium.*;
import org.openqa.selenium.support.locators.RelativeLocator;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v122.security.Security;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
public class GroupTests {
    @Test(groups = {"DragAndDrop"})
    public void testDragandDrop() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.w3schools.com/howto/tryit.asp?filename=tryhow_css_rangeslider");
        driver.switchTo().frame("iframeResult");

        WebElement slider = driver.findElement(By.xpath("//input[@id='myRange']"));
        int width = slider.getSize().width;
        System.out.println("Size is " + width);
        Actions actionS = new Actions(driver);
        actionS.dragAndDropBy(slider, 60, 0).perform();
    }

    @Test(groups = {"frame"})
    public static void frames() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://seleniumbase.io/w3schools/iframes");
        System.out.println(driver.findElements(By.tagName("iframe")).size());
        //Go to the outside frame first
        driver.switchTo().frame("iframeResult");

        //locate the frame by webelement when no id //ThIS IS THE NESTED FRAME
        WebElement wantedFrame = driver.findElement(By.xpath("//iframe[@title='Iframe Example']"));
        driver.switchTo().frame(wantedFrame);
        System.out.println(driver.findElement(By.cssSelector("h1")).getText());

    }
    @Test(groups = {"dropdown"})
    public void testDropdownContents() {
        int v;
        String[] dropdownContents = new String[3];
        dropdownContents[0] = "Please select an option";
        dropdownContents[1] = "Option 1";
        dropdownContents[2] = "Option 2";
        System.out.println("the internet.herokuapp.com Dropdown test");
        WebDriver driver = new EdgeDriver();

        driver.get("https://the-internet.herokuapp.com/dropdown");
        //click on the dropdown
        driver.findElement(By.id("dropdown")).click();
        WebElement dropdown = driver.findElement(By.xpath("//*[@id=\"dropdown\"]"));
        //assign all the options in dropdown to the list
        List<WebElement> options = dropdown.findElements(By.tagName("option"));
        System.out.println("There are " + options.size() + " options in the dropdown.");
        //verify options are expected.
        for (v = 0; v < options.size(); v++) {
            System.out.println(options.get(v).getText());
            Assert.assertEquals(options.get(v).getText(), dropdownContents[v]);
        }
        driver.quit();
    }

    @Test(groups = {"dropdown"})
    public void testDropdownSelection() {

        System.out.println("the internet.herokuapp.com Dropdown test 2");
        WebDriver driver = new EdgeDriver();

        driver.get("https://the-internet.herokuapp.com/dropdown");
        driver.findElement(By.id("dropdown")).click();
        WebElement option1 = driver.findElement(By.xpath("//*[@id='dropdown']/option[2]"));
        //*[@id="dropdown"]/option[2]
        option1.click();
        Assert.assertTrue(option1.isSelected());
        driver.quit();
    }

    @Test(groups = {"hovers"})
    public void testhovers() {
        System.out.println("the internet.herokuapp.com hovers test");
        System.setProperty("webdriver.edge.driver", System.getProperty("user.dir") + "\\testdata\\msedgedriver.exe");
        WebDriver driver = new EdgeDriver();
        driver.get("https://the-internet.herokuapp.com/hovers");
        WebElement hoverable = driver.findElement(By.cssSelector("#content > div > div:nth-child(3) > img"));
        new Actions(driver)
                .moveToElement(hoverable)
                .perform();

        Assert.assertTrue(driver.findElement(By.xpath("//*[@id=\"content\"]/div/div[1]/div/h5")).isDisplayed());
        driver.quit();
    }

    @Test(groups = {"context_menu"})
    public void testcontext_menu() {
        //no need set set since selenium manager is now by default in
        //System.setProperty("webdriver.edge.driver", System.getProperty("user.dir") + "\\testdata\\msedgedriver.exe");
        WebDriver driver = new EdgeDriver();
        driver.get("https://the-internet.herokuapp.com/context_menu");
        System.out.println("the internet.herokuapp.com context_menu test");

        WebElement hotspot = driver.findElement(By.id("hot-spot"));
        new Actions(driver)
                .contextClick(hotspot)
                .perform();
        String alerttext = driver.switchTo().alert().getText();
        System.out.println("The alert text is: " + alerttext);
        driver.switchTo().alert().accept();
        driver.quit();
    }

    @Test(groups = {"getAttribute"})
    public void testCssAttrib() {

        WebDriver driver = new EdgeDriver();
        driver.get("https://the-internet.herokuapp.com");
        System.out.println("the internet.herokuapp.com CSS attrib test");

        String checklink = driver.findElement(By.linkText("Challenging DOM")).getAttribute("href");
        Assert.assertEquals(checklink, "https://the-internet.herokuapp.com/challenging_dom");

        String checkpadding = driver.findElement(By.linkText("Challenging DOM")).getCssValue("background-origin");
        Assert.assertEquals(checkpadding, "padding-box");
    }

    @Test(groups = {"getCssValue"})
    public void cssvalue() {
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.idf.il/en");
        WebElement ele = driver.findElement(By.xpath("//a[contains(text(),'Press Releases')]"));

        String s = ele.getCssValue("color");
        System.out.println("BEFORE Color is :" + s);

        Actions action = new Actions(driver);
        action.moveToElement(ele).perform();
        s = ele.getCssValue("color");

        System.out.println("AFTER HOVER Color is :" + s);
    }

    @Test(groups = {"key_presses"})
    public void testKeypresses() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://the-internet.herokuapp.com/key_presses");
        driver.findElement(By.id("target")).sendKeys(Keys.ARROW_RIGHT);
        String testString = driver.findElement(By.id("result")).getText();
        Assert.assertEquals(testString, "You entered: RIGHT");
    }

    @Test(groups = {"cookies"})
    public void testCookiesValue() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://example.cypress.io/commands/cookies");
        driver.findElement(By.className("set-a-cookie")).click();
        Set<Cookie> mycookie = driver.manage().getCookies();
        //for(Cookie getcookies: mycookie) {
        System.out.println("Got Cookie: " + mycookie);
        //}
    }

    @Test(groups = {"mobile"})
    public void testMobileOptsinChrome() {
        Map<String, String> mobileEm = new HashMap<String, String>();
        mobileEm.put("deviceName", "iPhone XXX");
        ChromeOptions opt = new ChromeOptions();
        //opt.addArguments("--headless");
        //opt.addArguments("disable-infobars");
        //opt.addArguments("window-size=1400,1000");
        opt.setAcceptInsecureCerts(true);
        opt.addArguments("incognito");
        //disable the 'being automated infobar
        opt.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
        opt.setExperimentalOption("mobileEmulation", mobileEm);
        WebDriver driver = new ChromeDriver(opt);
        driver.get("https://expired.badssl.com");
        System.out.println(driver.getTitle());
    }

    @Test(groups = {"DevTools"})
    public void testIgnorecert() {
        WebDriver driver = new ChromeDriver();
        DevTools devTools = ((ChromeDriver) driver).getDevTools();
        devTools.createSession();
        devTools.send(Security.enable());
        devTools.send(Security.setIgnoreCertificateErrors(true));
        driver.get("https://expired.badssl.com");
    }

    @Test(groups = {"relativelocators"})
    public void testRelativeLocator() {
        WebDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        driver.get("https://www.blazemeter.com/");
        driver.switchTo().defaultContent(); //switch back from the cookies focus
        driver.findElement(By.id("mm-toggle")).click();
        WebElement linkLogin = driver.findElement(By.linkText("Login"));
        wait.until(ExpectedConditions.elementToBeClickable(linkLogin));
        linkLogin.click();

        //relative
        //WebElement signlink = driver.findElement(RelativeLocator.with(By.tagName("a")).near(By.xpath("//*[@id='block-secondarynavigation']/ul/li")));
        //signlink.click();

        driver.findElement(By.id("username")).sendKeys("blahblhablah@gmail.com");
        driver.findElement(By.id("password")).sendKeys("zxcvbn");
        WebElement signButton = driver.findElement(RelativeLocator.with(By.tagName("input")).below(By.id("password")));
        signButton.click();
    }

    @Test(groups={"webtable"})
    public void testWebTable() {
        WebDriver driver = new EdgeDriver();
        driver.get("https://www.dezlearn.com/webtable-example/");
        List <WebElement> rows = driver.findElements(By.xpath("//table/tbody/tr"));
        List <WebElement> cols = driver.findElements(By.xpath("//table/tbody/tr/th"));
        System.out.println("There are " + rows.size() + " rows." );
        System.out.println("There are " + cols.size() + " columns." );

        for (int i=1; i <= 2; i++) {
            for (int j=2; j <= rows.size(); j++) {

                WebElement data = driver.findElement(By.xpath("//table/tbody/tr[" + j + "]/td[" + i +  "]"));
                System.out.println("Contents " + data.getText());
            }
        }

    }
    @Test(groups = {"calendar"})
    public void testCalendar() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.agoda.com/");
        //String datestring = driver.findElement(By.xpath("//div[contains(@aria-label,'Select check-in date')]")).getText();
        String datestring = driver.findElement(By.cssSelector("div[data-selenium='checkInText']")).getText();
        System.out.println("Checkin date: " + datestring);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy");
        LocalDate datecheckin = LocalDate.parse(datestring, formatter);
        System.out.println("Checkin date: Converted" + datecheckin);

        //add 3 days
        String checkoutDate = datecheckin.plusDays(3).toString();
        System.out.println("Checkout date: " + checkoutDate);

        driver.findElement(By.xpath("//div[@data-selenium='checkOutText']")).click();
        String xpathDate = "//span[@data-selenium-date='" + checkoutDate + "']";
        driver.findElement(By.xpath(xpathDate)).click();
        //span[@data-selenium-date='2024-01-31']
    }
    //@Test(groups = {"screenshot"})
    //public static void screenshot(WebDriver driverS) {
        //driver.get("https://www.dezlearn.com/webtable-example/");
        //TakesScreenshot screenshot = (TakesScreenshot) driverS;
        //File source = screenshot.getScreenshotAs(OutputType.FILE);
        //File destination = new File(System.getProperty("user.dir") + "/testscreenshot.png");
        //try {
        //    FileHandler.copy(source, destination);
        //} catch (IOException e) {
        //    e.printStackTrace();
        //}
    //}
}
