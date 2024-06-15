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
public class landingPageTest extends baseTest {



    @Test
    void temp (){
        adminPortalLogin();
    }
    /*@Test
    @Order(1)
    @Description("Are we on Landing Page (Fill in Form)?")
    public void onForm(){
        Allure.step("Step 1: Insert Staff Id", () -> {
            insertStaffId();
            assertTrue(driver.findElement(By.cssSelector(".align-center > button:nth-child(1)")).isDisplayed());
        });
        }
    @Test
    @DisplayName("Verify NRIC field cant be empty")
    @Description("Validation If NRIC field is empty")
    public void NricRequiredTest(){
        assertTrue(driver.findElement(By.cssSelector("#mat-error-0 > strong:nth-child(1)")).isDisplayed());
    }

    @Test
    @DisplayName("Verify vehicle number field cant be empty")
    @Description("Validation if vehicle Number field is empty")

    public void vehNoRequiredTest(){
        //assertTrue(driver.findElement(By.cssSelector("#mat-error-1")).isDisplayed());
        assertTrue(driver.findElement(By.cssSelector("#mat-error-1")).isDisplayed());
    }*/
    /*@Test
    @Disabled
    @Description("Validation if name field is empty")
    void NameRequiredTest(){
        assertTrue(driver.findElement(By.cssSelector("#mat-error-1")).isDisplayed());
    }
    @Test
    @Disabled
    @Description("Validation if postcode empty")
    void postcodeRequiredTest(){
        assertTrue(driver.findElement(By.cssSelector("#mat-error-3")).isDisplayed());
    }
    @Test
    @Disabled
    @Description("Validation if email address is empty")
    void emailRequiredTest(){
        assertTrue(driver.findElement(By.cssSelector("#mat-error-4")).isDisplayed());
    }*/

    @RegisterExtension
    screenShotHelper screenshot = new screenShotHelper();
}
