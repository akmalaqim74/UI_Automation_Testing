package com.dglea.staging.senangpks;

import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.model.Status;
import org.openqa.selenium.*;

import java.io.ByteArrayInputStream;


public class personalInfoLogic extends baseTest{
    public static boolean presence;
    @Description("Id type is un-editable")
    void IDTypeField(){
        Allure.step("Step 1: Find ID type Field", () -> {
            searchField = driver.findElement(By.xpath("//mat-select[@formcontrolname='idType']\n"));
        });
        Allure.step("Verify Id Type Field un-editable", () -> {
            // Step 2 logic here
            boolean tempClickable = searchField.isEnabled();
            if (tempClickable) {
                Allure.step("Bug on field ID Type, Field can be edit: ", Status.FAILED);
            }else {
                Allure.step("Verify ID type cant be edit", Status.PASSED);
            }
        });

    }
    @Description("Id  is un-editable")
    void IDField(){
        Allure.step("Step 1: Find ID  Field", () -> {
            searchField = driver.findElement(By.xpath("//input[@formcontrolname='idNumber']\n"));
        });
        Allure.step("Verify Id Field un-editable", () -> {
            // Step 2 logic here
            boolean tempClickable = searchField.isEnabled();
            if (tempClickable) {
                Allure.step("Bug on  ID Type, Field can be edit: ",Status.FAILED);
            }else {
                Allure.step("Verify ID cant be edit", Status.PASSED);
            }
        });

    }
    void nameFieldDisplayFullName(){
        Allure.step("Test 1: It should display full name", () -> {
            if (name.equalsIgnoreCase(searchField.getText())) {
                Allure.step("Verify full name is displayed", Status.PASSED);
            }else {
                Allure.step("Full Name is not display", Status.FAILED);
            }
        });
    }
    @Description("It should display full name," +
            "\nAlphabet with selected special characters such as \"\"@/().-,’\" " +
            "\nThe length cannot be less than 5 and must be within 255 character")
    void nameField(){
        Allure.step("Step 1: Find Full Name Field", () -> {
            searchField.findElement(By.cssSelector("#mat-input-1"));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", searchField);
            //searchField = driver.findElement(By.xpath("//input[@formcontrolname='fullName']\n"));
        });

        Allure.step("Step: Alphabet with selected special characters such as \"\"@/().-,’\"", () -> {
            Allure.step("1. Fill in field name with special Character +^?&%$#=",()->{
                clearOutField(searchField);
                searchField.sendKeys("+^?&%$#=");
                 });
            //need to fill in all form first in order to validate this, fill in marital status field
            Allure.step("3. Click Next Button",()->{
                nextButton();
            });
            presence = isElementPresent(tempWait,By.xpath("//mat-dialog-container[contains(., \"Full Name must be alphabet with selected special character such as '/@(),.-.\")]\n"));
            if (presence) {
                //close the dialog message
                //logic for that--
                driver.findElement(By.cssSelector(".btn-dark > span:nth-child(1)")).click();

                Allure.step("Verify full name field only allow \n Alphabet with selected special characters such as \"\"@/().-,’\"", Status.PASSED);
            }else {
                //still need to validate element that only present on confirm and pay
                presence = isElementPresent(wait,By.xpath("//button[@class='btn btn-primary w-100 m-t-10 m-b-10 mat-focus-indicator mat-raised-button mat-button-base' and .//span[text()=' Proceed for Payment ']]\n"));
                //need to go back to personal info section
                if(presence){
                    driver.findElement(By.xpath("//div[@class='d-lg-flex d-md-line-flex justify-content-between align-items-center']/p[contains(text(), 'Fill in Your Details')]\n")).click();
                }
                Allure.step("Verification for special character  \"\"@/().-,’\" failed", Status.FAILED);
            }
        });
        clearOutField(searchField);
        searchField.sendKeys(name);

    }

