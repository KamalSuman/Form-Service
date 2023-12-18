package com.example.project1.model;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Data
public class Options {
    private List<String> choices;
    private Integer min;
    private Integer max;
    private Integer step;
}
