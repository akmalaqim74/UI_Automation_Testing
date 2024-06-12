package com.dglea.staging.senangpks;

import io.qameta.allure.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.DisabledIf;
import org.junit.jupiter.api.condition.EnabledIf;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.openqa.selenium.By;

import static junit.framework.Assert.assertTrue;


@Owner("Intern Akmal")
@DisplayName("3.Add Additional Coverage ")
@Story("Add Additional Coverage")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class addOnTest extends addOnLogic {

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
    @DisabledIf (value = "wsMaxAndMin", disabledReason = "Test disabled as provider selected did not support this")
    @Tag("WS")
    void windScreenSumCovered(){
        recommendedValueWS();
    }


    @Test
    @Order(2)
    @Tag("WS")
    @DisplayName("Verify Minimum value for windscreen")

    @DisabledIf (value = "wsMaxAndMin", disabledReason = "Test disabled as provider selected did not support this")
    @Description("This test will insert value lower than minimum value which is WS sumcovered - (WS sumcovered * 30%).If validation error message appear.it success")
    public void windShieldMinValue(){
        minValue();
        assertTrue(driver.findElement(By.xpath("//mat-error[strong[contains(text(), 'Please key in Sum Covered not less than RM')]]\n")).isDisplayed());
    }
    @Test
    @Order(3)
    @Tag("WS")
    @DisabledIf (value = "wsMaxAndMin", disabledReason = "Test disabled as provider selected did not support this")
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
    @DisabledIf(value = "disabledWS", disabledReason = "Test disabled as provider selected did not support this")
    public void windShield(){
        //addWindScreen to summary
        addWS();

    }
    @Test
    @Order(5)
    @DisplayName("Strike, Riot & Civil Commotion")
    @Description("Add SRCP to certificate and verify its diplayed on premium summary")
    @Tag("SRCP")
    @DisabledIf(value = "disabledSRCP", disabledReason = "Test disabled as provider selected did not support this")
    void SRCP(){
        addSRCP();
    }
    @Test
    @Order(6)
    @DisplayName("LLOP")
    @Description("Add LLOP to certificate and verify its diplayed on premium summary")
    @DisabledIf(value = "disabledLLOP", disabledReason = "Test disabled as provider selected did not support this")
    public void LLOP(){
        addLLOP();
    }

    @Test
    @Order(7)
    @DisplayName("LLTP")
    @Description("Add LLTP to certificate and verify its diplayed on premium summary")
    @DisabledIf(value = "disabledLLTP", disabledReason = "Test disabled as provider selected did not support this")
    public void LLTP(){
        addLLTP();
    }

    @Test
    @Order(8)
    @DisplayName("IOSP")
    @Description("Add IOSP to certificate and verify its diplayed on premium summary")
    @DisabledIf(value = "disabledIOSP", disabledReason = "Test disabled as provider selected did not support this")
    public void IOSP(){
       addIOSP();
    }

    @Test
    @Order(9)
    @DisplayName("Towing and Cleaning due to Water Damage")
    @Description("Add Towing to certificate and verify its diplayed on premium summary")
    @DisabledIf(value = "disabledTowing", disabledReason = "Test disabled as provider selected did not support this")
    public void towingPlan1(){
        addTowingPlan1();
    }

    @Test
    @Order(10)
    @DisplayName("Pa Plus Add On")
    @Description("Add Pa Plus to certificate and verify its diplayed on premium summary")
    @DisabledIf(value = "disabledPAPLUS", disabledReason = "Test disabled as provider selected did not support this")
    void PaPlus(){
        paPlus();

    }

    @Test
    @Order(11)
    @DisplayName("Verify supported add on by STM")
    @Description("We need to verify all the add on supported by STM can be added into premium summary")
    @Severity(SeverityLevel.CRITICAL)
    void allSupportedAddOn(){
        supportedAddOn();
    }

    @RegisterExtension
    screenShotHelper screenshot = new screenShotHelper();


}

