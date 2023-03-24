package com.example.travel_app_api.service;

import com.example.travel_app_api.model.Invoice;
import com.example.travel_app_api.model.Schedule;
import com.example.travel_app_api.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class InvoiceService {
    @Autowired
    private InvoiceRepository invoiceRepository;
    @Autowired
    private ScheduleService scheduleService;
    @Autowired
    private AccountService accountService;
    public List<Invoice> getListInvoice(){
        return invoiceRepository.findAll();
    }
    public List<Invoice> getListInvoiceByIdSchedule(String idSchedule){
        return invoiceRepository.getListInvoiceByIdSchedule(idSchedule);
    }
    public List<Invoice> getListInvoiceByIdAccount(String idAccount){
        return invoiceRepository.getListInvoiceByIdAccount(idAccount);
    }
    public Invoice getInvoiceById(String id){
        return invoiceRepository.findById(id).get();
    }
    public Map<String,Object> getDetailInvoice(String id){
        Map<String,Object> m=new HashMap<>();
        try {
            Invoice invoice=invoiceRepository.findById(id).get();
            m.put("invoice",invoice);
            try {
                m.put("schedule",scheduleService.getSchedule(invoice.getIdSchedule()));
            }catch (Exception a){
                m.put("schedule","0");
            }
            try {
                m.put("account",accountService.getAccountById(invoice.getIdAccount()));
            }catch (Exception b){
                m.put("account","0");
            }
            m.put("status","1");
        }catch (Exception e)
        {
            m.put("message","khong tim thay");
            m.put("status","0");
        }
        return m;
    }
    public Map<String,Object> addNewInvoice(Invoice invoice){
        Map<String,Object> m=new HashMap<>();
        if(invoice.getIdAccount()==null||invoice.getIdSchedule()==null){
            m.put("message","Khong thanh cong");
            m.put("status","0");
        }else{
            invoiceRepository.save(invoice);
            m.put("message","Booking thanh cong");
            m.put("status","1");
            m.put("invoice",invoice);
        }
        return m;
    }
    public Invoice updateInvoice(Invoice invoice){

        Invoice invoice1=invoiceRepository.findById(invoice.getId()).get();
        invoice1.setStatus(invoice.getStatus());
        invoice1.setAddress(invoice.getAddress());
        invoice1.setEmail(invoice.getAddress());
        invoice1.setFullName(invoice.getFullName());
        invoice1.setIdPayment(invoice.getIdPayment());
        invoice1.setNote(invoice.getNote());
        invoice1.setPeople(invoice.getPeople());
        invoice1.setPayDay(invoice.getPayDay());
        invoice1.setPayments(invoice.getPayments());
        invoice1.setPhone(invoice.getPhone());
        invoiceRepository.save(invoice1);
        return invoice1;
    }


}
