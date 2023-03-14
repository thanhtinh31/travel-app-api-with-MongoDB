package com.example.travel_app_api.repository;

import com.example.travel_app_api.model.Rating;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingRepository extends MongoRepository<Rating,String> {
}
