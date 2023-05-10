package com.example.travel_app_api.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountResponse {
    private String id;

    private String nameAccount;

    private String idFacebook;

    private String email;

    private String image;

    private String phoneNumber;

    private String address;

    private boolean status;

    private int typeAccount;

    private LocalDateTime timeLogin;
}
