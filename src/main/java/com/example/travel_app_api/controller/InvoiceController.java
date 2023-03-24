package com.example.travel_app_api.controller;

import com.example.travel_app_api.model.Invoice;
import com.example.travel_app_api.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/invoice")
@CrossOrigin
public class InvoiceController {
    @Autowired
    private InvoiceService invoiceService;
    @GetMapping("")
    public List<Invoice> getListIncoice(){
        return invoiceService.getListInvoice();
    }
    @GetMapping("/detail/{id}")
    public Map<String,Object> getDetailInvoice(@PathVariable String id){
        return invoiceService.getDetailInvoice(id);
    }
    @GetMapping("/schedule/{id}")
    public List<Invoice> getListIncoiceByIdSchedule(@PathVariable String id){
        return invoiceService.getListInvoiceByIdSchedule(id);
    }
    @GetMapping("/account/{id}")
    public List<Invoice> getListIncoiceByIdAccount(@PathVariable String id){
        return invoiceService.getListInvoiceByIdAccount(id);
    }
    @PostMapping
    public Map<String, Object> createInvoice(@RequestBody Invoice invoice){
        return invoiceService.addNewInvoice(invoice);
    }
    @PutMapping
    public Invoice updateInvoice(@RequestBody Invoice invoice){
        return invoiceService.updateInvoice(invoice);
    }

}
