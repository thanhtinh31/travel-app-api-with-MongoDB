package com.example.travel_app_api.service;

import com.example.travel_app_api.repository.RatingRepository;
import com.example.travel_app_api.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ServiceService {
    @Autowired
    private ServiceRepository serviceRepository;
    public List<com.example.travel_app_api.model.Service> getServiceActive(){
        return serviceRepository.getAllServiceActive();
    }

    public List<com.example.travel_app_api.model.Service> getAllService(){
        return serviceRepository.findAll();
    }
    public Map<String,Object> addNew(com.example.travel_app_api.model.Service service)
    {
        Map<String,Object> m=new HashMap<>();
        List<com.example.travel_app_api.model.Service> services=getAllService();
        for (int i=0;i<services.size();i++){
            if(service.getName().equals(services.get(i).getName())) {
                m.put("status", "0");
                m.put("message", "Tên dịch vụ đã tồn tại");
                return m;
            }
        }
        m.put("status","1");
        m.put("message","Thêm mới thành công");
        serviceRepository.save(service);
        m.put("service",service);
        return m;
    }

    public String deleteByid(String id){
        try {
            serviceRepository.deleteById(id);
            return "Xóa thành công";
        }catch (Exception e){
            return "Không thành công";
        }
    }
    public com.example.travel_app_api.model.Service getServiceById(String id){
        return serviceRepository.getServiceByid(id);
    }
    public Map<String,Object> update(com.example.travel_app_api.model.Service service){
        Map<String,Object> m=new HashMap<>();
        com.example.travel_app_api.model.Service service1=getServiceById(service.getId());
        if(service1.getName().equals(service.getName())){
            service1.setDescrible(service.getDescrible());
            service1.setIcon(service.getIcon());
            service1.setStatus(service.getStatus());
            serviceRepository.save(service1);
            m.put("status","1");
            m.put("message","Cập nhập thành công");
            m.put("service",service1);
            return m;
        }else {
            List<com.example.travel_app_api.model.Service> services = getAllService();
            for (int i = 0; i <services.size(); i++) {
                if (service.getName().equals(services.get(i).getName())) {
                    m.put("status", "0");
                    m.put("message", "Tên dịch vụ đã tồn tại");
                    return m;
                }
            }
            service1.setDescrible(service.getDescrible());
            service1.setIcon(service.getIcon());
            service1.setStatus(service.getStatus());
            service1.setName(service.getName());
            m.put("status", "1");
            m.put("message", "Cập nhật thành công");
            serviceRepository.save(service1);
            m.put("service", service1);
        }
        return m;
    }
    public int countService(){
        return serviceRepository.findAll().size();
    }
}
