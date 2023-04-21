package com.example.travel_app_api.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FilterTour {
    List<String> idCategory;
    int sort;
    double gt;
    double lt;
    String address;
}
