package com.example.travel_app_api.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Document(collection = "request")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Request {
    @Id
    private String id;
    private String idAccount;
    private String phone;
    private String email;
    private String address;
    private String fullName;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy")
    @DateTimeFormat(pattern = "MM/dd/yyyy")
    private Date dayStart;
    private int people;
    private String addressStart;
    private String idTour;
    private int accept;
    private Date dayCreate;
}
