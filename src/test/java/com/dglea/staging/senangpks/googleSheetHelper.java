package com.dglea.staging.senangpks;

import com.fasterxml.jackson.core.JsonFactory;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.UpdateValuesResponse;
import com.google.api.services.sheets.v4.model.ValueRange;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class googleSheetHelper extends baseTest implements TestWatcher {
    static String currentDir = System.getProperty("user.dir");
    private static final String APPLICATION_NAME = "Automated Test Runner";
    private static final GsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = currentDir + "\\tokens";
    private static final String CREDENTIALS_FILE_PATH = currentDir + "\\src\\test\\resources\\credentials.json";
    private static String testId;
    static String spreadsheetId = "1eDSzzBu3thjMfuhuqiQHIASnIxN9eX93o1avyGBFQNc";

    @Override
    public void testFailed(ExtensionContext context, Throwable cause)    {
        testId = extractTestID(context.getDisplayName());
        String sheetName = getSheetName(testId);
        try {
            updateTestStatus(spreadsheetId, sheetName, testId, "Fail");
        } catch (IOException | GeneralSecurityException e) {
            e.printStackTrace(); // Handle the exception (e.g., log it)
        }
    }

    @Override
    public void testSuccessful(ExtensionContext context)  {
        testId = extractTestID(context.getDisplayName());
        String sheetName = getSheetName(testId);
        try {
            updateTestStatus(spreadsheetId, sheetName, testId, "Pass");
        } catch (IOException | GeneralSecurityException e) {
            e.printStackTrace(); // Handle the exception (e.g., log it)
        }
    }
    @Override
    public void testDisabled(ExtensionContext context, Optional<String> reason) {
        testId = extractTestID(context.getDisplayName());
        String sheetName = getSheetName(testId);
        try {
            updateTestStatus(spreadsheetId, sheetName, testId, "Disabled");
        } catch (IOException | GeneralSecurityException e) {
            e.printStackTrace(); // Handle the exception (e.g., log it)
        }
    }

    private String getSheetName(String tempTestID){
        String uniqueID = extractPrefix(tempTestID);
        switch (uniqueID){
            case "LP":
                return "DGLEA - 1. Landing Page";
            case "GQ":
                return "DGLEA - 1.Generate Quote";
            case "AO":
                return "DGLEA - 2.ADD ON";
            case "FID":
                return "DGLEA - 3. Fill In Details";
            case "CP":
                return "DGLEA- 4. Confirm & Pay";
            default:
                return "N/A";
        }
    }
    private String extractPrefix(String testId) {
        return testId.replaceAll("\\d", "");  // Remove all digits
    }
    private String extractTestID(String displayName) {
        if (displayName != null && displayName.contains(" ")) {
            return displayName.substring(0, displayName.indexOf(" "));
        }
        return displayName;  // Fallback to full display name if no space is found
    }

    public static void main(String[] args) {
        try {

            testId = "LP001"; // Replace with actual test ID
            String status = "Fail"; // Replace with actual status
            String sheetName = "DGLEA - 1. Landing Page";

            updateTestStatus(spreadsheetId,sheetName, testId, status);
            System.out.println("Update successful.");
        } catch (IOException | GeneralSecurityException e) {
            e.printStackTrace();
        }
    }


    private static Credential getCredentials() throws IOException, GeneralSecurityException {
        // Load client secrets.
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(new FileInputStream(CREDENTIALS_FILE_PATH)));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                GoogleNetHttpTransport.newTrustedTransport(), JSON_FACTORY, clientSecrets, Collections.singleton(SheetsScopes.SPREADSHEETS))
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }
    private static Sheets getSheetsService() throws IOException, GeneralSecurityException {
        return new Sheets.Builder(GoogleNetHttpTransport.newTrustedTransport(), JSON_FACTORY, getCredentials())
                .setApplicationName(APPLICATION_NAME)
                .build();
    }
    public static void updateTestStatus(String spreadsheetId, String sheetName, String testId, String status) throws IOException, GeneralSecurityException {

        Sheets service = getSheetsService();

        // Step 1: Find the row with the test ID
        String readRange = sheetName + "!A1:Z"; // Assuming test IDs are in column A of the specified sheet
        ValueRange response = service.spreadsheets().values().get(spreadsheetId, readRange).execute();
        List<List<Object>> values = response.getValues();

        if (values == null || values.isEmpty()) {
            System.out.println("No data found.");
            return;
        }

        int rowIndex = -1;

        for (int i = 0; i < values.size(); i++) {
            List<Object> row = values.get(i);
            if (row.size() > 0 && row.get(0).toString().equals(testId)) {
                rowIndex = i + 1; // 1-based index
                System.out.println("Found Test ID");
                System.out.println("Test Id Row Index: "+rowIndex);
                break;
            }
        }
        //Iterates in first row to find out the index of status
        List<Object> firstRow = values.get(0);
        int columnIndex = -1;
        for (int j = 0; j < firstRow.size(); j++) {
            if (firstRow.get(j).toString().equals("Result")) {
                columnIndex = j; // 1-based index
                System.out.println("Found 'Result' at column index");
                System.out.println(columnIndex);
            }
        }
        String statusIndex = getCharForNumber(columnIndex);
        System.out.println(statusIndex);



        if (rowIndex == -1) {
            System.out.println("Test ID not found.");
            return;
        }

        // Step 2: Update the status in the corresponding row
        String updateRange = sheetName + "!"+statusIndex + rowIndex; // Assuming status is in column B of the specified sheet
        ValueRange body = new ValueRange()
                .setValues(Collections.singletonList(Collections.singletonList(status)));
        UpdateValuesResponse updateResponse = service.spreadsheets().values()
                .update(spreadsheetId, updateRange, body)
                .setValueInputOption("RAW")
                .execute();

        System.out.printf("%d cells updated.\n", updateResponse.getUpdatedCells());
    }
    private static String getCharForNumber(int i) {
        char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
        if (i > 25) {
            return null;
        }
        return Character.toString(alphabet[i]);
    }
}
