package com.dglea.staging.senangpks;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.firefox.FirefoxDriverLogLevel;
import java.time.Duration;

public class webDriverManager {
    private static WebDriver driver;
    private static WebDriverWait wait,shortWait,tempWait;


    private webDriverManager() {
        // private constructor to prevent instantiation
    }

    public static WebDriver getDriver() {
        String fileSeparator = System.getProperty("file.separator");
        String currentDir = System.getProperty("user.dir");
        if (driver == null) {
            System.setProperty("webdriver.gecko.driver", currentDir + fileSeparator + "geckodriver"+ fileSeparator + "win64" + fileSeparator + "0.34.0" + fileSeparator + "geckodriver.exe");

            //this one automatic download version, idont know why but cause some issues
            WebDriverManager.firefoxdriver().setup();
            FirefoxOptions options = new FirefoxOptions();
            options.addArguments("--headless");
            options.setLogLevel(FirefoxDriverLogLevel.TRACE);
            driver = new FirefoxDriver(options);
            driver.manage().window().maximize();
            driver.get("https://dglea.staging.senangpks.com.my/");
            ((JavascriptExecutor) driver).executeScript("document.body.style.zoom='60%'");
            //driver.get("https://www.google.com/");
            wait = new WebDriverWait(driver, Duration.ofSeconds(50));
        }
        return driver;
    }

    public static WebDriverWait getWait() {
        if (wait == null) {
            wait = new WebDriverWait(driver, Duration.ofSeconds(50));
        }
        return wait;
    }
    public static WebDriverWait getShortWait() {
        if (shortWait == null) {
            shortWait = new WebDriverWait(driver, Duration.ofSeconds(10));
        }
        return shortWait;
    }
    public static WebDriverWait getTempWait() {
        if (tempWait == null) {
            tempWait = new WebDriverWait(driver, Duration.ofSeconds(15));
        }
        return tempWait;
    }

    public static void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
            wait = null;
        }
    }
}
