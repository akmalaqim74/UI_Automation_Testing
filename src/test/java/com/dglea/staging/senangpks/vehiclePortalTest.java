package com.dglea.staging.senangpks;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.testng.AssertJUnit.assertEquals;
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class vehiclePortalTest extends baseTest {


    static ArrayList<String> tabs;
    static WebDriverWait tempWait;
    static WebElement tempSearch;
    static Boolean presenceC,presenceT,presence;
    static Actions action = new Actions(driver);
    @BeforeAll
    public static void setup(){
        System.out.println("Checking if vehicle Portal......");
        if (!isVehiclePortal()) {
            System.out.println("Executing Fill in form");
            generateQuote();// Ensure we navigate to the login page if not already there
             // Open admin Portal
        }
        else{
            System.out.println("Already in vehicle Portal?");
        }
        adminPortalLogin();
        tabs = new ArrayList<String>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(0));
        System.out.println("Finding Provider............");
        providerList();
        System.out.println("Finding Provider ended");
        System.out.println("Available Providers:");
        for (String provider : availableProvider) {
            System.out.println(provider);
            if(provider.equals("takaful_malaysia")){
                System.out.println("Successful in");
                driver.findElement(By.xpath("//img[@src='https://senang1.sgp1.digitaloceanspaces.com/public/assets/insurance_provider/takaful_malaysia_logo.svg']\n")).click();
                break;
            }else{
                driver.findElement(By.xpath("//img[@src='https://senang1.sgp1.digitaloceanspaces.com/public/assets/insurance_provider/"+provider+"_logo.svg']\n")).click();
            }
        }
        // Print NotAvailableProvider
        System.out.println("Not Available Providers:");
        for (String provider : NotAvailableProvider) {
            System.out.println(provider);
        }
    }
    @AfterAll
    public static void ended(){
        System.out.println("Ending vehicle portal test......");
        action.sendKeys(Keys.ESCAPE);

        //update Quote
        searchField = driver.findElement(By.xpath("//mat-select[@formcontrolname=\"vehicleCoverTypeID\"]\n"));//finding the dropbox
        searchField.click();
        tempWait = new WebDriverWait(driver, Duration.ofSeconds(10));
        presenceC = isElementPresent(tempWait,By.xpath("//span[text()=' Comprehensive ']"));
        if(presenceC){
            System.out.println("Checking update Quotation Button");
            searchField = driver.findElement(By.xpath("//span[text()=' Comprehensive ']"));
            coverType = searchField.getText();
            System.out.println("Cover Type: " + coverType);
            searchField.click();
            presenceC = isElementPresent(tempWait,By.xpath("//button[@class='btn btn-primary w-100 m-b-20 mat-focus-indicator mat-raised-button mat-button-base ng-star-inserted' and span[contains(text(), 'Update Quotation Pricing')]]\n"));
            if(presenceC){
                System.out.println("Update Quotation Button exist");
                driver.findElement(By.xpath("//button[@class='btn btn-primary w-100 m-b-20 mat-focus-indicator mat-raised-button mat-button-base ng-star-inserted' and span[contains(text(), 'Update Quotation Pricing')]]\n")).click();
                wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("circle.ng-star-inserted")));
                driver.findElement(By.cssSelector("button.btn-primary:nth-child(2)")).click();
            }else{
                System.out.println("Update Quotation Button Not exist");
                driver.findElement(By.cssSelector("button.btn-primary:nth-child(2)")).click();
            }
        }else{
            searchField = driver.findElement(By.xpath("//mat-option[span[contains(text(), 'Third Party Fire & Theft')]]\n"));
            searchField.click();
            coverType = searchField.getText();
            presence = isElementPresent(tempWait,By.xpath("//button[@class='btn btn-primary w-100 m-b-20 mat-focus-indicator mat-raised-button mat-button-base ng-star-inserted' and span[contains(text(), 'Update Quotation Pricing')]]\n"));
            if(presence){
                System.out.println("Update Quotation Button exist");
                driver.findElement(By.xpath("//button[@class='btn btn-primary w-100 m-b-20 mat-focus-indicator mat-raised-button mat-button-base ng-star-inserted' and span[contains(text(), 'Update Quotation Pricing')]]\n")).click();
                wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("circle.ng-star-inserted")));
                driver.findElement(By.cssSelector("button.btn-primary:nth-child(2)")).click();
            }else{
                System.out.println("Update Quotation Button Not exist");
                driver.findElement(By.cssSelector("button.btn-primary:nth-child(2)")).click();
            }

        }
        System.out.println("Available Providers:");
        for (String provider : availableProvider) {
            System.out.println(provider);
        }

        // Print NotAvailableProvider
        System.out.println("Not Available Providers:");
        for (String provider : NotAvailableProvider) {
            System.out.println(provider);
        }

        //button quotation


    }


    @Test
    @Order(1)
    public void canGenerateQuote(){
        System.out.println("can we generate a quote");
        wait.until(ExpectedConditions.urlContains("https://vehicle.staging.senangpks.com.my/quotation/"));
        System.out.println("can we generate a quote");
        assertTrue(driver.getCurrentUrl().contains("https://vehicle.staging.senangpks.com.my/quotation"));
    }

    @Test
    @Order(2)
    public void isComprehensive(){
        //wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#mat-select-13 > div:nth-child(1) > div:nth-child(1)")));
        System.out.println("co");
        searchField = driver.findElement(By.xpath("//mat-select[@formcontrolname=\"vehicleCoverTypeID\"]\n"));//finding the dropbox
        searchField.click();
        tempWait = new WebDriverWait(driver, Duration.ofSeconds(10));
        presenceC = isElementPresent(tempWait,By.xpath("//span[text()=' Comprehensive ']"));
        if(presenceC){
            System.out.println("test");
            driver.findElement(By.xpath("//span[text()=' Comprehensive ']")).click();
            presenceC = isElementPresent(tempWait,By.xpath("//button[@class='btn btn-primary w-100 m-b-20 mat-focus-indicator mat-raised-button mat-button-base ng-star-inserted' and span[contains(text(), 'Update Quotation Pricing')]]\n"));
            if(presenceC){
                driver.findElement(By.xpath("//button[@class='btn btn-primary w-100 m-b-20 mat-focus-indicator mat-raised-button mat-button-base ng-star-inserted' and span[contains(text(), 'Update Quotation Pricing')]]\n")).click();
                wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("circle.ng-star-inserted")));
            }else{
                action.sendKeys(Keys.ESCAPE);
            }
        }else{
            driver.findElement(By.xpath("//mat-option[span[contains(text(), 'Third Party Fire & Theft')]]\n")).click();

        }
        action.sendKeys(Keys.ESCAPE);
        assertTrue(driver.findElement(By.xpath("//p[text()='Comprehensive']\n")).isDisplayed());
    }
    @Test
    @Order(3)
    public void isTPFTAvailable(){
        searchField = driver.findElement(By.xpath("//mat-select[@formcontrolname=\"vehicleCoverTypeID\"]\n"));//finding the dropbox
        searchField.click();
        tempWait = new WebDriverWait(driver, Duration.ofSeconds(10));
        presenceT = isElementPresent(tempWait,By.xpath("//mat-option[span[contains(text(), 'Third Party Fire & Theft')]]\n"));
        System.out.println(presenceT);
        if(presenceT){
            driver.findElement(By.xpath("//mat-option[span[contains(text(), 'Third Party Fire & Theft')]]\n")).click();
            presenceT = isElementPresent(tempWait,By.xpath("//button[@class='btn btn-primary w-100 m-b-20 mat-focus-indicator mat-raised-button mat-button-base ng-star-inserted' and span[contains(text(), 'Update Quotation Pricing')]]\n"));
            if(presenceT){
                driver.findElement(By.xpath("//button[@class='btn btn-primary w-100 m-b-20 mat-focus-indicator mat-raised-button mat-button-base ng-star-inserted' and span[contains(text(), 'Update Quotation Pricing')]]\n")).click();
                wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("circle.ng-star-inserted")));
            }else{
                action.sendKeys(Keys.ESCAPE);
            }
        }else{
            driver.findElement(By.xpath("//span[text()=' Comprehensive ']")).click();
            action.sendKeys(Keys.ESCAPE);
        }
        presenceT = isElementPresent(tempWait,By.xpath("//p[text()='Third Party Fire & Theft']\n"));
        assertTrue(presenceT);
    }


    public  static void providerList(){
        //Find container for provider
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".splash-screen")));
        List<WebElement> providerAppear = driver.findElements(By.cssSelector("div.d-inline-flex.text-center.ng-star-inserted"));
        for (int i = 0; i < providerAppear.size(); i++) {
            try {
                // Re-locate the provider elements to avoid stale elements
                providerAppear = driver.findElements(By.cssSelector("div.d-inline-flex.text-center.ng-star-inserted"));
                WebElement box = providerAppear.get(i);

                WebElement nestedDiv = box.findElement(By.cssSelector("div.cursor-pointer"));
                WebElement imgElement = nestedDiv.findElement(By.tagName("img"));
                String imgUrl = imgElement.getAttribute("src");

                imgElement.click();

                tempWait = new WebDriverWait(driver, Duration.ofSeconds(5));
                boolean isPopupPresent = isElementPresent(tempWait, By.tagName("mat-dialog-container"));

                if (!isPopupPresent) {
                    availableProvider.add(extractProviderName(imgUrl));

                } else {
                    searchField = driver.findElement(By.tagName("mat-dialog-container"));
                    WebElement closeButton = searchField.findElement(By.tagName("button"));
                    closeButton.click();
                    NotAvailableProvider.add(extractProviderName(imgUrl));

                }
            } catch (StaleElementReferenceException e) {
                System.out.println("StaleElementReferenceException: " + e.getMessage());
                // Re-try the iteration if stale element exception occurs
                i--;
            } catch (Exception e) {
                System.out.println("An error occurred while processing box: " + e.getMessage());
                e.printStackTrace();
            }
        }

    }
    //========================================================================================
    private static String extractProviderName(String url) {
        // Extract the provider name from the URL
        String[] parts = url.split("/");
        String fileName = parts[parts.length - 1];
        String providerName = fileName.split("_logo")[0];
        return providerName;
    }

    //UNUSED FUNCTION
    /*@Test
    public void validateSumInsured(){
        driver.switchTo().window(tabs.get(1));
        searchField = driver.findElement(By.cssSelector("#mat-tab-label-0-2 > div:nth-child(1)"));
        searchField.click();
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#mat-expansion-panel-header-6 > span:nth-child(1) > mat-panel-title:nth-child(1)")));
        searchField = driver.findElement(By.cssSelector("#mat-expansion-panel-header-6 > span:nth-child(1) > mat-panel-title:nth-child(1)"));
        searchField.click();


    }
    public static void storeAllVar(){
        searchField = driver.findElement(By.cssSelector("mat-form-field.ng-tns-c27-5 > div:nth-child(1) > div:nth-child(1)"));
        searchField.click();
        searchField = driver.findElement(By.xpath("//span[text()=' Comprehensive ']"));
        Select dropdown = new Select(searchField);
        List<WebElement> options = dropdown.getOptions();
        availableCoverType = new String[dropdown.getOptions().size()];
        int i = 0;
        for (WebElement option : options) {
            availableCoverType[i] = option.getText();
            i++;
        }
    }*/


}
