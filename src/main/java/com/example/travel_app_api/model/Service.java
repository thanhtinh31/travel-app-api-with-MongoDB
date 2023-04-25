package com.example.travel_app_api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "service")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Service {
    @Id
    private String id;
    private String name;
    private String describle;
    private String icon;
    private Double price;
    private Boolean status;
}
