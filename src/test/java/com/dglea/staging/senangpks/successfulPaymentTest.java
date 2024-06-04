package com.dglea.staging.senangpks;

import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class successfulPaymentTest extends baseTest {
    @BeforeAll
    public static void setup(){
        //Make Payment
        driver.findElement(By.xpath("//div[@class='icon-logo-wrap' and img[@alt='Billplz'] and p[text()='Billplz']]\n")).click();

        driver.findElement(By.xpath("//div[@class='body']/a[@id='btn-pay-bill' and text()='PAY']\n")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[@class='btn btn-success btn-block' and text()='Successful payment']\n")));
        driver.findElement(By.xpath("//a[@class='btn btn-success btn-block' and text()='Successful payment']\n")).click();
    }
}
