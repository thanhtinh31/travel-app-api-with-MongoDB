package com.example.travel_app_api.controller;

import com.example.travel_app_api.model.Invoice;
import com.example.travel_app_api.request.MailRequest;
import com.example.travel_app_api.service.EmailSenderService;
import com.example.travel_app_api.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/mail")
@CrossOrigin
public class EmailController {
    @Autowired
    private EmailSenderService emailSenderService;
    @PostMapping
    public String send(@RequestBody MailRequest mailRequest){
        emailSenderService.sendMail(mailRequest.getToEmail(),mailRequest.getSubject(),mailRequest.getBody());
        return "sent";
    }
}
