package com.example.travel_app_api.repository;

import com.example.travel_app_api.model.Category;
import com.example.travel_app_api.model.Schedule;
import com.example.travel_app_api.model.Tour;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TourRepository extends MongoRepository<Tour,String> {

    @Query("{'idCategory':{$all:[?0]}}")
    List<Tour> getListTourByCategory(String idCategory);
  //  @Query("{$or: [{'title': { $regex: /?0/ ,$options:'i' }}, {'address': { $regex: /?0/ ,$options:'i' }}]}")
    @Query("{ $text:{ $search: ?0} }")
    List<Tour> searchTour(String key);
    @Query("{'status':true}")
    List<Tour> getListTourActive();

    @Aggregation(pipeline = {"{'$match':{'status': true}}","{ $limit : 6 }"})
    List<Tour> getListTourHomeActive();
}
