package com.dglea.staging.senangpks;

import io.qameta.allure.*;
import io.qameta.allure.model.Status;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.interactions.Actions;

import java.io.ByteArrayInputStream;

import static junit.framework.Assert.assertTrue;

@Story("Add Additional Coverage  ")
@Owner("Intern Akmal")
@DisplayName("Add Additional Coverage ")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class STM_addOnTest extends STM_addOnLogic{

    @BeforeAll
    static void setup(){
        Allure.step("Step :get cover type", () -> {
            // Step 1 logic here
            coverType = driver.findElement(By.xpath("//div[@class='p-tb-10 pl-5 pr-5 ng-star-inserted']/p[@class='text f600 black m-b-10' and contains(text(), 'Comprehensive')]\n")).getText();
            // Additional operations
        });
    }
    @AfterEach
    @DisplayName("Verify Cover Type Behaviour after update quote")
    @Description("Theres a bug when adding addOn, covertype will change from comprehensive to tpft")
    @Severity(SeverityLevel.BLOCKER)
    void eachTestEnded(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        verifyCoverTypeBehaviour();
    }
    @AfterAll
    public static void ended(){
        next();
    }
    //STM
    @Test
    @Order(1)
    @DisplayName("Get Wind Screen recommended Value")
    @Tag("WS")
    void windScreenSumCovered(){
        recommendedValueWS();
    }

    @Test
    @Order(2)
    @Tag("WS")
    @DisplayName("Verify Minimum value for windscreen")
    @Description("This test will insert value lower than minimum value which is WS sumcovered - (WS sumcovered * 30%).If validation error message appear.it success")
    public void windShieldMinValue(){
        minValue();
        assertTrue(driver.findElement(By.xpath("//mat-error[strong[contains(text(), 'Please key in Sum Covered not less than RM')]]\n")).isDisplayed());
    }
    @Test
    @Order(3)
    @Tag("WS")
    @DisplayName("Verify Maximum value for windscreen")
    @Description("This test will insert value lower than minimum value which is WS sumcovered + (WS sumcovered * 30%).If validation error message appear.it success")
    public void winShieldMaxValue(){
        maxValue();
        //This test will insert value more than maximum value.If validation error message appear.it success
        assertTrue(presence);
    }
    @Test
    @Order(4)
    @DisplayName("WindShield")
    @Description("Add Windshield to certificate and verify its diplayed on premium summary")
    @Tag("WS")
    public void windShield(){
        //addWindScreen to summary
        addWS();

    }
    @Test
    @Order(5)
    @DisplayName("LLOP")
    @Description("Add LLOP to certificate and verify its diplayed on premium summary")

    public void LLOP(){
        addLLOP();
    }
    @Test
    @Order(6)
    @DisplayName("LLTP")
    @Description("Add LLTP to certificate and verify its diplayed on premium summary")
    public void LLTP(){
        addLLTP();
    }
    @Test
    @Order(7)
    @DisplayName("IOSP")
    @Description("Add IOSP to certificate and verify its diplayed on premium summary")

    public void IOSP(){
       addIOSP();
    }
    @Test
    @Order(7)
    @DisplayName("Towing and Cleaning due to Water Damage")
    @Description("Add Towing to certificate and verify its diplayed on premium summary")
    public void towingPlan1(){
        addTowingPlan1();
    }
    @Test
    @Order(8)
    @DisplayName("Pa Plus Add On")
    @Description("Add Pa Plus to certificate and verify its diplayed on premium summary")
    void PaPlus(){
        paPlus();

    }
    @Test
    @Order(9)
    @DisplayName("Verify supported add on by STM")
    @Description("We need to verify all the add on supported by STM can be added into premium summary")
    @Severity(SeverityLevel.CRITICAL)
    void allSupportedAddOn(){
        supportedAddOn();
    }

    @RegisterExtension
    screenShotHelper screenshot = new screenShotHelper();


}

