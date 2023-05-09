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
    @GetMapping("/thongkebyidschedule/{id}")
    public Map<String,Object> getThongkeByIdSchedule(@PathVariable String id){
        return invoiceService.getThongKeByIdSchedule(id);
    }
    @GetMapping("/account/{id}")
    public List<Invoice> getListIncoiceByIdAccount(@PathVariable String id){
        return invoiceService.getListInvoiceByIdAccount(id);
    }
    @GetMapping("/idstatus/{id}/{status}")
    public List<Invoice> getListIncoiceByIdAccountStatus(@PathVariable String id,@PathVariable int status){
        return invoiceService.getListInvoiceByIdAccountStatus(id,status);
    }

    @GetMapping("/mytour/{id}")
    public List<Map<String,Object>> getLisChiTietByIdAccountStatus(@PathVariable String id){
        return invoiceService.getListChiTietByIdAccount(id);
    }


    @GetMapping("/{status}")
    public List<Invoice> getListIncoiceByStatus(@PathVariable String status){
        return invoiceService.getListInvoiceByStatus(status);
    }
    @PostMapping
    public Map<String, Object> createInvoice(@RequestBody Invoice invoice){
        return invoiceService.addNewInvoice(invoice);
    }

    @PutMapping
    public Invoice updateInvoice(@RequestBody Invoice invoice){
        return invoiceService.updateInvoice(invoice);
    }
    @PutMapping("/updatestatus/{id}/{status}")
    public Invoice updateStatus(@PathVariable String id,@PathVariable int status){
        return invoiceService.updateStatus(id,status);
    }
    @PutMapping("/xacnhan/{id}")
    public String xacnhan(@PathVariable String id){
        return invoiceService.xacNhanHoaDon(id);
    }
    @PutMapping("/xacnhantatca/{list}")
    public Map<String,Object> xacNhanTatCa(@PathVariable List<String> list){
        return invoiceService.xacNhanTatCa(list);
    }
    @PutMapping("/huy/{id}")
    public String xacnhan(@PathVariable String id,@RequestParam String lyDo){
        return invoiceService.huyHoaDon(id,lyDo);
    }
    @PutMapping("/thanhtoan/{id}")
    public String thanhtoan(@PathVariable String id,@RequestParam String nhanVien){
        return invoiceService.thanhToan(id,nhanVien);
    }
    @PutMapping("/thanhtoanvnpay")
    public Map<String, Object> thanhtoanvnpay(@RequestBody Invoice invoice){
        return invoiceService.updateThanhToanVNPay(invoice);
    }
    @DeleteMapping("/{id}")
    public String xoahoadon(@PathVariable String id){
        return invoiceService.xoaHoaDon(id);
    }
    @PutMapping("/xuly/{list}")
    public Map<String,Object> xuLyHoaDon(@PathVariable List<String> list){
        return invoiceService.xuLyHoaDon(list);
    }


}
