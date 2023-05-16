package com.example.travel_app_api.repository;

import com.example.travel_app_api.model.Rating;
import com.example.travel_app_api.model.Request;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequestRepository extends MongoRepository<Request,String> {
    @Query("{ 'accept' : ?0 }")
    List<Request> getRequestByAccept(int accept);
}
