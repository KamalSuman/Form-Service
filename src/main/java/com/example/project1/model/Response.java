package com.example.project1.model;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
@Document(collection = "responses")
@Data
public class Response {
    @Id
    private String id;
    private String formId;
    private String userId;
    @Autowired
    private List<Answer> answers;
    @Autowired
    private ResponseMetadata responseMetadata;
}
