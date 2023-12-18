package com.example.project1.controller;

import com.example.project1.model.Form;
import com.example.project1.model.Response;
import com.example.project1.service.FormService;
import com.example.project1.service.GoogleSheetsService;
import com.example.project1.service.ResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;

@RestController
@RequestMapping
public class FormController {

    private final FormService formService;
    private final GoogleSheetsService googleSheetsService;
    private final ResponseService responseService;
    @Autowired
    public FormController(FormService formService, GoogleSheetsService googleSheetsService, ResponseService responseService) {
        this.formService = formService;
        this.googleSheetsService = googleSheetsService;
        this.responseService = responseService;
    }
    // Endpoint to create a new form
    @GetMapping("/home")
    public String home(){
        return "HELLO WORLD";
    }
    @PostMapping("/create")
    public String createForm(@RequestBody Form form) {
        Form savedForm = formService.saveForm(form);
        String link = null;
        try {
            link = googleSheetsService.createSpreadsheetForFormOwner(savedForm.getId());
        } catch (IOException | GeneralSecurityException e) {
            // Handle exceptions as needed
           return "error";
        }
        return link;
    }
    // Endpoint to get a form by its ID
    @GetMapping("/{formId}")
    public ResponseEntity<Form> getForm(@PathVariable String formId) {
        Form form = formService.getFormById(formId);
        if (form != null) {
            return new ResponseEntity<>(form, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    // Endpoint to submit a response to a form
    @PostMapping("/{formId}/submit")
    public ResponseEntity<Response> submitResponse( @PathVariable String formId, @RequestBody Response response) {
        if (!response.getFormId().equals(formId)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Response savedResponse = responseService.saveResponse(response);
        try {
            googleSheetsService.addResponseToSpreadsheet("yourSpreadsheetId", formService.formName(formId), Arrays.asList("exampleData"));
        } catch (IOException | GeneralSecurityException e) {
            // Handle exceptions as needed
        }
        return new ResponseEntity<>(savedResponse, HttpStatus.CREATED);
    }
    // Endpoint to get a response by its ID
    @GetMapping("/responses/{responseId}")
    public ResponseEntity<Response> getResponse(@PathVariable String responseId) {
        Response response = responseService.getResponseById(responseId);
        if (response != null) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
