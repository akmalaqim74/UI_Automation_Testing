package com.dglea.staging.senangpks;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class confirmNpayTest extends baseTest {
    static boolean presence;

    @BeforeAll
    public static void setup(){
    }
    @AfterAll
    public static void ended(){
        driver.findElement(By.xpath("//div[@class='payment-type-option selected-type ng-star-inserted' and .//img[@src='assets/pg/online-payment.svg'] and .//p[@class='text grey m-b-0' and text()='FPX Online Banking']]\n")).click();
        //driver.findElement(By.xpath("//input[@class='mat-checkbox-input cdk-visually-hidden' and @type='checkbox' and @id='mat-checkbox-2-input']\n")).click();
        driver.findElement(By.cssSelector("#mat-checkbox-2")).click();
        driver.findElement(By.xpath("//button[@class='btn btn-primary w-100 m-t-10 m-b-10 mat-focus-indicator mat-raised-button mat-button-base' and .//span[text()=' Proceed for Payment ']]\n")).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("circle.ng-star-inserted")));
        driver.findElement(By.cssSelector("button.m-t-10:nth-child(1) > span:nth-child(1)")).click();
    }
    @Test
    public void coverType(){
        //coverType should stay the same
        searchField = driver.findElement(By.cssSelector("p.text:nth-child(4)"));
        String summaryCoverType = searchField.getText();
        assertEquals(coverType,summaryCoverType);
    }
    @Test
    public void grossContribution(){
        searchField = driver.findElement(By.xpath("//div[@class='d-flex m-b-1' and .//p[contains(text(), 'Gross Contribution')]]/p[@class='text f600 fvalue m-b-0 black']\n"));
        String grossContribution = searchField.getText();
        Double DGross = Double.parseDouble(grossContribution.replace("RM", "").replace(",", "").trim());

        assertEquals(DGross,addOnCalculation() + GrossContribution());

    }


    //Function================================================================
    public static double GrossContribution(){
        searchField = driver.findElement(By.xpath("//div[@class='d-flex m-b-5' and .//p[contains(text(), 'Basic Contribution (Market Value)')]]/p[@class='text fvalue m-b-0 grey']"));
        String basicContribution = searchField.getText().trim();
        double DBasic = Double.parseDouble(basicContribution.replace("RM", "").replace(",", "").trim());
        searchField = driver.findElement(By.xpath("//div[@class='d-flex m-b-5' and .//p[contains(text(), 'No Claim Discount (NCD)')]]/p[@class='text fvalue m-b-0']\n"));
        String NCD = searchField.getText().trim();
        double NCDValue = Double.parseDouble(NCD.replace("-","").replace("RM", "").replace(",", "").trim());
        return DBasic - NCDValue;
    }

    public static double addOnCalculation(){
        double addOnPrice = 0;
        for(String addOnName : addOn){
            presence = isElementPresent(shortWait,By.xpath("//div[@class='d-flex' and .//p[@class='text w-100 m-b-0 grey' and contains(text(), '" + addOnName+ "')]]/p[@class='text fvalue m-b-0 grey' and contains(text(), 'RM')]\n"));
            if (presence){
                searchField = driver.findElement(By.xpath("//div[@class='d-flex' and .//p[@class='text w-100 m-b-0 grey' and contains(text(), '" + addOnName+ "')]]/p[@class='text fvalue m-b-0 grey' and contains(text(), 'RM')]\n"));
                double tempPrice = Double.parseDouble(searchField.getText().replace("RM", "").replace(",", "").trim());
                addOnPrice +=  tempPrice;
            }else{
                addOnPrice += 0;
            }
            System.out.println("Total AddOn: " + addOnPrice);

        }
        return Math.round(addOnPrice * 100.0) / 100.0;

    }
}
