package com.example.travel_app_api.repository;

import com.example.travel_app_api.model.Invoice;
import com.example.travel_app_api.model.Schedule;
import com.example.travel_app_api.response.CountInvoice;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Repository
public interface InvoiceRepository extends MongoRepository<Invoice,String> {
    @Query("{ 'idSchedule' : ?0 }")
    List<Invoice> getListInvoiceByIdSchedule(String idSchedule);
    @Query("{ 'idAccount' : ?0 }")
    List<Invoice> getListInvoiceByIdAccount(String idAccount);
    @Aggregation(pipeline = {"{'$match':{'status': ?0}}","{'$sort':{'dateInvoice':-1}}"})
    List<Invoice> getListInvoiceByStatus(int status);
    @Aggregation(pipeline = {"{'$match':{'idAccount': ?0}}","{'$match':{'status': ?1}}","{'$sort':{'dateInvoice':-1}}"})
    List<Invoice> getListInvoiceByIdAccountStatus(String id,int status);
    @Aggregation(pipeline = {"{'$match':{'dateInvoice': {$gte:?0}}}","{'$match':{'dateInvoice': {$lte:?1}}}"})
    List<Invoice> getListInvoiceByYear(LocalDate from, LocalDate to);
    @Aggregation(pipeline = {"{'$project':{ 'idSchedule' : 1 ,'id': 1,'result':1 }}","{'$group':{'_id' : '$idSchedule','count': { $sum: 1 }}}"})
    List<CountInvoice> countInvoiceByIdSchedule();

}
