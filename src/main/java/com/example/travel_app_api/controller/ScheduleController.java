package com.example.travel_app_api.controller;

import com.example.travel_app_api.model.Schedule;
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
    public List<Schedule> getListScheduleActive(@PathVariable String idTour){
        return scheduleService.getListScheduleByIdTourActive(idTour);
    }
    @GetMapping("/getschedule")
    public Map<String, Object> getSchedule(@RequestParam String idSchedule){
        return scheduleService.getSchedule(idSchedule);
    }

    @PostMapping()
    public Map<String,Object> addSchedule(@RequestBody Schedule schedule){
        return scheduleService.addSchedule(schedule);
    }
    @PutMapping
    public Map<String,Object> updateSchedule(@RequestBody Schedule schedule){
            return scheduleService.updateSchedule(schedule);
    }
    @DeleteMapping
    public String daleteSchedule(@RequestParam(defaultValue = "") String id){
        return scheduleService.deleteSchedule(id);
    }



}
