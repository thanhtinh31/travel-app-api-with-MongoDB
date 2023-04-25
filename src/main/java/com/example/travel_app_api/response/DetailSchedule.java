package com.example.travel_app_api.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetailSchedule {
    private String idSchedule;
    private String title;
    private String address;
    private Date dayStart;
    private String addressStart;
    private String tourGuide;
    private String phone;
    private Map<String,Object> people;
}
