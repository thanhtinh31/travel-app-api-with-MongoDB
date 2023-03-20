package com.example.travel_app_api.controller;

import com.example.travel_app_api.model.Invoice;
import com.example.travel_app_api.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/invoice")
@CrossOrigin
public class InvoiceController {
    @Autowired
    private InvoiceService invoiceService;
    @GetMapping("/schedule/{id}")
    public List<Invoice> getListIncoiceByIdSchedule(@PathVariable String id){
        return invoiceService.getListInvoiceByIdSchedule(id);
    }
    @GetMapping("/account/{id}")
    public List<Invoice> getListIncoiceByIdAccount(@PathVariable String id){
        return invoiceService.getListInvoiceByIdAccount(id);
    }
    @PostMapping
    public Invoice createInvoice(@RequestBody Invoice invoice){
        return invoiceService.addNewInvoice(invoice);
    }

}
