package com.example.travel_app_api.response;

import com.example.travel_app_api.model.Account;
import com.example.travel_app_api.model.Rating;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RatingResponse {
    private Rating rating;
    private Account account;
}
