package com.example.project1.repository;

import com.example.project1.model.Form;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FormRepository extends MongoRepository<Form,String> {
    String findFormNameById(String formId);
}
