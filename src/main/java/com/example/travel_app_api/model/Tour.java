package com.example.travel_app_api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "tour")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Tour {
    @Id
    private String id;
    private String title;
    private String subTitle;
    private String image;
    private String describe;
    private String interesting;
    private String address;
    private String inteval;
    private String vehicle;
    private Double price;
    private Double sale;
    private int status;
    @DBRef
    private Account account;
    private String idCategory;
//    private List<Rating> listRating;
//
//    private List<Schedule> listSchedule;

}
