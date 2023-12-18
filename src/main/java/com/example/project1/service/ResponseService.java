package com.example.project1.service;
import com.example.project1.model.Response;
import com.example.project1.repository.ResponseRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Data
@Service
public class ResponseService {
    @Autowired
    private ResponseRepository responseRepository;
    public Response saveResponse(Response response) {
        // Add additional validation or logic as needed
        return responseRepository.save(response);
    }
    public Response getResponseById(String responseId) {
        return responseRepository.findById(responseId).orElse(null);
    }
}
