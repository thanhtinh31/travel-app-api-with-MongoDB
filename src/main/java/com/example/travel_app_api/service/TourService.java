package com.example.travel_app_api.service;
import com.example.travel_app_api.model.Rating;
import com.example.travel_app_api.model.Tour;
import com.example.travel_app_api.repository.TourRepository;
import com.example.travel_app_api.request.FilterTour;
import com.example.travel_app_api.response.ItemTour;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TourService {
    @Autowired
    TourRepository tourRepository;
    @Autowired
    ScheduleService scheduleService;
    @Autowired
    RatingService ratingService;
    public Page<Tour> getListTour(int page, int size, String sort){
        if(page<=0) page=1;
        return tourRepository.findAll(PageRequest.of(page-1,size, Sort.by(sort).descending()));
    };
    public List<Tour> getAllTour(){
        return tourRepository.getAllTour();
    };
    public List<Tour> getTourActive(){
        return tourRepository.getListTourActive();
    };
    public List<ItemTour> getTourHomeActive(){
        List<ItemTour> itemTours=tourRepository.getListTourHomeActive();
        for (int i=0;i<itemTours.size();i++)
        {
            itemTours.get(i).setStar(danhGia(itemTours.get(i).getId()));
        }
        return itemTours;
    };

    public double danhGia(String idTour){

        double total=0;
        List<Rating> ratings=ratingService.getRatingByidTour(idTour);
        for(int i=0;i<ratings.size();i++)
        {
            total=total+ratings.get(i).getStar();
        }
        if (ratings.size()==0) return 5.0;
        return total/ratings.size();
    }

    public List<Tour> getTourFilter(FilterTour filterTour){
        if(filterTour.getAddress()==null){
            if(filterTour.getIdCategory()==null) return tourRepository.filterNoAddressNoCategory(filterTour.getGt(),filterTour.getLt(),filterTour.getSort());
            else return tourRepository.filterNoAddress(filterTour.getIdCategory(),filterTour.getGt(),filterTour.getLt(),filterTour.getSort());
        }else if(filterTour.getIdCategory()==null) return tourRepository.filterNoCategory(filterTour.getAddress(),filterTour.getGt(),filterTour.getLt(),filterTour.getSort());
        else return tourRepository.filter(filterTour.getAddress(),filterTour.getIdCategory(),filterTour.getGt(),filterTour.getLt(),filterTour.getSort());
    };
    public Map<String,Object> addTour(Tour tour) {
        Map<String, Object> m = new HashMap<>();
        if (tour.getIdAccount() == null) {
            m.put("message", "Không thành công");
            m.put("status", "0");
        } else {
            LocalDateTime now=LocalDateTime.now();
            tour.setTimeUpdate(now);
            tourRepository.save(tour);
            m.put("message", "Thêm mới tour thành công");
            m.put("status", "1");
            m.put("tour", tour);
        }
        return m;
    }
    public Tour getTourById(String id){
        return tourRepository.findById(id).get();
    }
    public List<Tour> getTourByCategory(String idCategory){
        return tourRepository.getListTourByCategory(idCategory);
    }
    public Tour updateTour(Tour tour){
        LocalDateTime now=LocalDateTime.now();
        Tour tour1=tourRepository.findById(tour.getId()).get();
        tour1.setTitle(tour.getTitle());
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
        tour1.setHanhtrinh(tour.getHanhtrinh());
        tour1.setIdService(tour.getIdService());
        tour1.setTimeUpdate(now);
        return tourRepository.save(tour1);
    }
    public String deleteTour(String idTour){
        if(scheduleService.getListScheduleByIdTour(idTour).size()!=0){
            return "Vui long huy tat ca lich trinh de co the xoa tour";
        }
        else {
            tourRepository.deleteById(idTour);
            return "Xoa tour thanh cong";
        }
    }
    public List<Tour> searchTour(String key){
        return tourRepository.searchTour(key);
    }
    public int countTour(){
        return tourRepository.findAll().size();
    }
    public String deleteListTour(List<String> list){
        for(int i=0;i<list.size();i++)
        {
            if(scheduleService.getListScheduleByIdTour(list.get(i)).size()!=0){
                return "Tour đã co lich - khong duoc xoa";
            }

        }
        tourRepository.deleteAllById(list);
        return "Xóa thành công";
    }



}
