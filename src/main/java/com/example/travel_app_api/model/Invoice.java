package com.example.travel_app_api.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.Date;

@Document(collection = "invoice")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Invoice {

    @Id
    private String id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate dateInvoice=LocalDate.now();
    private int status;
    private String fullName;
    private String email;
    private String phone;
    private String address;
    private String note;
    private String payments;
    private String idPayment;
    private String payDay;
    private String Bank;
    private int people;
    private double amount;
    private String currency;
    private String idSchedule;
    private String idAccount;
    private String nhanVien;
    private boolean confirm=false;
}
