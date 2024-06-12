package com.dglea.staging.senangpks;

import io.qameta.allure.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("4.Personal Info")
@Story("As a user I want to verify and edit my personal info")
@Owner("Intern Akmal")
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
    @AfterEach
    void eachTestEnded(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Verify ID type field un-editable")
    @Description("It shouldnt Allow to edit")
    @Order(1)
    public void IdType(){
        IDTypeField();
    }
    @Test
    @DisplayName("Verify ID field un-editable")
    @Description("It shouldnt Allow to edit")
    @Order(2)
    public void ID(){
        IDField();
    }
    @Test
    @Order(6)
    void nameFieldTest(){
        nameField();
    }


    @Test
    @Order(3)
    public void maritalStatusTest(){
        maritalStatusField();
    }
    @Test
    @Order(4)
    public void addressTest(){

        assertTrue(address());
    }
    @Test
    @Order(5)
    public  void postCode(){
        //postCode cant be change or edit
        searchField = driver.findElement(By.xpath("//input[@formcontrolname='postcode']\n"));
        assertFalse(searchField.isEnabled());
    }
    @RegisterExtension
    screenShotHelper screenshot = new screenShotHelper();
}
