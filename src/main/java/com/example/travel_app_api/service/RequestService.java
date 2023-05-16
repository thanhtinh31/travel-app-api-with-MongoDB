package com.example.travel_app_api.service;

import com.example.travel_app_api.model.Invoice;
import com.example.travel_app_api.model.Request;
import com.example.travel_app_api.model.Schedule;
import com.example.travel_app_api.model.Tour;
import com.example.travel_app_api.repository.RequestRepository;
import org.jetbrains.annotations.Async;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class RequestService {
    @Autowired
    RequestRepository requestRepository;
    @Autowired
    ScheduleService scheduleService;
    @Autowired
    InvoiceService invoiceService;
    @Autowired TourService tourService;
    @Autowired EmailSenderService emailSenderService;
    public List<Request> getAllRequest(){
        return requestRepository.findAll();
    }
    public Map<String,Object> accept(String id,Schedule schedule){
        Request request= requestRepository.findById(id).get();
        Tour tour=tourService.getTourById(request.getIdTour());
        System.out.println(schedule.getTourGuide());
        schedule.setProgress(1);
        schedule.setStatus(false);
        schedule.setExpectedPeople(request.getPeople());
        schedule.setDayStart(request.getDayStart());
        schedule.setAddressStart(request.getAddressStart());
        schedule.setType("YC");
        schedule.setIdTour(request.getIdTour());
        Schedule schedule1=scheduleService.addnew(schedule);
        request.setAccept(1);
        requestRepository.save(request);
        Invoice invoice=new Invoice();
        invoice.setFullName(request.getFullName());
        invoice.setPhone(request.getPhone());
        invoice.setEmail(request.getEmail());
        invoice.setAddress(request.getAddress());
        invoice.setPeople(request.getPeople());
        invoice.setAmount((tour.getPrice()-tour.getSale()*tour.getPrice())*request.getPeople());
        invoice.setIdSchedule(schedule1.getId());
        invoice.setIdAccount(request.getIdAccount());
        invoice.setStatus(1);
        Map<String,Object> m=invoiceService.addNewInvoice(invoice);
        emailSenderService.sendMailHtml(request.getEmail(),"Thông báo","Yêu cầu đặt tour của bạn đã được xác nhận, Vui lòng thanh toán hóa đơn");
        return m;
    }
    public Map<String,Object> cancel(String id){
        Request request= requestRepository.findById(id).get();
        request.setAccept(2);
        requestRepository.save(request);
        emailSenderService.sendMailHtml(request.getEmail(),"Thông báo hủy yêu cầu","Yêu cầu đặt tour của bạn không được chấp nhận");
        return null;
    }
    public Request addRequest(Request request){
        requestRepository.save(request);
        return request;
    }
    public List<Request> getRequestByAccept(int accept){
        return requestRepository.getRequestByAccept(accept);
    }
    public String deleteRequest(String id)
    {
        requestRepository.deleteById(id);
        return "Xóa thành công";
    }
    public int count(int accept){
        int c=0;
        List<Request> list=getAllRequest();
        for (int i=0;i<list.size();i++)
        {
            if(list.get(i).getAccept()==accept) c++;
        }
        return c;
    }
}
