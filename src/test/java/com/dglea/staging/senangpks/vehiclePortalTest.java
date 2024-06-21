package com.dglea.staging.senangpks;

import io.qameta.allure.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.DisabledIf;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.ByteArrayInputStream;

import static org.junit.jupiter.api.Assertions.assertTrue;
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Epic("Get Quotation Section")
@Story("As a user, I want to view my vehicle details")
@DisplayName("2.Vehicle Portal(Get Quotation)")
@Owner("Intern Akmal")
public class vehiclePortalTest extends vehiclePortalLogic {
    @AfterAll
    public static void ended() {

        printProvider();
    }
    @Test
    @Order(1)
    @DisplayName("Generate Quote")
    @Description("""
            Generate a quote using data from excel
            Provider will be choose and admin portal will be open.
            If provider selected is not available, we will generate quotation again. \
            If provider basic contribution == 0, we will generate quote again""")
    void generateQuotation(){
        generateQuote();
        adminPortalLogin();
        //adminPortalLogin(); Can ignore for now, im not trying to validate against this
        /*providerList();
        This can be used to find out what available provider this testdata got, the reason I comment because
        this method making the test slow
         */
        System.out.println("NRIC: " + NRIC + "\nvechicle Register No: " + vehRegNo);
    }
    @Test
    @Order(2)
    @DisplayName("Verify On vehicle portal")

    public void onVehiclePortalTest() {
        TakesScreenshot screenshotDriver = (TakesScreenshot) driver;
        byte[] screenshot = screenshotDriver.getScreenshotAs(OutputType.BYTES);
        Allure.addAttachment("ScreenSot Quotation Page", new ByteArrayInputStream(screenshot));
        assertTrue(driver.getCurrentUrl().contains("https://vehicle.staging.senangpks.com.my/quotation"));
    }

    @Test
    @Order(3)
    @DisplayName("GQ001 verify Vehicle Registration")
    @Description("Verify value on vehicle portal against admin portal api response")
    void vehRegTest(){
        VehDetailsValidateValueAgainstAPI("1");
    }
    @Test
    @Order(3)
    @DisplayName("GQ002 verify Make and Model")
    @Description("Verify value on vehicle portal against admin portal api response")
    void makeAndModelTest(){
        VehDetailsValidateValueAgainstAPI("2");
    }
    @Test
    @Order(3)
    @DisplayName("GQ003 verify Chassis Number")
    @Description("Verify value on vehicle portal against admin portal api response")
    void chassisNoTest(){
        validateValueAgainstAPI("Chassis Number");
    }
    @Test
    @Order(3)
    @DisplayName("GQ004 verify Engine Number")
    @Description("Verify value on vehicle portal against admin portal api response")
    void engineNoTest(){
        validateValueAgainstAPI("Engine Number");
    }
    @Test
    @Order(3)
    @DisplayName("GQ005 verify Engine CC")
    @Description("Verify value on vehicle portal against admin portal api response")
    void engineCCTest(){
        validateValueAgainstAPI("Engine CC");
    }
    @Test
    @Order(3)
    @DisplayName("GQ006 verify Manufacturing Year")
    @Description("Verify value on vehicle portal against admin portal api response")
    void manufacturingYearTest(){
        validateValueAgainstAPI("Manufacturing Year");

    }
    @Test
    @Order(3)
    @DisplayName("GQ009 verify Type of Cover")
    @Description("Verify value on vehicle portal against admin portal api response")
    @Disabled("Logic cant be applied to this")
    void typeOfCover(){
        validateValueAgainstAPI("Type of Cover");

    }
    @Test
    @Order(3)
    @DisplayName("GQ011 verify NCD")
    @Description("Verify value on vehicle portal against admin portal api response")
    void ncdTest(){
        validateValueAgainstAPI("NCD");
    }





    @Test
    @Order(4)
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("GQ031 Verify Comprehensive is supported")
    @Description("Choose comprehensive then update the quote, validate whether Premium summary will show comprehensive")
    public void isComprehensiveTest() {
        assertTrue(isComprehensive());
        //assertTrue(driver.findElement(By.xpath("//div[@class='p-tb-10 pl-5 pr-5 ng-star-inserted']//p[@class='text f600 black m-b-10' and contains(text(), 'Comprehensive')]\n")).isDisplayed());
    }

    @Test
    @Order(5)
    @DisplayName("GQ032 Verify TPFT is supported")
    @Description("Choose comprehensive then update the quote, validate whether Premium summary will show TPFT")
    @Severity(SeverityLevel.CRITICAL)
    @DisabledIf (value = "conditionVehiclePortal", disabledReason = "Test disabled as provider selected did not support this")
    public void isTPFTAvailableTest() {
        assertTrue(isTPFT());
        //assertTrue(driver.findElement(By.xpath("//div[@class='p-tb-10 pl-5 pr-5 ng-star-inserted']//p[@class='text f600 black m-b-10' and contains(text(), 'Third Party Fire & Theft')]\n")).isDisplayed());
    }
    @Test
    @Order(6)
    @DisplayName("Update Quotation")
    void goToaddOn(){
        selectCOMP();
    }

    @RegisterExtension
    screenShotHelper screenshot = new screenShotHelper();
    @RegisterExtension
    googleSheetHelper googleSheet = new googleSheetHelper();


}

