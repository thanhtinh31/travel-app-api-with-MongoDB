package com.example.travel_app_api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Map;

@Document(collection = "tour")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Tour {
    @Id
    private String id;
    private String title;
    private String subTitle;
    private List<Image> image;
    private String describe;
    private String interesting;
    private String address;
    private String inteval;
    private String vehicle;
    private Double price;
    private Double sale;
    private List<Service> services;
    private Boolean status;
   // @DBRef
    private String idAccount;
    private List<Map<String,Object>> hanhtrinh;
    private List<String> idCategory;
//    private List<Rating> listRating;
//    private List<Schedule> listSchedule;

}
