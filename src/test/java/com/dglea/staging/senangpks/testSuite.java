package com.dglea.staging.senangpks;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({
        landingPageTest.class,
        vehiclePortalTest.class,
        STM_addOnTest.class
})
public class testSuite {
    // No need to add anything inside the class body
}
