package com.example.travel_app_api.controller;

import com.example.travel_app_api.model.Schedule;
import com.example.travel_app_api.model.Tour;
import com.example.travel_app_api.request.FilterSchedule;
import com.example.travel_app_api.request.FilterTour;
import com.example.travel_app_api.response.DetailSchedule;
import com.example.travel_app_api.response.ScheduleResponse;
import com.example.travel_app_api.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Date;
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
    @GetMapping("/filter")
    public List<Tour> getListScheduleTour(@RequestBody FilterSchedule filterSchedule){
        return scheduleService.filterSchedule(filterSchedule.getAddressStart(),filterSchedule.getFrom(),filterSchedule.getTo());
    }
    @GetMapping("/all")
    public List<Schedule> getAllListSchedule(){
        return scheduleService.getListSchedule();
    }
    @GetMapping("/active/{idTour}")
    public List<Schedule> getListScheduleActiveByIdTour(@PathVariable String idTour){
        return scheduleService.getListScheduleByIdTourActive(idTour);
    }
    @GetMapping("/countactive/{idTour}")
    public int countScheduleActiveByIdTour(@PathVariable String idTour){
        return scheduleService.countScheduleActiveByIdTour(idTour);
    }

    @GetMapping("/{status}/{idTour}")
    public List<Schedule> getListScheduleActiveById(@PathVariable String idTour,@PathVariable String status){
        return scheduleService.getListScheduleByIdTourStatus(idTour,status);
    }
    @GetMapping("/all/active")
    public List<Schedule> getListScheduleActive(){
        return scheduleService.getListScheduleActive();
    }
    @GetMapping("/home")
    public List<Schedule> getListLastTour(){
        return scheduleService.getListLastTourHome();
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
    @GetMapping("/detailpeople/{id}")
    public Map<String,Object> getDetailPeopleById(@PathVariable String id){
        return scheduleService.getDetaiPeopleSchedule(id);
    }
    @GetMapping("/getlistschedulebydaystart")
    public List<Map<String,Object>> getListScheduleByday(@RequestParam String dayStart){
        return scheduleService.getListScheduleByDayStart(dayStart);
    }

    @GetMapping("/listdetailschedule/{loai}")
    public List<Schedule> getListDetailSchedule(@PathVariable String loai){
        return scheduleService.quanLyChotTour(loai);
    }
    @GetMapping("/progress/{progress}")
    public List<Schedule> getListDetailSchedule(@PathVariable int progress){
        return scheduleService.getScheduleByProgress(progress);
    }
    @GetMapping("/idtourprogress/{idTour}/{progress}")
    public List<Schedule> getListDetailSchedule(@PathVariable String idTour,@PathVariable int progress){
        return scheduleService.getScheduleByIdTourAndProgress(idTour,progress);
    }

    @PostMapping()
    public Map<String,Object> addSchedule(@RequestBody Schedule schedule){
        return scheduleService.addSchedule(schedule);
    }
    @PutMapping
    public Map<String,Object> updateSchedule(@RequestBody Schedule schedule){
            return scheduleService.updateSchedule(schedule);
    }
    @PutMapping("/huytour/{id}/{lydo}")
    public Map<String,Object> huySchedule(@PathVariable String id,@PathVariable String lydo){
        return scheduleService.huyTour(id,lydo);
    }
    @PutMapping("/changestatus")
    public String changeStatus(@RequestBody Schedule schedule){
        return scheduleService.changeStatus(schedule.getId(),schedule.isStatus());
    }
    @PutMapping("/changeprogress/{id}/{progress}")
    public String changeProgress(@PathVariable String id,@PathVariable int progress){
        return scheduleService.updateProgress(id,progress);
    }
    @DeleteMapping("/{id}")
    public Map<String, Object> daleteSchedule(@PathVariable String id){
        return scheduleService.deleteSchedule(id);
    }


}
