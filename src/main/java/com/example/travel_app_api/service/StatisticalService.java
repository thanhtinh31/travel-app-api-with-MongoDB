package com.example.travel_app_api.service;

import com.example.travel_app_api.model.*;
import com.example.travel_app_api.response.AccountResponse;
import com.example.travel_app_api.response.TopTour;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class StatisticalService {
    @Autowired
    private AccountService accountService;
    @Autowired
    private InvoiceService invoiceService;
    @Autowired
    private TourService tourService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ServiceService serviceService;
    @Autowired
    private ScheduleService scheduleService;

    public Map<String,Object> thongketaikhoan(){
        Map<String,Object> m=new HashMap<>();
        List<AccountResponse> accounts=accountService.listAcount();
        int admin=0,seller=0,user=0,lock=0;
        for(int i=0;i<accounts.size();i++){
            if(accounts.get(i).isStatus()==false) lock++;
            if(accounts.get(i).getTypeAccount()==1) user++;
            else if(accounts.get(i).getTypeAccount()==2) seller++;else admin++;
        }
        m.put("admin",admin);
        m.put("seller",seller);
        m.put("user",user);
        m.put("lock",lock);
        return m;
    }
    public Map<String,Object> thongKeHoaDon(){
        Map<String,Object> m = new HashMap<>();
        List<Invoice> invoices=invoiceService.getListInvoice();
        int hdm=0,ctt=0,dtt=0,dh=0;
        for(int i=0;i<invoices.size();i++){
            if(invoices.get(i).getStatus()==0) hdm++;
            else  if(invoices.get(i).getStatus()==1) ctt++;
            else if(invoices.get(i).getStatus()==2) dtt++;
            else dh++;
        }
        m.put("hdm",hdm);
        m.put("ctt",ctt);
        m.put("dtt",dtt);
        m.put("dh",dh);
        return m;
    }
    public Map<String,Object> countHd(String idAccount){
        Map<String,Object> m=new HashMap<>();
        Account account=accountService.getAccountById(idAccount);
        int c=0;
        List<Invoice> invoices=invoiceService.getListInvoice();
        for(int i=0;i<invoices.size();i++){
            if(invoices.get(i).getIdAccount().equals(idAccount)) c++;
        }
        m.put("count",c);
        m.put("idAccount",idAccount);
        m.put("nameAccount",account.getNameAccount());
        return m;
    }
    public List<Map<String,Object>> topAccount(int sl){
        Map<String,Object> m=new HashMap<>();
        List<AccountResponse> accounts=accountService.listAcount();
        List<Map<String,Object>> lists=new ArrayList<>();
        for(int i=0;i<accounts.size();i++){
            lists.add(countHd(accounts.get(i).getId()));
        }
        for(int i=0;i<lists.size();i++){
            for (int j=i+1;j<lists.size();j++)
                if((int)lists.get(i).get("count")<(int)lists.get(j).get("count")){
                    Map<String,Object> temp =lists.get(i);
                    lists.set(i,lists.get(j));
                    lists.set(j,temp);
                }
        }
        if (sl>lists.size()) sl=lists.size();
        return lists.subList(0,sl);
    }
    public Map<String,Object> thongKeHoaDonTheoNam(String year){
        Map<String,Object> m = new HashMap<>();
        List<Invoice> invoices=invoiceService.getListInvoiceByYear(year);
        int hdm=0,ctt=0,dtt=0,dh=0;
        for(int i=0;i<invoices.size();i++){
            if(invoices.get(i).getStatus()==0) hdm++;
            else  if(invoices.get(i).getStatus()==1) ctt++;
            else if(invoices.get(i).getStatus()==2) dtt++;
            else dh++;
        }
        m.put("hdm",hdm);
        m.put("ctt",ctt);
        m.put("dtt",dtt);
        m.put("dh",dh);
        return m;
    }
    public Map<String,Object> thongKeHoaDonTheoNgay(String date) throws ParseException {
        SimpleDateFormat DateFor = new SimpleDateFormat("MM/dd/yyyy");
        if(date==null||date==""||date=="now"){
            date=new Date().toString();
        }
        LocalDate day = LocalDate.parse(date,
                DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        Map<String,Object> m = new HashMap<>();
        List<Invoice> invoices=invoiceService.getListInvoice();
        int hdm=0,ctt=0,dtt=0,dh=0;
        for(int i=0;i<invoices.size();i++){
            if(invoices.get(i).getDateInvoice().compareTo(day)==0) {
                if (invoices.get(i).getStatus() == 0)
                    hdm++;else if (invoices.get(i).getStatus() == 1) ctt++;
                else if (invoices.get(i).getStatus() == 2) dtt++;
                else dh++;
            }
        }
        m.put("hdm",hdm);
        m.put("ctt",ctt);
        m.put("dtt",dtt);
        m.put("dh",dh);

        return m;
    }
    public Map<String,Object> thongKeDoanhThuTheoNgay(String date) {
        SimpleDateFormat DateFor = new SimpleDateFormat("MM/dd/yyyy");
        if(date==null||date==""||date=="now"){
            date=new Date().toString();
        }
        LocalDate day = LocalDate.parse(date,
                DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        Map<String,Object> m = new HashMap<>();
        List<Invoice> invoices=invoiceService.getListInvoice();
        double doanhthu=0;
        for(int i=0;i<invoices.size();i++){
            if(invoices.get(i).getDateInvoice().compareTo((day))==0)
                if(invoices.get(i).getStatus()==2) doanhthu=doanhthu+invoices.get(i).getAmount();
        }
        m.put("date",date);
        m.put("doanhthu",doanhthu);
        return m;
    }
    public List<Map<String,Object>> thongKeDoanhThuDenNgay(String date) {
        List<Map<String,Object>> list =new ArrayList<>();
        List<Invoice> invoices = invoiceService.getListInvoice();
        for(int j=5;j>=0;j--) {
            SimpleDateFormat DateFor = new SimpleDateFormat("MM/dd/yyyy");
            if (date == null || date == "" || date == "now") {
                date = new Date().toString();
            }
            LocalDate day = LocalDate.parse(date,
                    DateTimeFormatter.ofPattern("MM/dd/yyyy"));
            day=day.minusDays(j);
            Map<String, Object> m = new HashMap<>();

            double doanhthu = 0;
            for (int i = 0; i < invoices.size(); i++) {
                if (invoices.get(i).getDateInvoice().compareTo((day)) == 0)
                    if (invoices.get(i).getStatus() == 2) doanhthu = doanhthu + invoices.get(i).getAmount();
            }
            m.put("date", day);
            m.put("doanhthu", doanhthu);
            list.add(m);
        }
        return list;
    }
    public Map<String,Object> thongKeDoanhThuTheoThang(String year) throws ParseException {
        Map<String,Object> m = new HashMap<>();
        List<Invoice> invoices=invoiceService.getListInvoiceByYear(year);
        double t1=0,t2=0,t3=0,t4=0,t5=0,t6=0,t7=0,t8=0,t9=0,t10=0,t11=0,t12=0;
        for(int i=0;i<invoices.size();i++){
            if(invoices.get(i).getStatus()==2) {
                Invoice invoice=invoices.get(i);
                LocalDate date= invoice.getDateInvoice();

                if(date.getMonthValue()==1) t1=t1+invoice.getAmount();
                else if(date.getMonthValue()==2) t2=t2+invoice.getAmount();
                else if(date.getMonthValue()==3) t3=t3+invoice.getAmount();
                else if(date.getMonthValue()==4) t4=t4+invoice.getAmount();
                else if(date.getMonthValue()==5) t5=t5+invoice.getAmount();
                else if(date.getMonthValue()==6) t6=t6+invoice.getAmount();
                else if(date.getMonthValue()==7) t7=t7+invoice.getAmount();
                else if(date.getMonthValue()==8) t8=t8+invoice.getAmount();
                else if(date.getMonthValue()==9) t9=t9+invoice.getAmount();
                else if(date.getMonthValue()==10) t10=t10+invoice.getAmount();
                else if(date.getMonthValue()==11) t11=t11+invoice.getAmount();
                else if(date.getMonthValue()==12) t12=t12+invoice.getAmount();
            }

        }
        m.put("t1",t1);
        m.put("t2",t2);
        m.put("t3",t3);
        m.put("t4",t4);
        m.put("t5",t5);
        m.put("t6",t6);
        m.put("t7",t7);
        m.put("t8",t8);
        m.put("t9",t9);
        m.put("t10",t10);
        m.put("t11",t11);
        m.put("t12",t12);
        return m;
    }
    public Map<String,Object> thongKeTour(){
        Map<String,Object> m=new HashMap<>();
        m.put("countTour",tourService.countTour());
        m.put("countCategory",categoryService.countCategory());
        m.put("countService",serviceService.countService());

        return m;
    }
    public List<TopTour> getTopTour(int sl){
        if(sl<3) sl=3;
        List<TopTour> topTours=scheduleService.thongkehoadon();
        for(int i=0;i<topTours.size();i++){
            for (int j=i+1;j<topTours.size();j++)
                if(topTours.get(i).getCount()<topTours.get(j).getCount()){
                    TopTour temp =topTours.get(i);
                    topTours.set(i,topTours.get(j));
                    topTours.set(j,temp);
                }
        }
        if (sl>topTours.size()) sl=topTours.size();
        return topTours.subList(0,sl);
    }

    public Map<String,Object> thongKeLichTrinh(){
        Map<String,Object> m=new HashMap<>();
        List<Schedule> schedules=scheduleService.getListSchedule();
        int ckh=0,dkh=0,dht=0,dh=0;
        for(int i=0;i<schedules.size();i++)
        {
            if(schedules.get(i).getProgress()==1||schedules.get(i).getProgress()==0) ckh=ckh+1;
            else if(schedules.get(i).getProgress()==2) dkh=dkh+1;
            else if(schedules.get(i).getProgress()==3) dht=dht+1;
            else dh=dh+1;

        }
        m.put("ckh",ckh);
        m.put("dkh",dkh);
        m.put("dht",dht);
        m.put("dh",dh);
        return m;
    }





}
