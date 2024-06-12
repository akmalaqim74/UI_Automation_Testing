package com.dglea.staging.senangpks;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import io.qameta.allure.model.Status;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.DisplayName;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class STM_addOnLogic extends baseTest{
    public static double recommendedValueWS;
    public static boolean presence;
    public boolean clickable;
    static ArrayList<String> supportedAddOnList = new ArrayList<>();
    //beforeAll
    @Step
    @DisplayName("Get Recommended Value for WS")
    public static void recommendedValueWS(){
        searchField = driver.findElement(By.xpath("//div[@class='d-flex justify-content-between ng-star-inserted'][p[contains(text(), 'Recommended sum covered:')]]/p[@class='text-small grey m-b-10' and contains(text(), 'RM')]\n"));
        recommendedValueWS = Double.parseDouble(searchField.getText().replace("RM", "").replace(",", "").trim());
        System.out.println(recommendedValueWS);
    }
    //afterAll
    public static void next(){
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
    @Step
    @DisplayName("Update Quotation")
    void updateQuote(String addOnName){
        Allure.step("Step 1: Verify update quotation button exist", () -> {
            // Step 2 logic here
            presence = isElementPresent(shortWait,By.xpath("//button[span[contains(text(), 'Update Quotation Pricing')]]\n"));
            if (!presence) {
                Allure.step("cant update quotation", Status.FAILED);
            } else {
            }
        });
        Allure.step("Step 2: update Quote and verify add on displayed on summary", () -> {
            if (presence) {
                driver.findElement(By.xpath("//button[span[contains(text(), 'Update Quotation Pricing')]]\n")).click();
                wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("circle.ng-star-inserted")));
                Allure.step("Verify addon is display");
                presence = isElementPresent(wait,By.xpath("//div[p[contains(text(), 'Add On :')]]//p[contains(text(), '"+addOnName+"')]\n"));
                if(presence){
                    addOn.add(addOnName);
                }
                assertTrue(presence);
            } else {
            }
        });

    }
    @Step
    public void minValue(){
        double DminValue = recommendedValueWS - (recommendedValueWS *0.3) - 1;
        String minValue= Double.toString(DminValue);
        searchField = driver.findElement(By.xpath("//input[@formcontrolname='windshield']\n"));
        searchField.click();
        searchField.clear();
        searchField.sendKeys(minValue);
        driver.findElement(By.xpath("//input[@formcontrolname='windshield']/ancestor::div[contains(@class, 'col-xl-5 col-lg-6 col-md-5 col-sm-12 p-tb-10 bg-light-grey')]//button[span[contains(text(), 'Add to certificate')]]\n")).click();
        shortWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//mat-error[strong[contains(text(), 'Please key in Sum Covered not less than RM')]]\n")));

    }
    @Step
    public void maxValue(){
        double DmaxValue = recommendedValueWS + (recommendedValueWS *0.3) + 1;
        String maxValue= Double.toString(DmaxValue);
        System.out.println(maxValue);
        searchField = driver.findElement(By.xpath("//input[@formcontrolname='windshield']\n"));
        searchField.click();
        searchField.clear();
        searchField.sendKeys(maxValue);
        driver.findElement(By.xpath("//input[@formcontrolname='windshield']/ancestor::div[contains(@class, 'col-xl-5 col-lg-6 col-md-5 col-sm-12 p-tb-10 bg-light-grey')]//button[span[contains(text(), 'Add to certificate')]]\n")).click();
        presence = isElementPresent(shortWait,By.xpath("//div[contains(@class, 'ng-trigger-transitionMessages') and contains(@class, 'ng-star-inserted')]//mat-error[strong[contains(text(), 'Please key in Sum Covered not more than RM')]]\n"));

    }
    public void addWS(){
        String SrecommendedValueWS = Double.toString(recommendedValueWS);
        Allure.step("Step 1: Insert WS recommended value", () -> {
            searchField = driver.findElement(By.xpath("//input[@formcontrolname='windshield']\n"));
            searchField.click();
            searchField.clear();
            searchField.sendKeys(SrecommendedValueWS);
        });
        Allure.step("Step 2: Add to certificate", () -> {
            driver.findElement(By.xpath
                    ("//input[@formcontrolname='windshield']/ancestor::div[contains(@class, 'col-xl-5 col-lg-6 col-md-5 col-sm-12 p-tb-10 bg-light-grey')]//button[span[contains(text(), 'Add to certificate')]]\n"))
                    .click();

        });
        Allure.step("Step 3:Update Quotation", () -> {
            // Step 1 logic here
            updateQuote("Cover for Windscreens, Windows and Sunroof");
        });
    }
    public void addLLOP(){
        Allure.step("Step 1: ADD to certificate", () -> {
            searchField = driver.findElement(By.cssSelector("div.row:nth-child(3) > div:nth-child(2) > div:nth-child(1) > div:nth-child(1) > button:nth-child(1)"));
            searchField.click();
        });
        Allure.step("Step 2:Update Quotation", () -> {
            // Step 1 logic here
            updateQuote("Legal Liability of Passengers for Negligence Acts");
        });
    }
    public void addLLTP(){
        Allure.step("Step 1: ADD to certificate", () -> {
            driver.findElement(By.xpath("//div[contains(@class, 'row m-tb-10 box-shadow ng-star-inserted') and .//p[contains(text(), 'Legal Liability to Passengers')]]//button[contains(@class, 'btn btn-primary w-100 mat-focus-indicator mat-raised-button mat-button-base ng-star-inserted')]\n")).click();

        });
        Allure.step("Step 2:Update Quotation", () -> {
            // Step 1 logic here
            updateQuote("Legal Liability to Passengers");
        });
    }
    public void addIOSP(){
        Allure.step("Step 1: Add to certificate", () -> {
            driver.findElement(By.xpath("//div[contains(@class, 'row m-tb-10 box-shadow ng-star-inserted') and (.//p[contains(text(), 'Extreme Weather Coverage')] or .//p[contains(text(), 'Inclusion of Special Perils')] or .//p[contains(text(), 'Flood, Windstorm, Typhoon Coverage')])]//button[contains(@class, 'btn btn-primary w-100 mat-focus-indicator mat-raised-button mat-button-base ng-star-inserted')]")).click();
        });
        Allure.step("Step 2:Update Quotation", () -> {
            // Step 1 logic here
            updateQuote("Inclusion of Special Perils");
        });

    }
    public void addTowingPlan1(){
        Allure.step("Step 1: Select Plan 1", () -> {
            driver.findElement(By.cssSelector("#mat-button-toggle-12-button > div:nth-child(1)")).click();
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //presence = isElementPresent(tempWait,By.xpath("//div[@class='m-t-10 d-flex justify-content-center ng-star-inserted']/span[text()='Clear Selection']\n"));
            clickable = isElementClickable(tempWait,By.cssSelector("div.row:nth-child(12) > div:nth-child(2) > div:nth-child(1) > div:nth-child(1) > button:nth-child(1)"));
            Assumptions.assumeTrue(clickable, "unAble to select Plan 1");
        });
        Allure.step("Step 2: Add to certificate", () -> {
            //driver.findElement(By.xpath("//div[p[@class='text-medium black m-b-0' and text()=' Towing and Cleaning due to Water Damage ']]//button[.//span[text()=' Add to certificate ']]\n")).click();
            driver.findElement(By.cssSelector("div.row:nth-child(12) > div:nth-child(2) > div:nth-child(1) > div:nth-child(1) > button:nth-child(1)")).click();
        });
        Allure.step("Step 3:Update Quotation", () -> {
            // Step 1 logic here
            updateQuote("Towing and Cleaning due to Water Damage");
        });
    }
    public void paPlus(){
        Allure.step("Step 1: Select Plan 1", () -> {
            driver.findElement(By.cssSelector("#mat-button-toggle-1-button > div:nth-child(1)")).click();
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //presence = isElementPresent(tempWait,By.xpath("//div[@class='m-t-10 d-flex justify-content-center ng-star-inserted']/span[text()='Clear Selection']\n"));
            clickable = isElementClickable(tempWait,By.cssSelector("div.row:nth-child(4) > div:nth-child(2) > div:nth-child(1) > div:nth-child(1) > button:nth-child(1)"));
            Assumptions.assumeTrue(clickable, "unAble to select Plan 1");
        });
        Allure.step("Step 2: Add to certificate", () -> {
            //driver.findElement(By.xpath("//div[p[@class='text-medium black m-b-0' and text()=' Towing and Cleaning due to Water Damage ']]//button[.//span[text()=' Add to certificate ']]\n")).click();
            driver.findElement(By.cssSelector("div.row:nth-child(4) > div:nth-child(2) > div:nth-child(1) > div:nth-child(1) > button:nth-child(1)")).click();
        });
        Allure.step("Step 3:Update Quotation", () -> {
            // Step 1 logic here
            updateQuote("Towing and Cleaning due to Water Damage");
        });
    }

    public void supportedAddOn(){
        try {
            supportedAddOnList = sheetHelper.supportedAddOnSTM();
        } catch (IOException e) {
            e.printStackTrace(); // Or handle the exception in some appropriate way
        }
        for(String addOn : supportedAddOnList){
            Allure.step("Verify " + addOn + " supported by STM can be added", () -> {
                // Step 2 logic here
                presence = isElementPresent(shortWait,By.xpath("//div[p[contains(text(), 'Add On :')]]//p[contains(text(), '"+addOn+"')]\n"));
                if (!presence) {
                    Allure.step("Error/bug occured for add on: " + addOn, Status.FAILED);
                    TakesScreenshot screenshotDriver = (TakesScreenshot) driver;
                    byte[] screenshot = screenshotDriver.getScreenshotAs(OutputType.BYTES);
                    Allure.addAttachment("Add On: " + addOn + "Not displayed or some bug occured", new ByteArrayInputStream(screenshot));

                }else {
                    Allure.step("Verify add on " + addOn + " is selected", Status.PASSED);
                }
            });
        }
    }
    public void verifyCoverTypeBehaviour(){
        Allure.step("Verify CoverType will not change", () -> {
            // Step 2 logic here
            boolean tempCoverType;
            tempCoverType = coverType.equalsIgnoreCase( driver.findElement(By.xpath("//div[@class='p-tb-10 pl-5 pr-5 ng-star-inserted']/p[@class='text f600 black m-b-10' and contains(text(), 'Comprehensive')]\n")).getText());
            if (!tempCoverType) {
                Allure.step("Bug on coverType during addOn: " + addOn, Status.FAILED);
                TakesScreenshot screenshotDriver = (TakesScreenshot) driver;
                byte[] screenshot = screenshotDriver.getScreenshotAs(OutputType.BYTES);
                Allure.addAttachment("Cover Type change from " + coverType + " to " +
                                driver.findElement
                                                (By.xpath
                                                        ("//div[@class='p-tb-10 pl-5 pr-5 ng-star-inserted']/p[@class='text f600 black m-b-10' and contains(text(), 'Comprehensive')]\n"))
                                        .getText(),
                        new ByteArrayInputStream(screenshot));
            }else {
                Allure.step("Cover Type remains the same", Status.PASSED);
            }
        });
    }
}
