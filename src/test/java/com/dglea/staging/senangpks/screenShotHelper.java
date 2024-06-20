package com.dglea.staging.senangpks;

import io.qameta.allure.Allure;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class screenShotHelper extends baseTest implements TestWatcher {

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        if (driver instanceof TakesScreenshot) {
            try {
                ((JavascriptExecutor) driver).executeScript("document.body.style.zoom='60%'");
                Thread.sleep(5000);
                TakesScreenshot screenshotDriver = (TakesScreenshot) driver;
                byte[] screenshot = screenshotDriver.getScreenshotAs(OutputType.BYTES);
                // Get the test display name
                String projectRoot = System.getProperty("user.dir");
                String testName = context.getDisplayName();
                saveScreenshotToFile(screenshot,projectRoot + "\\failed-testcases\\"+testName + ".png");
                Allure.addAttachment(testName, new ByteArrayInputStream(screenshot));
                ((JavascriptExecutor) driver).executeScript("document.body.style.zoom='80%'");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }
    private void saveScreenshotToFile(byte[] screenshot, String screenshotFileName) {
        try (FileOutputStream fos = new FileOutputStream(screenshotFileName)) {
            fos.write(screenshot);  // Write the byte array (screenshot) to the file
        } catch (IOException e) {
            e.printStackTrace();  // Handle any IO exceptions (e.g., file not found, permission denied)
        }
    }


}
