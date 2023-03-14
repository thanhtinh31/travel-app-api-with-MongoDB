package com.example.travel_app_api.controller;

import com.example.travel_app_api.model.Schedule;
import com.example.travel_app_api.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

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
    @PostMapping()
    public Schedule addSchedule(@RequestBody Schedule schedule){
        return scheduleService.add(schedule);
    }

}
