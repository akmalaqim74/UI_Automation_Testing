package com.dglea.staging.senangpks;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Main {


    public static void main(String[] args) {
        WebDriver driver = new FirefoxDriver();

        WebDriverWait wait;
        wait = new WebDriverWait(driver, Duration.ofSeconds(180));

        //starting the process
        generateQuote generate_quote = new generateQuote();
        generate_quote.genQ(driver,wait);
        addOn add = new addOn();
        add.addOn(driver);

    }
}