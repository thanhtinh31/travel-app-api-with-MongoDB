package com.example.travel_app_api.service;

import com.example.travel_app_api.model.Category;
import com.example.travel_app_api.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CategoryService {
    @Autowired
    CategoryRepository categoryRepository;
    public  Page<Category> findCategory(int page,int size,String sort){
        if(page<=0) page=1;
        return categoryRepository.findAll(PageRequest.of(page-1,size,Sort.by(sort).descending()));
    };
    public List<Category> searchCategory(String key){
        return categoryRepository.searchCategory(key);
    }
    public Category addCategory(Category category){
        return categoryRepository.save(category);
    }
}
