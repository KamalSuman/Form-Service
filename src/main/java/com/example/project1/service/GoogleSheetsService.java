package com.example.project1.service;

import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.Permission;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.AppendValuesResponse;
import com.google.api.services.sheets.v4.model.Spreadsheet;
import com.google.api.services.sheets.v4.model.ValueRange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class GoogleSheetsService {
    private final Sheets sheets;
    private static final String APPLICATION_NAME = "Form Service";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    public static Drive getDriveService() throws IOException, GeneralSecurityException {
        return new Drive.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                JSON_FACTORY,
                getCredentials()
        ).setApplicationName(APPLICATION_NAME).build();
    }

    private static GoogleCredential getCredentials() throws IOException, GeneralSecurityException {
        InputStream in = new FileInputStream("C:\\Users\\91766\\IdeaProjects\\project1\\src\\main\\resources\\credentials\\google auth service.json");

        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        List<String> scopes = Collections.singletonList(
                SheetsScopes.SPREADSHEETS  // Google Sheets scope only
        );

        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                JSON_FACTORY,
                clientSecrets,
                scopes
        ).setDataStoreFactory(new FileDataStoreFactory(new File("tokens"))).build();

        return (GoogleCredential) new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
    }


    @Autowired
    public GoogleSheetsService(Sheets sheets) {
        this.sheets = sheets;
    }
    public String createSpreadsheetForFormOwner(String formOwnerId) throws IOException, GeneralSecurityException {
        Spreadsheet spreadsheet = sheets.spreadsheets().create(new Spreadsheet()).setFields("spreadsheetId").execute();
        String spreadsheetId = spreadsheet.getSpreadsheetId();
        Permission userPermission = new Permission()
                .setType("anyone")
                .setRole("writer");
        Drive driveService = getDriveService();
        driveService.permissions().create(spreadsheetId, userPermission).execute();
        String spreadsheetUrl = "https://docs.google.com/spreadsheets/d/" + spreadsheet.getSpreadsheetId();
        return spreadsheetUrl;
    }
    public void addResponseToSpreadsheet(String spreadsheetId, String formName, List<Object> responseData) throws IOException, GeneralSecurityException {

        ValueRange body  = new ValueRange().setValues(Arrays.asList(responseData));
        AppendValuesResponse appendValuesResponse = sheets.spreadsheets().values().append(spreadsheetId,formName, body)
                .setValueInputOption("RAW").execute();
    }
}
