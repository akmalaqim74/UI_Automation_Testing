package com.dglea.staging.senangpks;

import io.qameta.allure.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Epic("Landing Page")
@Story("As User, sometimes I missed out to fill in certain field")
@Tag("Field `Validation")
@DisplayName("1.Landing Page")
@Owner("Intern Akmal")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class landingPageTest extends landingPageLogic {

    @Test
    @Order(1)
    @DisplayName("Pre Staff Id Insert")
    @Description("Are we on Landing Page (Fill in Form)?")
    public void onForm(){
        Allure.step("Step 1: Insert Staff Id", () -> {
            insertStaffId();
            assertTrue(driver.findElement(By.cssSelector(".align-center > button:nth-child(1)")).isDisplayed());
        });
        fillInDetails();
        }
    @Test
    @Order(2)
    @DisplayName("LP010 Verify NRIC field cant be empty")
    @Description("Validation If NRIC field is empty")
    public void NricRequiredTest(){
        blankNRICField();
    }
    @Test
    @Order(3)
    @DisplayName("LP005 Verify name field cant be empty")
    @Description("Validation if name field is empty")
    void NameRequiredTest(){
        blankNameField();
    }

    @Test
    @DisplayName("LP002 Verify vehicle number field cant be empty")
    @Description("Validation if vehicle Number field is empty")
    public void vehNoRequiredTest(){
       blankVehRegField();
    }

    @Test
    @DisplayName("LP014 Verify postcode field cant be empty")
    @Description("Validation if postcode empty")
    void postcodeRequiredTest(){
        blankPostCodeField();
    }
    @Test
    @DisplayName("LP016 Verify email field cant be empty")
    @Description("Validation if email address is empty")
    void emailRequiredTest(){
        blankEmailField();
    }

    @RegisterExtension
    screenShotHelper screenshot = new screenShotHelper();

    @RegisterExtension
    googleSheetHelper googleSheet = new googleSheetHelper();
}
