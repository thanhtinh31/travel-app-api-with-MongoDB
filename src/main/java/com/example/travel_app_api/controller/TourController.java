package com.example.travel_app_api.controller;

import com.example.travel_app_api.model.Account;
import com.example.travel_app_api.model.Category;
import com.example.travel_app_api.model.Tour;
import com.example.travel_app_api.request.FilterTour;
import com.example.travel_app_api.service.AccountService;
import com.example.travel_app_api.service.TourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tour")
@CrossOrigin
public class TourController {
    @Autowired
    private TourService tourService;
    @GetMapping
    public Page<Tour> getListTour(@RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                                  @RequestParam(name = "size", required = false, defaultValue = "5") Integer size,
                                  @RequestParam(name = "sort", required = false, defaultValue = "id") String sort){
        Page<Tour> tours= tourService.getListTour(page,size,sort);
        return tours;
    }
    @GetMapping("/{id}")
    public Tour getTourById(@PathVariable String id){
        return  tourService.getTourById(id);
    }
    @GetMapping("/active")
    public List<Tour> getTourActive(){
        return  tourService.getTourActive();
    }
    @GetMapping("/homeactive")
    public List<Tour> getTourHomeActive(){
        return  tourService.getTourHomeActive();
    }
    @GetMapping("/all")
    public List<Tour> getAllTOur(){
        return  tourService.getAllTour();
    }
    @GetMapping("/gettourbycategory")
    public List<Tour> getListTourByCategory(@RequestParam(name = "category") String id ){
        return tourService.getTourByCategory(id);
    }
    @GetMapping("/search")
    private List<Tour> search(@RequestParam(name="key",required = false,defaultValue = "") String key){
        return tourService.searchTour(key);
    }
    @PostMapping("/filter")
    private List<Tour> search(@RequestBody FilterTour filterTour){
        return tourService.
                getTourFilter(filterTour);
    }

    @PostMapping
    public Map<String, Object> addTour(@RequestBody Tour tour){
        return tourService.addTour(tour);
    }
    @DeleteMapping("/{id}")
    public String deleteTour(@PathVariable String id){
        return tourService.deleteTour(id);
    }
    @DeleteMapping("/deletelist/{ids}")
    public String deleteTour(@PathVariable List<String> ids){
        return tourService.deleteListTour(ids);
    }

    @PutMapping
    public Tour update(@RequestBody Tour tour){
        return tourService.updateTour(tour);
    }

}
