package com.example.project1.repository;

import com.example.project1.model.Response;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResponseRepository extends MongoRepository<Response,String> {
}
