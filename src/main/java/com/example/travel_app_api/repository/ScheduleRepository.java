package com.example.travel_app_api.repository;

import com.example.travel_app_api.model.Schedule;
import com.example.travel_app_api.model.Tour;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

@Repository
public interface ScheduleRepository extends MongoRepository<Schedule, String> {
    @Query("{ 'idTour' : ?0 }")
    List<Schedule> getListScheduleByTourId(String idTour);



}
