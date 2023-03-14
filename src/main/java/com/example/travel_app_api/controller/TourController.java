package com.example.travel_app_api.controller;

import com.example.travel_app_api.model.Account;
import com.example.travel_app_api.model.Tour;
import com.example.travel_app_api.service.AccountService;
import com.example.travel_app_api.service.TourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tour")
@CrossOrigin
public class TourController {
    @Autowired
    private TourService tourService;
    @GetMapping
    public List<Tour> getListTotur(){
        List<Tour> tour= tourService.getlist();
        return tour;
    }
    @PostMapping
    public Tour addTour(@RequestBody Tour tour){
        return tourService.addTour(tour);
    }
}
