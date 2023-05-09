package com.example.travel_app_api.controller;

import com.example.travel_app_api.model.Schedule;
import com.example.travel_app_api.model.Service;
import com.example.travel_app_api.model.Tour;
import com.example.travel_app_api.service.ServiceService;
import com.example.travel_app_api.service.TourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/service")
@CrossOrigin
public class ServiceController {
    @Autowired
    private ServiceService serviceService;
    @GetMapping("/{id}")
    public Service getTourById(@PathVariable String id){
        return  serviceService.getServiceById(id);
    }
    @GetMapping("/list/{ids}")
    public List<Service> getTourById(@PathVariable List<String> ids){
        return  serviceService.getServiceByListId(ids);
    }
    @GetMapping("/all")
    public List<Service> getAll(){
        return  serviceService.getAllService();
    }
    @GetMapping("/active")
    public List<Service> getActive(){
        return  serviceService.getServiceActive();
    }
    @PostMapping()
    public Map<String,Object> addNew(@RequestBody Service service){
        return serviceService.addNew(service);
    }
    @PutMapping
    public Map<String,Object> update(@RequestBody Service service){
        return serviceService.update(service);
    }
    @DeleteMapping("/{id}")
    public String delete(@PathVariable String id){
        return serviceService.deleteByid(id);
    }
}
