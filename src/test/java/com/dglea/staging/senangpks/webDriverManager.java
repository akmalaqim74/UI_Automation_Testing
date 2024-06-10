package com.dglea.staging.senangpks;

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
        if (driver == null) {
            WebDriverManager.firefoxdriver().setup();
            FirefoxOptions options = new FirefoxOptions();
            options.setLogLevel(FirefoxDriverLogLevel.TRACE);
            driver = new FirefoxDriver(options);
            driver.manage().window().maximize();
            driver.get("https://dglea.staging.senangpks.com.my/");
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
