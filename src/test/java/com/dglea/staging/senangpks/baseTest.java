package com.dglea.staging.senangpks;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class baseTest {
    protected static WebDriver driver;
    static WebElement searchField;
    static WebDriverWait wait,shortWait,tempWait;
    protected static String coverType;
    static ArrayList<String> availableProvider = new ArrayList<>();
    static ArrayList<String> NotAvailableProvider = new ArrayList<>();
    public static  ArrayList<String> testDataList = new ArrayList<>();
    static ArrayList<String> supportedAddOnList = new ArrayList<>();
    static ArrayList<String> tabs = new ArrayList<String>();
    public static String name,email,postcode;
    public static String provider;
    static String quotationId;
    static String jsonData;
    @BeforeAll
    public static void setUpClass() {
        driver = webDriverManager.getDriver();
        ((JavascriptExecutor) driver).executeScript("document.body.style.zoom='70%'");
        wait = webDriverManager.getWait();
        shortWait = webDriverManager.getShortWait();
        tempWait = webDriverManager.getTempWait();
        //Fetch data from testData
        try {
            testDataList = sheetHelper.getDataFromExcel();
        } catch (IOException e) {
            e.printStackTrace(); // Or handle the exception in some appropriate way
        }
        personalInfo();
        provider();
        try {
            supportedAddOnList = sheetHelper.supportedAddOn(provider);
        } catch (IOException e) {
            e.printStackTrace(); // Or handle the exception in some appropriate way
        }
    }
    static void personalInfo(){
        name = "Mal Testing";
        email = "akmalmustaqimsenang@gmail.com";
        postcode = "58000";
    }
    static void provider(){
        /* just copy one of this
        chubb
        takaful_ikhlas
        takaful_malaysia
        aig
        zurich
        zurich_general
        etiqa
        msig
         */
        //provider = "chubb";
        provider = "takaful_malaysia";
    }
    public boolean conditionVehiclePortal(){
        if(provider.equals("takaful_malaysia"))
            return true;
        else
            return false;
    }
    public boolean wsMaxAndMin(){
        if(provider.equals("takaful_malaysia"))
            return false;
        else
            return true;
    }




    public static void insertStaffId() {
        System.out.println("Checking if it's form page...");
        if (!isFormPage()) {
            System.out.println("Navigating to form page...");
            try {
                System.out.println("Executing staffId method");
                // Ensure the #staffNo element is visible and clickable
                searchField = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#staffNo")));
                searchField = driver.findElement(By.id("staffNo"));
                searchField.click();
                searchField.sendKeys("11111");
                searchField = driver.findElement(By.cssSelector(".step1-submit > button:nth-child(1)"));
                searchField.click();

                System.out.println("staffId method executed successfully");
            } catch (Exception e) {
                System.err.println("An error occurred in staffId method: " + e.getMessage());
                e.printStackTrace();
            }
        }
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".align-center > button:nth-child(1)")));
        driver.findElement(By.cssSelector(".align-center > button:nth-child(1)")).click();

    }


    //----------------------------------------------------------------------------
    @Step("Open Admin Portal and Get API LOG Response")
    public static void adminPortalLogin(){
        Allure.step("Step 1: Open Admin Portal", () -> {
            // Step 1 logic here
            /*wait.until(ExpectedConditions.urlContains("https://vehicle.staging.senangpks.com.my/quotation/"));
            quotationId = driver.getCurrentUrl();
            String[] parts  = quotationId.split("/");
            quotationId = parts[parts.length - 1];*/
            ((JavascriptExecutor) driver).executeScript("window.open();");
            tabs = new ArrayList<String>(driver.getWindowHandles());
            driver.switchTo().window(tabs.get(1));
            driver.get("https://portal.staging.senangpks.com.my/auth/login");
            System.out.println(quotationId);
            //-----------------------------------------------------------------------
            //Login In
            Allure.step("Step 1.1: login", () -> {
                // Step 1 login here
                System.out.println("Current URL: " + driver.getCurrentUrl());
                wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("splash-screen")));

                searchField = driver.findElement(By.cssSelector("#mat-input-0"));
                searchField.click();
                searchField.sendKeys("farah.diyana@senang.io");
                searchField = driver.findElement(By.cssSelector("#mat-input-1"));
                searchField.click();
                searchField.sendKeys("EdwY;0Haa!NX");
                searchField = driver.findElement(By.cssSelector(".mat-button-wrapper"));
                searchField.click();
                // Additional operations
            });
            Allure.step("Step 1.2: Open Quotation", () -> {
                // Step 1 logic here
                System.out.println("Initialization logic");
                wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("splash-screen")));
                wait.until(ExpectedConditions.urlContains("https://portal.staging.senangpks.com.my/dashboard"));
                driver.get("https://portal.staging.senangpks.com.my/vehicle-policy/view/22c8f7ea-2b9d-480f-842f-fec9a11af556" );
                //driver.get("https://portal.staging.senangpks.com.my/vehicle-policy/view/" + quotationId);
                wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("splash-screen")));
                ((JavascriptExecutor) driver).executeScript("document.body.style.zoom='60%'");
                // Additional operations
            });


        });
        Allure.step("Step 2:Navigate To Api Log", () -> {
            searchField = driver.findElement(By.xpath("//div[@role='tab' and .//div[text()='API Logs']]\n"));
            searchField.click();
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Allure.step("Step 2.1: Navigate to provider API LOG", () -> {
                // Step 1 logic here
                searchField = driver.findElement(By.xpath(adminPortalAPILOGS(provider)));
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", searchField);
                searchField.click();
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // Additional operations
            });
            Allure.step("Step 2.2: Navigate to get vehicle info api response", () -> {
                getVehicleInfoAPI(provider);

                //driver.findElement(By.xpath("//div[@class='mat-tab-labels']//div[contains(text(), 'Response')]")).click();
                List<WebElement> tabLabels = driver.findElements(By.cssSelector(".mat-tab-label"));
                // Loop through the elements
                for (WebElement tab : tabLabels) {
                    // Get the text of each tab
                    String tabText = tab.findElement(By.cssSelector(".mat-tab-label-content")).getText().trim();

                    // Check if the text is 'Response'
                    if ("Response".equals(tabText)) {
                        // Scroll the tab into view and click it
                        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", tab);
                        tab.click();
                        break;  // Exit the loop after clicking the desired tab
                    }
                }
            });
            Allure.step("Step 2.3: Copy JSON", () -> {

                driver.findElement(By.cssSelector("#cdk-accordion-child-2 > div:nth-child(1) > div:nth-child(1) > div:nth-child(2) > div:nth-child(1) > button:nth-child(1)"));
                copyResponse(provider);
            });



        });



        //driver.switchTo().window(tabs.get(0));
    }
    //----------------------------------------------------------------------------
    public static boolean isElementPresent(WebDriverWait wait, By locator) {
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }
    public static boolean isElementClickable(WebDriverWait waitM,By locator){
        try {
            waitM.until(ExpectedConditions.elementToBeClickable(locator));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }
    //Page Confirmation
    protected static boolean isFormPage() {
        // Check if already on login page or logged in
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        Boolean staffInserted = isElementPresent(wait,By.cssSelector(".align-center > button:nth-child(1) > span:nth-child(1)"));
        System.out.println(staffInserted);
        wait = new WebDriverWait(driver, Duration.ofSeconds(50));
        return staffInserted;
    }
    protected static boolean isVehiclePortal() {
        // Check if already on login page or logged in
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        Boolean staffInserted = isElementPresent(wait,By.cssSelector(".mat-slider-track-background"));
        System.out.println(staffInserted);
        wait = new WebDriverWait(driver, Duration.ofSeconds(50));
        return staffInserted;
    }
    //useful for admin portal
    public String temprovider(String tempProvider){
        if(provider.contains("_")){
            return provider.replace("_"," ");
        }else
            return tempProvider;
    }
    public static String adminPortalAPILOGS(String tempProvider){

        switch (tempProvider){
            case "takaful_malaysia":
                return "//mat-expansion-panel-header[.//p[text()='TAKAFUL MALAYSIA']]";
            case "takaful_ikhlas":
                return " ";
            case "etiqa":
                return " ";
            default:
                return " ";
        }

    }
    public static void getVehicleInfoAPI(String tempProvider){

        switch (tempProvider){
            case "takaful_malaysia":
                //return "div.navi-item:nth-child(10) > a:nth-child(1) > span:nth-child(1)";
                //return "//a[contains(., 'GetVIXNCD')]\n";
                List<WebElement> naviItems = driver.findElements(By.cssSelector("div.navi-item"));

                // Iterate through navi-items and find the one containing "GetVIXNCD"
                for (WebElement naviItem : naviItems) {
                    searchField = naviItem.findElement(By.cssSelector("span.navi-text"));
                    String naviText = searchField.getText();

                    // Check if the naviText contains "GetVIXNCD"
                    if (naviText.contains("GetVIXNCD")) {
                        // Scroll into view
                        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", naviItem);

                        // Click on the element
                        naviItem.click();
                        break;  // Exit the loop after clicking the element
                    }
                }
                break;
            case "takaful_ikhlas":
                //return " ";
            case "etiqa":
                //return " ";
            default:
                //return " ";
        }

    }
    public static void copyResponse(String tempProvider) throws Exception{

        switch (tempProvider){
            case "takaful_malaysia":
                searchField =  driver.findElement(By.id("TAKAFUL_MALAYSIA"));
                searchField = searchField.findElement(By.xpath("//mat-tab-body[contains(@class, 'mat-tab-body')]//pre\n"));
                jsonData = searchField.getText();
                System.out.println(searchField.getText());
                break;
            case "takaful_ikhlas":
                //return " ";
            case "etiqa":
                //return " ";
            default:
                //return " ";
        }
        String currentDir = System.getProperty("user.dir");
        String filePath = currentDir + "\\src\\test\\resources\\vehicleData.json";
        File directory = new File(filePath);
        if (!directory.exists()) {
            System.out.println("Creating new json data");
            directory.createNewFile();
        }else{
            System.out.println("Delete existing vehicle data");
            FileUtils.deleteDirectory(directory);
            System.out.println("Creating new Directory for failed testcases");
            directory.createNewFile();
        }
        FileWriter fileWriter = new FileWriter(filePath);
        fileWriter.write(jsonData);
        fileWriter.close();
        JsonElement jsonElement = JsonParser.parseReader(new FileReader(filePath));
        if (jsonElement.isJsonArray()) {


        }
    }


}
