package com.dglea.staging.senangpks;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.WebDriver;

import java.time.Duration;

public class addOn {
    WebElement searchField,element;
    WebDriverWait wait,tempWait;
    public void addOn(WebDriver driver){
        wait = new WebDriverWait(driver, Duration.ofSeconds(180));
        tempWait = new WebDriverWait(driver, Duration.ofSeconds(10));
        searchField = driver.findElement(By.cssSelector("#mat-input-2"));
        searchField.click();
        searchField.sendKeys("770");
        searchField = driver.findElement(By.cssSelector("div.m-t-10:nth-child(4) > div:nth-child(1) > button:nth-child(1)"));
        searchField.click();
        searchField = driver.findElement(By.cssSelector("div.row:nth-child(3) > div:nth-child(2) > div:nth-child(1) > div:nth-child(1) > button:nth-child(1) > span:nth-child(1)"));
        searchField.click();
        searchField = driver.findElement(By.cssSelector("div.row:nth-child(6) > div:nth-child(2) > div:nth-child(1) > div:nth-child(1) > button:nth-child(1) > span:nth-child(1)"));
        searchField.click();
        searchField = driver.findElement(By.cssSelector("button.btn-primary:nth-child(2) > span:nth-child(1)"));
        searchField.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button.btn-primary:nth-child(2)")));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("circle.ng-star-inserted")));
        searchField = driver.findElement(By.cssSelector("button.btn-primary:nth-child(2)"));
        searchField.click();
        boolean isPopupPresent = isElementPresent(tempWait, By.tagName("mat-dialog-container"));
        if(isPopupPresent){
            searchField = driver.findElement(By.cssSelector("div.justify-content-center:nth-child(1) > button:nth-child(2)"));
            searchField.click();
        }
        //update personal info
        searchField = driver.findElement(By.id("mat-select-7"));
        searchField.click();
        searchField = driver.findElement(By.id("mat-option-44"));
        searchField.click();
        searchField = driver.findElement(By.cssSelector("button.btn-primary:nth-child(2)"));
        searchField.click();


    }
    // Method to check for the presence of an element
    public static boolean isElementPresent(WebDriverWait wait, By locator) {
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }
}
