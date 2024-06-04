package com.dglea.staging.senangpks;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static org.testng.AssertJUnit.assertFalse;
import static org.testng.AssertJUnit.assertTrue;
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class personalInfoTest extends baseTest {
    public static boolean presence;
    @BeforeAll
    public static void setup(){

    }
    @AfterAll
    public static void ended(){
        driver.findElement(By.xpath("//button[normalize-space()='Next']\n")).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("circle.ng-star-inserted")));
        presence = isElementPresent(shortWait,By.xpath("//button[normalize-space()='Proceed for Payment']\n"));
        /*while(!presence){
            driver.findElement(By.cssSelector("button.btn-primary:nth-child(2)"));
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("circle.ng-star-inserted")));
            presence = isElementPresent(shortWait,By.xpath("//button[normalize-space()='Proceed for Payment']\n"));
        }*/
    }
    @Test
    @Order(1)
    public void Id(){
    //It shouldnt allow to edit
        searchField = driver.findElement(By.xpath("//input[@formcontrolname='idNumber']\n"));
        assertFalse(searchField.isEnabled());
    }

    @Test
    @Order(2)
    public void maritalStatus(){
        driver.findElement(By.xpath("//mat-select[@formcontrolname='maritalStatus']\n")).click();
        driver.findElement(By.xpath("//mat-option[normalize-space()='--Select--']\n")).click();
        presence = isElementPresent(shortWait,By.cssSelector("#mat-error-3"));
        assertTrue(presence);
        driver.findElement(By.xpath("//mat-select[@formcontrolname='maritalStatus']\n")).click();
        driver.findElement(By.xpath("//mat-option[normalize-space()='Single']\n")).click();
    }
    @Test
    @Order(3)
    public void address(){
        searchField = driver.findElement(By.xpath("//input[@formcontrolname='address1']\n"));
        searchField.click();
        searchField.clear();
        searchField.sendKeys(" ");
        WebElement TempsearchField = driver.findElement(By.xpath("//input[@formcontrolname='address2']\n"));
        TempsearchField.click();
        TempsearchField.clear();
        presence = isElementPresent(shortWait,By.cssSelector("#mat-error-4 > strong:nth-child(1)"));

        searchField = driver.findElement(By.xpath("//input[@formcontrolname='address1']\n"));
        searchField.click();
        searchField.sendKeys("No Where To be");
        assertTrue(presence);
    }
    @Test
    @Order(4)
    public  void postCode(){
        //postCode cant be change or edit
        searchField = driver.findElement(By.cssSelector("#mat-input-21"));
        assertFalse(searchField.isEnabled());
    }

}
