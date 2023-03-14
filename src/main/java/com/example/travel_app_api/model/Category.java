package com.example.travel_app_api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "category")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category {
    @Id
    private String id;

    private String name;
    private String content;

    private String image;

    private boolean status;

    private List<Tour> listTour;
}
