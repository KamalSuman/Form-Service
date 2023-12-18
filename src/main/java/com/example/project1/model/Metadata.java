package com.example.project1.model;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class Metadata {
    private String createdBy;
    private String createdAt;
}
