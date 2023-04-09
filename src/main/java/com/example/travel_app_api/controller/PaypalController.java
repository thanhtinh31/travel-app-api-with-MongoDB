package com.example.travel_app_api.controller;

import com.example.travel_app_api.model.Invoice;
import com.example.travel_app_api.service.InvoiceService;
import com.example.travel_app_api.service.PaypalService;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/pay")
public class PaypalController {
    @Autowired
    PaypalService service;
    @Autowired
    InvoiceService invoiceService;
    public static final String SUCCESS_URL="paypal/success";
    public static final String CANCEL_URL="paypal/cancel";
    @PostMapping("/paypal")
    public String payment(@RequestBody Invoice invoice){
        try {
            Payment payment= service.createPayment(invoice.getAmount(),"USD",
                    "paypal","sale",invoice.getNote(),
                    "http://localhost:8080/pay/"+CANCEL_URL
                    ,"http://localhost:8080/pay/"+SUCCESS_URL+"?id="+invoice.getId());
            for(Links link:payment.getLinks()) {
                if(link.getRel().equals("approval_url")) {
                    return link.getHref();
                }
            }
        }catch (PayPalRESTException e){
            e.printStackTrace();
        }
        return "redirect:/";
    }
    @GetMapping(value = CANCEL_URL)
    public String cancelPay(){
        return "https://travel-app-react-ivory.vercel.app/";
    }
    @GetMapping(value = SUCCESS_URL)
    public String successPay(@RequestParam("paymentId") String paymentID,@RequestParam("PayerID") String payerID,@RequestParam("id")String id){
        try {
            Payment payment = service.executePayment(paymentID, payerID);
            return service.successPayPal(payment,id);
        } catch (PayPalRESTException e) {
            System.out.println(e.getMessage());
        }
        return "That Bai";
    }
}
