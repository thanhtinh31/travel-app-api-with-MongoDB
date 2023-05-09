package com.example.travel_app_api.service;

import com.example.travel_app_api.model.Invoice;
import com.example.travel_app_api.model.Schedule;
import com.example.travel_app_api.model.Tour;
import com.example.travel_app_api.repository.InvoiceRepository;
import com.example.travel_app_api.repository.ScheduleRepository;
import com.example.travel_app_api.repository.TourRepository;
import com.example.travel_app_api.response.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;

@Service
public class ScheduleService {
    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private TourRepository tourRepository;
    @Autowired
    InvoiceRepository invoiceRepository;
    @Autowired
    private EmailSenderService emailSenderService;

    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    public List<Schedule> getListScheduleByIdTour(String idTour){
        return scheduleRepository.getListScheduleByTourId(idTour);
    }
    public List<ScheduleResponse> listSchedule(){
        List<ScheduleResponse> scheduleResponses =new ArrayList<>() ;
        List<Tour> list=tourRepository.findAll();
        for(int i=0;i<list.size();i++){
            scheduleResponses.add(new ScheduleResponse(list.get(i),getListScheduleByIdTour(list.get(i).getId())));
        }
        return scheduleResponses;
    }
    public List<Schedule> getListSchedule(){
        return scheduleRepository.findAll();
    }
    public List<Schedule> getListScheduleByIdTourActive(String idTour){
        long millis = System.currentTimeMillis();
        Date date = new java.sql.Date(millis);
        return scheduleRepository.getListScheduleByTourIdActive(idTour,date);
    }
    public int countScheduleActiveByIdTour(String idTour){
        long millis = System.currentTimeMillis();
        Date date = new java.sql.Date(millis);
        return scheduleRepository.getListScheduleByTourIdActive(idTour,date).size();
    }
    public List<Schedule> getListScheduleByIdTourStatus(String idTour,String status){
        long millis = System.currentTimeMillis();
        Date date = new java.sql.Date(millis);
        if(status.equals("0")){
        return scheduleRepository.getListScheduleByTourIdActiveSeller(idTour,date);}
        else return scheduleRepository.getListScheduleByTourIdPass(idTour,date);
    }
    public List<Schedule> getListScheduleActive(){
        long millis = System.currentTimeMillis();
        Date date = new java.sql.Date(millis);
        List<Schedule> list=scheduleRepository.getListScheduleActive(date);
        return list;
    }
    public List<Schedule> getListLastTourHome(){
        long millis = System.currentTimeMillis();
        Date date = new java.sql.Date(millis);
        List<Schedule> list=scheduleRepository.getListScheduleActive(date);
        return list;
    }


