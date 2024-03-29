package com.example.travel_app_api.service;

import com.example.travel_app_api.model.Category;
import com.example.travel_app_api.model.Tour;
import com.example.travel_app_api.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class CategoryService {
    @Autowired
    CategoryRepository categoryRepository;
    public List<Category> getAll(){return categoryRepository.findAll();}
    public  Page<Category> findCategory(int page,int size,String sort){
        if(page<=0) page=1;
        return categoryRepository.findAll(PageRequest.of(page-1,size,Sort.by(sort).descending()));
    };
    public List<Category> getListCategoryActive(){
        return categoryRepository.getListCategoryActive();
    }
    public List<Category> getCategoryHome(){
        return categoryRepository.getListCategoryHome();
    };
    public Category findById(String id){
        try {
            return categoryRepository.findById(id).get();
        }catch (Exception e){
            return null;
        }
    }
    public List<Category> searchCategory(String key){
        return categoryRepository.searchCategory(key);
    }
    public Map<String,Object> addCategory(Category category){
        Map<String,Object> m=new HashMap<>();
        if(categoryRepository.findByName(category.getName())==null){
            LocalDateTime now=LocalDateTime.now();
            category.setTimeUpdate(now);
            categoryRepository.save(category);
            m.put("message","Thêm mới thành công");
            m.put("status","1");
        }
        else{
            m.put("message","Tên danh mục đã tồn tại");
            m.put("status","0");
        }

        return m;
    }
    public Category updateCategory(Category category){
        LocalDateTime now=LocalDateTime.now();
        Category category1=categoryRepository.findById(category.getId()).get();
        category1.setName(category.getName());
        category1.setContent(category.getContent());
        category1.setImage(category.getImage());
        category1.setStatus(category.isStatus());
        category1.setTimeUpdate(now);
        return categoryRepository.save(category1);

    }
    public String deleteCategory(String id){
            categoryRepository.deleteById(id);
            return "Deleted";
    }
    public int countCategory(){
        return categoryRepository.findAll().size();
    }

}
