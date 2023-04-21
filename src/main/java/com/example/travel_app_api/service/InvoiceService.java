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
    public List<Invoice> getListInvoiceByIdAccountStatus(String id,int status){
        return invoiceRepository.getListInvoiceByIdAccountStatus(id,status);
    }
    public Map<String,Object> getThongKeByIdSchedule(String idSchedule){
        Map<String,Object> m=new HashMap<>();
        List<Invoice> list=invoiceRepository.getListInvoiceByIdSchedule(idSchedule);
        int xn=0, d=0,c=0,h=0;
        for(int i=0;i<list.size();i++){
            if(list.get(i).getStatus()==0) xn++;
            if(list.get(i).getStatus()==1) c++;
            if(list.get(i).getStatus()==2) d++;
            if(list.get(i).getStatus()==3) h++;
        }
        m.put("choxacnhan",xn);
        m.put("dathanhtoan",d);
        m.put("chuathanhtoan",c);
        m.put("dahuy",h);

        return m;
    }
    public Invoice getInvoiceById(String id){
        return invoiceRepository.findById(id).get();
    }
    public Map<String,Object> getDetailInvoice(String id){
        Map<String,Object> m=new HashMap<>();
//        try {
//            Invoice invoice=invoiceRepository.findById(id).get();
//            m.put("invoice",invoice);
//            try {
//                m.put("schedule",scheduleService.getSchedule(invoice.getIdSchedule()));
//            }catch (Exception a){
//                m.put("schedule","0");
//            }
//            try {
//                m.put("account",accountService.getAccountById(invoice.getIdAccount()));
//            }catch (Exception b){
//                m.put("account","0");
//            }
//            m.put("status","1");
//        }catch (Exception e)
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
        invoice1.setEmail(invoice.getEmail());
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
    public Invoice updateStatus(String id,int status){
        Invoice invoice1=invoiceRepository.findById(id).get();
        invoice1.setStatus(status);
        invoiceRepository.save(invoice1);
        return invoice1;
    }
    public List<Invoice> getListInvoiceByStatus(String status){
        int stt=Integer.parseInt(status);
        return invoiceRepository.getListInvoiceByStatus(stt);
    }


}
