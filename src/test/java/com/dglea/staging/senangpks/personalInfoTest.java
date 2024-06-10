package com.dglea.staging.senangpks;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class personalInfoTest extends personalInfoLogic {

    @BeforeAll
    public static void setup(){

    }
    @AfterAll
    public static void ended(){
        driver.findElement(By.xpath("//button[normalize-space()='Next']\n")).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("circle.ng-star-inserted")));
        presence = isElementPresent(shortWait,By.xpath("//button[normalize-space()='Proceed for Payment']\n"));
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
    public void maritalStatusTest(){
        assertTrue(maritalStatus());
    }
    @Test
    @Order(3)
    public void addressTest(){

        assertTrue(address());
    }
    @Test
    @Order(4)
    public  void postCode(){
        //postCode cant be change or edit
        searchField = driver.findElement(By.cssSelector("#mat-input-21"));
        assertFalse(searchField.isEnabled());
    }

}