    @Description("Marital Status cant be left as \"Select\"")
    void maritalStatusField(){
        Allure.step("Step 1: Find Marital Status  Field", () -> {
            searchField = driver.findElement(By.xpath("//mat-select[@formcontrolname='maritalStatus']\n"));
        });
        Allure.step("Select Marital status as \"Select\"", () -> {
            // Step 2 logic here
            searchField.click();
            driver.findElement(By.xpath("//mat-option[normalize-space()='--Select--']\n")).click();
            if (isElementPresent(shortWait,By.xpath("//mat-error[contains(., 'Please select your marital status')]\n"))) {

                Allure.step("Verify Marital Status Field need to be select", Status.PASSED);
            }else {
                Allure.step("Bug on Marital Status field because \"Select\" can be choose", Status.FAILED);
            }
        });
        Allure.step("Select \"Single\" as Marital Status",()->{
            driver.findElement(By.xpath("//mat-select[@formcontrolname='maritalStatus']\n")).click();
            driver.findElement(By.xpath("//mat-option[normalize-space()='Single']\n")).click();
        });

    }
    public void blankAddress(){
        Allure.step("Step 1: Verify address cant be left blank", () -> {
            // Step 1 logic here
            searchField = driver.findElement(By.xpath("//input[@formcontrolname='address1']\n"));
            clearOutField(searchField);
            WebElement TempsearchField = driver.findElement(By.xpath("//input[@formcontrolname='address2']\n"));
            clearOutField(TempsearchField);
            // Additional operations
        });
        Allure.step("It should display a validation message for blank address", () -> {
            // Step 2 logic here
            presence = isElementPresent(shortWait,By.tagName("mat-error"));
            if (presence) {
                searchField = driver.findElement(By.xpath("//input[@formcontrolname='address1']\n"));
                Allure.step("Verify address Field cant be left blank", Status.PASSED);

            }else {
                Allure.step("Verify address Field cant be left blank", Status.FAILED);
            }
        });
        searchField.sendKeys("No Where To be");
    }

    void blankEmail(){
        Allure.step("Step 1: Verify email cant be left blank", () -> {
            // Step 1 logic here
            searchField = driver.findElement(By.xpath("//input[@formcontrolname='email']\n"));
            clearOutField(searchField);
            // Additional operations
        });
        Allure.step("It should display a validation message for blank email", () -> {
            // Step 2 logic here
            presence = isElementPresent(shortWait,By.tagName("mat-error"));
            if (presence) {

                Allure.step("Verify email Field cant be left blank", Status.PASSED);
            }else {
                Allure.step("Verify email Field cant be left blank", Status.FAILED);
            }
        });
        searchField.sendKeys(email);
    }
    void blankPhoneNo(){
        Allure.step("Step 1: Verify phone number cant be left blank", () -> {
            // Step 1 logic here
            searchField = driver.findElement(By.xpath("//input[@formcontrolname='mobileNo']\n"));
            clearOutField(searchField);
            // Additional operations
        });
        Allure.step("It should display a validation message for blank email", () -> {
            // Step 2 logic here
            presence = isElementPresent(shortWait,By.tagName("mat-error"));
            if (presence) {
                Allure.step("Verify phone number Field cant be left blank", Status.PASSED);
            }else {
                Allure.step("Verify phone number Field cant be left blank", Status.FAILED);
            }
        });
        searchField.sendKeys("11111111111");
    }
    @Description("state is un-editable")
    void stateField(){
        Allure.step("Step 1: Find ID  Field", () -> {
            searchField = driver.findElement(By.xpath("//input[@formcontrolname='state']\n"));
        });
        Allure.step("Verifystate field un-editable", () -> {
            // Step 2 logic here
            boolean tempClickable = searchField.isEnabled();
            if (tempClickable) {
                Allure.step("Bug on  state, Field can be edit: ",Status.FAILED);
            }else {
                Allure.step("Verify state cant be edit", Status.PASSED);
            }
        });

    }
    @Description("city is un-editable")
    void cityField(){
        Allure.step("Step 1: Find city  Field", () -> {
            searchField = driver.findElement(By.xpath("//input[@formcontrolname='city']\n"));
        });
        Allure.step("Verify city field un-editable", () -> {
            // Step 2 logic here
            boolean tempClickable = searchField.isEnabled();
            if (tempClickable) {
                Allure.step("Bug on  city, Field can be edit: ",Status.FAILED);
            }else {
                Allure.step("Verify city cant be edit", Status.PASSED);
            }
        });

    }

    @Step("Next Button")
    void nextButton(){
        driver.findElement(By.cssSelector("button.btn-primary:nth-child(2) > span:nth-child(1)")).click();
    }
}
