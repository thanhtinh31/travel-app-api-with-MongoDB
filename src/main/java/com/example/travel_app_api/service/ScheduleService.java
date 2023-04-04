package com.example.travel_app_api.service;

import com.example.travel_app_api.model.Schedule;
import com.example.travel_app_api.model.Tour;
import com.example.travel_app_api.repository.ScheduleRepository;
import com.example.travel_app_api.repository.TourRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ScheduleService {
    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private TourRepository tourRepository;
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    public List<Schedule> getListScheduleByIdTour(String idTour){
        return scheduleRepository.getListScheduleByTourId(idTour);
    }
    public List<Schedule> getListSchedule(){
        return scheduleRepository.findAll();
    }
    public List<Schedule> getListScheduleByIdTourActive(String idTour){
        long millis = System.currentTimeMillis();
        Date date = new java.sql.Date(millis);
        return scheduleRepository.getListScheduleByTourIdActive(idTour,date);
    }
    public List<Schedule> getListScheduleActive(){
        long millis = System.currentTimeMillis();
        Date date = new java.sql.Date(millis);
        List<Schedule> list=scheduleRepository.getListScheduleActive(date);

        return list;
    }
    public Map<String,Object> getSchedule(String id){
        Map<String,Object> m=new HashMap<>();
        Schedule schedule =scheduleRepository.findById(id).get();
        Tour tour=tourRepository.findById(schedule.getIdTour()).get();
        m.put("tour",tour);
        m.put("schedule",schedule);
        return m;

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
        if(checkExist(schedule.getIdTour(),formatter.format(schedule.getDayStart()))==1){
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
        if(checkExist(schedule.getIdTour(),formatter.format(schedule.getDayStart()))==1 && schedule1.getDayStart().compareTo(schedule.getDayStart())!=0){
            m.put("message","Đã tồn tại lịch trình");
            m.put("status","0");
        }
        else{
            schedule1.setDayStart(schedule.getDayStart());
            schedule1.setPhone(schedule.getPhone());
            schedule1.setTourGuide(schedule.getTourGuide());
            scheduleRepository.save(schedule1);
            m.put("message","Cập nhật lịch trình thành công");
            m.put("status","1");
            m.put("schedule",schedule1);
        }
        return m;

    }

    public int checkExist(String idTour,String day){
        List<Schedule> list=getListScheduleByIdTour(idTour);

        for(int i=0;i<list.size();i++){
            String strDate = formatter.format(list.get(i).getDayStart());
            if(strDate.equals(day)) return 1;
        }
        return 0;
    }
    public String deleteSchedule(String id){
        scheduleRepository.deleteById(id);
        return "deleted";
    }




}
