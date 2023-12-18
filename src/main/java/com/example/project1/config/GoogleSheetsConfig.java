package com.example.project1.config;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.http.HttpCredentialsAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

@Configuration
public class GoogleSheetsConfig {

    @Bean
    public Sheets sheetsService() throws IOException, GeneralSecurityException {
        JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
        HttpCredentialsAdapter credentialsAdapter = new HttpCredentialsAdapter(getCredentials());

        return new Sheets.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                jsonFactory,
                credentialsAdapter)
                .setApplicationName("Form Service")
                .build();
    }

    private Credentials getCredentials() throws IOException {
        FileInputStream credentialsStream = new FileInputStream("C:\\Users\\91766\\IdeaProjects\\project1\\src\\main\\resources\\credentials\\form-service-407918-dba123b6cefc.json");

        return GoogleCredentials.fromStream(credentialsStream)
                .createScoped(Collections.singletonList(SheetsScopes.SPREADSHEETS));
    }
}
