package com.example.travel_app_api.service;

import com.example.travel_app_api.model.Category;
import com.example.travel_app_api.model.Tour;
import com.example.travel_app_api.repository.TourRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TourService {
    @Autowired
    TourRepository tourRepository;
    public Page<Tour> getListTour(int page, int size, String sort){
        if(page<=0) page=1;
        return tourRepository.findAll(PageRequest.of(page-1,size, Sort.by(sort).descending()));
    };
    public Tour addTour(Tour tour){
        return tourRepository.save(tour);
    }
    public Tour getTourById(String id){
        return tourRepository.findById(id).get();
    }
    public List<Tour> getTourByCategory(String idCategory){
        return tourRepository.getListTourByCategory(idCategory);
    }
    public Tour updateTour(Tour tour){
        Tour tour1=tourRepository.findById(tour.getId()).get();
        tour1.setImage(tour.getImage());
        tour1.setDescribe(tour.getDescribe());
        tour1.setAddress(tour.getAddress());
        tour1.setInteval(tour.getInteval());
        tour1.setSale(tour.getSale());
        tour1.setPrice(tour.getPrice());
        tour1.setStatus(tour.getStatus());
        tour1.setInteresting(tour.getInteresting());
        tour1.setImage(tour.getImage());
        tour1.setSubTitle(tour.getSubTitle());
        tour1.setVehicle(tour.getVehicle());
        tour1.setIdCategory(tour.getIdCategory());
        return tourRepository.save(tour1);
    }
    public String deleteTour(String id){
        tourRepository.deleteById(id);
        return "deleted";
    }
    public List<Tour> searchTour(String key){
        return tourRepository.searchTour(key);
    }

}
