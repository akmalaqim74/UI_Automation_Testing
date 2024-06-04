package com.dglea.staging.senangpks;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class landingPageTest extends baseTest {


    @BeforeAll
    public static void setup(){
        System.out.println("Checking if it's form page...");
        if (!isFormPage()) {
            System.out.println("Navigating to form page...");
            staffId();// Ensure we navigate to the login page if not already there
        }
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".align-center > button:nth-child(1)")));
        driver.findElement(By.cssSelector(".align-center > button:nth-child(1)")).click();
    }
    @Test
    public void canGoToForm(){
        assertTrue(driver.findElement(By.cssSelector(".align-center > button:nth-child(1)")).isDisplayed());
    }
    @Test
    public void NricRequired(){

        assertTrue(driver.findElement(By.cssSelector("#mat-error-0 > strong:nth-child(1)")).isDisplayed());
    }
    @Test
    public void vehNoRequired(){
        assertTrue(driver.findElement(By.cssSelector("#mat-error-1 > strong:nth-child(1)")).isDisplayed());
    }




}
