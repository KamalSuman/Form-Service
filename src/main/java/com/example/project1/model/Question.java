package com.example.project1.model;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
@Data
public class Question {
    private String questionId;
    private String questionText;
    @Autowired
    private Options options;
}
