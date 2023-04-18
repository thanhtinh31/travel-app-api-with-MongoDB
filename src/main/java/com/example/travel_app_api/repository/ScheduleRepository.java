package com.example.travel_app_api.repository;

import com.example.travel_app_api.model.Schedule;
import com.example.travel_app_api.model.Tour;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.text.DateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
public interface ScheduleRepository extends MongoRepository<Schedule, String> {
    @Query("{ 'idTour' : ?0 }")
    List<Schedule> getListScheduleByTourId(String idTour);
    @Query("{$and: [{ 'idTour' : ?0 }, {'dayStart': {$gt:?1}}]}") //{$or: [{ 'idTour' : ?0 }, {'dayStart': { }}]}
    List<Schedule> getListScheduleByTourIdActive(String idTour, Date day);



   // @Query(" {'dayStart': {$gt:?0}}" ) //{$or: [{ 'idTour' : ?0 }, {'dayStart': { }}]}
    @Aggregation(pipeline = {"{'$match':{'dayStart': {$gt:?0}}}","{'$sort':{'dayStart':1}}","{ $limit : 3 }"})
    List<Schedule> getListScheduleActive( Date day);
    @Query("{$and: [{ 'idTour' : ?0 }, {'dayStart': {$lt:?1}}]}") //{$or: [{ 'idTour' : ?0 }, {'dayStart': { }}]}
    List<Schedule> getListScheduleByTourIdPass(String idTour, Date day);




}
