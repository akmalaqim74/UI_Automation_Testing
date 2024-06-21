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
    @DisplayName("FID002 Verify ID type field un-editable")
    @Description("It shouldnt Allow to edit")
    @Order(1)
    public void IdType(){
        IDTypeField();
    }
    @Test
    @DisplayName("FID001 Verify ID field un-editable")
    @Description("It shouldnt Allow to edit")
    @Order(2)
    public void ID(){
        IDField();
    }



    @Test
    @Order(3)
    @DisplayName("FID010 Verify Marital Status need to be selected ")
    @Description("Verify Marital Status need to be selected")
    public void maritalStatusTest(){
        maritalStatusField();
    }

    @Test
    @Order(4)
    @DisplayName("FID004 Verify allow Alphabet with selected special characters such as \"\"@/().-,’\"")
    @Description("It should  Alphabet with selected special characters such as \"\"@/().-,’\"")
    void nameFieldTest(){
        nameField();
    }
    @Test
    @Order(4)
    @DisplayName("FID003 Verify full name is displayed")
    @Description("It should display full name")
    void nameFieldDisplayTest(){
        nameFieldDisplayFullName();
    }
    @Test
    @Order(5)
    @DisplayName("FID016 Verify It should display a validation message for blank address")
    @Description("Verify It should display a validation message for blank address")
    public void addressBlank(){
        blankAddress();
    }
    @Test
    @Order(5)
    @DisplayName("FID013 Verify It should display a validation message for blank email")
    @Description("Verify It should display a validation message for blank email")
    public void emailBlank(){
        blankEmail();
    }
    @Test
    @Order(5)
    @DisplayName("FID019 Verify Postcode field un-editable")
    @Description("Verify Postcode field un-editable")
    public  void postCodeBlank(){
        //postCode cant be change or edit
        searchField = driver.findElement(By.xpath("//input[@formcontrolname='postcode']\n"));
        assertFalse(searchField.isEnabled());
    }

    @Test
    @Order(7)
    @DisplayName("FID020 Verify city field un-editable")
    @Description("It should display full name")
    void cityFieldUneditable(){
        cityField();
    }
    @Test
    @Order(7)
    @DisplayName("FID021 Verify state field un-editable")
    @Description("It should display full name")
    void stateFieldUneditable(){
        stateField();
    }
    @Test
    @Order(7)
    @DisplayName("FID023 Verify phone number field blank")
    @Description("- It should display validation message for blank mobile number")
    void blankPhoneNumber(){
        blankPhoneNo();
    }



    @RegisterExtension
    screenShotHelper screenshot = new screenShotHelper();
    @RegisterExtension
    googleSheetHelper googleSheet = new googleSheetHelper();
}
