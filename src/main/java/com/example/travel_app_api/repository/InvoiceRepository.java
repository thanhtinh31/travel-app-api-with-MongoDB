package com.example.travel_app_api.repository;

import com.example.travel_app_api.model.Invoice;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface InvoiceRepository extends MongoRepository<Invoice,String> {
    @Query("{ 'idSchedule' : ?0 }")
    List<Invoice> getListInvoiceByIdSchedule(String idSchedule);
    @Query("{ 'idAccount' : ?0 }")
    List<Invoice> getListInvoiceByIdAccount(String idAccount);
    @Aggregation(pipeline = {"{'$match':{'status': ?0}}","{'$sort':{'dateInvoice':-1}}"})
    List<Invoice> getListInvoiceByStatus(int status);
}
