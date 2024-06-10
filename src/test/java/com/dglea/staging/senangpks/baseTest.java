package com.dglea.staging.senangpks;

import io.qameta.allure.Step;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class baseTest {
    protected static WebDriver driver;
    static WebElement searchField;
    static WebDriverWait wait,shortWait,tempWait;
    protected static String coverType;
    static ArrayList<String> availableProvider = new ArrayList<>();
    static ArrayList<String> NotAvailableProvider = new ArrayList<>();
    static ArrayList<String> addOn = new ArrayList<>();
    public static  ArrayList<String> testDataList = new ArrayList<>();

    @BeforeAll
    public static void setUpClass() {
        driver = webDriverManager.getDriver();
        wait = webDriverManager.getWait();
        shortWait = webDriverManager.getShortWait();
        tempWait = webDriverManager.getTempWait();
        try {
            testDataList = sheetHelper.getDataFromExcel();
        } catch (IOException e) {
            e.printStackTrace(); // Or handle the exception in some appropriate way
        }

    }


    public static void insertStaffId() {
        System.out.println("Checking if it's form page...");
        if (!isFormPage()) {
            System.out.println("Navigating to form page...");
            try {
                System.out.println("Executing staffId method");
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
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".align-center > button:nth-child(1)")));
        driver.findElement(By.cssSelector(".align-center > button:nth-child(1)")).click();

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
        driver.switchTo().window(tabs.get(0));
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
    public static boolean isElementClickable(WebDriverWait waitM,By locator){
        try {
            waitM.until(ExpectedConditions.elementToBeClickable(locator));
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
