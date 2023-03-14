package com.example.travel_app_api.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;
@Document(collection = "schedule")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Schedule {
    @Id
    private String id;

    private String tourGuide;

    private String phone;

    private Date dayStart;


    private Tour tour_id;

    //private List<Invoice> listInvoice;
}
