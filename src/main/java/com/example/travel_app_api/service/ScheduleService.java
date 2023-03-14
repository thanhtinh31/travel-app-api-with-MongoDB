package com.example.travel_app_api.service;

import com.example.travel_app_api.model.Schedule;
import com.example.travel_app_api.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScheduleService {
    @Autowired
    private ScheduleRepository scheduleRepository;

}
