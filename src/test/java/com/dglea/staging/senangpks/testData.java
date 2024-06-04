package com.dglea.staging.senangpks;
import java.util.ArrayList;
import java.util.List;

public class testData {
    private String vehNo;
    private String NRIC;


    public testData(String vehNo, String NRIC) {
        this.vehNo = vehNo;
        this.NRIC = NRIC;

    }

    public String getVehNo() {
        return vehNo;
    }

    public String getNRIC() {
        return NRIC;
    }



    public static List<testData> getTestData() {
        List<testData> testDataList = new ArrayList<>();

        // Add test data to the list
        testDataList.add(new testData("vehicle1", "nric1"));
        testDataList.add(new testData("vehicle2", "nric2"));
        // Add more test data as needed
        return testDataList;
    }
}
