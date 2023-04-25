package com.example.travel_app_api.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CountInvoice {
    private String id;
    private int count;
    private String idTour;
    private List<Map<String,Object>> result;

}
