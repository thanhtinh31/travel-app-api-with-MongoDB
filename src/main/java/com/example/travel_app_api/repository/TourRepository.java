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
import java.util.Map;

@Repository
public interface TourRepository extends MongoRepository<Tour,String> {

    @Query("{'idCategory':{$all:[?0]}}")
    List<Tour> getListTourByCategory(String idCategory);
  //  @Query("{$or: [{'title': { $regex: /?0/ ,$options:'i' }}, {'address': { $regex: /?0/ ,$options:'i' }}]}")
//    @Query("{ $text:{ $search: ?0} }")
//    List<Tour> searchTour(String key);
  @Aggregation(pipeline = {"{'$match':{ $text:{ $search: ?0} }}","{'$match':{'status': true}}"})
  List<Tour> searchTour(String key);

    @Query("{'status':true}")
    List<Tour> getListTourActive();

    @Aggregation(pipeline = {"{'$sort':{'id':-1}}"})
    List<Tour> getAllTour();

    @Aggregation(pipeline = {"{'$match':{'status': true}}","{ $limit : 6 }"})
    List<Tour> getListTourHomeActive();
    @Aggregation(pipeline = {"{'$match':{ $text: { $search: ?0 } }}","{'$match':{'idCategory':{$in:?1}}}", "{'$match':{'price':{$gt:?2}}}","{'$match':{'price':{$lt:?3}}}","{'$sort':{'price':?4}}","{'$match':{'status':true}}"})
    List<Tour> filter(String address,List<String> idCategory,double gt,double lt,int sort);
    @Aggregation(pipeline = {"{'$match':{'idCategory':{$in:?0}}}", "{'$match':{'price':{$gt:?1}}}","{'$match':{'price':{$lt:?2}}}","{'$sort':{'price':?3}}","{'$match':{'status':true}}"})
    List<Tour> filterNoAddress(List<String> idCategory,double gt,double lt,int sort);
    @Aggregation(pipeline = {"{'$match':{ $text: { $search: ?0 } }}", "{'$match':{'price':{$gt:?1}}}","{'$match':{'price':{$lt:?2}}}","{'$sort':{'price':?3}}","{'$match':{'status':true}}"})
    List<Tour> filterNoCategory(String address,double gt,double lt,int sort);
    @Aggregation(pipeline = {"{'$match':{'price':{$gt:?0}}}","{'$match':{'price':{$lt:?1}}}","{'$sort':{'price':?2}}","{'$match':{'status':true}}"})
    List<Tour> filterNoAddressNoCategory(double gt,double lt,int sort);
   @Aggregation(pipeline = {"{'$match':{'idCategory':{$in:[?0]}}","{ '$count' : 'count' }"})
    Map<String,Object> countByIdCategory(String id);

}