    public Tour getTour(String id){
        Schedule schedule =scheduleRepository.findById(id).get();
        Tour tour=tourRepository.findById(schedule.getIdTour()).get();
        return tour;
    }
    public Schedule getSchedule(String id){
        Schedule schedule =scheduleRepository.findById(id).get();
        return schedule;

    }
    public Map<String,Object> addSchedule(Schedule schedule){
        Map<String,Object> m=new HashMap<>();
        Date dayNow=new Date();
        if(!dayNow.before(schedule.getDayStart())){
            m.put("message","Lên lịch thất bại, Ngày đã qua");
            m.put("status","0");
            return m;
        }
        else
        if(checkExist(schedule.getIdTour(),formatter.format(schedule.getDayStart()),schedule.getAddressStart())==1){
            m.put("message","Đã ton tại lịch trình");
            m.put("status","0");
        }
        else{
            scheduleRepository.save(schedule);
            m.put("message","Thêm lịch trình thành công");
            m.put("status","1");
            m.put("schedule",schedule);
        }
        return m;
    }
    public Map<String,Object> updateSchedule(Schedule schedule){
        Map<String,Object> m =new HashMap<>();
        Date dayNow=new Date();
        Schedule schedule1 = scheduleRepository.findById(schedule.getId()).get();
        if(!dayNow.before(schedule.getDayStart())){
            m.put("message","Cập nhật thất bại, Ngày khong hợp lệ");
            m.put("status","0");
            return m;
        }
        else
        if(checkExist(schedule.getIdTour(),formatter.format(schedule.getDayStart()),schedule.getAddressStart())==1 &&
                schedule1.getDayStart().compareTo(schedule.getDayStart())!=0){
            m.put("message","Đã tồn tại lịch trình");
            m.put("status","0");
        }
        else{
            schedule1.setDayStart(schedule.getDayStart());
            schedule1.setPhone(schedule.getPhone());
            schedule1.setTourGuide(schedule.getTourGuide());
            schedule1.setAddressStart(schedule.getAddressStart());
            schedule1.setStatus(schedule.isStatus());

            scheduleRepository.save(schedule1);
            m.put("message","Cập nhật lịch trình thành công");
            m.put("status","1");
            m.put("schedule",schedule1);
        }
        return m;

    }
    public int checkExist(String idTour,String day,String address){
        List<Schedule> list=getListScheduleByIdTour(idTour);

        for(int i=0;i<list.size();i++){
            String strDate = formatter.format(list.get(i).getDayStart());
            if(strDate.equals(day)&&list.get(i).getAddressStart().equals(address)) return 1;
        }
        return 0;
    }
    public Map<String,Object> deleteSchedule(String id){
        Map<String,Object> m=new HashMap<>();

        if(invoiceRepository.getListInvoiceByIdSchedule(id).size()!=0)
        {
            m.put("status","0");
            m.put("message","Xóa không thành công");
        }
        else{
            scheduleRepository.deleteById(id);
            m.put("status","1");
            m.put("message","Xóa thành cong");
        }
        return m;
    }

