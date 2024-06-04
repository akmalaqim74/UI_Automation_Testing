package com.dglea.staging.senangpks;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;

public class generateQuote {
    String[] parts;
    String url;
    Boolean exist = false;
    WebElement searchField;
    public void genQ(WebDriver driver, WebDriverWait wait ){
        staffId(driver,wait);
        forms(driver,searchField,wait);
        vehiclePortal(driver, searchField, wait);

    }
    public String staffId(WebDriver driver, WebDriverWait wait){
        String aggrement ="";
        driver.get("https://dglea.staging.senangpks.com.my/");

        // Wait for the splash screen to disappear
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("splash-screen")));

        // Now, the desired element should be clickable
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.className("mat-form-field-infix")));
        element.click();
        searchField = driver.findElement(By.id("staffNo"));
        searchField.click();
        searchField.sendKeys("11111");
        searchField = driver.findElement(By.cssSelector(".step1-submit > button:nth-child(1)"));
        searchField.click();
        searchField = driver.findElement(By.cssSelector(".home-banner-container > div:nth-child(1) > p:nth-child(3)"));
        aggrement = searchField.getText();
        exist = isElementPresent(wait,By.cssSelector(".align-center > button:nth-child(1) > span:nth-child(1)"));
        return aggrement;
    }
    public void forms(WebDriver driver,WebElement searchField, WebDriverWait wait){
        System.out.println("Enter Vehicle NUmber");
        String vehNo = "WA3882D"; //scanner.nextLine();
        System.out.println("Enter NRIC");
        String NRIC = "930706145318"; //scanner.nextLine();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#mat-input-1")));
        searchField = driver.findElement(By.cssSelector("#mat-input-1"));
        searchField.click();
        searchField.sendKeys(NRIC);
        searchField = driver.findElement(By.cssSelector("#mat-input-3"));
        searchField.click();
        searchField.sendKeys(vehNo);
        searchField = driver.findElement(By.cssSelector("#mat-input-5"));
        searchField.clear();
        searchField.click();
        searchField.sendKeys("akmalmustaqimsenang@gmail.com");
        searchField = driver.findElement(By.cssSelector(".p-t-10 > div:nth-child(2)"));
        searchField.click();

    }
    public String vehiclePortal(WebDriver driver,WebElement searchField, WebDriverWait wait){
        String quotationId= " ";
        wait.until(ExpectedConditions.urlContains("https://vehicle.staging.senangpks.com.my/quotation/"));
        url = driver.getCurrentUrl();
        parts  = url.split("/");
        url = parts[parts.length - 1];
        adminPortalLogin login = new adminPortalLogin();
        login.login("https://portal.staging.senangpks.com.my/vehicle-policy/view/" + url,driver);
        searchField = driver.findElement(By.xpath("//img[@src='https://senang1.sgp1.digitaloceanspaces.com/public/assets/insurance_provider/msig_logo.svg']\n"));
        searchField.click();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        boolean isPopupPresent = isElementPresent(wait, By.tagName("mat-dialog-container"));
        if(isPopupPresent){
            searchField = driver.findElement(By.cssSelector("button.mat-focus-indicator:nth-child(1)"));
            searchField.click();
            searchField = driver.findElement(By.xpath("//img[@src='https://senang1.sgp1.digitaloceanspaces.com/public/assets/insurance_provider/takaful_malaysia_logo.svg']\n"));
            searchField.click();
        }
        searchField = driver.findElement(By.cssSelector("button.btn-primary:nth-child(2)"));
        searchField.click();

        return quotationId;
    }

    private ExpectedCondition<Boolean> responseIs200OK(String url) {
        return new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                return checkHttpResponse(url);
            }
        };
    }

    private boolean checkHttpResponse(String url) {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("HEAD");
            int responseCode = connection.getResponseCode();
            return responseCode == HttpURLConnection.HTTP_OK;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    public static boolean isElementPresent(WebDriverWait wait, By locator) {
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }
    public boolean existTest(){
        return exist;
    }
}
