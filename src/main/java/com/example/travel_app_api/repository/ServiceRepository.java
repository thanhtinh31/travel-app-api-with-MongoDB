package com.example.travel_app_api.repository;
import com.example.travel_app_api.model.Service;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceRepository extends MongoRepository<Service,String> {
    @Query("{ 'status' : true }")
    List<Service> getAllServiceActive();
    @Query("{ 'id' : ?0 }")
    Service getServiceByid(String id);
}
