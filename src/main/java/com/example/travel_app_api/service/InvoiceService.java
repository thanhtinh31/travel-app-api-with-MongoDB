package com.example.travel_app_api.service;

import com.example.travel_app_api.model.Invoice;
import com.example.travel_app_api.model.Schedule;
import com.example.travel_app_api.model.Tour;
import com.example.travel_app_api.repository.InvoiceRepository;
import com.example.travel_app_api.response.CountInvoice;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class InvoiceService {
    @Autowired
    private InvoiceRepository invoiceRepository;
    @Autowired
    private ScheduleService scheduleService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private TourService tourService;
    @Autowired
    private  EmailSenderService emailSenderService;
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
        try {
            Invoice invoice=invoiceRepository.findById(id).get();
            m.put("invoice",invoice);
            try {
                m.put("schedule",scheduleService.getSchedule(invoice.getIdSchedule()));
                m.put("tour",scheduleService.getTour(invoice.getIdSchedule()));
            }catch (Exception a){
                m.put("schedule","0");
            }
//            try {
//                m.put("account",accountService.getAccountById(invoice.getIdAccount()));
//            }catch (Exception b){
//                m.put("account","0");
//            }
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
    public List<Invoice> getListInvoiceByYear(String year){
        LocalDate dayfrom = LocalDate.parse("01/01/"+year,
                DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        LocalDate dayto = LocalDate.parse("12/31/"+year,
                DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        return invoiceRepository.getListInvoiceByYear(dayfrom,dayto);
    }
    public List<CountInvoice> countInvoiceByIdSchedule(){
        return invoiceRepository.countInvoiceByIdSchedule();
    }

    public String xacNhanHoaDon(String id){
        Invoice invoice=invoiceRepository.findById(id).get();
        invoice.setStatus(1);
        invoiceRepository.save(invoice);
      //  emailSenderService.sendMailHtml(invoice.getEmail(),"Thông báo hóa đơn","Xac nhan hoa don thanh cong, Vui long thanh toan");
        return "Xác nhân thành công";
    }
    public String huyHoaDon(String id,String lyDo){
        Invoice invoice=invoiceRepository.findById(id).get();
        invoice.setStatus(3);
        invoice.setConfirm(false);
        invoiceRepository.save(invoice);
      //  emailSenderService.sendMailHtml(invoice.getEmail(),"Thông báo hóa đơn","Tour cua ban da bi huy vi "+lyDo);
        return "Hủy thành công";
    }
    public String thanhToan(String id,String nhanVien){
        LocalDate date=LocalDate.now();
        Invoice invoice=invoiceRepository.findById(id).get();
        invoice.setStatus(2);
        invoice.setPayDay(date.toString());
        invoice.setPayments("Tại cửa hàng");
        invoice.setConfirm(false);
        invoice.setNhanVien(nhanVien);
        invoiceRepository.save(invoice);

        emailSenderService.sendMailHtml(invoice.getEmail(),"Giao dịch thành công","<h2>Thanh toán hóa đơn thành công</h2><br/>" +
                "Mã hóa đơn:"+invoice.getId()+"<br/>" +
                "Người đặt:"+invoice.getFullName()+"<br/>" +
                "Địa chỉ:"+invoice.getAddress()+"<br/>" +
                "Ngày lập hóa đơn:"+invoice.getDateInvoice()+"<br/>" +
                "Ngày thanh toán:"+invoice.getPayDay()+"<br/>" +
                "Hình thức thanh toán:"+invoice.getPayments()+"<br/>" +

                "Số tiền thanh toán:"+invoice.getAmount()+" vnđ <br/>" +
                "Nhân viên :"+nhanVien+"<br/>" +
                "<h3>Chúc bạn có một chuyến đi vui vẻ</h3>");

        return "Thanh toán thành công";
    }
    public Map<String,Object> updateThanhToanVNPay(Invoice invoice){
        Map<String,Object> m=new HashMap<>();
        Invoice invoice1=getInvoiceById(invoice.getId());
        invoice1.setIdPayment(invoice.getIdPayment());
        invoice1.setBank(invoice.getBank());
        invoice1.setPayments("Thanh toán VNPAY");
        invoice1.setStatus(2);
        LocalDateTime localDateTime=LocalDateTime.now();
        invoice1.setPayDay(localDateTime.toString());
        invoiceRepository.save(invoice1);
        emailSenderService.sendMailHtml(invoice1.getEmail(),"Giao dịch thành công","<h2>Thanh toán hóa đơn thành công</h2><br/>" +
                "Mã hóa đơn:"+invoice1.getId()+"<br/>" +
                "Người đặt:"+invoice1.getFullName()+"<br/>" +
                "Địa chỉ:"+invoice1.getAddress()+"<br/>" +
                "Ngày lập hóa đơn:"+invoice1.getDateInvoice()+"<br/>" +
                "Ngày thanh toán:"+invoice1.getPayDay()+"<br/>" +
                "Hình thức thanh toán:"+invoice1.getPayments()+"<br/>" +
                "Ngân hàng:"+invoice1.getBank()+"<br/>" +
                "Mã giao dịch:"+invoice1.getIdPayment()+"<br/>" +
                "Số tiền thanh toán:"+invoice1.getAmount()+" vnđ <br/>" +
                "<h3>Chúc bạn có một chuyến đi vui vẻ</h3>");
        return m;
    }

    public String xoaHoaDon(String id){
        Invoice invoice=invoiceRepository.findById(id).get();
        if(invoice.getStatus()!=3) return "Xóa không thành công";
        else invoiceRepository.delete(invoice);
        return "Xóa thành công";
    }
    public Map<String,Object> xuLyHoaDon(List<String> list){
        Map<String, Object> m=new HashMap<>();
        for(int i=0;i<list.size();i++){
            Invoice invoice=getInvoiceById(list.get(i));
            invoice.setConfirm(true);
            invoiceRepository.save(invoice);
        }
        m.put("status","1");
        m.put("message","Xử lý thành công");
        return m;
    }
    public Map<String,Object> xacNhanTatCa(List<String> list){
        Map<String, Object> m=new HashMap<>();
        for(int i=0;i<list.size();i++){
            Invoice invoice=getInvoiceById(list.get(i));
            invoice.setStatus(1);
            invoiceRepository.save(invoice);
        }
        m.put("status","1");
        m.put("message","Xác nhận thành công");
        return m;
    }

    public Map<String,Object> getChiTiet(String idInvoice){
        Date now=new Date();
        Map<String,Object> m=new HashMap<>();
        Invoice invoice=getInvoiceById(idInvoice);
        m.put("people",invoice.getPeople());
        m.put("status",invoice.getStatus());
        m.put("totalpeople",scheduleService.countPeople(invoice.getIdSchedule()));
        m.put("amount",invoice.getAmount());
        m.put("payments",invoice.getPayments());
        m.put("payDay",invoice.getPayDay());
        Schedule schedule=scheduleService.getSchedule(invoice.getIdSchedule());
        long getDiff = schedule.getDayStart().getTime() - now.getTime();
        long getDaysDiff = TimeUnit.MILLISECONDS.toDays(getDiff);
        m.put("dayStart",schedule.getDayStart());
        m.put("countDay",getDaysDiff);
        m.put("addressStart",schedule.getAddressStart());
        m.put("idTour",schedule.getIdTour());
        m.put("tourGuide",schedule.getTourGuide());
        m.put("phone",schedule.getPhone());
        m.put("progress",schedule.getProgress());
        Tour tour=scheduleService.getTour(schedule.getId());
        m.put("vehicle",tour.getVehicle());
        m.put("inteval",tour.getInteval());
        m.put("title",tour.getTitle());
        m.put("subTitle",tour.getSubTitle());
        m.put("services",tour.getIdService());
        m.put("image",tour.getImage().get(0));
        m.put("price",tour.getPrice());
        m.put("sale",tour.getSale());
        m.put("address",tour.getAddress());
        m.put("star",tourService.danhGia(tour.getId()));

        return m;
    }
    public List<Map<String,Object>> getListChiTietByIdAccount(String idAccount){
        List<Map<String,Object>> list =new ArrayList<>();
        List<Invoice> invoices=getListInvoiceByIdAccount(idAccount);
        for(int i=0;i<invoices.size();i++)
        {
            list.add(getChiTiet(invoices.get(i).getId()));
        }
        return list;
    }


}
