package com.example.travel_app_api.controller;

import com.example.travel_app_api.model.Request;
import com.example.travel_app_api.model.Schedule;
import com.example.travel_app_api.service.RequestService;
import com.example.travel_app_api.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/request")
@CrossOrigin
public class RequestController {
    @Autowired
    RequestService requestService;
    @GetMapping("")
    public List<Request> getAllRequest(){
        return requestService.getAllRequest();
    }
    @GetMapping("/accept/{accept}")
    public List<Request> getRequestByAccept(@PathVariable int accept){
        return requestService.getRequestByAccept(accept);
    }
    @GetMapping("/count/{accept}")
    public int count(@PathVariable int accept){
        return requestService.count(accept);
    }
    @PostMapping
    public Request add(@RequestBody Request request){
        return requestService.addRequest(request);
    }
    @PutMapping("/accept/{id}")
    public Map<String,Object> accept(@PathVariable String id,@RequestBody Schedule schedule){
        return requestService.accept(id,schedule);
    }
    @PutMapping("/cancel/{id}")
    public Map<String,Object> cancel(@PathVariable String id){
        return requestService.cancel(id);
    }
    @DeleteMapping("/{id}")
    public String delete(@PathVariable String id){
        return requestService.deleteRequest(id);
    }

}
