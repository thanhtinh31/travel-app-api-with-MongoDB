package com.example.travel_app_api.controller;

import com.example.travel_app_api.model.Rating;
import com.example.travel_app_api.model.Schedule;
import com.example.travel_app_api.service.RatingService;
import com.example.travel_app_api.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rating")
@CrossOrigin
public class RatingController {
    @Autowired
    RatingService ratingService;
    @GetMapping("/{idTour}")
    public List<Rating> getListSchedule(@PathVariable String idTour){
        return ratingService.getRatingByidTour(idTour);
    }
    @GetMapping("/tongquat/{idTour}")
    public Map<String,Object> getThongKe(@PathVariable String idTour){
        return ratingService.getThongKe(idTour);
    }
    @PostMapping
    public Rating guiDanhGia(@RequestBody Rating rating){
        return ratingService.postDanhGia(rating);
    }
}
