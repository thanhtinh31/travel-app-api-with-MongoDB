package com.example.travel_app_api.repository;

import com.example.travel_app_api.model.Tour;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TourRepository extends MongoRepository<Tour,String> {
}
