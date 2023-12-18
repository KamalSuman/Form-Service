package com.example.project1.model;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
@Data
@Document(collection = "forms")
public class Form {
    @Id
    private String id;
    private String formName;
    private String formSubject;
    private String formDescription;
    private List<Question> questions;
    private Metadata metadata;
    @Autowired
    public Form(List<Question> questions, Metadata metadata) {
        this.questions = questions;
        this.metadata = metadata;
    }

}
