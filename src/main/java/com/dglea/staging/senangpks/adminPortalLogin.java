package com.dglea.staging.senangpks;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;

public class adminPortalLogin {
    WebElement searchField;
    WebDriverWait wait;
    public void login(String url, WebDriver driver){
        wait = new WebDriverWait(driver, Duration.ofSeconds(180));
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
        driver.get(url);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("splash-screen")));
        driver.switchTo().window(tabs.get(0));
    }
}