    public List<Tour> filterSchedule(String addressStart,String from,String to){
        LocalDate dayfrom = LocalDate.parse(from,
                DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        LocalDate dayto = LocalDate.parse(to,
                DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        List<Tour> list = new ArrayList<>();
        List<Schedule> schedules=null;
        if(addressStart==null||addressStart.equals("")){
            schedules=scheduleRepository.filterNoAddress(dayfrom,dayto);
        }
        else{
            schedules=scheduleRepository.filter(addressStart,dayfrom,dayto);
        }
        for (int i=0;i<schedules.size();i++){
            list.add(tourRepository.findById(schedules.get(i).getIdTour()).get());
        }
        return list;
    }

    public List<TopTour> thongkehoadon(){
        List<CountInvoice> list=invoiceRepository.countInvoiceByIdSchedule();
        for (int i=0;i<list.size();i++){
            Schedule schedule=getSchedule(list.get(i).getId());
            list.get(i).setIdTour(schedule.getIdTour());
        }
        List<TopTour> topTours=new ArrayList<>();
        List<Tour> tourList =tourRepository.findAll();
        for (int i=0;i<tourList.size();i++){
            int c=0;
            for (int j=0;j<list.size();j++){
                if(list.get(j).getIdTour().equals(tourList.get(i).getId())) c=c+list.get(j).getCount();
            }
            TopTour topTour=new TopTour(tourList.get(i).getId(),c,tourList.get(i).getTitle());
            topTours.add(topTour);
        }
        return topTours;
    }
    public Map<String,Object> getDetaiPeopleSchedule(String idSchedule){
        Map<String,Object> m=new HashMap<>();
        List<Invoice> invoices=invoiceRepository.getListInvoiceByIdSchedule(idSchedule);
        int dachot=0,chuachot=0,dahuy=0,choxacnhan=0;
        for(int i=0;i<invoices.size();i++){
            if(invoices.get(i).getStatus()==2) dachot=dachot+invoices.get(i).getPeople();
            else if(invoices.get(i).getStatus()==1) chuachot=chuachot+invoices.get(i).getPeople();
            else if(invoices.get(i).getStatus()==3) dahuy=dahuy+invoices.get(i).getPeople();
            else choxacnhan=choxacnhan+invoices.get(i).getPeople();
        }
        m.put("choxacnhan",choxacnhan);
        m.put("dachot",dachot);
        m.put("chuachot",chuachot);
        m.put("dahuy",dahuy);
        return m;
    }
    public List<Schedule> quanLyChotTour(String loai){
        List<DetailSchedule> list=new ArrayList<>();
        long millis = System.currentTimeMillis();
        Date date = new java.sql.Date(millis);
        List<Schedule> schedules;
        if(loai.equals("dachot")) schedules=scheduleRepository.getListScheduleDaChot(date);
        else if(loai.equals("chuachot")) schedules=scheduleRepository.getListScheduleChuaChot(date);
        else if(loai.equals("dahuy")) schedules=scheduleRepository.getListScheduleDaHuy(date);
        else if(loai.equals("dangkhoihanh")) schedules=scheduleRepository.getListScheduleDangKhoiHanh(date);
        else schedules=scheduleRepository.getListScheduleDaChotDaHoanThanh(date);
       // System.out.println(schedules.size());
//        for (int i=0;i<schedules.size();i++){
//            DetailSchedule m=new DetailSchedule();
//            m.setTitle(tourRepository.findById(schedules.get(i).getIdTour()).get().getTitle());
//            m.setAddress(tourRepository.findById(schedules.get(i).getIdTour()).get().getAddress());
//            m.setPhone(schedules.get(i).getPhone());
//            m.setTourGuide(schedules.get(i).getTourGuide());
//            m.setDayStart(schedules.get(i).getDayStart());
//            m.setPeople(getDetaiPeopleSchedule(schedules.get(i).getId()));
//            m.setIdSchedule(schedules.get(i).getId());
//            list.add(m);
//        }
        return schedules;
    }

    public Map<String,Object> huyTour(String idSchedule,String lydo){
        Map<String,Object> m=new HashMap<>();
        if(lydo!=null&&idSchedule!=null) {
            Schedule schedule = getSchedule(idSchedule);
            schedule.setStatus(false);
            schedule.setProgress(3);
            scheduleRepository.save(schedule);
            List<Invoice> invoices = invoiceRepository.getListInvoiceByIdSchedule(idSchedule);
            List<String> listEmail = new ArrayList<>();
            for (int i = 0; i < invoices.size(); i++) {
                listEmail.add(invoices.get(i).getEmail());
                invoices.get(i).setStatus(3);
                invoices.get(i).setConfirm(false);
                invoiceRepository.save(invoices.get(i));
            }
            if (listEmail != null)
                emailSenderService.sendListMailHtml(listEmail, "THÔNG BÁO HỦY TOUR", "Tour của bạn đã bị hủy vì lí do "+lydo);
            m.put("status","1");
        }
        else m.put("status","0");
        return m;
    }
    public String changeStatus(String idSchedule,Boolean status){
        Schedule schedule=getSchedule(idSchedule);
        schedule.setStatus(status);
        scheduleRepository.save(schedule);
        return "Thay đổi thành công";
    }
    public String updateProgress(String id,int progress){
        Schedule schedule=getSchedule(id);
        schedule.setProgress(progress);
        scheduleRepository.save(schedule);
        return "Thanh cong";
    }
    public Map<String,Object> countPeople(String idSchedule){
        Map<String,Object> m=new HashMap<>();
        List<Invoice> invoices=invoiceRepository.getListInvoiceByIdSchedule(idSchedule);
        int cxn=0,ctt=0,dtt=0,h=0;
        for (int i=0;i<invoices.size();i++)
        {
            if(invoices.get(i).getStatus()==0) cxn=cxn+invoices.get(i).getPeople();
            else if(invoices.get(i).getStatus()==1) ctt=ctt+invoices.get(i).getPeople();
            else if(invoices.get(i).getStatus()==2) dtt=dtt+invoices.get(i).getPeople();
            else  h=h+invoices.get(i).getPeople();

        }
        m.put("cxn",cxn);
        m.put("ctt",ctt);
        m.put("dtt",dtt);
        m.put("dh",h);
        return m;
    }


}
