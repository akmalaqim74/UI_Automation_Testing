package com.dglea.staging.senangpks;

import io.qameta.allure.Allure;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.ByteArrayInputStream;

public class screenShotHelper extends baseTest implements TestWatcher {

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        if (driver instanceof TakesScreenshot) {
            TakesScreenshot screenshotDriver = (TakesScreenshot) driver;
            byte[] screenshot = screenshotDriver.getScreenshotAs(OutputType.BYTES);
            Allure.addAttachment("Test Failure Screenshot", new ByteArrayInputStream(screenshot));
        }
    }
}
