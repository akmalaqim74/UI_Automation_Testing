package com.dglea.staging.senangpks;

import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.model.Status;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.io.ByteArrayInputStream;
import java.util.List;


public class vehiclePortalLogic extends baseTest{
    static Boolean presenceC,presenceT,presence;
    public static String vehNo,NRIC;

    @Step("Generate Quote Using test data")
    public static void generateQuote(){
        System.out.println("Checking if vehicle Portal......");
        if (!isVehiclePortal()) {
            int testDataSize = testDataList.size();
            for(int i = 0; i< testDataSize; i++){
                String data = testDataList.get(i);
                String[] parts = data.split(",");
                System.out.println("Count: " + i + " \nData: "+data);
                NRIC = parts[0];
                vehNo = parts[1];
                Allure.step("Step 1: Fill In Field", () -> {
                    //NRIC Field
                    searchField = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#mat-input-1")));
                    searchField = driver.findElement(By.cssSelector("#mat-input-1"));
                    searchField.click();
                    searchField.clear();
                    searchField.sendKeys(NRIC);
                    //Full Name Field
                    searchField = driver.findElement(By.cssSelector("#mat-input-2"));
                    searchField.click();
                    searchField.clear();
                    searchField.sendKeys("Mal Test");
                    //vehicle number field
                    searchField = driver.findElement(By.cssSelector("#mat-input-3"));
                    searchField.click();
                    searchField.clear();
                    searchField.sendKeys(vehNo);
                    //postcode field
                    searchField = driver.findElement(By.cssSelector("#mat-input-4"));
                    searchField.click();
                    searchField.clear();
                    searchField.sendKeys("58000");
                    //Email Field
                    searchField = driver.findElement(By.cssSelector("#mat-input-5"));
                    searchField.clear();
                    searchField.click();
                    searchField.sendKeys("akmalmustaqimsenang@gmail.com");
                });

                Allure.step("2. Generate Quotation", () -> {
                    searchField = driver.findElement(By.cssSelector(".p-t-10 > div:nth-child(2)"));
                    searchField.click();
                });
                Allure.step("Step 3: Verify Quote Generated", () -> {
                    // Step 2 logic here
                    presence = isElementPresent(tempWait,By.xpath("//mat-dialog-container[.//button[contains(., 'CLOSE')]]\n"));

                    if (presence) {
                        Allure.step("Generate Quote Failed", Status.FAILED);
                        System.out.println("Handle cant generate quote data");
                        tempWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("cdk-overlay-0")));
                        TakesScreenshot screenshotDriver = (TakesScreenshot) driver;
                        byte[] screenshot = screenshotDriver.getScreenshotAs(OutputType.BYTES);
                        Allure.addAttachment("Generate quote Failed for NRIC: " + NRIC + "Vehicle No: " + vehNo, new ByteArrayInputStream(screenshot));
                        driver.findElement(By.cssSelector(".mat-dialog-actions > div:nth-child(1) > button:nth-child(1) > span:nth-child(1)")).click();
                        //temporary solution


                    }else {
                        Allure.step("Generate Quote Successful", Status.PASSED);
                        System.out.println("Generating Quotation wait yaa...............");
                        wait.until(ExpectedConditions.urlContains("https://vehicle.staging.senangpks.com.my/quotation"));
                        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("splash-screen")));
                        System.out.println("Thanks For Waiting, we are on vehicle portal");

                    }
                });
                if(driver.getCurrentUrl().contains("https://vehicle.staging.senangpks.com.my/quotation")){
                    i = testDataSize -1;
                }else{
                    //working on to remove data from excel if generate quote failed for the test data
                }
            }

        }
        else{
            System.out.println("Already in vehicle Portal?");
        }
    }
    public boolean isComprehensive(){
        System.out.println("co");
        Allure.step("Step 1: Click DropBox", () -> {
            searchField = driver.findElement(By.xpath("//mat-select[@formcontrolname=\"vehicleCoverTypeID\"]\n"));//finding the dropbox
            searchField.click();
        });
        Allure.step("Step 2: Verify Comprehensive available and Displayed on summary after update Quote", () -> {
            // Step 2 logic here
            presenceC = isElementPresent(shortWait,By.xpath("//span[text()=' Comprehensive ']"));
            if(presenceC){
                System.out.println("test");
                driver.findElement(By.xpath("//span[text()=' Comprehensive ']")).click();
                presenceC = isElementPresent(tempWait,By.xpath("//button[@class='btn btn-primary w-100 m-b-20 mat-focus-indicator mat-raised-button mat-button-base ng-star-inserted' and span[contains(text(), 'Update Quotation Pricing')]]\n"));
                if(presenceC){
                    driver.findElement(By.xpath("//button[@class='btn btn-primary w-100 m-b-20 mat-focus-indicator mat-raised-button mat-button-base ng-star-inserted' and span[contains(text(), 'Update Quotation Pricing')]]\n")).click();
                    wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("circle.ng-star-inserted")));
                }else{
                    System.out.println("Error button Update Quotation");
                }
                Allure.step("Comprehensive Available", Status.PASSED);
            } else{
                driver.findElement(By.xpath("//mat-option[span[contains(text(), 'Third Party Fire & Theft')]]\n")).click();
                Allure.step("Error", Status.FAILED);
            }
        });

        ((JavascriptExecutor) driver).executeScript("window.scrollBy(0, 100);");
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return isElementPresent(tempWait,By.xpath("//div[@class='p-tb-10 pl-5 pr-5 ng-star-inserted']/p[@class='text f600 black m-b-10' and contains(text(), 'Comprehensive')]\n"));
    }
    public boolean isTPFT(){
        Allure.step("Step 1: Click DropBox", () -> {
            searchField = driver.findElement(By.xpath("//mat-select[@formcontrolname=\"vehicleCoverTypeID\"]\n"));//finding the dropbox
            searchField.click();
        });
        Allure.step("Step 2: Verify TPFT available and Displayed on summary after update Quote", () -> {
            // Step 2 logic here
            presenceT = isElementPresent(shortWait,By.xpath("//mat-option[span[contains(text(), 'Third Party Fire & Theft')]]\n"));
            System.out.println(presenceT);
            if(presenceT){
                driver.findElement(By.xpath("//mat-option[span[contains(text(), 'Third Party Fire & Theft')]]\n")).click();
                presence = isElementPresent(tempWait,By.xpath("//button[@class='btn btn-primary w-100 m-b-20 mat-focus-indicator mat-raised-button mat-button-base ng-star-inserted' and span[contains(text(), 'Update Quotation Pricing')]]\n"));
                if(presence){
                    driver.findElement(By.xpath("//button[@class='btn btn-primary w-100 m-b-20 mat-focus-indicator mat-raised-button mat-button-base ng-star-inserted' and span[contains(text(), 'Update Quotation Pricing')]]\n")).click();
                    wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("circle.ng-star-inserted")));
                }else{
                    System.out.println("Failed button update Quote");
                }
                Allure.step("TPFT Available", Status.PASSED);
            } else{
                driver.findElement(By.xpath("//span[text()=' Comprehensive ']")).click();
                Allure.step("Error", Status.FAILED);
            }
        });
        ((JavascriptExecutor) driver).executeScript("window.scrollBy(0, 100);");
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return isElementPresent(shortWait,By.xpath("//div[@class='p-tb-10 pl-5 pr-5 ng-star-inserted']//p[@class='text f600 black m-b-10' and contains(text(), 'Third Party Fire & Theft')]\n"));
    }

    @Step("Select Comp and Go next")
    @Description("Select cover type as comprehensive")
    public static void selectCOMP(){
        System.out.println("co");
        Allure.step("Step 1: Click DropBox", () -> {
            searchField = driver.findElement(By.xpath("//mat-select[@formcontrolname=\"vehicleCoverTypeID\"]\n"));//finding the dropbox
            searchField.click();
        });
        Allure.step("Step 2: Verify Comprehensive available and select comprehensive as covertype", () -> {
            // Step 2 logic here
            presenceC = isElementPresent(shortWait,By.xpath("//span[text()=' Comprehensive ']"));
            if(presenceC){
                System.out.println("test");
                driver.findElement(By.xpath("//span[text()=' Comprehensive ']")).click();
                presenceC = isElementPresent(tempWait,By.xpath("//button[@class='btn btn-primary w-100 m-b-20 mat-focus-indicator mat-raised-button mat-button-base ng-star-inserted' and span[contains(text(), 'Update Quotation Pricing')]]\n"));
                if(presenceC){
                    driver.findElement(By.xpath("//button[@class='btn btn-primary w-100 m-b-20 mat-focus-indicator mat-raised-button mat-button-base ng-star-inserted' and span[contains(text(), 'Update Quotation Pricing')]]\n")).click();
                    wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("circle.ng-star-inserted")));
                    driver.findElement(By.cssSelector("button.btn-primary:nth-child(2)")).click();
                    Allure.step("Update quotation", Status.PASSED);
                }else{
                    System.out.println("Update Quotation Button Not exist");
                    driver.findElement(By.cssSelector("button.btn-primary:nth-child(2)")).click();
                    Allure.step("Update Quotation", Status.PASSED);
                }
            } else{
                driver.findElement(By.xpath("//mat-option[span[contains(text(), 'Third Party Fire & Theft')]]\n")).click();
                Allure.step("Cant choose Comprehensive as covertype", Status.FAILED);
            }
        });
    }

    public static void printProvider(){
        System.out.println("Available Providers:");
        for (String provider : availableProvider) {
            System.out.println(provider);
        }

        // Print NotAvailableProvider
        System.out.println("Not Available Providers:");
        for (String provider : NotAvailableProvider) {
            System.out.println(provider);
        }
    }
    @Step("Choosing Takaful Malaysia as provider")
    @Description("Lets just break all test for now if takaful malaysia not available")
    public static void choosingSTM(){
        for (String provider : availableProvider) {
            System.out.println(provider);
            if (provider.equals("takaful_malaysia")) {
                System.out.println("Successful in");
                driver.findElement(By.xpath("//img[@src='https://senang1.sgp1.digitaloceanspaces.com/public/assets/insurance_provider/takaful_malaysia_logo.svg']\n")).click();
                break;
            } else {
                //driver.get("https://lh6.googleusercontent.com/Bu-pRqU_tWZV7O3rJ5nV1P6NjqFnnAs8kVLC5VGz_Kf7ws0nDUXoGTc7pP87tyUCfu8VyXi0YviIm7CxAISDr2lJSwWwXQxxz98qxVfMcKTJfLPqbcfhn-QEeOowjrlwX1LYDFJN");
                driver.findElement(By.xpath("//img[@src='https://senang1.sgp1.digitaloceanspaces.com/public/assets/insurance_provider/" + provider + "_logo.svg']\n")).click();
            }
        }
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

                boolean isPopupPresent = isElementPresent(shortWait, By.tagName("mat-dialog-container"));

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
}
