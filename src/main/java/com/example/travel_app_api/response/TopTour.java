package com.example.travel_app_api.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TopTour {
    private String idTour;
    private int count;
    private String title;
}
