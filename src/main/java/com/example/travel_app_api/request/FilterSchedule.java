package com.example.travel_app_api.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FilterSchedule {
    private String addressStart;
    private String from;
    private String to;
}
