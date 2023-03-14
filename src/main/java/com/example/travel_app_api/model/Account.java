package com.example.travel_app_api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "account")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    @Id
    private String id;

    private String nameAccount;

    private String password;

    private String idFacebook;

    private String email;

    private String image;

    private String phoneNumber;

    private String address;

    private boolean status;

    private int typeAccount;




}
