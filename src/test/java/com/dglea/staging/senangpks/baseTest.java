package com.dglea.staging.senangpks;

import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class baseTest {
    protected static WebDriver driver;
    static WebElement searchField;
    static WebDriverWait wait,shortWait;
    protected static String coverType;
    public static double totalAddOn = 0;
    static ArrayList<String> availableProvider = new ArrayList<>();
    static ArrayList<String> NotAvailableProvider = new ArrayList<>();
    static ArrayList<String> addOn = new ArrayList<>();

    @BeforeAll
    public static void setUpClass() {
        driver = webDriverManager.getDriver();
        wait = webDriverManager.getWait();
        shortWait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }


    protected boolean isQuoteGenerated() {
        // Check if the current URL contains a string that indicates the user is logged in
        return driver.getCurrentUrl().contains("https://vehicle.staging.senangpks.com.my/quotation"); // Adjust this based on your application's URL structure
    }

    public static void staffId() {

        try {
            System.out.println("Executing staffId method");

            // Correct usage of "css selector" for locating elements by class name
            //wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".splash-screen")));
            //System.out.println("Splash screen disappeared");

            // Ensure the #staffNo element is visible and clickable
            searchField = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#staffNo")));
            searchField = driver.findElement(By.id("staffNo"));
            searchField.click();
            searchField.sendKeys("11111");
            searchField = driver.findElement(By.cssSelector(".step1-submit > button:nth-child(1)"));
            searchField.click();

            System.out.println("staffId method executed successfully");
        } catch (Exception e) {
            System.err.println("An error occurred in staffId method: " + e.getMessage());
            e.printStackTrace();
        }

    }

    //public static void generateQuote(List<testData> testDataList){
    public static void generateQuote(){
        System.out.println("Executing Vehicle Number field");
        String vehNo = "WA3882D"; //scanner.nextLine();
        System.out.println("Executing Nric field");
        String NRIC = "930706145318"; //scanner.nextLine();

        searchField = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#mat-input-1")));
        searchField = driver.findElement(By.cssSelector("#mat-input-1"));
        searchField.click();
        searchField.sendKeys(NRIC);
        searchField = driver.findElement(By.cssSelector("#mat-input-3"));
        searchField.click();
        searchField.sendKeys(vehNo);
        searchField = driver.findElement(By.cssSelector("#mat-input-5"));
        searchField.clear();
        searchField.click();
        searchField.sendKeys("akmalmustaqimsenang@gmail.com");
        searchField = driver.findElement(By.cssSelector(".p-t-10 > div:nth-child(2)"));
        searchField.click();
        System.out.println("Generating Quotation wait yaa...............");
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("splash-screen")));
        System.out.println("Thanks For Waiting, we are on vehicle portal");

        /*for (testData testData : testDataList) {
            System.out.println("Executing Vehicle Number field");
            String vehNo = testData.getVehNo();
            System.out.println("Executing NRIC field");
            String NRIC = testData.getNRIC();

            WebElement searchField = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#mat-input-1")));
            searchField = driver.findElement(By.cssSelector("#mat-input-1"));
            searchField.click();
            searchField.sendKeys(NRIC);
            searchField = driver.findElement(By.cssSelector("#mat-input-3"));
            searchField.click();
            searchField.sendKeys(vehNo);
            searchField = driver.findElement(By.cssSelector("#mat-input-5"));
            searchField.clear();
            searchField.click();
            searchField.sendKeys(email);
            searchField = driver.findElement(By.cssSelector(".p-t-10 > div:nth-child(2)"));
            searchField.click();
            System.out.println("Generating Quotation wait yaa...............");
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("splash-screen")));
            System.out.println("Thanks For Waiting, we are on vehicle portal");
        }*/
    }
    //----------------------------------------------------------------------------
    public static void adminPortalLogin(){
        wait.until(ExpectedConditions.urlContains("https://vehicle.staging.senangpks.com.my/quotation/"));
        String quotationId = driver.getCurrentUrl();
        String[] parts  = quotationId.split("/");
        quotationId = parts[parts.length - 1];
        System.out.println(quotationId);

        //-----------------------------------------------------------------------
        //Login In
        ((JavascriptExecutor) driver).executeScript("window.open();");

        ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));
        driver.get("https://portal.staging.senangpks.com.my/auth/login");
        System.out.println("Current URL: " + driver.getCurrentUrl());
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("splash-screen")));

        searchField = driver.findElement(By.cssSelector("#mat-input-0"));
        searchField.click();
        searchField.sendKeys("farah.diyana@senang.io");
        searchField = driver.findElement(By.cssSelector("#mat-input-1"));
        searchField.click();
        searchField.sendKeys("EdwY;0Haa!NX");
        searchField = driver.findElement(By.cssSelector(".mat-button-wrapper"));
        searchField.click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("splash-screen")));
        wait.until(ExpectedConditions.urlContains("https://portal.staging.senangpks.com.my/dashboard"));
        driver.get("https://portal.staging.senangpks.com.my/vehicle-policy/view/" + quotationId);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("splash-screen")));
    }

    //----------------------------------------------------------------------------
    public static boolean isElementPresent(WebDriverWait wait, By locator) {
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }
    //Page Confirmation
    protected static boolean isFormPage() {
        // Check if already on login page or logged in
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        Boolean staffInserted = isElementPresent(wait,By.cssSelector(".align-center > button:nth-child(1) > span:nth-child(1)"));
        System.out.println(staffInserted);
        wait = new WebDriverWait(driver, Duration.ofSeconds(50));
        return staffInserted;
    }
    protected static boolean isVehiclePortal() {
        // Check if already on login page or logged in
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        Boolean staffInserted = isElementPresent(wait,By.cssSelector(".mat-slider-track-background"));
        System.out.println(staffInserted);
        wait = new WebDriverWait(driver, Duration.ofSeconds(50));
        return staffInserted;
    }


}
