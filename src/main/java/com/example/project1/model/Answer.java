package com.example.project1.model;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class Answer {
    private String questionId;
    private String answer;
}