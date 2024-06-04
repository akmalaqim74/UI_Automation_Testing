package com.dglea.staging.senangpks;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.testng.AssertJUnit.assertTrue;
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class STM_addOnTest extends baseTest{
    public static boolean presence;
    public static double recommendedValueWS;
    Actions actions = new Actions(driver);
    @BeforeAll
    public static void setup(){

        searchField = driver.findElement(By.xpath("//div[@class='d-flex justify-content-between ng-star-inserted'][p[contains(text(), 'Recommended sum covered:')]]/p[@class='text-small grey m-b-10' and contains(text(), 'RM')]\n"));
        recommendedValueWS = Double.parseDouble(searchField.getText().replace("RM", "").replace(",", "").trim());
        System.out.println(recommendedValueWS);
    }
    @AfterAll
    public static void ended(){
        presence = isElementPresent(shortWait,By.xpath("//button[span[contains(text(), 'Update Quotation Pricing')]]\n"));
        if(presence){
            System.out.println("Update Quotation Button exist");
            driver.findElement(By.xpath("//button[span[contains(text(), 'Update Quotation Pricing')]]\n")).click();
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("circle.ng-star-inserted")));
            driver.findElement(By.cssSelector("button.btn-primary:nth-child(2)")).click();
        }else{
            System.out.println("Update Quotation Button Not exist");
            driver.findElement(By.cssSelector("button.btn-primary:nth-child(2) > span:nth-child(1)")).click();
        }
    }
    //STM

    @Test
    @Order(1)
    public void windShieldMinValue(){

        //This test will insert value lower than minimum value.If validation error message appear.it success
        double DminValue = recommendedValueWS - (recommendedValueWS *0.3) - 1;
        String minValue= Double.toString(DminValue);
        searchField = driver.findElement(By.xpath("//input[@formcontrolname='windshield']\n"));
        searchField.click();
        searchField.clear();
        searchField.sendKeys(minValue);
        driver.findElement(By.xpath("//input[@formcontrolname='windshield']/ancestor::div[contains(@class, 'col-xl-5 col-lg-6 col-md-5 col-sm-12 p-tb-10 bg-light-grey')]//button[span[contains(text(), 'Add to certificate')]]\n")).click();
        shortWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//mat-error[strong[contains(text(), 'Please key in Sum Covered not less than RM')]]\n")));
        assertTrue(driver.findElement(By.xpath("//mat-error[strong[contains(text(), 'Please key in Sum Covered not less than RM')]]\n")).isDisplayed());


    }
    @Test
    @Order(2)
    public void winShieldMaxValue(){
        //This test will insert value more than maximum value.If validation error message appear.it success
        double DmaxValue = recommendedValueWS + (recommendedValueWS *0.3) + 1;
        String maxValue= Double.toString(DmaxValue);
        System.out.println(maxValue);
        searchField = driver.findElement(By.xpath("//input[@formcontrolname='windshield']\n"));
        searchField.click();
        searchField.clear();
        searchField.sendKeys(maxValue);
        driver.findElement(By.xpath("//input[@formcontrolname='windshield']/ancestor::div[contains(@class, 'col-xl-5 col-lg-6 col-md-5 col-sm-12 p-tb-10 bg-light-grey')]//button[span[contains(text(), 'Add to certificate')]]\n")).click();
        presence = isElementPresent(shortWait,By.xpath("//div[contains(@class, 'ng-trigger-transitionMessages') and contains(@class, 'ng-star-inserted')]//mat-error[strong[contains(text(), 'Please key in Sum Covered not more than RM')]]\n"));
        assertTrue(presence);
    }
    @Test
    @Order(3)
    public void windShield(){
        String SrecommendedValueWS = Double.toString(recommendedValueWS);
        searchField = driver.findElement(By.xpath("//input[@formcontrolname='windshield']\n"));
        searchField.click();
        searchField.clear();
        searchField.sendKeys(SrecommendedValueWS);
        driver.findElement(By.xpath("//input[@formcontrolname='windshield']/ancestor::div[contains(@class, 'col-xl-5 col-lg-6 col-md-5 col-sm-12 p-tb-10 bg-light-grey')]//button[span[contains(text(), 'Add to certificate')]]\n")).click();
        presence = isElementPresent(shortWait,By.xpath("//button[@class='btn btn-primary w-100 m-b-20 mat-focus-indicator mat-raised-button mat-button-base ng-star-inserted' and span[contains(text(), 'Update Quotation Pricing')]]\n"));
        if(presence){
            driver.findElement(By.xpath("//button[@class='btn btn-primary w-100 m-b-20 mat-focus-indicator mat-raised-button mat-button-base ng-star-inserted' and span[contains(text(), 'Update Quotation Pricing')]]\n")).click();
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("circle.ng-star-inserted")));
        }else{
            System.out.println("Error");
        }
        presence = isElementPresent(wait,By.xpath("//div[p[contains(text(), 'Add On :')]]//p[contains(text(), 'Cover for Windscreens, Windows and Sunroof')]\n"));

        //driver.findElement(By.cssSelector(".btn-success > span:nth-child(1)")).click();
        assertTrue(presence);
        if(presence){
            addOn.add("Cover for Windscreens, Windows and Sunroof");
        }
    }
    @Test
    @Order(4)
    public void LLOP(){
        searchField = driver.findElement(By.cssSelector("div.row:nth-child(3) > div:nth-child(2) > div:nth-child(1) > div:nth-child(1) > button:nth-child(1)"));
        searchField.click();
        presence = isElementPresent(wait,By.xpath("//button[span[contains(text(), 'Update Quotation Pricing')]]\n"));
        System.out.println(presence);
        if(presence){
            System.out.println("Executing LLOP.......");
            driver.findElement(By.xpath("//button[span[contains(text(), 'Update Quotation Pricing')]]\n")).click();
            wait = new WebDriverWait(driver, Duration.ofSeconds(60));
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("circle.ng-star-inserted")));
        }else{
            System.out.println("Error");
        }
        presence = isElementPresent(wait,By.xpath("//div[p[contains(text(), 'Add On :')]]//p[contains(text(), 'Legal Liability of Passengers for Negligence Acts')]\n"));

        //searchField = driver.findElement(By.cssSelector("div.row:nth-child(3) > div:nth-child(2) > div:nth-child(1) > div:nth-child(1) > button:nth-child(1)"));
        //searchField.click();
        assertTrue(presence);
        if(presence){
            addOn.add("Legal Liability of Passengers for Negligence Acts");
        }

    }


    @Test
    @Order(5)
    public void LLTP(){
        driver.findElement(By.xpath("//div[contains(@class, 'row m-tb-10 box-shadow ng-star-inserted') and .//p[contains(text(), 'Legal Liability to Passengers')]]//button[contains(@class, 'btn btn-primary w-100 mat-focus-indicator mat-raised-button mat-button-base ng-star-inserted')]\n")).click();

        presence = isElementPresent(wait,By.xpath("//button[span[contains(text(), 'Update Quotation Pricing')]]\n"));
        System.out.println(presence);
        if(presence){
            System.out.println("Executing LLTP.......");
            driver.findElement(By.xpath("//button[@class='btn btn-primary w-100 m-b-20 mat-focus-indicator mat-raised-button mat-button-base ng-star-inserted' and span[contains(text(), 'Update Quotation Pricing')]]\n")).click();

            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("circle.ng-star-inserted")));
            System.out.println("Adding LLTP.......");
        }else{
            System.out.println("Error");
        }
        presence = isElementPresent(wait,By.xpath("//div[p[contains(text(), 'Add On :')]]//p[contains(text(), 'Legal Liability to Passengers')]\n"));

        //driver.findElement(By.xpath("//div[contains(@class, 'row m-tb-10 box-shadow ng-star-inserted') and .//p[contains(text(), 'Legal Liability to Passengers')]]//button[contains(@class, 'btn btn-success w-100 mat-focus-indicator mat-raised-button mat-button-base ng-star-inserted')]\n")).click();
        assertTrue(presence);
        if(presence){
            addOn.add("Legal Liability to Passengers");
        }
    }
    @Test
    @Order(6)
    public void IOSP(){
        driver.findElement(By.xpath("//div[contains(@class, 'row m-tb-10 box-shadow ng-star-inserted') and (.//p[contains(text(), 'Extreme Weather Coverage')] or .//p[contains(text(), 'Inclusion of Special Perils')])]//button[contains(@class, 'btn btn-primary w-100 mat-focus-indicator mat-raised-button mat-button-base ng-star-inserted')]")).click();
        presence = isElementPresent(wait,By.xpath("//button[span[contains(text(), 'Update Quotation Pricing')]]\n"));
        System.out.println(presence);
        if(presence){
            System.out.println("Executing IOSP.......");
            driver.findElement(By.xpath("//button[span[contains(text(), 'Update Quotation Pricing')]]\n")).click();
            wait = new WebDriverWait(driver, Duration.ofSeconds(60));
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("circle.ng-star-inserted")));
        }else{
            System.out.println("Error");
        }
        presence = isElementPresent(wait,By.xpath("//div[p[contains(text(), 'Add On :')]]//p[contains(text(), 'Inclusion of Special Perils')]\n"));

        //driver.findElement(By.cssSelector("div.row:nth-child(6) > div:nth-child(2) > div:nth-child(1) > div:nth-child(1) > button:nth-child(1)")).click();
        assertTrue(presence);
        if(presence){
            addOn.add("Inclusion of Special Perils");
        }
    }

    @Test
    @Order(7)
    public void towingPlan1(){
        driver.findElement(By.xpath("//button[@class='mat-button-toggle-button mat-focus-indicator' and @type='button' and @id='mat-button-toggle-12-button' and @name='mat-button-toggle-group-11' and .//div[@class='mat-button-toggle-label-content' and text()='PLAN 1']]\n")).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[contains(@class, 'row m-tb-10 box-shadow ng-star-inserted') and .//p[contains(text(), 'Towing and Cleaning due to Water Damage')]]//button[contains(@class, 'btn btn-primary w-100 mat-focus-indicator mat-raised-button mat-button-base ng-star-inserted')]\n")));
        driver.findElement(By.xpath("//div[contains(@class, 'row m-tb-10 box-shadow ng-star-inserted') and .//p[contains(text(), 'Towing and Cleaning due to Water Damage')]]//button[contains(@class, 'btn btn-primary w-100 mat-focus-indicator mat-raised-button mat-button-base ng-star-inserted')]\n")).click();
        presence = isElementPresent(wait,By.xpath("//button[span[contains(text(), 'Update Quotation Pricing')]]\n"));
        System.out.println(presence);
        if(presence){
            System.out.println("Executing Towing Plan 1.......");
            searchField = driver.findElement(By.xpath("//button[.//span[text()=' Update Quotation Pricing ']]\n"));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", searchField);

            wait = new WebDriverWait(driver, Duration.ofSeconds(60));
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("circle.ng-star-inserted")));
        }else{
            System.out.println("Error");
        }
        presence = isElementPresent(wait,By.xpath("//div[p[contains(text(), 'Add On :')]]//p[contains(text(), 'Towing and Cleaning due to Water Damage')]\n"));

        //driver.findElement(By.xpath("//div[contains(@class, 'row m-tb-10 box-shadow ng-star-inserted') and .//p[contains(text(), 'Towing and Cleaning due to Water Damage')]]//button[contains(@class, 'btn btn-success w-100 mat-focus-indicator mat-raised-button mat-button-base ng-star-inserted') and .//span[contains(., 'Selected')]]\n")).click();
        assertTrue(presence);
        if(presence){
            addOn.add("Towing and Cleaning due to Water Damage");
        }
    }
    @Test
    @Order(8)
    public void allAddOn(){
        /*
        //Ws
        driver.findElement(By.xpath("//input[@formcontrolname='windshield']/ancestor::div[contains(@class, 'col-xl-5 col-lg-6 col-md-5 col-sm-12 p-tb-10 bg-light-grey')]//button[span[contains(text(), 'Add to certificate')]]\n")).click();
        //LLOP
        searchField = driver.findElement(By.cssSelector("div.row:nth-child(3) > div:nth-child(2) > div:nth-child(1) > div:nth-child(1) > button:nth-child(1)"));
        searchField.click();
        //LLTP
        driver.findElement(By.xpath("//div[contains(@class, 'row m-tb-10 box-shadow ng-star-inserted') and .//p[contains(text(), 'Legal Liability to Passengers')]]//button[contains(@class, 'btn btn-primary w-100 mat-focus-indicator mat-raised-button mat-button-base ng-star-inserted')]\n")).click();

        //IOSP
        driver.findElement(By.cssSelector("div.row:nth-child(6) > div:nth-child(2) > div:nth-child(1) > div:nth-child(1) > button:nth-child(1)")).click();
        //All Driver
        driver.findElement(By.cssSelector("div.row:nth-child(9) > div:nth-child(2) > div:nth-child(1) > div:nth-child(1) > button:nth-child(1) > span:nth-child(1)")).click();
        //Towing Plan 1
        //driver.findElement(By.cssSelector("div.row:nth-child(12) > div:nth-child(2) > div:nth-child(1) > div:nth-child(1) > button:nth-child(1)")).click();
        */
        presence = isElementPresent(wait,By.xpath("//button[span[contains(text(), 'Update Quotation Pricing')]]\n"));
        System.out.println(presence);
        if(presence){
            searchField = driver.findElement(By.xpath("//button[.//span[text()=' Update Quotation Pricing ']]\n"));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", searchField);
            wait = new WebDriverWait(driver, Duration.ofSeconds(60));
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("circle.ng-star-inserted")));
        }else{
            System.out.println("Error");
        }
        for(String addOnSelected: addOn){
            presence = isElementPresent(wait,By.xpath("//div[p[contains(text(), 'Add On :')]]//p[contains(text(), '"+addOnSelected+"')]\n"));
            if(presence){
                System.out.println("PASSED: " + addOnSelected);

            }else{
                System.out.println("FAILED: " + addOnSelected);
            }
        }
        assertTrue(presence);
    }



}

