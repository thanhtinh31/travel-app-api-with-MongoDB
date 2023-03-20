package com.example.travel_app_api.service;

import com.example.travel_app_api.model.Invoice;
import com.example.travel_app_api.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class InvoiceService {
    @Autowired
    private InvoiceRepository invoiceRepository;
    public List<Invoice> getListInvoiceByIdSchedule(String idSchedule){
        return invoiceRepository.getListInvoiceByIdSchedule(idSchedule);
    }
    public List<Invoice> getListInvoiceByIdAccount(String idAccount){
        return invoiceRepository.getListInvoiceByIdAccount(idAccount);
    }
    public Invoice getInvoiceById(String id){
        return invoiceRepository.findById(id).get();
    }
    public Invoice addNewInvoice(Invoice invoice){
        return invoiceRepository.save(invoice);
    }
    public Invoice updateInvoice(Invoice invoice){
        Invoice invoice1=invoiceRepository.findById(invoice.getId()).get();
        invoice1.setAddress(invoice.getAddress());
        invoice1.setEmail(invoice.getAddress());
        invoice1.setFullName(invoice.getFullName());
        invoice1.setBankTranNo(invoice.getBankTranNo());
        invoice1.setNote(invoice.getNote());
        invoice1.setPeople(invoice.getPeople());
        invoice1.setPayDay(invoice.getPayDay());
        invoice1.setPayments(invoice.getPayments());
        invoice1.setPhone(invoice.getPhone());
        invoiceRepository.save(invoice1);
        return invoice1;
    }


}
