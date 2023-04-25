package com.example.travel_app_api.response;

import com.example.travel_app_api.model.Tour;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TourSchedule {
    private Tour tour;
    private Date dayStart;
    private String addressStart;
    private String idSchedule;
}
