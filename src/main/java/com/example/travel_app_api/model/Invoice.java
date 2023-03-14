package com.example.travel_app_api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "invoice")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Invoice {

    @Id
    private String id;
    private Date dateInvoice;
    private int status;
    private String fullName;
    private String email;
    private String phone;
    private String address;
    private String note;
    private String payments;
    private String bankTranNo;
    private String payDay;
    private String Bank;
    private int people;
    private double amount;

    private Schedule schedule;

    private Account accountInvoice;
}
