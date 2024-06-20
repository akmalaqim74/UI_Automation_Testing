package com.dglea.staging.senangpks;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class landingPageLogic extends baseTest{

    @Step("Fill in all Details")
    static void fillInDetails(){
        Allure.step("Step 1: Fill In Field", () -> {
            //NRIC Field
            searchField = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#mat-input-1")));
            searchField = driver.findElement(By.cssSelector("#mat-input-1"));
            searchField.click();
            searchField.clear();
            searchField.sendKeys("810430-08-5504");//Dummy NRIC
            //Full Name Field
            searchField = driver.findElement(By.cssSelector("#mat-input-2"));
            searchField.click();
            searchField.clear();
            searchField.sendKeys(name);
            //vehicle number field
            searchField = driver.findElement(By.cssSelector("#mat-input-3"));
            searchField.click();
            searchField.clear();
            searchField.sendKeys("WEV10721");//Dummy Vehicle no
            //postcode field
            searchField = driver.findElement(By.cssSelector("#mat-input-4"));
            searchField.click();
            searchField.clear();
            searchField.sendKeys(postcode);
            //Email Field
            searchField = driver.findElement(By.cssSelector("#mat-input-5"));
            searchField.clear();
            searchField.click();
            searchField.sendKeys(email);
        });
    }
    @Step("Clear NRIC Field")
    void blankNRICField(){
        Allure.step("Step 1: Clear out Field", () -> {
            searchField = driver.findElement(By.cssSelector("#mat-input-1"));
            clearOutField(searchField);
        });
        Allure.step("Step 2: Click \"Get Free Quotation\"", () -> {
            // Step 1 logic here
            driver.findElement(By.cssSelector(".align-center > button:nth-child(1)")).click();
            searchField = driver.findElement(By.className("form-container"));
            assertTrue(driver.findElement(By.tagName("mat-error")).isDisplayed());
            // Additional operations
        });
        //Fill in back
        searchField = driver.findElement(By.cssSelector("#mat-input-1"));
        searchField.sendKeys("810430-08-5504");//Dummy NRIC
    }
    @Step("Clear Name Field")
    void blankNameField(){
        Allure.step("Step 1: Clear out Field", () -> {
            searchField = driver.findElement(By.cssSelector("#mat-input-2"));
            clearOutField(searchField);
        });
        Allure.step("Step 2: Click \"Get Free Quotation\"", () -> {
            // Step 1 logic here
            driver.findElement(By.cssSelector(".align-center > button:nth-child(1)")).click();
            searchField = driver.findElement(By.className("form-container"));
            assertTrue(driver.findElement(By.tagName("mat-error")).isDisplayed());
            // Additional operations
        });
        searchField = driver.findElement(By.cssSelector("#mat-input-2"));
        searchField.sendKeys(name);
    }
    @Step("Clear vehicle no Field")
    void blankVehRegField(){
        Allure.step("Step 1: Clear out Field", () -> {
            searchField = driver.findElement(By.cssSelector("#mat-input-3"));
            clearOutField(searchField);
        });
        Allure.step("Step 2: Click \"Get Free Quotation\"", () -> {
            // Step 1 logic here
            driver.findElement(By.cssSelector(".align-center > button:nth-child(1)")).click();
            searchField = driver.findElement(By.className("form-container"));
            assertTrue(driver.findElement(By.tagName("mat-error")).isDisplayed());
            // Additional operations
        });
        searchField = driver.findElement(By.cssSelector("#mat-input-3"));
        searchField.sendKeys("WEV10721");
    }
    @Step("Clear post code Field")
    void blankPostCodeField(){
        Allure.step("Step 1: Clear out Field", () -> {
            searchField = driver.findElement(By.cssSelector("#mat-input-4"));
            clearOutField(searchField);
        });
        Allure.step("Step 2: Click \"Get Free Quotation\"", () -> {
            // Step 1 logic here
            driver.findElement(By.cssSelector(".align-center > button:nth-child(1)")).click();
            searchField = driver.findElement(By.className("form-container"));
            assertTrue(driver.findElement(By.tagName("mat-error")).isDisplayed());
            // Additional operations
        });
        searchField = driver.findElement(By.cssSelector("#mat-input-4"));
        searchField.sendKeys(postcode);
    }
    @Step("Clear email Field")
    void blankEmailField(){
        Allure.step("Step 1: Clear out Field", () -> {
            searchField = driver.findElement(By.cssSelector("#mat-input-5"));
            clearOutField(searchField);
        });
        Allure.step("Step 2: Click \"Get Free Quotation\"", () -> {
            // Step 1 logic here
            driver.findElement(By.cssSelector(".align-center > button:nth-child(1)")).click();
            searchField = driver.findElement(By.className("form-container"));
            assertTrue(driver.findElement(By.tagName("mat-error")).isDisplayed());
            // Additional operations
        });
        searchField = driver.findElement(By.cssSelector("#mat-input-5"));
        searchField.sendKeys(email);
    }

}
