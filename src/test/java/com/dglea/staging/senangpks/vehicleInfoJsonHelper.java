package com.dglea.staging.senangpks;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class vehicleInfoJsonHelper {
    static HashMap<String, String> vehicleInfoMap = new HashMap<>();
    static ObjectMapper objectMapper = new ObjectMapper();
    static String currentDir = System.getProperty("user.dir");
    static String fileSeparator = System.getProperty("file.separator");
    static String filePath = currentDir + fileSeparator +"src"+ fileSeparator + "test"+ fileSeparator +"resources" + fileSeparator + "vehicleData.json";

    public static void main(String[] args) throws IOException {

        vehicleInfoJsonHelper("takaful_malaysia");
    }

    public  static HashMap<String, String> vehicleInfoJsonHelper(String provider) throws IOException {
        switch (provider){
            case "takaful_malaysia":
                vehicleInfoMap = jsonReaderSTM();
                break;
        }

        return vehicleInfoMap;
    }
    public static HashMap<String, String> jsonReaderSTM() throws IOException{
        HashMap<String, String> tempMapping = new HashMap<>();
        try{
            String tempString;
            JsonNode jsonNode = objectMapper.readTree(new File(filePath));
            //chassis No
            tempString = jsonNode.get("chassisno").asText();
            System.out.println("chassisNo: "+tempString);
            tempMapping.put("Chassis Number",tempString);
            //Vehicle Reg No
            tempString = jsonNode.get("vehRegNo").asText();
            System.out.println("vehicle registration no: "+tempString);
            tempMapping.put("Vehicle Registration No.",tempString);
            //Car Model
            JsonNode arrResExtraParam = jsonNode.get("arrResExtraParam");
            String nvicmakeValue = " ",carFamily = " ";
            for (JsonNode item : arrResExtraParam.get("item")) {

                if (item.get("indicator").asText().equals("nvicmake")) {
                     nvicmakeValue = item.get("value").asText();
                    System.out.println("nvicmake value: " + nvicmakeValue);
                }
                if (item.get("indicator").asText().equals("family")) {
                    carFamily = item.get("value").asText();
                    System.out.println("nvicmake value: " + carFamily);
                }
                tempString = nvicmakeValue + " " + carFamily;

            }
            System.out.println("Make and Model: "+ tempString);
            tempMapping.put("Make and Model",tempString);
            //CC
            tempString = jsonNode.get("vehcapacity").asText();
            tempMapping.put("Engine CC",tempString);
            System.out.println("Engine CC"+tempString);
            //Engine Number
            tempString = jsonNode.get("engineno").asText();
            tempMapping.put("Engine Number",tempString);
            System.out.println("engine no: "+tempString);
            //Manufacturing Year
            tempString = jsonNode.get("makeyear").asText();
            tempMapping.put("Manufacturing Year",tempString);
            System.out.println("Manufacturing Year: "+tempString);
            //comp code, comprehensive
            tempString = jsonNode.get("compcode").asText();
            if (tempString.equals("92")){
                tempString = "Comprehensive";
            }else{
                tempString = "Third Party Fire Theft";
            }
            tempMapping.put("Type of Cover",tempString);
            System.out.println("Cover Type "+tempString);
            tempString = jsonNode.get("ncdperc").asText();
            tempMapping.put("NCD",tempString);
            System.out.println("NCC: "+tempString);
            /*
            Note:
            Soon to add
            marketValue min and max
            aggreedValue Min and Max
             */

        }
        catch (IOException e) {
            e.printStackTrace();
        }


        return tempMapping;
    }

}
