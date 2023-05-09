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
import java.util.Map;

@Repository
public interface ScheduleRepository extends MongoRepository<Schedule, String> {
    @Query("{ 'idTour' : ?0 }")
    List<Schedule> getListScheduleByTourId(String idTour);
    @Query("{$and: [{ 'idTour' : ?0 ,'progress':{ $in: [0,1,2] }}, {'dayStart': {$gt:?1}},{'status': true}]}") //{$or: [{ 'idTour' : ?0 }, {'dayStart': { }}]}
    List<Schedule> getListScheduleByTourIdActive(String idTour, Date day);
    @Query("{$and: [{ 'idTour' : ?0,'progress':{ $in: [0,1,2] } }, {'dayStart': {$gt:?1}}]}") //{$or: [{ 'idTour' : ?0 }, {'dayStart': { }}]}
    List<Schedule> getListScheduleByTourIdActiveSeller(String idTour, Date day);



   // @Query(" {'dayStart': {$gt:?0}}" ) //{$or: [{ 'idTour' : ?0 }, {'dayStart': { }}]}
    @Aggregation(pipeline = {"{'$match':{'dayStart': {$gt:?0}}}","{'$match':{'status': true,'progress':{ $in: [0,1,2] }}}","{'$sort':{'dayStart':1}}","{ $limit : 3 }"})
    List<Schedule> getListScheduleActive( Date day);
    @Query("{$and: [{ 'idTour' : ?0 }, {'dayStart': {$lt:?1}}]}") //{$or: [{ 'idTour' : ?0 }, {'dayStart': { }}]}
    List<Schedule> getListScheduleByTourIdPass(String idTour, Date day);

    @Aggregation(pipeline = {"{'$match':{ $text: { $search: ?0 } }}","{'$match':{'dayStart': {$gte:?1}}}","{'$match':{'dayStart': {$lte:?2}}}","{'$match':{'status': true}}"})
    List<Schedule> filter(String address, LocalDate from,LocalDate to);

    @Aggregation(pipeline = {"{'$match':{'dayStart': {$gte:?0}}}","{'$match':{'dayStart': {$lte:?1}}}","{'$match':{'status': true}}"})
    List<Schedule> filterNoAddress( LocalDate from,LocalDate to);

    @Aggregation(pipeline = {"{'$match':{'dayStart': {$gt:?0}}}","{'$match':{'status': false}}","{'$sort':{'dayStart':1}}"})
    List<Schedule> getListScheduleDaChotChuaDi( Date day);
    @Aggregation(pipeline = {"{'$match':{'status': false,'progress':2}}","{'$sort':{'dayStart':1}}"})
    List<Schedule> getListScheduleDaChotDaHoanThanh( Date day);
    @Aggregation(pipeline = {"{'$match':{'dayStart': {$gt:?0}}}","{'$match':{'status': true}}","{'$sort':{'dayStart':1}}"})
    List<Schedule> getListScheduleChuaChotChuaDi(Date day);

    @Aggregation(pipeline = {"{'$match':{'status': false,'progress':{ $in: [0] }}}","{'$sort':{'dayStart':1}}"})
    List<Schedule> getListScheduleDaChot( Date day);
    @Aggregation(pipeline = {"{'$match':{'status': true,'progress':{ $in: [0,1] }}}","{'$sort':{'dayStart':1}}"})
    List<Schedule> getListScheduleChuaChot( Date day);

    @Aggregation(pipeline = {"{'$match':{'status': false,'progress':{ $in: [1] }}}","{'$sort':{'dayStart':1}}"})
    List<Schedule> getListScheduleDangKhoiHanh( Date day);

    @Aggregation(pipeline = {"{'$match':{'progress':{ $in: [3] }}}","{'$sort':{'dayStart':1}}"})
    List<Schedule> getListScheduleDaHuy( Date day);




//{ $in: [<value1>, <value2>, ... <valueN> ] }





}
