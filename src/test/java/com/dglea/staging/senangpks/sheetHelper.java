package com.dglea.staging.senangpks;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;

public class sheetHelper {

    public static void main(String[] args) throws IOException {
        ArrayList<String> dataList = supportedAddOn("takaful_malaysia");

        for(String data : dataList) {
            System.out.println(data);
        }
    }

    public static ArrayList<String> getDataFromExcel() throws IOException {
        ArrayList<String> list = new ArrayList<String>();
        System.out.println("Excel Reader");

        // Load the Excel file as a resource
        InputStream inputStream = sheetHelper.class.getClassLoader().getResourceAsStream("testDataSheet.xlsx");
        if (inputStream == null) {
            throw new IOException("File testDataSheet.xlsx not found");
        }

        XSSFWorkbook wb = new XSSFWorkbook(inputStream);

        int sheetcount = wb.getNumberOfSheets();

        for(int i=0;i<sheetcount;i++) {
            System.out.println("Going for Loop");

            if(wb.getSheetName(i).equals("testData")) {
                System.out.println("Successful find testData sheet");
                XSSFSheet sheet = wb.getSheetAt(i);
                Iterator<Row> rows = sheet.iterator();

                // Skip the header row
                if (rows.hasNext()) {
                    rows.next(); // Skip the first row
                }

                // Inside the while loop where rows are processed
                while (rows.hasNext()) {
                    Row row = rows.next();
                    System.out.println("Processing row: " + row.getRowNum()); // Debug statement

                    // Assuming NRIC is in the first column and vehicle number is in the second column
                    Cell nricCell = row.getCell(1);
                    Cell vehicleCell = row.getCell(2);
                    if (nricCell != null && vehicleCell != null) {
                        String nric = "";
                        String vehicleNo = "";

                        // Checking cell type and retrieving values accordingly
                        if (nricCell.getCellType() == CellType.STRING) {
                            nric = nricCell.getStringCellValue();
                        } else if (nricCell.getCellType() == CellType.NUMERIC) {
                            nric = NumberToTextConverter.toText(nricCell.getNumericCellValue());
                        }

                        if (vehicleCell.getCellType() == CellType.STRING) {
                            vehicleNo = vehicleCell.getStringCellValue();
                        } else if (vehicleCell.getCellType() == CellType.NUMERIC) {
                            vehicleNo = NumberToTextConverter.toText(vehicleCell.getNumericCellValue());
                        }
                        // Combining NRIC and vehicle number with a comma separator
                        String rowData = nric + "," + vehicleNo;
                        // Adding combined string to the list
                        list.add(rowData);
                    }
                }
            }
        }

        return list;
    }
    public static ArrayList<String> supportedAddOn(String providerName) throws IOException {
        ArrayList<String> list = new ArrayList<String>();
        System.out.println("Excel Reader");

        // Load the Excel file as a resource
        InputStream inputStream = sheetHelper.class.getClassLoader().getResourceAsStream("testDataSheet.xlsx");
        if (inputStream == null) {
            throw new IOException("File testDataSheet.xlsx not found");
        }

        XSSFWorkbook wb = new XSSFWorkbook(inputStream);

        int sheetcount = wb.getNumberOfSheets();

        for(int i=0;i<sheetcount;i++) {
            System.out.println("Going for Loop");
            if(wb.getSheetName(i).contains(providerName)) {
                System.out.println("Successful find testData sheet");
                XSSFSheet sheet = wb.getSheetAt(i);
                Iterator<Row> rows = sheet.iterator();

                // Skip the header row
                if (rows.hasNext()) {
                    rows.next(); // Skip the first row
                }

                // Inside the while loop where rows are processed
                while (rows.hasNext()) {
                    Row row = rows.next();
                    System.out.println("Processing row: " + row.getRowNum()); // Debug statement
                    // Assuming NRIC is in the first column and vehicle number is in the second column
                    Cell nricCell = row.getCell(1);
                    if (nricCell != null) {
                        String suportedAddOn = "";

                        // Checking cell type and retrieving values accordingly
                        if (nricCell.getCellType() == CellType.STRING) {
                            suportedAddOn = nricCell.getStringCellValue();
                        } else if (nricCell.getCellType() == CellType.NUMERIC) {
                            suportedAddOn = NumberToTextConverter.toText(nricCell.getNumericCellValue());
                        }
                        // Combining NRIC and vehicle number with a comma separator
                        list.add(suportedAddOn);
                    }
                }
            }
        }

        return list;
    }
}
