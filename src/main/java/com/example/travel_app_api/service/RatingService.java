package com.example.travel_app_api.service;

import com.example.travel_app_api.model.Rating;
import com.example.travel_app_api.repository.RatingRepository;
import com.example.travel_app_api.response.RatingResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RatingService {
    @Autowired
    private RatingRepository ratingRepository;
    @Autowired
    private AccountService accountService;
    public List<Rating> getRatingByidTour(String id){
        return ratingRepository.getListRatingByIdTour(id);
    }
    public Rating postDanhGia(Rating rating){
        LocalDateTime now=LocalDateTime.now();
        rating.setTime(now);
        return ratingRepository.save(rating);
    }
    public Map<String,Object> getThongKe(String id){
        Map<String,Object> m=new HashMap<>();
        List<Rating> ratings=ratingRepository.getListRatingByIdTour(id);
        int mot=0 ,hai=0,ba=0,bon=0,nam=0;
        for(int i=0;i<ratings.size();i++){
            if(ratings.get(i).getStar()==1) mot++;else
            if(ratings.get(i).getStar()==2) hai++;else
            if(ratings.get(i).getStar()==3) ba++;else
            if(ratings.get(i).getStar()==4) bon++;else nam++;
        }
        m.put("tong",mot+hai+ba+bon+nam);
        m.put("mot",mot);
        m.put("hai",hai);
        m.put("ba",ba);
        m.put("bon",bon);
        m.put("nam",nam);
        m.put("trungbinh",(float)(mot*1+hai*2+ba*3+bon*4+nam*5)/(mot+hai+ba+bon+nam));
        return m;
    }
}
