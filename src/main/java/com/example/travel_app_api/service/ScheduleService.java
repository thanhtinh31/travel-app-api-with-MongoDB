package com.example.travel_app_api.service;

import com.example.travel_app_api.model.Schedule;
import com.example.travel_app_api.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ScheduleService {
    @Autowired
    private ScheduleRepository scheduleRepository;
    public List<Schedule> getListScheduleByIdTour(String idTour){
        return scheduleRepository.getListScheduleByTourId(idTour);
    }
    public Map<String,Object> addSchedule(Schedule schedule){
        Map<String,Object> m=new HashMap<>();
        //if()
        return m;
    }
    public Schedule add(Schedule schedule){
        return scheduleRepository.save(schedule);
    }



}
