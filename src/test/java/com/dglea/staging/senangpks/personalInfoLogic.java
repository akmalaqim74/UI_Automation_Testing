package com.dglea.staging.senangpks;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;


public class personalInfoLogic extends baseTest{
    public static boolean presence;
    public boolean maritalStatus(){
        driver.findElement(By.xpath("//mat-select[@formcontrolname='maritalStatus']\n")).click();
        driver.findElement(By.xpath("//mat-option[normalize-space()='--Select--']\n")).click();
        presence = isElementPresent(shortWait,By.cssSelector("#mat-error-3"));
        driver.findElement(By.xpath("//mat-select[@formcontrolname='maritalStatus']\n")).click();
        driver.findElement(By.xpath("//mat-option[normalize-space()='Single']\n")).click();
        return presence;
    }
    public boolean address(){
        searchField = driver.findElement(By.xpath("//input[@formcontrolname='address1']\n"));
        searchField.click();
        searchField.clear();
        searchField.sendKeys(" ");
        WebElement TempsearchField = driver.findElement(By.xpath("//input[@formcontrolname='address2']\n"));
        TempsearchField.click();
        TempsearchField.clear();
        presence = isElementPresent(shortWait,By.cssSelector("#mat-error-4 > strong:nth-child(1)"));

        searchField = driver.findElement(By.xpath("//input[@formcontrolname='address1']\n"));
        searchField.click();
        searchField.sendKeys("No Where To be");
        return presence;
    }
}
