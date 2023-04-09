package com.example.travel_app_api.response;

import com.example.travel_app_api.model.Schedule;
import com.example.travel_app_api.model.Tour;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleResponse {
    private Tour tour;
    private List<Schedule> schedules;
}
