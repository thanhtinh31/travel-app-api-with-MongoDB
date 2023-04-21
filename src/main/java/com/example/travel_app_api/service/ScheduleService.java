package com.example.travel_app_api.service;

import com.example.travel_app_api.model.Schedule;
import com.example.travel_app_api.model.Tour;
import com.example.travel_app_api.repository.InvoiceRepository;
import com.example.travel_app_api.repository.ScheduleRepository;
import com.example.travel_app_api.repository.TourRepository;
import com.example.travel_app_api.response.ScheduleResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
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
    public List<Schedule> getListScheduleByIdTourStatus(String idTour,String status){
        long millis = System.currentTimeMillis();
        Date date = new java.sql.Date(millis);
        if(status.equals("0")){
        return scheduleRepository.getListScheduleByTourIdActive(idTour,date);}
        else return scheduleRepository.getListScheduleByTourIdPass(idTour,date);
    }
    public List<Schedule> getListScheduleActive(){
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




}
