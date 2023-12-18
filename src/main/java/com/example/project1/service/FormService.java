package com.example.project1.service;
import com.example.project1.model.Form;
import com.example.project1.repository.FormRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Data
@Service
public class FormService {
    @Autowired
    private final FormRepository formRepository;
    public Form saveForm(Form form) {
        // Add additional validation or logic as needed

        return formRepository.save(form);
    }
    public String formName(String formId){
        return formRepository.findFormNameById(formId);
    }

    public Form getFormById(String formId) {
        return formRepository.findById(formId).orElse(null);
    }
}
