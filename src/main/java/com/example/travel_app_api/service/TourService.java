package com.example.travel_app_api.service;

import com.example.travel_app_api.model.Tour;
import com.example.travel_app_api.repository.TourRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TourService {
    @Autowired
    TourRepository tourRepository;
    public List<Tour> getlist(){
        return tourRepository.findAll();
    }
    public Tour addTour(Tour tour){
        return tourRepository.save(tour);
    }
}
