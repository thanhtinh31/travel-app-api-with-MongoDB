package com.example.travel_app_api.repository;

import com.example.travel_app_api.model.Posts;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostsRepository extends MongoRepository<Posts,String> {
}
