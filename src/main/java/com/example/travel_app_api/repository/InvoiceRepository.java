package com.example.travel_app_api.repository;

import com.example.travel_app_api.model.Invoice;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceRepository extends MongoRepository<Invoice,String> {
}
