package com.example.travel_app_api.controller;

import com.example.travel_app_api.model.Schedule;
import com.example.travel_app_api.model.Tour;
import com.example.travel_app_api.response.ScheduleResponse;
import com.example.travel_app_api.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/schedule")
@CrossOrigin
public class ScheduleController {
    @Autowired
    ScheduleService scheduleService;
    @GetMapping("/{idTour}")
    public List<Schedule> getListSchedule(@PathVariable String idTour){
        return scheduleService.getListScheduleByIdTour(idTour);
    }
    @GetMapping("/all")
    public List<Schedule> getAllListSchedule(){
        return scheduleService.getListSchedule();
    }
    @GetMapping("/active/{idTour}")
    public List<Schedule> getListScheduleActiveById(@PathVariable String idTour){
        return scheduleService.getListScheduleByIdTourActive(idTour);
    }
    @GetMapping("/{status}/{idTour}")
    public List<Schedule> getListScheduleActiveById(@PathVariable String idTour,@PathVariable String status){
        return scheduleService.getListScheduleByIdTourStatus(idTour,status);
    }
    @GetMapping("/all/active")
    public List<Schedule> getListScheduleActive(){
        return scheduleService.getListScheduleActive();
    }
    @GetMapping("/gettour/{id}")
    public Tour getTour(@PathVariable String id){
        return scheduleService.getTour(id);
    }
    @GetMapping("/getschedule/{id}")
    public Schedule getSchedule(@PathVariable String id){
        return scheduleService.getSchedule(id);
    }
    @GetMapping("/getAllschedule")
    public List<ScheduleResponse> getAllSchedule(){
        return scheduleService.listSchedule();
    }

    @PostMapping()
    public Map<String,Object> addSchedule(@RequestBody Schedule schedule){
        return scheduleService.addSchedule(schedule);
    }
    @PutMapping
    public Map<String,Object> updateSchedule(@RequestBody Schedule schedule){
            return scheduleService.updateSchedule(schedule);
    }
    @DeleteMapping("/{id}")
    public Map<String, Object> daleteSchedule(@PathVariable String id){
        return scheduleService.deleteSchedule(id);
    }

}
