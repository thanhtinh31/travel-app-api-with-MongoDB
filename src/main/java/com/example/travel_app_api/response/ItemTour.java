package com.example.travel_app_api.response;

import com.example.travel_app_api.model.Image;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemTour {
    private String id;
    private String title;
    private String subTitle;
    private List<Image> image;

    private String address;
    private String inteval;
    private String vehicle;
    private Double price;
    private Double sale;
    private Boolean status;
    private double star;

}
