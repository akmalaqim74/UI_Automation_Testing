package com.dglea.staging.senangpks;
import io.qameta.allure.Owner;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@Owner("Intern Akmal")
@SelectClasses({
        landingPageTest.class,
        vehiclePortalTest.class,
        addOnTest.class,
        personalInfoTest.class,
        confirmNpayTest.class
})
public class testSuite {

}
