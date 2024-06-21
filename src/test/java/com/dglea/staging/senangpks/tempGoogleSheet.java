package com.dglea.staging.senangpks;

import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
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

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class tempGoogleSheet {
    private static Sheets sheetsServices;
    static String currentDir = System.getProperty("user.dir");
    private static final String APPLICATION_NAME = "Automated Test Runner";
    static String spreadsheetId = "1kFWxC3cuhrUihSdf4xbRM3nOgBFAWhqMltcEKwwHEGY";
    //private static final String CREDENTIALS_FILE_PATH = currentDir + "\\src\\test\\resources\\credentials.json";

    private static Credential authorize() throws IOException, GeneralSecurityException{
        InputStream in = tempGoogleSheet.class.getResourceAsStream("/credentials.json");
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(
                GsonFactory.getDefaultInstance(),new InputStreamReader(in)
        );
        List<String> scopes = Arrays.asList(SheetsScopes.SPREADSHEETS);
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),GsonFactory.getDefaultInstance(),
                clientSecrets,scopes)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File("tokens")))
                .setAccessType("offline")
                .build();
        Credential credential = new AuthorizationCodeInstalledApp(
                flow,new LocalServerReceiver())
                .authorize("user");
        return credential;
    }
    public static Sheets getSheetsServices() throws IOException,GeneralSecurityException{
        Credential credential = authorize();
        return new Sheets.Builder(GoogleNetHttpTransport.newTrustedTransport(),
                GsonFactory.getDefaultInstance(),credential)
                .setApplicationName(APPLICATION_NAME)
                .build();

    }
    public static void updateTestStatus(String spreadsheetId, String sheetName, String testId, String status) throws IOException, GeneralSecurityException {
        Sheets service = getSheetsServices();

        // Step 1: Find the row with the test ID
        String readRange = sheetName + "!A1:F10"; // Assuming test IDs are in column A of the specified sheet
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
                break;
            }
        }

        if (rowIndex == -1) {
            System.out.println("Test ID not found.");
            return;
        }

        // Step 2: Update the status in the corresponding row
        String updateRange = sheetName + "!B" + rowIndex; // Assuming status is in column B of the specified sheet
        ValueRange body = new ValueRange()
                .setValues(Collections.singletonList(Collections.singletonList(status)));
        UpdateValuesResponse updateResponse = service.spreadsheets().values()
                .update(spreadsheetId, updateRange, body)
                .setValueInputOption("RAW")
                .execute();

        System.out.printf("%d cells updated.\n", updateResponse.getUpdatedCells());
    }
    public static void main(String[] args) throws IOException, GeneralSecurityException{
        sheetsServices = getSheetsServices();
        String range = "Sheet1!A2:F10";
        ValueRange response = sheetsServices.spreadsheets().values()
                .get(spreadsheetId,range)
                .execute();
        List<List<Object>> values = response.getValues();
        if (values == null || values.isEmpty()){
            System.out.println("No Data Found");
        }else
            System.out.println(" Good, we are in");


    }
}
